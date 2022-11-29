package inpdf.watcher;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;

import inpdf.BoletoReader;
import inpdf.DirectoryManager;
import inpdf.DocumentType;
import inpdf.IRPFReader;
import inpdf.Reader;

public class WatcherService implements Runnable {
	private static Path directory;
	private static Queue<Path> filePaths = new PriorityBlockingQueue<Path>();
	private static volatile boolean suspended = false;
    private final Object pauseLock = new Object();
    private static int interval = 1000;
    
    private WatchService watchService;
    private WatchKey watchKey;
	
	public WatcherService() {
		directory = DirectoryManager.getInputDirectoryPath();
	}
	
	public WatcherService(Path dir) {
		directory = dir;
	}
	
	@Override
	public void run() {		
		try {
			watchService = FileSystems.getDefault().newWatchService();
			watchKey = directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

			while (true && !Thread.interrupted()) {
				System.out.println("Procurando arquivos em '" + directory + "' ...");
				Thread.sleep(interval);
				
				synchronized (pauseLock) {
                	while (suspended) {
                		pauseLock.wait();
                		watchKey.pollEvents();
                	}
				}
				
				for (WatchEvent<?> event : watchKey.pollEvents()) {
					if (!filePaths.contains((Path) event.context())) {
						Path path = directory.resolve((Path) event.context());
						System.out.println("PATH: " + path);
						boolean pass;
						
						try {
							pass = Reader.checkFileConformity(path);				
						} catch (Exception e) {
							e.printStackTrace();
							continue;
						}
						
						if (pass) {
							DocumentType docType = Reader.determineDocumentType(path);
							if (docType != null && docType != DocumentType.UNKNOWN) {
								convert(path, docType);
							} else {
								System.out.println("Não foi possível ler o tipo de documento");
								DirectoryManager.moveToRejectedFolder(path);
							}
						} else {
							DirectoryManager.moveToRejectedFolder(path);
						}
					}
				}
				
				if (!watchKey.reset()) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private <T> void convert(Path path, DocumentType docType) throws IOException {
		T type = (T) Reader.createReaderFromType(path, docType);
		
		if (type instanceof IRPFReader) {
			IRPFReader reader = (IRPFReader) type;
			reader.readPDF(path);
		} else if (type instanceof BoletoReader) {
			BoletoReader reader = (BoletoReader) type;
			reader.readPDF(path, docType);
		}
	}

    public void suspend() {
    	suspended = true;
    }

    public void resume() {
        synchronized (pauseLock) {
        	suspended = false;
            pauseLock.notifyAll();
        }
    }
    
    public void change(Path newDir) {
    	watchKey.cancel();
    	directory = newDir;
    	
    	try {
			watchKey = directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);		
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	public Queue<Path> getFilePathsQueue() {
		return filePaths;
	}
	
	public boolean getState() {
		return suspended;
	}

	public static void setInterval(int interval) {
		WatcherService.interval = interval;
	}
	
	public static int getInterval() {
		return WatcherService.interval;
	}
}
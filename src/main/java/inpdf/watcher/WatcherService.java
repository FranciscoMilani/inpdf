package inpdf.watcher;

import java.awt.AWTEvent;
import java.awt.Desktop.Action;
import java.awt.desktop.ScreenSleepEvent;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RunnableFuture;

import inpdf.DirectoryManager;
import inpdf.Reader;

public class WatcherService implements Runnable {
	private static Path directory;
	private static Queue<Path> filePaths = new PriorityBlockingQueue<Path>();
	private static volatile boolean suspended = false;
    private final Object pauseLock = new Object();
    private final int interval = 1000;
    
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

			while (true) {
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
						//filePaths.add(path);
						boolean pass = Reader.checkFileConformity(path);
						if (pass) {
							new Reader().ReadPDF(path);
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
			Thread.currentThread().interrupt();
		}
	}

    public void suspend() {
        // you may want to throw an IllegalStateException if !running
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
}
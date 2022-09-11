package inpdf;

public class GeneralConfiguration {
	private String inputPath;
	private String outputPath;
	private String rejectedPath;
	
	public String getInputPath() {
		return inputPath;
	}
	public void setInputPath(String inputPath) {
		this.inputPath = inputPath;
	}
	public String getOutputPath() {
		return outputPath;
	}
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}
	public String getRejectedPath() {
		return rejectedPath;
	}
	public void setRejectedPath(String rejectedPath) {
		this.rejectedPath = rejectedPath;
	}
}

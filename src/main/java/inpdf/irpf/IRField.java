package inpdf.irpf;

import com.google.gson.annotations.Expose;

public class IRField {
	@Expose
	private final String name;
	@Expose
	private Integer line;
	@Expose
	private Boolean read;
	
	public IRField(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public Integer getLine() {
		return line;
	}
	
	public void setLine(Integer line) {
		this.line = line;
	}
	
	public Boolean getRead() {
		return read;
	}
	
	public void setRead(Boolean read) {
		this.read = read;
	}
}
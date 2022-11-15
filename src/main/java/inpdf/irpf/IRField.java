package inpdf.irpf;

public class IRField {
	private final String name;
	private String value;
	private Integer line;
	private Boolean read;
	
	public IRField(String name) {
		this.name = name;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
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
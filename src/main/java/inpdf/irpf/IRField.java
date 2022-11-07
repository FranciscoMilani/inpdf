package inpdf.irpf;

public class IRField {
	private final String name;
	private String value;
	
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
}
package myTest.accessModifierTest;

public class ParentClass {

	private String privateString = "privateSTring";

	String defaultString = "defaultString";

	protected String protectedString = "protectedString";

	public String publicString = "publicString";

	private String privateMethod() {
		System.out.println("privateMethod called");
		return "privateMethod";
	}

	String defaultMethod() {
		System.out.println("defaultMethod called");
		return "defaultMethod";

	}

	protected String protectedMethod() {
		System.out.println("protectedMethod called");
		return "protectedMethod";
	}

	public String publicMethod() {
		System.out.println("publicMethod called");
		return "publicMethod";
	}
}


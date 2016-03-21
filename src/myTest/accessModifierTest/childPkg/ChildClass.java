package myTest.accessModifierTest.childPkg;

import myTest.accessModifierTest.ParentClass;

public class ChildClass extends ParentClass {

	protected String protectedString = "protectedString2222";

	public static void main(String... args) {

		ChildClass a = new ChildClass();

		// System.out.println(a.privateString);
		// System.out.println(a.defaultString);
		System.out.println(a.protectedString);
		System.out.println(a.publicString);

		// System.out.println(a.privateMethod());
		// System.out.println(a.defaultMethod());
		System.out.println(a.protectedMethod());
		System.out.println(a.publicMethod());

	}

}

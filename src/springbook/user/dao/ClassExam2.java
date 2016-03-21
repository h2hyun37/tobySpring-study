package springbook.user.dao;

public class ClassExam2 extends ClassExam {

	public void myMethod() {

		System.out.println("myMethod");
	}

	public void method2() {

	}

	public static void main(String[] args) {

		ClassExam obj = new ClassExam2();

		System.out.println(obj.getClass().getName());

	}

}

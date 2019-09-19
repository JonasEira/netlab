package main;

public class Main {

	public static void main(String[] args) {
		JavaModel jm = new JavaModel();
		JavaView jv = new JavaView(jm);
		jm.addDatainterface(jv);
	}
}

package main;

public class Main {

	public static void main(String[] args) {
		JavaView jv = new JavaView();
		JavaModel jm = new JavaModel();
		jv.addDataInterface(jm);
	}
}

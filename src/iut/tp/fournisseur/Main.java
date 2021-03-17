package iut.tp.fournisseur;

public class Main {

	public static void main(String[] args) {
		
		FileReader f = new FileReader(args[0]);
//		System.out.println(f.aff());
		System.out.println(f.eval());

	}
}

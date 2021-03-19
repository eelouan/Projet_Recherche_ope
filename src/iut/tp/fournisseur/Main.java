package iut.tp.fournisseur;

public class Main {

	public static void main(String[] args) {
		
		FileReader f = new FileReader(args[0]);
		System.out.println("Eval(i)");
		System.out.println();
		System.out.println(f.evalBis());
		System.out.println();
		System.out.println("/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*");
		System.out.println();
		System.out.println("Algorithme glouton : ");
		System.out.println();
		f.glouton();
//		System.out.println(f.eval(1));
//		System.out.println(f.aff());
//		System.out.println(f.evalBis());
//		System.out.println(f.evalBis().size());

	}
}

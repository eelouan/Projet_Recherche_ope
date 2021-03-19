package iut.tp.fournisseur;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

public class FileReader {
	
	private List<Fournisseur> listeFournisseurs;
	public String nomFichier = null;
	public int nbFournisseurs = 0;
	public int nbClients = 0;
	
	public FileReader(String args) {
		Path path = Paths.get(args);
	    
try(BufferedReader reader = Files.newBufferedReader(path)) {
			
			// Lecture premiere ligne : nom fichier
			nomFichier = reader.readLine();
//			System.out.println("Le nom du fichier est : " + nomFichier);
			
			// Lecture seconde ligne : nbFournisseurs et nbClients
			String secondeLigne = reader.readLine();
			String[] tableauLigne = secondeLigne.split(" ");
			nbFournisseurs = Integer.parseInt(tableauLigne[0]);
			nbClients = Integer.parseInt(tableauLigne[1]);
			listeFournisseurs = new ArrayList<>(nbFournisseurs);
			
			// Lecture des lignes correspondant à un fournisseur
			for(int i =0; i<nbFournisseurs; i++) {
				String ligneFournisseur = reader.readLine();
				listeFournisseurs.add(traiterLigneFournisseur(ligneFournisseur, nbClients));
			}
			
			// 2 -  cout client minimal et maximal pour chaque fournisseur
//			System.out.println("\nEXEMPLE 2");	
			for(Fournisseur fournisseur : listeFournisseurs) {
				Integer coutMinimal = Collections.min(fournisseur.getListeCoutsClients());
//				System.out.println("Le cout client minimal pour le fournisseur " + fournisseur.getNumero() + " est " + coutMinimal);	
				Integer coutMaximal = Collections.max(fournisseur.getListeCoutsClients());
//				System.out.println("Le cout client maximal pour le fournisseur " + fournisseur.getNumero() + " est " + coutMaximal);	
			}
			
			// 3 - Affichier le coût du troisième client pour tous les fournisseurs
//			System.out.println("\nEXEMPLE 3");	
			for(Fournisseur fournisseur : listeFournisseurs) {
//				System.out.println("Le coût pour le troisième client est " + fournisseur.getListeCoutsClients().get(2));	
			}
			
			// 4 - Quel est le fournisseur qui a le coût client minimal pour le troisième client
//			System.out.println("\nEXEMPLE 4");	
			// Liste de travail de tou sles couts pour le troisieme client
			List<Integer> listeCoutsTroisiemeClient = new ArrayList<Integer>(nbFournisseurs);
			
			for(Fournisseur fournisseur : listeFournisseurs) {
				listeCoutsTroisiemeClient.add(fournisseur.getListeCoutsClients().get(2));
			}
			Integer coutMinimalClient = Collections.min(listeCoutsTroisiemeClient);
//			System.out.println("Le cout minimal pour le troisième client est " + coutMinimalClient);
			
			Integer indexFournisseurCoutMini = listeCoutsTroisiemeClient.indexOf(coutMinimalClient);
//			System.out.println("Le fournisseur numéro " + listeFournisseurs.get(indexFournisseurCoutMini).getNumero() + " a le coût minimal pour le troisieme client");
			
			
			    
		} catch (IOException e) {
			System.out.println("Erreur d'accès au fichier spécifié");
		}	
	}
		
	/**
	* Traiter une ligne fournisseur. 
	* Renvoie l'objet Fournisseur correspondant à la ligne.
	*
	* @param ligneFournisseur the ligne fournisseur
	* @param nbClients the nb clients
	* @return the fournisseur
	*/
	private Fournisseur traiterLigneFournisseur(String ligneFournisseur, int nbClients) {
		String[] tableauFournisseur = ligneFournisseur.split(" ");
		 
		// Instanciation fournisseur
		Fournisseur fournisseur = new Fournisseur();
		// Renseignement des propriétés fixes numero et coutOuverture
		fournisseur.setNumero(Integer.parseInt(tableauFournisseur[0]));
		fournisseur.setCoutOuverture(Integer.parseInt(tableauFournisseur[1]));
		// Instanciation nouvelle liste
		fournisseur.setListeCoutsClients(new ArrayList<>(nbClients));
		 
		// Renseignement de la liste de couts clients
		for(int i =2; i <= nbClients + 1; i++) {
			 fournisseur.getListeCoutsClients().add(Integer.parseInt(tableauFournisseur[i]));
		}		
		 
		 return fournisseur;
	}
	
	public ArrayList<Integer> evalTest() {
		ArrayList<Integer> cout = new ArrayList<>();
		for(Fournisseur f : listeFournisseurs) {
			int coutMinimal = Collections.min(f.getListeCoutsClients());
			coutMinimal += f.getCoutOuverture();
			cout.add(coutMinimal);
		}
		return cout;
	}
	
	public ArrayList<Integer> evalBis(){
		ArrayList<Integer> cout = new ArrayList<>();
		int c = 0;
		for(Fournisseur f : listeFournisseurs) {
			for(int i = 0 ; i < nbClients ; ++i) {
				c += f.getListeCoutsClients().get(i);
			}
			cout.add(f.getCoutOuverture() + c);
			c = 0;
		}
		return cout;
	}
	
	public Integer eval(int nb){
		ArrayList<Integer> cout = new ArrayList<>();
		int c = 0;
		for(Fournisseur f : listeFournisseurs) {
			for(int i = 0 ; i < nbClients ; ++i) {
				c += f.getListeCoutsClients().get(i);		
			}
//			System.out.println(f.getCoutOuverture() + c);
			cout.add(f.getCoutOuverture() + c);
			c = 0;
		}
		return cout.get(nb);
	}
	
	public void glouton() {//l'algo glouton de la q3
		boolean over = true;
		ArrayList<Integer> listI = new ArrayList<>();//contient les num forunisseurs
		ArrayList<Integer> tabEvalTmp = new ArrayList<>(this.evalBis());//contient les résultats eval
		ArrayList<Integer> coutClientsMin = new ArrayList<>();// tableau des clients min 
		ArrayList<Integer> minEval = new ArrayList<>();//contient toute les plus petite valeur 
		int coutMinimal = 0;
		int index = 0;
		int king = 0;
		int i = 0;
		int coutMinimalF = 0;
		int evalTmp = 0;

		do {
			coutMinimal = Collections.min(tabEvalTmp);//on prend la plus petite eval de la liste
			index = tabEvalTmp.indexOf(coutMinimal);//on prend le num forunisseur de l'eval min
			tabEvalTmp.remove(index);//on supprimme pour faire les calculs
				for(int u = 0 ; u < tabEvalTmp.size() ; ++u) { //une itération
					Fournisseur f = listeFournisseurs.get(u);//on prend le num fournisseur dans le tableau
					if(f.getNumero() != listeFournisseurs.get(king).getNumero()) {//if 
						for(int k = 0 ; k < nbClients ; ++k) { //on incremente k tant que pas fais tout les fournisseurs
							coutClientsMin.add(f.getListeCoutsClients().get(k));
							coutClientsMin.add(listeFournisseurs.get(king).getListeCoutsClients().get(k));
							coutMinimalF += Collections.min(coutClientsMin);
							coutClientsMin.clear();
						}
					}
					evalTmp = listeFournisseurs.get(king).getCoutOuverture() + f.getCoutOuverture() + coutMinimalF;
					coutMinimalF = 0;
					minEval.add(evalTmp);
				}
				over = Collections.min(minEval) > listeFournisseurs.get(king).getCoutOuverture() ? true : false;
				king = index;
			++i;
		}while(over && i < this.evalBis().size());
		System.out.println("eval(O) : " + Collections.min(minEval) + " ; nombre d'iteration : " + i);
	}
	
	public void gloutonB() {
		boolean over = true;
		ArrayList<Integer> listI = new ArrayList<>();
		ArrayList<Integer> tabEvalTmp = new ArrayList<>(this.evalBis());
		ArrayList<Integer> coutClientsMin = new ArrayList<>();
		ArrayList<Integer> minEval = new ArrayList<>();
		int coutMinimal = 0;
		int index = 0;
		int king = 0;
		int i = 0;
		int coutTmp = 0;
		int coutMinimalF = 0;
		int evalTmp = 0;
		for(int it = 0 ; it < nbFournisseurs ; ++it) {
			listI.add(it);
		}
//		System.out.println(tabEvalTmp);
		for(int c = 0 ; c < 2 ; ++c) {
			
//			minEval.clear();
			coutMinimal = Collections.min(tabEvalTmp);
			index = tabEvalTmp.indexOf(coutMinimal);
		System.out.println(coutMinimal);
			tabEvalTmp.remove(index);
			listI.remove(index);
			king = index;
			minEval.clear();
			System.out.println(listI);
			for(int u = 0 ; u < this.evalBis().size() ; ++u) {
				Fournisseur f = listeFournisseurs.get(u);
				if(f.getNumero() != listeFournisseurs.get(index).getNumero()) {
					for(int k = 0 ; k < nbClients ; ++k) {
						coutClientsMin.add(f.getListeCoutsClients().get(k));
						coutClientsMin.add(listeFournisseurs.get(king).getListeCoutsClients().get(k));
//					System.out.println(coutClientsMin);
						coutMinimalF = Collections.min(coutClientsMin);
						coutTmp += coutMinimalF;
//					System.out.println("cout :" + coutMinimalF);
//					System.out.println(" " + coutTmp);
						coutClientsMin.clear();
					}
//				System.out.println(coutTmp);
//				System.out.println(listeFournisseurs.get(index).getCoutOuverture() + " " + f.getCoutOuverture());
//					System.out.println(minEval);
					evalTmp = listeFournisseurs.get(king).getCoutOuverture() + f.getCoutOuverture() + coutTmp;
					coutTmp = 0;
					minEval.add(evalTmp);
				}
			}
			tabEvalTmp = minEval;
			System.out.println(minEval);
//			tabEvalTmp.clear();
//		System.out.println(Collections.min(tabEvalTmp));
//			minEval.clear();
		}
	}
	
	public void jenaimarre() {
		ArrayList<Integer> listI = new ArrayList<>();
		TreeMap<Integer, Integer> cf = new TreeMap<>();
		ArrayList<Integer> evalTmp = new ArrayList<>();
		int coutMinimal = 0;
		int min = 0;
		int index = 0;
		int coutMinimalF = 0;
		int coutTmp = 0;
//		int evalTmp = 0;
		int king = 0;
		int taille = 0;
		int i = 0;
		boolean over = true;
		ArrayList<Integer> coutClientsMin = new ArrayList<>();
		ArrayList<Integer> ind = new ArrayList<>();
//		int index = tabEvalTmp.indexOf(coutMinimal);
//		Fournisseur f = new Fournisseur();
//		int coutF = f.getCoutOuverture();
//		System.out.println(coutMinarg1imal);
		for(int it = 0 ; it < nbFournisseurs ; ++it) {
			 cf.put(this.evalBis().get(it), it);
			 listI.add(it);
		}

		min = Collections.min(this.evalBis());
		do {
			coutMinimal = i < 1 ? Collections.min(this.evalBis()) : Collections.min((evalTmp));
			evalTmp.clear();
//			System.out.println(cf);
			index = cf.get(coutMinimal);
//			listI.remove(index);
//			System.out.println(listI);
//			taille = listI.size();
//			king = index;
			cf.clear();
//			ind.add(index);
//			cf.remove(coutMinimal);
//			System.out.println(cf);
			for(int u = 0 ; u < this.evalBis().size() ; ++u) {
				Fournisseur f = listeFournisseurs.get(u);
				if(f.getNumero() != listeFournisseurs.get(index).getNumero()) {
					for(int k = 0 ; k < nbClients ; ++k) {
						coutClientsMin.add(f.getListeCoutsClients().get(k));
						coutClientsMin.add(listeFournisseurs.get(index).getListeCoutsClients().get(k));
//					System.out.println(coutClientsMin);
						coutMinimalF = Collections.min(coutClientsMin);
						coutTmp += coutMinimalF;
						coutClientsMin.clear();
//						System.out.println(coutMinimalF);
					}
					evalTmp.add(listeFournisseurs.get(index).getCoutOuverture() + f.getCoutOuverture() + coutTmp);
					cf.put(listeFournisseurs.get(index).getCoutOuverture() + f.getCoutOuverture() + coutTmp, listI.get(u));
				}
				coutTmp = 0;
				
			}

//			System.out.println(cf);
//			System.out.println(evalTmp);
//			System.out.println(cf.);
			++i;
			if(min <= coutMinimal) {
				over = false;
			} else {
				min = coutMinimal;
//				System.out.println(min);
//				System.out.println(coutMinimal);
//				System.out.println();
			}
		}while(over || i < nbFournisseurs);
		System.out.println("Eval(O) : " + min);
	}
}
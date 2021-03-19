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

	/*
	 * Algo calculant Eval(0) de façon optimal grace à la  méthode gloutonne
	 */
	public void glouton() {
		ArrayList<Integer> listI = new ArrayList<>(); // Liste des fournisseurs restant
		TreeMap<Integer, Integer> cf = new TreeMap<>(); // Liste associative de Eval
		ArrayList<Integer> evalTmp = new ArrayList<>();
		int coutMinimal = 0;
		int min = 0; // Sock le cout d'avant
		int index = 0;
		int coutMinimalF = 0;
		int coutTmp = 0;
		int i = 0;
		boolean over = true; // Variable de sortie de boucle  
		ArrayList<Integer> coutClientsMin = new ArrayList<>(); // Contient Cij
		for(int it = 0 ; it < nbFournisseurs ; ++it) { // Nous remplissons cf et listI
			 cf.put(this.evalBis().get(it), it);
			 listI.add(it);
		}

		min = Collections.min(this.evalBis());
		do { // Boucle de l'algo
			coutMinimal = i < 1 ? Collections.min(this.evalBis()) : Collections.min((evalTmp)); // coutMinimal Prend le min de la liste this.evalBis() ou de evalTmp en fonction de l'iteration
			evalTmp.clear();
			index = cf.get(coutMinimal); // index du coutMinimal -> donc le fournisseur i-1
			cf.clear(); // on vide le contenue car il ne nous sert plus à rien
			for(int u = 0 ; u < this.evalBis().size() ; ++u) {
				Fournisseur f = listeFournisseurs.get(u);
				if(f.getNumero() != listeFournisseurs.get(index).getNumero()) { // Condition pour ne pas ajouter deux fois le fournisseur 
					for(int k = 0 ; k < nbClients ; ++k) { // Parcours de tous les clients
						coutClientsMin.add(f.getListeCoutsClients().get(k));
						coutClientsMin.add(listeFournisseurs.get(index).getListeCoutsClients().get(k));
						coutMinimalF = Collections.min(coutClientsMin); // Nous prenons le minimal de ces deux cout
						coutTmp += coutMinimalF; // tous les couts minimals des clients sont ajouter et stock dans la variable coutTmp
						coutClientsMin.clear();
					}
					evalTmp.add(listeFournisseurs.get(index).getCoutOuverture() + f.getCoutOuverture() + coutTmp); // Ajout de l'eval(i) dans evalTmp
					cf.put(listeFournisseurs.get(index).getCoutOuverture() + f.getCoutOuverture() + coutTmp, listI.get(u)); // Ajout dans cf [eval(i),Fi]
				}
				coutTmp = 0;
				
			}

			++i;
			if(min <= coutMinimal) { // Condition de fin de boucle
				over = false;
			} else {
				min = coutMinimal;
			}
		}while(over || i < nbFournisseurs);
		System.out.println("Eval(O) : " + min); // min est le resultat	
	}
}
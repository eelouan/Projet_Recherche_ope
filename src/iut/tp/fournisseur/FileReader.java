package iut.tp.fournisseur;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileReader {

	public static void main(String[] args) {	
		
		String nomFichier = null;
		int nbFournisseurs = 0;
		int nbClients = 0;
		List<Fournisseur> listeFournisseurs;
		Path path = Paths.get(args[0]);
	    
		try(BufferedReader reader = Files.newBufferedReader(path)) {
			
			// Lecture premiere ligne : nom fichier
			nomFichier = reader.readLine();
			System.out.println("Le nom du fichier est : " + nomFichier);
			
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
			
			System.out.println("Les " + nbFournisseurs + " fournisseurs ont été traités.");
			
			// Exemples de traitements
			
			// 1 -  Recupération du numero du premier fournisseur de la liste
			System.out.println("\nEXEMPLE 1");	
			System.out.println("Le numéro du premier fournisseur est " + listeFournisseurs.get(0).getNumero());	
			
			
			// 2 -  cout client minimal et maximal pour chaque fournisseur
			System.out.println("\nEXEMPLE 2");	
			for(Fournisseur fournisseur : listeFournisseurs) {
				Integer coutMinimal = Collections.min(fournisseur.getListeCoutsClients());
				System.out.println("Le cout client minimal pour le fournisseur " + fournisseur.getNumero() + " est " + coutMinimal);	
				Integer coutMaximal = Collections.max(fournisseur.getListeCoutsClients());
				System.out.println("Le cout client maximal pour le fournisseur " + fournisseur.getNumero() + " est " + coutMaximal);	
			}
			
			// 3 - Affichier le coût du troisième client pour tous les fournisseurs
			System.out.println("\nEXEMPLE 3");	
			for(Fournisseur fournisseur : listeFournisseurs) {
				System.out.println("Le coût pour le troisième client est " + fournisseur.getListeCoutsClients().get(2));	
			}
			
			// 4 - Quel est le fournisseur qui a le coût client minimal pour le troisième client
			System.out.println("\nEXEMPLE 4");	
			// Liste de travail de tou sles couts pour le troisieme client
			List<Integer> listeCoutsTroisiemeClient = new ArrayList<Integer>(nbFournisseurs);
			
			for(Fournisseur fournisseur : listeFournisseurs) {
				listeCoutsTroisiemeClient.add(fournisseur.getListeCoutsClients().get(2));
			}
			Integer coutMinimalClient = Collections.min(listeCoutsTroisiemeClient);
			System.out.println("Le cout minimal pour le troisième client est " + coutMinimalClient);
			
			Integer indexFournisseurCoutMini = listeCoutsTroisiemeClient.indexOf(coutMinimalClient);
			System.out.println("Le fournisseur numéro " + listeFournisseurs.get(indexFournisseurCoutMini).getNumero() + " a le coût minimal pour le troisieme client");
			
			
			    
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
	private static Fournisseur traiterLigneFournisseur(String ligneFournisseur, int nbClients) {
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

}
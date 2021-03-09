package iut.tp.fournisseur;

import java.util.List;

public class Fournisseur {
	
	private Integer numero;
	
	private Integer coutOuverture;
	
	private List<Integer> listeCoutsClients;
	
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Integer getCoutOuverture() {
		return coutOuverture;
	}

	public void setCoutOuverture(Integer coutOuverture) {
		this.coutOuverture = coutOuverture;
	}

	public List<Integer> getListeCoutsClients() {
		return listeCoutsClients;
	}

	public void setListeCoutsClients(List<Integer> listeCoutsClients) {
		this.listeCoutsClients = listeCoutsClients;
	}	

}
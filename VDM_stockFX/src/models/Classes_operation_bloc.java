package models;

import controllers.*;

public enum Classes_operation_bloc {
	
	NOUVELLE("nouvelle", Operation_nouvelle_controller.class),
	DERNIERES("dernieres", Operation_dernieres_controller.class);
	
	private String nom;
	private Class<?> classe;
	
	Classes_operation_bloc(String nom, Class<?> classe){
		
		this.nom = nom;
		this.classe = classe;
	}

	public String getNom() {
		return nom;
	}

	public Class<?> getClasse() {
		return classe;
	}
	
	

}

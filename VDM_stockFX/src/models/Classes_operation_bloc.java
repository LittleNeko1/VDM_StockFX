package models;

import controllers.*;

public enum Classes_operation_bloc {
	
	NOUVELLE("nouvelle", Operation_nouvelle_controller.class, Operation.class),
	DERNIERES("dernières", Operation_dernieres_controller.class, null);
	
	private String nom;
	private Class<?> classe;
	private Class<Enregistrable> operation;
	
	Classes_operation_bloc(String nom, Class<?> classe, Class<?> operation){
		
		this.nom = nom;
		this.classe = classe;
		this.operation = (Class<Enregistrable>) operation;
	}

	public String getNom() {
		return nom;
	}

	public Class<?> getClasse() {
		return classe;
	}

	public Class<Enregistrable> getOperation() {
		return operation;
	}

	public void setOperation(Class<Enregistrable> operation) {
		this.operation = operation;
	}
	
	

}

package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jongo.MongoCursor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import models.Commun;
import models.Enregistrable;
import models.Expediteur;
import models.Operation;
import utils.AutoCompletion;
import utils.CustomComboBox;
import utils.Messages;
import utils.MongoAccess;

/**
 * Controle le formulaire pour enregistrer les nouvelles opérations
 */
public class Operation_nouvelle_controller implements SuperController{

	private TextArea ta5;
	private Operation operation;
		
	private List<CustomComboBox<String>> list_choiceboxes;
	private List<Map<String, VBox>> list_resumes;
		
	@Override
	public void unfreeze(){}
	
	
	@Override
	public void reinit(){
		
		operation = new Operation();
		
		getList_choiceboxes().get(0).getSelectionModel().select(Messages.getLastExpediteur());
		getList_choiceboxes().get(1).getSelectionModel().select(null);
		getList_choiceboxes().get(2).getSelectionModel().select(Messages.getLastDestinataire());
		getList_choiceboxes().get(3).getSelectionModel().select(Messages.getLastComplement());
		
		ta5.setText(null);
		
		getList_choiceboxes().get(0).requestFocus();
		
		System.out.println("reinit()");
	}
	
	/**
	 * Réinitialise les champs du formulaire avec une différentiation destinataire valides/invalides.
	 * @param s le nom de matériel selectionné et qui va fournir les tags des destinataires invalides. 
	 */
	public void reinit(String s){
		
        operation = new Operation();
		
		getList_choiceboxes().get(0).getSelectionModel().select(Messages.getLastExpediteur());
		getList_choiceboxes().get(1).getSelectionModel().select(s);
		// ici nouvelle liste des destinataires
		ArrayList<String> wrongTags = AutoCompletion.wrongTag("destinataire", s);
		System.out.println(wrongTags);
		
		// getList_choiceboxes().get(2).setDisabledItems(<liste avec les tags>); // ajouter un popup d'aide
		for (String x : wrongTags){
			System.out.println("x : " + x);
			getList_choiceboxes().get(2).setDisabledItems(x);
		}
		//getList_choiceboxes().get(2).setDisabledItems(wrongTags.toArray(new String [0]));
		
		
		getList_choiceboxes().get(2).getSelectionModel().select(Messages.getLastDestinataire());
		getList_choiceboxes().get(3).getSelectionModel().select(Messages.getLastComplement());
		
		ta5.setText(null);

		System.out.println(String.format("reinit(%s)", s));
	}

	/**
	 * Initialisation des éléments et de leurs écouteurs
	 */
    public VBox init(VBox form){
    	
    	System.out.println("operation_nouvelle.init()");
		
    	Messages.setOnc(this);
    	
		form.getChildren().clear();
		
		HBox h1 = new HBox();
		h1.setSpacing(15);
		h1.setMaxWidth(Double.MAX_VALUE);
		
		Map<String, String> choiceboxes = new LinkedHashMap<String, String>();
		setList_choiceboxes(new LinkedList<CustomComboBox<String>>());
		
		list_resumes = new ArrayList<>();
		
		
		choiceboxes.put("expediteur", "Expéditeur : ");
		choiceboxes.put("materiel", "Matériel : ");
		choiceboxes.put("destinataire", "Destinataire : ");
		choiceboxes.put("complement", "Complément : ");
		
		for (String s : choiceboxes.keySet()){
			
			ObservableList<String> liste = FXCollections.observableArrayList();
			
			MongoCursor<Commun> enregistrable_cursor = MongoAccess.request(s).as(Commun.class);
			
			enregistrable_cursor.forEach(a -> liste.add(a.getNom()));

			VBox v1 = new VBox();
			VBox v2 = new VBox();
			v1.setMaxWidth(Double.MAX_VALUE);
			v1.setAlignment(Pos.CENTER);
			v1.setSpacing(5);
			
			Label l1 = new Label(choiceboxes.get(s));
			l1.maxWidth(75);
			l1.setMaxSize(120.0, Control.USE_PREF_SIZE);
			
			CustomComboBox<String> cb1 = new CustomComboBox<>();
			cb1.prefWidth(400);
			cb1.setMaxSize(400.0, Control.USE_PREF_SIZE);
			cb1.setEditable(true);
			
			cb1.setItems(liste);	
			//cb1.setDisabledItems(liste.get(0));
			//cb1.
			
			v1.getChildren().addAll(l1, cb1, v2);
			h1.getChildren().add(v1);	
			
			Map<String, VBox> hm = new HashMap<>();
			hm.put(s, v2);
			list_resumes.add(hm);
			
			ChangeListener<String> auto_completion_listener = new ChangeListener<String>(){

	 			@Override
	 			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	 				
	 				cb1.editorProperty().get().textProperty().unbind();
	 				
	 				cb1.getEditor().setText(newValue.toUpperCase());
	 				ObservableList<String> liste_autocompletion = FXCollections.observableArrayList();
					liste_autocompletion = AutoCompletion.autocomplete(s, "nom", newValue.toUpperCase());
	 				
	 				cb1.setItems(liste_autocompletion);
	 				cb1.hide();
	 				cb1.setVisibleRowCount(liste_autocompletion.size());
	 				cb1.show();
	 			}			
	 		};
	 		
	 		cb1.getEditor().setOnKeyPressed(a-> {
	        	cb1.editorProperty().get().textProperty().addListener(auto_completion_listener);
	        	// System.out.println("auto_completion_listener on");
			});
	        cb1.getEditor().setOnKeyReleased(a-> {
	        	cb1.editorProperty().get().textProperty().removeListener(auto_completion_listener);
	        	// System.out.println("auto_completion_listener off");
			}); 		

	        cb1.setOnAction(a -> mise_a_jour(s, cb1.getValue()));
//			
//			HBox.setHgrow(cb1, Priority.ALWAYS);
//			HBox.setHgrow(l1, Priority.ALWAYS);
//			HBox.setHgrow(v1, Priority.ALWAYS);
			
			getList_choiceboxes().add(cb1);
		}		
		
		form.getChildren().add(h1);	
		
		HBox h5 = new HBox();
		h5.setSpacing(20);
		h5.setPadding(new Insets(30, 0, 0, 0));
		Label l5 = new Label("Commentaire : ");
		ta5 = new TextArea();
		h5.getChildren().add(l5);
		h5.getChildren().add(ta5);

		form.getChildren().add(h5);	
		
		operation = new Operation();

		getList_choiceboxes().get(0).requestFocus();
		
		return form;
	}
    
    public void mise_a_jour(String element, String selection){
    	
    	switch (element){
    	
    	///case "expediteur" : Expediteur e = MongoAccess.request(s, "nom", selection).
    	
    	}
    	
		
	}

    @Override
	public Enregistrable getEnregistrable() {
		return operation;
	}

	/**
	 * Retourne la liste des 'choiceBox'
	 * @return la liste des 'choiceBox'
	 */
	public List<CustomComboBox<String>> getList_choiceboxes() {
		return list_choiceboxes;
	}

    /**
     * Modifie la liste des 'choiceBox'
     * @param list_choiceboxes la liste des 'choiceBox'
     */
	public void setList_choiceboxes(List<CustomComboBox<String>> list_choiceboxes) {
		this.list_choiceboxes = list_choiceboxes;
	}



}

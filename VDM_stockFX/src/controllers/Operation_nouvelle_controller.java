package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import models.Commun;
import models.Complement;
import models.Destinataire;
import models.Enregistrable;
import models.Expediteur;
import models.Materiel;
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
	private Map<String, VBox> resumes;
	
		
	@Override
	public void unfreeze(){}
	
	
	@Override
	public void reinit(){
		
		System.out.println("reinit()");
		
		operation = new Operation();
		
		if (! "".equals(Messages.getLastExpediteur())){
			getList_choiceboxes().get(0).getSelectionModel().select(Messages.getLastExpediteur());
		}
		
		getList_choiceboxes().get(1).getSelectionModel().select(null);
		//getList_choiceboxes().get(2).getSelectionModel().select(Messages.getLastDestinataire());
		
		
		if(! "".equals(Messages.getLastComplement())){
			getList_choiceboxes().get(3).getSelectionModel().select(Messages.getLastComplement());
		}
		
		
		ta5.setText(null);
		
		getList_choiceboxes().get(0).requestFocus();
		
		
	}
	
	/**
	 * Réinitialise les champs du formulaire avec une différentiation destinataire valides/invalides.
	 * @param s le nom de matériel selectionné et qui va fournir les tags des destinataires invalides. 
	 */
	public void reinit(String s){
		
		System.out.println(String.format("reinit(%s)", s));
		
        operation = new Operation();
		
        if (! "".equals(Messages.getLastExpediteur())){
        	System.out.println("select expediteur *" + Messages.getLastExpediteur() + "*");
        	getList_choiceboxes().get(0).getSelectionModel().select(Messages.getLastExpediteur());
        }
        System.out.println("select materiel");
		getList_choiceboxes().get(1).getSelectionModel().select(s);

		
		// ici nouvelle liste des destinataires
		ArrayList<String> wrongTags = AutoCompletion.wrongTag("destinataire", s);
		System.out.println("Wrongtags à traiter : " + wrongTags);
		
		getList_choiceboxes().get(2).getSelectionModel().select(null);
		getList_choiceboxes().get(2).setItems(AutoCompletion.autocomplete("destinataire", "nom", ""));
		getList_choiceboxes().get(2).setDisabledItems(wrongTags);
		
    	VBox v = Messages.getResumes().get("destinataire");
        v.getChildren().clear();		
		
		if(! "".equals(Messages.getLastComplement())){
			System.out.println("select complement");
			getList_choiceboxes().get(3).getSelectionModel().select(Messages.getLastComplement());
		}

		ta5.setText(null);
		
		mise_a_jour("materiel", getList_choiceboxes().get(1).getValue());
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
		
		resumes = new HashMap<>();
		
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
			v1.setAlignment(Pos.TOP_CENTER);
			v1.setSpacing(5);
			
			Label l1 = new Label(choiceboxes.get(s));
			l1.maxWidth(75);
			l1.setMaxSize(120.0, Control.USE_PREF_SIZE);
			
			CustomComboBox<String> cb1 = new CustomComboBox<>();
			cb1.prefWidth(400);
			cb1.setMaxSize(400.0, Control.USE_PREF_SIZE);
			cb1.setEditable(true);
			
			cb1.setItems(liste);	
			
			v1.getChildren().addAll(l1, cb1, v2);
			h1.getChildren().add(v1);	
	
			resumes.put(s, v2);
			
			ChangeListener<String> auto_completion_listener = new ChangeListener<String>(){

	 			@Override
	 			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	 				
	 				cb1.editorProperty().get().textProperty().unbind();
	 				
	 				cb1.getEditor().setText(newValue.toUpperCase());
	 				ObservableList<String> liste_autocompletion = FXCollections.observableArrayList();
					liste_autocompletion = AutoCompletion.autocomplete(s, "nom", newValue.toUpperCase());
	 				
	 				cb1.setItems(liste_autocompletion);
	 				cb1.hide();
	 				cb1.setVisibleRowCount(10);
	 				cb1.show();
	 			}			
	 		};
	 		
	 		cb1.getEditor().setOnKeyPressed(a-> {
	 			System.out.println("[code] : " + a.getCode());
				if (a.getCode().equals(KeyCode.BACK_SPACE)){
					cb1.getEditor().setText("");
					cb1.getItems().clear();
					reinit();
				}
	        	cb1.editorProperty().get().textProperty().addListener(auto_completion_listener);
	        	cb1.editorProperty().get().textProperty().addListener(auto_completion_listener);
	        	// System.out.println("auto_completion_listener on");
			});
	        cb1.getEditor().setOnKeyReleased(a-> {
	        	cb1.editorProperty().get().textProperty().removeListener(auto_completion_listener);
	        	// System.out.println("auto_completion_listener off");
			}); 		

	        cb1.setOnAction(a -> {
	        	if (cb1.getValue() != null){
	        		mise_a_jour(s, cb1.getValue());
		        	if (s.equals("materiel")){
		        		reinit(cb1.getValue());
		        	}
	        	}
	        	
	        });

//			
//			HBox.setHgrow(cb1, Priority.ALWAYS);
//			HBox.setHgrow(l1, Priority.ALWAYS);
//			HBox.setHgrow(v1, Priority.ALWAYS);
			
			getList_choiceboxes().add(cb1);
		}
		
		getList_choiceboxes().get(1).setOnAction(a -> {
			reinit(getList_choiceboxes().get(1).getValue());
		});
		
		Messages.setResumes(resumes);
		
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
    	
    	VBox v;
    	Label l1, l2, l3, l4, l5, l6, l7;
    	
    	switch (element){
    	
    	case "expediteur" : Expediteur e = MongoAccess.request(element, "nom", selection).as(Expediteur.class);
    	                    v = Messages.getResumes().get(element);
    	                    v.getChildren().clear();
    	                    l1 = new Label(String.format("Commentaire : %s", e.getCommentaire() != null ? e.getCommentaire() : ""));
					        v.getChildren().addAll(l1);
    	                    break;
    	case "materiel" :   Materiel m = MongoAccess.request(element, "nom", selection).as(Materiel.class);
					        v = Messages.getResumes().get(element);
					        v.getChildren().clear();
					        l1 = new Label(String.format("Marque : %s", m.getMarque() != null ? m.getMarque() : ""));
					        l2 = new Label(String.format("Modèle : %s", m.getModele() != null ? m.getModele() : ""));
					        l3 = new Label(String.format("Capacité : %s", m.getCapacite() != null ? m.getCapacite() : ""));
					        l4 = new Label(String.format("TAGS : %s", m.getTags().stream().collect(Collectors.joining(", "))));
					        l4.setStyle("-fx-font-weight: bold;");
					        //l4.setWrapText(true);
					        l5 = new Label(String.format("Commentaire : %s", m.getCommentaire()!= null ? m.getCommentaire() : ""));
					        v.getChildren().addAll(l1, l2, l3, l4, l5);
					        break;
    	case "destinataire" :  Destinataire d = MongoAccess.request(element, "nom", selection).as(Destinataire.class);
					        v = Messages.getResumes().get(element);
					        v.getChildren().clear();
					        l1 = new Label(String.format("Nom : %s", d.getPatronyme() != null ? d.getPatronyme() : ""));
					        l2 = new Label(String.format("Prénom : %s", d.getPrenom() != null ? d.getPrenom() : ""));
					        l3 = new Label(String.format("Fonction : %s", d.getFonction() != null ? d.getFonction() : ""));
					        l4 = new Label(String.format("Société : %s", d.getSociete() != null ? d.getSociete() : ""));
					        l5 = new Label(String.format("MAUVAIS TAGS : %s", d.getTags().stream().collect(Collectors.joining(", "))));
					        l5.setStyle("-fx-font-weight: bold;");
					        //l5.setWrapText(true);
					        l6 = new Label(String.format("Commentaire : %s", d.getCommentaire() != null ? d.getCommentaire() : ""));
					        v.getChildren().addAll(l1, l2, l3, l4, l5, l6);
					        break;
    	case "complement" : Complement c = MongoAccess.request(element, "nom", selection).as(Complement.class);
					        v = Messages.getResumes().get(element);
					        v.getChildren().clear();
					        l1 = new Label(String.format("Commentaire : %s", c.getCommentaire() != null ? c.getCommentaire() : ""));
					        v.getChildren().addAll(l1);
					        break;
    	                    
    	
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

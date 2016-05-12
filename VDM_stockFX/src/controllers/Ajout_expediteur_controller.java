package controllers;

import java.util.LinkedHashMap;
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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import models.Destinataire;
import models.Enregistrable;
import models.Expediteur;
import utils.AutoCompletion;
import utils.MongoAccess;

/**
 * Controle la zone graphique du formulaire d'ajout d'un expéditeur.
 */
public class Ajout_expediteur_controller implements SuperController{
	 
	private ObservableList<String> liste_autocompletion;
	private Expediteur expediteur;
	private TextArea ta5;
	private ComboBox<String> cb1;
	
	@Override
	public void unfreeze(){
		
		editable(true);
		
	}
	
	/**
	 * Bloque l'accès en modification aux champs du formulaire
	 */
    public void freeze(){
		
		editable(false);
		
	}

    /**
     * Détail du code appelé par {@link freeze} {@link unfreeze} 
     * @param oui bouléen retourné par {@link freeze} / {@link unfreeze} 
     */
	public void editable(boolean oui){

		ta5.setEditable(oui);
		
		Ajout_enregistrer_controller.getEnregistrer().setVisible(oui);
		Ajout_enregistrer_controller.getEditer().setVisible(!oui);	
	}
	
	@Override
	public void reinit(){
		expediteur = new Expediteur();
		cb1.setItems(null);
		cb1.getSelectionModel().select(null);
		cb1.getEditor().setText(null);
		
		ta5.setText(null);
		cb1.requestFocus();
		
		cb1.hide();
	}
	
	/**
	 * Initialisation du Formulaire et des écouteurs sur les champs.
	 */
     public VBox init(VBox form){
    	 
    	 liste_autocompletion = FXCollections.observableArrayList();
		
		form.getChildren().clear();
		form.setPadding(new Insets(20, 0, 0, 0));
	
		HBox h1 = new HBox();
		h1.setMaxWidth(Double.MAX_VALUE);
		h1.setAlignment(Pos.CENTER_LEFT);
		h1.setSpacing(5);
					
		Label l1 = new Label("Nom : ");
		l1.maxWidth(75);
		l1.setMaxSize(75.0, Control.USE_PREF_SIZE);
		HBox.setHgrow(l1, Priority.ALWAYS);
		
		cb1 = new ComboBox<String>();
		cb1.setEditable(true);
		cb1.prefWidth(400);
		cb1.setVisibleRowCount(10);
		cb1.setMaxSize(400.0, Control.USE_PREF_SIZE);
		HBox.setHgrow(cb1, Priority.ALWAYS);
		
		ChangeListener<String> auto_completion_listener = new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				cb1.getEditor().setText(newValue.toUpperCase());
				liste_autocompletion = AutoCompletion.autocomplete("expediteur", "nom", newValue.toUpperCase());
				
				cb1.setItems(liste_autocompletion);
				cb1.hide();
				//cb1.setVisibleRowCount(liste_autocompletion.size());
				cb1.show();
				
				//cb1.editorProperty().get().textProperty().removeListener(this);
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
		});
        cb1.getEditor().setOnKeyReleased(a-> {
        	cb1.editorProperty().get().textProperty().removeListener(auto_completion_listener);
		});
        
        cb1.setOnAction(a -> mise_a_jour());

		h1.getChildren().add(l1);
		h1.getChildren().add(cb1);
		form.getChildren().add(h1);	
		
		HBox h5 = new HBox();
		h5.setSpacing(20);
		h5.setPadding(new Insets(30, 0, 0, 0));
		Label l5 = new Label("Commentaire : ");
		ta5 = new TextArea();
		h5.getChildren().add(l5);
		h5.getChildren().add(ta5);

		form.getChildren().add(h5);	
		
		unfreeze();
		
		cb1.requestFocus();
		
		return form;
	 }
     
     /**
      * Mise à jour de l'affichage du formulaire
      */
     public void mise_a_jour(){
			expediteur = MongoAccess.request("expediteur", "nom", cb1.getSelectionModel().getSelectedItem()).as(Expediteur.class);

			if (expediteur == null){
				expediteur = new Expediteur();
				unfreeze();
			}
			else {
				freeze();
			}
			
			ta5.setText(expediteur.getCommentaire());
	  	}

     /**
      * Retourne la liste correspondant aux propositions de l'autocomplétion
      * @return la liste correspondant aux propositions de l'autocomplétion
      */
	public ObservableList<String> getListe_autocompletion() {
		return liste_autocompletion;
	}

	/**
	 * Modifie la liste correspondant aux propositions de l'autocomplétion
	 * @param liste_autocompletion la liste correspondant aux propositions de l'autocomplétion
	 */
	public void setListe_autocompletion(ObservableList<String> liste_autocompletion) {
		this.liste_autocompletion = liste_autocompletion;
	}

	/**
	 * Retourne l'instance de l'expéditeur
	 * @return l'instance de l'expéditeur
	 */
	public Expediteur getExpediteur() {
		return expediteur;
	}

	/**
	 * Modifie l'instance de l'expéditeur
	 * @param expediteur l'instance de l'expéditeur
	 */
	public void setExpediteur(Expediteur expediteur) {
		this.expediteur = expediteur;
	}

	@Override
	public Enregistrable getEnregistrable() {
		return expediteur;
	}
     
     

}

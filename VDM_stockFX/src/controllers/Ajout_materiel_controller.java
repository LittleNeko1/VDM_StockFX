package controllers;

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
import javafx.scene.control.TextField;

import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import models.Destinataire;
import models.Enregistrable;
import models.Materiel;
import utils.AutoCompletion;
import utils.Messages;
import utils.MongoAccess;

public class Ajout_materiel_controller  implements SuperController{
	
	private ObservableList<String> liste_autocompletion;
	private Materiel materiel;
	private List<TextField> textFields;
	private TextArea ta5;
	private ComboBox<String> cb1;
	
	@Override
	public void unfreeze(){
		
		editable(true);
		
	}
	
    public void freeze(){
		
		editable(false);
		
	}
	
	
	
	public void editable(boolean oui){
		
		for (TextField tf : textFields){
			tf.setEditable(oui);
		}
		
		ta5.setEditable(oui);
		
		Ajout_enregistrer_controller.getEnregistrer().setVisible(oui);
		Ajout_enregistrer_controller.getEditer().setVisible(!oui);
		
		
	}
	
	@Override
	public void reinit(){
		materiel = new Materiel();
		cb1.setItems(null);
		cb1.getSelectionModel().select(null);
		cb1.getEditor().setText(null);
		
		textFields.get(0).setText(null);
        textFields.get(1).setText(null);
        textFields.get(2).setText(null);
		ta5.setText(null);
		
		cb1.requestFocus();
		
		cb1.hide();
	}
	
	public void reinit(String s){
		
		materiel = new Materiel();
		cb1.setItems(null);
		cb1.getSelectionModel().select(s);
		cb1.getEditor().setText(s);
		
		textFields.get(0).setText(null);
        textFields.get(1).setText(null);
        textFields.get(2).setText(null);
		ta5.setText(null);
		
		textFields.get(0).requestFocus();
		
		cb1.hide();
	}
	
	public VBox init(VBox form){
		
		Messages.setAmc(this);
		
        liste_autocompletion = FXCollections.observableArrayList();
		
		form.getChildren().clear();
		form.setPadding(new Insets(20, 0, 0, 0));
		
		HBox h1 = new HBox();
		h1.setMaxWidth(Double.MAX_VALUE);
		h1.setAlignment(Pos.CENTER_LEFT);
		h1.setSpacing(5);
					
		Label l1 = new Label("nom : ");
		l1.maxWidth(75);
		l1.setMaxSize(75.0, Control.USE_PREF_SIZE);
		HBox.setHgrow(l1, Priority.ALWAYS);
				
		cb1 = new ComboBox<String>();
		cb1.setEditable(true);
		cb1.prefWidth(400);
		cb1.setMaxSize(400.0, Control.USE_PREF_SIZE);
		HBox.setHgrow(cb1, Priority.ALWAYS);
		
		ChangeListener<String> auto_completion_listener = new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				cb1.getEditor().setText(newValue.toUpperCase());
				liste_autocompletion = AutoCompletion.autocomplete("materiel", "nom", newValue.toUpperCase());
				
				cb1.setItems(liste_autocompletion);
				cb1.hide();
				cb1.setVisibleRowCount(liste_autocompletion.size());
				cb1.show();
				
				//cb1.editorProperty().get().textProperty().removeListener(this);
			}			
		};
		
		cb1.getEditor().setOnKeyPressed(a-> {
        	cb1.editorProperty().get().textProperty().addListener(auto_completion_listener);
		});
        cb1.getEditor().setOnKeyReleased(a-> {
        	cb1.editorProperty().get().textProperty().removeListener(auto_completion_listener);
		});
        
        cb1.setOnAction(a -> mise_a_jour());
        h1.getChildren().add(l1);
		h1.getChildren().add(cb1);
		form.getChildren().add(h1);		

		Map<String, String> champs_textField = new LinkedHashMap<String, String>();
		champs_textField.put("marque", "Marque : ");
		champs_textField.put("modele", "Modèle : ");
		champs_textField.put("capacite", "Capacité : ");
		
		textFields = new LinkedList<>();
		
		for (String s : champs_textField.keySet()){
			
			HBox h2 = new HBox();
			h2.setMaxWidth(Double.MAX_VALUE);
			h2.setAlignment(Pos.CENTER_LEFT);
			h2.setSpacing(5);
			
			
			Label l2 = new Label(champs_textField.get(s));
			l2.maxWidth(75);
			l2.setMaxSize(75.0, Control.USE_PREF_SIZE);
			HBox.setHgrow(l2, Priority.ALWAYS);
			
			TextField tf1 = new TextField();
			tf1.prefWidth(400);
			tf1.setMaxSize(400.0, Control.USE_PREF_SIZE);
			HBox.setHgrow(tf1, Priority.ALWAYS);
			
			h2.getChildren().add(l2);
			h2.getChildren().add(tf1);
			textFields.add(tf1);
			form.getChildren().add(h2);	
		}
		
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
	
	public void mise_a_jour(){
		materiel = MongoAccess.request("materiel", "nom", cb1.getSelectionModel().getSelectedItem()).as(Materiel.class);

		if (materiel == null){
			materiel = new Materiel();
			unfreeze();
		}
		else {
			freeze();
		}
		
        textFields.get(0).setText(materiel.getMarque());
        textFields.get(1).setText(materiel.getModele());
        textFields.get(2).setText(materiel.getCapacite());

		ta5.setText(materiel.getCommentaire());
  	}

	public ObservableList<String> getListe_autocompletion() {
		return liste_autocompletion;
	}

	public void setListe_autocompletion(ObservableList<String> liste_autocompletion) {
		this.liste_autocompletion = liste_autocompletion;
	}

	public Materiel getMateriel() {
		return materiel;
	}

	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	public List<TextField> getTextFields() {
		return textFields;
	}

	public void setTextFields(List<TextField> textFields) {
		this.textFields = textFields;
	}

	public TextArea getTa5() {
		return ta5;
	}

	public void setTa5(TextArea ta5) {
		this.ta5 = ta5;
	}

	public ComboBox<String> getCb1() {
		return cb1;
	}

	public void setCb1(ComboBox<String> cb1) {
		this.cb1 = cb1;
	}

	@Override
	public Enregistrable getEnregistrable() {
		return materiel;
	}
	
	
	
}

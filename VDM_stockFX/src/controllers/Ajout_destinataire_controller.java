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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import models.Destinataire;
import models.Enregistrable;
import utils.AutoCompletion;
import utils.MongoAccess;

public class Ajout_destinataire_controller implements SuperController{
	
	private ObservableList<String> liste_autocompletion;
	private Destinataire destinataire;
	private List<TextField> textFields;
	private TextArea ta5;
	private ComboBox<String> cb1;
	
	private static TextField saisie; 
	
	ChangeListener<String> auto_completion_listener;
	
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
		
		textFields.get(0).textProperty().removeListener(auto_completion_listener);
		cb1.editorProperty().get().textProperty().unbind();
		textFields.get(0).textProperty().unbind();
		textFields.get(3).textProperty().unbind();
		
		destinataire = new Destinataire();
		cb1.setItems(null);
		cb1.getSelectionModel().select(null);
		cb1.getEditor().setText(null);
		
		textFields.get(0).setText(null);
		textFields.get(1).setText(null);
		textFields.get(2).setText(null);
		textFields.get(3).setText(null);
		ta5.setText(null);
		
		unfreeze();
		
		cb1.requestFocus();
		
		cb1.hide();
		
	}
	
	
     public VBox init(VBox form){
		
    	 liste_autocompletion = FXCollections.observableArrayList();
 		
 		form.getChildren().clear();
 		form.setPadding(new Insets(20, 0, 0, 0));
 		
 		HBox h1 = new HBox();
 		h1.setMaxWidth(Double.MAX_VALUE);
 		h1.setAlignment(Pos.CENTER_LEFT);
 		h1.setSpacing(5);
 					
 		Label l1 = new Label("nom\naffiché : ");
 		l1.maxWidth(75);
 		l1.setMaxSize(75.0, Control.USE_PREF_SIZE);
 		HBox.setHgrow(l1, Priority.ALWAYS);
 				
 		cb1 = new ComboBox<String>();
 		cb1.setEditable(true);
 		cb1.prefWidth(400);
 		cb1.setMaxSize(400.0, Control.USE_PREF_SIZE);
 		HBox.setHgrow(cb1, Priority.ALWAYS);
	
 		auto_completion_listener = new ChangeListener<String>(){

 			@Override
 			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
 				
 				cb1.editorProperty().get().textProperty().unbind();
 				
 				cb1.getEditor().setText(newValue.toUpperCase());
				liste_autocompletion = AutoCompletion.autocomplete("destinataire", "nom", newValue.toUpperCase());
 				
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
		
 		champs_textField.put("nom", "Nom : ");
		champs_textField.put("prenom", "Prénom : ");
		champs_textField.put("fonction", "Fonction : ");
		champs_textField.put("societe", "Société : ");
		
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
		
		
		textFields.get(0).setOnKeyPressed(a -> {
				cb1.editorProperty().get().textProperty().unbind();
				cb1.editorProperty().get().textProperty().bind(textFields.get(0).textProperty());
				textFields.get(0).textProperty().addListener(auto_completion_listener);
				//saisie = textFields.get(0);
				
			});
		
		
		textFields.get(3).setOnKeyPressed(a -> {
			cb1.editorProperty().get().textProperty().unbind();
			if (textFields.get(0).getText() == null || "".equals(textFields.get(0).getText())){
				cb1.editorProperty().get().textProperty().bind(textFields.get(3).textProperty());
				textFields.get(3).textProperty().addListener(auto_completion_listener);
				//saisie = textFields.get(3);
			}

		});

		textFields.get(0).setOnKeyReleased(a -> {
			
			textFields.get(0).textProperty().removeListener(auto_completion_listener);
			cb1.editorProperty().get().textProperty().unbind();

			if (textFields.get(0).getText() == null || "".equals(textFields.get(0).getText())){
				saisie = null;
			}
		});
		
		textFields.get(3).setOnKeyReleased(a -> {
			
			textFields.get(0).textProperty().removeListener(auto_completion_listener);
			cb1.editorProperty().get().textProperty().unbind();
			
			if (textFields.get(3).getText() == null || "".equals(textFields.get(3).getText())){
				saisie = null;
			}

		});

		HBox h5 = new HBox();
		h5.setSpacing(20);
		h5.setPadding(new Insets(30, 0, 0, 0));
		Label l5 = new Label("Commentaire : ");
		ta5 = new TextArea();
		h5.getChildren().add(l5);
		h5.getChildren().add(ta5);

		form.getChildren().add(h5);	
		
		mise_a_jour_autocompletion();
		
		unfreeze();
		
		cb1.requestFocus();
		
		return form;
	}

	@Override
	public Enregistrable getEnregistrable() {
		
		System.out.println("destinataire retourné : " + destinataire);

		return destinataire;
	}
	
    public void mise_a_jour(){
    	
    	cb1.editorProperty().get().textProperty().unbind();
    	
    	if (cb1.getSelectionModel().getSelectedIndex() >= 0){
    		
    		destinataire = MongoAccess.request("destinataire", "nom", cb1.getSelectionModel().getSelectedItem()).as(Destinataire.class);

    		textFields.get(0).setText(destinataire.getPatronyme());
    		textFields.get(1).setText(destinataire.getPrenom());
    		textFields.get(2).setText(destinataire.getFonction());
    		textFields.get(3).setText(destinataire.getSociete());

    		ta5.setText(destinataire.getCommentaire());
    		
    		freeze();
    	}
  	}
	
	public void mise_a_jour_autocompletion(){
		
		cb1.editorProperty().get().textProperty().unbind();
		
		if (cb1.getSelectionModel().getSelectedIndex() >= 0){
			
			destinataire = MongoAccess.request("destinataire", "nom", cb1.getSelectionModel().getSelectedItem()).as(Destinataire.class);
		}
		
		if (destinataire == null){
			destinataire = new Destinataire();	
			unfreeze();
		}
		else {
			freeze();
		}
		
		if (saisie == null){
			textFields.get(0).setText(destinataire.getPatronyme());
			textFields.get(1).setText(destinataire.getPrenom());
			textFields.get(2).setText(destinataire.getFonction());
			textFields.get(3).setText(destinataire.getSociete());
		}
		
		else if(! saisie.equals(textFields.get(0))){
			textFields.get(0).setText(destinataire.getPatronyme());
		}
		else if(! saisie.equals(textFields.get(1))){
			textFields.get(1).setText(destinataire.getPrenom());
		}
		else if(! saisie.equals(textFields.get(2))){
			textFields.get(2).setText(destinataire.getFonction());
		}
		else if(! saisie.equals(textFields.get(3))){
			textFields.get(3).setText(destinataire.getSociete());
		}

		ta5.setText(destinataire.getCommentaire());
		
  	}

}

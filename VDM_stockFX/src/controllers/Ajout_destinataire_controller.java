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
import utils.MongoAccess;

public class Ajout_destinataire_controller implements SuperController{
	
	private ObservableList<String> liste_autocompletion;
	private Destinataire destinataire;
	private List<TextField> textFields;
	private TextArea ta5;
	private ComboBox<String> cb1;
	
	
     public VBox init(VBox form){
		
    	 liste_autocompletion = FXCollections.observableArrayList();
 		
 		form.getChildren().clear();
 		
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
 		
 		ChangeListener<String> auto_completion_listener = new ChangeListener<String>(){

 			@Override
 			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
 				
 				liste_autocompletion.clear();
 				
 				MongoCursor<Destinataire> destinataire_cursor = MongoAccess.request("destinataire", "nom", newValue, true).as(Destinataire.class);
 				
 				while(destinataire_cursor.hasNext()){
 					
 					Destinataire destinataire = destinataire_cursor.next();
 					
 					liste_autocompletion.add(destinataire.getNom());
 				}
 				
 				cb1.setItems(liste_autocompletion);
 				cb1.hide();
 				cb1.setVisibleRowCount(liste_autocompletion.size());
 				cb1.show();
 				mise_a_jour();
 			}			
 		};
 		
 		cb1.setOnKeyPressed(a-> {
 			cb1.editorProperty().get().textProperty().addListener(auto_completion_listener);
 		});
         cb1.setOnKeyReleased(a-> {
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
			
			tf1.setOnMouseEntered(a -> {
				if (s.equals("nom")){
					cb1.editorProperty().get().textProperty().bind(textFields.get(0).textProperty());
					
				}
				else if (s.equals("societe") && textFields.get(0).getText().equals("")){
					cb1.editorProperty().get().textProperty().bind(textFields.get(3).textProperty());

				}
				
				
			});
			tf1.setOnMouseExited(a -> {
				if (s.equals("nom") && textFields.get(0).getText().equals("") && !textFields.get(3).getText().equals("")){
					cb1.editorProperty().get().textProperty().unbind();
					cb1.editorProperty().get().textProperty().bind(textFields.get(3).textProperty());
				}
				else if (s.equals("nom") && textFields.get(0).getText().equals("")){
					cb1.editorProperty().get().textProperty().unbind();
				}
				else if (s.equals("societe") && textFields.get(3).getText().equals("")){
					cb1.editorProperty().get().textProperty().unbind();
				}
				mise_a_jour();

			});
			
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
		
		return form;
	}

	@Override
	public Enregistrable getEnregistrable() {
		
		System.out.println("destinataire retourné : " + destinataire);

		return destinataire;
	}
	
	public void mise_a_jour(){
		
		destinataire = MongoAccess.request("destinataire", "nom", cb1.getSelectionModel().getSelectedItem()).as(Destinataire.class);

		if (destinataire == null){
			destinataire = new Destinataire();				
		}
		
        textFields.get(0).setText(destinataire.getPrenom());
        textFields.get(1).setText(destinataire.getFonction());
        textFields.get(2).setText(destinataire.getSociete());

		ta5.setText(destinataire.getCommentaire());
		
		System.out.println("destinataire mis a jour : " + destinataire);
  	}

}

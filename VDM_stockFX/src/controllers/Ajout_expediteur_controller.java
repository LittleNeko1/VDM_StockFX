package controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.jongo.MongoCursor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import models.Ajout_bloc;
import models.Expediteur;
import utils.MongoAccess;

public class Ajout_expediteur_controller implements Ajout_bloc{
	 
	private ObservableList<String> liste_autocompletion;
	private Expediteur expediteur;
	private TextArea ta5;
	private ComboBox<String> tf1;
	
     public VBox init(VBox form){
    	 
    	 liste_autocompletion = FXCollections.observableArrayList();
		
		form.getChildren().clear();
		
		Map<String, String> champs_textField = new LinkedHashMap<String, String>();
		
		champs_textField.put("nom", "Nom : ");
		
		for (String s : champs_textField.keySet()){
			
			HBox h1 = new HBox();
			h1.setMaxWidth(Double.MAX_VALUE);
			h1.setAlignment(Pos.CENTER_LEFT);
			h1.setSpacing(5);
			
			
			Label l1 = new Label(champs_textField.get(s));
			l1.maxWidth(75);
			l1.setMaxSize(75.0, Control.USE_PREF_SIZE);
			HBox.setHgrow(l1, Priority.ALWAYS);
			
			tf1 = new ComboBox<String>();
			tf1.setEditable(true);
			tf1.prefWidth(400);
			tf1.setMaxSize(400.0, Control.USE_PREF_SIZE);
			HBox.setHgrow(tf1, Priority.ALWAYS);
			
			ChangeListener<String> auto_completion_listener = new ChangeListener<String>(){

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					
					liste_autocompletion.clear();
					
					MongoCursor<Expediteur> expediteur_cursor = MongoAccess.request("expediteur", "nom", newValue, true).as(Expediteur.class);
					
					while(expediteur_cursor.hasNext()){
						
						Expediteur expediteur = expediteur_cursor.next();
						
						System.out.println(expediteur.getNom());
						
						liste_autocompletion.add(expediteur.getNom());
					}
					
					tf1.setItems(liste_autocompletion);
					tf1.hide();
					tf1.setVisibleRowCount(liste_autocompletion.size());
					tf1.show();
					
					Expediteur.setAutoCompletion(liste_autocompletion);
					
					
					
				}
				
			};
			
			tf1.setOnKeyPressed(a-> {
				tf1.editorProperty().get().textProperty().addListener(auto_completion_listener);
			});
            tf1.setOnKeyReleased(a-> {
            	tf1.editorProperty().get().textProperty().removeListener(auto_completion_listener);
			});
            
            tf1.setOnAction(a -> mise_a_jour());

			h1.getChildren().add(l1);
			h1.getChildren().add(tf1);
			form.getChildren().add(h1);	
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
     
     public void mise_a_jour(){
     	if(liste_autocompletion.contains(tf1.getSelectionModel().getSelectedItem())){
				expediteur = MongoAccess.request("expediteur", "nom", tf1.getSelectionModel().getSelectedItem()).as(Expediteur.class);
				ta5.setText(expediteur.getCommentaire());
				Expediteur.setExpediteur(expediteur);	
			}
     	else {
     		ta5.setText("");
     		Expediteur.setExpediteur(null);
     	}
     }

	public ObservableList<String> getListe_autocompletion() {
		return liste_autocompletion;
	}

	public void setListe_autocompletion(ObservableList<String> liste_autocompletion) {
		this.liste_autocompletion = liste_autocompletion;
	}
     
     

}

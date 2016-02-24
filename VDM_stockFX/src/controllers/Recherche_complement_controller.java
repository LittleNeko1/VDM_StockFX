package controllers;

import java.util.LinkedHashMap;
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
import models.Complement;
import models.Destinataire;
import models.Enregistrable;
import models.Materiel;
import utils.MongoAccess;

public class Recherche_complement_controller implements SuperController{
	
	private ObservableList<String> liste_autocompletion;
	private Complement complement;
	private TextArea ta5;
	private ComboBox<String> cb1;
	
	@Override
	public void unfreeze(){}
	
	@Override
	public void reinit(){
		complement = new Complement();
		cb1.setItems(null);
		cb1.getSelectionModel().select(null);
		cb1.getEditor().setText(null);
		
		ta5.setText(null);
		
		cb1.hide();
	}
	
    public VBox init(VBox form){
		
        liste_autocompletion = FXCollections.observableArrayList();
		
		form.getChildren().clear();
		
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
				
				liste_autocompletion.clear();
				
				MongoCursor<Complement> complement_cursor = MongoAccess.request("complement", "nom", newValue, true).as(Complement.class);
				
				while(complement_cursor.hasNext()){
					
					Complement complement = complement_cursor.next();
					
					liste_autocompletion.add(complement.getNom());
				}
				
				cb1.setItems(liste_autocompletion);
				cb1.hide();
				cb1.setVisibleRowCount(liste_autocompletion.size());
				cb1.show();
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
	complement = MongoAccess.request("complement", "nom", cb1.getSelectionModel().getSelectedItem()).as(Complement.class);

	if (complement == null){
		complement = new Complement();				
	}

	ta5.setText(complement.getCommentaire());
	}

	@Override
	public Enregistrable getEnregistrable() {
		return complement;
	}

}

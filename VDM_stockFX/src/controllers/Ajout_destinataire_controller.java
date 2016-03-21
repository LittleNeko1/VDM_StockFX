package controllers;

import java.util.ArrayList;
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
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import models.Destinataire;
import models.Enregistrable;
import models.Tags;
import utils.AutoCompletion;
import utils.Messages;
import utils.MongoAccess;

/**
 * Controle la zone graphique du formulaire d'ajout d'un destinataire.
 */
public class Ajout_destinataire_controller implements SuperController{
	
	private ObservableList<String> liste_autocompletion;
	private Destinataire destinataire;
	private List<TextField> textFields;
	private TextArea ta5;
	private ComboBox<String> cb1;
	
	private GridPane tags_grid;
	private HBox sd_hbox;
	private Label uhs;
	private HBox uhs_hbox;
	private VBox uhs_vbox;
	private CheckBox uhs1;
	private CheckBox uhs3;
	private Label classe;
	private HBox classe_hbox;
	private VBox classe_vbox;
	private CheckBox classe8;
	private CheckBox classe10;

	
	private HBox cf_hbox;
	private VBox udma7_vbox;
	private Label udma;
	private CheckBox udma7;
	
	private Label format;
	private Label mauvais_tags;
	private CheckBox sd_cbox;
	private CheckBox cf_cbox;
	
	private static TextField saisie; 
	
	ChangeListener<String> auto_completion_listener;
	
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
		
		for (TextField tf : textFields){
			tf.setEditable(oui);
		}
		
		ta5.setEditable(oui);
		tags_grid.setDisable(!oui);
		
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
		
		// System.out.println("reinit().unfreeze()");
		unfreeze();
		
		cb1.requestFocus();
		
		cb1.hide();		
	}
	
	/**
	 * Initialisation du Formulaire et des écouteurs sur les champs.
	 */
     public VBox init(VBox form){
    	 
    	 Messages.setAdc(this);

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
 				
 				// System.out.println("auto_completion_listener.changed() : " + newValue);
 				
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
        	// System.out.println("auto_completion_listener on");
		});
        cb1.getEditor().setOnKeyReleased(a-> {
        	cb1.editorProperty().get().textProperty().removeListener(auto_completion_listener);
        	// System.out.println("auto_completion_listener off");
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
		
		format = new Label("CARTE");		
		format.setWrapText(true);
		format.setAlignment(Pos.CENTER);
		format.setTextAlignment(TextAlignment.CENTER);
		
		mauvais_tags = new Label("ATTENTION :\nRENSEIGNER LES MAUVAIS TAGS !!!");
		mauvais_tags.setStyle("-fx-font-weight: bold;");
		mauvais_tags.setWrapText(true);
		mauvais_tags.setAlignment(Pos.CENTER);
		mauvais_tags.setTextAlignment(TextAlignment.CENTER);

		sd_cbox  = new CheckBox("SD"); 
		cf_cbox  = new CheckBox("CF");	
		
		uhs_vbox = new VBox();
		uhs_vbox.setSpacing(10);
		uhs_hbox = new HBox();
		uhs_hbox.setSpacing(10);
		
		uhs = new Label("UHS",new ImageView("u3.jpg"));
		uhs1 = new CheckBox("1");
		uhs3 = new CheckBox("3");
		uhs_hbox.getChildren().addAll(uhs1, uhs3);
		uhs_vbox.getChildren().addAll(uhs, uhs_hbox);
		
		classe_vbox = new VBox();
		classe_vbox.setSpacing(10);
		classe_hbox = new HBox();
		classe_hbox.setSpacing(10);
		
		classe = new Label("CLASSE",new ImageView("c10.jpg"));
		classe8 = new CheckBox("8");
		classe10 = new CheckBox("10");
		classe_hbox.getChildren().addAll(classe8, classe10);
		classe_vbox.getChildren().addAll(classe, classe_hbox);
		
		sd_hbox = new HBox();
		sd_hbox.setSpacing(20);
		sd_hbox.getChildren().addAll(uhs_vbox, new Separator(Orientation.VERTICAL), classe_vbox);
		sd_hbox.setDisable(true);
		
		udma7_vbox = new VBox();
		udma7_vbox.setSpacing(10);
		cf_hbox = new HBox();
		cf_hbox.setAlignment(Pos.CENTER);
		
		udma = new Label("UDMA", new ImageView("udma7.jpg"));
		udma7 = new CheckBox("7");
		udma7_vbox.getChildren().addAll(udma, udma7);
		cf_hbox.getChildren().addAll(udma7_vbox);
		cf_hbox.setDisable(true);
		
	    sd_cbox.setOnAction(a -> {
	    	
	    	if(sd_cbox.isSelected()){
	    		cf_cbox.setSelected(false);
		    	udma7.setSelected(false);
		    	cf_hbox.setDisable(true);
		    	sd_hbox.setDisable(false);
		    	Tags.CF.setSelected(true);
		    	Tags.SD.setSelected(false);
	    		Tags.UDMA7.setSelected(false);
	    	}
	    	else {
	    		uhs1.setSelected(false);
		    	uhs3.setSelected(false);
		    	classe8.setSelected(false);
		    	classe10.setSelected(false);
		    	sd_hbox.setDisable(true);
		    	Tags.CF.setSelected(false);
		    	Tags.UHS1.setSelected(false);
		    	Tags.UHS3.setSelected(false);
		    	Tags.CLASSE8.setSelected(false);
		    	Tags.CLASSE10.setSelected(false);
	    	}
	    	
	    	
	    	
	    });
	    
	    cf_cbox.setOnAction(a -> {
	    	
	    	if(cf_cbox.isSelected()){
	    		sd_cbox.setSelected(false);
		    	uhs1.setSelected(false);
		    	uhs3.setSelected(false);
		    	classe8.setSelected(false);
		    	classe10.setSelected(false);
		    	sd_hbox.setDisable(true);
		    	cf_hbox.setDisable(false);
		    	Tags.SD.setSelected(true);
		    	Tags.CF.setSelected(false);
		    	Tags.UHS1.setSelected(false);
		    	Tags.UHS3.setSelected(false);
		    	Tags.CLASSE8.setSelected(false);
		    	Tags.CLASSE10.setSelected(false);
	    	}
	    	else {
	    		udma7.setSelected(false);
	    		cf_hbox.setDisable(true);
	    		Tags.SD.setSelected(false);
	    		Tags.UDMA7.setSelected(false);
	    	}
	    	
	    });
	    
	    uhs1.setOnAction(a -> {
	    	if (uhs1.isSelected()){
	    		uhs3.setSelected(false);
	    		Tags.UHS1.setSelected(true);
	    		Tags.UHS3.setSelected(false);
	    	}
	    	else {
	    		Tags.UHS1.setSelected(false);
	    	}
	    });
		
	    uhs3.setOnAction(a -> {
	    	if (uhs3.isSelected()){
	    		uhs1.setSelected(false);
	    		Tags.UHS3.setSelected(true);
	    		Tags.UHS1.setSelected(false);
	    	}
	    	else {
	    		Tags.UHS3.setSelected(false);
	    	}
	    });
	    
	    classe10.setOnAction(a -> {
	    	if (classe10.isSelected()){
	    		classe8.setSelected(false);
	    		Tags.CLASSE10.setSelected(true);
	    		Tags.CLASSE8.setSelected(false);
	    	}
	    	else {
	    		
	    		Tags.CLASSE10.setSelected(false);
	    	}
	    });
	    
	    classe8.setOnAction(a -> {
	    	if (classe8.isSelected()){
	    		classe10.setSelected(false);
	    		Tags.CLASSE8.setSelected(true);
	    		Tags.CLASSE10.setSelected(false);
	    	}
	    	else {
	    		Tags.CLASSE8.setSelected(false);
	    	}
	    });
	    
	    udma7.setOnAction(a -> {
	    	if (udma7.isSelected()){
	    		Tags.UDMA7.setSelected(true);
	    	}
	    	else {
	    		Tags.UDMA7.setSelected(false);
	    	}
	    });
	    
		tags_grid = new GridPane();
		tags_grid.setVgap(10);
		tags_grid.setHgap(20);
		//tags_grid.setStyle("-fx-background-color: #ddd");
		
		RowConstraints constraint = new RowConstraints();
		constraint.setPrefHeight(35);
		tags_grid.getRowConstraints().add(constraint);
		
		tags_grid.add(format, 0, 0);
		
		tags_grid.add(new Separator(Orientation.HORIZONTAL), 0, 1);
		tags_grid.add(sd_cbox, 0, 2);
		tags_grid.add(new Separator(Orientation.HORIZONTAL), 0, 3);
		tags_grid.add(cf_cbox, 0, 4);
		
		tags_grid.add(new Separator(Orientation.VERTICAL), 1, 0);
		tags_grid.add(new Separator(Orientation.VERTICAL), 1, 2);
		tags_grid.add(new Separator(Orientation.VERTICAL), 1, 4);
		
		tags_grid.add(mauvais_tags, 2, 0);
		
		tags_grid.add(new Separator(Orientation.HORIZONTAL), 2, 1);
		tags_grid.add(sd_hbox, 2, 2);
		tags_grid.add(new Separator(Orientation.HORIZONTAL), 2, 3);
		tags_grid.add(cf_hbox, 2, 4);
		
		tags_grid.add(new Separator(Orientation.VERTICAL), 3, 2);
		tags_grid.add(new Separator(Orientation.VERTICAL), 3, 4);
		
		form.getChildren().add(tags_grid);	

		HBox h5 = new HBox();
		h5.setSpacing(20);
		h5.setPadding(new Insets(30, 0, 0, 0));
		Label l5 = new Label("Commentaire : ");
		ta5 = new TextArea();
		h5.getChildren().add(l5);
		h5.getChildren().add(ta5);

		form.getChildren().add(h5);	
		
		//mise_a_jour_autocompletion();
		destinataire = new Destinataire();	
		// System.out.println("init().unfreeze()");
		unfreeze();
		
		cb1.requestFocus();
		
		Messages.setDestinataire_form(form);
		
		return form;
	}

	@Override
	public Enregistrable getEnregistrable() {
		
		System.out.println("destinataire retourné : " + destinataire);

		return destinataire;
	}
	
	/**
     * Mise à jour de l'affichage du formulaire
     */
    public void mise_a_jour(){
    	
    	cb1.editorProperty().get().textProperty().unbind();
    	
    	if (cb1.getSelectionModel().getSelectedIndex() >= 0){
    		
    		destinataire = MongoAccess.request("destinataire", "nom", cb1.getSelectionModel().getSelectedItem()).as(Destinataire.class);

    		textFields.get(0).setText(destinataire.getPatronyme());
    		textFields.get(1).setText(destinataire.getPrenom());
    		textFields.get(2).setText(destinataire.getFonction());
    		textFields.get(3).setText(destinataire.getSociete());
    		
    		for (String s : destinataire.getTags()){
            	
            	switch (s){
            	
            	case "CF" : sd_hbox.setDisable(false);
            	            sd_cbox.setSelected(true);
            	            break;
            	case "SD" : cf_hbox.setDisable(false);
            	            cf_cbox.setSelected(true);
                            break;
            	case "UDMA7" : udma7.setSelected(true);
            	            break;
            	case "UHS1" : uhs1.setSelected(true);
                            break;
            	case "UHS3" : uhs3.setSelected(true);
                            break;
            	case "CLASSE8" : classe8.setSelected(true);
                            break;
            	case "CLASSE10" : classe10.setSelected(true);
                            break;
            	}
            }

    		ta5.setText(destinataire.getCommentaire());
    		
    		if (destinataire == null){
    			destinataire = new Destinataire();	
    			// System.out.println("mise_a_jour().unfreeze()");
    			unfreeze();
    		}
    		else {
    			// System.out.println("mise_a_jour().freeze()");
    			freeze();
    		}
    	}
  	}
	
    /**
     * Mise à jour de l'affichage du formulaire dans le cass de l'autocomplétion à la saisie. 
     */
	public void mise_a_jour_autocompletion(){
		
		cb1.editorProperty().get().textProperty().unbind();
		
		if (cb1.getSelectionModel().getSelectedIndex() >= 0){
			
			destinataire = MongoAccess.request("destinataire", "nom", cb1.getSelectionModel().getSelectedItem()).as(Destinataire.class);
		}
		
		if (destinataire == null){
			destinataire = new Destinataire();	
			// System.out.println("mise_a_jour_autocompletion().unfreeze()");
			unfreeze();
		}
		else {
			// System.out.println("mise_a_jour_autocompletion().freeze()");
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
		
		for (String s : destinataire.getTags()){
        	
        	switch (s){
        	
        	case "CF" : sd_hbox.setDisable(false);
        	            sd_cbox.setSelected(true);
        	            break;
        	case "SD" : cf_hbox.setDisable(false);
        	            cf_cbox.setSelected(true);
                        break;
        	case "UDMA7" : udma7.setSelected(true);
        	            break;
        	case "UHS1" : uhs1.setSelected(true);
                        break;
        	case "UHS3" : uhs3.setSelected(true);
                        break;
        	case "CLASSE8" : classe8.setSelected(true);
                        break;
        	case "CLASSE10" : classe10.setSelected(true);
                        break;
        	}
        }

		ta5.setText(destinataire.getCommentaire());
		
  	}
	
	/**
	 * Retourne la liste des tags <b><u>invalides</u></b> chez le destinataire
	 * @see models.Materiel
	 * @return la liste des tags <b><u>invalides</u></b> chez le destinataire
	 */
    public ArrayList<String> getTags(){
		
		ArrayList<String> a = new ArrayList<>();
		
		for (Tags t : Tags.values()){
			
			if (t.isSelected()){
				a.add(t.name());
			}
		}
		return a;
	}
}

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
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import models.Destinataire;
import models.Enregistrable;
import models.Materiel;
import models.Tags;
import utils.AutoCompletion;
import utils.Messages;
import utils.MongoAccess;

public class Ajout_materiel_controller  implements SuperController{
	
	private ObservableList<String> liste_autocompletion;
	private Materiel materiel;
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
	private CheckBox sd_cbox;
	private CheckBox cf_cbox;
	
	
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
		tags_grid.setDisable(!oui);
		
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
		
		format = new Label("CARTE");
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
		cf_hbox.setAlignment(Pos.CENTER);;
		
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
		    	Tags.SD.setSelected(true);
		    	Tags.CF.setSelected(false);
	    		Tags.UDMA7.setSelected(false);
	    	}
	    	else {
	    		uhs1.setSelected(false);
		    	uhs3.setSelected(false);
		    	classe8.setSelected(false);
		    	classe10.setSelected(false);
		    	sd_hbox.setDisable(true);
		    	Tags.SD.setSelected(false);
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
		    	Tags.CF.setSelected(true);
		    	Tags.SD.setSelected(false);
		    	Tags.UHS1.setSelected(false);
		    	Tags.UHS3.setSelected(false);
		    	Tags.CLASSE8.setSelected(false);
		    	Tags.CLASSE10.setSelected(false);
	    	}
	    	else {
	    		udma7.setSelected(false);
	    		cf_hbox.setDisable(true);
	    		Tags.CF.setSelected(false);
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
		tags_grid.add(format, 0, 0);
		tags_grid.add(new Separator(Orientation.HORIZONTAL), 0, 1);
		tags_grid.add(sd_cbox, 0, 2);
		tags_grid.add(new Separator(Orientation.HORIZONTAL), 0, 3);
		tags_grid.add(cf_cbox, 0, 4);
		
		tags_grid.add(new Separator(Orientation.VERTICAL), 1, 0);
		tags_grid.add(new Separator(Orientation.VERTICAL), 1, 2);
		tags_grid.add(new Separator(Orientation.VERTICAL), 1, 4);
		
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
        
        for (String s : materiel.getTags()){
        	
        	switch (s){
        	
        	case "SD" : sd_hbox.setDisable(false);
        	            sd_cbox.setSelected(true);
        	            break;
        	case "CF" : cf_hbox.setDisable(false);
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

		ta5.setText(materiel.getCommentaire());
  	}
	
	public ArrayList<String> getTags(){
		
		ArrayList<String> a = new ArrayList<>();
		
		for (Tags t : Tags.values()){
			
			if (t.isSelected()){
				a.add(t.name());
			}
		}
		return a;
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

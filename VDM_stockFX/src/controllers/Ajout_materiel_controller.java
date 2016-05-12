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
import javafx.scene.input.KeyCode;
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

/**
 * Controle la zone graphique du formulaire d'ajout d'un matériel.
 */
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
	
	private VBox capacity_vbox;
	private HBox capacity_hbox;
	private HBox c_hbox;
	private Label hc;
	private Label xc;
	private CheckBox sdhc;
	private CheckBox sdxc;
	
	private HBox cf_hbox;
	private VBox udma7_vbox;
	private Label udma;
	private CheckBox udma7;
	
	private Label format;
	private CheckBox sd_cbox;
	private CheckBox cf_cbox;
	private Label ssd;
	private CheckBox usb2_cbox;
	
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
		materiel = new Materiel();
		materiel.setHors_service(false);
		materiel.setEn_stock(true);
		
		cb1.setItems(null);
		cb1.getSelectionModel().select(null);
		cb1.getEditor().setText(null);
		
		textFields.get(0).setText(null);
        textFields.get(1).setText(null);
        textFields.get(2).setText(null);
		ta5.setText(null);
		
		cf_cbox.setSelected(false);
    	udma7.setSelected(false);
    	sd_cbox.setSelected(false);
    	uhs1.setSelected(false);
    	uhs3.setSelected(false);
    	classe8.setSelected(false);
    	classe10.setSelected(false);
    	sdhc.setSelected(false);
    	sdxc.setSelected(false);
    	usb2_cbox.setSelected(false);
    	
    	
    	cf_hbox.setDisable(false);
    	sd_hbox.setDisable(false);

    	Tags.CF.setSelected(false);
		Tags.UDMA7.setSelected(false);
    	Tags.SD.setSelected(false);
    	Tags.UHS1.setSelected(false);
    	Tags.UHS3.setSelected(false);
    	Tags.CLASSE8.setSelected(false);
    	Tags.CLASSE10.setSelected(false);
    	Tags.SDHC.setSelected(false);
    	Tags.SDXC.setSelected(false);
    	Tags.USB2.setSelected(false);
    	
		cb1.requestFocus();
		
		cb1.hide();
	}
	
	/**
	 * Recharge la série des éléments graphiques affichés.
	 * Seule la valeur de la liste déroulante n'est pas nulle. 
	 * @param s La valeur a afficher dans la liste déroulante.
	 */
	public void reinit(String s){
		
		materiel = new Materiel();
		materiel.setHors_service(false);
		materiel.setEn_stock(true);
		
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
	
	/**
	 * Initialisation du Formulaire et des écouteurs sur les champs.
	 */
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
		cb1.setVisibleRowCount(10);
		cb1.setMaxSize(400.0, 600.0);
		HBox.setHgrow(cb1, Priority.ALWAYS);
		
		ChangeListener<String> auto_completion_listener = new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				cb1.getEditor().setText(newValue.toUpperCase());
				liste_autocompletion = AutoCompletion.autocomplete("materiel", "nom", newValue.toUpperCase());
				
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
		format.setStyle("-fx-font-weight: bold;");
		sd_cbox  = new CheckBox("SD"); 
		cf_cbox  = new CheckBox("CF");	
		
		ssd = new Label("SSD");
		ssd.setStyle("-fx-font-weight: bold;");
		usb2_cbox  = new CheckBox("USB2");	

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
		
		capacity_vbox = new VBox();
		capacity_vbox.setSpacing(10);
		capacity_hbox = new HBox();
		capacity_hbox.setSpacing(10);
		c_hbox = new HBox();
		c_hbox.setSpacing(30);
		
		hc = new Label("", new ImageView("sdhc.jpg"));
		xc = new Label("", new ImageView("sdxc.jpg"));
		sdhc = new CheckBox("SDHC");
		sdxc = new CheckBox("SDXC");
		c_hbox.getChildren().addAll(hc, xc);
		capacity_hbox.getChildren().addAll(sdhc, sdxc);
		capacity_vbox.getChildren().addAll(c_hbox, capacity_hbox);
		
		sd_hbox = new HBox();
		sd_hbox.setSpacing(20);
		sd_hbox.getChildren().addAll(uhs_vbox, new Separator(Orientation.VERTICAL), classe_vbox, new Separator(Orientation.VERTICAL), capacity_vbox);
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
		    	sd_hbox.setDisable(false);
		    	cf_hbox.setDisable(true);
		    	Tags.CF.setSelected(true);
		    	Tags.SD.setSelected(false);
		    	Tags.UHS1.setSelected(false);
		    	Tags.UHS3.setSelected(false);
		    	Tags.CLASSE8.setSelected(false);
		    	Tags.CLASSE10.setSelected(false);
		    	Tags.SDHC.setSelected(false);
		    	Tags.SDXC.setSelected(false);
	    		Tags.UDMA7.setSelected(false);
	    	}
	    	else {
	    		uhs1.setSelected(false);
		    	uhs3.setSelected(false);
		    	classe8.setSelected(false);
		    	classe10.setSelected(false);
		    	sdhc.setSelected(false);
		    	sdxc.setSelected(false);
		    	sd_hbox.setDisable(true);
		    	Tags.SD.setSelected(true);
		    	Tags.CF.setSelected(false);
		    	Tags.UDMA7.setSelected(false);
		    	Tags.UHS1.setSelected(false);
		    	Tags.UHS3.setSelected(false);
		    	Tags.CLASSE8.setSelected(false);
		    	Tags.CLASSE10.setSelected(false);
		    	Tags.SDHC.setSelected(false);
		    	Tags.SDXC.setSelected(false);
	    	}
	    	
	    	
	    	
	    });
	    
	    cf_cbox.setOnAction(a -> {
	    	
	    	if(cf_cbox.isSelected()){
	    		sd_cbox.setSelected(false);
		    	uhs1.setSelected(false);
		    	uhs3.setSelected(false);
		    	classe8.setSelected(false);
		    	classe10.setSelected(false);
		    	sdhc.setSelected(false);
		    	sdxc.setSelected(false);
		    	cf_hbox.setDisable(false);
		    	sd_hbox.setDisable(true);
		    	Tags.SD.setSelected(true);
		    	Tags.CF.setSelected(false);
		    	Tags.UDMA7.setSelected(false);
		    	Tags.UHS1.setSelected(false);
		    	Tags.UHS3.setSelected(false);
		    	Tags.CLASSE8.setSelected(false);
		    	Tags.CLASSE10.setSelected(false);
		    	Tags.SDHC.setSelected(false);
		    	Tags.SDXC.setSelected(false);
	    	}
	    	else {
	    		udma7.setSelected(false);
	    		cf_hbox.setDisable(true);
	    		Tags.CF.setSelected(true);
	    		Tags.UDMA7.setSelected(false);
	    		Tags.SD.setSelected(false);
		    	Tags.UHS1.setSelected(false);
		    	Tags.UHS3.setSelected(false);
		    	Tags.CLASSE8.setSelected(false);
		    	Tags.CLASSE10.setSelected(false);
		    	Tags.SDHC.setSelected(false);
		    	Tags.SDXC.setSelected(false);
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
	    
	    sdxc.setOnAction(a -> {
	    	if (sdxc.isSelected()){
	    		sdhc.setSelected(false);
	    		Tags.SDXC.setSelected(true);
	    		Tags.SDHC.setSelected(false);
	    	}
	    	else {
	    		
	    		Tags.SDXC.setSelected(false);
	    	}
	    });
	    
	    sdhc.setOnAction(a -> {
	    	if (sdhc.isSelected()){
	    		sdxc.setSelected(false);
	    		Tags.SDHC.setSelected(true);
	    		Tags.SDXC.setSelected(false);
	    	}
	    	else {
	    		Tags.SDHC.setSelected(false);
	    	}
	    });
	    
	    usb2_cbox.setOnAction(a -> {
	    	if (usb2_cbox.isSelected()){
	    		Tags.USB2.setSelected(true);

	    	}
	    	else {
	    		Tags.USB2.setSelected(false);
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
		tags_grid.add(new Separator(Orientation.HORIZONTAL), 0, 5);
		tags_grid.add(ssd, 0, 6);
		
		tags_grid.add(new Separator(Orientation.VERTICAL), 1, 0);
		tags_grid.add(new Separator(Orientation.VERTICAL), 1, 2);
		tags_grid.add(new Separator(Orientation.VERTICAL), 1, 4);
		tags_grid.add(new Separator(Orientation.VERTICAL), 1, 6);
		
		tags_grid.add(new Separator(Orientation.HORIZONTAL), 2, 1);
		tags_grid.add(sd_hbox, 2, 2);
		tags_grid.add(new Separator(Orientation.HORIZONTAL), 2, 3);
		tags_grid.add(cf_hbox, 2, 4);
		tags_grid.add(new Separator(Orientation.HORIZONTAL), 2, 5);
		tags_grid.add(usb2_cbox, 2, 6);
		
		tags_grid.add(new Separator(Orientation.VERTICAL), 3, 2);
		tags_grid.add(new Separator(Orientation.VERTICAL), 3, 4);
		
		form.getChildren().add(tags_grid);	
		
		HBox h5 = new HBox();
		h5.setSpacing(10);
		h5.setPadding(new Insets(10, 0, 0, 0));
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
		materiel = MongoAccess.request("materiel", "nom", cb1.getSelectionModel().getSelectedItem()).as(Materiel.class);

		if (materiel == null){
			materiel = new Materiel();
			materiel.setHors_service(false);
			materiel.setEn_stock(true);
			
			unfreeze();
		}
		else {
			freeze();
		
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
            	case "SDHC" : sdhc.setSelected(true);
                            break;
                case "SDXC" : sdxc.setSelected(true);
                            break;
            	case "USB2" : usb2_cbox.setSelected(true);
                            break;
            	}
            }
            
        	ta5.setText(materiel.getCommentaire());
        }
        
		
  	}
	
	/**
	 * Retourne la liste des tags <b><u>valides</u></b> pour le matériel
	 * @return la liste des tags <b><u>valides</u></b> pour le matériel
	 * @see Ajout_destinataire_controller
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
	 * Retourne l'instance du matériel
	 * @return l'instance du matériel
	 */
	public Materiel getMateriel() {
		return materiel;
	}

	/**
	 * Modifie l'instance du matériel
	 * @param materiel l'instance du matériel
	 */
	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	/**
	 * Retourne la liste les champs de saisie de texte du formulaire
	 * @return la liste les champs de saisie de texte du formulaire
	 */
	public List<TextField> getTextFields() {
		return textFields;
	}

	/**
	 * Modifie la liste les champs de saisie de texte du formulaire
	 * @param textFields la liste les champs de saisie de texte du formulaire
	 */
	public void setTextFields(List<TextField> textFields) {
		this.textFields = textFields;
	}

	/**
	 * Retourne l'instance du champ de commentaire
	 * @return l'instance du champ de commentaire
	 */
	public TextArea getTa5() {
		return ta5;
	}

	/**
	 * Modifie l'instance du champ de commentaire
	 * @param ta5 l'instance du champ de commentaire
	 */
	public void setTa5(TextArea ta5) {
		this.ta5 = ta5;
	}

	/**
	 * Retourne l'instance de la liste déroulante.
	 * @return l'instance de la liste déroulante.
	 */
	public ComboBox<String> getCb1() {
		return cb1;
	}

	/**
	 * Modifie l'instance de la liste déroulante.
	 * @param cb1 l'instance de la liste déroulante.
	 */
	public void setCb1(ComboBox<String> cb1) {
		this.cb1 = cb1;
	}

	@Override
	public Enregistrable getEnregistrable() {
		
		System.out.println("Enregistrable : " +  materiel.getNom());
		return materiel;
	}

}

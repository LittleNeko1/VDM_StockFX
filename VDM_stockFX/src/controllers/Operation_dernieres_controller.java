package controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.jongo.MongoCursor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import models.Classes_ajout_bloc;
import models.Commun;
import models.Enregistrable;
import models.Operation;
import utils.MongoAccess;

public class Operation_dernieres_controller implements SuperController{
	
	private TableView<Operation> tv5 ;
	private TableColumn<Operation, String> expediteurCol;
    private TableColumn<Operation, String> materielCol;
    private TableColumn<Operation, String> destinataireCol;
    private TableColumn<Operation, String> complementCol;
    private TableColumn<Operation, String> dateOperationCol;
    private TableColumn<Operation, String> dateRetourCol;
    
    private ObservableList<Operation> observableOperations;
    
    private Map<String, ComboBox<String>> comboboxes;

	
	@Override
	public void reinit(){}
	
	@Override
	public void unfreeze(){}
	
	
	
    @SuppressWarnings("unchecked")
	public VBox init(VBox form){
		
		form.getChildren().clear();
		
		HBox h1 = new HBox();
		h1.setSpacing(15);
		h1.setMaxWidth(Double.MAX_VALUE);
		
		Map<String, String> choiceboxes = new LinkedHashMap<String, String>();
		comboboxes = new LinkedHashMap<>();
		
		choiceboxes.put("expediteur", "Filtrer par expéditeur : ");
		choiceboxes.put("destinataire", "Filtrer par destinataire : ");
		choiceboxes.put("materiel", "Filtrer par matériel : ");
		choiceboxes.put("complement", "Filtrer par complément : ");
		
		for (String s : choiceboxes.keySet()){
			
			VBox v1 = new VBox();
			v1.setMaxWidth(Double.MAX_VALUE);
			v1.setAlignment(Pos.CENTER);
			v1.setSpacing(5);
			
			
			Label l1 = new Label(choiceboxes.get(s));
			l1.maxWidth(200);
			l1.setMaxSize(200.0, Control.USE_PREF_SIZE);
			
			//l1.setStyle("-fx-background-color: rgb(160,160,160)");
			
			ComboBox<String> cb1 = new ComboBox<String>();
			cb1.prefWidth(400);
			cb1.setMaxSize(400.0, Control.USE_PREF_SIZE);
			cb1.setEditable(true);

		    comboboxes.put(s, cb1);
			
			ObservableList<String> liste_enregistrable = FXCollections.observableArrayList();
			
			System.out.println(s.toUpperCase());
			System.out.println(Classes_ajout_bloc.valueOf(s.toUpperCase()));
			System.out.println(Classes_ajout_bloc.valueOf(s.toUpperCase()).getClasse());
			
			MongoCursor<Commun> cursor_enregistrable = MongoAccess.request(s).as(Commun.class);
			
			cursor_enregistrable.forEach(a -> liste_enregistrable.add(a.getNom()));
			
			cb1.setItems(liste_enregistrable);
			
			cb1.setOnAction(a -> {
				
				if (((ComboBox<String>) a.getSource()).getValue() != null){
					
					MongoCursor<Operation> mongo_cursor_operations = MongoAccess.requestAll("operation", s, ((ComboBox<String>) a.getSource()).getValue()).as(Operation.class);
					observableOperations.clear();
					mongo_cursor_operations.forEach(b -> observableOperations.add(b));
					
					for (String t : choiceboxes.keySet()){
						if (! t.equals(s)){
							comboboxes.get(t).getSelectionModel().select(null);
						}					
					}			
			    }
			});
			
			
			v1.getChildren().add(l1);
			v1.getChildren().add(cb1);
			h1.getChildren().add(v1);	
			
			HBox.setHgrow(cb1, Priority.ALWAYS);
			HBox.setHgrow(l1, Priority.ALWAYS);
			HBox.setHgrow(v1, Priority.ALWAYS);
		}
		
		form.getChildren().add(h1);	

		tv5 = new TableView<Operation>();
		
		expediteurCol = new TableColumn<Operation, String>("Expéditeur");
        materielCol = new TableColumn<Operation, String>("Matériel");
        destinataireCol = new TableColumn<Operation, String>("Destinataire");
        complementCol = new TableColumn<Operation, String>("Complement");
        dateOperationCol = new TableColumn<Operation, String>("Date opération");
        dateRetourCol = new TableColumn<Operation, String>("Date retour");
        
        
        tv5.getColumns().addAll(expediteurCol, materielCol, destinataireCol, complementCol, dateOperationCol, dateRetourCol);
       
        observableOperations = FXCollections.observableArrayList();
        
        MongoCursor<Operation> mongo_cursor_operations = MongoAccess.request("operation").as(Operation.class);
        mongo_cursor_operations.forEach(a -> observableOperations.add(a));
        
        expediteurCol.setCellValueFactory(
        	    new PropertyValueFactory<>("expediteur")
        	);
        materielCol.setCellValueFactory(
        	    new PropertyValueFactory<>("materiel")
        	);
        destinataireCol.setCellValueFactory(
        	    new PropertyValueFactory<>("destinataire")
        	);
        complementCol.setCellValueFactory(
        	    new PropertyValueFactory<>("complement")
        	);
        dateOperationCol.setCellValueFactory(
        	    new PropertyValueFactory<>("date_operation")
        	);
        dateRetourCol.setCellValueFactory(
        	    new PropertyValueFactory<>("dateRetour")
        	);
        
        
        tv5.setItems(observableOperations);

		form.getChildren().add(tv5);	
		
		return form;
	}

@Override
public Enregistrable getEnregistrable() {
	// TODO Auto-generated method stub
	return null;
}

}

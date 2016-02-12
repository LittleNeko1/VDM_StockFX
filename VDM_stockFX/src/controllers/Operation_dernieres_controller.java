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
import models.Destinataire;
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

	
	@Override
	public void reinit(){

	}
	
	
    @SuppressWarnings("unchecked")
	public VBox init(VBox form){
		
		form.getChildren().clear();
		
		HBox h1 = new HBox();
		h1.setSpacing(15);
		h1.setMaxWidth(Double.MAX_VALUE);
		
		Map<String, String> choiceboxes = new LinkedHashMap<String, String>();
		
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

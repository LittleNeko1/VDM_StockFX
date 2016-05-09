package utils;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.paint.Color;
import javafx.util.Callback;

/**
 * Classe étandant {@link javafx.scene.control.ComboBox} et permettant d'invalider certains champs.
 * @author Nidia 
 * @see  <a href="http://stackoverflow.com/questions/13587134/how-to-disable-some-items-of-javafx-combobox">Page originale</a>
 */
public class CustomComboBox<T> extends ComboBox<T> {

    private ArrayList<T> disabledItems = new ArrayList<T>();

    /**
     * Constructeur vide
     */
    public CustomComboBox() {
        super();
        setup();
    }

    /**
     * Constructeur à partir d'une liste d'éléments
     * @param list liste initiale des éléments
     */
    public CustomComboBox(ObservableList<T> list) {
        super(list);
        setup();
    }

    /**
     * Parametrage de l'objet
     */
    private void setup() {

        SingleSelectionModel<T> model = new SingleSelectionModel<T>() {

            @Override
            public void select(T item) {

                if (disabledItems.contains(item)) {
                    return;
                }

                super.select(item);
            }

            @Override
            public void select(int index) {
                T item = getItems().get(index);

                if (disabledItems.contains(item)) {
                    return;
                }

                super.select(index);
            }

            @Override
            protected int getItemCount() {
                return getItems().size();
            }

            @Override
            protected T getModelItem(int index) {
            	
            	if (index >= 0){
            		return getItems().get(index);
            	}
            	else {
            		return null;
            	}
            	
                
            }

        };

        Callback<ListView<T>, ListCell<T>> callback = new Callback<ListView<T>, ListCell<T>>() {

            @Override
            public ListCell<T> call(ListView<T> param) {
                final ListCell<T> cell = new ListCell<T>() {
                	
                	/**
                	 * Mise à jour qui change l'aspect et le comportement de cette entrée :
                	 * Ici, l'élément est coloré en rouge et n'est plus sélectionnable.
                	 */
                    @Override
                    public void updateItem(T item, boolean empty) {                    	

                        super.updateItem(item, empty);

                        if (item != null) {

                            setText(item.toString());

                            if (disabledItems.contains(item)) {
                            	System.out.println("updateItem [wrong] : " + item.toString());
                                setTextFill(Color.LIGHTCORAL);
                                setDisabled(true);
                            }
                            else {
                            	System.out.println("updateItem [good!] : " + item.toString());
                                setTextFill(Color.BLACK);
                                setDisabled(false);
                            }

                        } else {

                            setText(null);

                        }
                    }
                };

                return cell;
            }

        };

        setSelectionModel(model);
        setCellFactory(callback);

    }

    /**
     * Invalide les entrées passées en parametre.
     * Dans cette implémentation, le nom est coloré en rouge et ne peut pas etre sélectionné.
     * @param items Les éléments à invalider.
     */
    public void setDisabledItems(ArrayList<T> items) {
    	
    	disabledItems = new ArrayList<>();
    	
        for (int i = 0; i < items.size(); i++) {
            disabledItems.add(items.get(i));
        }
        
        System.out.println("disabledItems : " + disabledItems);
    }
    
    /**
     * Invalide les entrées passées en parametre.
     * Dans cette implémentation, le nom est coloré en rouge et ne peut pas etre sélectionné.
     * @param items Les éléments à invalider.
     */
    public void setDisabledItem(T item) {

        disabledItems.add(item);
        
        System.out.println("disabledItems : " + disabledItems);
    }

}
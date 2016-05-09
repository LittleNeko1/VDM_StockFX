package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Génère les listes mises à jour des ComboBox.
 * La connexion à la base MongoDb se fait au travers d'un socket.
 * */
public class AutoCompletion {
	
	private static Socket socket ;
	
	/**
	 * Transmet une requète regex /valeur/.
	 * @param table : String
	 * @param champ : String
	 * @param valeur : String
	 * @return une liste de noms vérifiant le filtre regex : ObservableList&lt;String&gt;
	 */
	public static ObservableList<String> autocomplete(String table, String champ, String valeur){
		
		ObservableList<String> ol = FXCollections.observableArrayList();
		
		
		try
        {
            // Connect to the server
            socket = new Socket( "192.168.0.201", 44800 );

            // Create input and output streams to read from and write to the server
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            out.write(String.format("0=%s&%s&%s", table, champ, valeur));
            out.newLine();
            out.flush();


            // Read data from the server until we finish reading the document
            String line = in.readLine();
            for (String s : line.split("&")){
        		ol.add(s);
        	}
            

            // Close our streams
            in.close();
            out.close();
            socket.close();
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
		finally {		
			if (socket != null){
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return ol;
    }
	
	/**
	 * Transmet une requète regex /^valeur$/.
	 * @param table : String
	 * @param champ : String
	 * @param valeur : String
	 * @return une liste de noms vérifiant le filtre regex : ObservableList&lt;String&gt;
	 */
    public static ObservableList<String> perfectMatch(String table, String champ, String valeur){
		
		ObservableList<String> ol = FXCollections.observableArrayList();
		
		
		try
        {
            // Connect to the server
            socket = new Socket( "192.168.0.201", 44800 );

            // Create input and output streams to read from and write to the server
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            out.write(String.format("00=%s&%s&%s", table, champ, valeur));
            out.newLine();
            out.flush();


            // Read data from the server until we finish reading the document
            String line = in.readLine();
            for (String s : line.split("&")){
        		ol.add(s);
        	}
            

            // Close our streams
            in.close();
            out.close();
            socket.close();
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
		
		finally {		
			if (socket != null){
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return ol;
    }
	
    /**
     * Transmet une requète pour trouver les destinataires incompatibles avec le materiel sélectionné.
     * @param table  : String
     * @param materiel  : String
     * @return une liste de noms de destinataires invalides : ArrayList&lt;String&gt;
     */
    public static ArrayList<String> wrongTag(String table, String materiel){
		
		ArrayList<String> ol =  new ArrayList<>();
		
		
		try
        {
            // Connect to the server
            socket = new Socket( "192.168.0.201", 44800 );

            // Create input and output streams to read from and write to the server
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            out.write(String.format("1=%s", materiel));
            out.newLine();
            out.flush();


            // Read data from the server until we finish reading the document
            String line = in.readLine();
            for (String s : line.split("&")){
        		ol.add(s);
        	}
            

            // Close our streams
            in.close();
            out.close();
            socket.close();
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
		finally {		
			if (socket != null){
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return ol;
    }



}

package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AutoCompletion {
	
	public static ObservableList<String> autocomplete(String table, String champ, String valeur){
		
		ObservableList<String> ol = FXCollections.observableArrayList();
		
		
		try
        {
            // Connect to the server
            Socket socket = new Socket( "192.168.0.201", 44800 );

            // Create input and output streams to read from and write to the server
            //PrintStream out = new PrintStream( socket.getOutputStream() );
            //BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            out.println(String.format("0=%s&%s&%s", table, champ, valeur));
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
		return ol;
    }
	
    public static ObservableList<String> perfectMatch(String table, String champ, String valeur){
		
		ObservableList<String> ol = FXCollections.observableArrayList();
		
		
		try
        {
            // Connect to the server
            Socket socket = new Socket( "192.168.0.201", 44800 );

            // Create input and output streams to read from and write to the server
            //PrintStream out = new PrintStream( socket.getOutputStream() );
            //BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            out.println(String.format("00=%s&%s&%s", table, champ, valeur));
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
		return ol;
    }
	
    public static ArrayList<String> wrongTag(String table, String materiel){
		
		ArrayList<String> ol =  new ArrayList<>();
		
		
		try
        {
            // Connect to the server
            Socket socket = new Socket( "192.168.0.201", 44800 );

            // Create input and output streams to read from and write to the server
            //PrintStream out = new PrintStream( socket.getOutputStream() );
            //BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            out.println(String.format("1=%s", materiel));
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
		return ol;
    }



}

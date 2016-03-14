package utils;

import controllers.Operation_nouvelle_controller;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class ComInput {
	
	private static String comPort;
	private static int baudrate;
	private static int bytesize;
	private static int parity;
	private static int stopbits;
	private static int timeout;
	private static boolean xonxoff;
	private static boolean rtscts;
	
	private static Operation_nouvelle_controller gui;
	
	// port='COM1', baudrate=19200, bytesize=8, parity='N', stopbits=1, timeout=None, xonxoff=0, rtscts=0
	
private static SerialPort serialPort;
	
	public static void init(Operation_nouvelle_controller onc) {
	    String[] portNames = SerialPortList.getPortNames();
//	    for(int i = 0; i < portNames.length; i++){
//	        System.out.println(portNames[i]);
//	    }
	    gui = onc;
	    comPort = portNames[0];
	    baudrate = 9600;
	    bytesize = 8;
	    stopbits = 1;
	    parity = 0;
	    
	    
	    boolean running = true;
	    
	    try {
	       open();
	       
	       while (running){
	    	   read();
	       }
	    }
	    catch (SerialPortException spe){
	    	System.out.println(spe);
	    }
	    
	}
	
	public static void open() throws SerialPortException{
		serialPort = new SerialPort(comPort);
	    serialPort.openPort();//Open serial port
	    serialPort.setParams(baudrate, bytesize, stopbits, parity);//Set params.
	    
		
	}
	
	public static void read() throws SerialPortException {
	    
	        
	        byte[] buffer = serialPort.readBytes(1);//Read 10 bytes from serial port
	        
	        String s = "";

	        for (byte b : buffer){
	        	s += (char)b;
	        }
	        
	        while (!s.contains("\n")){
	        	
	        	buffer = serialPort.readBytes(1);
	        	
	        	for (byte b : buffer){
		        	s += (char)b;
		        }
	        	
	        }
	        
	        System.out.println("s finale : " + s);
	        gui.reinit(s);
	        //serialPort.closePort();//Close serial port
	    

	}

}

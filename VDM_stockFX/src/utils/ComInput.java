package utils;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class ComInput {
	
	private static String comPort;
	private static int baudrate;
	private static int bytesize;
	private static int parity;
	private static int stopbits;
	
	// port='COM1', baudrate=19200, bytesize=8, parity='N', stopbits=1, timeout=None, xonxoff=0, rtscts=0
	
private static SerialPort serialPort;
	
	public static void init() {
	    String[] portNames = SerialPortList.getPortNames();

	    comPort = portNames[0];
	    baudrate = 9600;
	    bytesize = 8;
	    stopbits = 1;
	    parity = 0;
	    
	    try {
	       open();
	    }
	    catch (SerialPortException spe){
	    	System.out.println(spe);
	    }
	    
	}
	
	public static void open() throws SerialPortException{
		serialPort = new SerialPort(comPort);
	    serialPort.openPort();
	    serialPort.setParams(baudrate, bytesize, stopbits, parity);
	    
		
	}
	
	public static String read() throws SerialPortException {
		
		System.out.println("read() en attente ...");

		 
        byte[] buffer = serialPort.readBytes(1);
        
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
        return s;
        
        //serialPort.closePort();//Close serial port

    
	       
	}

}

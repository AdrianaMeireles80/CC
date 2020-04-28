import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

public class Main {

    //private AnonGW anon;
    //private Origin cliente;
    //private TargetServer servidor;

    public static void main(String[] args) throws Exception { 

        AnonGW anon = new AnonGW();  

    	try {
           
            while (true){
            	try{

            	anon.connectOrigin(1234);

    	  		anon.connectTarget("127.0.0.1", 1234);

    	  	   }catch(IOException e){
    				e.printStackTrace();
				}
    	  	   	   
           }  
        }catch (ConnectException e){
        	//throw new Exception("O servidor não se encontra conectável.");
            System.err.println("O servidor não se encontra conectável.");
        }
    }
}


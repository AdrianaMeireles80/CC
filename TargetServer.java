import java.net.*;
import java.io.*;


public class TargetServer {
  
	public static void main(String[] args) throws Exception {

		System.out.println("Servidor iniciado.");
		
		ServerSocket servidor = new ServerSocket(80);
		System.out.println("Aguardar Conexão de um cliente.");
	
		while(true){
			Socket anonpeer = servidor.accept();
			System.out.println("Conexão estabelecida");
			Thread c = new Thread(new TargetServerT(anonpeer));
			c.start();            
        }
	}
}
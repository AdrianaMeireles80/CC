import java.net.*;
import java.io.*;


public class TargetServer {
  
	public static void main(String[] args) throws Exception {

		System.out.println("Servidor iniciado.");
		// Instancia o ServerSocket ouvindo a porta 80
		ServerSocket servidor = new ServerSocket(80);
		System.out.println("Aguardar Conexão de um cliente.");
	
		while(true){
			Socket cliente = servidor.accept();
			System.out.println("Conexão estabelecida");
			Thread c = new Thread(new TargetServerT(cliente));
			c.start();            
        } 
   

		
	}
}
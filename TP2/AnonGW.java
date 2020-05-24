import java.net.*;
import java.io.*;


public class AnonGW{
	public static void main(String[] args) throws Exception{
		System.out.println("Anon iniciado.");

		System.out.println("Iniciar conexão com o servidor");
		ServerSocket anon = new ServerSocket(80);

		while(true){
			Socket cliente = anon.accept();
			System.out.println("Conexão estabelecida");
			Thread c = new Thread(new AnonGWT(cliente));
			c.start();            
        } 
   }
}
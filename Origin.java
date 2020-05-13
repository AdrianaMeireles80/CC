// cliente comunica com o AnonGW
//AnonGW comunica com o servidor

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Origin{
	

  public static void main(String[] args) throws Exception{
		System.out.println("Cliente iniciado.");

		System.out.println("Iniciar conexão com o servidor");

		Socket socket = new Socket("10.1.1.3",80);

		System.out.println("Conexão Estabelecida.");

		InputStream input = socket.getInputStream();

		OutputStream output = socket.getOutputStream();

		BufferedReader in = new BufferedReader(new InputStreamReader(input));
		PrintStream out = new PrintStream(output);
		
		Scanner scan = new Scanner(System.in);
		
		while(true){

			System.out.print("Escreva uma mensagem: ");
			
			String mensagem = scan.nextLine();

                	out.println(mensagem);

			if("FIM".equals(mensagem)){
				break;
			}

			mensagem = in.readLine();
			System.out.println(
				"Mensagem recebida do servidor: " +
				mensagem);
		}
		System.out.println("Conexão encerrada.");
		in.close();
		out.close();
		socket.close();
   }
}
 
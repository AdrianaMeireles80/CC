import java.net.*;
import java.io.*;


public class TargetServer {
  
	public static void main(String[] args) throws Exception {

		System.out.println("Servidor iniciado.");
		// Instancia o ServerSocket ouvindo a porta 80
		ServerSocket servidor = new ServerSocket(80);
		System.out.println("Aguardar Conexão de um cliente.");

		while(true){
      
        		// o método accept() bloqueia a execução até que
        		// o servidor receba um pedido de conexão
        		Socket socket = servidor.accept();

        		System.out.println("Conexão estabelecida");
        
        		InputStream input = socket.getInputStream();
        		OutputStream output = socket.getOutputStream();
	
        		BufferedReader in = new BufferedReader(new InputStreamReader(input));
        		PrintStream out = new PrintStream(output);
        
       			while(true){
				String mensagem = in.readLine();
 		
				System.out.println("Mensagem recebida do cliente [" + socket.getInetAddress().getHostName() + "]: " + mensagem);
		
				if("FIM".equals(mensagem)){
					break;
         			}

                		out.println(mensagem);
 		       	}

			System.out.println("Conexão encerrada.");
			in.close();
			out.close();
			socket.close();
		}      
	}
}
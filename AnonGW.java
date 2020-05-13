import java.net.*;
import java.io.*;

public class AnonGW{
	public static void main(String[] args) throws Exception{
		System.out.println("Anon iniciado.");

		System.out.println("Iniciar conexão com o servidor");
		ServerSocket anon = new ServerSocket(80);

		while(true){

			Socket socket = anon.accept();

			Socket server = new Socket("10.3.3.1",80);
		
			System.out.println("Conexão estabelecida");

			InputStream input = socket.getInputStream();
                	OutputStream output = socket.getOutputStream();

	                BufferedReader in = new BufferedReader(new InputStreamReader(input));
        	        PrintStream out = new PrintStream(output);

			input = server.getInputStream();
                	output = server.getOutputStream();

              		BufferedReader inp = new BufferedReader(new InputStreamReader(input));
                	PrintStream outp = new PrintStream(output);
				
        
        		while(true){
				String mensagem = in.readLine();
 		
				System.out.println("Mensagem recebida do cliente [" +
                		socket.getInetAddress().getHostName()+"]: " + mensagem);

                		outp.println(mensagem);
		
				if("FIM".equals(mensagem)){
					break;
         			}

				mensagem = inp.readLine();
				out.println(mensagem);
			}
			
			System.out.println("Conexão encerrada.");
			in.close();
			out.close();
			inp.close();
			outp.close();
			socket.close();
			server.close();
        	}
        }
}
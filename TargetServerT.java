import java.net.*;
import java.io.*;
import java.util.*;

public class TargetServerT implements Runnable {

	private Socket socket ;
	private BufferedReader in;
	private PrintStream out;

	public TargetServerT(Socket c) throws IOException {

	  	this.socket = c;

	}

	public void run() {
        	try {    

           		InputStream input = socket.getInputStream();
        		OutputStream output = socket.getOutputStream();
	
        		this.in = new BufferedReader(new InputStreamReader(input));
        		this.out = new PrintStream(output);
  
	    		while(true){
         		
				String mensagem = in.readLine();
				//String mens = out.realLine();
				System.out.println("Mensagem recebida do cliente [" + socket.getInetAddress().getHostName() + "]: " + mensagem);
				System.out.println("Tamanho da mensagem do cliente [" + socket.getInetAddress().getHostName() + "]: " + mensagem.length());

				if("FIM".equals(mensagem)) break;

 				if (mensagem.equals("file")) {
					System.out.println("ficheiro");
					String filename = "file.txt";
					
    					StringBuilder file = new StringBuilder();

    					try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
      					  	String line;
       						while ((line = br.readLine()) != null) {
         					   file.append(line).append(" ");
        					}
    					}
 					
	  				Map<Integer, StringBuilder> pacote = new HashMap<Integer, StringBuilder>();
					pacote.put(file.length(),file);
					out.println(pacote);
					System.out.println("Mensagem enviada do cliente [" + socket.getInetAddress().getHostName() + "]: " + file.toString());
					System.out.println("Tamanho da mensagem do cliente [" + socket.getInetAddress().getHostName() + "]: " + file.length()); 
				}else {

				
         	

	            out.println(mensagem);
				System.out.println("Mensagem enviada do cliente [" + socket.getInetAddress().getHostName() + "]: " + mensagem);
				System.out.println("Tamanho da mensagem do cliente [" + socket.getInetAddress().getHostName() + "]: " + mensagem.length()); 	}		
}

 		}catch(Exception e){
				e.printStackTrace();
				}finally{
					try{
						System.out.println("Conexão encerrada.");
						in.close();
						out.close();
						socket.close();
					}
					catch(Exception e){
			   			e.printStackTrace();
			  		}
		   		 }
		} 
	}
    
	

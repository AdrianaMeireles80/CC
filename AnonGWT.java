import java.net.*;
import java.io.*;

public class AnonGWT implements Runnable{

			private Socket socket ;
			private Socket server = new Socket("10.3.3.1", 80); 
			private BufferedReader inp;
			private PrintStream outp;
			private BufferedReader in;
			private PrintStream out;
	
		    public AnonGWT(Socket c) throws IOException{
		    	this.socket = c;
		    }
				
        
            public void run(){
            	try{
            			
				System.out.println("Conexão estabelecida");

				InputStream input = socket.getInputStream();
            	OutputStream output = socket.getOutputStream();

	        	this.in = new BufferedReader(new InputStreamReader(input));
        		this.out = new PrintStream(output);

				input = server.getInputStream();
            	output = server.getOutputStream();

            	this.inp = new BufferedReader(new InputStreamReader(input));
            	this.outp = new PrintStream(output);



            	while(true){
					String mensagem = in.readLine();
 		
					System.out.println("Mensagem recebida do cliente [" + socket.getInetAddress().getHostName() + "]: " + mensagem);

                	outp.println(mensagem);
		
					if("FIM".equals(mensagem)){
						break;
         			}

					mensagem = inp.readLine();
					out.println(mensagem);
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
			    try{
				System.out.println("Conexão encerrada.");
				in.close();
				out.close();
				inp.close();
				outp.close();
				socket.close();
				server.close();
			   }
			   catch(Exception e){
			   	e.printStackTrace();
			   }
			}
        	}
        
}
import java.net.*;
import java.io.*;


public class TargetServerT implements Runnable {

		private Socket socket ;
		private BufferedReader in;
		private PrintStream out;

	    public TargetServerT(Socket c) throws IOException{
		    	this.socket = c;
		    }

	public void run(){
        try{    

            InputStream input = socket.getInputStream();
        	OutputStream output = socket.getOutputStream();
	
        	this.in = new BufferedReader(new InputStreamReader(input));
        	this.out = new PrintStream(output);
  
	    	while(true){
         		
				String mensagem = in.readLine();
 		
				System.out.println("Mensagem recebida do cliente [" + socket.getInetAddress().getHostName() + "]: " + mensagem);
		
				if("FIM".equals(mensagem)){
					break;
         			}

                out.println(mensagem);
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
    
	

import java.net.*;
import java.io.*;
import java.util.Random;

public class AnonGWT implements Runnable{


	  		private Socket socket ; //socket que vem do cliente
			 
			private DatagramSocket peer; //para enviar po peer
			private String[] anonPeer = {"10.1.1.2","10.4.4.2","10.4.4.3"} ;
			private BufferedReader in;// do q se le do socket do cliente
			private PrintStream out;//para responder ao cliente
			private byte buf[] = new byte[1024];


		    public AnonGWT(Socket c) throws IOException{
		    	this.socket = c;
		    }

		  
            public void run(){
            	try{
            	    	
			    this.peer  = new DatagramSocket(6666);

			    Random random = new Random();
			    int num = random.nextInt(2);

			    System.out.println("Numero do peer a qual vai ligar: " + anonPeer[num]);


			    InputStream input = socket.getInputStream();
            		    OutputStream output = socket.getOutputStream();

	        	    this.in = new BufferedReader(new InputStreamReader(input));
        		    this.out = new PrintStream(output);

        		    InetAddress addrPeer = InetAddress.getByName(anonPeer[num]); //para enviar para um dos 3 peers

            	            while(true){
					String mensagem = in.readLine(); //ler o que vem do cliente
 		
					System.out.println("Mensagem recebida do cliente [" + socket.getInetAddress().getHostName() + "]: " + mensagem);

					buf = mensagem.getBytes();

					DatagramPacket packet = new DatagramPacket(buf, buf.length, addrPeer, 6666);
                    
                                        peer.send(packet); // enviar o pacote po anonpeer
		
					if("FIM".equals(mensagem)){
						break;
         			        }

         			packet = new DatagramPacket(buf,buf.length);
         			peer.receive(packet); //t receber pacote do anonpeer

         			String received = new String(packet.getData(),0,packet.getLength());

         			out.println(received); //enviar a resposta po cliente

					
				}

			}catch(Exception e){
				e.printStackTrace();
			}finally{
			    try{
				System.out.println("Conexão encerrada.");
				in.close();
				out.close();
				//inp.close();
				//outp.close();
				socket.close();
				peer.close();
			   }
			   catch(Exception e){
			   	e.printStackTrace();
			   }
			}
        	}
        
}

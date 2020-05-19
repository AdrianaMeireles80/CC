import java.net.*;
import java.io.*;
import java.util.*;
public class AnonPeerT extends Thread{

    private BufferedReader in; //receber o que vem do server
    private PrintStream out;  //escrever o que vai po server
    private Socket server ; //mandar po server
    private DatagramSocket anon; //tratar do que vem do anon
    private byte buf[] = new byte[1024];


    public void run(){
    	try {

    		server = new Socket("10.3.3.1",80);

    		InputStream input = server.getInputStream();
                OutputStream output = server.getOutputStream();

                this.anon = new DatagramSocket(6666);


	        this.in = new BufferedReader(new InputStreamReader(input));
        	this.out = new PrintStream(output);

        	while(true){

        		
        		DatagramPacket packet = new DatagramPacket(buf, buf.length);
         		anon.receive(packet); // extrair dados que vem do anon
         		
         		InetAddress address = packet.getAddress();
         		int port = packet.getPort();

         		//packet = new DatagramPacket(buf,buf.length,address,port);
			
         		String received = new String(packet.getData(), 0, packet.getLength());
			System.out.println("Mensagem recebida do cliente [" + server.getInetAddress().getHostName() + "]: " + received);
         		out.println(received); //enviar a resposta po servidor

         		if("FIM".equals(received)){
				break;
         	        }
        	    

         	        received = in.readLine(); // ler o que vem do servidor
			Map<Integer, StringBuilder> pacote = new HashMap<Integer, StringBuilder>();
			pacote.put(received.key,received);
			buf = new byte[4*1024+200];
         	        buf = received.getBytes();
			
			System.out.println("vindo do servidor" + buf.length);			

		        packet = new DatagramPacket(buf,buf.length,address,port);         	    
			System.out.println("Mensagem recebida do server [" + server.getInetAddress().getHostName() + "]: " + received);
         	        anon.send(packet); //enviar a resposta po anon
			buf = new byte[4*1024+200];

        	}

    	}catch(Exception e){
				e.printStackTrace();
			}finally{
				try{
					System.out.println("ConexÃ£o encerrada.");
					in.close();
					out.close();
					server.close();
					anon.close();
				}
				catch(Exception e){
			   		e.printStackTrace();
			   }
		    	}
    	}

	public static void main(String[] args) {

		while(true){
		AnonPeerT anonpeer = new AnonPeerT();
    		anonpeer.run();
       		}
   	}
}

import java.net.*;
import java.io.*;
import java.util.*;
import java.nio.ByteBuffer;

public class AnonPeerT extends Thread{

    private BufferedReader in; //receber o que vem do server
    private PrintStream out;  //escrever o que vai po server
    private Socket server ; //mandar po server
    private DatagramSocket anon; //tratar do que vem do anon
    private byte buf[] = new byte[5*1024+200];
    private Map<Integer, PDU> pdublocks;

   	public void run(){
    		try {
			server = new Socket("10.3.3.1",80);

    			InputStream input = server.getInputStream();
            		OutputStream output = server.getOutputStream();

            		this.anon = new DatagramSocket(6666);


	      		this.in = new BufferedReader(new InputStreamReader(input));
        		this.out = new PrintStream(output);       	

        		while(true){
        			buf = new byte[5*1024+200];
				
        			DatagramPacket packet = new DatagramPacket(buf, buf.length);
         			anon.receive(packet); // para receber dados que vêm do anon
         		        		
         			InetAddress address = packet.getAddress();
         			int port = packet.getPort();         			
					
         			String received = new String(packet.getData(), 0, packet.getLength());

         			System.out.println("Mensagem recebida do anonGW ["  + "]: " + received);

         			out.println(received); //enviar a resposta para o servidor

         			if("FIM".equals(received)){
					break;
         	    		}        	    

         	    		received = in.readLine(); // ler o que vem do servidor

         	    		PDU pdu = new PDU();

         	   		this.pdublocks = pdu.stringToBlocks(received,100);				
	
				for (PDU p  : this.pdublocks.values()) {
					byte[] b = p.toByte();
					
          				packet = new DatagramPacket(b,b.length,address,port); 

          				System.out.println("Mensagem recebida do server [" + server.getInetAddress().getHostName() + "]: " + new String(p.getData(), 0, p.getData().length) );
					System.out.println("Tamanho do bloco" + b.length + "numero " + p.getNumSeq());

                			anon.send(packet); //enviar a resposta para o anon
          	       		}         	  
        		}

    		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				System.out.println("Conexão encerrada.");
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

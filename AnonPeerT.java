import java.net.*;
import java.io.*;
import java.util.*;
import java.nio.ByteBuffer;

public class AnonPeerT extends Thread{

	private BufferedReader in; //receber o que vem do server
    	private PrintStream out;  //escrever o que vai po server
    	private Socket server ; //mandar po server
    	private DatagramSocket anon; //tratar do que vem do anon
    	private byte buf[] = new byte[4*1024+200];
	private Map<Integer, PDU> pdublocks;

	public Map<Integer, PDU> stringToBlocks(String s, int size){
   		
		byte buf[] = new byte[4*1024+200];
		buf = s.getBytes();

		Map<Integer, PDU> aux = new HashMap<>();

    		byte value[];
    		int pos = 0;
    		int byteN, i, j, k;

    		for(i = 0; pos < buf.length; pos = (i+1)*size, i++){
			
			
       			byteN = Math.min(size, buf.length-pos);
        		value = new byte[byteN];

        		for(j = pos, k = 0; k < byteN; j++, k++)
            		value[k] = buf[j];
			
			PDU pdu = new PDU(i, value);
        		aux.put(i, pdu);
    		}
			

   		 return aux;
   	}

	

   	public void run(){
    		try {

    			server = new Socket("10.3.3.1",80);

    			InputStream input = server.getInputStream();
            		OutputStream output = server.getOutputStream();

            		this.anon = new DatagramSocket(6666);


	      		this.in = new BufferedReader(new InputStreamReader(input));
        		this.out = new PrintStream(output);

        	

        		while(true){
        			buf = new byte[7*1024+200];

        		
        			DatagramPacket packet = new DatagramPacket(buf, buf.length);
         			anon.receive(packet); // extrair dados que vem do anon
         		        		
         			InetAddress address = packet.getAddress();
         			int port = packet.getPort();

         			//packet = new DatagramPacket(buf,buf.length,address,port);
					
         			String received = new String(packet.getData(), 0, packet.getLength());

         			System.out.println("Mensagem recebida do anonGW ["  + "]: " + received);

         			out.println(received); //enviar a resposta po servidor

         			if("FIM".equals(received)){
					break;
         	    		}        	    

         	    		received = in.readLine(); // ler o que vem do servidor

         	    		//buf = new byte[5*1024+200];
         	    		//buf = received.getBytes();

         	   		this.pdublocks = stringToBlocks(received,500);
				int i = 1;
	
				for (PDU p  : this.pdublocks.values()) {
					byte[] b = p.toByte();
					System.out.println("eyeye");
          				packet = new DatagramPacket(b,b.length,address,port); 

          				System.out.println("Mensagem recebida do server [" + server.getInetAddress().getHostName() + "]: " + new String(p.getData(), 0, p.getData().length) );
					System.out.println("Tamanho do bloco" + b.length + "numero " + i); i++;

                	   		anon.send(packet); //enviar a resposta po anon

          	           }
         	  		
         	  
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

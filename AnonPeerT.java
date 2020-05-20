import java.net.*;
import java.io.*;
import java.util.*;

public class AnonPeerT extends Thread{

	private BufferedReader in; //receber o que vem do server
    private PrintStream out;  //escrever o que vai po server
    private Socket server ; //mandar po server
    private DatagramSocket anon; //tratar do que vem do anon
    private byte buf[] = new byte[4*1024+200];
	private Map<Integer, byte[]> pdublocks;

	public Map<Integer, byte[]> stringToBlocks(String s, int size){
   				 byte buf[] = new byte[4*1024+200];
   				 buf = s.getBytes();

    			Map<Integer, byte[]> aux = new HashMap<>();

    			byte value[];
    			int pos = 0;
    			int byteN, i, j, k;

    			for(i = 0; pos < buf.length; pos = (i+1)*size, i++){
       			 	byteN = Math.min(size, buf.length-pos);
        			value = new byte[byteN];

        		for(j = pos, k = 0; k < byteN; j++, k++)
            		value[k] = buf[j];

        			aux.put(i, value);
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
        		buf = new byte[4*1024+200];

        		
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

         	    //buf = new byte[4*1024+200];
         	    //buf = received.getBytes();

         	    this.pdublocks = stringToBlocks(received,128);

         	    for(byte b[] :this.pdublocks.values()){
         	    
          				packet = new DatagramPacket(b,b.length,address,port); 

          				System.out.println("Mensagem recebida do server [" + server.getInetAddress().getHostName() + "]: " + new String(b, 0, b.length));

                	    anon.send(packet); //enviar a resposta po anon
          	           
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
import java.net.*;
import java.io.*;
import java.util.*;


public class AnonGWT implements Runnable{

	private Socket socket ; //socket que vem do cliente	 
	private DatagramSocket peer; //para enviar po peer
	private String[] anonPeer = {"10.1.1.2","10.4.4.2","10.4.4.3"} ;
	private BufferedReader in;// do q se le do socket do cliente
	private PrintStream out;//para responder ao cliente
	private byte buf[] = new byte[6*1024+200];
	private Map<Integer, byte[]> pdublocks;

	public Map<Integer, byte[]> stringToBlocks(String s, int size){
   		
		byte buf[] = new byte[6*1024+200];
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

	public AnonGWT(Socket c) throws IOException{
	 	this.socket = c;
	}

		  
        public void run(){
   	   	try{
            	    	
		    this.peer  = new DatagramSocket();
				

			Random random = new Random();
			    int num = random.nextInt(2);
			    System.out.println("Numero random: " + num);

			    System.out.println("Numero do peer a qual vai ligar: " + anonPeer[num]);


			    InputStream input = socket.getInputStream();
            	OutputStream output = socket.getOutputStream();

	        	this.in = new BufferedReader(new InputStreamReader(input));
        		this.out = new PrintStream(output);

        		    InetAddress addrPeer = InetAddress.getByName(anonPeer[num]); //para enviar para um dos 3 peers
			    peer.connect(addrPeer, 6666);
			
        while(true){
        			buf = new byte[6*1024+200];
					String mensagem = in.readLine(); //ler o que vem do cliente
 		
					System.out.println("Mensagem recebida do cliente [" + socket.getInetAddress().getHostName() + "]: " + mensagem);

					buf = mensagem.getBytes();

					DatagramPacket packet = new DatagramPacket(buf, buf.length, addrPeer, 6666);
                    
                    peer.send(packet); // enviar o pacote po anonpeer
		
					if("FIM".equals(mensagem)){
						break;
         			}

         			buf = new byte[7*1024+200];
         			int tam=0;
				int i =1;
				
			
			
			
	
				Map<Integer, byte[]> rec = new HashMap<>();

	         		while(true){
				    	
				    	byte byteslidos[] = new byte[7*1024+200];

         				packet = new DatagramPacket(byteslidos,byteslidos.length);
         				peer.receive(packet); //receber pacote do anonpeer
					
					PDU aux = new PDU(byteslidos);
					
					rec.put(aux.getNumSeq(),aux.getData());
					String h = new String(aux.getData());
					
					int valor = aux.getNumSeq();

					System.out.println("numero" + valor);
         				if((tam+packet.getLength()>7*1024+200)) break;

         				System.arraycopy(aux.getData(),0,buf,tam,h.length());
					System.out.println("Tamanho do bloco" + h.length() + "numero " + i); i++;
					
					

         				if(packet.getLength() < 500) break;
					tam+=h.length();
         				System.out.println("Bloco ;"+h + "tam" + tam);
					
					
					}
					
					
					String received = new String(buf,0,buf.length);
				   	System.out.println("Mensagem recebida do peer [" + "]: " + received);
         				out.println(received); //enviar a resposta po cliente

				}

			}catch(Exception e){
				e.printStackTrace();
			}finally{
			    try{
				System.out.println("ConexÃ£o encerrada.");
				in.close();
				out.close();
				socket.close();
				peer.close();
			   }
			   catch(Exception e){
			   	e.printStackTrace();
			   }
			}
        	}
        
}

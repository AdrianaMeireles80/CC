import java.net.*;
import java.io.*;
import java.util.*;


public class AnonGWT implements Runnable{

	private Socket socketCliente ; //socket que vem do cliente	 
	private DatagramSocket peer; // datagramsocket do peer
	private String[] anonPeer = {"10.1.1.2","10.4.4.2","10.4.4.3"} ;
	private BufferedReader in;// para ler socket do cliente
	private PrintStream out;//para escrever no Socket cliente
	private byte buf[] = new byte[5*1024+200];
	

	public AnonGWT(Socket c) throws IOException{
	 	this.socketCliente = c;
	}

		  
    	public void run(){
   	   	try{
				
			this.peer  = new DatagramSocket();	

		  	Random random = new Random();
		    	int num = random.nextInt(2);
		   	System.out.println("Numero random: " + num);
		        System.out.println("Numero do peer a qual vai ligar: " + anonPeer[num]);


		    	InputStream input = socketCliente.getInputStream();
           		OutputStream output = socketCliente.getOutputStream();
	
	       		this.in = new BufferedReader(new InputStreamReader(input));
       			this.out = new PrintStream(output);

        		InetAddress addrPeer = InetAddress.getByName(anonPeer[num]); //para enviar para um dos 3 peers
			peer.connect(addrPeer, 6666); //permite a multiplexagems
			
        		while(true){
        			buf = new byte[5*1024+200];
				String mensagem = in.readLine(); //ler o que vem do cliente
 		
				System.out.println("Mensagem recebida do cliente [" + socketCliente.getInetAddress().getHostName() + "]: " + mensagem);

				buf = mensagem.getBytes();

				DatagramPacket packet = new DatagramPacket(buf, buf.length, addrPeer, 6666);
                    
                    		peer.send(packet); // enviar o pacote para o anonpeer
		
				if("FIM".equals(mensagem)){
					break;
         			}

         			buf = new byte[5*1024+200];
         			int tam=0;				    
	
				Map<Integer, byte[]> rec = new HashMap<>();

	         		while(true){
				    	
				    	byte byteslidos[] = new byte[5*1024+200];

         				packet = new DatagramPacket(byteslidos,byteslidos.length);
         				peer.receive(packet); //receber pacote do anonpeer
					
					PDU aux = new PDU(byteslidos);
					
					rec.put(aux.getNumSeq(),aux.getData());
					String h = new String(aux.getData());
					
					int valor = aux.getNumSeq();

         				if((tam+aux.getData().length >5*1024+200)) break;
         				
					System.out.println("Tamanho do bloco" + aux.getData().length  + "numero " + aux.getNumSeq()); 					    		

         				if(aux.getData().length < 100) break;

					tam+=h.length();	

					System.out.println("Bloco;" + h); 			
					
				}

				//arraylist para armazenar e ordenar os pacotes provenientes do peer por ordem crescente 
				ArrayList<Integer> ord = new ArrayList<>(rec.keySet()); 
				Collections.sort(ord);
				tam=0;
				for(Integer in : ord){
					System.arraycopy(rec.get(in),0,buf,tam,rec.get(in).length);
					tam += rec.get(in).length;
				}

				String received = new String(buf,0,buf.length);
				System.out.println("Mensagem recebida do peer [" + "]: " + received);
         			out.println(received); //enviar a resposta para o socket do cliente					

			}

			}catch(Exception e){
				e.printStackTrace();
			}finally{
			    try{
				System.out.println("Conexão encerrada.");
				in.close();
				out.close();
				socketCliente.close();
				peer.close();
			   }
			   catch(Exception e){
			   	e.printStackTrace();
			   }
		}
     }
        
}

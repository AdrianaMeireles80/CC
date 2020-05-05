// cliente comunica com o AnonGW
//AnonGW comunica com o servidor

import java.net.*;
import java.io.*;

public class Origin{

  private Socket clientSocket;
  private BufferedReader br;
  private DataOutputStream dos;



    public void clientConnection (String address, int port) throws Exception
    {
      

      //criar socket do cliente
      clientSocket = new Socket(address, port);

      //enviar info para o servidor
      dos = new DataOutputStream(clientSocket.getOutputStream());

      //ler info que vem do servidor
      br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));




      dos.close();
      br.close();
      clientSocket.close();



    }

    public void main(String[] args) throws Exception{
      clientConnection("10.3.3.1", 80);
  
    }
}

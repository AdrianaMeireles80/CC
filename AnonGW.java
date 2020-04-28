//tem duas conexões tcp
//faz de servidor quando Origin comunica com ele
//faz de cliente quando comunica com TargetServer

import java.net.*;
import java.io.*;

/*
o Socket 'anonCliente' serve para comunicar com o TargetServer (quando o AnonGW faz de cliente)
o Socket 'anonServidor' e o ServerSocket 'servAnon' serve para comunicar com o Origin (quando o AnonGW faz de servidor)

*/

public class AnonGW{

  private Socket anonCliente, anonServidor;
  private BufferedReader bufferCliente, bufferServidor;
  private DataOutputStream outCliente;
  private ServerSocket servAnon; //servidor
  private PrintStream printServidor;

  //conecta com a Origin
  public void connectOrigin(int port) throws Exception{


    servAnon = new ServerSocket(port);

    anonServidor = servAnon.accept();
    System.out.println("Um cliente ligou se a um anon");

      //enviar dados para o cliente
    printServidor = new PrintStream(anonServidor.getOutputStream());

      //ler dados vindos do cliente
    bufferCliente = new BufferedReader(new InputStreamReader(anonServidor.getInputStream()));

      printServidor.close();
      bufferCliente.close();
      servAnon.close();
      anonServidor.close();
}


//conecta com o Target
public void connectTarget(String address, int port) throws Exception{

      //criar socket do cliente
      anonCliente = new Socket(address, port);

      //enviar info para o servidor
      outCliente = new DataOutputStream(anonCliente.getOutputStream());

      //ler info que vem do servidor
      bufferServidor = new BufferedReader(new InputStreamReader(anonCliente.getInputStream()));

      outCliente.close();
      bufferServidor.close();
      anonCliente.close();
 }
}





//servidor

import java.net.*;
import java.io.*;

public class TargetServer{

  private ServerSocket serverSocket;
  private Socket clientSocket;
  private PrintStream ps;
  private BufferedReader br;


  public void serverConnection(int port) throws Exception{

      //criar socket do Server
      serverSocket = new ServerSocket(port);

      //conectar ao socket do cliente
      clientSocket = serverSocket.accept();
      System.out.println("Connection established");

      //enviar dados para o cliente
      ps = new PrintStream(clientSocket.getOutputStream());

      //ler dados vindos do cliente
      br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));




      ps.close();
      br.close();
      serverSocket.close();
      clientSocket.close();


  }
}



// cliente comunica com o AnonGW
//AnonGW comunica com o servidor

import java.net.*;
import java.io.*;

public class Origin{


  public static void main(String[] args) throws IOException{
    Socket socket = new Socket("10.3.3.1", 80);
    DataInputStream in = new DataInputStream(socket.getInputStream());
    System.out.println(in.readUTF());
    DataOutputStream out = new DataOutputStream((socket.getOutputStream()));
    out.writeUTF("waiting for connection");
    socket.close();
  }

}

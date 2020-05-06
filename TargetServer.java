//servidor

import java.net.*;
import java.io.*;

public class TargetServer{

 public static void main(String[] args) throws IOException{
  ServerSocket servsocket = new ServerSocket(80);
  Socket socket = servsocket.accept();
  DataOutputStream out = new DataOutputStream(socket.getOutputStream());
  out.writeUTF("weleleleleele");
  DataInputStream in = new DataInputStream(socket.getInputStream());
  System.out.println(in.readUTF());
  socket.close();
 }
}

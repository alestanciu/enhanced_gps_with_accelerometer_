package TCP;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;

class TCPServer {
 public static void main(String argv[]) throws Exception {
	 
	 System.out.println("Initiated TCP Server");
	 
  String clientSentence, capitalizedSentence;
  ServerSocket welcomeSocket = new ServerSocket(Integer.parseInt(argv[0]));
  Socket connectionSocket;
  DataInputStream inFromClient;
  DataOutputStream outToClient;
  
  while(true)
  {
  
   connectionSocket = welcomeSocket.accept();
   System.out.println("Client recieved! IP:"+connectionSocket.getInetAddress().getHostAddress());
  
   inFromClient = new DataInputStream(connectionSocket.getInputStream());
   outToClient = new DataOutputStream(connectionSocket.getOutputStream());
   
   try
   {
   while((clientSentence = inFromClient.readLine()) != null)
   {
   System.out.println("["+LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute()+":"+LocalDateTime.now().getSecond()+":"+"]Received: " + clientSentence);
   System.out.println("------------------------");
   //outToClient.writeBytes("\n");
   }
   } catch (Exception e) {}
   
   
  }
   //capitalizedSentence = clientSentence.toUpperCase() + 'n';
   //outToClient.writeBytes(capitalizedSentence);
  
 }
}
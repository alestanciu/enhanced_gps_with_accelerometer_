package TCP;

import java.io.*;
import java.net.*;

class TCPClient {
	
	private static String sentence,modifiedSentence;
	private static BufferedReader inFromUser;
	private static Socket clientSocket;
	private static DataOutputStream outToServer;
	
	
 public static void main(String argv[]) throws Exception {
	 
	System.out.println("Initiated TCP Client");

  inFromUser = new BufferedReader(new InputStreamReader(System.in));
  clientSocket = new Socket("localhost", 6789);
  outToServer = new DataOutputStream(clientSocket.getOutputStream());
 
  while(true)
  {
  sentence = inFromUser.readLine();
  outToServer.writeBytes(sentence + '\n');
  }
  
  //BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  //sentence = inFromUser.readLine();
  //modifiedSentence = inFromServer.readLine();
  //System.out.println("FROM SERVER: " + modifiedSentence);
  
  //clientSocket.close();
 }
 

}
package TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.net.ServerSocket;
import java.net.Socket;
import org.lwjgl.util.vector.Vector3f;

public class TCPMain {
	
	  private static String clientSentence;
	  private static ServerSocket welcomeSocket;
	  private static Socket connectionSocket;
	  private static DataInputStream inFromClient;
	  public boolean isConnected = false;
	  public boolean isReadingFile = false;
	  public boolean isReceiving = false;
	  private static int ctr = 0;
	  FileWriter fle;
	  FileWriter fle2;
	  FileWriter fle3;
	  long old_time;
	  
	  public TCPMain() throws Exception
	  {
		  fle = new FileWriter(new File("acl.dta"),false);
		  fle2 = new FileWriter(new File("gps.dta"),false);
		  fle3 = new FileWriter(new File("acl_string.dta"),false);
		  System.setProperty("org.lwjgl.librarypath", new File("lib/natives").getAbsolutePath());
		  
		  
	  }
	  
	 
	 
	 	public  void  Initialize(int port) throws Exception
	 	{
	 		System.out.println(">>Initiated TCP Server at port:"+port);
			welcomeSocket = new ServerSocket(port);
	 		System.out.println(">>Server initialized , waiting for connection...");
	 		connectionSocket = welcomeSocket.accept();
	 		System.out.println("Client recieved! IP:"+connectionSocket.getInetAddress().getHostAddress());
	 		isConnected = true;
	 		  
	 		inFromClient = new DataInputStream(connectionSocket.getInputStream());
	 		new DataOutputStream(connectionSocket.getOutputStream());
	 		
	 		old_time = System.currentTimeMillis();
	 	}
	 	
	 	  private String to_send = null;
	 	  private String[] data;
	 	  private boolean first_time_data = true;
	 	  private Vector3f acl_old_data = new Vector3f();
	 	  private Vector3f acl_new_data = new Vector3f();
	 	  private float dif_x,dif_y,dif_z;
 		  
 		  public float dif_x_sensibility=0.3f;
 		  public float dif_y_sensibility=0.3f;
 		  public float dif_z_sensibility=1;
	 	
 		  
 		  
	 	public synchronized String ReadData() throws Exception
	 	{
	 		to_send = "";
	 		if ((clientSentence = inFromClient.readLine()) != null)
			   {
	 				isReceiving = true;
				   if(clientSentence.startsWith("acl:"))
				   {
					   data = clientSentence.substring(4).split(",");
					   //System.out.println(data[0].substring(2) + " " + data[1].substring(2) + " " + data[2].substring(2));
		
					   acl_new_data = new Vector3f(Float.parseFloat(data[0].substring(2)),Float.parseFloat(data[1].substring(2)),Float.parseFloat(data[2].substring(2)));
					   if(first_time_data) { acl_old_data = acl_new_data; first_time_data = false; }
					   
					   dif_x = Math.abs(acl_new_data.x - acl_old_data.x);
					   dif_y = Math.abs(acl_new_data.y - acl_old_data.y);
					   dif_z = Math.abs(acl_new_data.z - acl_old_data.z);
					   
					   if(isReadingFile) old_time = 0;
					   
					   if(!isReadingFile) if(dif_x > dif_x_sensibility) { System.out.println("Detection on X axis of :"+dif_x);  fle3.write("Detection on X axis of :"+dif_x+" after "+(System.currentTimeMillis()-old_time)+"ms\n"); old_time=System.currentTimeMillis();}
					   if(!isReadingFile) if(dif_y > dif_y_sensibility) { System.out.println("Detection on Y axis of :"+dif_x);  fle3.write("Detection on Y axis of :"+dif_y+" after "+(System.currentTimeMillis()-old_time)+"ms\n"); old_time=System.currentTimeMillis();}
					   if(!isReadingFile) if(dif_z > dif_z_sensibility) { System.out.println("Detection on Z axis of :"+dif_x);  fle3.write("Detection on Z axis of :"+dif_z+" after "+(System.currentTimeMillis()-old_time)+"ms\n"); old_time=System.currentTimeMillis();}
					   
					   //to_send = acl_new_data.x + " " + acl_new_data.y + " " + acl_new_data.z;
					   
					   if(!isReadingFile) fle.write(acl_new_data.x + " " + acl_new_data.y + " " + acl_new_data.z+"\n");
				   } 
				   if(clientSentence.startsWith("$GPGGA,"))
				   {
					   data = clientSentence.substring(4).split(",");
					   //System.out.println("GPS:"+data[2]+" "+data[3]+" , "+data[4]+" "+data[5]);
					   to_send = data[2]+" "+data[3]+" , "+data[4]+" "+data[5];
					   if(!isReadingFile) 
					   {
						   fle2.write("GPS:"+data[2]+" "+data[3]+" , "+data[4]+" "+data[5]+"\n");
						   if(ctr == 12)
						   {
						   	CloseWriters();
						   	InitiateWriters();
						   	ctr = 0;
						   	System.out.println("Saved data!");
						   }
						   ctr++;
					   }
				   }
			   }
	 		else isReceiving = false;
	 		return to_send;
	 	}
	 	
	 	public void InitiateWriters() throws Exception{
	 		fle = new FileWriter(new File("acl.dta"),true);
			  fle2 = new FileWriter(new File("gps.dta"),true);
			  fle3 = new FileWriter(new File("acl_string.dta"),true);
		}

	 	
	 	public void CloseWriters() throws Exception{
			fle.close();
			fle2.close();
			fle3.close();
			isReceiving = false;
		}
	 	
		public void CloseConnection() throws Exception{
			welcomeSocket.close();
			fle.close();
			fle2.close();
			fle3.close();
			isReceiving = false;
		}
}

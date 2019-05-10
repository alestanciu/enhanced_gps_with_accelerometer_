package Monopoli;

public class TCPThread implements Runnable{

	@Override
	public void run() {
		while(true)
		{
			try
			{
			System.out.println("Salut");
			Thread.sleep(1000);
			} catch(Exception e) {}
		}
	}
	
	

}

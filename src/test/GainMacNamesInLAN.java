package test;

import java.net.*;
import java.io.*;
public class GainMacNamesInLAN 
{ 
   private int startIP4th = 1; 
   private int endIP4th = 255; 
   private int macNums = 0; 
   private int IP4th = 1; 
   private int sleepedExplorerNums = 0; 
   private int explorerNums = 0; 
   private long startExplorerTime = 0L;
   private long endExplorerTime = 0L; 
   private class PrintEndStatus extends Thread { 
        private boolean runFlag = false;
		public void run() { 
		  while (true) { 
			 if (runFlag) {
				if(sleepedExplorerNums == explorerNums) { 
					endExplorerTime = System.currentTimeMillis(); 
					printEndInfo();
					runFlag = false;
					System.exit(1); 
				  }
			 } 
			 else { 
			   sleep(); 
			 } 
		   } 
		}
		public void start(){ 
		   if (!runFlag){
			 runFlag = true;
			 super.start();
		   }
		 } 
		public void sleep() { 
		  try { 
			 Thread.sleep(100000);
		  } catch (InterruptedException e){ } 
		} 
}
  private class IPExploreThread
     extends Thread 
      { 
        private boolean runFlag = false; 
        private String hostname = null;
	    private int nowIP4th = 1; 
	    public void run() { 
//			Date dt = new Date();
//			String filename = String.valueOf(dt.getTime());
//			File f = new File("./"+filename+".txt");
//			FileWriter o = null;
			BufferedWriter out = null;
			/*try{
			  o = new FileWriter(f);
			  out = new BufferedWriter(o);
			}catch (IOException e){
				 System.out.println("创建日志文件失败");
			}*/			
	        while (true) { 
	          if(runFlag){
		          nowIP4th = getNextIP4th(); 
		          if(nowIP4th == 0) { 
		               runFlag = false; 
		               addSleepExplorerNums();
		           }
		           else { 
		              try {
		                 hostname = InetAddress.getByAddress( new byte[]{ -64, -88, 21, (byte)nowIP4th}).getHostName();
		               } catch (UnknownHostException e) { } 
		              if(hostname.length() > 10 && hostname.substring(0, 4).equals("192.")){ 
		            	  
		              } 
		              else { 
						  String temp = hostname + " / 192.168.1." + nowIP4th;
						  if (out != null){
							  try{
							    out.write(temp);	
							  }catch (IOException e){
							  }
						 }
		                 System.out.println( hostname + " / 192.168.1." + nowIP4th); 
			             addMacNums();
		               } 
		            } 
		       } 
		      else{ 
		          sleep(); 
		       } 
	      
		  try{
			out.flush();
		  //out.close();
		  }
		  catch (IOException ee){
		  }
		 } 
      }
   public void start() { 
      if (!runFlag) { 
         runFlag = true;
	     super.start(); 
       } 
    }
   public void sleep() { 
      try { 
         Thread.sleep(1000000); 
       } catch (InterruptedException e){ }
    }
  } //end class 
 private void addMacNums() {
    macNums++; 
  } 
  private void addSleepExplorerNums() { 
    sleepedExplorerNums++; 
   }
  private void printStartInfo(){ 
     System.out.println( "nn********** Welcome to use IP Explorer version1.0 ***********");
     System.out.println( "Start exploring, using " + explorerNums + " explorers...n");
   }
  private void printEndInfo() { 
     System.out.println( "nIP exploring process is finished.nTotal " + macNums + " machines between 192.168.1." + startIP4th + " to 192.168.1." + endIP4th); 
     System.out.println( "Waste time: " + (endExplorerTime - startExplorerTime) / 1000L + " seconds."); 
     System.out.println( "************************* alin&ASS *************************");
   }
 private synchronized int getNextIP4th() { 
    if (IP4th > endIP4th) { 
       return 0; 
     }//我要记住下行这样写是可以的 
	 return IP4th++;
  }/** * Initialize explorer. * @param startAddress start IP's fourth segment * @param endAddress end IP's fourth segment * @param threadNums explorer nums */ 
private void startIPExploreThreadBetween( int startAddress, int endAddress, int threadNums) 
 { 
  startIP4th = startAddress; 
  IP4th = startIP4th; 
  endIP4th = endAddress; 
  int IPNums = endIP4th - startIP4th + 1; 
  if (IPNums < threadNums) 
   {
     explorerNums = IPNums; 
   } 
   else 
   { 
     explorerNums = threadNums; 
   }
   printStartInfo();
   startExplorerTime = System.currentTimeMillis(); 
   for (int i = 0; i < explorerNums; i++) 
    {
      new IPExploreThread().start();
    } 
   new PrintEndStatus().start(); 
  } 
  public static void main(String[] args) 
  { 
    GainMacNamesInLAN alinLAN = new GainMacNamesInLAN();
    if (args.length == 3) 
     { 
       alinLAN.startIPExploreThreadBetween( Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2])); 
     } 
     else 
     { 
       alinLAN.startIPExploreThreadBetween(2, 255, 254); 
     } 
   } 
}

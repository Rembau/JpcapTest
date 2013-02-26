package test;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
 
public class DispalyNetInterface {
	
	
   public static void main(String args[]){
	   try{
		   final NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		   for(int i=0;i<devices.length;i++){
			   NetworkInterface nc=devices[i];
	  
			   String address="";
			   for(int t=0;t<nc.addresses.length;t++){
				   address+="|addresses["+t+"]: "+nc.addresses[t].address.toString();
		      }
			   System.out.println("�?"+i+"个接�?:"+"|name: "+nc.name
+"|loopback: "+nc.loopback+"\r\naddress: "+address);
		}
		 
		}catch(Exception ef){
			ef.printStackTrace();
	 System.out.println("显示 数据失败:  "+ef);
	}
   }
}
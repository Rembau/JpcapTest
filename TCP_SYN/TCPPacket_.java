package TCP_SYN;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import ExceptionT.IPFormatError;
import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.*;

public class TCPPacket_ extends TCPPacket{
	private static NetworkInterface[] devices = JpcapCaptor.getDeviceList();
	public TCPPacket_(int src_port, int dst_port, long sequence, long ack_num,
			boolean urg, boolean ack, boolean psh, boolean rst, boolean syn,
			boolean fin, boolean rsv1, boolean rsv2, int window, int urgent) {
		super(src_port, dst_port, sequence, ack_num, urg, ack, psh, rst, syn, fin,
				rsv1, rsv2, window, urgent);
		init();
	}
	void init(){
		EthernetPacket e=new EthernetPacket();
		e.frametype=EthernetPacket.ETHERTYPE_IP;
		e.src_mac=devices[1].mac_address;
		e.dst_mac=new byte[]{(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};
		this.datalink=e;
	}
	public void setIp(String ip){
		try {
			this.setIPv4Parameter(0,false,false,false,0,false,false,false,0,1010101,255,IPPacket.IPPROTO_TCP,
					InetAddress.getByAddress(Transition.intToByte("192.168.21.132")),
					InetAddress.getByAddress(Transition.intToByte(ip)));
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IPFormatError e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.data="".getBytes();
	}
	private static final long serialVersionUID = 1L;
	public static void main(String args[]){
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		JpcapCaptor jpcap=null;
		try {
			jpcap = JpcapCaptor.openDevice(devices[1], 2000, false, 20);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Dump_TCP_SYN d=new Dump_TCP_SYN(devices[1]);
		new Th(jpcap,d).start();
		try {
			JpcapSender s=JpcapSender.openDevice(devices[1]);
			TCPPacket_ t=null;
			t=new TCPPacket_(237,23,123,0,false,false,false,false,true,false,false,false,1024,0);
			int i=95;
			for(i=1;i<256;i++){
				t.setIp("192.168.21."+i);
				//System.out.println(t);
				s.sendPacket(t);
				s.sendPacket(t);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print(d.getOnlineIP().size()+" "+d.getOnlineIP());
		System.exit(0);
	}
}
class Th extends Thread{
	JpcapCaptor jpcap;
	Dump_TCP_SYN d;
	Th(JpcapCaptor jpcap,Dump_TCP_SYN d){
		this.d=d;
		this.jpcap=jpcap;
	}
	public void run(){
		jpcap.loopPacket(-1, d);
	}
}
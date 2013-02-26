package ICMP;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import ExceptionT.IPFormatError;
import Send.Transition;


import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.EthernetPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;

/**
 * Description:构建ICMP报文...该类可以查看机器是否连网中
 * @author Administrator
 *
 */
public class ICMPPacket_ extends ICMPPacket{
	private static final long serialVersionUID = 1L;
	private static NetworkInterface[] devices = JpcapCaptor.getDeviceList();
	ICMPPacket_(){
		super();
		init();
	}
	void init(){
		this.type=ICMPPacket.ICMP_ECHO;
		EthernetPacket e=new EthernetPacket();
		e.frametype=EthernetPacket.ETHERTYPE_IP;
		e.src_mac=devices[1].mac_address;
		e.dst_mac=new byte[]{(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};
		this.datalink=e;
	}
	void setIP(String ip){
		try {
			this.setIPv4Parameter(0,false,false,false,0,false,false,false,0,1010101,10,IPPacket.IPPROTO_ICMP,
					InetAddress.getByAddress(Transition.intToByte("192.168.21.132")),
					InetAddress.getByAddress(Transition.intToByte(ip)));
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IPFormatError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.data="1234".getBytes();
	}
	public static void main(String[] args) {
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		JpcapCaptor jpcap=null;
		try {
			jpcap = JpcapCaptor.openDevice(devices[1], 2000, false, 20);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Dump_ICMP d=new Dump_ICMP(devices[1]);
		System.out.println("=========");
		new Th(jpcap,d).start();
		System.out.println("=========");
		try {
			JpcapSender s=JpcapSender.openDevice(devices[1]);
			ICMPPacket_ icmp=new ICMPPacket_();
			int i=255;
			//for(i=1;i<256;i++){
				icmp.setIP("192.168.2."+i);
				s.sendPacket(icmp);
			//}
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
	Dump_ICMP d;
	Th(JpcapCaptor jpcap,Dump_ICMP d){
		this.d=d;
		this.jpcap=jpcap;
	}
	public void run(){
		jpcap.loopPacket(-1, d);
	}
}

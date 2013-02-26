package ARP;

import java.io.IOException;

import ExceptionT.IPFormatError;
import Send.Transition;

import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.ARPPacket;
import jpcap.packet.EthernetPacket;

public class ARPPacket_ extends ARPPacket{
	private static final long serialVersionUID = 1L;
	private static NetworkInterface[] devices = JpcapCaptor.getDeviceList();
	public ARPPacket_(){
		super();
		init();
	}
	void init(){
		this.hardtype = 1;
		this.prototype = 2048;
		this.operation = 1;
		this.hlen = 6;
		this.plen = 4;
		this.sender_hardaddr = devices[1].mac_address;
		this.sender_protoaddr = devices[1].addresses[0].address.getAddress();
		this.target_hardaddr = new byte[]{(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};
		
	    EthernetPacket ether = new EthernetPacket();
	    ether.frametype = 2054;
	    ether.src_mac = devices[1].mac_address;
	    ether.dst_mac = new byte[]{(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};
	    this.datalink = ether;
	}
	void setIP(String ip){
		try {
			this.target_protoaddr=Transition.intToByte(ip);
		} catch (IPFormatError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		try {
			ARPPacket_ arp=new ARPPacket_();
			JpcapSender s=JpcapSender.openDevice(devices[1]);
			int i=95;
			for(i=1;i<255;i++){
				arp.setIP("192.168.21."+i);
				s.sendPacket(arp);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Dump_ARP a=new Dump_ARP(devices[1]);
		new Th1(jpcap,a).start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print(a.getOnlineIP().size()+" "+a.getOnlineIP());
	}
}
class Th1 extends Thread{
	JpcapCaptor jpcap;
	Dump_ARP d;
	Th1(JpcapCaptor jpcap,Dump_ARP d){
		this.d=d;
		this.jpcap=jpcap;
	}
	public void run(){
		jpcap.loopPacket(-1, d);
	}
}
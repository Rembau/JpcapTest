package test;

import java.net.InetAddress;

import Send.Transition;

import jpcap.JpcapCaptor;
import jpcap.packet.*;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.ICMPPacket;
public class ICMPP_ {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		try {
			JpcapSender s=JpcapSender.openDevice(devices[1]);
			ICMPPacket p=new ICMPPacket();
			p.type=ICMPPacket.ICMP_ECHO;
			p.setIPv4Parameter(0,false,false,false,0,false,false,false,0,1010101,10,IPPacket.IPPROTO_ICMP,
					InetAddress.getByAddress(Transition.intToByte("192.168.21.132")),
					InetAddress.getByAddress(Transition.intToByte("192.168.2.1")));
			p.data="abcdabcdabcdabcdabcdabcdabcdabcd".getBytes();
			EthernetPacket e=new EthernetPacket();
			e.frametype=EthernetPacket.ETHERTYPE_IP;
			e.src_mac=devices[1].mac_address;
			e.dst_mac=new byte[]{(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};
			p.datalink=e;
			for(int i=0;i<1;i++){
				s.sendPacket(p);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

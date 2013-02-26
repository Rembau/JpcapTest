package igmp;

import java.io.IOException;
import java.net.InetAddress;

import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.EthernetPacket;
import jpcap.packet.IPPacket;
import ExceptionT.IPFormatError;
import Send.Transition;

public class IGMPPacket_ extends IPPacket{
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		IGMPPacket_ i=null;
		try {
			JpcapSender s=JpcapSender.openDevice(devices[1]);
			try {
				i=new IGMPPacket_();
				EthernetPacket e=new EthernetPacket();
				e.frametype=EthernetPacket.ETHERTYPE_IP;
				e.src_mac=devices[1].mac_address;
				e.dst_mac=new byte[]{(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};
				i.datalink=e;
				i.setIPv4Parameter(0,false,false,false,0,false,false,false,0,1010101,1,IPPacket.IPPROTO_IGMP,
						InetAddress.getByAddress(Transition.intToByte("192.168.21.132")),
						InetAddress.getByAddress(Transition.intToByte("224.0.0.5")));
			} catch (IPFormatError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte b1= new Integer(18).byteValue();
			byte b2= new Integer(0).byteValue();
			byte b3= new Integer(0).byteValue();
			byte b4= new Integer(0).byteValue();
			
			byte b5= new Integer(224).byteValue();
			byte b6= new Integer(0).byteValue();
			byte b7= new Integer(0).byteValue();
			byte b8= new Integer(6).byteValue();
			i.data=new byte[]{b1,b2,b3,b4,b5,b6,b7,b8};
			s.sendPacket(i);
			System.out.println(i);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

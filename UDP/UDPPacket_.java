package UDP;

import java.io.IOException;
import java.net.InetAddress;

import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.EthernetPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.UDPPacket;
import ExceptionT.IPFormatError;
import Send.Transition;

public class UDPPacket_ extends UDPPacket{
	public UDPPacket_(int src_port, int dst_port) {
		super(src_port, dst_port);
	}

	private static final long serialVersionUID = 1L;

	public static void main(String args[]){
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		try {
			JpcapSender s=JpcapSender.openDevice(devices[1]);
			UDPPacket_ i=null;
			try {
				i=new UDPPacket_(60,3389);
				EthernetPacket e=new EthernetPacket();
				e.frametype=EthernetPacket.ETHERTYPE_IP;
				e.src_mac=devices[1].mac_address;
				e.dst_mac=new byte[]{(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};
				i.datalink=e;
				i.setIPv4Parameter(0,false,false,false,0,false,false,false,0,1010101,255,IPPacket.IPPROTO_UDP,
						InetAddress.getByAddress(Transition.intToByte("192.168.21.132")),
						InetAddress.getByAddress(Transition.intToByte("192.168.21.1")));
			} catch (IPFormatError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i.data="".getBytes();
			System.out.println(i);
				s.sendPacket(i);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

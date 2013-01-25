package Send;

import java.io.IOException;
import java.net.InetAddress;

import ExceptionT.IPFormatError;

import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.EthernetPacket;
import jpcap.packet.*;

public class TCPPacket_ extends TCPPacket{

	public TCPPacket_(int src_port, int dst_port, long sequence, long ack_num,
			boolean urg, boolean ack, boolean psh, boolean rst, boolean syn,
			boolean fin, boolean rsv1, boolean rsv2, int window, int urgent) {
		super(src_port, dst_port, sequence, ack_num, urg, ack, psh, rst, syn, fin,
				rsv1, rsv2, window, urgent);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;
	public static void main(String args[]){
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		try {
			JpcapSender s=JpcapSender.openDevice(devices[1]);
			TCPPacket_ i=null;
			try {
				i=new TCPPacket_(3389,3389,123,0,false,false,false,false,true,false,false,false,1024,0);
				EthernetPacket e=new EthernetPacket();
				e.frametype=EthernetPacket.ETHERTYPE_IP;
				e.src_mac=devices[1].mac_address;
				e.dst_mac=new byte[]{(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};
				i.datalink=e;
				i.setIPv4Parameter(0,false,false,false,0,false,false,false,0,1010101,255,IPPacket.IPPROTO_TCP,
						InetAddress.getByAddress(Transition.intToByte("192.168.21.132")),
						InetAddress.getByAddress(Transition.intToByte("192.168.21.95")));
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

package rip;

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
				i=new UDPPacket_(520,520);
				EthernetPacket e=new EthernetPacket();
				e.frametype=EthernetPacket.ETHERTYPE_IP;
				e.src_mac=devices[1].mac_address;
				e.dst_mac=new byte[]{(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};
				i.datalink=e;
				i.setIPv4Parameter(0,false,false,false,0,false,false,false,0,1010101,255,IPPacket.IPPROTO_UDP,
						InetAddress.getByAddress(Transition.intToByte("192.168.21.132")),
						InetAddress.getByAddress(Transition.intToByte("192.168.0.0")));
			} catch (IPFormatError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte b1=new Integer(1).byteValue();
			byte b2=new Integer(1).byteValue();
			byte b3=new Integer(0).byteValue();
			byte b4=new Integer(0).byteValue();
			
			byte b5=new Integer(0).byteValue();
			byte b6=new Integer(0).byteValue();
			byte b7=new Integer(0).byteValue();
			byte b8=new Integer(0).byteValue();
			
			byte b9=new Integer(192).byteValue();
			byte b10=new Integer(168).byteValue();
			byte b11=new Integer(21).byteValue();
			byte b12=new Integer(132).byteValue(); //ip
			
			byte b13=new Integer(0).byteValue();
			byte b14=new Integer(0).byteValue();
			byte b15=new Integer(0).byteValue();
			byte b16=new Integer(0).byteValue();
			
			byte b17=new Integer(0).byteValue();
			byte b18=new Integer(0).byteValue();
			byte b19=new Integer(0).byteValue();
			byte b20=new Integer(16).byteValue();  //∂»¡ø
			i.data=new byte[]{
					b1,b2,b3,b4,
					b5,b6,b7,b8,
					b9,b10,b11,b12,
					b13,b14,b15,b16,
					b13,b14,b15,b16,
					b17,b18,b19,b20};//"".getBytes();
			System.out.println(i);
				s.sendPacket(i);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

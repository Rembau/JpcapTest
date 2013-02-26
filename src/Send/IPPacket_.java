package Send;

import java.io.IOException;
import java.net.InetAddress;

import ExceptionT.IPFormatError;


import jpcap.*;
import jpcap.packet.IPPacket;
/*
d_flag - IP flag bit: [D]elay
t_flag - IP flag bit: [T]hrough
r_flag - IP flag bit: [R]eliability
rsv_tos - Type of Service (TOS)
priority - Priority
rsv_frag - Fragmentation Reservation flag
dont_frag - Don't fragment flag
more_frag - More fragment flag
offset - Offset
ident - Identifier
ttl - Time To Live
protocol - Protocol 
This value is ignored when this packets inherits a higher layer protocol(e.g. TCPPacket)
src - Source IP address
dst - Destination IP address*/
public class IPPacket_ extends IPPacket {

	private static final long serialVersionUID = 1L;
	int priority;
    boolean d_flag;
    boolean t_flag;
    boolean r_flag;
    int rsv_tos;
    boolean rsv_frag;
    boolean dont_frag;
    boolean more_frag;
    int offset;
    int ident;
    int ttl;
    int protocol;
    java.net.InetAddress src;
    java.net.InetAddress dst;
	public IPPacket_(int priority,boolean d_flag,boolean t_flag,boolean r_flag,
			int rsv_tos,boolean rsv_frag,boolean dont_frag,
            boolean more_frag,int offset,int ident,int ttl,int protocol,
            java.net.InetAddress src,java.net.InetAddress dst){
		this.priority=priority;
		this.d_flag=d_flag;
		this.t_flag=t_flag;
		this.r_flag=r_flag;
		this.rsv_frag=rsv_frag;
		this.rsv_tos=rsv_tos;
		this.dont_frag=dont_frag;
		this.more_frag=more_frag;
		this.offset=offset;
		this.ident=ident;
		this.ttl=ttl;
		this.protocol=protocol;
		this.src=src;
		this.dst=dst;
		setIPv4Parameter(priority, d_flag,t_flag,r_flag, rsv_tos, rsv_frag, 
				dont_frag, more_frag, offset, ident, ttl, protocol, src, dst);
	}
	public static void main(String args[]){
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		try {
			JpcapSender s=JpcapSender.openDevice(devices[1]);
			IPPacket_ i=null;
			try {
				i = new IPPacket_(0,false,false,false,0,false,false,false,0,0,255,230,
						InetAddress.getByAddress(Transition.intToByte("192.168.21.1")),
						InetAddress.getByAddress(Transition.intToByte("192.168.21.95")));
			} catch (IPFormatError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i.data="123".getBytes();
			while(true){
				s.sendPacket(i);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

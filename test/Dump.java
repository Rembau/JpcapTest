package test;

import jpcap.*;
import jpcap.packet.*;

class Dump implements PacketReceiver {
	private String localHost;
	Dump(NetworkInterface networkInterface){
		this.localHost=networkInterface.addresses[0].address.toString();
		System.out.println(this.localHost);
	}
	public void receivePacket(Packet packet) {
		//System.out.println(packet.sec+"  "+packet.usec);
		if(getType(packet).equals("UDP"))
		{
			System.out.println(packet);
			System.out.println(((UDPPacket)packet).datalink);
			for(int i=0;i<((UDPPacket)packet).data.length;i++){
				System.out.print(Integer.toBinaryString(new Byte(((UDPPacket)packet).data[i]).intValue()));	
			}
			System.out.println(((UDPPacket)packet).dst_ip.toString());
		}
		/*if(getType(packet).equals("TCP")&& Transition.byteToString(((IPPacket)packet).dst_ip).equals("/192.168.21.95")){
			System.out.println(packet);
		}*/
		
	}
	public void printP(String p,Packet packet){//打印协议详细信息
		//System.out.println(p);
		if(p.equals("TCP")){
			printTCP(packet);
		}
		/*else if(p.equals("UDP")){
			printUDP(packet);
		}*/
	}
	public void printTCP(Packet packet){   //打印tcp
		TCPPacket tcpP=(TCPPacket)packet;
		EthernetPacket eP=(EthernetPacket)packet.datalink;
		System.out.println("源IP:"+tcpP.src_ip+" 源端口:"+tcpP.src_port+" 目的IP:"+tcpP.dst_ip+"目的端口:"+tcpP.dst_port);
		System.out.println("源MAC地址:"+eP.getSourceAddress()+" 目的MAC地址:"+eP.getDestinationAddress());
		System.out.println("数据: \n");
		for(int i=0;i<tcpP.data.length;i++){
			System.out.print((char)tcpP.data[i]);
		}
		System.out.println();
	}
	public void printUDP(Packet packet){   //打印udp
		UDPPacket udpP=(UDPPacket)packet;
		EthernetPacket eP=(EthernetPacket)packet.datalink;
		System.out.println("源IP:"+udpP.src_ip+" 源端口:"+udpP.src_port+" 目的IP:"+udpP.dst_ip+"目的端口:"+udpP.dst_port);
		System.out.println("源MAC地址:"+eP.getSourceAddress()+" 目的MAC地址:"+eP.getDestinationAddress());
		System.out.println("数据: \n");
		for(int i=0;i<udpP.data.length;i++){
			System.out.print((char)udpP.data[i]);
		}
	}
	public String getType(Packet packet){  //获取协议类型
		String type=null;
		if(packet instanceof TCPPacket){
			type="TCP";
		}
		else if(packet instanceof UDPPacket){
			type="UDP";
		}
		else if(packet instanceof ARPPacket){
			type="ARP";
		}
		else if(packet instanceof ICMPPacket){
			type="ICMP";
		}
		else if(packet instanceof IPPacket){
			type="IP";
		}
		else type="未知";
		return type;
	}
	
	public void printAllByte(Packet packet){    //打印包所有字节
		System.out.println(packet.caplen);
		int i=0;
		for(i=0;i<packet.data.length;i++){
			System.out.print(packet.data[i]+"_");
		}
		System.out.println();
		System.out.println(i);
		for(i=0;i<packet.header.length;i++){
			System.out.print(packet.header[i]+"_");
		}
		System.out.println();
		System.out.println(i);
	}
	public static void print(NetworkInterface []devices){   //打印驱动 信息
		System.out.println("usage: java Tcpdump <select a number from the following>");
		
		for (int i = 0; i < devices.length; i++) {
			System.out.println(i+" :"+devices[i].name + "(" + devices[i].description+")");
			System.out.println("    data link:"+devices[i].datalink_name + "("
					+ devices[i].datalink_description+")");
			System.out.print("    MAC address:");
			for (byte b : devices[i].mac_address)
				System.out.print(Integer.toHexString(b&0xff) + ":");
			System.out.println();
			for (NetworkInterfaceAddress a : devices[i].addresses)
				System.out.println("    address:"+a.address + " " + a.subnet + " "
						+ a.broadcast);
		}
	}
	public static void main(String[] args) throws Exception {
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		print(devices);
		JpcapCaptor jpcap = JpcapCaptor.openDevice(devices[1], 2000, true, 20);
		Dump d=new Dump(devices[1]);
		jpcap.loopPacket(-1, d);
	}
}

package filter;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;
import jpcap.*;
import jpcap.packet.*;

class Dump implements PacketReceiver {
	File f=new File("f:/text.txt");
	DataOutputStream ds;
	Dump(){
		try {
			ds=new DataOutputStream(new FileOutputStream(f));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void receivePacket(Packet packet) {
		printP(getType(packet),packet);   //打印协议详细信息
		//System.out.println(packet.sec+"  "+packet.usec);
		//System.out.println(packet);
	}
	public void printP(String p,Packet packet){//打印协议详细信息
		//System.out.println(p);
		/*if(!p.equals("ARP")){
			System.out.println(packet);
		}*/
		/*if(p.equals("ICMP")){
			System.out.println(packet);
			DBoperate_.insert("insert into r value('"+packet+"')");
		}*/
		if(p.equals("TCP") || p.equals("UDP")){
			printIP(packet);
		}
		/*
		else if(p.equals("UDP")){
			printUDP(packet);
		}*/
		
		/*if(p.equals("TCP")){
			printTCP(packet);
		}*/
	}
	public void printIP(Packet packet){
		IPPacket ipP=(IPPacket)packet;
		if(ipP.option!=null)
		{
			System.out.println((ipP.options));
			System.out.println(ipP);
		}
	}
	public void printTCP(Packet packet){   //打印tcp
		TCPPacket tcpP=(TCPPacket)packet;
		EthernetPacket eP=(EthernetPacket)packet.datalink;
		/*if(tcpP.dst_port==808 || tcpP.src_port==808 || tcpP.dst_port==80 || tcpP.src_port==80){
			return;
		}*/
		if(tcpP.dst_port!=80 && tcpP.src_port!=80){
			return;
		}
		/*if(!tcpP.src_ip.toString().equals("/172.16.2.14") && !tcpP.dst_ip.toString().equals("/172.16.2.14")){
			return;
		}*/
		Calendar ca=Calendar.getInstance();
		int h=ca.get(Calendar.HOUR_OF_DAY);
		int m=ca.get(Calendar.MINUTE);
		int s=ca.get(Calendar.SECOND);
		System.out.println(h+":"+m+":"+s);
		System.out.println("源IP:"+tcpP.src_ip+" 源端口:"+tcpP.src_port+" 目的IP:"+tcpP.dst_ip+"目的端口:"+tcpP.dst_port);
		System.out.println("源MAC地址:"+eP.getSourceAddress()+" 目的MAC地址:"+eP.getDestinationAddress());
		System.out.println("数据长度："+tcpP.data.length);
		System.out.println("数据: \n");
		for(int i=0;i<tcpP.data.length;i++){
			System.out.print((char)tcpP.data[i]);
		}
		System.out.println();
	}
	public void printUDP(Packet packet){   //打印udp
		UDPPacket udpP=(UDPPacket)packet;
		EthernetPacket eP=(EthernetPacket)packet.datalink;
		if(udpP.src_port!=520 && udpP.dst_port!=520)
			return;
		System.out.println("源IP:"+udpP.src_ip+" 源端口:"+udpP.src_port+" 目的IP:"+udpP.dst_ip+"目的端口:"+udpP.dst_port);
		System.out.println("源MAC地址:"+eP.getSourceAddress()+" 目的MAC地址:"+eP.getDestinationAddress());
		System.out.println("数据: \n");
		for(int i=0;i<udpP.data.length;i++){
			System.out.print(udpP.data[i]+"  ");
		}
		System.out.println();
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
	public void print(NetworkInterface []devices){   //打印驱动 信息
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
		JpcapCaptor jpcap = JpcapCaptor.openDevice(devices[1], 2000, true, 20);
		Dump d=new Dump();
		jpcap.loopPacket(-1, d);
	}
}

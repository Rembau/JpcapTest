package TCP_SYN;

import java.net.InetAddress;

import ExceptionT.IPFormatError;


/**
 * 
 * Description:把字符串ip地址转化为长度为4的字节数组
 *
 */
public class Transition {
	public static String byteToString(InetAddress i){
		/*System.out.println(i.toString());
		StringBuffer sb=new StringBuffer();
		byte[] b=i.getAddress();
		for(int j=0;j<4;j++){
			int x=new Byte(b[j]).intValue();
			if(x>=0){
				sb.append(x);
			}
			else {
				sb.append(256+x);
			}
			if(j!=3){
				sb.append(".");
			}
		}*/
		return i.toString();
	}
	public static byte[] intToByte(String ip) throws IPFormatError {
		byte b[]=new byte[4];
		String str[]=ip.split("\\.");
		if(str.length!=4){
			throw new IPFormatError();
		}
		try{
			for(int i=0;i<4;i++){
				b[i]=new Integer(str[i]).byteValue();
			}
		}catch(Exception e){
			throw new IPFormatError();
		}
		return b;
	}
}

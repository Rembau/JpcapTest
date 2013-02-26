package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import conn.DBoperate_;

public class HandlePingPacket {
	LinkedList<String> p221 =new LinkedList<String>();
	LinkedList<String> p202 =new LinkedList<String>();
	public HandlePingPacket(){
		getDataList();
		//handle();
	}
	public void getDataList(){
		ResultSet rs=DBoperate_.select("select * from r");
		try {
			int ss202=0,es202=0;
			int sms202=0,ems202=0;
			boolean mark202=false;
			int ss221=0,es221=0;
			int sms221=0,ems221=0;
			boolean mark221=false;
			while(rs.next()){
				String str = rs.getString(1);
				//System.out.println(str);
				if(str.indexOf("202.102")!=-1){
					String strs[]=str.split(" ");
					if(strs[1].startsWith("/192")){
						if(mark202){
							DBoperate_.insert("insert into t value(0,202)");
						}
						String t[]=strs[0].split(":");
						ss202=Integer.valueOf(t[0]);
						sms202=Integer.valueOf(t[1]);
						mark202=true;
					} else{
						String t[]=strs[0].split(":");
						es202=Integer.valueOf(t[0]);
						ems202=Integer.valueOf(t[1]);
						mark202=false;
						long t1=1000000*(es202-ss202)+(ems202-sms202);
						System.out.println(t1);
						//System.out.println(es202+" "+ems202+"-"+ss202+" "+sms202);
						DBoperate_.insert("insert into t value('"+t1+"',202)");
					}

				}else if(str.indexOf("221.130")!=-1){
					String strs[]=str.split(" ");
					if(strs[1].startsWith("/192")){
						if(mark221){
							DBoperate_.insert("insert into t value(0,221)");
						}
						String t[]=strs[0].split(":");
						ss221=Integer.valueOf(t[0]);
						sms221=Integer.valueOf(t[1]);
						mark221=true;
					} else{
						String t[]=strs[0].split(":");
						es221=Integer.valueOf(t[0]);
						ems221=Integer.valueOf(t[1]);
						mark221=false;
						long t1=1000000*(es221-ss221)+(ems221-sms221);
						System.out.println(t1);
						//System.out.println(es221+" "+ems221+"-"+ss221+" "+sms221);
						DBoperate_.insert("insert into t value('"+t1+"',221)");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				rs.getStatement().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void handle(){
		h(p221,221);
		h(p202,202);
	}
	public void h(LinkedList<String> p,int ip){
		int ss=0,es=0;
		int sms=0,ems=0;
		boolean mark=false;
		for(String str:p){
			String strs[]=str.split(" ");
			if(strs[1].startsWith("/192")){
				if(mark){
					DBoperate_.insert("insert into t value(0,'"+ip+"')");
					continue;
				}
				String t[]=strs[0].split(":");
				ss=Integer.valueOf(t[0]);
				sms=Integer.valueOf(t[1]);
				mark=true;
			} else{
				String t[]=strs[0].split(":");
				es=Integer.valueOf(t[0]);
				ems=Integer.valueOf(t[1]);
				mark=false;
			}
			long t=1000000*(es-ss)+(ems-sms);
			DBoperate_.insert("insert into t value('"+t+"','"+ip+"')");
		}
	}
	public static void main(String[] args) {
		new HandlePingPacket();
	}
}

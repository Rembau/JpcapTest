package statsFlow;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class LoadToFile {
	public static File file;
	public static DataOutputStream ds;
	static int count=1;
	static {
		initFile();
	}
	public static void initFile(){
		int year,month,day,hour,mi,second;
		Calendar ca = Calendar.getInstance();
		year=ca.get(Calendar.YEAR);
		month=ca.get(Calendar.MONTH)+1;
		day=ca.get(Calendar.DATE);
		hour=ca.get(Calendar.HOUR_OF_DAY);
		mi=ca.get(Calendar.MINUTE);
		second=ca.get(Calendar.SECOND);
		String name = year+"-"+month+"-"+day+"_"+hour+"."+mi+"."+second;
		file = new File(name);
		try {
			ds = new DataOutputStream(new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void loadData(String data,String data2){
		if(count>3600){
			initFile();
			count=1;
		} else {
			count++;
		}
		int length = data.length();
		if(length<20){
			for(int i=20-length;i>0;i--){
				data+=" ";
			}
		}
		try {
			ds.writeBytes(data+":"+data2+"\t\n");
		} catch (IOException e) {
			try {
				ds=new DataOutputStream(new FileOutputStream(file));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} 
			e.printStackTrace();
		}
	}
}

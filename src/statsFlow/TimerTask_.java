package statsFlow;

import java.util.TimerTask;

public class TimerTask_ extends TimerTask{
	Dump d;
	String ip;
	public TimerTask_(Dump d){
		this.d=d;
	}
	public void run() {
		System.out.println(d.getInCountByte()+"×Ö½Ú; "+d.getInCountByte()/1024.0+"kb/s" +" "+d.getOutCountByte()/1024.0+"kb/s");
		LoadToFile.loadData(d.getInCountByte()/1024.0+"kb/s", d.getOutCountByte()/1024.0+"kb/s");
		d.setInCountByte(0);
		d.setOutCountByte(0);
	}

}

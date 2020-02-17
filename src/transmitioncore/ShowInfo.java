package transmitioncore;

import java.net.*;
import java.util.Calendar;

public class ShowInfo {
	/*
	 * 这个是用来显示服务器基本信息的类 所有的函数必须是static 使用的时候不需要再建立对象浪费计算力
	 */

	public static void showNetInfo() {
		String server_hostnameString, server_IPAddressString;
		try {
			InetAddress serverAddress = InetAddress.getLocalHost();// show information of server
			server_hostnameString = serverAddress.getHostName();
			server_IPAddressString = serverAddress.getHostAddress();
			System.out.println("server hostname is :   " + server_hostnameString);
			System.out.println("Server IP address is : " + server_IPAddressString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 这个打算后期通过读取json文件的方式进行升级，现在先不写
	public static void showServerInfo() {

	}

	/*
	 * 获取现在系统时间的函数
	 */
	public static String getTime() {
		String time=null;
		Calendar myCalendar = Calendar.getInstance();
		int year = myCalendar.get(Calendar.YEAR);
		int month = myCalendar.get(Calendar.MONTH);
		int date = myCalendar.get(Calendar.DATE);
		int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
		int minute = myCalendar.get(Calendar.MINUTE);
		int second = myCalendar.get(Calendar.SECOND);
		time=year+"/"+month+"/"+date+"/"+hour+":"+minute+":"+second;
		return time;
	}

}

package transmitioncore;

import java.net.*;
import java.util.Calendar;

public class ShowInfo {
	/*
	 * �����������ʾ������������Ϣ���� ���еĺ���������static ʹ�õ�ʱ����Ҫ�ٽ��������˷Ѽ�����
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

	// ����������ͨ����ȡjson�ļ��ķ�ʽ���������������Ȳ�д
	public static void showServerInfo() {

	}

	/*
	 * ��ȡ����ϵͳʱ��ĺ���
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

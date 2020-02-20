package transmitioncore;

import java.net.*;
import java.util.Calendar;
import java.util.Enumeration;

public class ShowInfo {
	/*
	 * �����������ʾ������������Ϣ���� ���еĺ���������static ʹ�õ�ʱ����Ҫ�ٽ��������˷Ѽ�����
	 */

	public static void showNetInfo() {
		String server_hostnameString, server_IPAddressString;
		try {
			InetAddress serverAddress = InetAddress.getLocalHost();// show information of server
			server_hostnameString = serverAddress.getHostName();
			server_IPAddressString = ShowInfo.getIpAddress();
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
		String time = null;
		Calendar myCalendar = Calendar.getInstance();
		int year = myCalendar.get(Calendar.YEAR);
		int month = myCalendar.get(Calendar.MONTH);
		int date = myCalendar.get(Calendar.DATE);
		int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
		int minute = myCalendar.get(Calendar.MINUTE);
		int second = myCalendar.get(Calendar.SECOND);
		time = year + "/" + month + "/" + date + "/" + hour + ":" + minute + ":" + second;
		return time;
	}

	/*
	 * ��ȡСʱ�ĺ���
	 */
	public static int getHour() {
		Calendar myCalendar = Calendar.getInstance();
		int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
		return hour;
	}

	/*
	 * ��ȡʱ��ĺ���
	 */
	public static String getTime_day() {
		Calendar myCalendar = Calendar.getInstance();
		int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
		int minute = myCalendar.get(Calendar.MINUTE);
		int second = myCalendar.get(Calendar.SECOND);
		String time = ("" + hour) + (":" + minute) + (":" + second);
		return time;
	}

	/*
	 * ��ȡ����ϵͳ���� ĳЩʱ����Ҫ�������
	 */
	public static String getSystemType() {
		String system_type = System.getProperties().getProperty("os.name");
		return system_type;
	}

	/*
	 * ��ȡ����ip��ַ�ķ��� ����ip��ַ ��Ϊ�ϱ�showNetInfo����ֻ������windows ������д��һ�ַ�����д��ȡip��ַ
	 */
	public static String getIpAddress() {
		String sysType = ShowInfo.getSystemType();
		String ip_local;
		if (sysType.toLowerCase().startsWith("win")) { // �����Windowsϵͳ����ȡ����IP��ַ
			try {
				ip_local = InetAddress.getLocalHost().getHostAddress();
				return ip_local;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			// �������������һ�ֻ�ȡip��ַ�ķ���
			ip_local=getUnknowDevIpAddr();
			return ip_local;
		}
	}

	/*
	 * ͨ���������������豸��ȡIP��ַ�ķ���
	 */
	public static String getUnknowDevIpAddr() {
		String ip_local = null;
		InetAddress ip;
		Enumeration<InetAddress> addrs;
		Enumeration<NetworkInterface> networks = null;
		try {
			networks = NetworkInterface.getNetworkInterfaces();
		} catch (Exception e) {
			e.printStackTrace();
		}
		while (networks.hasMoreElements()) {
			NetworkInterface ni = networks.nextElement();
			try {
				// ���˵� loopback�豸����������
				if (!ni.isUp() || ni.isLoopback() || ni.isVirtual()) {
					continue;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			addrs = ni.getInetAddresses();
			if (addrs == null) {
				System.out.println("InetAddress is null");
				continue;
			}
			// ����InetAddress��Ϣ
			while (addrs.hasMoreElements()) {
				ip = addrs.nextElement();
				if (!ip.isLoopbackAddress() && ip.isSiteLocalAddress() && ip.getHostAddress().indexOf(":") == -1) {
					try {
						ip_local = ip.toString().split("/")[1];
					} catch (ArrayIndexOutOfBoundsException e) {
						ip_local = null;
					}
				}
			}
		}
		return ip_local;
	}

}

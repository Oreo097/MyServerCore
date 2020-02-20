package transmitioncore;

import java.net.*;
import java.util.Calendar;
import java.util.Enumeration;

public class ShowInfo {
	/*
	 * 这个是用来显示服务器基本信息的类 所有的函数必须是static 使用的时候不需要再建立对象浪费计算力
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

	// 这个打算后期通过读取json文件的方式进行升级，现在先不写
	public static void showServerInfo() {

	}

	/*
	 * 获取现在系统时间的函数
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
	 * 获取小时的函数
	 */
	public static int getHour() {
		Calendar myCalendar = Calendar.getInstance();
		int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
		return hour;
	}

	/*
	 * 获取时间的函数
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
	 * 获取操作系统类型 某些时候需要这个功能
	 */
	public static String getSystemType() {
		String system_type = System.getProperties().getProperty("os.name");
		return system_type;
	}

	/*
	 * 获取本机ip地址的方法 返回ip地址 因为上边showNetInfo方法只适用于windows 所以新写了一种方法来写获取ip地址
	 */
	public static String getIpAddress() {
		String sysType = ShowInfo.getSystemType();
		String ip_local;
		if (sysType.toLowerCase().startsWith("win")) { // 如果是Windows系统，获取本地IP地址
			try {
				ip_local = InetAddress.getLocalHost().getHostAddress();
				return ip_local;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			// 这里打算用另外一种获取ip地址的方法
			ip_local=getUnknowDevIpAddr();
			return ip_local;
		}
	}

	/*
	 * 通过遍历所有网卡设备获取IP地址的方法
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
				// 过滤掉 loopback设备、虚拟网卡
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
			// 遍历InetAddress信息
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

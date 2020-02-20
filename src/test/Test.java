package test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import transmitioncore.Message;
import transmitioncore.ServerCore;

public class Test {
	public static void main(String[] args) {
		ServerSocket myServerSocket = null;
		Socket linkedSocket = null;
		BufferedReader breader = null;
		InputStreamReader isr=null;
		try {
			myServerSocket = new ServerSocket(1900);
			cout("ServerSocket set up successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		cout(("setupSocket"));
		try {
			cout("waiting for connection");
			linkedSocket = myServerSocket.accept();
			cout("connected client start to communicate!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			InputStream istream = linkedSocket.getInputStream();
			breader = new BufferedReader(new InputStreamReader(istream));
			cout("function Receive inited");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String message;
			while(true) {
				while((message = breader.readLine())!=null) {
					System.out.println(message);
				}
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void cout(String m_output) {// 实在不知道起什么名字所以就用了cout，我就不信cout在java里面能重名
			System.out.println(m_output);
	}

}


package test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import transmitioncore.Message;
import transmitioncore.ServerCore;

public class Test {
	public static void main(String[] args) {
		String b=null;
		String a="-zhao1996-2\n";
		int c=a.indexOf("-");
		b=a.substring(0,c);
		cout(b);
		if(b.equals("")) {
			cout("yes");
		}
		
		
		
		
		
		
		
		
		
		}
	
	
	
	
	
	
	
	
	
	public static void cout(String m_output) {// 实在不知道起什么名字所以就用了cout，我就不信cout在java里面能重名
			System.out.println(m_output);
	}

}


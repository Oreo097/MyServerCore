package transmitioncore;

import java.net.Socket;
import transmitioncore.*;

public class User {
	/*
	 * 这个类是用来建立用户对象的
	 * 一个用户类中应该包含这样一些功能
	 * 接收/发送数据
	 */
	/********************变量表*********************/
	/********************对象定义变量表*********************/
	private String user_name;
	private int user_id;
	private Socket user_Socket;
	/********************全局变量表*********************/
	/********************public*********************/
	/********************private*********************/
	/*
	 * 构造函数
	 */
	public User(int m_user_id,String m_user_name,Socket m_user_Socket) {
		user_id=m_user_id;
		user_name=m_user_name;
		user_Socket=m_user_Socket;
	}
	/*
	 * 数据获取函数
	 * 从ServerCore的矩阵中获取该user收到的信息
	 */
	public void pullMessage() {
		
	}
	/*
	 * 数据发送函数
	 * 将要发送的数据发送至ServerCore类的二维数组里
	 * 传输协议是 user_name+message_send+time
	 */
	public void sendMessage(String m_message_send) {
		String message_send;
		message_send=
	}
	/*
	 * 接受数据处理函数
	 * 接受的数据是一个数组
	 * 后期可以考虑加入校验程序
	 */
	public void receiveMessage() {
		
	}
}

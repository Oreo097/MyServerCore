package transmitioncore;

import java.io.*;
import java.net.Socket;
import transmitioncore.*;

public class User {
	/*
	 * 这个类是用来建立用户对象的
	 * 一个用户类中应该包含这样一些功能
	 * 接收/发送数据
	 * 所以使用另一个线程发送数据
	 * 主线程接收数据
	 */
	/********************变量表*********************/
	/********************对象定义变量表*********************/
	private int user_id;
	private Socket user_Socket;
	private int user_index;
	/********************全局变量表*********************/
	/********************public*********************/
	/********************private*********************/
	private boolean debugmode;
	private OutputStream ostream;
	private PrintWriter pwriter;
	private InputStream istream;
	private BufferedReader breader;
	/*
	 * 构造函数
	 */
	public User(int m_user_id,int m_user_index,Socket m_user_Socket) {
		user_id=m_user_id;
		user_index=m_user_index;
		user_Socket=m_user_Socket;
	}
	/*
	 * 我觉得有必要建立测试用的推送机制
	 * 所以写了这个函数用于debug
	 */
	public void cout(String m_output) {//实在不知道起什么名字所以就用了cout，我就不信cout在java里面能重名
		if(debugmode==true) {
			System.out.println(m_output);
		}
	}
	/*
	 * 数据获取函数
	 * 从ServerCore的矩阵中获取该user收到的信息
	 */
	public Message pullMessage(DataBay m_DataBay) {
		Message message_pull=null;
		return message_pull;
	}
	/*
	 * 数据上传函数
	 * 把接收到的数据上传到ServerCore其他User的信息队列中
	 */
	public void pushMessage(Message m_message,int m_index,DataBay m_DataBay) {
		
	}
	/*
	 * 初始化发送数据函数
	 * 只需要初始化一次
	 */
	public void initSend() {
		try {
			ostream = user_Socket.getOutputStream();
			pwriter = new PrintWriter(ostream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * 初始化数据接收函数
	 * 只需要初始化一次
	 */
	public void initReceive() {
		try {
			istream = user_Socket.getInputStream();
			breader = new BufferedReader(new InputStreamReader(istream));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * 数据发送函数
	 * 将要发送的数据发送至ServerCore类的二维数组里
	 * 传输协议是 user_id_message_send_time
	 */
	public void sendMessage(Message m_message_send) {//向物理上的用户发送数据
		pwriter.write(m_message_send.message);
		pwriter.flush();
	}
	/*
	 * 接受数据处理函数
	 * 接受的数据是一个数组
	 * 后期可以考虑加入校验程序
	 */
	public Message receiveMessage() {//从物理上的用户接受的信息
		try {
			String message;
			message = breader.readLine();
			Message message_receive=new Message(message);
			message_receive.dealMessage();//这样就可以使用message对象的处理信息函数找出user_id
			return message_receive;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}

package transmitioncore;

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
	private String user_name;
	private int user_id;
	private Socket user_Socket;
	private int user_index;
	/********************全局变量表*********************/
	/********************public*********************/
	/********************private*********************/
	private boolean debugmode;
	/*
	 * 构造函数
	 */
	public User(int m_user_id,String m_user_name,int m_user_index,Socket m_user_Socket) {
		user_id=m_user_id;
		user_name=m_user_name;
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
	public void pullMessage() {
		
	}
	/*
	 * 数据上传函数
	 * 把接收到的数据上传到ServerCore其他User的信息队列中
	 */
	public void pushMessage() {
		
	}
	/*
	 * 初始化发送数据函数
	 * 只需要初始化一次
	 */
	public void initSend() {
		
	}
	/*
	 * 初始化数据接收函数
	 * 只需要初始化一次
	 */
	public void initReceive() {
		
	}
	/*
	 * 数据发送函数
	 * 将要发送的数据发送至ServerCore类的二维数组里
	 * 传输协议是 user_name+message_send+time
	 */
	public void sendMessage(Message m_message_send,int target_id) {
		
	}
	/*
	 * 接受数据处理函数
	 * 接受的数据是一个数组
	 * 后期可以考虑加入校验程序
	 */
	public Message receiveMessage() {
		Message message_receive=null;
		
		return message_receive;
	}
}

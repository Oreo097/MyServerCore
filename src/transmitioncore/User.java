package transmitioncore;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import transmitioncore.DataBay;

public class User {
	/*
	 * 这个类是用来建立用户对象的 一个用户类中应该包含这样一些功能 接收/发送数据 所以使用另一个线程发送数据 主线程接收数据
	 */
	/******************** 变量表 *********************/
	/******************** 对象定义变量表 *********************/
	public int user_id;
	private Socket user_Socket;
	private DataBay core_databay;
	/******************** 全局变量表 *********************/
	/******************** public *********************/
	public ArrayList<Message> message_list;
	public boolean checkpoint = true;
	public String user_name;
	/******************** private *********************/
	private boolean debugmode=true;
	private OutputStream ostream;
	private PrintWriter pwriter;
	private InputStream istream;
	private BufferedReader breader;
	private Thread thread_send;

	/*
	 * 构造函数
	 */
	public User(Socket m_user_Socket, DataBay m_databay) {
		user_Socket = m_user_Socket;
		core_databay = m_databay;
	}

	/*
	 * 我觉得有必要建立测试用的推送机制 所以写了这个函数用于debug
	 */
	public void cout(String m_output) {// 实在不知道起什么名字所以就用了cout，我就不信cout在java里面能重名
		if (debugmode == true) {
			System.out.println(m_output);
		}
	}

//	/*
//	 * 数据获取函数
//	 * 从ServerCore的矩阵中获取该user收到的信息
//	 */
//	public Message pullMessage(DataBay m_DataBay) {
//		Message message_pull=m_DataBay.messageArray[user_id][0];
//		return message_pull;
//	}
	/*
	 * 数据上传函数 把接收到的数据上传到ServerCore其他User的信息队列中
	 */
	public void pushMessage(Message m_message_push) {
		core_databay.getUser(m_message_push.target_id).message_list.add(m_message_push);// 向目标列表添加数据
	}

	/*
	 * 初始化发送数据函数 只需要初始化一次
	 */
	public void initSend() {
		try {
			ostream = user_Socket.getOutputStream();
			pwriter = new PrintWriter(ostream);
			cout("function Send inited");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 初始化数据接收函数 只需要初始化一次
	 */
	public void initReceive() {
		try {
			istream = user_Socket.getInputStream();
			breader = new BufferedReader(new InputStreamReader(istream));
			cout("function Receive inited");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 数据发送函数 将要发送的数据发送至ServerCore类的二维数组里 传输协议是 user_id_message_send_time
	 */
	public void sendMessage() {// 向物理上的用户发送数据
		Message message_send = message_list.get(0);
		if (message_send != null) {
			pwriter.write(message_send.message);
			pwriter.flush();// 清除缓冲区
			message_list.remove(0);
		}
	}

	/*
	 * 重载函数，用来发送最初的握手信息和服务器的提示信息
	 */
	public void sendMessage(String message_send) {// 向物理上的用户发送数据
		pwriter.write(message_send);
		pwriter.flush();// 清除缓冲区
		message_list.remove(0);
	}

	/*
	 * 接受数据处理函数 接受的数据是一个数组 后期可以考虑加入校验程序
	 */
	public Message receiveMessage() {// 从物理上的用户接受的信息
		try {
			String message;
			message = breader.readLine();
			cout(message);
			Message message_receive = new Message(message);
			message_receive.dealMessage();// 这样就可以使用message对象的处理信息函数找出user_id
			return message_receive;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * 下面是用户类的主程序 包含创建新的线程 交换数据
	 */
	public Runnable send_Runnable = new Runnable() {// 定义一个线程runnable

		@Override
		public void run() {
			while (checkpoint) {
				sendMessage();
			}
		}
	};

	/*
	 * 登陆验证
	 */
	public boolean checkTicket() {
		Message loginmessage = null;
		receiveMessage();
		//cout(loginmessage.message);
		cout("start check password");
		for (int i = 0; i < 5; i++) {// 密码最多错5次
			while (loginmessage.message == null) {//检测是不是没输密码
				sendMessage("denied"+i);
				loginmessage = receiveMessage();
				cout("deined");
			}
			if (loginmessage.message == core_databay.getPasswd(loginmessage.target_id)) {
				sendMessage("accessed");
				cout("User:" + loginmessage.sender_id + "login");
				user_name=core_databay.getName(loginmessage.target_id);
				return true;
			}
		}
		cout("User:" + loginmessage.sender_id + "denied");
		return false;
	}

	/*
	 * 对象启动函数
	 */
	public void start() {
		initSend();
		initReceive();
		if (checkTicket()) {
			sendMessage(user_name);
			thread_send = new Thread(send_Runnable);//启动发送信息的线程
			thread_send.start();
			while (checkpoint) {
				Message message_receive = receiveMessage();
				if (message_receive != null) {
					pushMessage(message_receive);
				}
			}
		} else {
			cout("user access denied");
		}

	}
}

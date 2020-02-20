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
	 * ����������������û������ һ���û�����Ӧ�ð�������һЩ���� ����/�������� ����ʹ����һ���̷߳������� ���߳̽�������
	 */
	/******************** ������ *********************/
	/******************** ����������� *********************/
	public int user_id;
	private Socket user_Socket;
	private DataBay core_databay;
	/******************** ȫ�ֱ����� *********************/
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
	 * ���캯��
	 */
	public User(Socket m_user_Socket, DataBay m_databay) {
		user_Socket = m_user_Socket;
		core_databay = m_databay;
	}

	/*
	 * �Ҿ����б�Ҫ���������õ����ͻ��� ����д�������������debug
	 */
	public void cout(String m_output) {// ʵ�ڲ�֪����ʲô�������Ծ�����cout���ҾͲ���cout��java����������
		if (debugmode == true) {
			System.out.println(m_output);
		}
	}

//	/*
//	 * ���ݻ�ȡ����
//	 * ��ServerCore�ľ����л�ȡ��user�յ�����Ϣ
//	 */
//	public Message pullMessage(DataBay m_DataBay) {
//		Message message_pull=m_DataBay.messageArray[user_id][0];
//		return message_pull;
//	}
	/*
	 * �����ϴ����� �ѽ��յ��������ϴ���ServerCore����User����Ϣ������
	 */
	public void pushMessage(Message m_message_push) {
		core_databay.getUser(m_message_push.target_id).message_list.add(m_message_push);// ��Ŀ���б��������
	}

	/*
	 * ��ʼ���������ݺ��� ֻ��Ҫ��ʼ��һ��
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
	 * ��ʼ�����ݽ��պ��� ֻ��Ҫ��ʼ��һ��
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
	 * ���ݷ��ͺ��� ��Ҫ���͵����ݷ�����ServerCore��Ķ�ά������ ����Э���� user_id_message_send_time
	 */
	public void sendMessage() {// �������ϵ��û���������
		Message message_send = message_list.get(0);
		if (message_send != null) {
			pwriter.write(message_send.message);
			pwriter.flush();// ���������
			message_list.remove(0);
		}
	}

	/*
	 * ���غ������������������������Ϣ�ͷ���������ʾ��Ϣ
	 */
	public void sendMessage(String message_send) {// �������ϵ��û���������
		pwriter.write(message_send);
		pwriter.flush();// ���������
		message_list.remove(0);
	}

	/*
	 * �������ݴ����� ���ܵ�������һ������ ���ڿ��Կ��Ǽ���У�����
	 */
	public Message receiveMessage() {// �������ϵ��û����ܵ���Ϣ
		try {
			String message;
			message = breader.readLine();
			cout(message);
			Message message_receive = new Message(message);
			message_receive.dealMessage();// �����Ϳ���ʹ��message����Ĵ�����Ϣ�����ҳ�user_id
			return message_receive;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * �������û���������� ���������µ��߳� ��������
	 */
	public Runnable send_Runnable = new Runnable() {// ����һ���߳�runnable

		@Override
		public void run() {
			while (checkpoint) {
				sendMessage();
			}
		}
	};

	/*
	 * ��½��֤
	 */
	public boolean checkTicket() {
		Message loginmessage = null;
		receiveMessage();
		//cout(loginmessage.message);
		cout("start check password");
		for (int i = 0; i < 5; i++) {// ��������5��
			while (loginmessage.message == null) {//����ǲ���û������
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
	 * ������������
	 */
	public void start() {
		initSend();
		initReceive();
		if (checkTicket()) {
			sendMessage(user_name);
			thread_send = new Thread(send_Runnable);//����������Ϣ���߳�
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

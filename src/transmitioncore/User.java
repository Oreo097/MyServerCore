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
	public boolean message_checkpoint=false;
	public boolean checkpoint = true;
	public String user_name;
	/******************** private *********************/
	private boolean debugmode=true;
	private OutputStream ostream;
	private PrintWriter pwriter;
	private InputStream istream;
	private BufferedReader breader;
	//private Thread thread_send;

	/*
	 * ���캯��
	 */
	public User(Socket m_user_Socket, DataBay m_databay) {
		user_Socket = m_user_Socket;
		core_databay = m_databay;
		message_list=new ArrayList<Message>();
	}

	/*
	 * �Ҿ����б�Ҫ���������õ����ͻ��� ����д�������������debug
	 */
	public void cout(String m_output) {// ʵ�ڲ�֪����ʲô�������Ծ�����cout���ҾͲ���cout��java����������
		if (debugmode == true) {
			System.out.println(m_output);
		}
	}

	/*
	 * �����ϴ����� �ѽ��յ��������ϴ���ServerCore����User����Ϣ������
	 */
	public void pushMessage(Message m_message_push) {
		User user_target=null;
		if((user_target=core_databay.getUser(m_message_push.target_id))!=null) {
			cout("pushMessage(Message m_message_push):not null start push to "+m_message_push.target_id);
			cout("pushMessage(Message m_message_push):user_target.userid "+user_target.user_id);
			user_target.message_list.add(m_message_push);// ��Ŀ���б��������
			cout("pushMessage(Message m_message_push):"+user_name+"message pushed");
			user_target.message_checkpoint=true;
		}
		else {//���ͷ�������
			cout("pushMessage(Message m_message_push):user is null");
			//pushMessage(m_message_push);
		}
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
	 * ��ʼ������
	 */
	public void init() {
		initSend();
		initReceive();
	}
	/*
	 * ���ݷ��ͺ��� ��Ҫ���͵����ݷ�����ServerCore��Ķ�ά������ ����Э���� user_id_message_send_time
	 */
	public void sendMessage_A() {// �������ϵ��û���������
		//cout("sendMessage_A():start");
		if(message_checkpoint==true) {
			try {
				Message message_send = message_list.get(0);
				pwriter.write(message_send.message+"\n");
				cout(user_name+"sendMessage():sended");
				pwriter.flush();// ���������
				cout(user_name+"sendMessage():flushed");
				message_list.remove(0);
			}catch(Exception e) {
				cout("no message");
				message_checkpoint=false;
				e.printStackTrace();
			}
		}
	}

	/*
	 * ���غ������������������������Ϣ�ͷ���������ʾ��Ϣ
	 */
	public void sendMessage_M(String message_send) {// �������ϵ��û���������
		pwriter.write(message_send+"\n");
		cout(user_name+"sendMessage(String message_send):sended");
		pwriter.flush();// ���������
		cout(user_name+" sendMessage(String message_send):flushed");
	}

	/*
	 * �������ݴ����� ���ܵ�������һ������ ���ڿ��Կ��Ǽ���У�����
	 */
	public Message receiveMessage() {// �������ϵ��û����ܵ���Ϣ
		try {
			String message;
			message = breader.readLine();
			cout("receiveMessage():"+message);
			Message message_receive = new Message(message);
			message_receive.dealMessage();// �����Ϳ���ʹ��message����Ĵ�����Ϣ�����ҳ�user_id
			return message_receive;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * ��½��֤
	 */
	public boolean checkTicket() {
		Message loginmessage = null;
		loginmessage=receiveMessage();
		cout(loginmessage.message);
		cout("start check password");
		for (int i = 0; i < 5; i++) {// ��������5��
			while (loginmessage.getMessage() == null) {//����ǲ���û������
				cout("deined");
				sendMessage_M("denied");
				loginmessage = receiveMessage();
			}
			if (loginmessage.getMessage().equals( core_databay.getPasswd(loginmessage.sender_id))) {
				sendMessage_M("accessed");
				cout("User: " + loginmessage.sender_id + " login");
				user_name=core_databay.getName(loginmessage.sender_id);
				user_id=loginmessage.sender_id;
				cout("checkTicket:user_id is "+user_id);
				return true;
			}
			else {
				cout("access deined "+"this is the "+i+" try");
				loginmessage=null;
			}
		}
		cout("User:" + loginmessage.sender_id + " denied");
		return false;
	}

	/*
	 * �������û���������� ���������µ��߳� ��������
	 */
	public Runnable send_Runnable = new Runnable() {// ����һ���߳�runnable

		@Override
		public void run() {
			cout("send thread start");
			while (checkpoint==true) {
				sendMessage_A();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					cout("sleep failed");
					e.printStackTrace();
				}
			}
		}
	};
	
	/*
	 * ������������
	 * �ݲ�ʹ��
	 */
	public void start() {
		init();
		if (checkTicket()) {
			sendMessage_M(user_name);
			Thread thread_send = new Thread(send_Runnable);//����������Ϣ���߳�
			thread_send.start();
			while (checkpoint) {
				Message message_receive = receiveMessage();
				cout("start():message_receive.message:"+message_receive.message);
				pushMessage(message_receive);
			}
		} else {
			cout("user access denied");
		}

	}
}

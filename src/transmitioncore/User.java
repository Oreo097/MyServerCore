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
	public boolean message_checkpoint = false;
	public boolean checkpoint = true;
	public String user_name;
	/******************** private *********************/
	private boolean debugmode = true;
	private OutputStream ostream;
	private PrintWriter pwriter;
	private InputStream istream;
	private BufferedReader breader;
	// private Thread thread_send;

	/*
	 * ���캯��
	 */
	public User(Socket m_user_Socket, DataBay m_databay) {
		user_Socket = m_user_Socket;
		core_databay = m_databay;
		message_list = new ArrayList<Message>();
		user_id=0;
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
		User user_target = null;
		if ((user_target = core_databay.getUser(m_message_push.target_id)) != null) {
			cout("pushMessage(Message m_message_push):not null start push to " + m_message_push.target_id);
			cout("pushMessage(Message m_message_push):user_target.userid " + user_target.user_id);
			user_target.message_list.add(m_message_push);// ��Ŀ���б��������
			cout("pushMessage(Message m_message_push):" + user_name + "message pushed");
			user_target.message_checkpoint = true;
		} else {// ���ͷ�������
			cout("pushMessage(Message m_message_push):user is null");
			// pushMessage(m_message_push);
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
		// cout("sendMessage_A():start");
		if (message_checkpoint == true) {
			try {
				Message message_send = message_list.get(0);
				pwriter.write(message_send.message + "\n");
				cout(user_name + "sendMessage():sended");
				pwriter.flush();// ���������
				cout(user_name + "sendMessage():flushed");
				message_list.remove(0);
			} catch (Exception e) {
				cout("no message");
				message_checkpoint = false;
				// e.printStackTrace();//ȥ���˱�����ʾ
			}
		}
	}

	/*
	 * ���غ������������������������Ϣ�ͷ���������ʾ��Ϣ
	 */
	public void sendMessage_M(String message_send) {// �������ϵ��û���������
		pwriter.write(message_send + "\n");
		cout(user_name + "sendMessage(String message_send):sended");
		pwriter.flush();// ���������
		cout(user_name + " sendMessage(String message_send):flushed");
	}

	/*
	 * �������ݴ����� ���ܵ�������һ������ ���ڿ��Կ��Ǽ���У�����
	 */
	public Message receiveMessage() {// �������ϵ��û����ܵ���Ϣ
		try {
			String message;
			message = breader.readLine();
			if (Message.checkMessage(message) == 0) {// ������֤��Ϣ׼ȷ��
				Message message_receive = new Message(message);
				message_receive.dealMessage();// �����Ϳ���ʹ��message����Ĵ�����Ϣ�����ҳ�user_id
				return message_receive;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * �ֶ�������Ϣ
	 */
	public String receiveMessage_M() {
		try {
			String message;
			message = breader.readLine();
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/*
	 * ��֤����
	 */
	public boolean checkPasswd(Message m_message) {
		String passwd=core_databay.getPasswd(m_message.sender_id);
		if(m_message.getMessage().equals(passwd)) {
			cout("checkPasswd(Message m_message):pass");
			return true;
		}else {
			cout("checkPasswd(Message m_message):passwd not ture");
			sendMessage_M("password error");
			return false;
		}
	}

	/*
	 * ��½��֤
	 */
	public boolean checkTicket() {
		for(int i=0;i<5;i++) {
			String message=receiveMessage_M();
			cout("checkTicket():received message is:"+message);
			if(Message.checkMessage(message)!=1) {
				sendMessage_M("message error");
				cout("checkTicket():message error");
				continue;
			}
			Message check_message=new Message(message);
			check_message.dealMessage();
			if(checkPasswd(check_message)==true) {
				user_id=check_message.sender_id;
				user_name=core_databay.getName(user_id);
				cout("checkTicket():User: "+user_name+" access");
				sendMessage_M("Access");
				sendMessage_M(user_name);
				return true;
			}
			cout("checkTicket():password error and this is the :"+i+" try");
		}
		sendMessage_M("error too more");
		cout("checkTicket():error too more");
		return false;
	}

	/*
	 * �������û���������� ���������µ��߳� ��������
	 */
	public Runnable send_Runnable = new Runnable() {// ����һ���߳�runnable

		@Override
		public void run() {
			cout("send thread start");
			while (checkpoint == true) {
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
	 * ������������ �ݲ�ʹ��
	 */
	public void start() {
			Thread thread_send = new Thread(send_Runnable);// ����������Ϣ���߳�
			thread_send.start();
			while (checkpoint) {
				Message message_receive = receiveMessage();
				cout("start():message_receive.message:" + message_receive.message);
				pushMessage(message_receive);
			}
	}
}

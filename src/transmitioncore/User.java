package transmitioncore;

import java.io.*;
import java.net.Socket;
import transmitioncore.*;

public class User {
	/*
	 * ����������������û������
	 * һ���û�����Ӧ�ð�������һЩ����
	 * ����/��������
	 * ����ʹ����һ���̷߳�������
	 * ���߳̽�������
	 */
	/********************������*********************/
	/********************�����������*********************/
	private int user_id;
	private Socket user_Socket;
	private int user_index;
	/********************ȫ�ֱ�����*********************/
	/********************public*********************/
	/********************private*********************/
	private boolean debugmode;
	private OutputStream ostream;
	private PrintWriter pwriter;
	private InputStream istream;
	private BufferedReader breader;
	/*
	 * ���캯��
	 */
	public User(int m_user_id,int m_user_index,Socket m_user_Socket) {
		user_id=m_user_id;
		user_index=m_user_index;
		user_Socket=m_user_Socket;
	}
	/*
	 * �Ҿ����б�Ҫ���������õ����ͻ���
	 * ����д�������������debug
	 */
	public void cout(String m_output) {//ʵ�ڲ�֪����ʲô�������Ծ�����cout���ҾͲ���cout��java����������
		if(debugmode==true) {
			System.out.println(m_output);
		}
	}
	/*
	 * ���ݻ�ȡ����
	 * ��ServerCore�ľ����л�ȡ��user�յ�����Ϣ
	 */
	public Message pullMessage(DataBay m_DataBay) {
		Message message_pull=null;
		return message_pull;
	}
	/*
	 * �����ϴ�����
	 * �ѽ��յ��������ϴ���ServerCore����User����Ϣ������
	 */
	public void pushMessage(Message m_message,int m_index,DataBay m_DataBay) {
		
	}
	/*
	 * ��ʼ���������ݺ���
	 * ֻ��Ҫ��ʼ��һ��
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
	 * ��ʼ�����ݽ��պ���
	 * ֻ��Ҫ��ʼ��һ��
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
	 * ���ݷ��ͺ���
	 * ��Ҫ���͵����ݷ�����ServerCore��Ķ�ά������
	 * ����Э���� user_id_message_send_time
	 */
	public void sendMessage(Message m_message_send) {//�������ϵ��û���������
		pwriter.write(m_message_send.message);
		pwriter.flush();
	}
	/*
	 * �������ݴ�����
	 * ���ܵ�������һ������
	 * ���ڿ��Կ��Ǽ���У�����
	 */
	public Message receiveMessage() {//�������ϵ��û����ܵ���Ϣ
		try {
			String message;
			message = breader.readLine();
			Message message_receive=new Message(message);
			message_receive.dealMessage();//�����Ϳ���ʹ��message����Ĵ�����Ϣ�����ҳ�user_id
			return message_receive;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}

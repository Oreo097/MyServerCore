package transmitioncore;

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
	private String user_name;
	private int user_id;
	private Socket user_Socket;
	private int user_index;
	/********************ȫ�ֱ�����*********************/
	/********************public*********************/
	/********************private*********************/
	private boolean debugmode;
	/*
	 * ���캯��
	 */
	public User(int m_user_id,String m_user_name,int m_user_index,Socket m_user_Socket) {
		user_id=m_user_id;
		user_name=m_user_name;
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
	public void pullMessage() {
		
	}
	/*
	 * �����ϴ�����
	 * �ѽ��յ��������ϴ���ServerCore����User����Ϣ������
	 */
	public void pushMessage() {
		
	}
	/*
	 * ��ʼ���������ݺ���
	 * ֻ��Ҫ��ʼ��һ��
	 */
	public void initSend() {
		
	}
	/*
	 * ��ʼ�����ݽ��պ���
	 * ֻ��Ҫ��ʼ��һ��
	 */
	public void initReceive() {
		
	}
	/*
	 * ���ݷ��ͺ���
	 * ��Ҫ���͵����ݷ�����ServerCore��Ķ�ά������
	 * ����Э���� user_name+message_send+time
	 */
	public void sendMessage(Message m_message_send,int target_id) {
		
	}
	/*
	 * �������ݴ�����
	 * ���ܵ�������һ������
	 * ���ڿ��Կ��Ǽ���У�����
	 */
	public Message receiveMessage() {
		Message message_receive=null;
		
		return message_receive;
	}
}

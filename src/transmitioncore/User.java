package transmitioncore;

import java.net.Socket;
import transmitioncore.*;

public class User {
	/*
	 * ����������������û������
	 * һ���û�����Ӧ�ð�������һЩ����
	 * ����/��������
	 */
	/********************������*********************/
	/********************�����������*********************/
	private String user_name;
	private int user_id;
	private Socket user_Socket;
	/********************ȫ�ֱ�����*********************/
	/********************public*********************/
	/********************private*********************/
	/*
	 * ���캯��
	 */
	public User(int m_user_id,String m_user_name,Socket m_user_Socket) {
		user_id=m_user_id;
		user_name=m_user_name;
		user_Socket=m_user_Socket;
	}
	/*
	 * ���ݻ�ȡ����
	 * ��ServerCore�ľ����л�ȡ��user�յ�����Ϣ
	 */
	public void pullMessage() {
		
	}
	/*
	 * ���ݷ��ͺ���
	 * ��Ҫ���͵����ݷ�����ServerCore��Ķ�ά������
	 * ����Э���� user_name+message_send+time
	 */
	public void sendMessage(String m_message_send) {
		String message_send;
		message_send=
	}
	/*
	 * �������ݴ�����
	 * ���ܵ�������һ������
	 * ���ڿ��Կ��Ǽ���У�����
	 */
	public void receiveMessage() {
		
	}
}

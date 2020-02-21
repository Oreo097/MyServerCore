package transmitioncore;

public class Message {
	/*
	 * ������Ϣ���� ��User��������
	 */
	/******************** ������ *********************/
	/******************** ����������� *********************/
	public String message;
	/******************** ȫ�ֱ����� *********************/
	/******************** public *********************/
	public String target_name;
	public String sender_name;
	public int target_id;
	public int sender_id;
	/******************** private *********************/
	private boolean debugmode=true;

	/*
	 * ���캯��
	 */
	public Message(String m_message) {
		message = m_message;
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
	 * ����Message�ĺ��� ��Ҫ�Ǵ�����Ϣ ��ȡtarget_name��sender_name ��Ϣ��ʽΪ target_name-message-sender_name
	 */
	public void dealMessage() {
		String can = message;
		int target1, target2;// ����������-����λ��
		target1 = can.indexOf("-");
		target2 = can.lastIndexOf("-");
		String target_id_String = can.substring(0, target1);
		String sender_id_String = can.substring(target2 + 1);
		cout("message is send to: " + target_id_String);
		cout("message is send from: " + sender_id_String);
		if(!check(target_id_String)) {
			cout("target_id missed");
		}
		if(!check(sender_id_String)) {
			cout("target_id missed");
		}
		this.target_id = Integer.parseInt(target_id_String);// ת��������
		this.sender_id = Integer.parseInt(sender_id_String);
	}
	public boolean check(String m_message) {
		if(m_message==""||m_message==null) {
			return false;
		}
		return true;
	}
	/*
	 * ȡ����Ϣ�ĺ���
	 */
	public String getMessage() {
		String Message=null;
		String can = message;
		int target1, target2;// ����������-����λ��
		target1 = can.indexOf("-");
		target2 = can.lastIndexOf("-");
		Message=can.substring(target1+1,target2);
		cout("Message.getmessage():"+Message);
		return Message;
	}
}

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
	private boolean debugmode;

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
	 * ����Message�ĺ��� ��Ҫ�Ǵ�����Ϣ ��ȡtarget_name��sender_name
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
		this.target_id = Integer.parseInt(target_id_String);// ת��������
		this.sender_id = Integer.parseInt(sender_id_String);
	}
}

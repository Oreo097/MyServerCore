package transmitioncore;

import java.util.ArrayList;

public class DataBay {
	/*
	 * �����м�洢�Ķ���
	 * �������һЩ����
	 * ��������ʵ����SQL���ݿ�Խ�
	 */
	/********************ȫ�ֱ�����*********************/
	/********************public*********************/
	public ArrayList <User> user_list;//�洢user������б�index_user��
	/********************private*********************/
	public DataBay() {
		user_list=new ArrayList<User>();
	}

	public User getUser(int m_id) {
		for (User the_user : user_list) {
			if(the_user.user_id==m_id) {
				return the_user;
			}
		}
		return null;
	}
	public int getid() {
		return id;
		
	}
	public int creatid() {
		
		return id;
	}
}

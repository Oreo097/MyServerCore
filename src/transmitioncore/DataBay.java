package transmitioncore;

import java.util.List;

public class DataBay {
	/*
	 * �����м�洢�Ķ���
	 * �������һЩ����
	 * ��������ʵ����SQL���ݿ�Խ�
	 */
	/********************ȫ�ֱ�����*********************/
	/********************public*********************/
	public List <User> user_list;//�洢user������б�index_user��
	public Message[][] messageArray;//���message������е��б�index_user����index_message��
	public boolean add[];
	public boolean delete[];
	public int index_message;//��Ϣ���е�����
	/********************private*********************/
}

package transmitioncore;

import java.util.ArrayList;
import java.util.Random;
import transmitioncore.ShowInfo;

public class DataBay {
	/*
	 * �����м�洢�Ķ��� �������һЩ���� ��������ʵ����SQL���ݿ�Խ�
	 */
	/******************** ȫ�ֱ����� *********************/
	/******************** public *********************/
	public ArrayList<User> user_list;// �洢user������б�index_user��
	public ArrayList<Integer> user_id_list;
	public boolean main_checkpoint;
	public boolean cleaner_checkpoint;

	/******************** private *********************/
	public DataBay() {
		user_list = new ArrayList<User>();
		main_checkpoint = true;
		cleaner_checkpoint = false;
		Cleaner();
	}

	/*
	 * ��ȡUser�ĺ��� ����User���� �����ҵ���Ӧ��User����������
	 */
	public User getUser(int m_id) {
		for (User the_user : user_list) {
			if (the_user.user_id == m_id) {
				return the_user;
			}
		}
		return null;
	}

	/*
	 * ��ȡuser_id�ĺ���
	 */
//	public int getid() {
//		return id;
//		
//	}
	/*
	 * ����user_id�ĺ���
	 */
	public synchronized int creatId() {
		Random user_id_random = new Random();
		int id = user_id_random.nextInt();
		int i = 0;
		while (i < user_id_list.size()) {
			if (id == user_id_list.get(i)) {
				id = user_id_random.nextInt();
				i = 0;
			}
		}
		user_id_list.add(id);
		return id;
	}

	/*
	 * ����user_id�б�ĺ���
	 */
	public synchronized void cleanNullId() {
		for (int i : user_id_list) {
			if (getUser(i) == null) {
				user_id_list.remove(i);
			}
		}
	}

	/*
	 * ����user�б�ĺ���
	 */
	public synchronized void cleanNullUser() {
		for (User i : user_list) {
			if (i == null) {
				user_list.remove(i);
			}
		}
	}

	/*
	 * ��ʱ����ĳ���
	 */
	public void cleanList() {
		String time = ShowInfo.getTime_day();
		if (time == "00:00:00") {
			if (cleaner_checkpoint = false) {
				cleanNullId();
				cleanList();
				cleaner_checkpoint = true;
			}
		}
	}

	/*
	 * ��ʱ��ת����ǵĳ���
	 */
	public void turnCheckPoint() {
		String time = ShowInfo.getTime_day();
		if (time == "00:00:30") {
			if (cleaner_checkpoint = true) {
				cleaner_checkpoint = false;
				System.out.println("cleaner check point is turned");
			} else {
				System.out.println("warring cleaner check point is not truned");
			}
		}
	}

	/*
	 * ����һ���µ��̼߳���ʱ��
	 */
	public void Cleaner() {
		Runnable cleaner_Runnable = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (main_checkpoint) {
					cleanList();
					turnCheckPoint();
				}
			}
		};
		Thread cleanerThread = new Thread(cleaner_Runnable);
		cleanerThread.start();
	}
}

package transmitioncore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import transmitioncore.ShowInfo;

public class DataBay {
	/*
	 * �����м�洢�Ķ��� �������һЩ���� ��������ʵ����SQL���ݿ�Խ�
	 */
	/*
	 * �����߼�������ô��ƣ���½ʱ�������֤�����ݵ�¼idƥ������
	 */
	/******************** ȫ�ֱ����� *********************/
	/******************** public *********************/
	public ArrayList<User> user_list;// �洢user������б�index_user��
	public ArrayList<Integer> user_id_list;
	public boolean main_checkpoint;
	public boolean cleaner_checkpoint;

	/******************** private *********************/
	/******************** ���ݿ��¼��� *********************/
	private String database_url = "jdbc:mysql://localhost:3306/userlist"
			+ "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
	private String database_username = "root";
	private String database_passwd = "zhao1996";
	private Connection connect;// ���ݿ������
	private Statement stmt;// ���ݿ�״̬

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
	 * ����user_id�ĺ���   �ѷ���
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
		for(int id=0;id<user_id_list.size();id++) {
			for(int user=0;user<user_list.size();user++) {
				if(user_list.get(user).user_id==id) {
					user_list.remove(user);
					user_id_list.remove(id);
				}
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
				cleanNullUser();
				cleaner_checkpoint = true;
				ServerCore.cout("cleanList():Clean finsh");
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
				ServerCore.cout("turnCheckPoint():cleaner check point is turned");
			} else {
				ServerCore.cout("turnCheckPoint():warring cleaner check point is not truned");
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
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						ServerCore.cout("failed to sleep");
						e.printStackTrace();
					}
				}
			}
		};
		Thread cleanerThread = new Thread(cleaner_Runnable);
		cleanerThread.start();
	}

	/*
	 * ���ݿ���ز�������
	 */
	/*
	 * �������ݿ��¼��Ϣ�ĺ���
	 */
	public void changeLoginInfo(String m_url, String m_username, String m_passwd) {
		database_url = m_url + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
		database_username = m_username;
		database_passwd = m_passwd;
	}

	/*
	 * ��������������
	 */
	public void initDriver() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // ����MYSQL JDBC��������
			// Class.forName("org.gjt.mm.mysql.Driver");
			ServerCore.cout("Success loading Mysql Driver!");
		} catch (Exception e) {
			ServerCore.cout("Error loading Mysql Driver!");
			e.printStackTrace();
		}
	}

	/*
	 * �������ݿ⺯��
	 */
	public void connectDataBase() {
		try {
			connect = DriverManager.getConnection(database_url, database_username, database_passwd);
			ServerCore.cout("Success connect Mysql server!");
			stmt = connect.createStatement();

		} catch (Exception e) {
			ServerCore.cout("get data error!");
			e.printStackTrace();
		}
	}

	/*
	 * �����ݿ��ȡ����
	 */
	public String getPasswd(int m_id) {
		try {
			ResultSet rs = stmt.executeQuery("select * from userinfo where id=" + m_id+";");
			rs.next();// ���ָ����һ��
			String password = rs.getString("password");
			ServerCore.cout("password is :"+password);
			return password;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * �����ݿ��л�ȡ����
	 */
	public String getName(int m_id) {
		try {
			ResultSet rs = stmt.executeQuery("select * from userinfo where id=" + m_id+";");
			rs.next();// ���ָ����һ��
			String user_name = rs.getString("name");
			return user_name;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * ������ݿ���֤��¼����
	 */
	public boolean logIn(int m_id,String m_passwd) {
		String passwd=getPasswd(m_id);
		if(m_passwd==passwd) {
			return true;
		}
		else {
			return false;
		}
	}
	/*
	 * �ҵ�Userλ�ù���
	 */
	//public int getUserLocation(int m_user_id) {
		
	//}
}

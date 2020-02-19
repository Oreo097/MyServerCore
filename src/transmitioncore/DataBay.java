package transmitioncore;

import java.util.ArrayList;
import java.util.Random;
import transmitioncore.ShowInfo;

public class DataBay {
	/*
	 * 数据中间存储的对象 用来存放一些数据 后期用来实现与SQL数据库对接
	 */
	/******************** 全局变量表 *********************/
	/******************** public *********************/
	public ArrayList<User> user_list;// 存储user对象的列表【index_user】
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
	 * 获取User的函数 返回User类型 用来找到相应的User并发送数据
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
	 * 获取user_id的函数
	 */
//	public int getid() {
//		return id;
//		
//	}
	/*
	 * 生成user_id的函数
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
	 * 清理user_id列表的函数
	 */
	public synchronized void cleanNullId() {
		for (int i : user_id_list) {
			if (getUser(i) == null) {
				user_id_list.remove(i);
			}
		}
	}

	/*
	 * 清理user列表的函数
	 */
	public synchronized void cleanNullUser() {
		for (User i : user_list) {
			if (i == null) {
				user_list.remove(i);
			}
		}
	}

	/*
	 * 定时清理的程序
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
	 * 定时反转清洁标记的程序
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
	 * 开辟一个新的线程监视时间
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

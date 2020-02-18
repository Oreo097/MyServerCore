package transmitioncore;

import java.util.ArrayList;

public class DataBay {
	/*
	 * 数据中间存储的对象
	 * 用来存放一些数据
	 * 后期用来实现与SQL数据库对接
	 */
	/********************全局变量表*********************/
	/********************public*********************/
	public ArrayList <User> user_list;//存储user对象的列表【index_user】
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

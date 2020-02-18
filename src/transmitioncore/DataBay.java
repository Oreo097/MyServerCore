package transmitioncore;

import java.util.List;

public class DataBay {
	/*
	 * 数据中间存储的对象
	 * 用来存放一些数据
	 * 后期用来实现与SQL数据库对接
	 */
	/********************全局变量表*********************/
	/********************public*********************/
	public List <User> user_list;//存储user对象的列表【index_user】
	public Message[][] messageArray;//存放message对象队列的列表【index_user】【index_message】
	public boolean add[];
	public boolean delete[];
	public int index_message;//信息队列的索引
	/********************private*********************/
}

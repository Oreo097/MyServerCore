package transmitioncore;

public class DataBay {
	/*
	 * 数据中间存储的对象
	 * 用来存放一些数据
	 * 后期用来实现与SQL数据库对接
	 */
	/********************全局变量表*********************/
	/********************public*********************/
	public User[] userArray;//存储user对象的数组【index_user】
	public Message[][] messageArray;//存放message对象队列的数组【index_user】【index_message】
	public int index_user;//用户的索引
	public int index_message;//信息队列的索引
	/********************private*********************/
}

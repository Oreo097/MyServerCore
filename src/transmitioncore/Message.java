package transmitioncore;

public class Message {
	/*
	 * 这是信息对象 由User对象生成
	 */
	/******************** 变量表 *********************/
	/******************** 对象定义变量表 *********************/
	public String message;
	/******************** 全局变量表 *********************/
	/******************** public *********************/
	public String target_name;
	public String sender_name;
	public int target_id;
	public int sender_id;
	/******************** private *********************/
	private boolean debugmode=true;

	/*
	 * 构造函数
	 */
	public Message(String m_message) {
		message = m_message;
	}

	/*
	 * 我觉得有必要建立测试用的推送机制 所以写了这个函数用于debug
	 */
	public void cout(String m_output) {// 实在不知道起什么名字所以就用了cout，我就不信cout在java里面能重名
		if (debugmode == true) {
			System.out.println(m_output);
		}
	}

	/*
	 * 处理Message的函数 主要是处理信息 获取target_name、sender_name 信息格式为 target_name-message-sender_name
	 */
	public void dealMessage() {
		String can = message;
		int target1, target2;// 索引两个“-”的位置
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
		this.target_id = Integer.parseInt(target_id_String);// 转换成整数
		this.sender_id = Integer.parseInt(sender_id_String);
	}
	public boolean check(String m_message) {
		if(m_message==""||m_message==null) {
			return false;
		}
		return true;
	}
	/*
	 * 取出信息的函数
	 */
	public String getMessage() {
		String Message=null;
		String can = message;
		int target1, target2;// 索引两个“-”的位置
		target1 = can.indexOf("-");
		target2 = can.lastIndexOf("-");
		Message=can.substring(target1+1,target2);
		cout("Message.getmessage():"+Message);
		return Message;
	}
}

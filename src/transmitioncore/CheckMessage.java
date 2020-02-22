package transmitioncore;

public interface CheckMessage{

	public abstract boolean checkMessage(String m_message);//用这个方法来实现书写协议和规则
	public abstract Message dealMessage(String m_message);
}

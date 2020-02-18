package transmitioncore;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.net.*;

public class ServerCore {

	/*
	 * ServerCore������������ַ����ݵ���������socketͨѶ���ĺ���
	 * ����Ӧ��ӵ�еĹ����У�
	 * ����socket����
	 * �����̳߳�
	 * �ַ�����
	 * ����user����
	 */
	/*
	 * �����̳߳ص�ʱ��Ӧ��ע��
	 * �����ܵ���ͬ�豸����������
	 * ����Ӧ��д�������̳߳صĴ�����ʽ
	 * �����㲻ͬ�豸����������
	 */
	/********************������*********************/
	/********************�����������*********************/
	private int threadpool_type;
	private int blockedthread_max;
	private int portnumber;
	private boolean debugmode;
	private int threadpool_thread_number;
	/********************ȫ�ֱ�����*********************/
	/********************public*********************/
	public DataBay myDataBay;
	/********************private*********************/
	private ServerSocket myServerSocket;
	private ExecutorService myThreadPool;
	private int blockedThread_number;
	/********************��������*********************/
	
	/*
	 * ���캯��
	 * ���ܣ���ʼ�����ֱ���
	 */
	public ServerCore(int m_threadpool_type,int m_blockedthread_max,int m_portmnuber,boolean m_debugmode) {
		threadpool_type=m_threadpool_type;
		blockedthread_max=m_blockedthread_max;
		portnumber=m_portmnuber;
		debugmode=m_debugmode;
		myDataBay=new DataBay();
	}
	public ServerCore(int m_threadpool_type,int m_blockedthread_max,int m_portmnuber) {//���ع��캯����Ĭ�Ϸ�debugģʽ
		threadpool_type=m_threadpool_type;
		blockedthread_max=m_blockedthread_max;
		portnumber=m_portmnuber;
		debugmode=false;
		myDataBay=new DataBay();
	}
	/*
	 * �Ҿ����б�Ҫ���������õ����ͻ���
	 * ����д�������������debug
	 */
	public void cout(String m_output) {//ʵ�ڲ�֪����ʲô�������Ծ�����cout���ҾͲ���cout��java����������
		if(debugmode==true) {
			System.out.println(m_output);
		}
	}
	/*
	 * ��һ���Ĳ����õĺ���
	 * ��Ҫ����װ�������Ϣ
	 * ��Ҫ����
	 */
	public String getFunctionInfo(String m_function_name) {
		String string ="function "+m_function_name+"executed";
		return string;
	}
	/*
	 * ����Scoket�������ĺ���
	 * �����˿�Ϊ portnumber
	 */
	public void setupServerSocket() {
		try {
			myServerSocket = new ServerSocket(portnumber);
			cout("ServerSocket set up successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		cout(getFunctionInfo("setupSocket"));
	}
	/*
	 * �����̳߳�
	 * Ҫ���������̳߳�
	 * ��һ����fixed�̳߳�ӵ�й̶����߳�����
	 * �ڶ�����cached�̳߳�ӵ�����޵��߳�����
	 */
	public void setupCacheThreadPool() {//����һ��ӵ�����������Ƶ��̳߳�
		myThreadPool = Executors.newCachedThreadPool();
		cout("cached thread pool has been already seted up!");
	}
	public void setupFixedThreadPool(int m_threadpool_thread) {//����һ�����������Ƶ��̳߳�
		myThreadPool=Executors.newFixedThreadPool(m_threadpool_thread);
		cout("fixed thread pool has been already seted up!");
	}
	public void setupThreadPool(int m_threadpool_type) {//�Զ���⽨������������̳߳�
		switch(m_threadpool_type) {
		case 0:setupCacheThreadPool();break;
		case 1:setupFixedThreadPool(threadpool_thread_number);break;
		}
		cout(getFunctionInfo("setupThreadPool"));
	}
	/*
	 * ����Socket���ӵĺ���
	 * ����Ҫ���������̳߳ص�һ���߳�����
	 */
	public Socket setupSocket() {//���ǽ���Socket���ӵĺ���
		try {
			Socket linkedSocket = myServerSocket.accept();
			blockedThread_number-= 1;//ÿ���ӳɹ�һ���ͼ�һ����һֱ���ֵȴ��߳�����һ��
			cout("connected client start to communicate!");
			return linkedSocket;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	/*
	 * ���������ǽ����û������ˣ�user��
	 * ���������ṩ��Ӧ����
	 */
	public void service() {//���������������д���
		Socket mySocket=setupSocket();
		User myUser=new User(,mySocket, myDataBay);//id? ����֪����ô��ȡ
		myDataBay.user_list.add(myUser);//��user���뵽�б�
		myUser.start();
	}
	/*
	 * ����������������䲿��
	 * ����blockedThread_max���߳����μ����̳߳�
	 * ��������ȷ�Ϻ�����¼���һ���߳�
	 * ֮���Ի��涨blocked�̵߳���������Ϊ�˷�ֹ���ִ���blocked�̼߳����̳߳ص��·�����ֱ�ӱ���
	 */
	public void distrubuteThread() {//������������������̺߳�ʹ�̼߳����̳߳�
		while(true) {
			for(;blockedThread_number<blockedthread_max;blockedThread_number++) {
				Thread service_thread=new Thread(service_runnable);
				myThreadPool.submit(service_thread);
			}
		}
	}
	public Runnable service_runnable=new Runnable() {
		
		@Override
		public void run() {
			service();
		}
	};
	/*
	 * ��������
	 * ������ĵ���������
	 */
	public void start() {
		cout("ready to go");
		setupServerSocket();
		setupThreadPool(threadpool_type);
		distrubuteThread();
	}
}

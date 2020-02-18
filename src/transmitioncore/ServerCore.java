package transmitioncore;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.net.*;

public class ServerCore {

	/*
	 * ServerCore类是用来处理分发数据的类是整个socket通讯包的核心
	 * 该类应该拥有的功能有：
	 * 建立socket监听
	 * 创建线程池
	 * 分发数据
	 * 创建user对象
	 */
	/*
	 * 创建线程池的时候应该注意
	 * 由于受到不同设备的性能限制
	 * 所以应该写出多种线程池的创建方式
	 * 以满足不同设备的性能需求
	 */
	/********************变量表*********************/
	/********************对象定义变量表*********************/
	private int threadpool_type;
	private int blockedthread_max;
	private int portnumber;
	private boolean debugmode;
	private int threadpool_thread_number;
	/********************全局变量表*********************/
	/********************public*********************/
	public DataBay myDataBay;
	/********************private*********************/
	private ServerSocket myServerSocket;
	private ExecutorService myThreadPool;
	private int blockedThread_number;
	/********************函数部分*********************/
	
	/*
	 * 构造函数
	 * 功能：初始化各种变量
	 */
	public ServerCore(int m_threadpool_type,int m_blockedthread_max,int m_portmnuber,boolean m_debugmode) {
		threadpool_type=m_threadpool_type;
		blockedthread_max=m_blockedthread_max;
		portnumber=m_portmnuber;
		debugmode=m_debugmode;
		myDataBay=new DataBay();
	}
	public ServerCore(int m_threadpool_type,int m_blockedthread_max,int m_portmnuber) {//重载构造函数，默认非debug模式
		threadpool_type=m_threadpool_type;
		blockedthread_max=m_blockedthread_max;
		portnumber=m_portmnuber;
		debugmode=false;
		myDataBay=new DataBay();
	}
	/*
	 * 我觉得有必要建立测试用的推送机制
	 * 所以写了这个函数用于debug
	 */
	public void cout(String m_output) {//实在不知道起什么名字所以就用了cout，我就不信cout在java里面能重名
		if(debugmode==true) {
			System.out.println(m_output);
		}
	}
	/*
	 * 另一个的不够用的函数
	 * 主要是组装输出的信息
	 * 主要是懒
	 */
	public String getFunctionInfo(String m_function_name) {
		String string ="function "+m_function_name+"executed";
		return string;
	}
	/*
	 * 建立Scoket并监听的函数
	 * 监听端口为 portnumber
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
	 * 建立线程池
	 * 要建立两种线程池
	 * 第一种是fixed线程池拥有固定的线程数量
	 * 第二种是cached线程池拥有无限的线程数量
	 */
	public void setupCacheThreadPool() {//建立一个拥有无数量限制的线程池
		myThreadPool = Executors.newCachedThreadPool();
		cout("cached thread pool has been already seted up!");
	}
	public void setupFixedThreadPool(int m_threadpool_thread) {//建立一个有数量限制的线程池
		myThreadPool=Executors.newFixedThreadPool(m_threadpool_thread);
		cout("fixed thread pool has been already seted up!");
	}
	public void setupThreadPool(int m_threadpool_type) {//自动检测建立多大数量的线程池
		switch(m_threadpool_type) {
		case 0:setupCacheThreadPool();break;
		case 1:setupFixedThreadPool(threadpool_thread_number);break;
		}
		cout(getFunctionInfo("setupThreadPool"));
	}
	/*
	 * 建立Socket链接的函数
	 * 后面要把它加入线程池的一个线程里面
	 */
	public Socket setupSocket() {//这是建立Socket链接的函数
		try {
			Socket linkedSocket = myServerSocket.accept();
			blockedThread_number-= 1;//每链接成功一个就减一个，一直保持等待线程数量一定
			cout("connected client start to communicate!");
			return linkedSocket;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	/*
	 * 接下来就是建立用户对象了（user）
	 * 建立对象并提供相应功能
	 */
	public void service() {//添加其他功能请重写这个
		Socket mySocket=setupSocket();
		User myUser=new User(,mySocket, myDataBay);//id? 还不知道怎么获取
		myDataBay.user_list.add(myUser);//把user加入到列表
		myUser.start();
	}
	/*
	 * 接下来就是任务分配部分
	 * 建立blockedThread_max个线程依次加入线程池
	 * 当有链接确认后就再新加入一个线程
	 * 之所以还规定blocked线程的数量就是为了防止开局大量blocked线程加入线程池导致服务器直接崩溃
	 */
	public void distrubuteThread() {//这个函数是用来分配线程和使线程加入线程池
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
	 * 启动函数
	 * 传输核心的启动函数
	 */
	public void start() {
		cout("ready to go");
		setupServerSocket();
		setupThreadPool(threadpool_type);
		distrubuteThread();
	}
}

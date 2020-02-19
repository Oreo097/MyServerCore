package transmitioncore;

public class Main {
	/*
	 * 这里的写的是整个程序的实现 程序需要定义的全局变量都在这里
	 */
	public static void main(String[] args) {
		ShowInfo.showNetInfo();
		ShowInfo.showServerInfo();
		ServerCore myServerCore=new ServerCore(0, 10, 1900,true);
		myServerCore.start();
	}
}

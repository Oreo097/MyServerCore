package transmitioncore;

public class Main {
	/*
	 * �����д�������������ʵ�� ������Ҫ�����ȫ�ֱ�����������
	 */
	public static void main(String[] args) {
		ShowInfo.showNetInfo();//��ʾ��������
		ShowInfo.showServerInfo();//��ʾ����������
		ServerCore myServerCore=new ServerCore(0, 10, 1900,true);//��������
		myServerCore.start();//��������
	}
}

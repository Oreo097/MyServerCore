package test;

public class Test {
	public static void main(String[] args) {
		String a="782689160-helloworld-782689160";
		int a1=a.indexOf("-");
		int a2=a.lastIndexOf("-");
		String a_target_id=a.substring(0,a1);
		System.out.println(a_target_id);
		String a_Sender_id=a.substring(a2+1);
		System.out.println(a_Sender_id);
		int a3=Integer.parseInt(a_Sender_id);
		System.out.println(a3);
	}

}
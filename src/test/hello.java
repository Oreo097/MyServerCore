package test;

public class hello {
public String a;
hello(String a){
	this.a=a;
}
public void change(hello m_hello) {
	m_hello.a="a";
}
public hello get(hello m_hello) {
	return m_hello;
}
}

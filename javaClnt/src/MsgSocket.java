import java.io.*;
import java.net.*;
import java.util.*;
/*
 * MainFrame���� MsgSocket�� ȣ���ϰ� MsgSocket���� SSL�� ȣ���Ѵ�.
 * MainFrame�� �׳� �������� ��������. ����Ŭ������ �ۼ��ϸ�... ���� frame�� �����ϱ�. Ŭ���� ������.
 * ��¹��۴� flush�����. C������ str_len�� �Ἥ �׳� �Ǵ°Ű�.. �Է¹��۴� ���ʿ� flush�� ���� �Ӵ��� ���ʿ䵵 ���µ�??? 
 *  
 */
public class MsgSocket {
	private Socket sock = null;
	private Scanner scan;
//	private BufferedReader in;
//	private PrintWriter out;
	private BufferedInputStream in;
	private BufferedOutputStream out; //flush�� �Բ�.
	private byte[] rcvmsg; //�ϴ��� msg�� ¥��.
	private byte[] sndmsg;
	


	public MsgSocket(){
		try{
			sock = new Socket("127.0.0.1", 9797);	//in�� out�� ����. ���ϱ���ڸ� 2�� �Ҵ��ϴ� �� ��������.
			scan = new Scanner(System.in);
//			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
//			out = new PrintWriter(sock.getOutputStream(), false);//false�� auto flush�� false��� ��.
			in = new BufferedInputStream(sock.getInputStream());
			out = new BufferedOutputStream(sock.getOutputStream());
			rcvmsg = new byte[1024];
		}catch( UnknownHostException e){
			System.out.println(e.toString());
			System.exit(1);
		}catch( IOException e){
			System.out.println(e.toString());
			System.exit(1);
		}
		
	}
	
	public void waitMsg() throws IOException{
		int i=0;
		int t;
		String s = new String(rcvmsg);
		while((t = in.read()) != -1){
			System.out.println(t);
			rcvmsg[i++] = (byte)t;
		}
		System.out.println("FROM : "+s);
	}
	
	public void sendMsg(String plain) throws IOException{
		//��ȣȭ���ְ�.
		
		sndmsg = new String("dd"+plain).getBytes();
		String s = new String(sndmsg);
		System.out.println("TO : "+s);
		out.write(sndmsg);
		out.flush();
	}
	
	public void closeSock(){
		
		try {
			scan.close();
			in.close();
			out.close();
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}

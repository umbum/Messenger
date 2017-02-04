import java.io.*;
import java.net.*;
import java.util.*;
/*
 * MainFrame에서 MsgSocket을 호출하고 MsgSocket에서 SSL을 호출한다.
 * MainFrame은 그냥 껍데기라고 생각하자. 내부클래스로 작성하면... 여러 frame을 못쓰니까. 클래스 나누고.
 * 출력버퍼는 flush해줘야. C에서는 str_len을 써서 그냥 되는거고.. 입력버퍼는 애초에 flush가 없을 뿐더러 할필요도 없는듯??? 
 *  
 */
public class MsgSocket {
	private Socket sock = null;
	private Scanner scan;
//	private BufferedReader in;
//	private PrintWriter out;
	private BufferedInputStream in;
	private BufferedOutputStream out; //flush와 함께.
	private byte[] rcvmsg; //일단은 msg로 짜자.
	private byte[] sndmsg;
	


	public MsgSocket(){
		try{
			sock = new Socket("127.0.0.1", 9797);	//in과 out을 구분. 파일기술자를 2개 할당하는 것 같은느낌.
			scan = new Scanner(System.in);
//			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
//			out = new PrintWriter(sock.getOutputStream(), false);//false는 auto flush가 false라는 뜻.
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
		//암호화해주고.
		
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

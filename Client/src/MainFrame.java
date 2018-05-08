import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;


public class MainFrame extends JFrame{ 
	private JPanel loginpanel;
	private JPanel panel;
	private BufferedImage img = null, loginimg = null;
	private JTextField textfield;	
	private MsgSocket s;
	public static void main(String[] args) {
		new MainFrame();
	}
	
	public MainFrame() {
		s = new MsgSocket();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(350, 600);
		setLocation(screenSize.width / 2 - 150, screenSize.height / 2 - 350);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Talk");

		ImageIcon icon = new ImageIcon("icon.jpg");
		setIconImage(icon.getImage());

		try{
			img = ImageIO.read(new File("main.jpg"));
			loginimg = ImageIO.read(new File("main.jpg"));
		}catch (IOException e){
			System.out.println(e.getMessage());
			System.exit(0);
		}

		// JPanel toppanel = new JPanel();
		// add(toppanel, BorderLayout.NORTH);
						
		//main panel
		panel = new JPanel(){
			public void paintComponent(Graphics g){
				g.drawImage(img, 0, 0, 350, 600, null);
			}
		};
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		add(new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
		
		//main panel위에 login panel붙이고 로그인시 remove
		loginpanel = new LoginPanel();
		add(loginpanel, BorderLayout.CENTER);
		
		setVisible(true);
	}
	


	private class LoginPanel extends JPanel { //login과 main의 배경변경 때문에라도 inner class 사용해야..
		public LoginPanel() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			textfield = new JTextField(15);
			textfield.setMaximumSize(new Dimension(220, 35));
			textfield.setAlignmentX(CENTER_ALIGNMENT);
			textfield.addActionListener(new TextListener());
			JPanel emptyPanel = new JPanel();
			emptyPanel.setMaximumSize(new Dimension(0, 300));
			JLabel informLabel = new JLabel("nickname                                                   ");
			informLabel.setAlignmentX(CENTER_ALIGNMENT);
			add(emptyPanel);
			add(informLabel);
			add(textfield);
		}
		public void paintComponent(Graphics g){
			g.drawImage(loginimg, 0, 0, 350, 600, null);
		}
	}
	
	private class TextListener implements ActionListener{ //내부클래스로 작성해야만 s 사용가능.
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				s.sendMsg(textfield.getText());
				//System.out.println("TO : "+textfield.getText());
				s.waitMsg();
			}catch(IOException ex){
				System.out.println(ex.getMessage());
				System.exit(1);
			}
		}
	}
	//사용자 클릭하면 대화창 열리도록 하는거.. Listner달아서 해결하고.. 리스트 또는 버튼을.. 근데 리스트가 젤 편할거같아.
	//리스트로 만들고 테마를 바꿔보자. 리스트가 지원하는 의미적이벤트는 ListSelection 하나임. 딱히 쓸일 없을듯. Key와 Mouse를 활용.
	private class ListListener implements MouseListener, KeyListener{
		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
		
	}
}


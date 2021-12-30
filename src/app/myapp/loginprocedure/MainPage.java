package app.myapp.loginprocedure;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import app.myapp.loginprocedure.DAO.MemberDAO;
import app.myapp.loginprocedure.Util.JDBCUtil;
import app.myapp.loginprocedure.VO.MemberVO;

public class MainPage extends JFrame implements ActionListener, WindowListener{
	
	//TODO 얘가 userID 들고 있어야 함 
	JButton logoutButton, changeInfoButton, withdrawButton;
	JPanel panel;
	String userName;
	
	public MainPage(String userName) {
		setTitle("Main Page");
		setSize(300,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(this);
		
		this.userName = userName;
		
		logoutButton = new JButton("로그아웃");
		changeInfoButton = new JButton("회원 정보 변경");
		withdrawButton = new JButton("회원 탈퇴");
		
		panel = new JPanel(new GridLayout(0,1,4,4));
		panel.add(logoutButton);
		logoutButton.addActionListener(this);
		panel.add(changeInfoButton);
		changeInfoButton.addActionListener(this);
		panel.add(withdrawButton);
		withdrawButton.addActionListener(this);
		
		add(panel,BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton b = (JButton) e.getSource();
		
		if(b == logoutButton) {
			new LoginProcedure();
			dispose();
		}
		if(b == changeInfoButton) {
			new ChangeInfoPage(userName);
			dispose();
		}
		if(b == withdrawButton) {
			int result = JOptionPane.showConfirmDialog(null,"정말로 탈퇴하시겠어요?ㅜㅠ","회원탈퇴",JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				MemberDAO dao = new MemberDAO();
				MemberVO vo = dao.getMember(userName);
				dao.deleteMember(vo);
				JDBCUtil.closeConnection();
				new LoginProcedure();
				dispose();
			}
		}
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
	}
	@Override
	public void windowClosing(WindowEvent e) {
		JDBCUtil.closeConnection();
	}
	@Override
	public void windowClosed(WindowEvent e) {
	}
	@Override
	public void windowIconified(WindowEvent e) {
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
	}
	@Override
	public void windowActivated(WindowEvent e) {
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
	}

}

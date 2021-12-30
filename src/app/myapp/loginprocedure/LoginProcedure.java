package app.myapp.loginprocedure;

import java.awt.GridLayout;
import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import app.myapp.loginprocedure.DAO.MemberDAO;
import app.myapp.loginprocedure.Util.JDBCUtil;

public class LoginProcedure extends JFrame implements ActionListener, WindowListener{
	
	JPanel loginPanel;
	JLabel userLabel, passwordLabel, message;
	JTextField userInput;
	JPasswordField passwordInput;
	JButton submit, signup;
	
	LoginProcedure() {
		
		setTitle("Login");
		setSize(300,100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(this);
		
		message = new JLabel("Sign IN");
		message.setHorizontalAlignment(JLabel.CENTER);
		add(message,BorderLayout.NORTH);
		
		userLabel = new JLabel();
		userLabel.setText("User Name");
		userInput = new JTextField();
		
		passwordLabel = new JLabel();
		passwordLabel.setText("Password");
		passwordInput = new JPasswordField();
		
		signup = new JButton("Sign up");
		submit = new JButton("Login");
		signup.addActionListener(this);
		submit.addActionListener(this);
		
		loginPanel = new JPanel(new GridLayout(3,1));
		loginPanel.add(userLabel);
		loginPanel.add(userInput);
		loginPanel.add(passwordLabel);
		loginPanel.add(passwordInput);
		
		loginPanel.add(signup);
		loginPanel.add(submit);
		add(loginPanel,BorderLayout.CENTER);
		
		setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton b = (JButton) e.getSource();
		
		if(b==signup) {
			System.out.println("Sign Up");
			new SignUpPage();
			dispose();
		}
		else if(b==submit) {
			String userName = userInput.getText();
			String password = String.valueOf(passwordInput.getPassword());
			
			MemberDAO dao = new MemberDAO();
			String dbPassword = dao.getPassword(userName);
			JDBCUtil.closeConnection();
			if (dbPassword.equals("")) {
				message.setText("없는 아이디입니다");
			}
			else if (!dbPassword.equals(password)) {
				message.setText("비밀번호가 옳지 않습니다");
			}
			else if (userName.equals("admin")) {
				new AdminPage();
				dispose();
			}
			else {
				new MainPage(userName);
				dispose();
			}
		}
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new LoginProcedure();
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

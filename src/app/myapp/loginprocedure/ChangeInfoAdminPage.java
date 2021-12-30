package app.myapp.loginprocedure;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import app.myapp.loginprocedure.DAO.MemberDAO;
import app.myapp.loginprocedure.Util.DateLabelFormatter;
import app.myapp.loginprocedure.Util.JDBCUtil;
import app.myapp.loginprocedure.VO.MemberVO;

public class ChangeInfoAdminPage extends JFrame implements ActionListener, WindowListener{
	
	JPanel loginPanel;
	JLabel userIDLabel, userPasswordLabel, nameLabel, birthLabel, sexLabel, emailLabel, message, userIDInput, blank;
	JTextField nameInput, emailInput;
	JPasswordField userPasswordInput, userPasswordReInput;
	JButton submit, cancel;
	JComboBox sexInput, emailDomain;
	JDatePickerImpl datePicker;
	String email;
	String[] sexList = {"남자", "여자"};
	String[] domainList = {"직접 입력","@naver.com", "@gmail.com", "@daum.net", "@handong.edu"};
	String userName;
	MemberDAO dao;
	MemberVO vo;
	

	public ChangeInfoAdminPage(String userName){
		setTitle("회원 정보 변경");
		setSize(300,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(this);
		
		this.userName = userName;
		dao = new MemberDAO();
		vo = dao.getMember(userName);
		
		message = new JLabel("");
		message.setHorizontalAlignment(JLabel.CENTER);
		add(message,BorderLayout.NORTH);
		
		userIDLabel = new JLabel("아이디");
		userIDInput = new JLabel(vo.getUserID());
		
		userPasswordLabel = new JLabel("비밀번호 * (8자리 이상)");
		userPasswordInput = new JPasswordField(vo.getUserPassword());
		
		nameLabel = new JLabel("이름 *");
		nameInput = new JTextField(vo.getName());
		
		birthLabel = new JLabel("생년월일 *");
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		
		sexLabel = new JLabel("성별 *");
		sexInput = new JComboBox(sexList);
		sexInput.setSelectedItem(vo.getSex());
		
		emailLabel = new JLabel("본인 확인 이메일 *");
		emailInput = new JTextField(vo.getEmail());
		blank = new JLabel("");
		emailDomain = new JComboBox(domainList);
		emailDomain.addActionListener(this);
		
		cancel = new JButton("취소");
		cancel.addActionListener(this);
		submit = new JButton("정보 수정");
		submit.addActionListener(this);
		
		loginPanel = new JPanel(new GridLayout(0,2));
		loginPanel.add(userIDLabel);
		loginPanel.add(userIDInput);
		loginPanel.add(userPasswordLabel);
		loginPanel.add(userPasswordInput);
		loginPanel.add(nameLabel);
		loginPanel.add(nameInput);
		loginPanel.add(birthLabel);
		loginPanel.add(datePicker);
		loginPanel.add(sexLabel);
		loginPanel.add(sexInput);
		loginPanel.add(emailLabel);
		loginPanel.add(emailInput);
		loginPanel.add(blank);
		loginPanel.add(emailDomain);
		loginPanel.add(cancel);
		loginPanel.add(submit);
		
		add(loginPanel,BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			JComboBox cb = (JComboBox) e.getSource();
			if(!emailInput.getText().contains("@") || !emailInput.getText().contains(".")) {
				email = emailInput.getText();
			}
			if(cb == emailDomain && !emailDomain.getSelectedItem().toString().equals("직접 입력")) {
				emailInput.setText(email+emailDomain.getSelectedItem().toString());
			}
			if(cb == emailDomain && emailDomain.getSelectedItem().toString().equals("직접 입력")) {
				emailInput.setText(email);
			}
		} catch (ClassCastException CCe) {
			try {
				JButton b = (JButton) e.getSource();
				
				if(b==cancel) {
					JDBCUtil.closeConnection();
					new AdminPage();
					dispose();
				}
				else if (b==submit) {
					if(userPasswordInput.getPassword().length < 8) {
						message.setText("비밀번호를 8자리 이상 지정해주세요");
						message.setForeground(Color.RED);
					}
					else if(!emailInput.getText().contains("@") || !emailInput.getText().contains(".")) {
						message.setText("이메일을 형식에 맞게 입력해주세요");
						message.setForeground(Color.RED);
					}
					else {
							MemberVO vo = new MemberVO();
							vo.setUserID(userIDInput.getText());
							vo.setUserPassword(String.valueOf(userPasswordInput.getPassword()));
							vo.setName(nameInput.getText());
							
							java.util.Date datee = (java.util.Date) datePicker.getModel().getValue();
							java.sql.Date sqlDate = new java.sql.Date(datee.getTime());
							vo.setBirth(sqlDate);
							
							vo.setSex(sexInput.getSelectedItem().toString());
							vo.setEmail(emailInput.getText());
							
							dao.updateMember(vo);
							JDBCUtil.closeConnection();
							new AdminPage();
							dispose();
					}
				}
			} catch (NullPointerException NPe) {
				message.setText("모든 항목을 입력하여 주세요");
				message.setForeground(Color.RED);
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

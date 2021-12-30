package app.myapp.loginprocedure;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import app.myapp.loginprocedure.DAO.MemberDAO;
import app.myapp.loginprocedure.Util.JDBCUtil;
import app.myapp.loginprocedure.VO.MemberVO;

import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Date;
import java.util.List;

public class AdminPage extends JFrame implements ActionListener, WindowListener{
	
	JButton logoutButton, changeInfoButton, withdrawButton;
	MemberDAO dao;
	MemberVO vo;
	List<MemberVO> list;
	JTable table;
	DefaultTableModel tableModel;
	
	AdminPage(){
		setTitle("Admin Page");
		setSize(900,550);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(this);
		
		dao = new MemberDAO();
		list = dao.getBoardList();
		
		String[] columns = {"UserID", "UserPassword", "Name", "Birth", "Sex", "Email" , "", ""};
		tableModel = new DefaultTableModel(columns, 0);
		
		for(int i=0; i<list.size(); i++) {
			String UserID = list.get(i).getUserID();
			String UserPassword = list.get(i).getUserPassword();
			String name = list.get(i).getName();
			Date birth = list.get(i).getBirth();
			String sex = list.get(i).getSex();
			String email = list.get(i).getEmail();
			JButton changeInfoButton = new JButton("회원 정보 수정");
			JButton withdrawButton = new JButton("탈퇴 처리");
			Object data[] = {UserID, UserPassword, name, birth, sex, email, changeInfoButton, withdrawButton};
			tableModel.addRow(data);
		}
		
		table = new JTable(tableModel);
		
		table.getColumnModel().getColumn(6).setCellRenderer(new editTableCell());
		table.getColumnModel().getColumn(6).setCellEditor(new editTableCell());
		table.getColumnModel().getColumn(7).setCellRenderer(new delTableCell());
		table.getColumnModel().getColumn(7).setCellEditor(new delTableCell());
		
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane,BorderLayout.CENTER);
		
		logoutButton = new JButton("로그아웃");
		logoutButton.setHorizontalAlignment(JButton.CENTER);
		logoutButton.addActionListener(this);
		add(logoutButton,BorderLayout.SOUTH);
		
		
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
	}
	
	class editTableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
        JButton jb;
 
        public editTableCell() {
            jb = new JButton("회원 정보 수정");
            jb.addActionListener(e -> {
            	JTableEditMember();
            });
        }
 
        @Override
        public Object getCellEditorValue() {
            return null;
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            return jb;
        }
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            return jb;
        }
    }
	
	class delTableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
        JButton jb;
 
        public delTableCell() {
            jb = new JButton("탈퇴 처리");
            jb.addActionListener(e -> {
            	JTableRemoveMember();
            });
        }
 
        @Override
        public Object getCellEditorValue() {
            return null;
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            return jb;
        }
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            return jb;
        }
    }
	
	@SuppressWarnings("rawtypes")
    public void JTableEditMember() {
		int row = table.getSelectedRow();
		String userName = (String)table.getValueAt(row,0);
		System.out.println(userName);
		new ChangeInfoAdminPage(userName);
		dispose();
    } 
	
	@SuppressWarnings("rawtypes")
    public void JTableRemoveMember() {
		int row = table.getSelectedRow();
		String userName = (String)table.getValueAt(row,0);
		System.out.println(userName);
		int result = JOptionPane.showConfirmDialog(null,"정말로 탈퇴처리하시겠습니까?","회원탈퇴",JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			MemberDAO dao = new MemberDAO();
			MemberVO vo = dao.getMember(userName);
			dao.deleteMember(vo);
			JDBCUtil.closeConnection();
			new LoginProcedure();
			dispose();
		}
		new AdminPage();
		dispose();
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

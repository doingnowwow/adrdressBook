package hs.addressbook.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class InsertGroupDialog extends JDialog{
	
	private JPanel mainContentPane;
//	private JPanel topPane;
	private JPanel centerpane;
	private JLabel lbltop;
	private JTextField txtinsertGroup;
	private JButton btnOk;
	private Frame frame;

	public InsertGroupDialog(Frame frame, String title) {
		super(frame, title);
		setModal(true);
		setResizable(false);
		setSize(200,200);
		this.start();
	}
	
	
	public void start() {
		
		mainContentPane = new JPanel();
		mainContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainContentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(mainContentPane);
		
		
		centerpane = new JPanel();
		centerpane.setLayout(new FlowLayout());
		lbltop = new JLabel("그룹 추가");
		centerpane.add(lbltop,new FlowLayout(FlowLayout.CENTER,5,5));
		mainContentPane.add(centerpane,BorderLayout.NORTH);
		
		
		
		
		centerpane = new JPanel();
		centerpane.setLayout(new FlowLayout());
		mainContentPane.add(centerpane,BorderLayout.CENTER);
		txtinsertGroup = new JTextField();
		txtinsertGroup.setSize(100, 10);
		centerpane.add(txtinsertGroup,new FlowLayout(FlowLayout.CENTER,5,5));
		
		btnOk = new JButton("그룹 추가");
		mainContentPane.add(btnOk,BorderLayout.SOUTH);
		
		
	}

	
}

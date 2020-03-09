package hs.addressbook.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import hs.addressbook.ui.AddressBookMainUI;

public class PickData extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JButton btnDB;
	private JLabel lblTop;
	private JLabel lblQ;
	private JButton btnFile;

	static PickData frame = new PickData();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//					PickData frame = new PickData();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PickData() {
		jbInit();
	}

	private void jbInit() {

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
		}

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 444, 282);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(null);

		btnDB = new JButton("데이터베이스");
		btnDB.setBounds(255, 150, 132, 40);
		panel.add(btnDB);

		lblTop = new JLabel("주소록");
		lblTop.setFont(new Font("휴먼모음T", Font.BOLD, 40));
		lblTop.setBounds(158, 18, 139, 61);
		panel.add(lblTop);

		lblQ = new JLabel("실행 할 방식을 선택하세요");
		lblQ.setBounds(133, 89, 157, 40);
		panel.add(lblQ);

		btnFile = new JButton("파일");
		btnFile.setBounds(55, 150, 132, 40);
		panel.add(btnFile);

		contentPane.updateUI();
		panel.updateUI();
		btnDB.updateUI();
		lblTop.updateUI();
		lblQ.updateUI();
		btnFile.updateUI();

		// 파일선택
		btnFile.addActionListener(e -> {
			String data = "file";
			AddressBookMainUI main = new AddressBookMainUI(data);
			main.setLocationRelativeTo(null);
			main.setVisible(true);
			frame.setVisible(false);

		});
		// 데이터베ㅣ스 선택
		btnDB.addActionListener(e -> {
			String data = "database";
			AddressBookMainUI main = new AddressBookMainUI(data);
			main.setLocationRelativeTo(null);
			main.setVisible(true);
			frame.setVisible(false);

		});

	}
}

package hs.addressbook.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.SwingConstants;
import javax.swing.JSplitPane;
import java.awt.ScrollPane;
import java.awt.Panel;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JTree;

public class TestAddressBook extends JFrame {
	private JSplitPane splitPane;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JScrollPane scrollPane_1;
	private JPanel panel_3;
	private JPanel panel_4;
	private JScrollPane scrollPane;
	private JTree tree;
	private JPanel panel_5;
	private JLabel lblNewLabel;



	/**
	 * Create the frame.
	 */
	public TestAddressBook() {
		
		jbInit();
	}
	
	
	
	private void jbInit() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1057, 592);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		splitPane = new JSplitPane();
		getContentPane().add(splitPane);
		
		panel = new JPanel();
		splitPane.setRightComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.SOUTH);
		
		scrollPane_1 = new JScrollPane();
		panel.add(scrollPane_1, BorderLayout.CENTER);
		
		panel_3 = new JPanel();
		splitPane.setLeftComponent(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.SOUTH);
		
		scrollPane = new JScrollPane();
		panel_3.add(scrollPane, BorderLayout.CENTER);
		
		tree = new JTree();
		scrollPane.setViewportView(tree);
		
		panel_5 = new JPanel();
		panel_3.add(panel_5, BorderLayout.NORTH);
		
		lblNewLabel = new JLabel("그룹관리");
		panel_5.add(lblNewLabel);
	}
}

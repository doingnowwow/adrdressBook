package hs.addressbook.test;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Test extends JFrame {

	// 예시로 쓰일 컴포넌트들
	JButton btn1 = new JButton("버튼");
	JTextField tf = new JTextField();
	JTextArea ta = new JTextArea();
	JComboBox<String> cb = new JComboBox<String>();
	JMenuBar mb = new JMenuBar();
	JMenu menu = new JMenu("메뉴");
	JMenuItem i1 = new JMenuItem("하위메뉴1");
	JMenuItem i2 = new JMenuItem("하위메뉴2");
	JMenuItem i3 = new JMenuItem("하위메뉴3");
	JScrollBar sb = new JScrollBar();
	JRadioButton rb = new JRadioButton("라디오 버튼");

	public Test() {
		super("LookAndFeelTest");

		try {
			// 현재 가지고 있는 룩앤필 목록

//	          UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
//	          UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
//	          UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
//	          UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
//	          UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
//	          UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
//	          UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
//	          UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
//	          UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");
//	          UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
//	          UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
//	          UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
//	          UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
//	          UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
	          UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

//	          새롭게 작성된 JFrame이 현재의 룩앤필에 의해 제공되는 윈도우 수식(경계, 윈도우 클로즈, 타이틀바 등)을
//	          사용할지 선택하는 함수이다. true인 경우 swing형식, false일 경우 현재 윈도우즈의 형식으로 변경
	          setDefaultLookAndFeelDecorated(true);       // 주석처리해도 사실 큰 차이는 없는 거 같다.

		} catch (Exception e) {
		}

		setBounds(300, 300, 300, 300);
		setLayout(new GridLayout(3, 2));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.add(sb);

		cb.addItem("선택1");
		cb.addItem("선택2");
		cb.addItem("선택3");

		mb.add(menu);
		menu.add(i1);
		menu.add(i2);
		menu.add(i3);

		setJMenuBar(mb);

		add(btn1);
		add(tf);
		add(ta);
		add(cb);
		add(panel);
		add(rb);

		// 룩앤필을 적용한 후에는 추가한 컴포넌트들의 updateUI() 메소드를 호출해야 한다.
		btn1.updateUI();
		tf.updateUI();
		ta.updateUI();
		cb.updateUI();
		sb.updateUI();
		rb.updateUI();

		setVisible(true);
	}

	public static void main(String[] args) {
		new Test();
	}

}

package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import data.UserVO;
import handler.FileHandler;

public class UpdateAddressDialog extends JDialog {

	private UserVO updateuser = new UserVO();
	private JPanel mainContentPane;
	private JPanel southPane;
	private JPanel northPane;
	private JLabel lblTop;
	private JPanel centerPane;

	// 라벨
	private JLabel lblName;
	private JLabel lblPhone;
	private JLabel lblEmail;
	private JLabel lblGol;
	private JLabel lblCom;
	private JLabel lblDepartment;
	private JLabel lblPosition;
	private JLabel lblMemo;
	private JLabel lblGroup;

	// txt필드
	private JTextField txtName;
	private JTextField txtPhone;
	private JTextField txtEmail;
	private JTextField txtEmail2;
	private JTextField txtCom;
	private JTextField txtDepartment;
	private JTextField txtPosition;
	private JTextArea txtMemo;
	private JTextField txtGroup;
	private JTextField txtNo;

	private JTextField text = new JTextField(10);
	private JButton btnReset = new JButton("원래대로");
	private JButton btnUpdate = new JButton("수정완료");
	private JButton btnClose = new JButton("닫기");
	private JPanel mainPanel = new JPanel();

	private IAddUser listner = null;
	private AddressBookMainUI addressBookMainUI = null;

	private String userNo = "";

	public UpdateAddressDialog(IAddUser listner, String title, UserVO user) {
		this.updateuser = user;
		this.listner = listner;
		this.setTitle(title);
		this.setModal(true);
		this.setResizable(false);
		this.initUI();

	}

	public UpdateAddressDialog(AddressBookMainUI addressBookMainUI, String title, UserVO user) {
		this.addressBookMainUI = addressBookMainUI;
		this.updateuser = user;
		this.setTitle(title);
		this.setModal(true);
		this.setResizable(false);
		this.initUI();
	}

	public void initUI() {

		// 메인페널
		mainContentPane = new JPanel();
		mainContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainContentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(mainContentPane);

		// 가운데 부분 시작
		centerPane = new JPanel();
		centerPane.setLayout(null);
		mainContentPane.add(centerPane, BorderLayout.CENTER);

		// 라벨
		lblName = new JLabel("이   름");
		lblPhone = new JLabel("폰번호");
		lblEmail = new JLabel("이메일");
		lblGol = new JLabel(" @ ");
		lblCom = new JLabel("회   사");
		lblDepartment = new JLabel("부   서");
		lblPosition = new JLabel("직   책");
		lblMemo = new JLabel("메   모");
		lblGroup = new JLabel("그   룹");

		// 라벨위치
		lblName.setBounds(10, 10, 100, 30);
		lblPhone.setBounds(10, 50, 100, 30);
		lblEmail.setBounds(10, 100, 100, 30);
		lblGol.setBounds(180, 100, 100, 30);
		lblCom.setBounds(10, 150, 100, 30);
		lblDepartment.setBounds(10, 200, 100, 30);
		lblPosition.setBounds(10, 250, 100, 30);
		lblMemo.setBounds(10, 300, 100, 30);
		lblGroup.setBounds(10, 400, 100, 30);

		// 라벨셋팅
		centerPane.add(lblName);
		centerPane.add(lblPhone);
		centerPane.add(lblEmail);
		centerPane.add(lblGol);
		centerPane.add(lblCom);
		centerPane.add(lblDepartment);
		centerPane.add(lblMemo);
		centerPane.add(lblPosition);
		centerPane.add(lblGroup);

		// 텍스트필드
		txtName = new JTextField();
		txtPhone = new JTextField();
		txtEmail = new JTextField();
		txtEmail2 = new JTextField();
		txtCom = new JTextField();
		txtDepartment = new JTextField();
		txtPosition = new JTextField();
		txtMemo = new JTextArea();
		txtGroup = new JTextField();
		txtNo = new JTextField();

		// 이메일리스트
		String mailList[] = { "naver.com", "gmail.com", "kakao.com", "hanmail.net", "직접입력하기" };
		JComboBox mailCombo = new JComboBox(mailList);

		// 그룹리스트
		JComboBox<?> groupCombo = new JComboBox(FileHandler.getInstance().getGroupList().toArray());
		

		// 텍스트필드 값 넣기
		userNo = String.valueOf(updateuser.getAd_no());
		txtNo.setText(userNo);

		txtName.setText(updateuser.getAd_name());

		if (updateuser.getAd_hp() != null) {
			txtPhone.setText(updateuser.getAd_hp());
		}
		if (updateuser.getAd_mail() != null && updateuser.getAd_mail().length() > 2) {
			if (updateuser.getAd_mail().contains(("@"))) {
				txtEmail.setText(updateuser.getAd_mail().split("@")[0]);
				txtEmail2.setText(updateuser.getAd_mail().split("@")[1]);
			}
		}
		if (updateuser.getAd_com() != null) {
			txtCom.setText(updateuser.getAd_com());
		}
		if (updateuser.getAd_department() != null) {
			txtDepartment.setText(updateuser.getAd_department());
		}
		if (updateuser.getAd_postion() != null) {
			txtPosition.setText(updateuser.getAd_postion());
		}
		if (updateuser.getAd_memo() != null) {
			txtMemo.setText(updateuser.getAd_memo());
		}
//		if (updateuser !=null) {
//			txtGroup.setText(String.valueOf(updateuser.getGroup_no()));
//		}

		// 텍스트필드위치
		txtName.setBounds(100, 10, 200, 25);
		txtPhone.setBounds(100, 50, 200, 25);
		txtEmail.setBounds(100, 100, 80, 25);
		txtEmail2.setBounds(200, 100, 100, 25);
		mailCombo.setBounds(310, 100, 100, 25);
		txtCom.setBounds(100, 150, 200, 25);
		txtDepartment.setBounds(100, 200, 200, 25);
		txtPosition.setBounds(100, 250, 200, 25);
		txtMemo.setBounds(100, 300, 310, 80);
		txtGroup.setBounds(100, 400, 200, 25);
		groupCombo.setBounds(310, 400, 100, 25);
		txtNo.setBounds(100, 450, 100, 25);

		// 콤보박스 사용위해 임의 입력 막기 (이메일 ,그룹)
//		txtEmail2.disable();
		txtEmail2.setEnabled(false);
		txtGroup.setEditable(false);

		// 이메일 선택 콤보박스 선택 이벤트
		mailCombo.addActionListener(e -> {
			txtEmail2.setText(mailCombo.getItemAt(mailCombo.getSelectedIndex()).toString());
			String userEmail = mailCombo.getItemAt(mailCombo.getSelectedIndex()).toString();
			txtEmail2.setEnabled(false);

			if (userEmail.equals("직접입력하기")) {
				txtEmail2.setEnabled(true);
				txtEmail2.setText("");
			}

		});

		// 그룹 콤보박스 선택시 이벤트
		groupCombo.addActionListener(e -> {
			txtGroup.setText(groupCombo.getItemAt(groupCombo.getSelectedIndex()).toString());

		});

		// 텍스트필드 셋팅
		centerPane.add(txtName);
		centerPane.add(txtPhone);
		centerPane.add(txtEmail);
		centerPane.add(txtEmail2);
		centerPane.add(mailCombo);
		centerPane.add(txtCom);
		centerPane.add(txtDepartment);
		centerPane.add(txtPosition);
		centerPane.add(txtMemo);
		centerPane.add(txtGroup);
		centerPane.add(groupCombo);
		centerPane.add(txtNo);

		// 번호받는 부분 숨겨버리기~
		txtNo.setVisible(false);

		// 상단부분
		lblTop = new JLabel("수정");
		northPane = new JPanel(new FlowLayout());
		mainContentPane.add(northPane, BorderLayout.NORTH);
		northPane.add(lblTop);

		// south부분
		southPane = new JPanel();
		mainContentPane.add(southPane, BorderLayout.SOUTH);

		// south 버튼정렬
		southPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		southPane.add(btnReset);
		southPane.add(btnUpdate);
		southPane.add(btnClose);

		// 닫기버튼이벤트
		btnClose.addActionListener(e -> {
			setVisible(false);
		});

		// 원래대로하는이벤트
		btnReset.addActionListener(e -> {

			if (updateuser.getAd_name() != null) {
				txtName.setText(updateuser.getAd_name());
			}

			if (updateuser.getAd_hp() != null) {
				txtPhone.setText(updateuser.getAd_hp());
			}
			if (updateuser.getAd_mail().length() > 2) {
				txtEmail.setText(updateuser.getAd_mail().split("@")[0]);
				txtEmail2.setText(updateuser.getAd_mail().split("@")[1]);
			}
			if (updateuser.getAd_com() != null) {
				txtCom.setText(updateuser.getAd_com());
			}
			if (updateuser.getAd_department() != null) {
				txtDepartment.setText(updateuser.getAd_department());
			}
			if (updateuser.getAd_postion() != null) {
				txtPosition.setText(updateuser.getAd_postion());
			}
			if (updateuser.getAd_memo() != null) {
				txtMemo.setText(updateuser.getAd_memo());
			}
			if (updateuser.getGroup_no() != 0) {
				txtGroup.setText(String.valueOf(updateuser.getGroup_no()));
			}

		});

		// 수정완료버튼

		btnUpdate.addActionListener(e -> {

			updateuser = new UserVO();

			// 이름 필수 입력 발리데이션
			if (txtName.getText().length() <= 0) {
				JOptionPane.showMessageDialog(mainContentPane, "이릅입력은 필수입니다. \n이름을입력해주세요\n", "경고",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// 핸드폰번호 or 이메일 입력 발리데이션
			if (txtPhone.getText().length() <= 0 && txtEmail.getText().length() <= 0) {
				JOptionPane.showMessageDialog(mainContentPane, "핸드폰번호 또는 이메일  둘중 하나는 \n필수로 입력사항입니다.\n 입력해주세요.", "경고",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (txtPhone.getText().trim().length() > 0) {
				boolean no = false;
				no = isPhone(txtPhone.getText());
				System.out.println(no);
				System.out.println(txtPhone.getText());

				if (no == false) {
					JOptionPane.showMessageDialog(mainContentPane, "핸드폰 번호 입력 형식이 잘못되었습니다.", "경고",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}

			// 값 보내기

			updateuser.setAd_no(Integer.parseInt(txtNo.getText()));
			updateuser.setAd_name(txtName.getText());
			updateuser.setAd_hp(txtPhone.getText());
			if (txtEmail.getText().trim().length()>1) {
				updateuser.setAd_mail(txtEmail.getText() + "@" + txtEmail2.getText());
			} else {
				updateuser.setAd_mail("");
			}
			updateuser.setAd_com(txtCom.getText());
			updateuser.setAd_department(txtDepartment.getText());
			updateuser.setAd_postion(txtPosition.getText());
			updateuser.setAd_memo(txtMemo.getText());

			updateuser.setGroup_no(groupCombo.getSelectedIndex()+1);
			this.addressBookMainUI.updateUser(updateuser);

			// 등록하면서 값 비우기
			txtName.setText("");
			txtPhone.setText("");
			txtEmail.setText("");
			txtEmail2.setText("");
			txtCom.setText("");
			txtDepartment.setText("");
			txtPosition.setText("");
			txtMemo.setText("");

			// 화면꺼
			setVisible(false);
			FileHandler.getInstance().updateUser(updateuser);

		});

		setSize(450, 600);
	}

	// 핸드폰번호 유효성검사
	private boolean isPhone(String hp) {

		Pattern p = Pattern.compile("^\\d{3}-\\d{3,4}-\\d{4}$");
		System.out.println("===유효성검사메서드===");
		System.out.println(hp);

		Matcher m = p.matcher(hp);

		if (m.find()) {

			return true;

		} else {
			return false;
		}
	}
}

package hs.addressbook.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;
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

import hs.addressbook.data.GroupVO;
import hs.addressbook.data.UserVO;
import hs.addressbook.handler.file.FileHandler;

public class UpdateAddressDialog extends JDialog {

	private UserVO updateuser = new UserVO();

	private ArrayList<GroupVO> groupList = new ArrayList<GroupVO>();
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
	private String selectedGroup = "";
	private int cnt = 0;

	private JTextField text = new JTextField(10);
	private JButton btnReset = new JButton("원래대로");
	private JButton btnUpdate = new JButton("수정완료");
	private JButton btnClose = new JButton("닫기");
	private JPanel mainPanel = new JPanel();
	private JComboBox<GroupVO> groupCombo;

	private IAddUser listner = null;
	private AddressBookMainUI addressBookMainUI = null;

	private String userNo = "";
	private String txtGroupFiled = "";

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
		lblName.setBounds(10, 10, 100, 30);
		centerPane.add(lblName);

		lblPhone = new JLabel("폰번호");
		lblPhone.setBounds(10, 50, 100, 30);
		centerPane.add(lblPhone);

		lblEmail = new JLabel("이메일");
		lblEmail.setBounds(10, 100, 100, 30);
		centerPane.add(lblEmail);

		lblGol = new JLabel(" @ ");
		lblGol.setBounds(180, 100, 100, 30);
		centerPane.add(lblGol);

		lblCom = new JLabel("회   사");
		lblCom.setBounds(10, 150, 100, 30);
		centerPane.add(lblCom);

		lblDepartment = new JLabel("부   서");
		lblDepartment.setBounds(10, 200, 100, 30);
		centerPane.add(lblDepartment);

		lblPosition = new JLabel("직   책");
		lblPosition.setBounds(10, 250, 100, 30);
		centerPane.add(lblPosition);

		lblMemo = new JLabel("메   모");
		lblMemo.setBounds(10, 300, 100, 30);
		centerPane.add(lblMemo);

		lblGroup = new JLabel("그   룹");
		lblGroup.setBounds(10, 400, 100, 30);
		centerPane.add(lblGroup);

		// 이름
		txtName = new JTextField();
		txtName.setBounds(100, 10, 200, 25);
		centerPane.add(txtName);

		// 핸드폰번호
		txtPhone = new JTextField();
		txtPhone.setBounds(100, 50, 200, 25);
		centerPane.add(txtPhone);

		// 이메일
		txtEmail = new JTextField();
		txtEmail2 = new JTextField();
		txtEmail.setBounds(100, 100, 80, 25);
		txtEmail2.setBounds(200, 100, 100, 25);
		txtEmail2.setEnabled(false);
		centerPane.add(txtEmail);
		centerPane.add(txtEmail2);

		// 이메일리스트
		String mailList[] = { "naver.com", "gmail.com", "kakao.com", "hanmail.net", "직접입력하기" };
		JComboBox mailCombo = new JComboBox(mailList);
		mailCombo.setBounds(310, 100, 100, 25);
		centerPane.add(mailCombo);

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

		// 회사
		txtCom = new JTextField();
		txtCom.setBounds(100, 150, 200, 25);
		centerPane.add(txtCom);

		// 부서
		txtDepartment = new JTextField();
		txtDepartment.setBounds(100, 200, 200, 25);
		centerPane.add(txtDepartment);

		// 직책
		txtPosition = new JTextField();
		txtPosition.setBounds(100, 250, 200, 25);
		centerPane.add(txtPosition);

		// 메모
		txtMemo = new JTextArea();
		txtMemo.setBounds(100, 300, 310, 80);
		centerPane.add(txtMemo);

		// 그룹
		txtGroup = new JTextField();
		txtGroup.setBounds(100, 400, 200, 25);
		txtGroup.setEditable(false);
		centerPane.add(txtGroup);

		txtNo = new JTextField();

		// 그룹리스트
		groupCombo = new JComboBox<GroupVO>();
		groupCombo.addItem(new GroupVO(0, "그룹 선택"));
		groupList = FileHandler.getInstance().getGroupList();

		for (GroupVO group : groupList) {
			this.groupCombo.addItem(group);
		}

		groupCombo.setBounds(310, 400, 100, 25);
		centerPane.add(groupCombo);
		txtNo.setBounds(100, 450, 100, 25);

		// 그룹 콤보박스 선택시 이벤트
		groupCombo.addActionListener(e -> {

			String comboGroupo = groupCombo.getItemAt(groupCombo.getSelectedIndex()).toString();

			txtGroupFiled = txtGroup.getText();
			System.out.println("txtGroupFiled = " + txtGroupFiled + "selectGroup" + comboGroupo);

			if (cnt == 0) {
				txtGroupFiled = "";
			} else {
				if (this.isSelectedGroup(txtGroupFiled, comboGroupo)) {

					if (txtGroupFiled.equals("")) {
						txtGroupFiled += comboGroupo;
					} else {
						txtGroupFiled += "," + comboGroupo;
					}
					System.out.println("txtGroupFiled===" + txtGroupFiled);
				}
			}
			cnt++;

			txtGroup.setText(txtGroupFiled);

		});

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
		if (updateuser.getAd_position() != null) {
			txtPosition.setText(updateuser.getAd_position());
		}
		if (updateuser.getAd_memo() != null) {
			txtMemo.setText(updateuser.getAd_memo());
		}
		if (updateuser.getGroup_no() != null) {
			txtGroup.setText(this.setGroupName(updateuser.getGroup_no()));
		}

		// 텍스트필드 셋팅

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
			this.reOriginData();
		});

		// 수정완료버튼
		btnUpdate.addActionListener(e -> {
			this.updateUserData();
		});

		setSize(450, 600);
	}

	/**
	 * 원래 가져왔던 데이터로 돌아가게 하는 메서드
	 */
	public void reOriginData() {
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
		if (updateuser.getAd_position() != null) {
			txtPosition.setText(updateuser.getAd_position());
		}
		if (updateuser.getAd_memo() != null) {
			txtMemo.setText(updateuser.getAd_memo());
		}
		if (updateuser.getGroup_no() != null) {
			txtGroup.setText(this.setGroupName(updateuser.getGroup_no()));
		}

	}

	/**
	 * 수정한 유저 정보 보내는 메서드
	 */
	public void updateUserData() {

		updateuser = new UserVO();

		// 이름 필수 입력 발리데이션
		if (txtName.getText().length() <= 0) {
			JOptionPane.showMessageDialog(mainContentPane, "이릅입력은 필수입니다. \n이름을입력해주세요\n", "경고", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// 핸드폰번호 or 이메일 입력 발리데이션
		if (txtPhone.getText().length() <= 0 && txtEmail.getText().length() <= 0) {
			JOptionPane.showMessageDialog(mainContentPane, "핸드폰번호 또는 이메일  둘중 하나는 \n필수로 입력사항입니다.\n 입력해주세요.", "경고", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (txtPhone.getText().trim().length() > 0) {
			boolean no = false;
			no = isPhone(txtPhone.getText());
			System.out.println(no);
			System.out.println(txtPhone.getText());

			if (no == false) {
				JOptionPane.showMessageDialog(mainContentPane, "핸드폰 번호 입력 형식이 잘못되었습니다.", "경고", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		// 값 보내기
		updateuser.setAd_no(txtNo.getText());
		updateuser.setAd_name(txtName.getText());
		updateuser.setAd_hp(txtPhone.getText());
		if (txtEmail.getText().trim().length() > 1) {
			updateuser.setAd_mail(txtEmail.getText() + "@" + txtEmail2.getText());
		} else {
			updateuser.setAd_mail("");
		}
		updateuser.setAd_com(txtCom.getText());
		updateuser.setAd_department(txtDepartment.getText());
		updateuser.setAd_position(txtPosition.getText());
		updateuser.setAd_memo(txtMemo.getText());

		// 그룹선택에 관한 부분 ///
		updateuser.setGroup_no(this.setGroupNo(txtGroup.getText()));
		this.addressBookMainUI.updateUser(updateuser);

		// 등록하면서 값 비우기
		this.clearTextFiled();

		// 화면꺼
		setVisible(false);
		FileHandler.getInstance().updateUser(updateuser);

	}

	/**
	 * 핸드폰 번호 유효성 검사 메서드
	 * 
	 * @param hp
	 * @return
	 */
	private boolean isPhone(String hp) {

		Pattern p = Pattern.compile("^\\d{3,4}-\\d{3,4}-\\d{4}$");
		System.out.println("===유효성검사메서드===");
		System.out.println(hp);

		Matcher m = p.matcher(hp);

		if (m.find()) {

			return true;

		} else {
			return false;
		}
	}

	/**
	 * 그룹리스트에서 번호를 가져오기..
	 * 
	 * @param txtGroupFiled
	 * @return
	 */
	public String setGroupNo(String txtGroupFiled) {

		String groupNo = "";

		if (txtGroupFiled == "" || txtGroupFiled.isEmpty() || txtGroupFiled.equals("그룹미지정")) {
			groupNo = "0";
			return groupNo;
		} else if (txtGroupFiled.contains(",")) {

			String[] groupList = txtGroupFiled.split(",");
			List<GroupVO> filegroupList = FileHandler.getInstance().getGroupList();

			for (int i = 0; i < filegroupList.size(); i++) {

				for (int j = 0; j < groupList.length; j++) {

					System.out.println("filegroupList.get(i).getGroup_name()=" + filegroupList.get(i).getGroup_name() + " / groupList[j]) = " + groupList[j]);
					if (filegroupList.get(i).getGroup_name().equals(groupList[j])) {
						if (groupNo.equals("")) {
							groupNo += filegroupList.get(i).getGroup_no();
						} else {
							groupNo += "," + filegroupList.get(i).getGroup_no();
						}
					}
				}
			}
			System.out.println("groupNO=" + groupNo);
			return groupNo;
		} else {
			List<GroupVO> filegroupList = FileHandler.getInstance().getGroupList();
			for (int i = 0; i < filegroupList.size(); i++) {

				System.out.println("filegroupList.get(i).getGroup_name()=" + filegroupList.get(i).getGroup_name());

				if (filegroupList.get(i).getGroup_name().equals(txtGroupFiled)) {
					groupNo += filegroupList.get(i).getGroup_no();
				}
			}

		}

		return groupNo;

	}

	/**
	 * 그룹 번호에 따른 그룹 이름 가져오기
	 * 
	 * @param txtGroupFiled
	 * @return
	 */
	public String setGroupName(String txtGroupFiled) {

		String groupName = "";

		if (txtGroupFiled.isEmpty() || txtGroupFiled.equals("0")) {
			groupName = "";
			return groupName;
		} else if (txtGroupFiled.contains(",")) {

			String[] groupList = txtGroupFiled.split(",");
			List<GroupVO> filegroupList = FileHandler.getInstance().getGroupList();

			for (int i = 0; i < filegroupList.size(); i++) {
				for (int j = 0; j < groupList.length; j++) {
					System.out.println("filegroupList.get(i).getGroup_name()=" + filegroupList.get(i).getGroup_name() + " / groupList[j]) = " + groupList[j]);
					if (String.valueOf(filegroupList.get(i).getGroup_no()).equals(groupList[j])) {
						if (groupName.equals("")) {
							groupName += filegroupList.get(i).getGroup_name();
						} else {
							groupName += "," + filegroupList.get(i).getGroup_name();
						}
					}
				}
			}
			System.out.println("groupName=" + groupName);
			return groupName;
		} else {
			List<GroupVO> filegroupList = FileHandler.getInstance().getGroupList();
			for (int i = 0; i < filegroupList.size(); i++) {

				System.out.println("filegroupList.get(i).getGroup_name()=" + filegroupList.get(i).getGroup_name());

				if (String.valueOf(filegroupList.get(i).getGroup_no()).equals(txtGroupFiled)) {
					groupName += filegroupList.get(i).getGroup_name();
				}
			}

		}

		return groupName;

	}

	/**
	 * 이미 선택된 그룹인지 아닌지 알려주는 메서드
	 * 
	 * true : input값을 비교하여 동일한 값이 없을때 , 값이 비어있을때 false : input값을 비교하여 동일한 값이 있을때
	 * 
	 * @param txtGroupFiled ex)A,B,C ....
	 * @param selectedGroup
	 * @return boolean
	 */
	public boolean isSelectedGroup(String txtGroupFiled, String selectedGroup) {

		boolean result = true;

		if (txtGroupFiled.isEmpty()) {

			result = true;
		} else {

			System.out.println("====contains,");
			String[] groupList = txtGroupFiled.split(",");

			for (int i = 0; i < groupList.length; i++) {
				System.out.println("groupList===" + groupList[i]);
				if (groupList[i].equals(selectedGroup)) {
					System.out.println("selectedGroup===" + selectedGroup);

					result = false;
					break;
				}
			}

		}
		return result;

	}

	/**
	 * 전체 텍스트 필드를 지워주는 메서드
	 */
	private void clearTextFiled() {

		txtName.setText("");
		txtPhone.setText("");
		txtEmail.setText("");
		txtEmail2.setText("");
		txtCom.setText("");
		txtDepartment.setText("");
		txtPosition.setText("");
		txtMemo.setText("");
		txtGroup.setText("");
		selectedGroup = "";
	}

}

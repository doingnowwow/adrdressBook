package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import data.GroupVO;
import data.UserVO;
import handler.FileHandler;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddressBookMainUI extends JFrame implements IAddUser, MouseListener, TreeSelectionListener {

	private UserVO userData = new UserVO();
	private GroupVO groupData = new GroupVO();

	private JPanel contentPane;
	private JSplitPane mainSplitPane;
	private JPanel leftSplitPane;
	private JPanel rightSplitPane;
	private JPanel leftSouthPane;
	private JButton btnInsertGroup;
	private JButton btnDeleteGroup;
	private JPanel rigthNorthPanel;
	private JPanel leftTopPane;
	private JLabel lblGroup;
	private JScrollPane treeSclPane;
	private JTree tree;
	private JPanel rigthSoutPanel;
	private JButton btnInsertAddress;
	private JButton btnDeleteAddress;
	private JScrollPane tableSclPane;
	private JTextField txtSearch;
	private JButton btnSearch;
	private JTable table;
	private DefaultMutableTreeNode root;

	private DefaultTableModel model;

	// 테이블 초기 셋팅
	String colNames[] = { "x", "번호", "이름", "핸드폰번호", "이메일", "회사", "부서", "직책", "메모" };
	int cols[] = { 50, 50, 100, 150, 150, 100, 100, 100, 200 };

	// 테이블 선택시 이벤트 발생을 위해
	// 테이블 row
	int selectedRow;
	// 테이블 column
	int selectedCol;

	// 비어있는 오브젝트 생성
	private Object tabeData = null;

	/**
	 * Create the frame.
	 */
	public AddressBookMainUI() {
		jbInit(this);
	}

	@Override
	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			FileHandler.getInstance().wirteGroup();
			FileHandler.getInstance().wirteUser();
			FileHandler.getInstance().writeKey();
		}
		super.processWindowEvent(e);
	}

	private void jbInit(AddressBookMainUI main) {

		this.setTitle("주소록");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setMinimumSize(new Dimension(700, 500));

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		mainSplitPane = new JSplitPane();
		contentPane.add(mainSplitPane, BorderLayout.CENTER);

		// 왼쪽부분시작
		leftSplitPane = new JPanel();
		mainSplitPane.setLeftComponent(leftSplitPane);
		leftSplitPane.setLayout(new BorderLayout(0, 0));

		// 왼쪽 JTree테이블 부분
		leftSouthPane = new JPanel();
		leftSplitPane.add(leftSouthPane, BorderLayout.SOUTH);
		leftSouthPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// 왼쪽 제목
		leftTopPane = new JPanel();
		leftSplitPane.add(leftTopPane, BorderLayout.NORTH);
		lblGroup = new JLabel("그룹관리");
		leftTopPane.add(lblGroup);

		// 왼쪽 아래 버튼
		btnInsertGroup = new JButton("그룹추가");
		leftSouthPane.add(btnInsertGroup);
		btnDeleteGroup = new JButton("그룹삭제");
		leftSouthPane.add(btnDeleteGroup);

		// 트리 스크롤 스판
		treeSclPane = new JScrollPane();
		leftSplitPane.add(treeSclPane, BorderLayout.CENTER);

///////////////////////// 트리부분////////////////////////////////////

		// 트리 넣기
		initTree();

		// 트리 아이콘 설정하기
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setLeafIcon(renderer.getDefaultClosedIcon());

		tree.setCellRenderer(renderer);
		tree.setEditable(true);
		tree.setDragEnabled(true);
		tree.setDropMode(DropMode.ON_OR_INSERT);
		treeSclPane.setViewportView(tree);
		tree.setEditable(false);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
		expandTree(tree);

		// 트리 마우스 리스너
		tree.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent event) {
				if (((event.getModifiers() & InputEvent.BUTTON3_MASK) != 0) && (tree.getSelectionCount() > 0)) {
					showMenu(event.getX(), event.getY());
				}
			}
		});

		// 그룹추가버튼 이벤트
		btnInsertGroup.addActionListener(e -> {

			String addGroupName = JOptionPane.showInputDialog(leftSplitPane, "추가할 그룹명을 작성하세요", "그룹 추가하기", JOptionPane.CLOSED_OPTION);

			if (addGroupName == null) {
				JOptionPane.showMessageDialog(leftSplitPane, "그룹추가가 취소되었습니다.", "취소", JOptionPane.OK_OPTION);
				return;
			}

			if (this.checkGroupName(addGroupName)) {
				DefaultMutableTreeNode newGroup = new DefaultMutableTreeNode(FileHandler.getInstance().addGroup(addGroupName));
				System.out.println("newGroup = " + newGroup.getUserObject().toString());
				System.out.println("root is null ? " + (this.root == null));
				System.out.println("root child = " + root.getChildCount());
				((DefaultTreeModel) tree.getModel()).insertNodeInto(newGroup, root, root.getChildCount());
				JOptionPane.showMessageDialog(leftSplitPane, "그룹추가가 완료되었습니다.", "성공", 1);

				System.out.println(addGroupName);

			} else {
				JOptionPane.showMessageDialog(leftSplitPane, "그룹명이 중복되어서 등록할 수없습니다.", "취소", JOptionPane.OK_OPTION);
				return;
			}

		});

		// 그룹삭제이벤트
		btnDeleteGroup.addActionListener(e -> {

			DefaultMutableTreeNode node = getSelectedNode();

			if (node == null) {
				JOptionPane.showMessageDialog(leftSplitPane, "그룹을 선택한 후에만 삭제할 수 있습니다..", "주의", JOptionPane.WARNING_MESSAGE);
				return;
			}

			if (node.getChildCount() > 0) {
				JOptionPane.showMessageDialog(leftSplitPane, "그룹 안에 정보목록이 있으므로 삭제할 수 없습니다.", "주의", JOptionPane.WARNING_MESSAGE);
			} else {
				int res = JOptionPane.showConfirmDialog(leftSplitPane, "삭제하면 다시 복원 불가능합니다.삭제하시겠습니까?", "삭제확인", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.OK_OPTION) {
					DefaultTreeModel model = (DefaultTreeModel) (tree.getModel());
					TreePath[] paths = tree.getSelectionPaths();
					for (int i = 0; i < paths.length; i++) {
						node = (DefaultMutableTreeNode) (paths[i].getLastPathComponent());
						model.removeNodeFromParent(node);
					}
				}
			}

		});

		// 오른쪽
		rightSplitPane = new JPanel();
		mainSplitPane.setRightComponent(rightSplitPane);
		rightSplitPane.setLayout(new BorderLayout(0, 0));

		// 오른쪽 North
		rigthNorthPanel = new JPanel();
		rightSplitPane.add(rigthNorthPanel, BorderLayout.NORTH);
		rigthNorthPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		// 검색 txt필드
		txtSearch = new JTextField();
		rigthNorthPanel.add(txtSearch);
		txtSearch.setColumns(10);

		// 검색버튼
		btnSearch = new JButton("검색");
		rigthNorthPanel.add(btnSearch);

		// 오른쪽 South
		rigthSoutPanel = new JPanel();
		rightSplitPane.add(rigthSoutPanel, BorderLayout.SOUTH);
		rigthSoutPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		// 오른쪽 전체 화면 아래
		btnInsertAddress = new JButton("주소록추가");
		rigthSoutPanel.add(btnInsertAddress);
		btnDeleteAddress = new JButton("주소록삭제");
		rigthSoutPanel.add(btnDeleteAddress);

		// 오른쪽 가운데 스크롤 스판
		tableSclPane = new JScrollPane();
		rightSplitPane.add(tableSclPane, BorderLayout.CENTER);

		// 테이블 에 들어갈 부분
		model = new DefaultTableModel(colNames, 0) {

			@Override
			public Class getColumnClass(int column) {

				switch (column) {
				case 0:

					return boolean.class;
				case 1:
					return Integer.class;
				case 2:
					return String.class;
				case 3:

					return String.class;
				case 4:

					return String.class;
				case 5:

					return String.class;
				case 6:

					return String.class;
				case 7:

					return String.class;
				case 8:

					return String.class;
				case 9:

					return String.class;

				default:
					return String.class;
				}

			}

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 0) {
					return true;
				}

				return false;

			}
		};

		table = new JTable(model);

		JCheckBox checkBox = new JCheckBox();

		DefaultCellEditor checkboxRend = new DefaultCellEditor(checkBox);
//			
		TableRenderer tableR = new TableRenderer();
		table.getColumn("x").setCellRenderer(tableR);
		table.getColumn("x").setCellEditor(checkboxRend);

		table.getTableHeader().setReorderingAllowed(false);// 컬럼들 이동 불가
		table.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가

		for (int i = 0; i < cols.length; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(cols[i]);
		}

		tableSclPane.setViewportView(table);

		// 테이블 마우스 이벤트 부분
		table.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent event) {
				// 마우스 선택 이벤트
				if (table.getSelectedRowCount() > 0) {

					DefaultMutableTreeNode selectedNode = getSelectedNode();

					selectedRow = table.getSelectedRow();
					selectedCol = table.getSelectedColumn();

					Object pobj = null;
					pobj = table.getValueAt(selectedRow, selectedCol);
					System.out.println("Selected MOUSE TABLE --> row: [" + selectedRow + "], column : [" + selectedCol + "], 선택한내용:[" + pobj.toString() + "]");

				}

				if (((event.getModifiers() & InputEvent.BUTTON3_MASK) != 0) && (table.getSelectedRowCount() > 0)) {
					showMenuTable(event.getX(), event.getY());
				}
			}
		});

		// 주소록 추가 버튼 클릭 이벤트
		btnInsertAddress.addActionListener(e -> {

			// 인스턴스화
			InsertAddressDialog insertAddressdiag = new InsertAddressDialog(this, "주소록 추가");
			// 주소록 추가화면 가운데 뜨게 하기
			insertAddressdiag.setLocationRelativeTo(main);
			insertAddressdiag.setVisible(true);

		});

		// 테이블 주소록 삭제 클릭 이벤트
		// 다중삭제이벤트
		btnDeleteAddress.addActionListener(e -> {
			deleteUesr();
		});

		// 검색버튼 이벤트
		btnSearch.addActionListener(e -> {
			String keyword = txtSearch.getText();

			ArrayList<UserVO> userList = FileHandler.getInstance().searchUserListBykeyword(keyword);
			setTableData(userList);

			txtSearch.setText("");
		});

	}

///////////////////////////////////////////////////////////////////////////////////
//////////////////////트리 관련 메서드//////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////
	/**
	 * 트리 초기화 메서드
	 */
	private void initTree() {

		System.out.println("==========트리 초기화 시작=========");

		this.setGroupTree(FileHandler.getInstance().getGroupList());
	}

	/**
	 * 트리 데이터 셋팅해주는 메서드
	 * 
	 * @param groupList
	 */
	private void setGroupTree(ArrayList<GroupVO> groupList) {

		DefaultMutableTreeNode node;

		root = new DefaultMutableTreeNode("전체 ");

		for (GroupVO groupData : groupList) {
			node = new DefaultMutableTreeNode(groupData);

			root.add(node);
		}

		tree = new JTree(root);
		tree.addTreeSelectionListener(this);

	}

	/**
	 * 트리에서 사용하는 마우스 팝업 이벤트 메서드 - 수정 - 삭제
	 * 
	 * @param x
	 * @param y
	 */
	protected void showMenu(int x, int y) {

		JPopupMenu popup = new JPopupMenu();
		JMenuItem mi = new JMenuItem("수정");
		popup.add(mi);
		TreePath path = tree.getSelectionPath();
		Object node = path.getLastPathComponent();

		String selectGroupName = path.toString();

		if (node == tree.getModel().getRoot() || selectGroupName.contentEquals("전체") || selectGroupName.contentEquals("가족") || selectGroupName.contentEquals("친구") || selectGroupName.contentEquals("회사") || selectGroupName.contentEquals("그룹없음")) {
			mi.setEnabled(false);
		}

		popup.add(mi);

		// 오른쪽 마우스 수정버튼 이벤트
		mi.addActionListener(e -> {
			modifySelectedNode();
		});

		// 오른쪽 마우스 삭제 이벤트
		mi = new JMenuItem("삭제");
		if (node == tree.getModel().getRoot()) {
			mi.setEnabled(false);
		}
		popup.add(mi);

		mi.addActionListener(e -> {
			deleteSelectedItems();
		});

		popup.show(tree, x, y);
	}

	/**
	 * 트리 노드 삭제하는 메서드
	 */
	protected void deleteSelectedItems() {
		DefaultMutableTreeNode node = getSelectedNode();
		if (node.getChildCount() > 0) {
			JOptionPane.showMessageDialog(leftSplitPane, "그룹 안에 정보목록이 있으므로 삭제할 수 없습니다.", "주의", JOptionPane.WARNING_MESSAGE);
		} else {
			int res = JOptionPane.showConfirmDialog(leftSplitPane, "삭제하면 다시 복원 불가능합니다.삭제하시겠습니까?", "삭제확인", JOptionPane.YES_NO_OPTION);
			if (res == JOptionPane.OK_OPTION) {
				DefaultTreeModel model = (DefaultTreeModel) (tree.getModel());
				TreePath[] paths = tree.getSelectionPaths();
				for (int i = 0; i < paths.length; i++) {
					node = (DefaultMutableTreeNode) (paths[i].getLastPathComponent());
					model.removeNodeFromParent(node);
				}
				FileHandler.getInstance().deleteGroup(groupData);

			}
		}
	}

	/**
	 * 트리 노드 수정하는 메서드
	 */
	private void modifySelectedNode() {
		DefaultMutableTreeNode node = getSelectedNode();
		if (node == null) {
			JOptionPane.showMessageDialog(leftSplitPane, "변경할 그룹을 선택하세요", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		String groupNewname = JOptionPane.showInputDialog(leftSplitPane, "새로운 그룹 이름을 입력하세요 ", "그룹이름 수정", JOptionPane.CLOSED_OPTION);
		if (groupNewname != null && !"".equals(groupNewname)) {
			System.out.println("====수정====");
			groupData.setGroup_name(groupNewname);
			node.setUserObject(groupData);

			System.out.println(groupData.getGroup_no());
			System.out.println("==========================");
			FileHandler.getInstance().updateGroup(groupData);
			FileHandler.getInstance().searchUserListByGroup(groupData);
		}
	}

	/**
	 * 트리 노드 Path 기본값 설정
	 * 
	 * @return
	 */
	private DefaultMutableTreeNode getSelectedNode() {
		return (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
	}

	/**
	 * 뭐하는 메서드인지는 잘 모르겠음
	 * 
	 * @param tree
	 */
	private void expandTree(JTree tree) {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
		Enumeration e = root.breadthFirstEnumeration();
		while (e.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
			if (node.isLeaf())
				continue;
			int row = tree.getRowForPath(new TreePath(node.getPath()));
			tree.expandRow(row);
		}
	}

	/**
	 * 트리 선택 이벤트
	 */
	@Override
	public void valueChanged(TreeSelectionEvent e) {

		DefaultMutableTreeNode node;
		node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

		TreePath path = tree.getSelectionPath();
		if (path != null) {

			String selectGroupName = path.toString();
			System.out.println("-----------------------여기");
			System.out.println("그룹이름 : " + selectGroupName);
			if (selectGroupName.equals("[전체 ]")) {
				System.out.println("확인");
				setTableData(FileHandler.getInstance().searchUserListByGroup(null));
			}

		}

		if (node == null) {
			return;
		}

		Object obj = (Object) node.getUserObject();
		System.out.println(obj.getClass());
		ArrayList<UserVO> userList = null;
		if (obj instanceof GroupVO) {
			GroupVO group = (GroupVO) obj;

			groupData = group;
			userList = FileHandler.getInstance().searchUserListByGroup(group);
		} else {
			userList = FileHandler.getInstance().searchUserListByGroup(null);
		}

		setTableData(userList);

	}

	/**
	 * 그룹 명 중복 체크
	 * 
	 * @param groupName
	 * @return
	 */
	public boolean checkGroupName(String groupName) {

		List<GroupVO> groupList = FileHandler.getInstance().getGroupList();

		for (int i = 0; i < groupList.size(); i++) {

			if (groupName.equals(groupList.get(i).getGroup_name())) {
				return false;
			}

		}
		return true;

	}

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
///////////////////////////////테이블관련메서드/////////////////////////////////////	
///////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////

	/**
	 * 트리에서 선택한 값 테이블에 넣어주기
	 * 
	 * @param userList
	 */
	private void setTableData(ArrayList<UserVO> userList) {

		table.getModel();
		model.setNumRows(0);

		for (int i = 0; i < userList.size(); i++) {
			model.addRow(new Object[] { false, userList.get(i).getAd_no(), userList.get(i).getAd_name(), userList.get(i).getAd_hp(), userList.get(i).getAd_mail(), userList.get(i).getAd_com(), userList.get(i).getAd_department(), userList.get(i).getAd_postion(), userList.get(i).getAd_memo(), userList.get(i).getGroup_no() });

		}

	}

	/**
	 * 테이블에서 사용하는 오른쪽 마우스 이벤트 팝업창 - 수정 - 삭제
	 * 
	 * @param x
	 * @param y
	 */
	protected void showMenuTable(int x, int y) {

		JPopupMenu popup = new JPopupMenu();
		JMenuItem mi = new JMenuItem("수정");
		popup.add(mi);

		// 오른쪽 마우스 수정버튼 이벤트
		mi.addActionListener(e -> {
			updateUserRow();
		});

		// 오른쪽 마우스 삭제 이벤트
		mi = new JMenuItem("삭제");
		popup.add(mi);
		mi.addActionListener(e -> {

			deleteUesr(table.getSelectedRow());
		});

		popup.show(table, x, y);
	}

	/**
	 * 테이블 한 줄 수정하는 메서드
	 */
	private void updateUserRow() {

		String tableValue[] = new String[9];

		for (int i = 1; i < 9; i++) {

			tabeData = table.getValueAt(selectedRow, i);

			System.out.println("UPDATE *-> row : [" + selectedRow + "]  col : [" + "순서 : " + i + "~  " + tabeData + " ]" + i + "번째 : col의 값 : [" + tableValue[i] + "]");

			if (i == 1) {
				userData.setAd_no(Integer.parseInt(tabeData.toString()));
			} else if (i == 2) {
				userData.setAd_name(tabeData.toString());
			} else if (i == 3) {
				if (tabeData != null) {
					userData.setAd_hp(tabeData.toString());
				}
			} else if (i == 4) {
				if (tabeData != null) {
					userData.setAd_mail(tabeData.toString());
				}
			} else if (i == 5) {
				if (tabeData != null) {
					userData.setAd_com(tabeData.toString());
				}
			} else if (i == 6) {
				if (tabeData != null) {
					userData.setAd_department(tabeData.toString());
				}
			} else if (i == 7) {
				if (tabeData != null) {
					userData.setAd_postion(tabeData.toString());
				}
			} else if (i == 8) {
				if (tabeData != null) {
					userData.setAd_memo(tabeData.toString());
				}
			}

		}

		System.out.println("======================for end ===========================");
	
		UpdateAddressDialog updateAddressdiag = new UpdateAddressDialog(this, "주소록 수정", userData);
		updateAddressdiag.setLocationRelativeTo(null);
		System.out.println(">>>선택한 유저 번호" + userData.getAd_no());
		System.out.println("*******************선택한유저이름?********" + userData.getAd_name());
		System.out.println("*******************선택한유저 핸드폰번호?********" + userData.getAd_hp());

		updateAddressdiag.setVisible(true);

	}

	/**
	 * 테이블 사용자 추가
	 */
	public void addUser(UserVO user) {

		System.out.println("메인==================================>넘어온다");
		System.out.println("이름 : " + user.getAd_name());
		System.out.println("핸드폰 : " + user.getAd_hp());
		System.out.println("이메일 : " + user.getAd_mail());
		System.out.println("회사 : " + user.getAd_com());
		System.out.println("부서 : " + user.getAd_department());
		System.out.println("직책: " + user.getAd_postion());
		System.out.println("메모 : " + user.getAd_memo());
		System.out.println("그룹번호 : " + user.getGroup_no());
		System.out.println("==================끝===================");

		FileHandler.getInstance().addUser(user);

		// 추가
		((DefaultTableModel) table.getModel()).addRow(new Object[] { false, user.getAd_no(), user.getAd_name(), user.getAd_hp(), user.getAd_mail(), user.getAd_com(), user.getAd_department(), user.getAd_postion(), user.getAd_memo(), user.getGroup_no() });

	}

	/**
	 * 테이블 사용자 수정
	 * 
	 * @param updateuser
	 */
	public void updateUser(UserVO updateuser) {

		System.out.println("========수정확인하려는 메인이다========");
		System.out.println("번호 : " + updateuser.getAd_no());
		System.out.println("이름 : " + updateuser.getAd_name());
		System.out.println("핸드폰 : " + updateuser.getAd_hp());
		System.out.println("이메일 : " + updateuser.getAd_mail());
		System.out.println("회사 : " + updateuser.getAd_com());
		System.out.println("부서 : " + updateuser.getAd_department());
		System.out.println("직책: " + updateuser.getAd_postion());
		System.out.println("메모 : " + updateuser.getAd_memo());
		System.out.println("그룹번호 : " + updateuser.getGroup_no());
		System.out.println("==================끝===================");

		int userNo = updateuser.getAd_no();

		((DefaultTableModel) table.getModel()).removeRow(selectedRow);

		// 수정
		((DefaultTableModel) table.getModel()).addRow(new Object[] { false, userNo, updateuser.getAd_name(), updateuser.getAd_hp(), updateuser.getAd_mail(), updateuser.getAd_com(), updateuser.getAd_department(), updateuser.getAd_postion(), updateuser.getAd_memo(), updateuser.getGroup_no() });

	}

	/**
	 * 테이블에 있는 유저를 삭제하는 메서드 체크박스 체크된 유저들만
	 */
	private void deleteUesr() {
		System.out.println("---delete start---");

		int tableRowCount = table.getModel().getRowCount();
		Object tablecheckbox = null;

		ArrayList<UserVO> deleteUserList = new ArrayList<UserVO>();

		for (int i = tableRowCount; i > 0; i--) {

			tablecheckbox = table.getValueAt(i - 1, 0);

			System.out.println(i - 1 + "i?");

			if (tablecheckbox.equals(true)) {

				userData = new UserVO();
				userData.setAd_no((int) model.getValueAt(i - 1, 1));

				deleteUserList.add(userData);
				FileHandler.getInstance().deleteUser(deleteUserList);
				((DefaultTableModel) table.getModel()).removeRow(i - 1);
			}
			System.out.println("---delete end---");
		}
	}

	/**
	 * 선택된 사용자 삭제하기
	 * 
	 * @param row
	 */
	private void deleteUesr(int row) {
		ArrayList<UserVO> deleteUserList = new ArrayList<UserVO>();
		UserVO userData = new UserVO();
		userData.setAd_no((int) model.getValueAt(row, 1));
		deleteUserList.add(userData);
		FileHandler.getInstance().deleteUser(deleteUserList);

		((DefaultTableModel) table.getModel()).removeRow(row);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}

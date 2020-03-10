package hs.addressbook.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import hs.addressbook.data.GroupVO;
import hs.addressbook.data.MappingVO;
import hs.addressbook.data.UserVO;
import hs.addressbook.handler.database.GroupDataHandler;
import hs.addressbook.handler.database.UserDataHandler;
import hs.addressbook.handler.file.FileHandler;
import net.mbiz.edt.barcode.table.data.TableData;
import net.mbiz.edt.barcode.table.model.MbizTableModel;
import java.awt.Font;

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

	private MbizTableModel model;

	private AddressBookMainUI main;

	private String pickData;

	// 테이블 초기 셋팅
	String colNames[] = { " ", "번호", "이름", "핸드폰번호", "이메일", "회사", "부서", "직책", "메모" };
	int cols[] = { 20, 50, 100, 200, 200, 100, 150, 100, 250 };

	// 테이블 선택시 이벤트 발생을 위해
	// 테이블 row
	int selectedRow;
	// 테이블 column
	int selectedCol;

	// 비어있는 오브젝트 생성
	private Object tableData = null;

	private UserDataHandler userDataHandler;
	private GroupDataHandler groupDataHandler;

	/**
	 * Create the frame.
	 */
	public AddressBookMainUI() {
		jbInit(this); 
		
	}

	private void initTableData() {
		
		if (pickData.equals("file")) {
			ArrayList<UserVO> userList = (ArrayList<UserVO>) UserDataHandler.getInstance().selectAllUser();
			model.addDataList(userList);
			
		}else{
			ArrayList<UserVO> userList = FileHandler.getInstance().searchUserListByGroup(null);
			model.addDataList(userList);
		}
	}


	public AddressBookMainUI(String data) {
		System.out.println("addressBookMainUi====>");
		pickData = data;
		System.out.println("====실행할 데이터는 ?====" + pickData);
		jbInit(this);
	}

	@Override
	protected void processWindowEvent(WindowEvent e) {

		if (pickData.equals("file")) {

			if (e.getID() == WindowEvent.WINDOW_CLOSING) {
				FileHandler.getInstance().wirteGroup();
				FileHandler.getInstance().wirteUser();
				FileHandler.getInstance().writeKey();
				System.out.println("file save.....");
				super.processWindowEvent(e);
			}
		} else {
			if (e.getID() == WindowEvent.WINDOW_CLOSING) {

				System.out.println("database save.....");
				super.processWindowEvent(e);
			}
		}

	}

	private void jbInit(AddressBookMainUI main) {

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
		}

		this.setTitle("주소록");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setMinimumSize(new Dimension(750, 500));

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
		lblGroup.setFont(new Font("양재참숯체B", Font.PLAIN, 15));
		leftTopPane.add(lblGroup);

		// 왼쪽 아래 버튼
		btnInsertGroup = new JButton("그룹추가");
		leftSouthPane.add(btnInsertGroup);
		btnDeleteGroup = new JButton("그룹삭제");
		leftSouthPane.add(btnDeleteGroup);

		// 트리 스크롤 스판
		treeSclPane = new JScrollPane();
		leftSplitPane.add(treeSclPane, BorderLayout.CENTER);

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
			addGroupEvent();

		});

		// 그룹삭제이벤트
		btnDeleteGroup.addActionListener(e -> {
			deleteSelectedItems();
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
		model = new MbizTableModel(colNames, cols);
		
		table = new JTable(model);
		
		model.setEditColumn(0);
		model.setSorting(false);

		JCheckBox checkBox = new JCheckBox();
		
		DefaultCellEditor checkboxRend = new DefaultCellEditor(checkBox);
		TableRenderer tableR = new TableRenderer();
		table.getColumnModel().getColumn(0).setCellRenderer(tableR);
		table.getColumnModel().getColumn(0).setCellEditor(checkboxRend); 

		table.getTableHeader().setReorderingAllowed(false);// 컬럼들 이동 불가
		table.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가
		table.setAutoCreateColumnsFromModel(false);

		for (int i = 0; i < cols.length; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(cols[i]);
		}

		tableSclPane.setViewportView(table);

		initTableData();
		
		// 테이블 마우스 이벤트 부분
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event) {
				// 마우스 선택 이벤트
				if (table.getSelectedRowCount() > 0) {
					selectedRow = table.getSelectedRow();
					selectedCol = table.getSelectedColumn();
					int selRow = table.getSelectedRow();
					UserVO user = (UserVO) model.getData(selRow);
					System.out.println("Selected MOUSE TABLE --> row: [" + selectedRow + "], column : [" + selectedCol + "], 선택한내용:[" + user.toString() + "]");
				}
				if (((event.getModifiers() & InputEvent.BUTTON3_MASK) != 0) && (table.getSelectedRowCount() > 0)) {
					showMenuTable(event.getX(), event.getY());
				}
			}
		});

		// 주소록 추가 버튼 클릭 이벤트
		btnInsertAddress.addActionListener(e -> {
			insertUser();
		});

		// 테이블 주소록 삭제 클릭 이벤트
		// 다중삭제이벤트
		btnDeleteAddress.addActionListener(e -> {
			deleteUesr();
		});

		// 검색버튼 이벤트
		btnSearch.addActionListener(e -> {
			searchEvent();
		});

		this.userAll();

	}

	/**
	 * 데이터베이스연결 테스트
	 * 
	 */
	private void userAll() {

		System.out.println("DB연결ㅋ");
		List<UserVO> userList = userDataHandler.getInstance().selectAllUser();

		
		String name = "한선";
		userList = userDataHandler.getInstance().selectAllUser(name);
		System.out.println(userList);
		
		int group_no = 3;

		UserDataHandler.getInstance().selectUserListByGroup(group_no);
		System.out.println("dddd");

		System.out.println("실행이되었는가?");
	}

	/**
	 * 트리 초기화 메서드
	 */
	private void initTree() {

		System.out.println("==========트리 초기화 시작=========");

		if (pickData.equals("file")) {
			this.setGroupTree(FileHandler.getInstance().getGroupList());
		} else {
			this.setGroupTree((ArrayList<GroupVO>) groupDataHandler.getInstance().selectGroupList());
		}

	}

	/**
	 * 그룹 추가 이벤트
	 */
	private void addGroupEvent() {

		String addGroupName = JOptionPane.showInputDialog(contentPane, "추가할 그룹명을 작성하세요", "그룹 추가하기", JOptionPane.CLOSED_OPTION);

		GroupVO groupInsert = new GroupVO();
		
		if (addGroupName == null) {
			JOptionPane.showMessageDialog(contentPane, "그룹추가가 취소되었습니다.", "취소", JOptionPane.OK_OPTION);
			return;
		}

		if (this.checkGroupName(addGroupName)) {
			DefaultMutableTreeNode newGroup = null;

			if (pickData.equals("file")) {
				newGroup = new DefaultMutableTreeNode(FileHandler.getInstance().addGroup(addGroupName));
			} else {
				// TODO 물어볼부분
				newGroup = new DefaultMutableTreeNode(GroupDataHandler.getInstance().insertGroup(addGroupName));
			}

			System.out.println("newGroup = " + newGroup.getUserObject().toString());
			System.out.println("root is null ? " + (this.root == null));
			System.out.println("root child = " + root.getChildCount());
			((DefaultTreeModel) tree.getModel()).insertNodeInto(newGroup, root, root.getChildCount());
			((DefaultTreeModel) tree.getModel()).reload();
			JOptionPane.showMessageDialog(contentPane, "그룹추가가 완료되었습니다.", "성공", 1);

			System.out.println(addGroupName);

		} else {
			JOptionPane.showMessageDialog(contentPane, "그룹명이 중복되어서 등록할 수없습니다.", "취소", JOptionPane.OK_OPTION);
			return;
		}
	}

	/**
	 * 그룹 삭제 이벤트 메서드
	 */
	private void deleteGroupEvent() {
		DefaultMutableTreeNode node = getSelectedNode();

		if (node == null) {
			JOptionPane.showMessageDialog(contentPane, "그룹을 선택한 후에만 삭제할 수 있습니다..", "주의", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (node.getChildCount() > 0) {
			JOptionPane.showMessageDialog(contentPane, "그룹 안에 정보목록이 있으므로 삭제할 수 없습니다.", "주의", JOptionPane.WARNING_MESSAGE);
		} else {
			int res = JOptionPane.showConfirmDialog(contentPane, "삭제하면 다시 복원 불가능합니다.삭제하시겠습니까?", "삭제확인", JOptionPane.YES_NO_OPTION);
			if (res == JOptionPane.OK_OPTION) {
				DefaultTreeModel model = (DefaultTreeModel) (tree.getModel());
				TreePath[] paths = tree.getSelectionPaths();
				for (int i = 0; i < paths.length; i++) {
					node = (DefaultMutableTreeNode) (paths[i].getLastPathComponent());
					model.removeNodeFromParent(node);
				}
			}
		}

		if (pickData.equals("file")) {
			FileHandler.getInstance().updateGroup(groupData);
			FileHandler.getInstance().deleteGroup(groupData);
		} else {
			GroupDataHandler.getInstance().updateGroup(groupData.getGroup_no());
			GroupDataHandler.getInstance().deleteGroup(groupData.getGroup_no());
		}

	}

	/**
	 * 검색버튼 이벤트
	 */
	private void searchEvent() {
		String keyword = txtSearch.getText();
		ArrayList<UserVO> userList = null;

		if (pickData.equals("file")) {
			userList = FileHandler.getInstance().searchUserListBykeyword(keyword);

		} else {
			userList = (ArrayList<UserVO>) userDataHandler.getInstance().selectAllUser(keyword);
		}

		setTableData(userList);

		txtSearch.setText("");

	}

	/**
	 * 주소록 추가버튼 Dialog 띄우는 메서드
	 */
	private void insertUser() {
		InsertAddressDialog insertAddressdiag = null;
		// 인스턴스화
		if (pickData.equals("file")) {
			insertAddressdiag = new InsertAddressDialog(this, "주소록 추가", "file");
		} else {
			insertAddressdiag = new InsertAddressDialog(this, "주소록 추가", "database");
		}
		// 주소록 추가화면 가운데 뜨게 하기
		insertAddressdiag.setLocationRelativeTo(main);
		insertAddressdiag.setVisible(true);

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
	private void showMenu(int x, int y) {

		JPopupMenu popup = new JPopupMenu();
		JMenuItem popnupItem = new JMenuItem("수정");
		popup.add(popnupItem);
		TreePath path = tree.getSelectionPath();
		Object node = path.getLastPathComponent();
		String selectGroupName = path.toString();

		if (node == tree.getModel().getRoot() || node == tree.getModel().getChild(root, 0)) {
			popnupItem.setEnabled(false);
		}

		popup.add(popnupItem);

		// 오른쪽 마우스 수정버튼 이벤트
		popnupItem.addActionListener(e -> {
			modifySelectedNode();
		});

		// 오른쪽 마우스 삭제 이벤트
		popnupItem = new JMenuItem("삭제");
		if (node == tree.getModel().getRoot() || node == tree.getModel().getChild(root, 0)) {
			popnupItem.setEnabled(false);
		}
		popup.add(popnupItem);

		popnupItem.addActionListener(e -> {
			deleteSelectedItems();
		});

		popup.show(tree, x, y);
	}

	/**
	 * 트리 노드 삭제하는 메서드
	 */
	private void deleteSelectedItems() {
		DefaultMutableTreeNode node = getSelectedNode();
		if (node.getChildCount() > 0) {
			System.out.println("node==?" + node.toString());
			JOptionPane.showMessageDialog(contentPane, "그룹 안에 정보목록이 있으므로 삭제할 수 없습니다.", "주의", JOptionPane.WARNING_MESSAGE);
		} else {
			if (node.toString().equals("그룹미지정")) {
				JOptionPane.showMessageDialog(contentPane, "미지정 그룹은 삭제할 수 없습니다.", "주의", JOptionPane.WARNING_MESSAGE);
				return;
			}
			int res = JOptionPane.showConfirmDialog(contentPane, "삭제하면 다시 복원 불가능합니다.삭제하시겠습니까?", "삭제확인", JOptionPane.YES_NO_OPTION);
			if (res == JOptionPane.OK_OPTION) {
				DefaultTreeModel model = (DefaultTreeModel) (tree.getModel());
				TreePath[] paths = tree.getSelectionPaths();
				for (int i = 0; i < paths.length; i++) {
					node = (DefaultMutableTreeNode) (paths[i].getLastPathComponent());
					model.removeNodeFromParent(node);
				}

				if (pickData.equals("file")) {
					FileHandler.getInstance().updateGroup(groupData);
					FileHandler.getInstance().deleteGroup(groupData);
				} else {
					groupDataHandler.getInstance().updateGroup(groupData.getGroup_no());
					groupDataHandler.getInstance().deleteGroup(groupData.getGroup_no());
				}

			}
		}
	}

	/**
	 * 트리 노드 수정하는 메서드
	 */
	private void modifySelectedNode() {
		DefaultMutableTreeNode node = getSelectedNode();
		if (node == null) {
			JOptionPane.showMessageDialog(contentPane, "변경할 그룹을 선택하세요", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		String groupNewname = JOptionPane.showInputDialog(contentPane, "새로운 그룹 이름을 입력하세요 ", "그룹이름 수정", JOptionPane.CLOSED_OPTION);
		if (groupNewname != null && !"".equals(groupNewname)) {
			System.out.println("====수정====");
			groupData.setGroup_name(groupNewname);
			node.setUserObject(groupData);

			System.out.println(groupData.getGroup_no());
			System.out.println("==========================");
			if (pickData.equals("file")) {
				FileHandler.getInstance().updateGroup(groupData);
				FileHandler.getInstance().searchUserListByGroup(groupData);
			} else {
				groupDataHandler.getInstance().updateGroup(groupData.getGroup_no());
				userDataHandler.getInstance().selectUserListByGroup(groupData.getGroup_no());
			}

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

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		ArrayList<UserVO> userList = null;

		if (node == null) {
			return;
		} else {
			GroupVO selectGroup = null;
			if (node.isRoot()) {

			} else {
				selectGroup = (GroupVO) node.getUserObject();
			}

			if (pickData.equals("file")) {
				userList = FileHandler.getInstance().searchUserListByGroup(selectGroup);
			} else {
				if (selectGroup == null) {
					userList = (ArrayList<UserVO>) UserDataHandler.getInstance().selectAllUser();
				} else {
					userList = (ArrayList<UserVO>) userDataHandler.getInstance().selectUserListByGroup(selectGroup.getGroup_no());
				}
			}
		}

		setTableData(userList);

//		TreePath path = tree.getSelectionPath();
//		ArrayList<UserVO> userList = null;
//		if (pickData.equals("file")) {
//			if (path != null) {
//
//				String selectGroupName = path.toString();
//				System.out.println("-----------------------여기");
//				System.out.println("그룹이름 : " + selectGroupName);
//				if (selectGroupName.equals("[전체 ]")) {
//					System.out.println("확인");
//					setTableData(FileHandler.getInstance().searchUserListByGroup(null));
//				}
//
//			}
//
//			if (node == null) {
//				return;
//			}
//
//			Object obj = (Object) node.getUserObject();
//			System.out.println(obj.getClass());
//			if (obj instanceof GroupVO) {
//				GroupVO group = (GroupVO) obj;
//
//				groupData = group;
//				userList = FileHandler.getInstance().searchUserListByGroup(group);
//			} else {
//				userList = FileHandler.getInstance().searchUserListByGroup(null);
//			}
//
//		} else {
//			if (path != null) {
//
//				String selectGroupName = path.toString();
//				System.out.println("-----------------------여기");
//				System.out.println("그룹이름 : " + selectGroupName);
//				if (selectGroupName.equals("[전체 ]")) {
//					System.out.println("확인");
//					setTableData((ArrayList<UserVO>) UserDataHandler.getInstance().selectAllUser());
//				}
//
//			}
//
//			if (node == null) {
//				return;
//			}
//
//			Object obj = (Object) node.getUserObject();
//			System.out.println(obj.getClass());
//			if (obj instanceof GroupVO) {
//				GroupVO group = (GroupVO) obj;
//
//				groupData = group;
//				userList = (ArrayList<UserVO>) userDataHandler.getInstance().selectUserListByGroup(group.getGroup_no());
//			} else {
//				// 데이터가 없을때는 리스트가 출력되면 안됨
//				userList = (ArrayList<UserVO>) userDataHandler.getInstance().selectAllUser();
////				userList = null;
//			}
//
//		}
//
//		setTableData(userList);

	}

	/**
	 * 그룹 명 중복 체크
	 * 
	 * @param groupName
	 * @return
	 */
	public boolean checkGroupName(String groupName) {

		List<GroupVO> groupList = null;
		if (pickData.equals("file")) {
			groupList = FileHandler.getInstance().getGroupList();
		} else {
			groupList = groupDataHandler.getInstance().selectGroupList();
		}

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

//		table.getModel();
//		model.setNumRows(0);

		model.removeAll();
		model.addDataList(userList);

//		for (int i = 0; i < userList.size(); i++) {
//			model.addRow(new Object[] { false, userList.get(i).getAd_no(), userList.get(i).getAd_name(), userList.get(i).getAd_hp(), userList.get(i).getAd_mail(), userList.get(i).getAd_com(), userList.get(i).getAd_department(), userList.get(i).getAd_postion(), userList.get(i).getAd_memo(), userList.get(i).getGroup_no() });
//		}

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

		System.out.println("model.getData(selectedRow)====" + model.getData(selectedRow).toString());

		UserVO userData = new UserVO();
		userData = (UserVO) model.getData(selectedRow);

		UpdateAddressDialog updateAddressdiag = null;

		if (pickData.equals("file")) {
			updateAddressdiag = new UpdateAddressDialog(this, "주소록 수정", userData, "file");
		} else {
			updateAddressdiag = new UpdateAddressDialog(this, "주소록 수정", userData, "database");
		}

		updateAddressdiag.setLocationRelativeTo(null);
		System.out.println(">>>선택한 유저 번호" + userData.getAd_no());
		System.out.println("selectedRow_getGroup_no : = " + userData.getGroup_no());
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
		System.out.println("직책: " + user.getAd_position());
		System.out.println("메모 : " + user.getAd_memo());
		System.out.println("그룹번호 : " + user.getGroup_no());
		System.out.println("==================끝===================");

		MappingVO mappingInfo = new MappingVO();
		
		String[] userGroupArray = user.getGroup_no().split(",");
		String group_no="";
		

		if (pickData.equals("file")) {
			FileHandler.getInstance().addUser(user);
		} else {
			UserDataHandler.getInstance().insertUser(user);
			int ad_no = UserDataHandler.getInstance().selectUserNum();
			mappingInfo.setAd_no(String.valueOf(ad_no));

			if(user.getGroup_no().contains(",")==false) {
				mappingInfo.setGroup_no(user.getGroup_no());
				UserDataHandler.getInstance().insertMappingUser(mappingInfo);
			}else {
				
				for (int i = 0; i < userGroupArray.length; i++) {
					group_no = userGroupArray[i];
					mappingInfo.setGroup_no(group_no);
					UserDataHandler.getInstance().insertMappingUser(mappingInfo);
				}
				
			}
			
		}

		// 추가
		model.addData(user);

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
		System.out.println("직책: " + updateuser.getAd_position());
		System.out.println("메모 : " + updateuser.getAd_memo());
		System.out.println("그룹번호 : " + updateuser.getGroup_no());
		System.out.println("==================끝===================");

		this.model.remove(selectedRow);

		// 수정
		this.model.addData(updateuser);

	}

	/**
	 * 테이블에 있는 유저를 삭제하는 메서드 체크박스 체크된 유저들만
	 */
	private void deleteUesr() {
		System.out.println("---delete start---");

		int tableRowCount = table.getModel().getRowCount();

		ArrayList<UserVO> deleteUserList = new ArrayList<UserVO>();

		for (int i = 0; i < model.getRowCount(); i++) {
			UserVO user = (UserVO) model.getData(i);
			if (user.isChecked()) {
				deleteUserList.add(user);
			}
		}

		if (pickData.equals("file")) {

			FileHandler.getInstance().deleteUser(deleteUserList);
		} else {

			for (int j = 0; j < deleteUserList.size(); j++) {

				userDataHandler.getInstance().deleteUser(Integer.parseInt(deleteUserList.get(j).getAd_no()));
			}
		}

		// 화면에서 삭제처리
		for (UserVO userVO : deleteUserList) {
			this.model.remove(userVO);
		}

		System.out.println("---delete end---");

	}

	/**
	 * 선택된 사용자 삭제하기
	 * 
	 * @param row
	 */
	private void deleteUesr(int row) {
		ArrayList<UserVO> deleteUserList = new ArrayList<UserVO>();
		UserVO userData = new UserVO();
		userData.setAd_no(String.valueOf(model.getValueAt(row, 1)));
		deleteUserList.add(userData);

		if (pickData.equals("file")) {
			FileHandler.getInstance().deleteUser(deleteUserList);
		} else {
			for (int j = 0; j < deleteUserList.size(); j++) {

				userDataHandler.getInstance().deleteUser(Integer.parseInt(userData.getAd_no()));
			}
		}

		model.remove(row);
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

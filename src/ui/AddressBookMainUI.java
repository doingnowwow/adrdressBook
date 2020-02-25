package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Enumeration;

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

public class AddressBookMainUI extends JFrame implements IAddUser, MouseListener, TreeSelectionListener {

	private UserVO userData = new UserVO();
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
	private InsertAddressDialog insertAddressdiag;
	private UpdateAddressDialog updateAddressdiag;
	private DefaultMutableTreeNode root;

	// 파일읽기

	private FileHandler filehandler = new FileHandler();;

	ArrayList<GroupVO> groupList = filehandler.readGroup();
	ArrayList<UserVO> userList = filehandler.readUser();
	
	
//	ArrayList<GroupVO> groupList = new ArrayList<GroupVO>();
	private DefaultTableModel model;

	// 테이블 초기 셋팅
	String colNames[] = { "x", "번호", "이름", "핸드폰번호", "이메일", "회사", "부서", "직책", "메모", "그룹" };

	// 테이블 선택시 이벤트 발생을 위해

	// 테이블 row
	int selectedRow;

	// 테이블 column
	int selectedCol;

	// 사람
	int lastRow = 0;

	private Object tabeData = null; // 비어있는 오브젝트 생성

	/**
	 * Create the frame.
	 */
	public AddressBookMainUI() {
		jbInit();
	}

	private void jbInit() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 601);

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
// 트리 값 넣기
//		DefaultMutableTreeNode root = new DefaultMutableTreeNode("전체");
//
//		DefaultMutableTreeNode gFamily = new DefaultMutableTreeNode("가족");
//		DefaultMutableTreeNode gFriend = new DefaultMutableTreeNode("친구");
//		DefaultMutableTreeNode gCompany = new DefaultMutableTreeNode("회사");
//		DefaultMutableTreeNode noGroup = new DefaultMutableTreeNode("그룹없음");
//		DefaultMutableTreeNode child1_child1 = new DefaultMutableTreeNode("1번째자식의 1번째자식");
//		root.add(gFamily);
//		root.add(gFriend);
//		root.add(gCompany);
//		root.add(noGroup);
//		gFamily.add(child1_child1);
//
//		DefaultTreeModel treemodel = new DefaultTreeModel(root);
//		treemodel.setRoot(root);
//
//		// 트리부분
//		tree = new JTree(treemodel);

		// 트리 넣기
		initTree();

		// 트리 아이콘 설정하기
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setLeafIcon(renderer.getDefaultClosedIcon());

		tree.setCellRenderer(renderer);
		tree.setEditable(true);
//		tree.setSelectionRow(0);
		tree.setDragEnabled(true);
		tree.setDropMode(DropMode.ON_OR_INSERT);
		treeSclPane.setViewportView(tree);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
		expandTree(tree);

		// 트리 마우스 리스너
		tree.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent event) {
				// 마우스 선택 이벤트
				if (tree.getSelectionCount() > 0) {
					DefaultMutableTreeNode selectedNode = getSelectedNode();
					Object obj = selectedNode.getUserObject();
					Object pobj = null;
					DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectedNode.getParent();
					if (parentNode != null)
						pobj = parentNode.getUserObject();
					System.out.println("Selected MOUSE **>: [" + obj + "], isLeaf[" + selectedNode.isLeaf()
							+ "], parent:[" + pobj + "]");
					System.out.println();
				}
				if (((event.getModifiers() & InputEvent.BUTTON3_MASK) != 0) && (tree.getSelectionCount() > 0)) {
					showMenu(event.getX(), event.getY());
				}
			}
		});

		tree.addMouseListener(this);

		// 그룹추가버튼 이벤트
		btnInsertGroup.addActionListener(e -> {
//			insertGroupdiag.setVisible(true);
			String addGroupName = JOptionPane.showInputDialog(leftSplitPane, "추가할 그룹명을 작성하세요", "그룹 추가하기",
					JOptionPane.CLOSED_OPTION);

			if (addGroupName == null) {
				JOptionPane.showMessageDialog(leftSplitPane, "그룹추가가 취소되었습니다..", "취소", JOptionPane.OK_OPTION);

			} else {

				DefaultMutableTreeNode newGroup = new DefaultMutableTreeNode(addGroupName);

				TreePath rootPath = tree.getSelectionPath();
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) rootPath.getLastPathComponent();

				((DefaultTreeModel) tree.getModel()).insertNodeInto(newGroup, selectedNode,
						selectedNode.getChildCount());

				JOptionPane.showMessageDialog(leftSplitPane, "그룹추가가 완료되었습니다.", "성공", 1);

			}

			System.out.println(addGroupName);

		});

		// 그룹삭제이벤트
		btnDeleteGroup.addActionListener(e -> {

//			 TreePath selectionPath=tree.getSelectionPath();
//			 DefaultMutableTreeNode selectedNode= (DefaultMutableTreeNode)selectionPath.getLastPathComponent();
//			
//			((DefaultTreeModel)tree.getModel()).removeNodeFromParent(selectedNode);
//			
//			  JOptionPane.showMessageDialog(null, "그룹삭제가 완료되었습니다.", "성공", 1);

			DefaultMutableTreeNode node = getSelectedNode();

			if (node.getChildCount() > 0) {
				JOptionPane.showMessageDialog(leftSplitPane, "그룹 안에 정보목록이 있으므로 삭제할 수 없습니다.", "주의",
						JOptionPane.WARNING_MESSAGE);
			} else {
				int res = JOptionPane.showConfirmDialog(leftSplitPane, "삭제하면 다시 복원 불가능합니다.삭제하시겠습니까?", "삭제확인",
						JOptionPane.YES_NO_OPTION);
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
				case 10:

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

		// 샘플 데이터
//		model.addRow(new Object[] { false, "1", "김한선", "123-123-456", "dd@naver.com", "4", "5555", "예", "메모", "%%" });
//		model.addRow(new Object[] { false, "2", "홍길동", "123-123-456", "dd@kakao.com", "ㅇㅇ", "00", "ㅇㅇ", "ㅇㅇ", "" });

		table = new JTable(model);

		JCheckBox checkBox = new JCheckBox();

		DefaultCellEditor checkboxRend = new DefaultCellEditor(checkBox);
//			
		TableRenderer tableR = new TableRenderer();
		table.getColumn("x").setCellRenderer(tableR);
		table.getColumn("x").setCellEditor(checkboxRend);

		table.getTableHeader().setReorderingAllowed(false);// 컬럼들 이동 불가
		table.getTableHeader().setResizingAllowed(false); // 컬럼 크기 조절 불가
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
					System.out.println("Selected MOUSE TABLE --> row: [" + selectedRow + "], column : [" + selectedCol
							+ "], 선택한내용:[" + pobj.toString() + "]");

				}

				if (((event.getModifiers() & InputEvent.BUTTON3_MASK) != 0) && (table.getSelectedRowCount() > 0)) {
					showMenuTable(event.getX(), event.getY());
				}
			}
		});

		// 인스턴스화
		insertAddressdiag = new InsertAddressDialog(this, "주소록 추가");

		// 주소록 추가 버튼 클릭 이벤트
		btnInsertAddress.addActionListener(e -> {
			insertAddressdiag.setVisible(true);

		});

		// 테이블 주소록 삭제 클릭 이벤트
		// 다중삭제이벤트
		btnDeleteAddress.addActionListener(e -> {

			System.out.println("---delete start---");

			int tableRowCount = table.getModel().getRowCount();
			Object tablecheckbox = null;

			for (int i = tableRowCount; i > 0; i--) {

				tablecheckbox = table.getValueAt(i - 1, 0);

				System.out.println(i - 1 + "i?");

				if (tablecheckbox.equals(true)) {
					((DefaultTableModel) table.getModel()).removeRow(i - 1);
				}
				System.out.println("---delete end---");
			}

		});

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
		System.out.println("그룹이름 : " + user.getGroup_name());
		System.out.println("==================끝===================");

		lastRow++;

		user.setAd_no(lastRow);

		// 추가
		((DefaultTableModel) table.getModel()).addRow(new Object[] { false, user.getAd_no(), user.getAd_name(),
				user.getAd_hp(), user.getAd_mail(), user.getAd_com(), user.getAd_department(), user.getAd_postion(),
				user.getAd_memo(), user.getGroup_name() });

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
		System.out.println("그룹이름 : " + updateuser.getGroup_name());
		System.out.println("==================끝===================");

		int userNo = updateuser.getAd_no();

		((DefaultTableModel) table.getModel()).removeRow(selectedRow);

		// 수정
		((DefaultTableModel) table.getModel()).addRow(new Object[] { false, userNo, updateuser.getAd_name(),
				updateuser.getAd_hp(), updateuser.getAd_mail(), updateuser.getAd_com(), updateuser.getAd_department(),
				updateuser.getAd_postion(), updateuser.getAd_memo(), updateuser.getGroup_name() });

	}

//////////////////////트리 관련 메서드//////////////////////////////////////////////////

	/**
	 * 트리 초기화 메서드
	 */
	private void initTree() {

//		filehandler = new FileHandler();
		System.out.println("==========트리 초기화 시작=========");

//		ArrayList<GroupVO> groupList = filehandler.readGroup();
//		ArrayList<UserVO> userList = filehandler.readUser();

		for (int i = 0; i < groupList.size(); i++) {
			// 1. 그룹꺼내기
			GroupVO group = groupList.get(i);

			for (int j = 0; j < userList.size(); j++) {

				// 2. user 꺼내기 1개
				UserVO user = userList.get(j);

				// 3. 그룹 이름비교

				if (group.getGroup_name().equals(user.getGroup_name())) {
					group.addUser(user);
				}

			}

		}

		this.setGroupTree(groupList);
		lastRow += 3;
	}

	/**
	 * 트리 데이터 셋팅해주는 메서드
	 * 
	 * @param groupList
	 */

	private void setGroupTree(ArrayList<GroupVO> groupList) {

		DefaultMutableTreeNode root, node;

		root = new DefaultMutableTreeNode("전체 ");

		for (GroupVO groupData : groupList) {
			node = new DefaultMutableTreeNode(groupData);

			for (UserVO userData : groupData.getMemberList()) {
				node.add(new DefaultMutableTreeNode(userData));
			}
			root.add(node);
		}

		tree = new JTree(root);
		tree.addTreeSelectionListener(this);

	}

//tree부분

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

		if (node == tree.getModel().getRoot() || selectGroupName.equals("전체") || selectGroupName.equals("가족")
				|| selectGroupName.equals("친구") || selectGroupName.equals("회사") || selectGroupName.equals("그룹없음")) {
			mi.setEnabled(false);
			System.out.println(path.toString().equals("전체") + path.toString());
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
			JOptionPane.showMessageDialog(leftSplitPane, "그룹 안에 정보목록이 있으므로 삭제할 수 없습니다.", "주의",
					JOptionPane.WARNING_MESSAGE);
		} else {
			int res = JOptionPane.showConfirmDialog(leftSplitPane, "삭제하면 다시 복원 불가능합니다.삭제하시겠습니까?", "삭제확인",
					JOptionPane.YES_NO_OPTION);
			if (res == JOptionPane.OK_OPTION) {
				DefaultTreeModel model = (DefaultTreeModel) (tree.getModel());
				TreePath[] paths = tree.getSelectionPaths();
				for (int i = 0; i < paths.length; i++) {
					node = (DefaultMutableTreeNode) (paths[i].getLastPathComponent());
					model.removeNodeFromParent(node);
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
			JOptionPane.showMessageDialog(leftSplitPane, "변경할 그룹을 선택하세요", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String name = JOptionPane.showInputDialog(leftSplitPane, "새로운 그룹 이름을 입력하세요 ", "그룹이름 수정",
				JOptionPane.CLOSED_OPTION);
		if (name != null && !"".equals(name)) {
			node.setUserObject(name);
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

///////////////////////////////테이블관련메서드/////////////////////////////////////		
	/**
	 * 트리에서 선택한 값 테이블에 넣어주기
	 * 
	 * @param userList
	 */
	private void setTableData(ArrayList<UserVO> userList) {

		table.getModel();
		model.setNumRows(0);

		for (int i = 0; i < userList.size(); i++) {
			model.addRow(new Object[] { false, userList.get(i).getAd_no(), userList.get(i).getAd_name(),
					userList.get(i).getAd_hp(), userList.get(i).getAd_mail(), userList.get(i).getAd_com(),
					userList.get(i).getAd_department(), userList.get(i).getAd_postion(), userList.get(i).getAd_memo(),
					userList.get(i).getGroup_name() });

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
			deleteUserRow();
		});

		popup.show(table, x, y);
	}

	/**
	 * 테이블 한 줄 수정하는 메서드
	 */
	private void updateUserRow() {

		String tableValue[] = new String[10];

		for (int i = 1; i < 10; i++) {

			tabeData = table.getValueAt(selectedRow, i);

//			String tabaDataValue = tabeData.toString();
//			tableValue[i] = tabaDataValue;

			System.out.println("UPDATE *-> row : [" + selectedRow + "]  col : [" + "순서 : " + i + "~  " + tabeData + " ]"
					+ i + "번째 : col의 값 : [" + tableValue[i] + "]");

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
			} else if (i == 9) {
				if (tabeData != null) {
					userData.setGroup_name(tabeData.toString());
				}
			}

		}

		System.out.println("======================for end ===========================");

		updateAddressdiag = new UpdateAddressDialog(this, "주소록 수정", userData);
		System.out.println(">>>선택한 유저 번호" + userData.getAd_no());
		System.out.println("*******************선택한유저이름?********" + userData.getAd_name());
		System.out.println("*******************선택한유저 핸드폰번호?********" + userData.getAd_hp());
		updateAddressdiag.setVisible(true);

	}

	/**
	 * 테이블 한 줄 삭제
	 */
	public void deleteUserRow() {
		((DefaultTableModel) table.getModel()).removeRow(selectedRow);

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * 트리 선택 이벤트
	 */
	@Override
	public void valueChanged(TreeSelectionEvent e) {

		DefaultMutableTreeNode node;
		node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

		TreePath path = tree.getSelectionPath();
		String selectGroupName = path.toString();

		System.out.println("-----------------------여기");
		System.out.println("그룹이름 : " + selectGroupName);

		if (selectGroupName.equals("[전체 ]")) {
			ArrayList<UserVO> userList = new ArrayList<UserVO>();
			table.getModel();
			model.setNumRows(0);

			for (int i = 0; i < groupList.size(); i++) {
				userList.addAll(groupList.get(i).getMemberList());
			}

			System.out.println("userList.size=" + userList.size());
			for (int i = 0; i < userList.size(); i++) {
				model.addRow(new Object[] { false, userList.get(i).getAd_no(), userList.get(i).getAd_name(),
						userList.get(i).getAd_hp(), userList.get(i).getAd_mail(), userList.get(i).getAd_com(),
						userList.get(i).getAd_department(), userList.get(i).getAd_postion(),
						userList.get(i).getAd_memo(), userList.get(i).getGroup_name() });

			}
		}

		if (node == null)
			return;

		Object obj = (Object) node.getUserObject();
		if (obj instanceof GroupVO) {
			GroupVO group = (GroupVO) obj;

			setTableData((ArrayList<UserVO>) group.getMemberList());

		} else if (obj instanceof UserVO) {

			ArrayList<UserVO> userList = new ArrayList<UserVO>();

			UserVO user = (UserVO) obj;
			userList.add(user);
			setTableData((ArrayList<UserVO>) userList);

			System.out.println("User번호  : " + user.getAd_no() + "\ruser 이름 : " + user.getAd_name() + "회사  : "
					+ user.getAd_com() + "이메일 : " + user.getAd_mail());

		}

	}

}

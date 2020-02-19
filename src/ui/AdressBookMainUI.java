package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;


public class AdressBookMainUI extends JFrame implements IAddUser {

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
	private InsertGroupDialog insertGroupdiag;
	private DefaultMutableTreeNode root;
	
	
	
	
	/**
	 * Create the frame.
	 */
	public AdressBookMainUI() {
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

		//왼쪽부분시작
		leftSplitPane = new JPanel();
		mainSplitPane.setLeftComponent(leftSplitPane);
		leftSplitPane.setLayout(new BorderLayout(0, 0));

		//왼쪽 JTree테이블 부분
		leftSouthPane = new JPanel();
		leftSplitPane.add(leftSouthPane, BorderLayout.SOUTH);
		leftSouthPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		//왼쪽 제목
		leftTopPane = new JPanel();
		leftSplitPane.add(leftTopPane, BorderLayout.NORTH);
		lblGroup = new JLabel("그룹관리");
		leftTopPane.add(lblGroup);

		//왼쪽 아래 버튼 
		btnInsertGroup = new JButton("그룹추가");
		leftSouthPane.add(btnInsertGroup);
		btnDeleteGroup = new JButton("그룹삭제");
		leftSouthPane.add(btnDeleteGroup);
		
		insertGroupdiag = new InsertGroupDialog(this, "그룹추가");

		
		//트리 스크롤 스판
		treeSclPane = new JScrollPane();
		leftSplitPane.add(treeSclPane, BorderLayout.CENTER);
		
		
		//트리 값 넣기
		  DefaultMutableTreeNode root = new DefaultMutableTreeNode("전체"); 
		  DefaultMutableTreeNode gFamily = new DefaultMutableTreeNode("가족");
		  DefaultMutableTreeNode gFriend = new DefaultMutableTreeNode("친구");
		  DefaultMutableTreeNode gCompany = new DefaultMutableTreeNode("회사");
		  DefaultMutableTreeNode noGroup = new DefaultMutableTreeNode("그룹없음");
		  DefaultMutableTreeNode child1_child1 = new DefaultMutableTreeNode("1번째자식의 1번째자식");
//		  DefaultMutableTreeNode child1_child2 = new DefaultMutableTreeNode("1번째자식의 2번째자식");
//		  DefaultMutableTreeNode child2_child1 = new DefaultMutableTreeNode("2번째자식의 1번째자식");
//		  DefaultMutableTreeNode child2_child2 = new DefaultMutableTreeNode("2번째자식의 2번째자식");
		  root.add(gFamily);
		  root.add(gFriend);
		  root.add(gCompany);
		  root.add(noGroup);
		  gFamily.add(child1_child1);
//		  child1.add(child1_child1); 
//		  child1.add(child1_child2);
//		  child2.add(child2_child1); 
//		  child2.add(child2_child2);

		
		  
		  //트리부분
		tree = new JTree(root);
		tree.setSelectionRow(0);
		tree.setDragEnabled(true);
		tree.setDropMode(DropMode.ON_OR_INSERT);
		treeSclPane.setViewportView(tree);
		
	     tree.getSelectionModel().setSelectionMode(TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);   
	     expandTree(tree);   
		 tree.addMouseListener(new MouseAdapter() {
		    	
			    public void mousePressed(MouseEvent event) {
					// 마우스 선택 이벤트    	
					if( tree.getSelectionCount() > 0) {
						DefaultMutableTreeNode selectedNode = getSelectedNode();
						Object obj = selectedNode.getUserObject();
						Object pobj = null;
						DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectedNode.getParent();
						if(parentNode != null) pobj = parentNode.getUserObject();
						System.out.println("Selected: [" + obj + "], isLeaf[" + selectedNode.isLeaf() + "], parent:[" + pobj + "]");
					}
					if (((event.getModifiers() & InputEvent.BUTTON3_MASK) != 0)
					    && (tree.getSelectionCount() > 0)) {
						showMenu(event.getX(), event.getY());
					}
			    }
		    });
		
		
	
		
		//그룹추가버튼 이벤트
		btnInsertGroup.addActionListener(e->{
//			insertGroupdiag.setVisible(true);
			String addGroupName = JOptionPane.showInputDialog(contentPane, "추가할 그룹명을 작성하세요", "그룹 추가하기", JOptionPane.CLOSED_OPTION);
			

			if (addGroupName == null){
				JOptionPane.showMessageDialog(null, "그룹추가가 취소되었습니다..", "취소", JOptionPane.OK_OPTION);
				
			} else {
				
					DefaultMutableTreeNode newGroup = new DefaultMutableTreeNode(addGroupName);
					
					TreePath rootPath = tree.getSelectionPath();
					DefaultMutableTreeNode selectedNode= (DefaultMutableTreeNode)rootPath.getLastPathComponent();
					
					
					((DefaultTreeModel)tree.getModel()).insertNodeInto(newGroup,selectedNode,selectedNode.getChildCount());
					
					JOptionPane.showMessageDialog(null, "그룹추가가 완료되었습니다.", "성공", 1);
					
				
			}
			
			
			System.out.println(addGroupName);
			
			
			
		});
		
		//그룹삭제이벤트
		
		btnDeleteGroup.addActionListener(e->{
			
			 TreePath selectionPath=tree.getSelectionPath();
			 DefaultMutableTreeNode selectedNode= (DefaultMutableTreeNode)selectionPath.getLastPathComponent();
			
			((DefaultTreeModel)tree.getModel()).removeNodeFromParent(selectedNode);
			
			  JOptionPane.showMessageDialog(null, "그룹삭제가 완료되었습니다.", "성공", 1);
		});
		
		
		

		//오른쪽
		rightSplitPane = new JPanel();
		mainSplitPane.setRightComponent(rightSplitPane);
		rightSplitPane.setLayout(new BorderLayout(0, 0));

		//오른쪽 North
		rigthNorthPanel = new JPanel();
		rightSplitPane.add(rigthNorthPanel, BorderLayout.NORTH);
		rigthNorthPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		//검색 txt필드
		txtSearch = new JTextField();
		rigthNorthPanel.add(txtSearch);
		txtSearch.setColumns(10);
		
		//검색버튼
		btnSearch = new JButton("검색");
		rigthNorthPanel.add(btnSearch);

		//오른쪽 South
		rigthSoutPanel = new JPanel();
		rightSplitPane.add(rigthSoutPanel, BorderLayout.SOUTH);
		rigthSoutPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		
		//오른쪽 전체 화면 아래
		btnInsertAddress = new JButton("주소록추가");
		rigthSoutPanel.add(btnInsertAddress);
		btnDeleteAddress = new JButton("주소록삭제");
		rigthSoutPanel.add(btnDeleteAddress);

		//오른쪽 가운데 스크롤 스판
		tableSclPane = new JScrollPane();
		rightSplitPane.add(tableSclPane, BorderLayout.CENTER);
		
		//테이블 에 들어갈 부분
		String colNames[] = { "체크박스","이름", "핸드폰번호", "이메일", "회사", "부서", "직책", "메모", "그룹" };
		DefaultTableModel model = new DefaultTableModel(colNames, 0);
		model.addRow(new Object[] {false, "김한선", "123-123-456", "dd@naver.com", "ㅇㅇ", "ㅇㅇ", "ㅇㅇ", "ㅇㅇ", "%%" });
		model.addRow(new Object[] {false, "홍길동", "123-123-456", "dd@naver.com", "ㅇㅇ", "ㅇㅇ", "ㅇㅇ", "ㅇㅇ", "" });
		table = new JTable(model);
		
		table.getColumn("체크박스"	).setCellRenderer(dcr);
		JCheckBox box = new JCheckBox();
		   box.setHorizontalAlignment(JLabel.CENTER);
		   table.getColumn("체크박스").setCellEditor(new DefaultCellEditor(box));
		  tableSclPane.setViewportView(table);


		
		
		
		//인스턴스화
		insertAddressdiag = new InsertAddressDialog(this, "주소록 추가");
		
		//주소록 추가 버튼 클릭 이벤트
		btnInsertAddress.addActionListener(e->{
			insertAddressdiag.setVisible(true);

			
			
		});
}
	 
	
	public void addUser() {
		System.out.println("넘어온다");
	}
	
	
	//tree부분
	//오늘쪽 마우스 이벤트 메뉴(tree)
		protected void showMenu(int x, int y) {
			
			JPopupMenu popup = new JPopupMenu();
		    JMenuItem mi = new JMenuItem("수정");
		    popup.add(mi);
		    
		    TreePath path = tree.getSelectionPath();
		    Object node = path.getLastPathComponent();
		    
		    String selectGroupName = path.toString();
		    
		    if (node == tree.getModel().getRoot() || selectGroupName.equals("[전체]")||selectGroupName.equals("[가족]")||selectGroupName.equals("[친구]")||selectGroupName.equals("[회사]")||selectGroupName.equals("[그룹없음]")) {
		      mi.setEnabled(false);
		      System.out.println(path.toString().equals("[전체]") + path.toString());
		    }
		    popup.add(mi);
		    
		    mi.addActionListener(e->{
		    	 modifySelectedNode();
		    });
		    
		  
		    mi = new JMenuItem("삭제");
		    if (node == tree.getModel().getRoot()) {
		      mi.setEnabled(false);
		    }
		    popup.add(mi);
		    mi.addActionListener(e->{
		    	 deleteSelectedItems();
		    });
		  
		    popup.show(tree, x, y);
		}
	
		//tree 그룹 삭제
		protected void deleteSelectedItems() {
		    DefaultMutableTreeNode node = getSelectedNode();
		    if(node.getChildCount() > 0) {
				JOptionPane.showMessageDialog(null, "그룹 안에 정보목록이 잇으므로 삭제할 수 없습니다.", "주의", JOptionPane.WARNING_MESSAGE); 
			}
			else {
				int res = JOptionPane.showConfirmDialog(null, "삭제하면 다시 복원 불가능합니다.삭제하시겠습니까?", "삭제확인", JOptionPane.YES_NO_OPTION);
				if(res == JOptionPane.OK_OPTION) {
			        DefaultTreeModel model = (DefaultTreeModel) (tree.getModel());
			        TreePath[] paths = tree.getSelectionPaths();
			        for (int i = 0; i < paths.length; i++) {
			          node = (DefaultMutableTreeNode) (paths[i].getLastPathComponent());
			          model.removeNodeFromParent(node);
			        }
				}
			}
		}

		
		//tree그룹수정
		private void modifySelectedNode() {
		    DefaultMutableTreeNode node = getSelectedNode();
		    if (node == null) {
				JOptionPane.showMessageDialog(null, "변경할 그룹을 선택하세요", "Error",
				          JOptionPane.ERROR_MESSAGE);
				return;
		    }
		    String name = JOptionPane.showInputDialog(null, "새로운 그룹 이름을 입력하세요 ","그룹이름 수정",JOptionPane.CLOSED_OPTION);
			if(name != null && !"".equals(name)) {
				node.setUserObject(name);
			}
		}
		
		//tree 그룹 패스 기본값
		private DefaultMutableTreeNode getSelectedNode() {
		    return (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		}
		
		private void expandTree(JTree tree){   
	        DefaultMutableTreeNode root = (DefaultMutableTreeNode)tree.getModel().getRoot();   
	        Enumeration e = root.breadthFirstEnumeration();   
	        while(e.hasMoreElements()) {   
	            DefaultMutableTreeNode node = (DefaultMutableTreeNode)e.nextElement();   
	            if(node.isLeaf()) continue;   
	            int row = tree.getRowForPath(new TreePath(node.getPath()));   
	            tree.expandRow(row);   
	        }   
	    } 
		
		
		
		//테이블 // 셀렌더러	 
		 DefaultTableCellRenderer dcr = new DefaultTableCellRenderer() {
		  public Component getTableCellRendererComponent    (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)  {
		   JCheckBox box= new JCheckBox();
		   box.setSelected(((Boolean)value).booleanValue());  
		   box.setHorizontalAlignment(JLabel.CENTER);
		   return box;
		  }
		 };


	
}

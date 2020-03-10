package hs.addressbook.handler.database;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import hs.addressbook.data.GroupVO;
import hs.addressbook.ibatis.config.SqlMapClientFactory;

public class GroupDataHandler {

	private static final GroupDataHandler instance = new GroupDataHandler();

	private SqlMapClient client;

	public static GroupDataHandler getInstance() {
		return instance;
	}

	public GroupDataHandler() {
		client = SqlMapClientFactory.getInstance();
	}

	public List<GroupVO> selectGroupList() {
		List<GroupVO> selectGroupList = null;
		try {
			selectGroupList = client.queryForList("group.selectGroupList");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return selectGroupList;
	}

	public GroupVO insertGroup(String group_name) {
		
		GroupVO group = new GroupVO();
		
		try {
			group.setGroup_no((int) client.queryForObject("group.selectNextGroupNum"));
			group.setGroup_name(group_name);
			client.insert("group.insertGroup",group);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return group;
	}

	public void deleteGroup(int group_no) {
		try {
			//그룹삭제시 mapping테이블 삭제
			client.delete("mapping.deleteMappingGroup",group_no);
			
			//그룹삭제
			client.delete("group.deleteGroup",group_no);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateGroup(int groupInfo) {
		try {
			client.update("group.updateGroup",groupInfo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

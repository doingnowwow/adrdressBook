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

	public void insertGroup(String group_name) {
		try {
			client.insert("group.insertGroup",group_name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteGroup(int group_no) {
		try {
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

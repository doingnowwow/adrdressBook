package hs.addressbook.handler.database;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.h2.engine.Session;

import com.ibatis.sqlmap.client.SqlMapClient;

import hs.addressbook.data.UserVO;
import hs.addressbook.ibatis.config.SqlMapClientFactory;

public class UserDataHandler {

	private static final UserDataHandler instance = new UserDataHandler();

	private SqlMapClient client;

	public static UserDataHandler getInstance() {
		return instance;
	}

	public UserDataHandler() {
		client = SqlMapClientFactory.getInstance();
	}

	// 전체사용자조회
	public List<UserVO> selectAllUser() {
		List<UserVO> selectAllUser = null;

		try {
			selectAllUser = client.queryForList("user.selectAllUser");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return selectAllUser;
	}

	public List<UserVO> selectAllUser(String user) {
		List<UserVO> selectAllUser = null;

		try {
			selectAllUser = client.queryForList("user.selectAllUser", user);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return selectAllUser;
	}

	// 사용자 정보조회
	public UserVO userInfo(int ad_no) {
		UserVO userInfo = null;
		try {
			userInfo = (UserVO) client.queryForObject("user.userInfo");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userInfo;

	}

	// 사용자추가
	public void insertUser(UserVO userInfo) {
		try {
			client.insert("user.insertUser", userInfo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateUser(UserVO userInfo) {
		try {
			client.update("user.updateUser", userInfo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteUser(UserVO userInfo) {
		try {
			client.delete("user.deleteUser", userInfo);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<UserVO> selectUserListByGroup(int group_no) {
		
		List<UserVO> selectUserListByGroup = null;

		try {
			selectUserListByGroup = client.queryForList("user.selectUserListByGroup", group_no);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return selectUserListByGroup;
	}

}

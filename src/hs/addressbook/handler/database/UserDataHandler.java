package hs.addressbook.handler.database;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.h2.engine.Session;

import com.ibatis.sqlmap.client.SqlMapClient;

import hs.addressbook.data.GroupVO;
import hs.addressbook.data.MappingVO;
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

	/**
	 * 방금 추가한 사용자 번호 알기
	 * @return
	 */
	public int selectUserNum() {

		int ad_no = 0;
		try {
			ad_no = (int) client.queryForObject("user.selectUserNum");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ad_no;

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

	public void insertMappingUser(MappingVO mappingInfo) {
		try {
			client.insert("mapping.insertMapping", mappingInfo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 수정
	public void updateUser(UserVO userInfo) {
		try {
			client.update("user.updateUser", userInfo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 삭제
	public void deleteUser(int ad_no) {
		try {
			// mapping테이블에 사용자 삭제
			client.delete("mapping.deleteMappingUser", ad_no);
			System.out.println("---mapping테이블에서삭제됨--");
			// 사용자삭제
			client.delete("user.deleteUser", ad_no);
			System.out.println("---user테이블에서삭제됨--");
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

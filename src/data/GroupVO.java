package data;

import java.util.List;

public class GroupVO {

	private int group_no;
	private String group_name;
	private List<UserVO> memberList;
	
	

	public int getGroup_no() {
		return group_no;
	}

	public void setGroup_no(int group_no) {
		this.group_no = group_no;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public List<UserVO> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<UserVO> memberList) {
		this.memberList = memberList;
	}
	
	
}
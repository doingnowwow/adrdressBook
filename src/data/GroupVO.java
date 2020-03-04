package data;

import java.util.ArrayList;
import java.util.List;

public class GroupVO {

	private int group_no;
	private String group_name;
	private List<UserVO> memberList = new ArrayList<UserVO>();

	public GroupVO() {
	}
	
	public GroupVO(int groupNo, String groupName) {
		this.group_no = groupNo;
		this.group_name = groupName;
	}
	
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

	public void addUser(UserVO uer) {
		if (this.memberList != null) {
			this.memberList.add(uer);
		}
	}

	@Override
	public String toString() {
		return group_name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GroupVO) {
			GroupVO target = (GroupVO) obj;
			return target.getGroup_no() == this.group_no;
		}
		return false;
	}

}

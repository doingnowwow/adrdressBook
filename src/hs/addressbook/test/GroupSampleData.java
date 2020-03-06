package hs.addressbook.test;

import java.util.ArrayList;
import java.util.List;

import hs.addressbook.data.GroupVO;

public class GroupSampleData {
	
	
	public static void main(String[] args) {
		
		// String의 ArrayList 객체 생성
		ArrayList<GroupVO> groupList = new ArrayList<GroupVO>(); 

		
		GroupVO group = new GroupVO();		
		group.setGroup_no(1);
		group.setGroup_name("가족");
		groupList.add(group);
		
		
		
		group = new GroupVO();
		group.setGroup_no(2);
		group.setGroup_name("친구");
		groupList.add(group);
		
		group = new GroupVO();
		group.setGroup_no(3);
		group.setGroup_name("회사");
		groupList.add(group);
		
		group = new GroupVO();
		group.setGroup_no(4);
		group.setGroup_name("그룹없음");
		groupList.add(group);
		
		
		
		
		System.out.println(groupList.get(0));
		System.out.println(groupList.get(1));
		System.out.println(groupList.get(2));
		System.out.println(groupList.get(3));
	}
	
	
	
}

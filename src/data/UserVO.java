package data;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator.OfDouble;

import org.apache.poi.hssf.util.HSSFColor.GOLD;

import net.mbiz.edt.barcode.table.data.TableData;

public class UserVO implements TableData {

	private boolean isChecked = false;
	private int ad_no;
	private String ad_name;
	private String ad_hp;
	private String ad_mail;
	private String ad_com;
	private String ad_department;
	private String ad_postion;
	private String ad_memo;
	private String group_no;
	private ArrayList<String> groupNoList = new ArrayList<String>();

	public UserVO() {
	}

	public UserVO(String[] dataArray) {

		if (dataArray.length == 9) {
			this.ad_no = Integer.parseInt(dataArray[0]);
			this.ad_name = dataArray[1];
			this.ad_hp = dataArray[2];
			this.ad_mail = dataArray[3];
			this.ad_com = dataArray[4];
			this.ad_department = dataArray[5];
			this.ad_postion = dataArray[6];
			this.ad_memo = dataArray[7];
			setGroup_no(dataArray[8]);
		}
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public int getAd_no() {
		return ad_no;
	}

	public void setAd_no(int ad_no) {
		this.ad_no = ad_no;
	}

	public String getAd_name() {
		return ad_name;
	}

	public void setAd_name(String ad_name) {
		this.ad_name = ad_name;
	}

	public String getAd_hp() {
		return ad_hp;
	}

	public void setAd_hp(String ad_hp) {
		this.ad_hp = ad_hp;
	}

	public String getAd_mail() {
		return ad_mail;
	}

	public void setAd_mail(String ad_mail) {
		this.ad_mail = ad_mail;
	}

	public String getAd_com() {
		return ad_com;
	}

	public void setAd_com(String ad_com) {
		this.ad_com = ad_com;
	}

	public String getAd_department() {
		return ad_department;
	}

	public void setAd_department(String ad_department) {
		this.ad_department = ad_department;
	}

	public String getAd_postion() {
		return ad_postion;
	}

	public void setAd_postion(String ad_postion) {
		this.ad_postion = ad_postion;
	}

	public String getAd_memo() {
		return ad_memo;
	}

	public void setAd_memo(String ad_memo) {
		this.ad_memo = ad_memo;
	}

	public String getGroup_no() {
		return group_no;
	}

	public void setGroup_no(String group_no) {
		this.group_no = group_no;
		String[] groupNoArr = this.group_no.split(",");
		for (String groupNo : groupNoArr) {
			this.groupNoList.add(groupNo);
		}
	}

	/**
	 * 그룹 추가
	 * 
	 * @param group
	 */
	public void addGroup(GroupVO group) {
		for (String groupNo : groupNoList) {
			if (groupNo.equals(String.valueOf(group.getGroup_no()))) {
				return;
			}
		}

		this.groupNoList.add("" + group.getGroup_no());
	}

	/**
	 * 그룹 삭제
	 * 
	 * @param group
	 */
	public void deleteGroup(GroupVO group) {
		for (int i = this.groupNoList.size() - 1; i > -1; i--) {
			String groupNo = this.groupNoList.get(i);
			if (groupNo.equals(String.valueOf(group.getGroup_no()))) {
				this.groupNoList.remove(i);
				
			}
		}
		
		if(groupNoList.size()==0) {
			setGroup_no("0");
		}
	}

	public boolean hasGroup(GroupVO group) {
		for (String groupNo : groupNoList) {
			if (groupNo.equals(String.valueOf(group.getGroup_no()))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 전체 그룹 정보 출력
	 * 
	 * @return
	 */
	public String getGroupListAsStr() {
		String groupNo = "";
		for (String no : groupNoList) {
			groupNo += no + ",";
		}

		return groupNo.substring(0, groupNo.length() - 1);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserVO) {
			UserVO target = (UserVO) obj;
			return target.getAd_no() == this.ad_no;
		}
		return false;
	}

	@Override
	public String toString() {
		return "UserVO [isChecked=" + isChecked + ", ad_no=" + ad_no + ", ad_name=" + ad_name + ", ad_hp=" + ad_hp + ", ad_mail=" + ad_mail + ", ad_com=" + ad_com + ", ad_department=" + ad_department + ", ad_postion=" + ad_postion + ", ad_memo=" + ad_memo + ", group_no=" + group_no + "]";
	}

	@Override
	public Object getValueByColumIndex(int col) {
		switch (col) {
		case 0:
			return this.isChecked;
		case 1:
			return this.ad_no;
		case 2:
			return this.ad_name;
		case 3:
			return this.ad_hp;
		case 4:
			return this.ad_mail;
		case 5:
			return this.ad_com;
		case 6:
			return this.ad_department;
		case 7:
			return this.ad_postion;
		case 8:
			return this.ad_memo;

		}
		return "";
	}

	@Override
	public void setValueByColumIndex(int col, Object obj) {
		this.isChecked = (boolean) obj;
	}

}

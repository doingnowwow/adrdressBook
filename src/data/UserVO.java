package data;

public class UserVO {
	
	private int ad_no;
	private String ad_name;
	private String ad_hp;
	private String ad_mail;
	private String ad_com;
	private String ad_department;
	private String ad_postion;
	private String ad_memo;
	private String group_name;
	
	
	
	@Override
	public String toString() {
		return  ad_name;
	}
	public UserVO() {
		// TODO Auto-generated constructor stub
	}
	public UserVO(String[] dataArray) {
		
		if(dataArray.length==9) {
			this.ad_no = Integer.parseInt(dataArray[0]);
			this.ad_name = dataArray[1];     
			this.ad_hp = dataArray[2];       
			this.ad_mail= dataArray[3];     
			this.ad_com = dataArray[4];      
			this.ad_department =dataArray[5];
			this.ad_postion = dataArray[6];  
			this.ad_memo = dataArray[7];     
			this.group_name = dataArray[8];  
			
		}
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
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	
	
	

}

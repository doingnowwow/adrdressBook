package handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import data.GroupVO;
import data.UserVO;

public class FileHandler {

	private static final FileHandler instance = new FileHandler();

	// 새 엑셀 시트 생성
	private XSSFWorkbook workbook = new XSSFWorkbook();
	private ArrayList<GroupVO> groupList = new ArrayList<GroupVO>();
	private ArrayList<UserVO> userList = new ArrayList<UserVO>();

	private int groupIdx = 0;
	private int userIdx = 0;

	private FileHandler() {
		readGroup();
		readUser();
	}

	public static FileHandler getInstance() {
		return instance;
	}

	public ArrayList<GroupVO> getGroupList() {
		return groupList;
	}
	

	

	/**
	 * 엑셀 파일 읽어오는 메서드 1번째 시트 (그룹)
	 * 
	 * @return
	 */
	public ArrayList<GroupVO> readGroup() {

		try {
			// 엑셀파일
			File file = new File("D://test1.xlsx");

			// 엑셀 파일 오픈
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));

			Cell cell = null;

			// 첫번재 sheet 내용 읽기
			for (Row row : wb.getSheetAt(0)) {
				// 2번째 줄부터 읽을꺼임
				if (row.getRowNum() < 1) {
					continue;
				}

//	              두번째 셀이 비어있으면 for문을 멈춘다.
				if (row.getCell(1) == null) {
					// 4번째 셀 값을 변경
					break;
				}

				// 콘솔 출력
				GroupVO group = new GroupVO();

				if (row.getCell(0) != null) {

					group.setGroup_no((int) row.getCell(0).getNumericCellValue());
				}
				if (row.getCell(1) != null) {

					group.setGroup_name(row.getCell(1).getStringCellValue());
				}

				groupList.add(group);
			}

			this.groupIdx = this.groupList.get(this.groupList.size() - 1).getGroup_no();

		} catch (FileNotFoundException fe) {
			System.out.println("FileNotFoundException >> " + fe.toString());
		} catch (IOException ie) {
			System.out.println("IOException >> " + ie.toString());
		}
		return groupList;

	}

	/**
	 * 엑셀 파일 읽어오는 메서드 2번째 시트 (사용자)
	 * 
	 * @return
	 */
	public ArrayList<UserVO> readUser() {

		try {
			// 엑셀파일
			File file = new File("D://test1.xlsx");

			// 엑셀 파일 오픈
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));

			Cell cell = null;

			// 첫번재 sheet 내용 읽기
			for (Row row : wb.getSheetAt(1)) {
				// 2번째 줄부터 읽을꺼임
				if (row.getRowNum() < 1) {
					continue;
				}

//	              두번째 셀이 비어있으면 for문을 멈춘다.
				if (row.getCell(1) == null) {
					// 4번째 셀 값을 변경
					break;
				}

				// 콘솔 출력
				UserVO user = new UserVO();
				user.setAd_no((int) row.getCell(0).getNumericCellValue());
				user.setAd_name(row.getCell(1).getStringCellValue());

				if (row.getCell(2) != null) {
					user.setAd_hp(row.getCell(2).getStringCellValue());
				}
				if (row.getCell(3) != null) {
					user.setAd_mail(row.getCell(3).getStringCellValue());
				}
				if (row.getCell(4) != null) {
					user.setAd_com(row.getCell(4).getStringCellValue());
				}

				if (row.getCell(5) != null) {
					user.setAd_department(row.getCell(5).getStringCellValue());

				}
				if (row.getCell(6) != null) {
					user.setAd_postion(row.getCell(6).getStringCellValue());
				}
				if (row.getCell(7) != null) {
					user.setAd_memo(row.getCell(7).getStringCellValue());
				}
				if (row.getCell(8) != null) {
					user.setGroup_no((int) row.getCell(8).getNumericCellValue());

				}

				userList.add(user);

			}

			// 사용자 마지막 번호
			if (this.userList.size() == 0) {
				this.userIdx = 1;
			} else {

				this.userIdx = this.userList.get(this.userList.size() - 1).getAd_no();
			}

			System.out.println("userIdx = " + userIdx);

		} catch (FileNotFoundException fe) {
			System.out.println("FileNotFoundException >> " + fe.toString());
		} catch (IOException ie) {
			System.out.println("IOException >> " + ie.toString());
		}
		return userList;
	}

	/**
	 * 그룹을 엑셀로 작성하는 메서드
	 * 
	 * @param groupList
	 */
	public void wirteGroup() {

		// 새 시트(SXeet) 생성
		XSSFSheet sheet = workbook.createSheet("그룹");

		// 엑셀의 행은 0번부터 시작
		XSSFRow row = sheet.createRow(0);
		// 행의 셀은 0번부터 시작
		XSSFCell cell = row.createCell(0);
		// 생성한 셀에 데이터 삽입
		cell.setCellValue("그룹번호");

		cell = row.createCell(1);
		cell.setCellValue("그룹이름");

		// 그룹번호
		for (int i = 0; i < groupList.size(); i++) {

			row = sheet.createRow(i + 1);
			cell = row.createCell(0);
			// 그룹번호 자동으로 증가하게 만들어야함
			cell.setCellValue(groupList.get(i).getGroup_no());

			cell = row.createCell(1);
			cell.setCellValue(groupList.get(i).getGroup_name());
		}

		groupIdx++;

		// 파일만들기
		this.wirteFile(sheet);

	}

	/**
	 * 사용자를을 엑셀로 작성하는 메서드
	 * 
	 * @param groupList
	 */
	public void wirteUser() {

		// 새 시트(SXeet) 생성
		XSSFSheet sheet = workbook.createSheet("사용자");

		String[] header = { "번호", "이름", "핸드폰번호", "이메일", "회사", "부서", "직책", "메모", "그룹" };
		XSSFRow headerRow = sheet.createRow(0);

		for (int i = 0; i < header.length; i++) {
			XSSFCell cell = headerRow.createCell(i);
			cell.setCellValue(header[i]);
		}

		for (int i = 0; i < userList.size(); i++) {
			int idx = 0;
			XSSFRow row = sheet.createRow(i + 1);

			XSSFCell cell = row.createCell(idx++);
			cell.setCellValue(userList.get(i).getAd_no());

			cell = row.createCell(idx++);
			cell.setCellValue(userList.get(i).getAd_name());

			cell = row.createCell(idx++);
			cell.setCellValue(userList.get(i).getAd_hp());

			cell = row.createCell(idx++);
			cell.setCellValue(userList.get(i).getAd_mail());

			cell = row.createCell(idx++);
			cell.setCellValue(userList.get(i).getAd_com());

			cell = row.createCell(idx++);
			cell.setCellValue(userList.get(i).getAd_department());

			cell = row.createCell(idx++);
			cell.setCellValue(userList.get(i).getAd_postion());

			cell = row.createCell(idx++);
			cell.setCellValue(userList.get(i).getAd_memo());

			cell = row.createCell(idx++);
			cell.setCellValue(userList.get(i).getGroup_no());

		}

		// 파일만들기
		this.wirteFile(sheet);

	}

	/**
	 * 그룹쓰기, 사용자쓰기 메서드를 받아서 엑셀 파일 생성하는 부분
	 * 
	 * @param workbook
	 */
	public void wirteFile(XSSFSheet sheet) {

		try {
			FileOutputStream fileoutputstream = new FileOutputStream("D://test2.xlsx");
			FileOutputStream fileoutputstream2 = new FileOutputStream("D://test1.xlsx");

			workbook.write(fileoutputstream);
			workbook.write(fileoutputstream2);

			fileoutputstream.close();
			fileoutputstream2.close();
			System.out.println("엑셀파일생성성공");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("엑셀파일생성실패");
		}
	}

	/**
	 * 그룹에 있는 사용자 찾기
	 * 
	 * @param group
	 * @return
	 */
	public ArrayList<UserVO> searchUserListByGroup(GroupVO group) {
		ArrayList<UserVO> resList = new ArrayList<UserVO>();
		System.out.println("searchUserListByGroup=====>Strart");
		
		if (group != null) {
			for (int i = 0; i < this.userList.size(); i++) {
				UserVO user = this.userList.get(i);

				if (user.getGroup_no() == group.getGroup_no()) {
					resList.add(user);
					System.out.println(user.toString());
				}

			}
			System.out.println("searchUserListByGroup=====>End");
			return resList;
		}
		System.out.println("searchUserListByGroup=====>End");
		resList = this.userList;
		
		return resList;
	}

	/**
	 * 검색해서 해당하는 문자를 가지고 있는 사용자 리스트 보여주기
	 * 
	 * @param keword
	 * @return
	 */
	public ArrayList<UserVO> searchUserListBykeyword(String keyword) {
		
		System.out.println("검색결과가 넘어오나?====" + keyword);

		ArrayList<UserVO> resultList = new ArrayList<UserVO>();
		for (int i = 0; i < this.userList.size(); i++) {
			UserVO user = this.userList.get(i);
			if (user.getAd_name().contains(keyword)) {
				resultList.add(user);
				continue;
			}
			if (user.getAd_hp().contains(keyword)) {
				resultList.add(user);
				continue;
			}
			if (user.getAd_mail().contains(keyword)) {
				resultList.add(user);
				continue;
			}
			if (user.getAd_com().contains(keyword)) {
				resultList.add(user);
				continue;
			}
			if (user.getAd_department().contains(keyword)) {
				resultList.add(user);
				continue;
			}
			if (user.getAd_postion().contains(keyword)) {
				resultList.add(user);
				continue;
			}
			if (user.getAd_postion().contains(keyword)) {
				resultList.add(user);
				continue;
			}
			if (user.getAd_mail().contains(keyword)) {
				resultList.add(user);
				continue;
			}
		}

		return resultList;
	}

	/**
	 * 그룹 이름 바꾸기
	 * 
	 * @param group
	 */
	public void updateGroup(GroupVO group) {

		System.out.println("======update======");
		for (int i = 0; i < groupList.size(); i++) {
			int groupNo = groupList.get(i).getGroup_no();
			System.out.println("-----------------------?수정부분?--------------");
			if (group.getGroup_no() == groupNo) {
				groupList.get(i).setGroup_name(group.getGroup_name());
			}
		}

	}

	/**
	 * 사용자 정보 바꾸기
	 * 
	 * @param user
	 */
	public void updateUser(UserVO user) {
		System.out.println("=====updateUser start=========");
		System.out.println("user.toString" + user.toString());
		System.out.println();

		int userNo = user.getAd_no();
		System.out.println(user.getAd_no());

		for (int i = 0; i < userList.size(); i++) {

			System.out.println(user.getAd_no());

			if (userList.get(i).getAd_no() == userNo) {

				System.out.println("userList.get(i)=============");

				userList.get(i).setAd_name(user.getAd_name());

				if (user.getAd_hp() != null) {
					userList.get(i).setAd_hp(user.getAd_hp());
				}
				if (user.getAd_mail() != null) {
					userList.get(i).setAd_mail(user.getAd_mail());
				}
				if (user.getAd_com() != null) {
					userList.get(i).setAd_com(user.getAd_com());
				}
				if (user.getAd_department() != null) {
					userList.get(i).setAd_department(user.getAd_department());
				}
				if (user.getAd_postion() != null) {
					userList.get(i).setAd_postion(user.getAd_postion());
				}
				if (user.getAd_memo() != null) {
					userList.get(i).setAd_memo(user.getAd_memo());
				}
				if (user.getGroup_no() != 0) {
					userList.get(i).setGroup_no(user.getGroup_no());
				}
			}
		}
		System.out.println("=====updateUser end=========");

	}

	/**
	 * 사용자 추가하기
	 * 
	 * @param user
	 */
	public void addUser(UserVO user) {
		System.out.println("===주소록 user추가===");

		user.setAd_no(++this.userIdx);
		user.setAd_name(user.getAd_name());
		if (user.getAd_hp() != null) {
			user.setAd_hp(user.getAd_hp());
		}

		if (user.getAd_mail() != null) {
			user.setAd_mail(user.getAd_mail());
		}
		if (user.getAd_com() != null) {
			user.setAd_com(user.getAd_com());
		}
		if (user.getAd_department() != null) {
			user.setAd_department(user.getAd_department());
		}
		if (user.getAd_postion() != null) {
			user.setAd_postion(user.getAd_postion());
		}
		if (user.getAd_memo() != null) {
			user.setAd_memo(user.getAd_memo());
		}
		if (user.getGroup_no() != 0) {
			user.setGroup_no(user.getGroup_no());
		}
		this.userList.add(user);
		System.out.println("추가완료" + user.toString());
	}

	/**
	 * 그룹추가하기
	 * 
	 * @param group
	 */
	public void addGroup(GroupVO group) {
		System.out.println("===그룹추가====");
		group.setGroup_no(++this.groupIdx);
		group.setGroup_name(group.getGroup_name());
		System.out.println("groupInsertName = " + group.getGroup_name());
		this.groupList.add(group);
	}

	/**
	 * 그룹 삭제하기
	 * 
	 * @param group
	 */
	public void deleteGroup(GroupVO group) {
		System.out.println("===delete Group===");

//		for (int i = groupList.size() - 1; i >= 0; i--) {
//			int groupNo = groupList.get(i).getGroup_no();
//			if (group.getGroup_no() == groupNo) {
//				groupList.remove(i);
//			}
//		}

		System.out.println("===delete Group end=======" + group.getGroup_no());
		if (this.groupList.contains(group)) {
			this.groupList.remove(group);
		}
	}

	/**
	 * 주소록 사람 삭제하기
	 * 
	 * @param userList
	 */
	public void deleteUser(ArrayList<UserVO> userList) {
		System.out.println("===deleteUser====");

		for (int i = userList.size() - 1; i >= 0; i--) {
			UserVO userVO = userList.get(i);
			System.out.println("userVO = " + userVO.toString());
			if (this.userList.contains(userVO)) {
				this.userList.remove(userVO);
			}
		}

	}
}

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

	public FileHandler() {
	}

	/**
	 * 엑셀 파일 읽어오는 메서드 1번째 시트 (그룹)
	 * 
	 * @return
	 */
	public ArrayList<GroupVO> readGroup() {

		ArrayList<GroupVO> groupList = new ArrayList<GroupVO>();

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

				group.setGroup_no((int) row.getCell(0).getNumericCellValue());
				group.setGroup_name(row.getCell(1).getStringCellValue());

				groupList.add(group);
			}

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

		ArrayList<UserVO> userList = new ArrayList<UserVO>();

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
					user.setGroup_name(row.getCell(8).getStringCellValue());

				}

				userList.add(user);

			}
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
	public void wirteGroup(ArrayList<GroupVO> groupList) {

		// 새 엑셀 생성
		XSSFWorkbook workbook = new XSSFWorkbook();
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
		for (int i = 1; i < groupList.size(); i++) {

			row = sheet.createRow(i);
			cell = row.createCell(0);
			cell.setCellValue(groupList.get(i).getGroup_no());
		}

		// 그룹이름
		for (int i = 1; i < groupList.size(); i++) {
			row = sheet.createRow(1);
			cell = row.createCell(i);
			cell.setCellValue(groupList.get(i).getGroup_name());

		}

		try {
			FileOutputStream fileoutputstream = new FileOutputStream("D://test2.xlsx");
			workbook.write(fileoutputstream);
			fileoutputstream.close();
			System.out.println("엑셀파일생성성공");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("엑셀파일생성실패");
		}

	}
}

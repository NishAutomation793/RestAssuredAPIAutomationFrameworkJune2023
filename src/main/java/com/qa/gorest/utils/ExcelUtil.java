package com.qa.gorest.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.qa.gorest.constants.APIConstants;

public class ExcelUtil {

	private static final String SHEET_PATH = ".\\src\\test\\resources\\testdata\\CreateUserData.xlsx";
	private static Workbook book;
	private static Sheet sheet;

	public static Object[][] getSheetData(String sheetName) {
		Object[][] obj = null;

		try {
			FileInputStream fp = new FileInputStream(SHEET_PATH);

			book = WorkbookFactory.create(fp);
			sheet = book.getSheet(sheetName);

			obj = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

			for (int i = 0; i < sheet.getLastRowNum(); i++) {

				for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
					obj[i][j] = sheet.getRow(i + 1).getCell(j).toString(); // Here we are getting excel string that needs to be changed explicitly to java string

				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return obj;
	}

}

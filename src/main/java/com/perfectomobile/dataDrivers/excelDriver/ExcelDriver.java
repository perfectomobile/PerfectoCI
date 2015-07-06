package com.perfectomobile.dataDrivers.excelDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.perfectomobile.dataDrivers.*;


/**
* ExcelDriver Library.
* 
* <P>Running data-driven tests from Excel files.
*  
* @author Avner Gershtansky
* @version 1.0
*/

public class ExcelDriver {
	private String filePath = "";
	private String sheetName = "";
	private String testCycle = "";
	
	private int testCycleColumnNumber;
	
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;

	private CreationHelper createHelper;
	
	private static Lock lock = new Lock();
	
	// Open the Excel file in "path" and sets it as active workbook
	public void setWorkbook(String path) throws Exception{
		this.filePath = path;
		try{
			FileInputStream inputFile = new FileInputStream(this.filePath);
			this.workbook = new XSSFWorkbook(inputFile);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	

	// Save the active workbook to the same file,
	// with all the changes that were made.
	public void closeWorkbook() throws IOException{
			this.workbook.close();
	}
	
	// Writes the current worksheet to file
	private void flushWorkbook() throws InterruptedException{
		try{
			FileOutputStream file = new FileOutputStream(this.filePath);
			this.workbook.write(file);
			file.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	// Saves active workbook to different file at <filePath>.
	public void saveWorkbookAs(String filePath){
		try{
			FileOutputStream file = new FileOutputStream(filePath);
			this.workbook.write(file);
			this.workbook.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	// Sets the active sheet by name
	public void setSheet(String sheetName, boolean addIfNotExists) throws Exception{
		this.sheetName = WorkbookUtil.createSafeSheetName(sheetName);
		
		lock.lock();
		this.refreshSheet();
		
		XSSFSheet tempSheet = this.workbook.getSheet(this.sheetName);
		if(tempSheet == null){
			if(addIfNotExists){
				this.sheet = this.workbook.createSheet(this.sheetName);
				XSSFRow row = this.sheet.createRow(0);
				if(this.createHelper == null){
					createHelper = this.workbook.getCreationHelper();
				}
				Cell cell = row.createCell(0);
				cell.setCellValue(createHelper.createRichTextString("Test Parameters"));

				this.flushWorkbook();
				lock.unlock();
			}
			else{
				System.out.println("Sheet '" + this.sheetName + "' doesn't exists"
						+ " and addSheet flag is false");
				lock.unlock();
				throw new Exception();
			}
		}
		else{
			this.sheet = this.workbook.getSheet(this.sheetName);
			lock.unlock();
		}
	}
	
	// Reads the sheet from file, to update any change
	// which was made in other thread.
	// Mostly for addCellPass() and addCellFail() functions,
	// So they don't overwrite other cell names which were
	// added during the test
	private void refreshSheet() throws Exception{
		try{
			FileInputStream inputFile = new FileInputStream(this.filePath);
			this.workbook = new XSSFWorkbook(inputFile);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		this.sheet = this.workbook.getSheet(this.sheetName);
	}
	
	public void setTestCycle(String testCycle, boolean addIfNotExists) throws Exception{
		
		boolean columnFound = false;
		int rowSize = 0;
		this.testCycle = testCycle;
		
		lock.lock();
		refreshSheet();
		// If sheet is not empty
		if(this.sheet.getPhysicalNumberOfRows() != 0 ){
			
			XSSFRow row = this.sheet.getRow(0);
			rowSize = row.getPhysicalNumberOfCells();
			for(Cell cell : row){
				if (cell.getStringCellValue().equals(this.testCycle)){
					this.testCycleColumnNumber = cell.getColumnIndex();
					columnFound = true;
					lock.unlock();
					break;
				}
			}
		}
		if(!columnFound){
			if(addIfNotExists){
				XSSFRow row;
				Cell cell;
				if(rowSize == 0){
					row = this.sheet.createRow(0);
					cell = row.createCell(1);
					this.testCycleColumnNumber = 1;
				}
				else{
					cell = this.sheet.getRow(0).createCell(rowSize);
					this.testCycleColumnNumber = rowSize;
				}
				if(this.createHelper == null){
					createHelper = this.workbook.getCreationHelper();
				}
				
				cell.setCellValue(createHelper.createRichTextString(testCycle));
				this.flushWorkbook();
				lock.unlock();
			}
			else{
				lock.unlock();
				System.out.println("Column '" + this.testCycle + "' doesn't exists"
						+ " and addColumn flag is false");
				throw new Exception();
			}
		}
	}
	
	public void setResultByTestCycle(boolean isPass, String... params) throws Exception{
		int resultRow = -1;
		String stepParams = "";
		
		
		if(this.testCycle.equals("")){
			System.out.println("Test cycle is not set");
			throw new NoSuchFieldException();
		}
		for(String s : params){
			stepParams += s + ", ";
		}
		stepParams = stepParams.substring(0, stepParams.lastIndexOf(","));
		
		lock.lock();
		this.refreshSheet();
		int tempResultRow = findCellRowByValue(stepParams);
		
		// If step doesn't exsist in sheet
		if(tempResultRow == -1){
			resultRow = this.sheet.getPhysicalNumberOfRows();
			XSSFRow row = this.sheet.createRow(resultRow);
			if(this.createHelper == null){
				createHelper = this.workbook.getCreationHelper();
			}
			Cell cell = row.createCell(0);
			cell.setCellValue(createHelper.createRichTextString(stepParams));
			
			this.flushWorkbook();
		}
		else{
			resultRow = tempResultRow;
		}
		
		if(isPass){
			this.setPassByCell(resultRow, this.testCycleColumnNumber);
		}
		else{
			this.setFailByCell(resultRow, this.testCycleColumnNumber);
		}
		this.flushWorkbook();
		lock.unlock();
	}
	
	private int findCellRowByValue(String value){
		int rowNum;
		
		for(Row row : this.sheet){
			for(Cell cell : row){
				if(cell.getStringCellValue().equals(value)){
					rowNum = cell.getRowIndex();
					return rowNum;
				}
			}
		}
		return -1;
	}
	
	private int findCellColumnByValue(String value){
		int colNum;
		
		for(Row row : this.sheet){
			for(Cell cell : row){
				if(cell.getStringCellValue().equals(value)){
					colNum = cell.getColumnIndex();
					return colNum;
				}
			}
		}
		return -1;
	}
	
	private int[] findCellByValue(String value){
		int[] cellCoordinates = new int[2];
		
		for(Row row : this.sheet){
			for(Cell cell : row){
				if(cell.getStringCellValue().equals(value)){
					cellCoordinates[0] = cell.getRowIndex();
					cellCoordinates[1] = cell.getColumnIndex();
					return cellCoordinates;
				}
			}
		}
		return null;
		
	}
	// Sets cell(row, col) with "FAIL" status
	public void setFailByCell(int row, int col){
		setCellAsString(row, col, "FAIL");
		setCellColor(row, col, CellColors.RED);
	}
	
	// Sets cell(row, col) with "PASS" status
	public void setPassByCell(int row, int col){
		setCellAsString(row, col, "PASS");
		setCellColor(row, col, CellColors.GREEN);
	}
	
	// Resets cell(row, col) including text and background color
	public void clearCell(int row, int col){
		setCellAsString(row, col, "");
		setCellColor(row, col, CellColors.WHITE);
	}
	
	// Sets the last cell in the row to "FAIL" status
	private void addCellFail(int row, String cellName) throws Exception{
		this.refreshSheet();
		int newCellIndex = this.sheet.getRow(0).getLastCellNum();
		setCellAsString(0, newCellIndex, cellName);
		setCellAsString(row, newCellIndex, "FAIL");
		setCellColor(row, newCellIndex, CellColors.RED);
	}
	
	//Sets the last cell in the row to "PASS" status
	private void addCellPass(int row, String cellName) throws Exception{
		this.refreshSheet();
		int newCellIndex = this.sheet.getRow(0).getLastCellNum();
		setCellAsString(0, newCellIndex, cellName);
		setCellAsString(row, newCellIndex, "PASS");
		setCellColor(row, newCellIndex, CellColors.GREEN);
	}
	
	private void clearLastCell(int row){
		int lastCellIndex = this.sheet.getRow(row).getLastCellNum();
		setCellAsString(row, lastCellIndex, "");
		setCellColor(row, lastCellIndex, CellColors.WHITE);
	}
	
	// Sets the cell with column name = "Result" to "PASS"
	// the column name IS NOT case sensitive
	// The boolean addColumn is used in case there is no "Result" column,
	// then it will add the column if addColumn == true, or will do nothing
	// if addColumn == false
	public void setResultPass(int row, boolean addColumn) throws Exception{
		int col = -1;
		ArrayList<String> arr = this.getRow(0);
		for(String s : arr){
			if(s.toLowerCase().equals("result")){
				col = arr.indexOf(s);
				this.setPassByCell(row, col);
				return;
			}
		}
		if(col == -1 && addColumn == true){
			this.addCellPass(row, "Result");
		}
	}
	
	// Sets the cell with column name = "Result" to "FAIL"
	// the column name IS NOT case sensitive
	// The boolean addColumn is used in case there is no "Result" column,
	// then it will add the column if addColumn == true, or will do nothing
	// if addColumn == false
	public void setResultFail(int row, boolean addColumn) throws Exception{
		int col = -1;
		ArrayList<String> arr = this.getRow(0);
		for(String s : arr){
			if(s.toLowerCase().equals("result")){
				col = arr.indexOf(s);
				this.setFailByCell(row, col);
				return;
			}
		}
		if(col == -1 && addColumn == true){
			this.addCellFail(row, "Result");
		}
	}
	
	// Sets the cell with column name = <deviceModel> to "PASS"
	// the column name IS NOT case sensitive
	// The boolean addColumn is used in case there is no "Result" column,
	// then it will add the column if addColumn == true, or will do nothing
	// if addColumn == false
	public void setDevicePass(int row, String deviceModel, boolean addColumn) throws Exception{
		int col = -1;
		ArrayList<String> arr = this.getRow(0);
		for(String s : arr){
			if(s.toLowerCase().equals(deviceModel.toLowerCase())){
				col = arr.indexOf(s);
				this.setPassByCell(row, col);
				this.flushWorkbook();
				return;
			}
		}
		if(col == -1 && addColumn == true){
			this.addCellPass(row, deviceModel);
		}
		this.flushWorkbook();
	}
	
	// Sets the cell with column name = <deviceModel> to "FAIL"
	// the column name IS NOT case sensitive
	// The boolean addColumn is used in case there is no "Result" column,
	// then it will add the column if addColumn == true, or will do nothing
	// if addColumn == false
	public void setDeviceFail(int row, String deviceModel, boolean addColumn) throws Exception{
		int col = -1;
		ArrayList<String> arr = this.getRow(0);
		for(String s : arr){
			if(s.toLowerCase().equals(deviceModel)){
				col = arr.indexOf(s);
				this.setFailByCell(row, col);
				return;
			}
		}
		if(col == -1 && addColumn == true){
			this.addCellFail(row, deviceModel);
		}
		this.flushWorkbook();
	}
	
	// Returns an array of strings, by row number.
	// First row is 0
	public ArrayList<String> getRow(int rowNum){
		ArrayList<String> row = new ArrayList<String>();
		for(Cell cell : this.sheet.getRow(rowNum)){
			row.add(cell.getStringCellValue());
		}
		return row;
	}
	
	// Returns a 2-dim array of strings,
	// with all the data from the active sheet.
	// This function can be used as is, for the TestNG data provider.
	// Size of the row is based on the header row.
	
	public Object[][] getData(int numOfCols){
		int rowsCount = this.sheet.getLastRowNum();
		//int colsCount = this.sheet.getRow(0).getLastCellNum();
		
		// Remove empty lines (false POI result)
		// If column 1-10 are empty, row is considered empty
		boolean emptyFlag = true;
		for(int i = rowsCount; i>1; i--){
			for(int j = 0; j<10; j++){
				if(this.sheet.getRow(i).getCell(j) != null){
					emptyFlag = false;
				}
			}
			if(emptyFlag == true){
				rowsCount -= 1;
				emptyFlag = true;
			}
		}
		
		String[][] data = new String[rowsCount][numOfCols];
		for(int i = 1; i <= rowsCount; i++){
			for(int j = 0; j < numOfCols; j++){
				//String val = this.sheet.getRow(i).getCell(j).getStringCellValue();
				if(this.sheet.getRow(i).getCell(j) != null){
					
					//String val = this.sheet.getRow(i).getCell(j).getStringCellValue();
					String val = getCellAsString(i,j);
					data[i-1][j] = val;
				}
				else{
					data[i-1][j] = "";
				}
				
				//data[i-1][j] = (val != null) ? val : "";
			}
		}
		
		/*
		for(int i = 1; i <= rowsCount; i++){
			ArrayList<String> arr = getRow(i);

			//data[i-1][0] = Integer.toString(i);
			for(int j = 0; j < arr.size() && j < numOfCols; j++){
				data[i-1][j] = arr.get(j);
			}
		}
		*/
		
		return data;
	}
	// Returns a 2-dim array of strings,
	// with all the data from the active sheet.
	// The first node in every line is the line number.
	// This function can be used as is, for the TestNG data provider.
	// Size of the row is based on the header row.
	
	public Object[][] getDataWithIndex(int numOfCols){
		int rowsCount = this.sheet.getLastRowNum();
		//int colsCount = this.sheet.getRow(0).getLastCellNum();
		
		// Remove empty lines (false POI result)
		// If column 1-3 are empty, row is considered empty
		for(int i = rowsCount; i>1; i--){
			if (this.sheet.getRow(i).getCell(0) == null && 
				this.sheet.getRow(i).getCell(1) == null && 
				this.sheet.getRow(i).getCell(2) == null){
				rowsCount -= 1;
			}
			else{
				break;
			}
		}
		
		String[][] data = new String[rowsCount][numOfCols + 1];
		for(int i = 1; i <= rowsCount; i++){
			data[i-1][0] = Integer.toString(i);
			for(int j = 0; j < numOfCols; j++){
				if(this.sheet.getRow(i).getCell(j) != null){
					//String val = this.sheet.getRow(i).getCell(j).getStringCellValue();
					String val = getCellAsString(i,j);
					data[i-1][j+1] = val;
				}
				else{
					data[i-1][j+1] = "";
				}
			}
		}
		
		/*
		String[][] data = new String[rowsCount][numOfCols + 1];
		for(int i = 1; i <= rowsCount; i++){
			ArrayList<String> arr = getRow(i);

			data[i-1][0] = Integer.toString(i);
			for(int j = 0; j < arr.size() && j < numOfCols; j++){
				data[i-1][j+1] = arr.get(j);
			}
		}
		*/
		
		return data;
	}
	
	// Returns a string from cell (row,col).
	// Top left corner is (0,0)
	public String getCellAsString(int row, int col){
		Cell cell = this.sheet.getRow(row).getCell(col);

		if (cell!=null) {
		    switch (cell.getCellType()) {
		        case Cell.CELL_TYPE_BOOLEAN:
		        	return String.valueOf(cell.getBooleanCellValue());
		            //System.out.println(cell.getBooleanCellValue());
		        case Cell.CELL_TYPE_NUMERIC:
		        	return String.valueOf(cell.getNumericCellValue());
		            //System.out.println(cell.getNumericCellValue());
		        case Cell.CELL_TYPE_STRING:
		        	return cell.getStringCellValue();
		            //System.out.println(cell.getStringCellValue());
		        case Cell.CELL_TYPE_BLANK:
		            return "";
		        case Cell.CELL_TYPE_ERROR:
		        	return String.valueOf(cell.getErrorCellValue());
		            //System.out.println(cell.getErrorCellValue());
		        case Cell.CELL_TYPE_FORMULA: 
		            break;
		    }
		}
		return null;
	}
	
	// Returns a string from cell by row number and column name.
	public String getCellByColNameAsString(int row, String colName){
		int col = this.getColIndexByName(colName);
		return this.sheet.getRow(row).getCell(col).getStringCellValue();
	}
	
	// Returns the index of the column, with the header = colName
	public int getColIndexByName(String colName){
		ArrayList<String> colNames = this.getRow(0);
		return(colNames.indexOf(colName));
	}
	
	public void setCellByColName(int row, String colName, String cellVal){
		if(this.createHelper == null){
			createHelper = this.workbook.getCreationHelper();
		}
		int col = this.getColIndexByName(colName);
		Cell cell = this.sheet.getRow(row).createCell(col);
		cell.setCellValue(createHelper.createRichTextString(cellVal));
	}
	public void setCellAsString(int row, int col, String cellVal){
		if(this.createHelper == null){
			createHelper = this.workbook.getCreationHelper();
		}
		Cell cell = this.sheet.getRow(row).createCell(col);
		cell.setCellValue(createHelper.createRichTextString(cellVal));
	}
	
	public void setCellColor(int row, int col, CellColors color){
		 	CellStyle style = this.workbook.createCellStyle();
		 	switch(color){
			 	case WHITE:
			 		style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			 		break;
			 	case YELLOW:
			 		style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			 		break;
			 	case RED:
			 		style.setFillForegroundColor(IndexedColors.RED.getIndex());
			 		break;
			 	case GREEN:
			 		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			 		break;
		 	}
		    
		    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		    this.sheet.getRow(row).getCell(col).setCellStyle(style);
		    //Cell cell = this.sheet.getRow(row).createCell(col);
		    //cell.setCellStyle(style);
	}
	public enum CellColors{
		WHITE, YELLOW, RED, GREEN
	}
	
	public void setAutoSize() throws Exception{

		if(this.sheet.getPhysicalNumberOfRows() != 0){
			lock.lock();
			this.refreshSheet();
			Row row = this.sheet.getRow(this.sheet.getFirstRowNum());
			for(Cell cell : row){
				this.sheet.autoSizeColumn(cell.getColumnIndex());
			}
			this.flushWorkbook();
			lock.unlock();
		}
	}
}

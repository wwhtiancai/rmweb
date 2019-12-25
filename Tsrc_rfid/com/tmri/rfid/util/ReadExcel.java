package com.tmri.rfid.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReadExcel {
	
	/**
	 * 读取一个sheet的数据
	 * @param st
	 * @param result
	 * @param ignoreRows
	 * @param rowSize
	 * @return
	 */
	public static Map<String, Object> readSheetByHSSF(HSSFSheet st, List<String[]> result, int ignoreRows,int rowSize){
		Map<String, Object> map = new HashMap<String, Object>();
		
		Cell cell = null;
		// 从第ignoreRows行开始取
		for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
			HSSFRow row = st.getRow(rowIndex);
			if (row == null) {
				continue;
			}
			int tempRowSize = row.getLastCellNum() + 1;
			if (tempRowSize > rowSize) {
				rowSize = tempRowSize;
			}
			String[] values = new String[rowSize];
			Arrays.fill(values, "");
			boolean hasValue = false;
			for (short columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) {
				String value = "";
                cell = (Cell) row.getCell(columnIndex);
				if (cell != null) {
					// 注意：一定要设成这个，否则可能会出现乱码
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;
					case HSSFCell.CELL_TYPE_NUMERIC:
						if (HSSFDateUtil.isCellDateFormatted((HSSFCell) cell)                                ) {
							Date date = cell.getDateCellValue();
							if (date != null) {
								value = new SimpleDateFormat("yyyy-MM-dd")
										.format(date);
							} else {
								value = "";
							}
						} else {
							value = new DecimalFormat("0").format(cell
									.getNumericCellValue());
						}
						break;
					case HSSFCell.CELL_TYPE_FORMULA:
						// 导入时如果为公式生成的数据则无值
						if (!cell.getStringCellValue().equals("")) {
							value = cell.getStringCellValue();
						} else {
							value = cell.getNumericCellValue() + "";
						}
						break;
					case HSSFCell.CELL_TYPE_BLANK:
						break;
					case HSSFCell.CELL_TYPE_ERROR:
						value = "";
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						value = (cell.getBooleanCellValue() == true ? "Y"
								: "N");
						break;
					default:
						value = "";
					}
				}
				if (columnIndex == 0 && value.trim().equals("")) {
					break;
				}
				values[columnIndex] = rightTrim(value);
				hasValue = true;
			}

			if (hasValue) {
				result.add(values);
			}
		}
		map.put("result", result);
		map.put("rowSize", rowSize);
		
		return map;
	}

	/**
	 * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
	 * 
	 * @param file
	 *            读取数据的源Excel
	 * @param ignoreRows
	 *            读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
	 * @param readOneSheet
	 *            是否只读一个sheet
	 * @param sheetNum 只读一个时读第几个sheet          
	 * @return 读出的Excel中数据的内容
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String[][] getArrByHSSF(File file, int ignoreRows, boolean readOneSheet, int sheetNum)
			throws FileNotFoundException, IOException {
		List<String[]> result = new ArrayList<String[]>();
		int rowSize = 0;
		
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
				file));
		// 打开HSSFWorkbook
		POIFSFileSystem fs = new POIFSFileSystem(in);
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		if(readOneSheet){//只读第一个sheet
			HSSFSheet st = wb.getSheetAt(sheetNum);
			if(st != null){
				Map<String, Object> map = readSheetByHSSF(st, result, ignoreRows, rowSize);
				result = (List<String[]>) map.get("result");
			}
		}else{
			for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
				HSSFSheet st = wb.getSheetAt(sheetIndex);
				Map<String, Object> map = readSheetByHSSF(st, result, ignoreRows, rowSize);
				rowSize = (Integer) map.get("rowSize");
				result = (List<String[]>) map.get("result");
			}
		}
		
		in.close();
		String[][] returnArray = new String[result.size()][rowSize];
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i] = (String[]) result.get(i);
		}
		return returnArray;
	}
	
	/**
	 * 
	 * @param file
	 * @param ignoreRows
	 * @param sheetName
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String[][] getArrByHSSF(File file, int ignoreRows, String sheetName)
			throws FileNotFoundException, IOException {
		List<String[]> result = new ArrayList<String[]>();
		int rowSize = 0;
		
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
				file));
		// 打开HSSFWorkbook
		POIFSFileSystem fs = new POIFSFileSystem(in);
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFSheet st = wb.getSheet(sheetName);
		if(st != null){
			Map<String, Object> map = readSheetByHSSF(st, result, ignoreRows, rowSize);
			result = (List<String[]>) map.get("result");
		}
		
		in.close();
		String[][] returnArray = new String[result.size()][rowSize];
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i] = (String[]) result.get(i);
		}
		return returnArray;
	}
	
	/**
	 * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
	 * 
	 * @param file
	 *            读取数据的源Excel
	 * @param ignoreRows
	 *            读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
	 * @param readOneSheet
	 *            是否只读一个sheet
	 * @return 读出的Excel中数据的内容
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String[][] getArrByHSSF(File file, int ignoreRows, boolean readOneSheet)
			throws FileNotFoundException, IOException {
		return getArrByHSSF(file, ignoreRows, readOneSheet, 0);
	}
	
	/**
	 * 读取一个sheet的数据
	 * @param st
	 * @param result
	 * @param ignoreRows
	 * @param rowSize
	 * @return
	 */
	public static Map<String, Object> readSheet(Sheet st, List<String[]> result, int ignoreRows,int rowSize){
		Map<String, Object> map = new HashMap<String, Object>();
		
		Cell cell = null;
		// 从第ignoreRows行开始取
		for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
			Row row = st.getRow(rowIndex);
			if (row == null) {
				continue;
			}
			int tempRowSize = row.getLastCellNum() + 1;
			if (tempRowSize > rowSize) {
				rowSize = tempRowSize;
			}
			String[] values = new String[rowSize];
			Arrays.fill(values, "");
			boolean hasValue = false;
			for (short columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) {
				String value = "";
				cell = row.getCell(columnIndex);
				if (cell != null) {
					// 注意：一定要设成这个，否则可能会出现乱码
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_NUMERIC:
						//if (HSSFDateUtil.isCellDateFormatted(cell)) {
						Date date = cell.getDateCellValue();
						if (date != null) {
							value = new SimpleDateFormat("yyyy-MM-dd")
									.format(date);
						} else {
							value = new DecimalFormat("0").format(cell
									.getNumericCellValue());
						}
						break;
					case Cell.CELL_TYPE_FORMULA:
						// 导入时如果为公式生成的数据则无值
						if (!cell.getStringCellValue().equals("")) {
							value = cell.getStringCellValue();
						} else {
							value = cell.getNumericCellValue() + "";
						}
						break;
					case Cell.CELL_TYPE_BLANK:
						break;
					case Cell.CELL_TYPE_ERROR:
						value = "";
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						value = (cell.getBooleanCellValue() == true ? "Y"
								: "N");
						break;
					default:
						value = "";
					}
				}
				if (columnIndex == 0 && value.trim().equals("")) {
					break;
				}
				values[columnIndex] = rightTrim(value);
				hasValue = true;
			}

			if (hasValue) {
				result.add(values);
			}
		}
		map.put("result", result);
		map.put("rowSize", rowSize);
		
		return map;
	}
	
	public static String[][] getArrByXSSF(File file, int ignoreRows, String sheetName) throws FileNotFoundException, IOException, InvalidFormatException{
		List<String[]> result = new ArrayList<String[]>();
		int rowSize = 0;
		
		InputStream ins = null;     
        Workbook wb = null;     
            ins=new FileInputStream(file);   
		wb = WorkbookFactory.create(ins);     
        ins.close();  
        Sheet st = wb.getSheet(sheetName);
		if(st != null){
			Map<String, Object> map = readSheet(st, result, ignoreRows, rowSize);
			result = (List<String[]>) map.get("result");
		}
		String[][] returnArray = new String[result.size()][rowSize];
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i] = (String[]) result.get(i);
		}
        
		return returnArray;
	}
	
	public static String[][] getArrByXSSF(File file, int ignoreRows, boolean readOneSheet, int sheetNum) throws FileNotFoundException, IOException, InvalidFormatException{
		List<String[]> result = new ArrayList<String[]>();
		int rowSize = 0;
		
		InputStream ins = null;     
        Workbook wb = null;     
            ins=new FileInputStream(file);   
		wb = WorkbookFactory.create(ins);     
        ins.close();  
        
		if(readOneSheet){//只读第一个sheet
			Sheet st = wb.getSheetAt(sheetNum);
			if(st != null){
				Map<String, Object> map = readSheet(st, result, ignoreRows, rowSize);
				result = (List<String[]>) map.get("result");
			}else{
				
			}
		}else{
			for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
				Sheet st = wb.getSheetAt(sheetIndex);
				Map<String, Object> map = readSheet(st, result, ignoreRows, rowSize);
				rowSize = (Integer) map.get("rowSize");
				result = (List<String[]>) map.get("result");
			}
		}
		
		String[][] returnArray = new String[result.size()][rowSize];
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i] = (String[]) result.get(i);
		}
		return returnArray;
	}
	
	public static String[][] getArrByXSSF(File file, int ignoreRows, boolean readOneSheet) throws FileNotFoundException, IOException, InvalidFormatException{
		return getArrByXSSF(file, ignoreRows, readOneSheet, 0);
	}
	
	
	/**
	 * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
	 * 
	 * @param file
	 *            读取数据的源Excel
	 * @param ignoreRows
	 *            读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
	 * @param readOneSheet 是否只读一个sheet
	 * @return 读出的Excel中数据的内容
	 * @throws Exception 
	 */
	public static String[][] getData(File file, int ignoreRows, boolean readOneSheet)
			throws Exception {
		return getData(file, ignoreRows, readOneSheet, 0);
	}
	
	
	/**
	 * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
	 * 
	 * @param file
	 *            读取数据的源Excel
	 * @param ignoreRows
	 *            读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
	 * @param readOneSheet 是否只读一个sheet
	 * @param sheetNum 读第几个sheet   
	 * @return 读出的Excel中数据的内容
	 * @throws Exception 
	 */
	public static String[][] getData(File file, int ignoreRows, boolean readOneSheet, int sheetNum)
			throws Exception {
		String name = file.getName();
		if(name.endsWith("xlsx")){
			return getArrByXSSF(file, ignoreRows, readOneSheet, sheetNum);
		}else if(name.endsWith("xls")){
			return getArrByHSSF(file, ignoreRows, readOneSheet, sheetNum);
		}else{
			throw new Exception("格式不是xls或xlsx");
		}
	}
	
	/**
	 * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
	 * 
	 * @param file
	 *            读取数据的源Excel
	 * @param ignoreRows
	 *            读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
	 * @param readOneSheet 是否只读一个sheet
	 * @param sheetName 读取sheet名   
	 * @return 读出的Excel中数据的内容
	 * @throws Exception 
	 */
	public static String[][] getData(File file, int ignoreRows, boolean readOneSheet, String sheetName)
			throws Exception {
		String name = file.getName();
		if(name.endsWith("xlsx")){
			return getArrByXSSF(file, ignoreRows, sheetName);
		}else if(name.endsWith("xls")){
			return getArrByHSSF(file, ignoreRows, sheetName);
		}else{
			throw new Exception("格式不是xls或xlsx");
		}
	}
	
	/**
	 * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
	 * 
	 * @param file
	 *            读取数据的源Excel
	 * @param ignoreRows
	 *            读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
	 * @return 读出的Excel中数据的内容
	 * @throws Exception 
	 */
	public static String[][] getData(File file, int ignoreRows)
			throws Exception {
		return getData(file, ignoreRows, false);
	}
	
	/**
	 * 去掉字符串右边的空格
	 * 
	 * @param str
	 *            要处理的字符串
	 * @return 处理后的字符串
	 */
	public static String rightTrim(String str) {
		if (str == null) {
			return "";
		}
		int length = str.length();
		for (int i = length - 1; i >= 0; i--) {
			if (str.charAt(i) != 0x20) {
				break;
			}
			length--;
		}
		return str.substring(0, length);
	}
}

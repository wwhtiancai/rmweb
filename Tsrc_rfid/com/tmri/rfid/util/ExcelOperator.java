package com.tmri.rfid.util;

import org.apache.poi.hssf.usermodel.*;

import com.tmri.rfid.bean.ExcelModel;

import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.math.BigDecimal;
import java.io.OutputStream;

public class ExcelOperator {

	/**
	 * 将数据信息写入到Excel表文件，采取自建输出流的方式。
	 * 
	 * @param excel
	 *            ExcelModel Excel表的模型对象
	 * @throws Exception
	 */
	public void WriteExcel(ExcelModel excel) throws Exception {

		try {

			String file = excel.getPath();

			// 新建一输出文件流
			FileOutputStream fOut = new FileOutputStream(file);
			BufferedOutputStream bf = new BufferedOutputStream(fOut);

			HSSFWorkbook workbook = this.getInitWorkbook(excel);

			// 把相应的Excel 工作簿存盘
			workbook.write(fOut);
			fOut.flush();
			bf.flush();
			// 操作结束，关闭文件
			bf.close();
			fOut.close();
			// System.out.println("Done!");
		} catch (Exception e) {
			// System.out.print("Failed!");
			throw new Exception(e.getMessage());
		}

	}

	/**
	 * 将数据信息写入到Excel表文件 ，采取传入输出流的方式。
	 * 
	 * @param excel
	 *            Excel表的模型对象
	 * @param out
	 *            OutputStream 输出流
	 * @throws Exception
	 */
	public void WriteExcel(ExcelModel excel, OutputStream out) throws Exception {
		try {
			HSSFWorkbook workbook = this.getInitWorkbook(excel);
			workbook.write(out);
			out.close();
			// System.out.println("Done!");
		} catch (Exception e) {
			// System.out.println("Failed!");
			throw new Exception(e.getMessage());
		}

	}

	/**
	 * 取得填充了数据的工作簿
	 * 
	 * @param excel
	 *            ExcelModel Excel表的模型对象
	 * @return HSSFWorkbook 工作簿对象
	 */
	private HSSFWorkbook getInitWorkbook(ExcelModel excel) {

		// 创建新的Excel 工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		HashMap map = excel.getDataMap();
		Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = iterator.next();
			//System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
			String sheetName = entry.getKey();
			ArrayList dataArr = (ArrayList) entry.getValue();

			// 在Excel工作簿中建一工作表
			HSSFSheet sheet = null;
			sheet = workbook.createSheet(sheetName);
			
			ArrayList cdata = dataArr;
			for (int i = 0; i < cdata.size(); i++) {
				// 从第二行开始
				HSSFRow row1 = sheet.createRow(i);
				ArrayList rdata = (ArrayList) cdata.get(i);
				// 打印一行数据
				for (int j = 0; j < rdata.size(); j++) {

					HSSFCell cell = row1.createCell((short) j);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					// 设置字符编码方式
					//cell.setEncoding((short) 1);

					Object o = rdata.get(j);

					// 造型,使写入到表中的数值型对象恢复为数值型，
					// 这样就可以进行运算了
					if (o instanceof BigDecimal) {
						BigDecimal b = (BigDecimal) o;
						cell.setCellValue(b.doubleValue());
					} else if (o instanceof Integer) {
						Integer it = (Integer) o;
						cell.setCellValue(it.intValue());

					} else if (o instanceof Long) {
						Long l = (Long) o;
						cell.setCellValue(l.intValue());

					} else if (o instanceof Double) {
						Double d = (Double) o;
						cell.setCellValue(d.doubleValue());
					} else if (o instanceof Float) {
						Float f = (Float) o;
						cell.setCellValue(f.floatValue());
					} else {
						cell.setCellValue((o !=null)? o+"":"");
					}

				}

			}
		}

		/*// 设置表头字体
		HSSFFont font_h = workbook.createFont();
		font_h.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		// 设置格式
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font_h);*/

		return workbook;
	}

	/**
	 * Just to test
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {

		ArrayList data = new ArrayList();
		ArrayList header = new ArrayList();
		header.add("学号");
		header.add("姓名");
		header.add("成绩");
		for (int i = 0; i < 3; i++) {

			ArrayList data1 = new ArrayList();

			data1.add((i + 1) + "");
			data1.add("Name" + (i + 1));
			data1.add("" + (80 + i));
			data.add(data1);
		}
		ExcelModel model = new ExcelModel();
		model.setPath("E:/test.xls");
		//model.setHeader(header);
		//model.setData(data);
		ExcelOperator eo = new ExcelOperator();
		try {
			eo.WriteExcel(model);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
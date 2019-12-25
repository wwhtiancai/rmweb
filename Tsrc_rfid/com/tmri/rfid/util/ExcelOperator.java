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
	 * ��������Ϣд�뵽Excel���ļ�����ȡ�Խ�������ķ�ʽ��
	 * 
	 * @param excel
	 *            ExcelModel Excel���ģ�Ͷ���
	 * @throws Exception
	 */
	public void WriteExcel(ExcelModel excel) throws Exception {

		try {

			String file = excel.getPath();

			// �½�һ����ļ���
			FileOutputStream fOut = new FileOutputStream(file);
			BufferedOutputStream bf = new BufferedOutputStream(fOut);

			HSSFWorkbook workbook = this.getInitWorkbook(excel);

			// ����Ӧ��Excel ����������
			workbook.write(fOut);
			fOut.flush();
			bf.flush();
			// �����������ر��ļ�
			bf.close();
			fOut.close();
			// System.out.println("Done!");
		} catch (Exception e) {
			// System.out.print("Failed!");
			throw new Exception(e.getMessage());
		}

	}

	/**
	 * ��������Ϣд�뵽Excel���ļ� ����ȡ����������ķ�ʽ��
	 * 
	 * @param excel
	 *            Excel���ģ�Ͷ���
	 * @param out
	 *            OutputStream �����
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
	 * ȡ����������ݵĹ�����
	 * 
	 * @param excel
	 *            ExcelModel Excel���ģ�Ͷ���
	 * @return HSSFWorkbook ����������
	 */
	private HSSFWorkbook getInitWorkbook(ExcelModel excel) {

		// �����µ�Excel ������
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		HashMap map = excel.getDataMap();
		Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = iterator.next();
			//System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
			String sheetName = entry.getKey();
			ArrayList dataArr = (ArrayList) entry.getValue();

			// ��Excel�������н�һ������
			HSSFSheet sheet = null;
			sheet = workbook.createSheet(sheetName);
			
			ArrayList cdata = dataArr;
			for (int i = 0; i < cdata.size(); i++) {
				// �ӵڶ��п�ʼ
				HSSFRow row1 = sheet.createRow(i);
				ArrayList rdata = (ArrayList) cdata.get(i);
				// ��ӡһ������
				for (int j = 0; j < rdata.size(); j++) {

					HSSFCell cell = row1.createCell((short) j);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					// �����ַ����뷽ʽ
					//cell.setEncoding((short) 1);

					Object o = rdata.get(j);

					// ����,ʹд�뵽���е���ֵ�Ͷ���ָ�Ϊ��ֵ�ͣ�
					// �����Ϳ��Խ���������
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

		/*// ���ñ�ͷ����
		HSSFFont font_h = workbook.createFont();
		font_h.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		// ���ø�ʽ
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
		header.add("ѧ��");
		header.add("����");
		header.add("�ɼ�");
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
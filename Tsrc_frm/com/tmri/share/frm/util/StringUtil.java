package com.tmri.share.frm.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Blob;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

import javax.imageio.ImageIO;

/**
 * 涉及字符串相关的操作、转换、读取
 * @author jianghailong
 * 
 */
public class StringUtil{
	public static final String CHAR_SET = "GBK";
	
	//增加两个参数,非现场图片的宽度、高度
	public static String img2Size(Blob blob,int maxlength,int outputWidth,int outputHeight) throws Exception {
		String result="";
		int blobsize = (int) blob.length();
		if(blobsize/1024>maxlength){
			result=StringUtil.imgBlob2Str(blob,outputWidth,outputHeight);
		}else{
			result=StringUtil.ConvertBlobToString(blob);
		}
		return result;
	}
	
	public static String img2Size(byte[] b,int maxlength,int outputWidth,int outputHeight) throws Exception {
		String result="";
		int blobsize = (int) b.length;
		if(blobsize/1024>maxlength){
			result=StringUtil.imgByte2Str(b,outputWidth,outputHeight);
		}else{
			sun.misc.BASE64Encoder encode = new sun.misc.BASE64Encoder();
			result=encode.encode(b);
		}
		return result;
	}
	
	public static byte[] img2SizeByte(byte[] b,int maxlength,int outputWidth,int outputHeight) throws Exception {
		byte[] result= null;
		int blobsize = (int) b.length;
		if(blobsize/1024>maxlength){
			result=StringUtil.imgByte2Byte(b,outputWidth,outputHeight);
		}
		return result;
	}
	
	public static String img2Size(String bas64Str,int maxlength,int outputWidth,int outputHeight) throws Exception {
		String result="";
		byte[] b = StringUtil.strToBase64(bas64Str);
		int blobsize = (int) b.length;
		if(blobsize/1024>maxlength){
			result=StringUtil.imgByte2Str(b,outputWidth,outputHeight);
		}else{
			result=bas64Str;
		}
		return result;
	}
	
	public static String imgBlob2Str(Blob blob,int outputWidth,int outputHeight) throws Exception { 
		String result="";
		InputStream input= null;
		try {
			input = blob.getBinaryStream();
			if(input!=null){
				Image img=ImageIO.read(input);
				if(img!=null){
					// 为等比缩放计算输出的图片宽度及高度
					double rate1 = ((double) img.getWidth(null))/ (double) outputWidth ;//+ 0.1;
					double rate2 = ((double) img.getHeight(null))/ (double) outputHeight;// + 0.1;
					// 根据缩放比率大的进行缩放控制
					double rate = rate1 > rate2 ? rate2 : rate1;
					int newWidth = (int) (((double) img.getWidth(null)) / rate);
					int newHeight = (int) (((double) img.getHeight(null)) / rate);
					
					System.out.println("width-"+img.getWidth(null)+";newWidth-"+newWidth);
					System.out.println("height-"+img.getHeight(null)+";newHeight-"+newHeight);
					
					BufferedImage bufferedImage = new BufferedImage((int) newWidth,
							(int) newHeight, BufferedImage.TYPE_INT_RGB);

					bufferedImage.getGraphics().drawImage(
							img.getScaledInstance(newWidth, newHeight,
									Image.SCALE_SMOOTH), 0, 0, null);
					ByteArrayOutputStream baos=new ByteArrayOutputStream();
					ImageIO.write(bufferedImage, "jpeg", baos);
					result=StringUtil.base64ToStr(baos.toByteArray());
				}
			}
		} finally{
			if (null != input) {
				input.close();
			}
		}
		
		return result;
	}
	
	public static String imgByte2Str(byte[] b,int outputWidth,int outputHeight) throws Exception { 
		String result="";
		InputStream input= null;
		try {
			input = new ByteArrayInputStream(b);
			if(input!=null){
				Image img=ImageIO.read(input);
				if(img!=null){
					// 为等比缩放计算输出的图片宽度及高度
					double rate1 = ((double) img.getWidth(null))/ (double) outputWidth ;//+ 0.1;
					double rate2 = ((double) img.getHeight(null))/ (double) outputHeight;// + 0.1;
					// 根据缩放比率大的进行缩放控制
					double rate = rate1 > rate2 ? rate2 : rate1;
					int newWidth = (int) (((double) img.getWidth(null)) / rate);
					int newHeight = (int) (((double) img.getHeight(null)) / rate);
					
					System.out.println("width-"+img.getWidth(null)+";newWidth-"+newWidth);
					System.out.println("height-"+img.getHeight(null)+";newHeight-"+newHeight);
					
					BufferedImage bufferedImage = new BufferedImage((int) newWidth,
							(int) newHeight, BufferedImage.TYPE_INT_RGB);

					bufferedImage.getGraphics().drawImage(
							img.getScaledInstance(newWidth, newHeight,
									Image.SCALE_SMOOTH), 0, 0, null);
					ByteArrayOutputStream baos=new ByteArrayOutputStream();
					ImageIO.write(bufferedImage, "jpeg", baos);
					result=StringUtil.base64ToStr(baos.toByteArray());
				}
			}
		} finally{
			if (null != input) {
				input.close();
			}
		}
		
		return result;
	}
	
	public static byte[] imgByte2Byte(byte[] b,int outputWidth,int outputHeight) throws Exception { 
		byte[] result= null;
		InputStream input= null;
		try {
			input = new ByteArrayInputStream(b);
			if(input!=null){
				Image img=ImageIO.read(input);
				if(img!=null){
					// 为等比缩放计算输出的图片宽度及高度
					double rate1 = ((double) img.getWidth(null))/ (double) outputWidth ;//+ 0.1;
					double rate2 = ((double) img.getHeight(null))/ (double) outputHeight;// + 0.1;
					// 根据缩放比率大的进行缩放控制
					double rate = rate1 > rate2 ? rate2 : rate1;
					int newWidth = (int) (((double) img.getWidth(null)) / rate);
					int newHeight = (int) (((double) img.getHeight(null)) / rate);
					
					System.out.println("width-"+img.getWidth(null)+";newWidth-"+newWidth);
					System.out.println("height-"+img.getHeight(null)+";newHeight-"+newHeight);
					
					BufferedImage bufferedImage = new BufferedImage((int) newWidth,
							(int) newHeight, BufferedImage.TYPE_INT_RGB);

					bufferedImage.getGraphics().drawImage(
							img.getScaledInstance(newWidth, newHeight,
									Image.SCALE_SMOOTH), 0, 0, null);
					ByteArrayOutputStream baos=new ByteArrayOutputStream();
					ImageIO.write(bufferedImage, "jpeg", baos);
					result=baos.toByteArray();
				}
			}
		} finally{
			if (null != input) {
				input.close();
			}
		}
		
		return result;
	}
	
	public static String imgBase642Str(String base64Str,int outputWidth,int outputHeight) throws Exception { 
		byte[] b = StringUtil.strToBase64(base64Str);
		return imgByte2Str(b, outputWidth, outputHeight);
	}
	
	public static boolean checkExistArray(String[] fields,String field){
		boolean result=false;
		for(int i=0;i<fields.length;i++){
			if(field.equalsIgnoreCase(fields[i])){
				result=true;
				break;
			}
		}
		return result;
	}
	
	public static boolean isEmpty(String cs){
		return cs==null || cs.length()==0;
	}
	
	// 将exception 转换为字符串
	public static String transExp2Script(Exception ex){
		String result="";
		result=transScriptStr(getExpMsg(ex));
		return result;
	}

	public static String getExpMsg(Exception e){
		String resultString="";
		boolean isnull=false;
		try{
			resultString=e.getMessage();
			/*
			 * StringWriter sw = new StringWriter(); e.printStackTrace(new
			 * PrintWriter(sw, true)); resultString = sw.toString();
			 */
			if(resultString==null){
				isnull=true;
				resultString="";
			}
			int i=resultString.indexOf("ORA-20001:");
			if(i>=0){
				
			}else if(resultString.indexOf("TERR")>=0){

			}else{
				StackTraceElement[] se=e.getStackTrace();
				if(se.length>0){
					String tmp=null;
					for(int j=0;j<se.length;j++){
						tmp=""+se[j];
						if(tmp.indexOf("com.tmri")>=0&&tmp.indexOf("com.tmri.framework.dao.jdbc.FrmJdbcTemplate")<0){
							resultString+="\\n"+tmp;
							break;
						}
					}
				}
			}
			if(isnull){
				resultString="Null值错误："+resultString;
			}
		}catch(Exception e2){
			e2.printStackTrace();
		}
		return resultString;
	}

	/**
	 * 显示最大字符串quan zhoujn 20100609
	 */
	public static String displayMax(String value,int maxlength){
		String result="";
		if(value==null){
			result="";
		}else{
			if(value.length()>maxlength){
				result="<span style='cursor:hand' title='"+value+"'>"+value.substring(0,maxlength)+"...</span>";
			}else{
				result=value;
			}
		}
		return result;
	}

	/**
	 * 字符串字符替换
	 * @param original 原始字符串
	 * @param find 需要替换的字符串
	 * @param replace 替换后的字符串
	 * @return
	 */
	public static String replaceString(String original,String find,String replace){
		if(original==null){
			original="";
		}
		String returnStr="";
		if(original.indexOf(find)<0){
			returnStr=original;
		}
		try{
			while(original.indexOf(find)>=0){
				int indexbegin=original.indexOf(find);
				String leftstring=original.substring(0,indexbegin);
				original=original.substring(indexbegin+find.length());
				if(original.indexOf(find)<=0){
					returnStr+=leftstring+replace+original;
				}else{
					returnStr+=leftstring+replace;
				}
			}
			return (returnStr);
		}catch(Exception e){
			System.out.println(e.getMessage());
			return original;
		}
	}
	
	//将字符串数组组装成
	public static String transArray2Str(String[] strArray,String split){
		String result="";
		for(int i=0;i<strArray.length;i++){
			result=result+strArray[i]+split;
		}
		if(result.endsWith(split)){
			result=result.substring(0,result.length()-1);
		}
		return result;
	}

	/**
	 * 以特定分割符的字符串分割成数组
	 * @param str 原始字符串
	 * @param split 分割符
	 * @return 字符串数组
	 */
	public static String[] splitString(String str,char split){
		String str2=str;
		int i=1;
		int j=0;
		String[] result;
		while(i>-1){
			j=j+1;
			i=str2.indexOf(split);
			if(i>-1){
				str2=str2.substring(i+1);
			}
		}
		result=new String[j];
		for(int k=0;k<j;k++){
			String str1;
			i=str.indexOf(split);
			if(i>-1){
				str1=str.substring(0,i);
				str=str.substring(i+1);
				result[k]=String.valueOf(str1);
			}else{
				result[k]=String.valueOf(str);
			}
		}
		return result;
	}

	protected static final int BLKSIZ=8192;

	private static String readerToString(Reader is) throws IOException{
		StringBuffer sb=new StringBuffer();
		char[] b=new char[BLKSIZ];
		int n;
		while((n=is.read(b))>0){
			sb.append(b,0,n);

		}
		return sb.toString();
	}

	/**
	 * 读取输入流信息到字符串
	 * @param InputStream 输入流
	 * @return 字符串
	 */
	public static String inputStreamToString(InputStream is) throws IOException{
		return readerToString(new InputStreamReader(is));
	}

	/**
	 * 转换Byte数组为16进制字符串
	 * @param b byte数组
	 * @return 16进制字符串
	 */
	public static String byte2hex(byte[] b){
		StringBuffer hs=new StringBuffer();
		String stmp="";
		for(int n=0;n<b.length;n++){
			stmp=(java.lang.Integer.toHexString(b[n]&0XFF));
			if(stmp.length()==1){
				hs.append("0");
				hs.append(stmp);
			}else{
				hs.append(stmp);
			}
		}
		return hs.toString().toUpperCase();
	}

	/**
	 * 转换16进制字符串为byte数组
	 * @param strkey 16进制字符串
	 * @return byte[] byte数组
	 */
	public static byte[] hex2byte(String strkey){
		int keylength=strkey.length()/2;
		String strValue="";
		byte[] key=new byte[keylength];
		for(int i=0;i<keylength;i++){
			strValue=strkey.substring(2*i,2*(i+1));
			key[i]=Integer.valueOf(strValue,16).byteValue();
		}
		return key;
	}

	/**
	 * 二进制转十六进制
	 * @param bin
	 * @return
	 */
	public static String bin2hex(String bin){
		char[] digital="0123456789ABCDEF".toCharArray();
		StringBuffer sb=new StringBuffer("");
		byte[] bs=bin.getBytes();
		int bit;
		for(int i=0;i<bs.length;i++){
			bit=(bs[i]&0x0f0)>>4;
			sb.append(digital[bit]);
			bit=bs[i]&0x0f;
			sb.append(digital[bit]);
		}
		return sb.toString();
	}

	/**
	 * 转换压缩16进制的字符串为一个Java对象
	 * @param hexStr 16进制字符串
	 * @return Object java对象
	 */
	public static Object pupuZipHexString2Object(String hexStr) throws IOException,ClassNotFoundException,DataFormatException{
		byte[] unzip=hex2byte(hexStr); // deCompress(hex2byte(hexStr));
		return byte2Object(unzip);
	}

	/**
	 * 转换Java对象为16进制压缩的字符串
	 * @param srcObject Java对象
	 * @return String 压缩字符串
	 * @throws IOException
	 */
	public static String popuObject2ZipHexString(Object srcObject) throws IOException{
		byte[] ziped=object2Byte(srcObject); // compress(object2Byte(srcObject));
		String tmp=byte2hex(ziped);
		return tmp;
	}

	/**
	 * 转换byte数组为一个Java对象
	 * @param srcByte byte数组
	 * @return Object java对象
	 */

	public static Object byte2Object(byte[] srcByte) throws ClassNotFoundException,IOException{
		ByteArrayInputStream is=new ByteArrayInputStream(srcByte);
		ObjectInputStream ois=new ObjectInputStream(is);
		return ois.readObject();
	}

	/**
	 * 转换Java对象为byte数组
	 * @param srcObject Java对象
	 * @return byte[] byte数组
	 * @throws IOException
	 */
	public static byte[] object2Byte(Object srcObject) throws IOException{
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		ObjectOutputStream oos=new ObjectOutputStream(os);
		oos.writeObject(srcObject);
		return os.toByteArray();
	}

	/**
	 * 转换double为一定百分比格式的字符串
	 * @param value double数字
	 * @return sjgs 数据格式
	 */
	public static String formatDoubleToPer(double fzvalue,double fmvalue){
		DecimalFormat df=(DecimalFormat)DecimalFormat.getInstance();
		df.applyPattern("##.##%");
		String result="-";
		if(fmvalue>0){
			result=df.format(fzvalue/fmvalue);
		}
		return result;
	}

	/**
	 * 转换String为一定百分比格式的字符串
	 * @param hgvalue 合格数
	 * @param bhgvalue 不合格数
	 * @return
	 */

	public static String formatStringToPer(String hgvalue,String bhgvalue){
		DecimalFormat df=(DecimalFormat)DecimalFormat.getInstance();
		df.applyPattern("#.##%");
		String result="-";
		if(hgvalue.equals("-")&&bhgvalue.equals("-")){
			result="-";
		}else if(hgvalue.equals("-")&&!bhgvalue.equals("-")){
			result="0.00%";
		}else if(!hgvalue.equals("-")&&bhgvalue.equals("-")){
			result="100%";
		}else{
			double d_hg=Double.parseDouble(hgvalue);
			double d_bhg=Double.parseDouble(bhgvalue);
			if((d_bhg+d_hg)>0){
				result=df.format(d_hg/(d_bhg+d_hg));
			}
		}
		return result;
	}
	
	
	public static String formatStringToPer2(String hgvalue,String bhgvalue){
		DecimalFormat df=(DecimalFormat)DecimalFormat.getInstance();
		df.applyPattern("#.##%");
		String result="-";
		if(hgvalue.equals("0")&&bhgvalue.equals("0")){
			result="-";
		}else if(hgvalue.equals("0")&&!bhgvalue.equals("0")){
			result="0.00%";
		}else if(!hgvalue.equals("0")&&bhgvalue.equals("0")){
			result="100%";
		}else{
			double d_hg=Double.parseDouble(hgvalue);
			double d_bhg=Double.parseDouble(bhgvalue);
			if(d_bhg>0){
				result=df.format(d_hg/d_bhg);
			}
		}
		return result;
	}
	
	
	/**
	 * 转换String为一定百分比格式的字符串
	 * @param hgvalue 合格数
	 * @param bhgvalue 不合格数
	 * @return
	 */

	public static String formatStringToPer3(String hgvalue,String bhgvalue,String chgvalue){
		DecimalFormat df=(DecimalFormat)DecimalFormat.getInstance();
		df.applyPattern("#.##%");
		String result="-";
		if(hgvalue.equals("0")&&bhgvalue.equals("0")){
			result="-";
		}else if(hgvalue.equals("0")&&!bhgvalue.equals("0")){
			result="0.00%";
		}else if(!hgvalue.equals("0")&&bhgvalue.equals("0")){
			result="100%";
		}else{
			double d_hg=Double.parseDouble(hgvalue);
			double d_bhg=Double.parseDouble(bhgvalue);
			double d_chg=Double.parseDouble(chgvalue);
			if((d_bhg+d_hg)>0){
				result=df.format(d_hg/(d_bhg+d_hg+d_chg));
			}
		}
		return result;
	}

	/**
	 * 转换double为一定百分比格式的字符串
	 * @param value double数字
	 * @return sjgs 数据格式
	 */
	public static String transNull(String str){

		String result=str;
		if(str==null||str.equals("")||str.equals("null")||str.equals("NULL")){
			result="--";
		}
		return result;
	}

	// 转换空
	// zhoujn 20100521
	public static String transBlank(String str){
		String result=str;
		if(str==null||str.equals("")||str.equals("null")||str.equals("NULL")){
			result="";
		}
		return result;
	}

    // 转换空
    // zhoujn 20100521
    public static String transInteger(String str) {
        String result = str;
        if (str == null || str.equals("") || str.equals("null") || str.equals("NULL")) {
            result = "";
        }
        return result;
    }

    /**
     * 将字符串转换为数字，为空和异常则返回0<br>
     * shiyl
     * @param String 需判断的字符串
     * @return boolean
     */
    public static int trans2Integer(String value) {
        int val = 0;
        try {
            if (StringUtil.checkBN(value)) {
                val = Integer.parseInt(value);
            }
        } catch (NumberFormatException e) {
            val = 0;
        }
        return val;
    }

	/**
	 * 字符串中的双引号和回车处理，以便在javascript中显示
	 * 
	 * @param strJava String
	 * @return String
	 */
	public static String transScriptStr(String strJava){
		String r=null;
		if(strJava==null){
			r="";
		}else{
			r=replaceStr(strJava,"'","\\\'");
			r=replaceStr(r,"\"","\\\"");
			// \r 回车 \n 换行
			r=replaceStr(r,"\r","\\r");
			r=replaceStr(r,"\n","\\n");
		}
		return r;
	}
	
	/**
	 * 处理数据 防止插入到oracle报错
	 * @param strJava
	 * @return
	 */
	public static String transOracleStr(String strJava){
		String r = null;
		if(null == strJava){
			r = "";
		}else{
			r = replaceStr(strJava,"&","and");
			r = replaceStr(r,"\'","''");
			// \r 回车 \n 换行
			r = replaceStr(r,"\r","\\r");
			r = replaceStr(r,"\n","\\n");
		}
		return r;
	}
	
	/**
	 * 字符串替换
	 * @param original 源字符串
	 * @param find
	 * @param replace 替换的字符串
	 * @return
	 */

	public static String replaceStr(String original,String find,String replace){

		if(original==null){
			original="";
		}
		String returnStr="";
		if(original.indexOf(find)<0){
			returnStr=original;
		}
		try{
			while(original.indexOf(find)>=0){
				int indexbegin=original.indexOf(find);
				String leftstring=original.substring(0,indexbegin);
				original=original.substring(indexbegin+find.length());
				if(original.indexOf(find)<0){
					returnStr+=leftstring+replace+original;
				}else{
					returnStr+=leftstring+replace;
				}
			}
			return (returnStr);
		}catch(Exception e){
			System.out.println(e.getMessage());
			return original;
		}
	}

	private static void copy(InputStream in,OutputStream out) throws IOException{

		try{
			byte[] buffer=new byte[4096];
			int nrOfBytes=-1;
			while((nrOfBytes=in.read(buffer))!=-1){
				out.write(buffer,0,nrOfBytes);
			}
			out.flush();
		}catch(IOException e){

		}finally{
			try{
				if(in!=null){
					in.close();
				}
			}catch(IOException ex){
			}
			try{
				if(out!=null){
					out.close();
				}
			}catch(IOException ex){
			}
		}

	}

	public static byte[] getByte(InputStream in){
		ByteArrayOutputStream out=new ByteArrayOutputStream(4096);
		try{
			copy(in,out);
		}catch(IOException e){
			e.printStackTrace();
		}
		return out.toByteArray();
	}
	
	//将inputstream写入bytes
	public static byte[] input2byte(InputStream inStream) throws IOException {
		ByteArrayOutputStream swapStream = new  ByteArrayOutputStream();
		byte[] buff =  new byte[100];
		int rc = 0;
		while((rc=inStream.read(buff, 0, 100))>=0){
			swapStream.write(buff, 0, rc);
		}
		byte[] result=swapStream.toByteArray();
		return result;
	}
	

	public static byte[] strToBase64(String content) throws IOException{
		byte[] result=new sun.misc.BASE64Decoder().decodeBuffer(content.trim());
		return result;
	}

	public static String base64ToStr(byte[] bytes) throws IOException{
		String content="";
		content=new sun.misc.BASE64Encoder().encode(bytes);
		return content;
	}

	/**
	 * 以逗号分隔的字符串相减，字符串1-字符串2
	 * @param aString 字符串1
	 * @param bString 字符串2
	 * @return 相减后的字符串
	 */
	public static String minusStrFromStr(String aString,String bString){
		String result="";
		boolean flag=true;
		String tmpString="";
		String[] aStrings=aString.split(",");
		String[] bStrings=bString.split(",");
		for(int i=0;i<aStrings.length;i++){
			flag=false;
			tmpString=aStrings[i];
			for(int j=0;j<bStrings.length;j++){
				if(tmpString.equals(bStrings[j])){
					flag=true;
					break;
				}
			}
			if(!flag){
				result+=tmpString+",";
			}
		}
		return result;
	}

	public static String subStringByAsciiLen(String original,int m_length){
		String tmp=original;
		int iLen=0;
		for(int i=0;i<original.length();i++){
			char s=original.charAt(i);
			if(s<256){
				iLen+=1;
			}else{
				iLen+=2;
			}
			if(iLen>m_length){
				tmp=original.substring(0,i);
			}
		}
		return tmp;
	}

	/**
	 * 如果为空转换为'--'
	 * 
	 * @param _tmp
	 * @return
	 */
	public static String transNullOrZero(String _tmp){

		String _result="";
		try{
			_result=_tmp;
			if(_tmp==null||_tmp.equals("")||_tmp.equals("0")){
				_result="--";
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return _result;
	}

	// 格式化缺省bean,如果为空设置为缺省值
	public static void FormatBeanEmptyField(Object beanObj,String defaultStr){
		if(beanObj==null){
			return;
		}
		Class classObject=beanObj.getClass(); // 得到class实例
		Field fields[]=classObject.getDeclaredFields(); // 获取私有变量
		for(int i=0;i<fields.length;i++){
			Field field=fields[i];
			if(field.getType().equals(String.class)){
				FormatBeanField(beanObj,field,defaultStr,0);
			}
		}
	}

	private static void FormatBeanField(Object beanObj,Field field,String defaultStr,int type){
		try{
			Class getParam[]=new Class[]{};
			Class setParam[]=new Class[]{String.class};
			Object getValues[]=new Object[0];
			Object setValues[]=new Object[1];
			String fName=field.getName().substring(0,1).toUpperCase()+field.getName().substring(1);
			Method getMeth=beanObj.getClass().getMethod("get"+fName,getParam);
			String val=(String)getMeth.invoke(beanObj,getValues);
			Method setMeth=beanObj.getClass().getMethod("set"+fName,setParam);
			if(val==null||"null".equals(val)||"NULL".equals(val)||"".equals(val)){
				setValues[0]=defaultStr;
				setMeth.invoke(beanObj,setValues);
			}else{
				if(type==2){
					setValues[0]=DateUtil.formatDate(val);
					setMeth.invoke(beanObj,setValues);
				}else if(type==3){
					setValues[0]=DateUtil.formatDateTime(val);
					setMeth.invoke(beanObj,setValues);
				}
			}
		}catch(Exception novalue){
			novalue.printStackTrace();
		}
	}

	public static void FormatBeanDate(Object beanObj,String[] fieldNames,String defaultStr){
		Class classObject=beanObj.getClass(); // 得到class实例
		Field fields[]=classObject.getDeclaredFields(); // 获取私有变量
		for(int i=0;i<fields.length;i++){
			Field field=fields[i];
			for(int j=0;j<fieldNames.length;j++){
				if(fieldNames[j].equals(field.getName())){
					if(field.getType().equals(String.class)){
						FormatBeanField(beanObj,field,defaultStr,2);
					}
				}
			}
		}
	}

	public static void FormatBeanDateTime(Object beanObj,String[] fieldNames,String defaultStr){
		Class classObject=beanObj.getClass(); // 得到class实例
		Field fields[]=classObject.getDeclaredFields(); // 获取私有变量
		for(int i=0;i<fields.length;i++){
			Field field=fields[i];
			for(int j=0;j<fieldNames.length;j++){
				if(fieldNames[j].equals(field.getName())){
					if(field.getType().equals(String.class)){
						FormatBeanField(beanObj,field,defaultStr,3);
					}
				}
			}
		}
	}

	// 验证string 不是null且blank
	public static boolean checkBN(String value){
		boolean result=true;
		if(value==null||value.trim().length()==0||value.equals("null")){
			result=false;
		}
		return result;
	}

	public static String formatMsg(String xtlb,String gnid,String msg_rank,String msg_level,String xh,String msg){
		String result="["+xtlb+gnid+msg_rank+msg_level+xh+"]:"+FuncUtil.cScriptInfoStr(msg);
		return result;
	}

	private final static int[] li_SecPosValue={1601,1637,1833,2078,2274,2302,2433,2594,2787,3106,3212,3472,3635,3722,3730,3858,4027,4086,4390,4558,4684,4925,5249,5590};

	private final static String[] lc_FirstLetter={"a","b","c","d","e","f","g","h","j","k","l","m","n","o","p","q","r","s","t","w","x","y","z"};

	/**
	 * 取得给定汉字串的首字母串,即声母串
	 * @param str 给定汉字串
	 * @return 声母串
	 */
	public static String getAllFirstLetter(String str){
		if(str==null||str.trim().length()==0){
			return "";
		}
		String _str="";
		for(int i=0;i<str.length();i++){
			_str=_str+getFirstLetter(str.substring(i,i+1));
		}
		return _str;
	}

	/**
	 * 取得给定汉字的首字母,即声母
	 * @param chinese 给定的汉字
	 * @return 给定汉字的声母
	 */
	public static String getFirstLetter(String chinese){
		if(chinese==null||chinese.trim().length()==0){
			return "";
		}
		chinese=conversionStr(chinese,"GB2312","ISO8859-1");
		if(chinese.length()>1) // 判断是不是汉字
		{
			int li_SectorCode=(int)chinese.charAt(0); // 汉字区码
			int li_PositionCode=(int)chinese.charAt(1); // 汉字位码
			li_SectorCode=li_SectorCode-160;
			li_PositionCode=li_PositionCode-160;
			int li_SecPosCode=li_SectorCode*100+li_PositionCode; // 汉字区位码
			if(li_SecPosCode>1600&&li_SecPosCode<5590){
				for(int i=0;i<23;i++){
					if(li_SecPosCode>=li_SecPosValue[i]&&li_SecPosCode<li_SecPosValue[i+1]){
						chinese=lc_FirstLetter[i];
						break;
					}
				}
			}else{ // 非汉字字符,如图形符号或ASCII码
				chinese=conversionStr(chinese,"ISO8859-1","GB2312");
				chinese=chinese.substring(0,1);
			}
		}
		return chinese;
	}

	/**
	 * 字符串编码转换
	 * @param str 要转换编码的字符串
	 * @param charsetName 原来的编码
	 * @param toCharsetName 转换后的编码
	 * @return 经过编码转换后的字符串
	 */
	private static String conversionStr(String str,String charsetName,String toCharsetName){
		try{
			str=new String(str.getBytes(charsetName),toCharsetName);
		}catch(UnsupportedEncodingException ex){
			System.out.println("字符串编码转换异常："+ex.getMessage());
		}
		return str;
	}

	/**
	 * 字符串中的双引号和回车处理，以便在javascript中显示
	 * 
	 * @param strJava String
	 * @return String
	 */
	public static String cScriptInfoStr(String strJava){
		String r=null;
		if(strJava==null){
			r="";
		}else{
			r=strJava.replaceAll("'","\"");
			r=r.replaceAll("\"","\\\"");
			// \r 回车 \n 换行
			r=r.replaceAll("\r","");
			r=r.replaceAll("\n","A~A~");
		}
		return r;
	}

	// 将字符串分割为字符数组
	public static String[] splitString(String str,String split){
		String[] result;
		String tmpStr;
		tmpStr=str.trim();
		if(tmpStr.equals("")){
			result=null;
		}else{
			result=tmpStr.split(split);
		}
		return result;
	}

	/**
	 * 15位身份证号转为18位
	 * 
	 * @param zjhm
	 * @return
	 */
	public static String id15to18(String zjhm){
		String[] strJiaoYan={"1","0","X","9","8","7","6","5","4","3","2"};
		int[] intQuan={7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1};

		int ll_sum=0;
		int i;
		zjhm=zjhm.substring(0,6)+"19"+zjhm.substring(6);
		for(i=0;i<zjhm.length();i++){
			ll_sum=ll_sum+(Integer.parseInt(zjhm.substring(i,i+1)))*intQuan[i];
		}
		ll_sum=ll_sum%11;
		zjhm=zjhm+strJiaoYan[ll_sum];
		return zjhm;
	}

	/**
	 * 18证件号转成15位
	 * 
	 * @param zjhm
	 * @return
	 */
	public static String id18to15(String zjhm){
		if(zjhm.length()==18){
			zjhm=zjhm.substring(0,6)+zjhm.substring(8,17);
		}
		return zjhm;
	}

	public static String formatNumPercent(String fms,String fzs){

		String returnStr="";
		if(fms.equals("0")){
			returnStr="--";
		}else{
			NumberFormat form=NumberFormat.getInstance();
			float percent=Float.parseFloat(fzs)/Float.parseFloat(fms);
			percent=percent*100;
			form.setMaximumIntegerDigits(3);
			form.setMinimumFractionDigits(2);
			form.setMaximumFractionDigits(2);
			returnStr=form.format(percent)+"%";
		}
		return returnStr;
	}

	public static String tranRbspCode(String code){
		String codefy="";
		if(code.equals("00")){
			codefy="正常返回";
		}else if(code.equals("01")){
			codefy="数据源状态不可用";
		}else if(code.equals("02")){
			codefy="要查询的业务对象不存在";
		}else if(code.equals("03")){
			codefy="查询条件信息构造错误";
		}else if(code.equals("04")){
			codefy="内部处理程序错误";
		}else if(code.equals("05")){
			codefy="系统内部对应的查询方案不存在";
		}else if(code.equals("06")){
			codefy="查询对象的字段未做共享映射配置";
		}else if(code.equals("07")){
			codefy="查询结果字段构造错误，即SQL语句的select部分构造错误";
		}else if(code.equals("08")){
			codefy="请求的查询对象没有做结果字段配置";
		}else if(code.equals("09")){
			codefy="数据集转换出错，需转换的数据对象包含非法类型字段类型，合法的类型为：STRING，BIGDECIMAL和TIMESTAMP";
		}else if(code.equals("10")){
			codefy="本地并未配置相应的时间字段类型";
		}else if(code.equals("11")){
			codefy="本地未定义图片标识字段";
		}else if(code.equals("12")){
			codefy="本地未定义图片的定位字段";
		}else if(code.equals("13")){
			codefy="本地未定义图片字段，请检查查询对象配置";
		}else if(code.equals("14")){
			codefy="本地未定义图片存储的实际表，请检查对象配置";
		}else if(code.equals("15")){
			codefy="查询图片出错";
		}else if(code.equals("16")){
			codefy="相关对象没有配置表码类型选项，请配置";
		}else if(code.equals("17")){
			codefy="系统中未找到相关的表码信息";
		}else if(code.equals("18")){
			codefy="查询时间超出设置的最大超时时间";
		}else if(code.equals("99")){
			codefy="其他异常";
		}
		return codefy;
	}

	/**
	 * 字符串中的回车处理，以便在网页中正常显示
	 * 
	 * @param strJava String
	 * @return String
	 */
	public static String replaceEnter(String strJava){
		String r=null;
		if(strJava==null){
			r="";
		}else{
			r=strJava.replaceAll("'","\"");
			r=r.replaceAll("\"","\\\"");
			r=r.replaceAll("\r","");
			// r = r.replaceAll("\n", "<Br>");
			r=r.replaceAll("\n","<P>");
			r=r.replaceAll(" ","&nbsp;");
		}
		return r;
	}

	public static String addQuote2Str(String strobj){
		String result="";
		String[] temp=strobj.split(",");
		for(int i=0;i<temp.length;i++){
			if(temp[i].indexOf("'")<0){
				result+="'"+temp[i]+"',";
			}else{
				result+=temp[i]+",";
			}
		}
		if(!result.equals("")){
			result=result.substring(0,result.length()-1);
		}
		return result;
	}

	public static java.sql.Timestamp praseTimestamp(String dateString){
		String format="yyyy-MM-dd HH:mm:ss";
		if(dateString.trim().length()>=17){
			format="yyyy-MM-dd HH:mm:ss";
		}else if(dateString.trim().length()>=14){
			format="yyyy-MM-dd HH:mm";
		}else if(dateString.trim().length()>=10){
			format="yyyy-MM-dd";
		}else{
			return null;
		}
		SimpleDateFormat formatter=new SimpleDateFormat(format);
		Date d=null;
		try{
			d=formatter.parse(dateString);
		}catch(ParseException e){
			e.printStackTrace();
		}
		return new java.sql.Timestamp(d.getTime());
	}

	public static String tss_title(String title){
		String s="";
		s+="<fieldset style=\"width:98%; border-width :thin;\">";
		s+="<legend class=\"legend\">"+title+"</legend>";
		s+="<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr><td height=\"2\"></td></tr></table>";
		return s;
	}

	public static String tss_down(){
		String s="";
		s+="<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr><td height=\"2\"></td></tr></table>";
		s+="</fieldset>";
		return s;
	}
	//清除所有html标签
	public static String HtmlText(String inputString) { 
	      String htmlStr = inputString; //含html标签的字符串 
	      String textStr =""; 
	      java.util.regex.Pattern p_script; 
	      java.util.regex.Matcher m_script; 
	      java.util.regex.Pattern p_style; 
	      java.util.regex.Matcher m_style; 
	      java.util.regex.Pattern p_html; 
	      java.util.regex.Matcher m_html; 
	      try { 
	       String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> } 
	       String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> } 
	          String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式 
	      
	          p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
	          m_script = p_script.matcher(htmlStr); 
	          htmlStr = m_script.replaceAll(""); //过滤script标签 
	
	          p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
	          m_style = p_style.matcher(htmlStr); 
	          htmlStr = m_style.replaceAll(""); //过滤style标签 
	      
	          p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
	          m_html = p_html.matcher(htmlStr); 
	          htmlStr = m_html.replaceAll(""); //过滤html标签 
	          
	          /* 空格 ——   */
	         // p_html = Pattern.compile("\\ ", Pattern.CASE_INSENSITIVE);
	          m_html = p_html.matcher(htmlStr);
	          htmlStr = htmlStr.replaceAll(" "," ");
	       textStr = htmlStr; 
	      
	      }catch(Exception e) { 
	      } 
	      return textStr;
  }  
	
	/**  
     * 判断是否是一个中文汉字  
     *   
     * @param c  
     *            字符  
     * @return true表示是中文汉字，false表示是英文字母  
     * @throws UnsupportedEncodingException  
     *             使用了JAVA不支持的编码格式  
     */  
    private static boolean isChineseChar(char c)   
            throws UnsupportedEncodingException {   
        // 如果字节数大于是汉字   
        // 以这种方式区别英文字母和中文汉字并不是十分严谨，但在这个题目中，这样判断已经足够了   
        return String.valueOf(c).getBytes(CHAR_SET).length > 1;
    }   
  
    /**  
     * 按字节截取字符串  
     *   
     * @param orignal  
     *            原始字符串  
     * @param count  
     *            截取位数  
     * @return 截取后的字符串  
     * @throws UnsupportedEncodingException  
     *             使用了JAVA不支持的编码格式  
     */  
    public static String substring(String orignal, int count)   
            throws UnsupportedEncodingException {   
        // 原始字符不为null，也不是空字符串   
        if (orignal != null && !"".equals(orignal)) {   
            // 将原始字符串转换为GBK编码格式   
            orignal = new String(orignal.getBytes(), CHAR_SET);   
            // 要截取的字节数大于且小于原始字符串的字节数   
            if (count > 0 && count < orignal.getBytes(CHAR_SET).length) {   
                StringBuffer buff = new StringBuffer();   
                char c;   
                for (int i = 0; i < count; i++) {   
                    c = orignal.charAt(i);   
                    buff.append(c);   
                    if (isChineseChar(c)) {   
                        // 遇到中文汉字，截取字节总数减  
                        --count;   
                    }   
                }   
                return buff.toString();   
            }   
        }   
        return orignal;   
    }
    
    
	public static String ScriptToText(String str){
		String r=null;
		if(str==null){
			r="";
		}else{
			r=str.replaceAll("<","〈");
			r=r.replaceAll(">","〉");
		}
		return r;
	}
	
	public static String getQueryNull(String str){
		if(str==null){
			return "|null|";
		}else{
			return str;
		}
	}
	
	/** 获取异常中的输出 */
	public static String getErrorInfoFromException(Exception e){
		try{
			StringWriter sw=new StringWriter();
			PrintWriter pw=new PrintWriter(sw);
			e.printStackTrace(pw);
			String s=sw.toString().replaceAll("	","");
			if(s.length()>=2000){
				s=s.substring(0,2000)+"\r\n......";
			}
			return s;
		}catch(Exception e2){
			return "bad getErrorInfoFromException";
		}
	}
	
	public static String getXtlbsql(String xtlbs,String alias){
		String sqlxtlb="";
		String[] xtlb=xtlbs.split(",");
		if(xtlb!=null&&xtlb.length>0){
			sqlxtlb=" and ( ";
			for(int i=0;i<xtlb.length;i++){
				if(i==0){
					sqlxtlb=sqlxtlb+"  "+alias+".xtlb = '"+xtlb[i]+"'";
				}else{
					sqlxtlb=sqlxtlb+"  or "+alias+".xtlb = '"+xtlb[i]+"'";
				}
			}
			sqlxtlb=sqlxtlb+" ) ";
		}	
		return sqlxtlb;
	}
	
	public static boolean checkHphmt(String zftj,String hphm){
		if(zftj==null||zftj.equals("")){
			return false;
		}
		if(hphm==null||hphm.equals("")){
			return false;
		}
		String arr[] = splitString(zftj,",");
		for(int i=0;i<arr.length;i++){
			if(hphm.indexOf(arr[i])>-1){
				return true;
			}
		}
		return false;
	}
	
	public static String ConvertBlobToString(java.sql.Blob blob1) throws Exception{
		InputStream pi = null;
		int blobsize = 0;
		if (blob1 != null && blob1.length() > 0) {
			pi = blob1.getBinaryStream();
			blobsize = (int) blob1.length();
			byte[] blobbytes = new byte[blobsize];
			int bytesRead = 0;
			while ((bytesRead = pi.read(blobbytes)) != -1) {

			}
			sun.misc.BASE64Encoder encode = new sun.misc.BASE64Encoder();
			String base64str = encode.encode(blobbytes);
			return base64str;
		}
		return "";
	}
	
	
	public static String encodeUTF8(String xmlDoc) {
		String str = "";
		try {
			xmlDoc=StringUtil.transBlank(xmlDoc);
			if(!xmlDoc.equals("")){
				str = URLEncoder.encode(xmlDoc, "utf-8");
			}			
			return str;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return str;
	}
    
	public static String decodeUTF8(String str) throws Exception {
		String xmlDoc = "";
		try {
            str = StringUtil.transBlank(str);
            if (!"".equals(str)) {
                xmlDoc = URLDecoder.decode(str, "utf-8");
            }
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return xmlDoc;
	}
		
}

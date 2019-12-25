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
 * �漰�ַ�����صĲ�����ת������ȡ
 * @author jianghailong
 * 
 */
public class StringUtil{
	public static final String CHAR_SET = "GBK";
	
	//������������,���ֳ�ͼƬ�Ŀ�ȡ��߶�
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
					// Ϊ�ȱ����ż��������ͼƬ��ȼ��߶�
					double rate1 = ((double) img.getWidth(null))/ (double) outputWidth ;//+ 0.1;
					double rate2 = ((double) img.getHeight(null))/ (double) outputHeight;// + 0.1;
					// �������ű��ʴ�Ľ������ſ���
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
					// Ϊ�ȱ����ż��������ͼƬ��ȼ��߶�
					double rate1 = ((double) img.getWidth(null))/ (double) outputWidth ;//+ 0.1;
					double rate2 = ((double) img.getHeight(null))/ (double) outputHeight;// + 0.1;
					// �������ű��ʴ�Ľ������ſ���
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
					// Ϊ�ȱ����ż��������ͼƬ��ȼ��߶�
					double rate1 = ((double) img.getWidth(null))/ (double) outputWidth ;//+ 0.1;
					double rate2 = ((double) img.getHeight(null))/ (double) outputHeight;// + 0.1;
					// �������ű��ʴ�Ľ������ſ���
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
	
	// ��exception ת��Ϊ�ַ���
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
				resultString="Nullֵ����"+resultString;
			}
		}catch(Exception e2){
			e2.printStackTrace();
		}
		return resultString;
	}

	/**
	 * ��ʾ����ַ���quan zhoujn 20100609
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
	 * �ַ����ַ��滻
	 * @param original ԭʼ�ַ���
	 * @param find ��Ҫ�滻���ַ���
	 * @param replace �滻����ַ���
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
	
	//���ַ���������װ��
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
	 * ���ض��ָ�����ַ����ָ������
	 * @param str ԭʼ�ַ���
	 * @param split �ָ��
	 * @return �ַ�������
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
	 * ��ȡ��������Ϣ���ַ���
	 * @param InputStream ������
	 * @return �ַ���
	 */
	public static String inputStreamToString(InputStream is) throws IOException{
		return readerToString(new InputStreamReader(is));
	}

	/**
	 * ת��Byte����Ϊ16�����ַ���
	 * @param b byte����
	 * @return 16�����ַ���
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
	 * ת��16�����ַ���Ϊbyte����
	 * @param strkey 16�����ַ���
	 * @return byte[] byte����
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
	 * ������תʮ������
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
	 * ת��ѹ��16���Ƶ��ַ���Ϊһ��Java����
	 * @param hexStr 16�����ַ���
	 * @return Object java����
	 */
	public static Object pupuZipHexString2Object(String hexStr) throws IOException,ClassNotFoundException,DataFormatException{
		byte[] unzip=hex2byte(hexStr); // deCompress(hex2byte(hexStr));
		return byte2Object(unzip);
	}

	/**
	 * ת��Java����Ϊ16����ѹ�����ַ���
	 * @param srcObject Java����
	 * @return String ѹ���ַ���
	 * @throws IOException
	 */
	public static String popuObject2ZipHexString(Object srcObject) throws IOException{
		byte[] ziped=object2Byte(srcObject); // compress(object2Byte(srcObject));
		String tmp=byte2hex(ziped);
		return tmp;
	}

	/**
	 * ת��byte����Ϊһ��Java����
	 * @param srcByte byte����
	 * @return Object java����
	 */

	public static Object byte2Object(byte[] srcByte) throws ClassNotFoundException,IOException{
		ByteArrayInputStream is=new ByteArrayInputStream(srcByte);
		ObjectInputStream ois=new ObjectInputStream(is);
		return ois.readObject();
	}

	/**
	 * ת��Java����Ϊbyte����
	 * @param srcObject Java����
	 * @return byte[] byte����
	 * @throws IOException
	 */
	public static byte[] object2Byte(Object srcObject) throws IOException{
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		ObjectOutputStream oos=new ObjectOutputStream(os);
		oos.writeObject(srcObject);
		return os.toByteArray();
	}

	/**
	 * ת��doubleΪһ���ٷֱȸ�ʽ���ַ���
	 * @param value double����
	 * @return sjgs ���ݸ�ʽ
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
	 * ת��StringΪһ���ٷֱȸ�ʽ���ַ���
	 * @param hgvalue �ϸ���
	 * @param bhgvalue ���ϸ���
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
	 * ת��StringΪһ���ٷֱȸ�ʽ���ַ���
	 * @param hgvalue �ϸ���
	 * @param bhgvalue ���ϸ���
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
	 * ת��doubleΪһ���ٷֱȸ�ʽ���ַ���
	 * @param value double����
	 * @return sjgs ���ݸ�ʽ
	 */
	public static String transNull(String str){

		String result=str;
		if(str==null||str.equals("")||str.equals("null")||str.equals("NULL")){
			result="--";
		}
		return result;
	}

	// ת����
	// zhoujn 20100521
	public static String transBlank(String str){
		String result=str;
		if(str==null||str.equals("")||str.equals("null")||str.equals("NULL")){
			result="";
		}
		return result;
	}

    // ת����
    // zhoujn 20100521
    public static String transInteger(String str) {
        String result = str;
        if (str == null || str.equals("") || str.equals("null") || str.equals("NULL")) {
            result = "";
        }
        return result;
    }

    /**
     * ���ַ���ת��Ϊ���֣�Ϊ�պ��쳣�򷵻�0<br>
     * shiyl
     * @param String ���жϵ��ַ���
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
	 * �ַ����е�˫���źͻس������Ա���javascript����ʾ
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
			// \r �س� \n ����
			r=replaceStr(r,"\r","\\r");
			r=replaceStr(r,"\n","\\n");
		}
		return r;
	}
	
	/**
	 * �������� ��ֹ���뵽oracle����
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
			// \r �س� \n ����
			r = replaceStr(r,"\r","\\r");
			r = replaceStr(r,"\n","\\n");
		}
		return r;
	}
	
	/**
	 * �ַ����滻
	 * @param original Դ�ַ���
	 * @param find
	 * @param replace �滻���ַ���
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
	
	//��inputstreamд��bytes
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
	 * �Զ��ŷָ����ַ���������ַ���1-�ַ���2
	 * @param aString �ַ���1
	 * @param bString �ַ���2
	 * @return �������ַ���
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
	 * ���Ϊ��ת��Ϊ'--'
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

	// ��ʽ��ȱʡbean,���Ϊ������Ϊȱʡֵ
	public static void FormatBeanEmptyField(Object beanObj,String defaultStr){
		if(beanObj==null){
			return;
		}
		Class classObject=beanObj.getClass(); // �õ�classʵ��
		Field fields[]=classObject.getDeclaredFields(); // ��ȡ˽�б���
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
		Class classObject=beanObj.getClass(); // �õ�classʵ��
		Field fields[]=classObject.getDeclaredFields(); // ��ȡ˽�б���
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
		Class classObject=beanObj.getClass(); // �õ�classʵ��
		Field fields[]=classObject.getDeclaredFields(); // ��ȡ˽�б���
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

	// ��֤string ����null��blank
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
	 * ȡ�ø������ִ�������ĸ��,����ĸ��
	 * @param str �������ִ�
	 * @return ��ĸ��
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
	 * ȡ�ø������ֵ�����ĸ,����ĸ
	 * @param chinese �����ĺ���
	 * @return �������ֵ���ĸ
	 */
	public static String getFirstLetter(String chinese){
		if(chinese==null||chinese.trim().length()==0){
			return "";
		}
		chinese=conversionStr(chinese,"GB2312","ISO8859-1");
		if(chinese.length()>1) // �ж��ǲ��Ǻ���
		{
			int li_SectorCode=(int)chinese.charAt(0); // ��������
			int li_PositionCode=(int)chinese.charAt(1); // ����λ��
			li_SectorCode=li_SectorCode-160;
			li_PositionCode=li_PositionCode-160;
			int li_SecPosCode=li_SectorCode*100+li_PositionCode; // ������λ��
			if(li_SecPosCode>1600&&li_SecPosCode<5590){
				for(int i=0;i<23;i++){
					if(li_SecPosCode>=li_SecPosValue[i]&&li_SecPosCode<li_SecPosValue[i+1]){
						chinese=lc_FirstLetter[i];
						break;
					}
				}
			}else{ // �Ǻ����ַ�,��ͼ�η��Ż�ASCII��
				chinese=conversionStr(chinese,"ISO8859-1","GB2312");
				chinese=chinese.substring(0,1);
			}
		}
		return chinese;
	}

	/**
	 * �ַ�������ת��
	 * @param str Ҫת��������ַ���
	 * @param charsetName ԭ���ı���
	 * @param toCharsetName ת����ı���
	 * @return ��������ת������ַ���
	 */
	private static String conversionStr(String str,String charsetName,String toCharsetName){
		try{
			str=new String(str.getBytes(charsetName),toCharsetName);
		}catch(UnsupportedEncodingException ex){
			System.out.println("�ַ�������ת���쳣��"+ex.getMessage());
		}
		return str;
	}

	/**
	 * �ַ����е�˫���źͻس������Ա���javascript����ʾ
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
			// \r �س� \n ����
			r=r.replaceAll("\r","");
			r=r.replaceAll("\n","A~A~");
		}
		return r;
	}

	// ���ַ����ָ�Ϊ�ַ�����
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
	 * 15λ���֤��תΪ18λ
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
	 * 18֤����ת��15λ
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
			codefy="��������";
		}else if(code.equals("01")){
			codefy="����Դ״̬������";
		}else if(code.equals("02")){
			codefy="Ҫ��ѯ��ҵ����󲻴���";
		}else if(code.equals("03")){
			codefy="��ѯ������Ϣ�������";
		}else if(code.equals("04")){
			codefy="�ڲ�����������";
		}else if(code.equals("05")){
			codefy="ϵͳ�ڲ���Ӧ�Ĳ�ѯ����������";
		}else if(code.equals("06")){
			codefy="��ѯ������ֶ�δ������ӳ������";
		}else if(code.equals("07")){
			codefy="��ѯ����ֶι�����󣬼�SQL����select���ֹ������";
		}else if(code.equals("08")){
			codefy="����Ĳ�ѯ����û��������ֶ�����";
		}else if(code.equals("09")){
			codefy="���ݼ�ת��������ת�������ݶ�������Ƿ������ֶ����ͣ��Ϸ�������Ϊ��STRING��BIGDECIMAL��TIMESTAMP";
		}else if(code.equals("10")){
			codefy="���ز�δ������Ӧ��ʱ���ֶ�����";
		}else if(code.equals("11")){
			codefy="����δ����ͼƬ��ʶ�ֶ�";
		}else if(code.equals("12")){
			codefy="����δ����ͼƬ�Ķ�λ�ֶ�";
		}else if(code.equals("13")){
			codefy="����δ����ͼƬ�ֶΣ������ѯ��������";
		}else if(code.equals("14")){
			codefy="����δ����ͼƬ�洢��ʵ�ʱ������������";
		}else if(code.equals("15")){
			codefy="��ѯͼƬ����";
		}else if(code.equals("16")){
			codefy="��ض���û�����ñ�������ѡ�������";
		}else if(code.equals("17")){
			codefy="ϵͳ��δ�ҵ���صı�����Ϣ";
		}else if(code.equals("18")){
			codefy="��ѯʱ�䳬�����õ����ʱʱ��";
		}else if(code.equals("99")){
			codefy="�����쳣";
		}
		return codefy;
	}

	/**
	 * �ַ����еĻس������Ա�����ҳ��������ʾ
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
	//�������html��ǩ
	public static String HtmlText(String inputString) { 
	      String htmlStr = inputString; //��html��ǩ���ַ��� 
	      String textStr =""; 
	      java.util.regex.Pattern p_script; 
	      java.util.regex.Matcher m_script; 
	      java.util.regex.Pattern p_style; 
	      java.util.regex.Matcher m_style; 
	      java.util.regex.Pattern p_html; 
	      java.util.regex.Matcher m_html; 
	      try { 
	       String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //����script��������ʽ{��<script[^>]*?>[\\s\\S]*?<\\/script> } 
	       String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //����style��������ʽ{��<style[^>]*?>[\\s\\S]*?<\\/style> } 
	          String regEx_html = "<[^>]+>"; //����HTML��ǩ��������ʽ 
	      
	          p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
	          m_script = p_script.matcher(htmlStr); 
	          htmlStr = m_script.replaceAll(""); //����script��ǩ 
	
	          p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
	          m_style = p_style.matcher(htmlStr); 
	          htmlStr = m_style.replaceAll(""); //����style��ǩ 
	      
	          p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
	          m_html = p_html.matcher(htmlStr); 
	          htmlStr = m_html.replaceAll(""); //����html��ǩ 
	          
	          /* �ո� ����   */
	         // p_html = Pattern.compile("\\ ", Pattern.CASE_INSENSITIVE);
	          m_html = p_html.matcher(htmlStr);
	          htmlStr = htmlStr.replaceAll(" "," ");
	       textStr = htmlStr; 
	      
	      }catch(Exception e) { 
	      } 
	      return textStr;
  }  
	
	/**  
     * �ж��Ƿ���һ�����ĺ���  
     *   
     * @param c  
     *            �ַ�  
     * @return true��ʾ�����ĺ��֣�false��ʾ��Ӣ����ĸ  
     * @throws UnsupportedEncodingException  
     *             ʹ����JAVA��֧�ֵı����ʽ  
     */  
    private static boolean isChineseChar(char c)   
            throws UnsupportedEncodingException {   
        // ����ֽ��������Ǻ���   
        // �����ַ�ʽ����Ӣ����ĸ�����ĺ��ֲ�����ʮ���Ͻ������������Ŀ�У������ж��Ѿ��㹻��   
        return String.valueOf(c).getBytes(CHAR_SET).length > 1;
    }   
  
    /**  
     * ���ֽڽ�ȡ�ַ���  
     *   
     * @param orignal  
     *            ԭʼ�ַ���  
     * @param count  
     *            ��ȡλ��  
     * @return ��ȡ����ַ���  
     * @throws UnsupportedEncodingException  
     *             ʹ����JAVA��֧�ֵı����ʽ  
     */  
    public static String substring(String orignal, int count)   
            throws UnsupportedEncodingException {   
        // ԭʼ�ַ���Ϊnull��Ҳ���ǿ��ַ���   
        if (orignal != null && !"".equals(orignal)) {   
            // ��ԭʼ�ַ���ת��ΪGBK�����ʽ   
            orignal = new String(orignal.getBytes(), CHAR_SET);   
            // Ҫ��ȡ���ֽ���������С��ԭʼ�ַ������ֽ���   
            if (count > 0 && count < orignal.getBytes(CHAR_SET).length) {   
                StringBuffer buff = new StringBuffer();   
                char c;   
                for (int i = 0; i < count; i++) {   
                    c = orignal.charAt(i);   
                    buff.append(c);   
                    if (isChineseChar(c)) {   
                        // �������ĺ��֣���ȡ�ֽ�������  
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
			r=str.replaceAll("<","��");
			r=r.replaceAll(">","��");
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
	
	/** ��ȡ�쳣�е���� */
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

package com.tmri.pub.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * �ṩ���ܽ��ܡ����������ѹ��������
 * @author jianghailong
 * 
 */
public class WrapUtil{

	/**
	 * ʹ��deflaterѹ���ֽ�����
	 * @param srcByte Դ����
	 * @return byte[] ѹ���������
	 */
	public static byte[] compress(byte[] srcByte){
		byte[] output=new byte[srcByte.length];
		Deflater compresser=new Deflater();
		compresser.setInput(srcByte);
		compresser.finish();
		int compressedDataLength=compresser.deflate(output);
		byte[] result=new byte[compressedDataLength];
		System.arraycopy(output,0,result,0,compressedDataLength);
		return result;
	}

	/**
	 * ʹ��inflater��ѹ���ֽ�����
	 * @param srcByte Դ����
	 * @return byte[] ��ѹ���������
	 */
	public static byte[] deCompress(byte[] compressedByte) throws DataFormatException{
		Inflater decompresser=new Inflater();
		decompresser.setInput(compressedByte,0,compressedByte.length);
		byte[] output=new byte[2*compressedByte.length];
		int resultLength=decompresser.inflate(output);
		decompresser.end();
		byte[] result=new byte[resultLength];
		System.arraycopy(output,0,result,0,resultLength);
		return result;
	}

	/**
	 * ��ȡ���趨��Χ���������
	 * @param min ��С��ֵ
	 * @param max �����ֵ
	 * @return �����
	 */
	public static int getRandomInt(int min,int max){
		// include min, exclude max
		int result=min+new Double(Math.random()*(max-min)).intValue();
		return result;
	}

	/**
	 * ��ȡ��������ַ� ����0-9,a-z,A-Z
	 * @return �����
	 */
	public static char getRandomNormalChar(){
		int number=getRandomInt(0,62);
		int zeroChar=48;
		int nineChar=57;
		int aChar=97;
		int zChar=122;
		int AChar=65;
		int ZChar=90;
		char result;
		if(number<10){
			result=(char)(getRandomInt(zeroChar,nineChar+1));
			return result;
		}else if(number>=10&&number<36){
			result=(char)(getRandomInt(AChar,ZChar+1));
			return result;
		}else if(number>=36&&number<62){
			result=(char)(getRandomInt(aChar,zChar+1));
			return result;
		}else{
			return 0;
		}

	}

	/**
	 * ��ȡ���������ַ���������� ����0-9,a-z,A-Z
	 * @param length �ַ�������
	 * @return ����ַ���
	 */
	public static String getRandomNormalString(int length){
		StringBuffer result=new StringBuffer();
		for(int i=0;i<length;i++){
			result.append(getRandomNormalChar());
		}
		return result.toString();
	}

	/**
	 * xml�ַ���ת��Ϊutf-8����
	 * @param xmlDoc xml�ַ���
	 * @return utf-8�����ַ���
	 */
	public static String encodeUTF8(String xmlDoc) throws Exception{
		String str=URLEncoder.encode(xmlDoc,"utf-8");
		return str;
	}

	/**
	 * utf-8����ת��Ϊһ���ַ���
	 * @param xmlDoc utf-8�����ַ���
	 * @return һ���ַ���
	 */
	public static String decodeUTF8(String str) throws Exception{
		String xmlDoc=URLDecoder.decode(str,"utf-8");
		return xmlDoc;
	}

	// ��������㷨,����DES,DESede,Blowfish
	private static String Algorithm="DES";

	private static String key="12345678";

	/**
	 * ʹ��Ĭ����Կ�����ַ�������
	 * @param cleartext �����ַ���
	 * @throws Exception
	 * @return �����ַ���
	 */
	public static String Encrypt(String cleartext) throws Exception{
		SecretKey skey=new SecretKeySpec(key.getBytes(),Algorithm);
		Cipher cipher=Cipher.getInstance(Algorithm);
		cipher.init(Cipher.ENCRYPT_MODE,skey);
		byte[] cipherByte=cipher.doFinal(cleartext.getBytes());
		return URLEncoder.encode(new String(cipherByte,"ISO-8859-1"),"ISO-8859-1");
	}
	/**
	 * ʹ��Ĭ����Կ�����ַ�������
	 * @param cleartext �����ַ���
	 * @throws Exception
	 * @return �����ַ���
	 */
	public static String EncryptBase64(String cleartext) throws Exception{
		SecretKey skey=new SecretKeySpec(key.getBytes(),Algorithm);
		Cipher cipher=Cipher.getInstance(Algorithm);
		cipher.init(Cipher.ENCRYPT_MODE,skey);
		byte[] cipherByte=cipher.doFinal(cleartext.getBytes());
		//return URLEncoder.encode(new String(cipherByte,"ISO-8859-1"),"ISO-8859-1");
		return new BASE64Encoder().encode(cipherByte);
	}
	/**
	 * ʹ����Կ�����ֽ�������,�������ܵ���Կ�������Կ��ͬ����Կ�������ƣ�
	 * @param ciphertext �����ַ���
	 * @throws Exception
	 * @return �����ַ���
	 */
	public static String Decrypt(String ciphertext) throws Exception{
		// ����
		ciphertext=URLDecoder.decode(ciphertext,"ISO-8859-1");
		SecretKey skey=new SecretKeySpec(key.getBytes(),Algorithm);
		Cipher cipher=Cipher.getInstance(Algorithm);
		cipher.init(Cipher.DECRYPT_MODE,skey);
		byte[] clearByte=cipher.doFinal(ciphertext.getBytes("ISO-8859-1"));
		return new String(clearByte);
	}
	/**
	 * ʹ����Կ�����ֽ�������,�������ܵ���Կ�������Կ��ͬ����Կ�������ƣ�
	 * @param ciphertext �����ַ���
	 * @throws Exception
	 * @return �����ַ���
	 */
	public static String DecryptBase64(String ciphertext) throws Exception{
		// ����
		SecretKey skey=new SecretKeySpec(key.getBytes(),Algorithm);
		Cipher cipher=Cipher.getInstance(Algorithm);
		cipher.init(Cipher.DECRYPT_MODE,skey);
		byte[] clearByte=cipher.doFinal(new BASE64Decoder().decodeBuffer(ciphertext));
		return new String(clearByte);
	}
	
	

	/**
	 * ʹ��Ĭ����Կ������ֵ������
	 * @param cleartext ��������
	 * @throws Exception
	 * @return �����ַ���
	 */
	public static String EncryptForFloat(float cleartext) throws Exception{
		SecretKey skey=new SecretKeySpec(key.getBytes(),Algorithm);
		Cipher cipher=Cipher.getInstance(Algorithm);
		cipher.init(Cipher.ENCRYPT_MODE,skey);
		// byte[] cipherByte = cipher.doFinal(cleartext.getBytes());
		// ת�룬��ת��Ϊiso-8859-1���һЩ����
		// return URLEncoder.encode(new
		// String(cipherByte,"ISO-8859-1"),"ISO-8859-1");
		byte[] cipherByte=cipher.doFinal(String.valueOf(cleartext).getBytes());
		// ת�룬��ת��Ϊiso-8859-1���һЩ����
		String temp=URLEncoder.encode(new String(cipherByte,"ISO-8859-1"),"ISO-8859-1");
		// return Float.valueOf(temp).floatValue();
		return temp;
	}

	/**
	 * ʹ����Կ�����ֽ�������,�������ܵ���Կ�������Կ��ͬ����Կ�������ƣ�
	 * @param ciphertext String
	 * @throws Exception
	 * @return String
	 */
	public static float DecryptForFloat(String ciphertext) throws Exception{
		// ����
		String temp1=String.valueOf(ciphertext);
		temp1=URLDecoder.decode(temp1,"ISO-8859-1");
		SecretKey skey=new SecretKeySpec(key.getBytes(),Algorithm);
		Cipher cipher=Cipher.getInstance(Algorithm);
		cipher.init(Cipher.DECRYPT_MODE,skey);
		byte[] clearByte=cipher.doFinal(temp1.getBytes("ISO-8859-1"));
		String temp2=new String(clearByte);
		return Float.valueOf(temp2).floatValue();
	}
}

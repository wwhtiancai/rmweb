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
 * 提供加密解密、随机函数、压缩、编码
 * @author jianghailong
 * 
 */
public class WrapUtil{

	/**
	 * 使用deflater压缩字节数组
	 * @param srcByte 源数组
	 * @return byte[] 压缩后的数组
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
	 * 使用inflater解压缩字节数组
	 * @param srcByte 源数组
	 * @return byte[] 解压缩后的数组
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
	 * 获取在设定范围内随机数字
	 * @param min 最小数值
	 * @param max 最大数值
	 * @return 随机数
	 */
	public static int getRandomInt(int min,int max){
		// include min, exclude max
		int result=min+new Double(Math.random()*(max-min)).intValue();
		return result;
	}

	/**
	 * 获取常规随机字符 包括0-9,a-z,A-Z
	 * @return 随机数
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
	 * 获取包括常规字符串的随机数 包括0-9,a-z,A-Z
	 * @param length 字符串长度
	 * @return 随机字符串
	 */
	public static String getRandomNormalString(int length){
		StringBuffer result=new StringBuffer();
		for(int i=0;i<length;i++){
			result.append(getRandomNormalChar());
		}
		return result.toString();
	}

	/**
	 * xml字符串转换为utf-8编码
	 * @param xmlDoc xml字符串
	 * @return utf-8编码字符串
	 */
	public static String encodeUTF8(String xmlDoc) throws Exception{
		String str=URLEncoder.encode(xmlDoc,"utf-8");
		return str;
	}

	/**
	 * utf-8编码转换为一般字符串
	 * @param xmlDoc utf-8编码字符串
	 * @return 一般字符串
	 */
	public static String decodeUTF8(String str) throws Exception{
		String xmlDoc=URLDecoder.decode(str,"utf-8");
		return xmlDoc;
	}

	// 定义加密算法,可用DES,DESede,Blowfish
	private static String Algorithm="DES";

	private static String key="12345678";

	/**
	 * 使用默认密钥加密字符串数据
	 * @param cleartext 明文字符串
	 * @throws Exception
	 * @return 密文字符串
	 */
	public static String Encrypt(String cleartext) throws Exception{
		SecretKey skey=new SecretKeySpec(key.getBytes(),Algorithm);
		Cipher cipher=Cipher.getInstance(Algorithm);
		cipher.init(Cipher.ENCRYPT_MODE,skey);
		byte[] cipherByte=cipher.doFinal(cleartext.getBytes());
		return URLEncoder.encode(new String(cipherByte,"ISO-8859-1"),"ISO-8859-1");
	}
	/**
	 * 使用默认密钥加密字符串数据
	 * @param cleartext 明文字符串
	 * @throws Exception
	 * @return 密文字符串
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
	 * 使用密钥解密字节码数据,用来解密的密钥与加密密钥相同（单钥密码体制）
	 * @param ciphertext 密文字符串
	 * @throws Exception
	 * @return 明文字符串
	 */
	public static String Decrypt(String ciphertext) throws Exception{
		// 解码
		ciphertext=URLDecoder.decode(ciphertext,"ISO-8859-1");
		SecretKey skey=new SecretKeySpec(key.getBytes(),Algorithm);
		Cipher cipher=Cipher.getInstance(Algorithm);
		cipher.init(Cipher.DECRYPT_MODE,skey);
		byte[] clearByte=cipher.doFinal(ciphertext.getBytes("ISO-8859-1"));
		return new String(clearByte);
	}
	/**
	 * 使用密钥解密字节码数据,用来解密的密钥与加密密钥相同（单钥密码体制）
	 * @param ciphertext 密文字符串
	 * @throws Exception
	 * @return 明文字符串
	 */
	public static String DecryptBase64(String ciphertext) throws Exception{
		// 解码
		SecretKey skey=new SecretKeySpec(key.getBytes(),Algorithm);
		Cipher cipher=Cipher.getInstance(Algorithm);
		cipher.init(Cipher.DECRYPT_MODE,skey);
		byte[] clearByte=cipher.doFinal(new BASE64Decoder().decodeBuffer(ciphertext));
		return new String(clearByte);
	}
	
	

	/**
	 * 使用默认密钥加密数值型数据
	 * @param cleartext 明文数字
	 * @throws Exception
	 * @return 密文字符串
	 */
	public static String EncryptForFloat(float cleartext) throws Exception{
		SecretKey skey=new SecretKeySpec(key.getBytes(),Algorithm);
		Cipher cipher=Cipher.getInstance(Algorithm);
		cipher.init(Cipher.ENCRYPT_MODE,skey);
		// byte[] cipherByte = cipher.doFinal(cleartext.getBytes());
		// 转码，不转码为iso-8859-1会出一些问题
		// return URLEncoder.encode(new
		// String(cipherByte,"ISO-8859-1"),"ISO-8859-1");
		byte[] cipherByte=cipher.doFinal(String.valueOf(cleartext).getBytes());
		// 转码，不转码为iso-8859-1会出一些问题
		String temp=URLEncoder.encode(new String(cipherByte,"ISO-8859-1"),"ISO-8859-1");
		// return Float.valueOf(temp).floatValue();
		return temp;
	}

	/**
	 * 使用密钥解密字节码数据,用来解密的密钥与加密密钥相同（单钥密码体制）
	 * @param ciphertext String
	 * @throws Exception
	 * @return String
	 */
	public static float DecryptForFloat(String ciphertext) throws Exception{
		// 解码
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

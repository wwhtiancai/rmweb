package com.tmri.framework.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class CheckCode{
	private String code;

	private int width;

	private int height;
 
	/* ϵͳ���п��õ����� */
	private static Font[] fonts;

	static{
		fonts=new Font[]{new Font("ITALIC",Font.BOLD,32),new Font("����",Font.BOLD,32),new Font("����",Font.BOLD,32)};
	}
	private int fontSize=30;

	/* ��֤��ı���ɫ������һ��int�ͱ�ʾ�� ���ֵ�� 16-23 λ��ʾ��ɫ������8-15 λ��ʾ��ɫ������0-7 λ��ʾ��ɫ���� */
	private int bgColor=0xFFFFFF;

	/* ��֤��ǰ��ɫ�ĸ���������Χ, ��СΪ0-255 ����С�������У�����R,G,B����ֵ */
	private int[] fgColorRange=new int[]{0,150};

	/* ����֤������ַ����б任ʱ���������ӷ�Χ������x�����y���� */
	private double[] scaleRange=new double[]{0.8,1};//05.1

	/* ��֤������ַ����б任ʱ���б����ӷ�Χ������x�����y���� */
	private double[] shearRange=new double[]{0,0};

	/* ��֤������ַ����б任ʱ����ת���ӷ�Χ����λΪ���� */
	/*20111031 ����������Ҫ�� ������ת�Ƕȴ�С����PI/3����ΪPI/6*/
	private double[] rotateRange=new double[]{-Math.PI/6,Math.PI/6};

	private int fontWidth;
  
	/*��ȡ��������ַ�*/
	private char[] generateCheckCode(int mode,int len){
		// ������֤����ַ���
		String chars="123456789ABCDEFGHJKLPQRSTUVXYabcdefghijklmnpqrstuvwxyz����������趯";
		int alen = (int)(Math.random()*(len + 1));
		char[] rands=new char[4+alen];
		int rand = 0;
		for(int i=0;i<4+alen;i++){
			if(mode==1){
				rand =(int)(Math.random()*9);	
			}else if(mode==2){
				rand =(int)(Math.random()*29);	
			}else if(mode==3){
				rand =(int)(Math.random()*50);	
			}else{
				rand =(int)(Math.random()*chars.length());	
			}
			rands[i]=chars.charAt(rand);
		}
		return rands;
	}
	/*
	 * mode 1:���� 2�����ּӴ�дӢ�� 3�����ּӴ�СдӢ�� 4�����а������� 
	 */
	public CheckCode(int mode,int width,int height,int len){
		this.code=new String(generateCheckCode(mode,len));	
		this.width=width;
		this.height=height;
	}

	public BufferedImage generateCheckCodeImage() throws Exception{

		if(code==null||code.equals("")){
			throw new Exception("��֤�벻��Ϊ��!");
		}
		char[] codeChars=code.toCharArray();

		/* ��������ַ�ͼƬ */
		BufferedImage[] images=new BufferedImage[codeChars.length];

		/* ���ѡ��һ������ */
		//20111104 ��ȡ���������
		String fontName=fonts[getRandomInRange(new int[]{2,2})].getFontName();
		Font font;
		fontSize = this.height - 2;
		if(Math.random()<0.5){
			font=new Font(fontName,Font.BOLD,fontSize);
		}else{
			font=new Font(fontName,Font.BOLD,fontSize);
		}

		/* ��������֤��ʱ��ʹ��ͬһ����ɫ�����ױ�ʶ�����ʶ�� */
		Color fgColor=getColor(fgColorRange);

		for(int i=0;i<codeChars.length;i++){
			/* ���ȴ���һ��height * height ��ͼƬ */
			images[i]=new BufferedImage(height,height,BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d=images[i].createGraphics();

			/*-----ʹ�û��Ƶ�ͼƬ����͸��-----*/
			images[i]=g2d.getDeviceConfiguration().createCompatibleImage(height,height,Transparency.TRANSLUCENT);
			g2d.dispose();
			/*------------------------------*/

			g2d=images[i].createGraphics();
			g2d.setFont(font);
			g2d.setColor(fgColor);

			/* �任���󣬶��ַ��ֱ����scale,rotate,shear�任 */
			AffineTransform affineTransform=new AffineTransform();
			affineTransform.scale(getRandomInRange(scaleRange),getRandomInRange(scaleRange));
			affineTransform.rotate(getRandomInRange(rotateRange),height/2.0,height/2.0);
			affineTransform.shear(getRandomInRange(shearRange),0);
			g2d.setTransform(affineTransform);
			FontRenderContext fontRenderContext=g2d.getFontRenderContext();

			/* ��������ַ�ʱ�Ļ���λ�ã�ʹ�ַ���ͼƬ���봦 */
		  //setFontWidth(g2d.getFontMetrics().charWidth(codeChars[i]));
			int p_x=(int)((height-g2d.getFontMetrics().charWidth(codeChars[i]))/2);
			int p_y=(int)(height/2+font.getLineMetrics(Character.toString(codeChars[i]),fontRenderContext).getAscent()/2);
			g2d.drawString(Character.toString(codeChars[i]),p_x,p_y);
			g2d.dispose();

		}

		return appendImages(images);
	}

	/**
	 * ƴ����֤��ͼƬ
	 * 
	 * @param images
	 * @return
	 */
	private BufferedImage appendImages(BufferedImage[] images){
		BufferedImage bgImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
		Graphics2D g2d=bgImage.createGraphics();
		g2d.setColor(new Color(bgColor));
		g2d.fillRect(0,0,width,height);
		g2d.setColor(Color.BLACK);
		g2d.drawRect(0,0,width,height);

		// //�洢ÿ��ͼƬ�����ҿհ�Ԫ��
		// int[][][] blankNums = new int[images.length][height][2];
		// for(int i = 0; i < blankNums.length ; i ++) {
		// blankNums[i] = calculateBlankNum(images[i]);
		// }
		//         
		// int[] distances = new int[images.length - 1];
		//         
		// int[] tempArray = new int[height];
		// for(int i = 0; i < distances.length; i ++) {
		// for(int j = 0; j < tempArray.length; j ++) {
		// tempArray[j] = blankNums[i][j][1] + blankNums[i + 1][j][0];
		// // System.out.println("��ͼ�ҿհף�" + blankNums[i][j][1]);
		// // System.out.println("��ͼ��հף�" + blankNums[i + 1][j][0]);
		// // System.out.println("------------------------------------");
		// }
		// distances[i] = min(tempArray);
		// System.out.println(distances[i]);
		// }

		// ��ǰ���ڴ����ͼƬ
		int index=0;

		// ������һ��ͼƬʱ��λ��
		int drawX=0;

		if(images!=null&&images.length!=0){

			g2d.drawImage(images[index],drawX,0,images[index].getWidth(),images[0].getHeight(),null);
			drawX+=images[index].getWidth();
			index++;
		}

		while(index<images.length){
			int distance=calculateDistanceBetweenChar2(images[index-1],images[index]);
		//	System.out.println("���룺"+distance);
			g2d.drawImage(images[index],drawX-distance,0,images[index].getWidth(),images[0].getHeight(),null);
			drawX+=images[index].getWidth()-distance;
			index++;
		}

		g2d.dispose();

		return bgImage;
	}

	private int min(int[] array){
		int result=Integer.MAX_VALUE;
		for(int item:array){
			if(item<result){
				result=item;
			}
		}
		return result;
	}

	/**
	 * ����ÿ��ͼƬÿ�е����ҿհ����ظ���. int[row][0]�洢��߿հ����ظ��� int[row][1]�洢�ұ߿հ����ظ���
	 * 
	 * @param image
	 * @return
	 */
	private int[][] calculateBlankNum(BufferedImage image){

		int width=image.getWidth();
		int height=image.getHeight();

		int[][] result=new int[height][2];
		for(int i=0;i<result.length;i++){
			result[i][0]=0;
			result[i][1]=width;
		}

		int[] colorArray=new int[4];

		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				colorArray=image.getRaster().getPixel(j,i,colorArray);
				if(!checkArray(colorArray,new int[]{0,0,0,0})){
					if(result[i][0]==0){
						result[i][0]=j;
					}
					result[i][1]=width-j-1;
				}
			}
		}

		return result;
	}

	/**
	 * �㷨2������ɨ��
	 * 
	 * @param leftImage
	 * @param rightImage
	 * @return
	 */
	private int calculateDistanceBetweenChar2(BufferedImage leftImage,BufferedImage rightImage){

		// ��ͼÿ���Ҳ�հ��ַ������б�
		int[][] left=calculateBlankNum(leftImage);
		// ��ͼÿ�����հ��ַ������б�
		int[][] right=calculateBlankNum(rightImage);

		int[] tempArray=new int[leftImage.getHeight()];
		for(int i=0;i<left.length;i++){
			if(right[i][0]==0){
				tempArray[i]=left[i][1]+leftImage.getWidth();
			}else{
				tempArray[i]=left[i][1]+right[i][0];
			}
		}

		return min(tempArray);
	}

	/**
	 * ������������ͼƬ���ַ�����ײ���
	 * 
	 * �㷨������ɨ������ͼƬ��������ͼ���Ҳ࿪ʼɨ�裬��ͼ����࿪ʼɨ��
	 * 
	 * ���Ӷ�Ӧ����2n^2
	 * 
	 * @param bufferedImage
	 * @param bufferedImage2
	 * @return
	 */
	private int calculateDistanceBetweenChar(BufferedImage leftImage,BufferedImage rightImage){
		// ɨ�����
		int times=1;

		int width=leftImage.getWidth();
		int height=leftImage.getHeight();

		// �洢����ͼÿ��ɨ��ʱÿ������
		int[][] lColumn=new int[width][4];
		int[][] rColumn=new int[width][4];

		for(int i=0;i<width*2;i++){
			for(int j=0;j<times;j++){

				for(int k=0;k<height;k++){

					if(width-j-1<=0||times-j>=width){
						System.out.println("�������뿴����");
						return times;
					}

					lColumn[k]=leftImage.getRaster().getPixel(width-j-1,k,lColumn[k]);
					rColumn[k]=rightImage.getRaster().getPixel(times-j-1,k,rColumn[k]);

				}

				if(checkColum(lColumn,rColumn)){
					System.out.println(times);
					return times;
				}else{
					continue;
				}
			}

			times++;

		}

		return 0;
	}

	/**
	 * ������������Ƿ���ȫ��ͬ
	 * 
	 * @param leftColumn
	 * @param rightColumn
	 * @return
	 */
	private boolean checkColum(int[][] leftColumn,int[][] rightColumn){
		for(int i=0;i<leftColumn.length;i++){
			if(checkArray(leftColumn[i],rightColumn[i])&&!checkArray(leftColumn[i],new int[]{0,0,0,0})&&!checkArray(rightColumn[i],new int[]{0,0,0,0})){
				return true;
			}
		}
		return false;
	}

	/*
	 * private int calculateDistanceBetwddeenChar(BufferedImage leftImage,
	 * BufferedImage rightImage) {
	 * 
	 * int distance = 0;
	 *  // �ֱ��ʶ����ͼ��ǰɨ�����Ƿ�ȫ��Ϊ�� boolean isLeftBlank = true; boolean isRightBlank =
	 * true;
	 *  // ����ͼ���ص����ɫֵ int[] lColorArray = new int[4]; int[] rColorArray = new
	 * int[4];
	 *  // ����ͼ��ǰɨ���� int lColumn = leftImage.getWidth() - 1; int rColumn = 0;
	 * 
	 * while (true) { for (int j = 0; j < leftImage.getHeight(); j++) {
	 * 
	 * if (lColumn == 0 || rColumn == leftImage.getWidth() - 1) {
	 * System.out.println(leftImage.getWidth() - fontWidth); return
	 * leftImage.getWidth() - fontWidth; }
	 * 
	 * lColorArray = leftImage.getRaster().getPixel(lColumn, j, lColorArray);
	 * rColorArray = rightImage.getRaster().getPixel(rColumn, j, rColorArray);
	 *  // �������ͼ��ǰɨ�������Ϊͬһ���� if (checkArray(lColorArray, rColorArray)) { //
	 * ��������Ҷ����ڱ���ɫ if (checkArray(lColorArray, new int[] { 0, 0, 0, 0 })) {
	 * isLeftBlank = true; isRightBlank = true; continue; } // ��������Ҷ�����ǰ��ɫ else if
	 * (!checkArray(lColorArray, new int[] { 0, 0, 0, 0 })) { //
	 * System.out.println(distance); return distance; } else {
	 * System.out.println("��Ҳ����1"); } } // �������ͼ��ǰɨ������ز���� else { // ��������ز��Ǳ���ɫ if
	 * (!checkArray(lColorArray, new int[] { 0, 0, 0, 0 })) { isLeftBlank = false; } //
	 * ��������ز��Ǳ���ɫ else if (!checkArray(rColorArray, new int[] { 0, 0, 0, 0 })) {
	 * isRightBlank = false; } else { System.out.println("��������2"); } } } if
	 * (isLeftBlank && isRightBlank) { lColumn--; rColumn++; distance += 2; } else
	 * if (isLeftBlank && !isRightBlank) { lColumn--; distance++; } else if
	 * (!isLeftBlank && isRightBlank) { rColumn++; distance++; } else if
	 * (!isLeftBlank && !isRightBlank) { rColumn++; distance++; } else {
	 * System.out.println("��������3"); } isLeftBlank = isRightBlank = true; } }
	 */

	/**
	 * �ж����������Ƿ���ȣ�Ҫ�󳤶���ȣ�ÿ��Ԫ�ص�ֵҲ���
	 * 
	 * @param colorArray
	 * @param colorArray2
	 * @return
	 */
	private boolean checkArray(int[] arrayA,int[] arrayB){
		if(arrayA==null||arrayB==null){
			return false;
		}
		if(arrayA.length!=arrayB.length){
			return false;
		}
		for(int i=0;i<arrayA.length;i++){
			if(arrayA[i]!=arrayB[i]){
				return false;
			}
		}
		return true;
	}

	/**
	 * ����һ����Χ����һ�������ɫ��������ɫ���������ֵΪ255
	 * 
	 * @param colorRange
	 * @return
	 */
	private Color getColor(int[] colorRange){
		int r=getRandomInRange(colorRange);
		int g=getRandomInRange(colorRange);
		int b=getRandomInRange(colorRange);
		//return new Color(r,g,b);
		//20111104 jianghailong Ӧ������Ҫ�����Ϊ��һ��ɫ
		return new Color(0,0,0);
	}

	/**
	 * ����һ���ڲ���ָ����Χ�ڵ������
	 * 
	 * @param range
	 * @return
	 */
	private int getRandomInRange(int[] range){
		if(range==null||range.length!=2){
			throw new RuntimeException("��Χ�������Ϸ��������������Ԫ��");
		}
		return (int)(Math.random()*(range[1]-range[0])+range[0]);
	}

	/**
	 * ����һ���ڲ���ָ����Χ�ڵ������
	 * 
	 * @param range
	 * @return
	 */
	private float getRandomInRange(float[] range){
		if(range==null||range.length!=2){
			throw new RuntimeException("��Χ�������Ϸ��������������Ԫ��");
		}
		return (float)(Math.random()*(range[1]-range[0])+range[0]);
	}

	/**
	 * ����һ���ڲ���ָ����Χ�ڵ������
	 * 
	 * @param range
	 * @return
	 */
	private double getRandomInRange(double[] range){
		if(range==null||range.length!=2){
			throw new RuntimeException("��Χ�������Ϸ��������������Ԫ��");
		}
		return Math.random()*(range[1]-range[0])+range[0];
	}

	/**
	 * ģ������ڿ���̨���ͼƬ�ϵ��ַ�
	 * 
	 * @param image
	 */
	private void printImage(BufferedImage image){
		int[] temp=new int[4];
		for(int i=0;i<height;i++){
			for(int j=0;j<height;j++){
				if(image.getRaster().getPixel(j,i,temp)[0]==0){
					System.out.print("- ");
				}else{
					System.out.print("�� ");
				}
			}
			System.out.println("");
		}
	}
	public String getCode(){
		return code;
	}

}

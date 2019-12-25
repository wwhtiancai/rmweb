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
 
	/* 系统所有可用的字体 */
	private static Font[] fonts;

	static{
		fonts=new Font[]{new Font("ITALIC",Font.BOLD,32),new Font("黑体",Font.BOLD,32),new Font("宋体",Font.BOLD,32)};
	}
	private int fontSize=30;

	/* 验证码的背景色，采用一个int型表示， 这个值的 16-23 位表示红色分量，8-15 位表示绿色分量，0-7 位表示蓝色分量 */
	private int bgColor=0xFFFFFF;

	/* 验证码前景色的各个分量范围, 大小为0-255 ，由小到大排列，包括R,G,B三个值 */
	private int[] fgColorRange=new int[]{0,150};

	/* 对验证码各个字符进行变换时的缩放因子范围，包括x方向和y方向 */
	private double[] scaleRange=new double[]{0.8,1};//05.1

	/* 验证码各个字符进行变换时的切变因子范围，包括x方向和y方向 */
	private double[] shearRange=new double[]{0,0};

	/* 验证码各个字符进行变换时的旋转因子范围，单位为弧度 */
	/*20111031 根据许主任要求 调整旋转角度大小，由PI/3调整为PI/6*/
	private double[] rotateRange=new double[]{-Math.PI/6,Math.PI/6};

	private int fontWidth;
  
	/*获取可用随机字符*/
	private char[] generateCheckCode(int mode,int len){
		// 定义验证码的字符表
		String chars="123456789ABCDEFGHJKLPQRSTUVXYabcdefghijklmnpqrstuvwxyz江武邹徐陈舞动";
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
	 * mode 1:数字 2：数字加大写英文 3：数字加大小写英文 4：所有包含中文 
	 */
	public CheckCode(int mode,int width,int height,int len){
		this.code=new String(generateCheckCode(mode,len));	
		this.width=width;
		this.height=height;
	}

	public BufferedImage generateCheckCodeImage() throws Exception{

		if(code==null||code.equals("")){
			throw new Exception("验证码不能为空!");
		}
		char[] codeChars=code.toCharArray();

		/* 待处理的字符图片 */
		BufferedImage[] images=new BufferedImage[codeChars.length];

		/* 随机选择一种字体 */
		//20111104 先取消随机字体
		String fontName=fonts[getRandomInRange(new int[]{2,2})].getFontName();
		Font font;
		fontSize = this.height - 2;
		if(Math.random()<0.5){
			font=new Font(fontName,Font.BOLD,fontSize);
		}else{
			font=new Font(fontName,Font.BOLD,fontSize);
		}

		/* 在生成验证码时，使用同一种颜色更不易被识别软件识别 */
		Color fgColor=getColor(fgColorRange);

		for(int i=0;i<codeChars.length;i++){
			/* 首先创建一个height * height 的图片 */
			images[i]=new BufferedImage(height,height,BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d=images[i].createGraphics();

			/*-----使得绘制的图片背景透明-----*/
			images[i]=g2d.getDeviceConfiguration().createCompatibleImage(height,height,Transparency.TRANSLUCENT);
			g2d.dispose();
			/*------------------------------*/

			g2d=images[i].createGraphics();
			g2d.setFont(font);
			g2d.setColor(fgColor);

			/* 变换矩阵，对字符分别进行scale,rotate,shear变换 */
			AffineTransform affineTransform=new AffineTransform();
			affineTransform.scale(getRandomInRange(scaleRange),getRandomInRange(scaleRange));
			affineTransform.rotate(getRandomInRange(rotateRange),height/2.0,height/2.0);
			affineTransform.shear(getRandomInRange(shearRange),0);
			g2d.setTransform(affineTransform);
			FontRenderContext fontRenderContext=g2d.getFontRenderContext();

			/* 计算绘制字符时的基线位置，使字符在图片中央处 */
		  //setFontWidth(g2d.getFontMetrics().charWidth(codeChars[i]));
			int p_x=(int)((height-g2d.getFontMetrics().charWidth(codeChars[i]))/2);
			int p_y=(int)(height/2+font.getLineMetrics(Character.toString(codeChars[i]),fontRenderContext).getAscent()/2);
			g2d.drawString(Character.toString(codeChars[i]),p_x,p_y);
			g2d.dispose();

		}

		return appendImages(images);
	}

	/**
	 * 拼接验证码图片
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

		// //存储每个图片的左右空白元素
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
		// // System.out.println("左图右空白：" + blankNums[i][j][1]);
		// // System.out.println("左图左空白：" + blankNums[i + 1][j][0]);
		// // System.out.println("------------------------------------");
		// }
		// distances[i] = min(tempArray);
		// System.out.println(distances[i]);
		// }

		// 当前正在处理的图片
		int index=0;

		// 绘制下一个图片时的位置
		int drawX=0;

		if(images!=null&&images.length!=0){

			g2d.drawImage(images[index],drawX,0,images[index].getWidth(),images[0].getHeight(),null);
			drawX+=images[index].getWidth();
			index++;
		}

		while(index<images.length){
			int distance=calculateDistanceBetweenChar2(images[index-1],images[index]);
		//	System.out.println("距离："+distance);
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
	 * 计算每个图片每行的左右空白像素个数. int[row][0]存储左边空白像素个数 int[row][1]存储右边空白像素个数
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
	 * 算法2：按行扫描
	 * 
	 * @param leftImage
	 * @param rightImage
	 * @return
	 */
	private int calculateDistanceBetweenChar2(BufferedImage leftImage,BufferedImage rightImage){

		// 左图每行右侧空白字符个数列表
		int[][] left=calculateBlankNum(leftImage);
		// 右图每行左侧空白字符个数列表
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
	 * 计算两个相邻图片上字符的碰撞间距
	 * 
	 * 算法：按列扫描两个图片，其中左图从右侧开始扫描，右图从左侧开始扫描
	 * 
	 * 复杂度应该在2n^2
	 * 
	 * @param bufferedImage
	 * @param bufferedImage2
	 * @return
	 */
	private int calculateDistanceBetweenChar(BufferedImage leftImage,BufferedImage rightImage){
		// 扫描次数
		int times=1;

		int width=leftImage.getWidth();
		int height=leftImage.getHeight();

		// 存储左右图每次扫描时每列数据
		int[][] lColumn=new int[width][4];
		int[][] rColumn=new int[width][4];

		for(int i=0;i<width*2;i++){
			for(int j=0;j<times;j++){

				for(int k=0;k<height;k++){

					if(width-j-1<=0||times-j>=width){
						System.out.println("不是我想看到的");
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
	 * 检查两列数据是否完全相同
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
	 *  // 分别标识左右图当前扫描列是否全部为空 boolean isLeftBlank = true; boolean isRightBlank =
	 * true;
	 *  // 左右图像素点的颜色值 int[] lColorArray = new int[4]; int[] rColorArray = new
	 * int[4];
	 *  // 左右图当前扫描列 int lColumn = leftImage.getWidth() - 1; int rColumn = 0;
	 * 
	 * while (true) { for (int j = 0; j < leftImage.getHeight(); j++) {
	 * 
	 * if (lColumn == 0 || rColumn == leftImage.getWidth() - 1) {
	 * System.out.println(leftImage.getWidth() - fontWidth); return
	 * leftImage.getWidth() - fontWidth; }
	 * 
	 * lColorArray = leftImage.getRaster().getPixel(lColumn, j, lColorArray);
	 * rColorArray = rightImage.getRaster().getPixel(rColumn, j, rColorArray);
	 *  // 如果左右图当前扫描的像素为同一像素 if (checkArray(lColorArray, rColorArray)) { //
	 * 像素相等且都等于背景色 if (checkArray(lColorArray, new int[] { 0, 0, 0, 0 })) {
	 * isLeftBlank = true; isRightBlank = true; continue; } // 像素相等且都等于前景色 else if
	 * (!checkArray(lColorArray, new int[] { 0, 0, 0, 0 })) { //
	 * System.out.println(distance); return distance; } else {
	 * System.out.println("我也意外1"); } } // 如果左右图当前扫描的像素不相等 else { // 如果左像素不是背景色 if
	 * (!checkArray(lColorArray, new int[] { 0, 0, 0, 0 })) { isLeftBlank = false; } //
	 * 如果右像素不是背景色 else if (!checkArray(rColorArray, new int[] { 0, 0, 0, 0 })) {
	 * isRightBlank = false; } else { System.out.println("我是意外2"); } } } if
	 * (isLeftBlank && isRightBlank) { lColumn--; rColumn++; distance += 2; } else
	 * if (isLeftBlank && !isRightBlank) { lColumn--; distance++; } else if
	 * (!isLeftBlank && isRightBlank) { rColumn++; distance++; } else if
	 * (!isLeftBlank && !isRightBlank) { rColumn++; distance++; } else {
	 * System.out.println("我是意外3"); } isLeftBlank = isRightBlank = true; } }
	 */

	/**
	 * 判断两个数组是否相等，要求长度相等，每个元素的值也相等
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
	 * 根据一个范围生成一个随机颜色，其中颜色分量的最大值为255
	 * 
	 * @param colorRange
	 * @return
	 */
	private Color getColor(int[] colorRange){
		int r=getRandomInRange(colorRange);
		int g=getRandomInRange(colorRange);
		int b=getRandomInRange(colorRange);
		//return new Color(r,g,b);
		//20111104 jianghailong 应许主任要求调整为单一黑色
		return new Color(0,0,0);
	}

	/**
	 * 返回一个在参数指定范围内的随机数
	 * 
	 * @param range
	 * @return
	 */
	private int getRandomInRange(int[] range){
		if(range==null||range.length!=2){
			throw new RuntimeException("范围参数不合法，必须包含两个元素");
		}
		return (int)(Math.random()*(range[1]-range[0])+range[0]);
	}

	/**
	 * 返回一个在参数指定范围内的随机数
	 * 
	 * @param range
	 * @return
	 */
	private float getRandomInRange(float[] range){
		if(range==null||range.length!=2){
			throw new RuntimeException("范围参数不合法，必须包含两个元素");
		}
		return (float)(Math.random()*(range[1]-range[0])+range[0]);
	}

	/**
	 * 返回一个在参数指定范围内的随机数
	 * 
	 * @param range
	 * @return
	 */
	private double getRandomInRange(double[] range){
		if(range==null||range.length!=2){
			throw new RuntimeException("范围参数不合法，必须包含两个元素");
		}
		return Math.random()*(range[1]-range[0])+range[0];
	}

	/**
	 * 模拟点阵在控制台输出图片上的字符
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
					System.out.print("■ ");
				}
			}
			System.out.println("");
		}
	}
	public String getCode(){
		return code;
	}

}

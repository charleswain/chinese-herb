package com.cnblogs.jbelial.Validation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.* ; 
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class ValidationCode extends HttpServlet
{
//	图形验证码的字符集合，系统通过随机从这些字符串中选择一些字符作为验证码
	private static String codeChars = 
		"0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTYVWXYZ" ; 
//	返回一个随机颜色
	private static Color getRandomColor(int minColor , int maxColor)
	{
		Random random = new Random() ;
		if (minColor > 255)
			minColor = 255 ; 
		if (maxColor > 255)
			maxColor = 255 ; 
//		获取颜色的随机值
		int red = minColor + random.nextInt(maxColor - minColor) ; 
		int green = minColor + random.nextInt(maxColor - minColor) ; 
		int blue = minColor + random.nextInt(maxColor - minColor) ; 
		return new Color(red,green,blue) ;  
	}
	public void doGet (HttpServletRequest request ,
			HttpServletResponse response) throws IOException
	{
		
		
//   	获取验证码集合的长度。
		int charsLength = codeChars.length() ; 
		
		response.setHeader("ragma", "No-cache") ; 
		response.setHeader("Cache-Control", "no-cache") ;
		response.setDateHeader("Expires", 0) ; 
		response.setContentType("image/jpeg");
		
//		设置图形验证码的长和宽度
		int width = 90 ;
		int height = 20 ;
//		建立图形缓冲区
		BufferedImage image = new BufferedImage(width , height,
				BufferedImage.TYPE_INT_RGB) ; 
//		获取用于输出文字的Graphics对象
		Graphics graphics = image.getGraphics() ; 
		
		Random random = new Random() ; 
		
//		设置要填充的颜色
		graphics.setColor(getRandomColor(180 , 250)) ; 
//		填充图形背景
		graphics.fillRect(0, 0, width, height) ; 
//		设置初始字体和颜色
		graphics.setFont(new Font("Time New Roman" , Font.ITALIC, height)) ; 
		graphics.setColor(getRandomColor(120,180)) ; 
		
//		保存验证码
		StringBuilder validationCode = new StringBuilder() ; 
//		验证码的随机字体
		String[] fontNames = {"Times New Roman" , "Book antiqua" , "Arial" } ;
//		随机生成验证码
		for (int i = 0 ; i < 4 ; ++ i)
		{
//			设置当前验证码字符的字体
			graphics.setFont(new Font(fontNames[random.nextInt(3)] , Font.ITALIC ,
					height)) ; 
//			随机获得验证码的字符
			char codeChar = codeChars.charAt(random.nextInt(charsLength)) ;
			validationCode.append(codeChar) ; 
			graphics.setColor(getRandomColor(20,120)) ; 
//			在图形上输出验证码字符
			graphics.drawString(String.valueOf(codeChar), 16*i+random.nextInt(7), 
					height - random.nextInt(6) ) ; 
		} 
//		获得Session对象，并设置Session对象为3分钟
		HttpSession session = request.getSession(); 
		session.setMaxInactiveInterval(5*60) ; 
//		将验证码放入session对象中.
		session.setAttribute("validationCode",validationCode.toString() ) ; 
		
		
//		关闭graphics对象。
		graphics.dispose() ;  
//		向客户端发送图形验证码
		ImageIO.write(image,"JPEG" ,response.getOutputStream()) ; 
		
	
		
	}
	public void doPost (HttpServletRequest request ,
			HttpServletResponse response) throws IOException
	{
			doGet(request , response) ; 
	}
	
}

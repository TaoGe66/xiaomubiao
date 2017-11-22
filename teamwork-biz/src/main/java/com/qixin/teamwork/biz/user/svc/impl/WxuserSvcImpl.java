package com.qixin.teamwork.biz.user.svc.impl;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.framework.utils.DesUtils;
import org.framework.utils.MatrixToImageWriter;
import org.framework.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.qixin.teamwork.biz.user.dao.WxuserDao;
import com.qixin.teamwork.biz.user.model.User;
import com.qixin.teamwork.biz.user.model.UserOpenid;
import com.qixin.teamwork.biz.user.svc.ValidatedSvc;
import com.qixin.teamwork.biz.user.svc.WxuserSvc;

/**
 * 用户实现层
 * @author zyting
 * @date 2017年6月12日
 * @time 上午10:04:59
 * @version V1.0
 */
@Service("wxuserSvc")
public class WxuserSvcImpl implements WxuserSvc {
	
    @Autowired
    private ValidatedSvc validatedSvc;
    @Autowired
    private WxuserDao wxuserDao;

    /**
     * 手机端关联登录
     * @author zyting
     * @date 2017年6月12日
     * @time 上午10:04:59
     * @version V1.0
     * 1.1 用户已注册但未关联微信
     *      1.1.1 关联手机号
     * 1.2 用户未注册过
     *      1.2.1 创建用户基本信息
     */
	@Override
	public User relevanceWx(User user, String oId) {
		UserOpenid userOpenId = new UserOpenid();
		userOpenId.setId(Long.valueOf(oId));
		UserOpenid userOpenId2 = validatedSvc.getUserOpenid(userOpenId);
		User user2 = getUserInfo(user);
		//1.1 用户已注册但未关联微信
		if(null!=user2){
			if(StrUtils.isEmpty(user2.getOpenId())){
				user2.setOpenId(userOpenId2.getOpenId());
			}
			if(StrUtils.isEmpty(user2.getUserName())){
				user2.setUserName(userOpenId2.getWxNice());
			}
			if(StrUtils.isEmpty(user2.getHeadUrl())){
				user2.setHeadUrl(userOpenId2.getWxLogo());
			}
			//1.1.1 关联手机号
			user2.setUpdateBy(user2.getUserId());
			updateUser(user2);
			user = user2;
		//1.2 用户未注册过
		}else{
			//1.2.1 创建用户基本信息
			user.setHeadUrl(userOpenId2.getWxLogo());
			user.setUserName(userOpenId2.getWxNice());
			user.setOpenId(userOpenId2.getOpenId());
			user.setIsValid((byte)1);
			insert(user);
		}
		return user;
	}

	 /**
     * 正常登录
     * @author zyting
     * @date 2017年6月12日
     * @time 上午10:04:59
     * @version V1.0
     */
	@Override
	public User registerUser(User user) {
		User user2 = getUserInfo(user);
		if(null!=user2){
		//	updateUser(user2);
			return user2;
		}else{
			user.setUserName(user.getTel());
			user.setIsValid((byte)1);
			insert(user);
			return user;
		}
	}

	@Override
	public User getUserInfo(User user) {
		return wxuserDao.getUserInfo(user);
	}

	@Override
	public void updateUser(User user) {
		
		if(user.getHeadUrl()!=null && user.getHeadUrl()!=""){
			 Properties props = new Properties();
			 try {
				props.load(Thread.currentThread().getContextClassLoader()
					         .getResourceAsStream("activity.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			  String path = props.getProperty("teamworkview");
			  String headPath = props.getProperty("uploadImage");
			  DesUtils u = new DesUtils();
			  path = path +"addfriends.html?team_friendId="+u.encrypt(user.getUserId().toString());
			  String qrPath = this.getDimensionImageCode(path, headPath+user.getHeadUrl());
			  user.setQrcodeUrl(qrPath);
		}
		
		wxuserDao.updateUser(user);
	}

	@Override
	public void insert(User user) {
		wxuserDao.insert(user);
		  Properties props = new Properties();
		 try {
			props.load(Thread.currentThread().getContextClassLoader()
				         .getResourceAsStream("activity.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		  String path = props.getProperty("teamworkview");
		  String headPath = props.getProperty("uploadImage");
		  DesUtils u = new DesUtils();
		  path = path +"addfriends.html?team_friendId="+u.encrypt(user.getUserId().toString());
		  String imgPath = user.getHeadUrl()!=null ? headPath+user.getHeadUrl() :  headPath+"img03.png";
		  String qrPath = this.getDimensionImageCode(path, imgPath);
		  
		  User user2 = new User();
		  user2.setUserId(user.getUserId());
		  //二维码存放
		  user2.setQrcodeUrl(qrPath);
		  wxuserDao.updateUser(user2);
	}

	/**
	 * zyting
	 * @param url    生成二维码的内容  
	 * @param imgPath logo路径
	 * @return
	 */
	public String getDimensionImageCode(String url,String imgPath){
         // 生成二维码
         Properties props = new Properties();
        //  path 为二维码 服务器 储存地址
         try {
			props.load(Thread.currentThread().getContextClassLoader()
			         .getResourceAsStream("activity.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
         String path = props.getProperty("ImagePath");

         // 生成图片名称（在此为时间规则生成）
         MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
         Date date = new Date();
         long da = date.getTime();
         String time = Long.toString(da);
         SimpleDateFormat matter1 = new SimpleDateFormat("yyyy");
         SimpleDateFormat matter2 = new SimpleDateFormat("MM");
         String d = matter1.format(date);
         String i = matter2.format(date);
         String s = time + ".jpg";
         String a = path + File.separator+ s;
         File file = new File(a);
       
         //生成图片
         Map hints = new HashMap();
         hints.put(EncodeHintType.MARGIN, 1);
         hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
         BitMatrix bitMatrix = null;
		try {
			bitMatrix = multiFormatWriter.encode(url,
			         BarcodeFormat.QR_CODE, 400, 400, hints);
		} catch (WriterException e) {
			e.printStackTrace();
		}
         
         int width = bitMatrix.getWidth();  
         int height = bitMatrix.getHeight();  
         BufferedImage image = new BufferedImage(width, height,  
                 BufferedImage.TYPE_INT_RGB);  
         for (int x = 0; x < width; x++) {  
             for (int y = 0; y < height; y++) {  
                 image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000  
                         : 0xFFFFFFFF);  
             }  
         }  
	         // 插入图片
	    //image    二维码图片    imgPath  LOGO图片地址 
	
	     File file2 = new File(imgPath);  
	     if (!file2.exists()) {  
	         System.err.println(""+imgPath+"   该文件不存在！");  
	         return s;  
	     }  
	     if (width > 60) {  
	         width = 60;  
	     }  
	     if (height > 60) {  
	         height = 60;  
	     }  
	     Image src = null;
		try {
			src = ImageIO.read(file2);
		} catch (IOException e) {
			e.printStackTrace();
		}  
	     // 插入LOGO  
	     Graphics2D graph = image.createGraphics();  
	     int x = (400 - width) / 2;  
	     graph.drawImage(src, x, x, width, height, null);  
	     Shape shape = new RoundRectangle2D.Float(x, x, width, height, 6, 6);  
	     graph.setStroke(new BasicStroke(3f));  
	     graph.draw(shape);  
	     graph.dispose(); 
	     
	     try {
			MatrixToImageWriter.writeToFileImage(image, "jpg", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	     return s;  
	}

	@Override
	public List<User> getUserAll() {
		// TODO Auto-generated method stub
		return wxuserDao.getUserAll();
	}
	
	
}


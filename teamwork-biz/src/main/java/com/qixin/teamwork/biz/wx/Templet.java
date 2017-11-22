package com.qixin.teamwork.biz.wx;

import java.io.IOException; 
import java.util.ArrayList; 
import java.util.List;
import java.util.Properties;

import com.qixin.payment.utils.other.WeiXinUtil;
import com.qixin.payment.utils.other.wx.HttpKit;
import com.qixin.teamwork.biz.wx.model.Template;
import com.qixin.teamwork.biz.wx.model.TemplateParam;

/**
 * 微信模板工具类
 * @author wuchao
 * @date 2017年6月14日
 * @time 下午4:12:28
 * @version V0.0.1
 */
public class Templet {

	/**
	 * 微信消息模板调用
	 * a 内容   b事件名称  c 通知类型  d 时间   e open_id
	 * @author wuchao
	 * @date 2017年6月14日
	 * @time 下午4:12:06
	 * @version V0.0.1
	 */
	public static void mould(String a,String b,String c,String d,String e){
	          Template tem=new Template(); 
	          //微信消息模板id
	          String templateId = Templet.getProperties("TemplateId");
	          tem.setTemplateId(templateId);
	          tem.setTopColor("#00DD00");
	          //用户微信open_id
	          tem.setToUser(e); 
	          //推送跳转链接
	          String teamIndex = Templet.getProperties("teamIndex");
	          tem.setUrl(teamIndex); 
	              System.out.println(c);
	          //微信消息模板内容
	          List<TemplateParam> paras=new ArrayList<TemplateParam>(); 
	          paras.add(new TemplateParam("first",a,"#FF3333")); 
	          paras.add(new TemplateParam("keyword1",b,"#0044BB")); 
	          paras.add(new TemplateParam("keyword2",c,"#0044BB")); 
	          paras.add(new TemplateParam("remark",d,"#AAAAAA")); 
	                   
	          tem.setTemplateParamList(paras);
	          
	          Templet.sendTemplateMsg(WeiXinUtil.getAccessToken(),tem);
	     }





	     public static void sendTemplateMsg(String token,Template template){ 
	          //消息请求接口
	        String requestUrl="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN"; 
	        requestUrl=requestUrl.replace("ACCESS_TOKEN", token); 
	        //在调用模板消息接口后，会返回JSON数据包
	        String post = HttpKit.post(requestUrl, template.toJSON());
	        System.out.println(post);
	         
	         
	} 
	     //获取properties参数内容
	   	public static String getProperties(String properties_name){
	   		Properties props = new Properties(); 
	   		try {
	   			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("iPay.properties"));
	   		} catch (IOException e) {
	   			e.printStackTrace();
	   		}
	   		String model = props.getProperty(properties_name);
	   		return model;
	   	}
}

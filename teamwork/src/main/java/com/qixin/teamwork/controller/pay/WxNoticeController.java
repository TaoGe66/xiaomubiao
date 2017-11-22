package com.qixin.teamwork.controller.pay;

import java.io.BufferedOutputStream;   
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qixin.payment.utils.other.wx.XMLUtil;
import com.qixin.teamwork.biz.order.svc.OrderPaySvc;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/wxnotice")
public class WxNoticeController {
	@Resource
	public OrderPaySvc orderPaySvc;
	
	@RequestMapping("notice")
	public void payNoticePc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        BufferedReader reader = request.getReader();
        String line = "";
        StringBuffer inputString = new StringBuffer();
        try{
        	while ((line = reader.readLine()) != null) {
    	        inputString.append(line);
    	        }
    	        request.getReader().close();
    	        @SuppressWarnings("unchecked")
				Map<Object, Object> map = XMLUtil.doXMLParse(inputString.toString());
    	        StringBuffer sb = new StringBuffer();
    	        if("SUCCESS".equals(map.get("result_code").toString())){
    	        	// pay_type 1 微信支付
    	        	map.put("pay_type", "1");
    	        	orderPaySvc.payNotice(map);

    	        	sb.append("<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"+"<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ");
    	        }else{
    				sb.append("<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ");
    			}
    	        BufferedOutputStream out = new BufferedOutputStream(
    					response.getOutputStream());
    			out.write(sb.toString().getBytes());
    			out.flush();
    			out.close();
        }catch(Exception e){
        	e.printStackTrace();
        }
	}

}

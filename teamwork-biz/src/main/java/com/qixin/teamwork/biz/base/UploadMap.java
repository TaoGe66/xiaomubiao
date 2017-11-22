package com.qixin.teamwork.biz.base;

import java.io.Serializable; 
import java.util.Map;

import com.qixin.teamwork.biz.user.model.User;



/**
 * 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author xiehuilin 
* @date 2015年12月3日 下午5:03:30 
*
 */
public class UploadMap implements Serializable {
	private static long serialVersionUID = 1L;
	private Map<String,byte[]> fileMap;
	private Map<String,String> parametricMap;
	private User   loginUser;
	private String  errorcode;	//上传文件出现的错误
	private String  errorContent;	//上传文件出现的错误
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public static void setSerialversionuid(long serialversionuid) {
		serialVersionUID = serialversionuid;
	}
	public Map<String, byte[]> getFileMap() {
		return fileMap;
	}
	public void setFileMap(Map<String, byte[]> fileMap) {
		this.fileMap = fileMap;
	}
	public Map<String, String> getParametricMap() {
		return parametricMap;
	}
	public void setParametricMap(Map<String, String> parametricMap) {
		this.parametricMap = parametricMap;
	}
	public User getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}
	public String getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}
	public String getErrorContent() {
		return errorContent;
	}
	public void setErrorContent(String errorContent) {
		this.errorContent = errorContent;
	}
	
}

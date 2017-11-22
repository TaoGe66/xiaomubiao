package com.qixin.teamwork.serlvt;
import java.io.IOException; 
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.framework.utils.CommConstants;
import org.framework.utils.EncodeUtils;
import org.framework.utils.StrUtils;
import org.framework.utils.img.FileUtil;

import com.qixin.teamwork.biz.base.APIErrorMap;
import com.qixin.teamwork.biz.base.UploadMap;


 /**
  * 图片上传
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author xiehuilin 
 * @date 2015年12月3日 下午4:12:44 
 *
  */
public class PostImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		String jsoncallback = request.getParameter("callback");//跨域请求
		String state="1";    //成功  1   失败  0
		String errorCode=""; //错误信息
		//request.setCharacterEncoding("utf-8"); 
		UploadMap uploadMap = getUploadMap(request);
		String errorcode = uploadMap.getErrorcode();
		//String errorcontext = uploadMap.getErrorContent();
		//if (StrUtils.isNotEmpty(errorcode) && StrUtils.isNotEmpty(errorcontext)) {
		if (StrUtils.isNotEmpty(errorcode)) {
			if ("300".equals(errorcode)) {
				// 系统异常
				//printNew(state,errorCode,msg,"4201","", request, response);
				state="0";
				errorCode=APIErrorMap.errorMap.get("11");
				printNew(jsoncallback,state,errorCode,"",  response);
			} else if ("301".equals(errorcode)) {
				// 上传的formdata中的数据超出了规定大小 8M post请求总数据大小
				state="0";
				errorCode=APIErrorMap.errorMap.get("12");
				printNew(jsoncallback,state,errorCode,"", response);
			} else if ("302".equals(errorcode)) {
				// 上传的文件超出了规定大小 1M 单个文件限制
				state="0";
				errorCode=APIErrorMap.errorMap.get("13");
				printNew(jsoncallback,state,errorCode,"", response);
			} 
		} else {
			try {
				Map<String, byte[]> fileMap = uploadMap.getFileMap();
				byte[] filebyte = fileMap.get("file");

				if (null == filebyte) {
					// 获取不到file
					state="0";
					errorCode=APIErrorMap.errorMap.get("14");
					printNew(jsoncallback,state,errorCode,"", response);
					return;
				}
				String fileType = FileUtil.getFileSuffix(filebyte);
				if (StrUtils.isEmpty(fileType)) {
					// 图片类型错误 4206
					state="0";
					errorCode=APIErrorMap.errorMap.get("14");
					printNew(jsoncallback,state,errorCode,"", response);
					return;
				}
				
				if ("|gif|png|jpg|bmp|".indexOf("|" + fileType + "|") < 0) {
					// 图片类型错误 4206
					state="0";
					errorCode=APIErrorMap.errorMap.get("14");
					printNew(jsoncallback,state,errorCode,"", response);
					return;
				}
				// 文件保存路径
				Properties props = new Properties(); 
				props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("activity.properties"));
				String model = props.getProperty("uploadImage");
				//String model="d://testImg";
				//System.out.println(model);
				String fileurl = StrUtils.getFormatNow("yyyyMM/dd/");
				String filepath = model+ fileurl;
				// 100 随机
				int rm = (int) (Math.random() * 10);
				
				// 保存文件名称
				String filename = new StringBuffer("").append(rm).append(System.currentTimeMillis()).append("_").append(rm).append(".").append(fileType).toString();
				FileUtil.byteToFile_info(filebyte, filepath, filename);
				String imgUrl =fileurl + filename;
				printNew(jsoncallback,state,errorCode,imgUrl, response);
			} catch (Exception e) {
				e.printStackTrace();
				state="0";
				errorCode=APIErrorMap.errorMap.get("11");
				printNew(jsoncallback,state,errorCode,"",  response);
				
			}

		}
	}

    private void printNew(String jsoncallback,String state,String errorCode,String saveUrl,  HttpServletResponse response) throws IOException {
    	String outString = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\"></head><body><script>window.parent._richText._uploadOk(\"" + state + "\",\"" + errorCode + "\",\"" + saveUrl + "\");</script></body></html>";
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Cache-Control", "no store");// HTTP 1.1
        response.setHeader("Pragma", "no store");// HTTP 1.0
        response.setDateHeader("Expires", 0);
        response.getWriter().write(outString);
        response.getWriter().flush();
        response.getWriter().close();

    }
	/**
	 * 组织请求所有参数（包括file）
	 * 
	 * @param request
	 * @return UploadMap 组织对象 
	 */
	@SuppressWarnings("unchecked")
	private UploadMap getUploadMap(HttpServletRequest request) {
		// 检查输入请求是否为multipart表单数据。
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		// 若果是的话
		UploadMap um = new UploadMap();
		Map<String, byte[]> fileMap = new HashMap<String, byte[]>();// 所有上传文件
		Map<String, String> parametricMap = new HashMap<String, String>();// 所有正常参数
		if (isMultipart) {
			/** 为该请求创建一个DiskFileItemFactory对象，通过它来解析请求。 **/
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(4096); // 设置缓冲区大小，这里是4kb
			//factory.setRepository(new File(tempPath));// 设置缓冲区目录
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("utf-8");// 解决文件乱码问题
			//upload.setSizeMax(CommConstants.sizePostMzx);// 设置formdata最大尺寸
			//upload.setFileSizeMax(CommConstants.sizeFileMax);
			List<FileItem> items = null;
			// 从request得到 所有 上传域的列表
			int k = 0;
			try {
				items = upload.parseRequest(request);
			} catch (FileUploadException e) {
				//e.printStackTrace();
				if (e instanceof SizeLimitExceededException) {
					// SizeLimitExceededException 上传的formdata总的数据超出了规定大小
					k = 1;
					//um.setErrorcode("301");
					//um.setErrorContent("上传的formdata中的数据超出了规定大小");
					return um;
				} else if (e instanceof FileSizeLimitExceededException) {
					// FileSizeLimitExceededException 上传的文件超出了规定大小
					k = 2;
					//um.setErrorcode("302");
					//um.setErrorContent("上传的文件超出了规定大小");
					//System.out.println("302上传的文件超出了规定大小");
					//um.setParametricMap(parametricMap);
					return um;
				} else {
					//e.printStackTrace();
					k = 3;
					// 其他错误
					um.setErrorcode("300");
					//um.setErrorContent("文件上传出现异常");
					//System.out.println("300文件上传出现异常");
					return um;
				}
			}
			if (k == 0) {
				Iterator<FileItem> itr = items.iterator();// 所有的表单项
				// 组织UploadMap
				while (itr.hasNext()) {
					FileItem item = itr.next();// 循环获得每个表单项
					if (!item.isFormField()) {// 如果是文件类型
						String name = item.getFieldName();// 获得form表单name名称
						String fileName = item.getName(); // 获得文件名
						if (StrUtils.isNotEmpty(name) && StrUtils.isNotEmpty(fileName)) {
							byte[] file = item.get();
							fileMap.put(name, file);
						}
					} else {
						// 如果是普通值
						try {
							String name = item.getFieldName();
							String itemvalue = item.getString();
							String o = StrUtils.getEncoding(itemvalue);
							String value = "";
							if ("ISO-8859-1".equals(o)) {
								value = new String(itemvalue.getBytes("ISO-8859-1"), "UTF-8");
							} else {
								value = EncodeUtils.urlDecode(item.getString());
							}

							parametricMap.put(name, value);
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
							um.setErrorcode("300");
							//um.setErrorContent("文件上传出现异常");
							return um;
						}
					}
				}
			} else {
				return um;
			}
		} else {
			Enumeration<String> keys = request.getParameterNames();
			while (keys.hasMoreElements()) {
				String element = keys.nextElement();
				parametricMap.put(element, EncodeUtils.urlDecode(request.getParameter(element)));
			}
		}
		um.setFileMap(fileMap);
		um.setParametricMap(parametricMap);
		return um;
	}
}

package com.qixin.teamwork.biz.enterprise.svc.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qixin.teamwork.biz.enterprise.dao.EnterpriseDao;
import com.qixin.teamwork.biz.enterprise.model.Enterprise;
import com.qixin.teamwork.biz.enterprise.svc.EnterpriseSvc;
import com.qixin.teamwork.biz.enterpriseMember.dao.MemberDao;
import com.qixin.teamwork.biz.enterpriseMember.model.Member;
import com.qixin.teamwork.biz.enterpriseMember.svc.MemberSvc;
import com.qixin.teamwork.biz.envet.dao.EventDao;
import com.qixin.teamwork.biz.envet.model.Event;
import com.qixin.teamwork.biz.light.dao.LightAuthDao;
import com.qixin.teamwork.biz.light.model.LightAuth;
import com.qixin.teamwork.biz.news.dao.NewsDao;
import com.qixin.teamwork.biz.news.model.News;
import com.qixin.teamwork.biz.notice.model.Notice;
import com.qixin.teamwork.biz.notice.svc.NoticeSvc;

@Service("enterpriseSvc")
public class EnterpriseSvcImpl implements EnterpriseSvc {
	
	@Resource 
	private EnterpriseDao enterpriseDao;
	@Resource 
	private MemberDao memberDao;
	@Resource 
	private NoticeSvc noticeSvc;
	@Resource
	private LightAuthDao authDao;
	@Resource 
	private MemberSvc memberSvc;
	@Resource
    private NewsDao newsDao;
	@Resource
    private EventDao eventDao;
	/**
	  * 1.新增轻企
	  * 2.成员表添加创建人
	  * @author zyting
	  * @date 2017年9月6日
	  * @time 下午4:08:50
	  * @version V0.0.1
	  */
	@Override
	public void insertEnterprise(Enterprise enterprise) {
		//1.新增轻企
		enterpriseDao.insert(enterprise);
		//2.成员表添加创建人
		Member member = new Member();
		member.setLeId(enterprise.getLeId());
		member.setCreateBy(enterprise.getCreateBy());
		member.setUserId(enterprise.getCreateBy());
		member.setLemState((byte)1);
		member.setRoleType((byte)0);
		Properties props = new Properties();
        try {
			props.load(Thread.currentThread().getContextClassLoader()
			        .getResourceAsStream("activity.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = props.getProperty("teamworkview")+"join.html?leId="+member.getLeId()+"&friendId="+member.getUserId();
		member.setQrcodeUrl(memberSvc.getDimensionCode(url));	
		memberDao.insert(member);
		
		Enterprise en= 	enterpriseDao.getEnterpriseInfo(member.getLeId());
		News news = new News();
		news.setLeId(member.getLeId());
		news.setnTitle("轻企名称："+en.getName());
		news.setSendId(member.getUserId());
		news.setCreateBy(en.getCreateBy());
		news.setIsRed((byte)0);
		news.setnTime(new Timestamp(System.currentTimeMillis()));
		news.setnContent("您好，您的"+en.getName()+"轻企创建成功！");
		news.setnType((byte)2);
		newsDao.insert(news);
	}

	 /**
	  * 编辑轻企
	  * @author zyting
	  * @date 2017年9月6日
	  * @time 下午4:08:50
	  * @version V0.0.1
	  */
	@Override
	public void updateEnterprise(Enterprise enterprise) {
		Enterprise e = enterpriseDao.getEnterpriseInfo(enterprise.getLeId());
		enterpriseDao.update(enterprise);
		
		if(!e.getName().equals(enterprise.getName())){
			/*Member member = new Member();
			member.setLeId(enterprise.getLeId());
			member.setLemState((byte)1);
			List<Member> l =memberDao.listMember(member);
			for(Member m :l){
				//添加动态
			}*/
			Notice notice = new Notice();
			notice.setTitle("“"+e.getName()+"”已改名为“"+enterprise.getName()+"”");
			notice.setContent("“"+e.getName()+"”已改名为“"+enterprise.getName()+"”");
			notice.setLeId(enterprise.getLeId());
			notice.setCreateBy(enterprise.getCreateBy());
			noticeSvc.insert(notice);
		}
		
	}

	@Override
	public List<Enterprise> enterpriseMyList(Enterprise enterprise) {
		 List<Enterprise> list = enterpriseDao.enterpriseMyList(enterprise);
		 for(int i =0;i<list.size();i++){
			 LightAuth auth=new LightAuth();
				auth.setLeId(list.get(i).getLeId());
				auth.setIsPay((byte)1);
			  LightAuth lightAuth=authDao.infoAuth(auth);
			  if(lightAuth !=null){
				  list.get(i).setType((byte)1);
			    }
		 }
		
		
		return list;
	}

	/**
	 * 轻企详情
	 * @author zyting
     * @date 2017年9月6日
     * @time 下午4:08:50
	 * @param enterprise
	 * @return
	 */
	@Override
	public Enterprise enterpriseInfo(Enterprise enterprise) {
		
		Enterprise e =	new Enterprise();
			if(enterprise.getUserId()!=null){
				e = enterpriseDao.enterpriseInfo(enterprise);
			}else{
				e =enterpriseDao.getEnterpriseInfo(enterprise.getLeId());
			}
				
		LightAuth auth=new LightAuth();
		auth.setLeId(enterprise.getLeId());
		auth.setIsPay((byte)1);
		LightAuth lightAuth=authDao.infoAuth(auth);
		if(lightAuth !=null){
			e.setType((byte)1);
			Calendar term =Calendar.getInstance();
			 term.setTime(lightAuth.getTerm());
			 term.add(Calendar.MONTH, -1); //月份减一
			 Calendar n = Calendar.getInstance(); //现在时间
			if(term.getTime().compareTo(n.getTime())>0){
				enterprise.setIsTerm((byte)0);
			}else{
				enterprise.setIsTerm((byte)1);
			}
		}
		Member member = new Member();
		member.setLeId(enterprise.getLeId());
		member.setLemState((byte)1);
		e.setMemberCount(memberDao.listGetMeber(member).size());
		
      return e;
	}


	/**
	 * 根据轻企id获取轻企详情
	 * @author xiehuilin
	 * @param  leId
	 * @return com.qixin.teamwork.biz.enterprise.model.Enterprise
	 * @version V0.0.1
	 * @date 2017/9/7 18:21
	 */
	@Override
	public Enterprise  getEnterpriseInfo(Long leId){
		return enterpriseDao.getEnterpriseInfo(leId);
	}

	/**
	   * 查询简称和全程有没有重复
	   * @author zyting
	   * @date 2017年9月13日
	   * @time 下午7:04:57
	   * @version V0.0.3
	   * @param request
	   * @param member
	   * @return
	   */
	@Override
	public List<Enterprise> selectEnterprise(Enterprise enterprise) {
		return enterpriseDao.selectEnterprise(enterprise);
	}

	@Override
	public void updateisEnterpriseDel(Enterprise enterprise) {
		Enterprise en= 	enterpriseDao.getEnterpriseInfo(enterprise.getLeId());
		//轻企置为无效
		enterprise.setIsValid((byte)0);
		enterpriseDao.update(enterprise);
		//项目置为无效
		Event event =new Event();
		eventDao.isDelLeid(event);
		
		Member member = new Member();
		member.setLeId(enterprise.getLeId());
		member.setLemState((byte)1);
	   List<Member> l = memberDao.listGetMeber(member);
		for(Member m :l){
			News news = new News();
			news.setLeId(member.getLeId());
			news.setnTitle("轻企名称："+en.getName());
			news.setSendId(m.getUserId());
			news.setCreateBy(en.getCreateBy());
			news.setIsRed((byte)0);
			news.setnTime(new Timestamp(System.currentTimeMillis()));
			news.setnContent("轻企已经解散！");
			news.setnType((byte)2);
			newsDao.insert(news);
		}
		
		
	}

}

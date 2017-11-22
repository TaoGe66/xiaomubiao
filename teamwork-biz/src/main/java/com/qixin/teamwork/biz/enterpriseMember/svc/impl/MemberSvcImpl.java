package com.qixin.teamwork.biz.enterpriseMember.svc.impl;


import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.framework.utils.MatrixToImageWriter;

import com.qixin.teamwork.biz.enterprise.dao.EnterpriseDao;
import com.qixin.teamwork.biz.enterprise.model.Enterprise;
import com.qixin.teamwork.biz.enterpriseMember.dao.MemberDao;
import com.qixin.teamwork.biz.enterpriseMember.model.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.ibm.icu.text.SimpleDateFormat;
import com.qixin.teamwork.biz.enterpriseMember.svc.MemberSvc;
import com.qixin.teamwork.biz.envet.dao.EventDao;
import com.qixin.teamwork.biz.envet.dao.EventItemDao;
import com.qixin.teamwork.biz.envet.dao.JoinEventDao;
import com.qixin.teamwork.biz.envet.model.Event;
import com.qixin.teamwork.biz.envet.model.EventItem;
import com.qixin.teamwork.biz.envet.model.JoinEvent;
import com.qixin.teamwork.biz.follow.dao.FollowDao;
import com.qixin.teamwork.biz.follow.model.Follow;
import com.qixin.teamwork.biz.light.dao.LightAuthDao;
import com.qixin.teamwork.biz.light.model.LightAuth;
import com.qixin.teamwork.biz.news.dao.NewsDao;
import com.qixin.teamwork.biz.news.model.News;
import com.qixin.teamwork.biz.user.dao.WxuserDao;
import com.qixin.teamwork.biz.user.model.User;

import java.util.List;

@Service("memberSvc")
public class MemberSvcImpl implements MemberSvc {
    
	@Resource
    private MemberDao memberDao;
    @Resource
	private LightAuthDao authDao;
    @Resource
   	private FollowDao followDao;
    @Resource
	private NewsDao newsDao;
    @Resource
   	private EnterpriseDao enterpriseDao;
    @Resource
    private WxuserDao wxuserDao;
    @Resource
    private JoinEventDao joinEventDao;
    @Autowired
	private EventDao eventDao;
    @Resource
	private EventItemDao eventItemDao;
    /**
     * 根据轻企id获取轻企成员列表--待定
     * @author xiehuilin
     * @param
     * @return
     * @version V0.0.1
     * @date 2017/9/8 10:24
     */
    @Override
    public List<Member> listGetMeber(Member member) {

        return memberDao.listGetMeber(member);
    }


	/**
	 * 我的轻企列表
	 * @author wuchao
	 * @date 2017年9月8日
	 * @time 下午3:23:08
	 * @version V0.0.3
	 * @param member
	 * @return
	 */
	@Override
	public List<Member> listMember(Member member) {
		List<Member> mrList=new ArrayList<Member>();
		//我的轻企列表
		List<Member> memberList=memberDao.listMember(member);
		if (memberList.size()>0) {
			for (int i = 0; i < memberList.size(); i++) {
				Member mr=memberList.get(i);
				mr.setIs_enter(1);
				LightAuth auth=new LightAuth();
				auth.setLeId(mr.getLeId());
				auth.setIsPay((byte)1);
				//轻企列表认证信息 已支付
				LightAuth lightAuth=authDao.infoAuth(auth);
				if ((lightAuth!=null && lightAuth.getTerm().getTime()<new Date().getTime())|| mr.getLemState()==2 ||mr.getLemState()==0) {
					// 是否有权限进入  0 否  1是
					mr.setIs_enter(0);
				}
				//认证状态: 0 不通过 、1 通过
				Byte authState=0;
				if (lightAuth!=null && lightAuth.getState()==1) {
					authState=1;
				}
				mr.setAuthState(authState);
				mrList.add(mr);
			}
		}
		 return mrList;
	}
    /**
     * 生成二维码
     * @param url   网址
     * @param imgPath  logo
     * @return
     */
	@Override
    public String getDimensionCode(String url) {
        String imgUrl = "";
        try {
            // 生成二维码
            Properties props = new Properties();
           //  path 为二维码 服务器 储存地址
            props.load(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("activity.properties"));
            String path = props.getProperty("ImagePath");
         //   String path ="C:/aaaaaa";
            // 生成图片名称（在此为时间规则生成）
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            Date date = new Date();
            long da = date.getTime();
            String time = Long.toString(da);
            SimpleDateFormat matter1 = new SimpleDateFormat("yyyy");
            SimpleDateFormat matter2 = new SimpleDateFormat("MM");
            String d = matter1.format(date);
            String i = matter2.format(date);
            imgUrl = time + ".jpg";
            String a = path + File.separator+ imgUrl;
            File file = new File(a);

            //生成图片
            Map hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = multiFormatWriter.encode(url,
                    BarcodeFormat.QR_CODE, 400, 400, hints);
            MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgUrl;

    }

    
    /**
	 * 是否能删除成员
	 * @author zyting
	 * @date 2017年9月8日
	 * @time 下午3:23:08
	 * @version V0.0.3
	 * @param member
	 * @return  leId  userId
	 */
	@Override
	public boolean isMemberDel(Member member) {
		boolean isrel = false;
		if(memberDao.isMemberDel(member).size()==0){
			isrel = true;
		}
		return isrel;
	}


	/**
   	 * 删除成员
   	 * @author zyting
   	 * @date 2017年9月8日
   	 * @time 下午3:23:08
   	 * @version V0.0.3
   	 * @param member
   	 * @return
   	 */
	@Override
	public void updateMemberDel(Member member) {
		member.setIsValid((byte)0);
		memberDao.updateMemberDel(member);
		Follow follow = new Follow();
		follow.setIsValid((byte)0);
		follow.setLeId(member.getLeId());
		follow.setUserId(member.getUserId());
		followDao.updateDel(follow);
		//将未完成的目标异常终止
		JoinEvent joinEvent=new JoinEvent();
		joinEvent.setUserId(member.getUserId());
		joinEvent.setLeId(member.getLeId());
		List<JoinEvent> joinList=joinEventDao.listDignity(joinEvent);
		if (joinList.size()>0) {
			for (int i = 0; i < joinList.size(); i++) {
				Event event=new Event();
				event.seteId(joinList.get(i).geteId());
				event.setState((byte)6);
				eventDao.updateEnvet(event);
			}
		}
		joinEvent.setType((byte)2);
		List<JoinEvent> jeList=joinEventDao.listItemDignity(joinEvent);
		if (jeList.size()>0) {
			for (int i = 0; i < jeList.size(); i++) {
				EventItem eventItem=new EventItem();
				eventItem.setEiId(joinList.get(i).getEiId());
				eventItem.setState((byte)5);
				eventItemDao.updateEventItem(eventItem);
			}
		}
		joinEvent.setType(null);
		List<JoinEvent> joeList=joinEventDao.listItemDignity(joinEvent);
		if (joeList.size()>0) {
			for (int i = 0; i < joeList.size(); i++) {
				JoinEvent je=new JoinEvent();
				je.setId(joeList.get(i).getId());
				je.setIsValid((byte)0);
				joinEventDao.updateJoinEvent(je);
			}
		}
		//系统消息
        Enterprise en= 	enterpriseDao.getEnterpriseInfo(member.getLeId());
        User user = new User();
		user.setUserId(member.getUserId());
		User use = wxuserDao.getUserInfo(user);
		News news = new News();
		news.setLeId(member.getLeId());
		news.setnTitle("轻企名称："+en.getName());
		news.setSendId(member.getUserId());
		news.setCreateBy(en.getCreateBy());
		news.setIsRed((byte)0);
		news.setnTime(new Timestamp(System.currentTimeMillis()));
		news.setnContent("您已被管理员移出轻企");
		news.setnType((byte)2);
		newsDao.insert(news);
		Member me=new Member();
		me.setLeId(member.getLeId());
		List<Member> mList=memberDao.listGetMeber(me);
		for (int i = 0; i < mList.size(); i++) {
			Member m=mList.get(i);
			News ne = new News();
			ne.setLeId(member.getLeId());
			ne.setnTitle("轻企名称："+en.getName());
			ne.setSendId(m.getUserId());
			ne.setCreateBy(en.getCreateBy());
			ne.setIsRed((byte)0);
			ne.setnTime(new Timestamp(System.currentTimeMillis()));
			ne.setnContent(use.getUserName()+"已被管理员移出轻企");
			ne.setnType((byte)2);
			newsDao.insert(ne);
		}
	}


	@Override
	public void updateMember(Member member) {
		Enterprise en= 	enterpriseDao.getEnterpriseInfo(member.getLeId());
		News news = new News();
		news.setLeId(member.getLeId());
		news.setnTitle("轻企名称："+en.getName());
		news.setSendId(member.getUserId());
		news.setCreateBy(en.getCreateBy());
		news.setIsRed((byte)0);
		news.setnTime(new Timestamp(System.currentTimeMillis()));
		news.setnType((byte)2);
		if(member.getLemState()==1){
            Properties props = new Properties();
            try {
				props.load(Thread.currentThread().getContextClassLoader()
				        .getResourceAsStream("activity.properties"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String url = props.getProperty("teamworkview")+"join.html?leId="+member.getLeId()+"&friendId="+member.getUserId();
			String u =getDimensionCode(url);
			member.setQrcodeUrl(u);	
			news.setnContent("您好，您已加入"+en.getName());
		}else{
			news.setnContent("抱歉，管理员拒绝了您的加入请求");
		}
		memberDao.updateMember(member);
		news.setnType((byte)2);
		newsDao.insert(news);
	}


	/**
	 * 轻企成员信息
	 * @author wuchao
	 * @date 2017年9月20日
	 * @time 上午10:28:52
	 * @version V0.0.3
	 * @param member
	 * @return
	 */
	@Override
	public Member infoMember(Member member) {
		return memberDao.infoMember(member);
	}


	@Override
	public void insert(Member member) {
		memberDao.insert(member);
		User user = new User();
		user.setUserId(member.getUserId());
		user =wxuserDao.getUserInfo(user);
        Enterprise en= 	enterpriseDao.getEnterpriseInfo(member.getLeId());
		
		News news = new News();
		news.setLeId(member.getLeId());
		news.setnTitle("轻企名称："+en.getName());
		news.setSendId(en.getCreateBy());
		news.setCreateBy(member.getUserId());
		news.setIsRed((byte)0);
		news.setnTime(new Timestamp(System.currentTimeMillis()));
		news.setnContent(user.getUserName()+"请求加入！");
		news.setnType((byte)2);
		newsDao.insert(news);
		
	}


	@Override
	public void updateMemberOne(Member member) {
		Enterprise en= 	enterpriseDao.getEnterpriseInfo(member.getLeId());
		
		memberDao.updateMember(member);
		News news = new News();
		news.setLeId(member.getLeId());
		news.setnTitle("轻企名称："+en.getName());
		news.setSendId(member.getUserId());
		news.setCreateBy(en.getCreateBy());
		news.setIsRed((byte)0);
		news.setnTime(new Timestamp(System.currentTimeMillis()));
		news.setnContent("您已经不是"+en.getName()+"管理员，感谢您的付出！");
		news.setnType((byte)2);
		newsDao.insert(news);
	}

	@Override
	public List<Member> listGetMemberExceptMy(Member member) {
		return memberDao.listGetMemberExceptMy(member);
	}


	/**
	 * 修改轻企成员身份
	 * @author wuchao
	 * @date 2017年9月28日
	 * @time 下午4:22:51
	 * @version V0.0.3
	 * @param member
	 */
	@Override
	public void updateMemberRole(Member member) {
		Enterprise en= 	enterpriseDao.getEnterpriseInfo(member.getLeId());
		News news = new News();
		news.setLeId(member.getLeId());
		news.setnTitle("轻企名称："+en.getName());
		news.setSendId(member.getUserId());
		news.setCreateBy(en.getCreateBy());
		news.setnType((byte)2);
		news.setIsRed((byte)0);
		news.setCreateBy(en.getCreateBy());
		news.setnTime(new Timestamp(new Date().getTime()));
		news.setnContent("您已经成为"+en.getName()+"管理员，感谢一直有你！");
		newsDao.insert(news);
		memberDao.updateMember(member);
	}
}

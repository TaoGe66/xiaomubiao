package com.qixin.teamwork.biz.enterpriseMember.svc;


import com.qixin.teamwork.biz.enterpriseMember.model.Member;

import java.util.List;

/**
 * 轻企人员模板接口
 * @author zyting
 * @date 2017年9月6日
 * @time 下午4:03:23
 * @version V0.0.1
 */
public interface MemberSvc {
    /**
    * 根据轻企id获取轻企成员列表
    * @author xiehuilin
    * @param
    * @return
    * @version V0.0.1
    * @date 2017/9/8 10:24
    */
    List<Member> listGetMeber(Member member);
	
	/**
	 * 我的轻企列表
	 * @author wuchao
	 * @date 2017年9月8日
	 * @time 下午3:23:08
	 * @version V0.0.3
	 * @param member
	 * @return
	 */
    List<Member> listMember(Member member);
    
    /**
	 * 是否能删除成员
	 * @author zyting
	 * @date 2017年9月8日
	 * @time 下午3:23:08
	 * @version V0.0.3
	 * @param member
	 * @return
	 */
    boolean isMemberDel(Member member);
    
    /**
   	 * 删除成员
   	 * @author zyting
   	 * @date 2017年9月8日
   	 * @time 下午3:23:08
   	 * @version V0.0.3
   	 * @param member
   	 * @return
   	 */
    void updateMemberDel(Member member);
    
    /**
   	 * 添加成员
   	 * @author zyting
   	 * @date 2017年9月8日
   	 * @time 下午3:23:08
   	 * @version V0.0.3
   	 * @param member
   	 * @return
   	 */
    void updateMember(Member member);

    /**
   	 * 取消管理员
   	 * @author zyting
   	 * @date 2017年9月8日
   	 * @time 下午3:23:08
   	 * @version V0.0.3
   	 * @param member
   	 * @return
   	 */
    void updateMemberOne(Member member);
    /**
   	 * 二维码
   	 * @author zyting
   	 * @date 2017年9月8日
   	 * @time 下午3:23:08
   	 * @version V0.0.3
   	 * @param member
   	 * @return
   	 */
    String getDimensionCode(String url);

    
    /**
     * 轻企成员信息
     * @author wuchao
     * @date 2017年9月20日
     * @time 上午10:27:42
     * @version V0.0.3
     * @param member
     * @return
     */
    public Member infoMember(Member member);
    
    /**
     * 添加轻企成员
     * @author zyting
     * @date 2017年9月20日
     * @time 上午10:27:42
     * @version V0.0.3
     * @param member
     * @return
     */
    void insert(Member member);

	/**
	 * 根据轻企id获取轻企成员不包含自己
	 * @author xiehuilin
	 * @param
	 * @return
	 * @version V0.0.1
	 * @date 2017/9/8 10:24
	 */
	List<Member> listGetMemberExceptMy(Member member);

	/**
	 * 修改轻企成员身份
	 * @author wuchao
	 * @date 2017年9月28日
	 * @time 下午4:22:51
	 * @version V0.0.3
	 * @param member
	 */
	void updateMemberRole(Member member);
    
}

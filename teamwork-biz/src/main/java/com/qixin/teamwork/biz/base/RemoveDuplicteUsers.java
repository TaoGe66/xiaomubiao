package com.qixin.teamwork.biz.base;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.qixin.teamwork.biz.user.model.User;

public class RemoveDuplicteUsers implements Comparator<User> {
	 public static  List<User> removeDuplicteUsers(List<User> users){
	      Set<User> s= new TreeSet<User>(new Comparator<User>(){

	           @Override
	           public int compare(User o1, User o2) {
	                return o1.getUserId().compareTo(o2.getUserId());
	           }

	      });

	      s.addAll(users);
	      return new ArrayList<User>(s);
	 }

	@Override
	public int compare(User o1, User o2) {
		// TODO Auto-generated method stub
		return 0;
	}
}

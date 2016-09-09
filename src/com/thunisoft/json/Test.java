package com.thunisoft.json;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.thunisoft.json.bean.Group;
import com.thunisoft.json.bean.User;


public class Test {

	public static void main(String[] args) {
		
		User devUser = new User();
		devUser.setUserAge(1);
		devUser.setUserID(20);
		devUser.setUserName("devUser");
		
		User testUser = new User();
		testUser.setUserAge(2);
		testUser.setUserAge(21);
		testUser.setUserName("testUser");
		
		Group group = new Group();
		group.setGroupID(1);
		group.setGroupName("group");
		group.addUser(devUser);
		group.addUser(testUser);
		
		String jsonString = JSON.toJSONString(group);
		System.out.println(jsonString);
		
		Group g = JSON.parseObject(jsonString, Group.class);
		System.out.println(g.getGroupName());
		
		
		Date date = new Date();
		String dateJsonString = JSON.toJSONStringWithDateFormat(date, "yyyy-MM-dd HH:mm:ss.SSS");
		System.out.println(dateJsonString);

	}

}

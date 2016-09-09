package com.thunisoft.json.bean;

import java.util.ArrayList;
import java.util.List;

public class Group {
	private int groupID;
	private String groupName;
	private List<User> users = new ArrayList<User>();
	
	public int getGroupID() {
		return groupID;
	}
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void addUser(User user) {
        users.add(user);
    }
	
	
}

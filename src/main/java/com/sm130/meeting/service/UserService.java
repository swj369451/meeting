package com.sm130.meeting.service;

import com.sm130.meeting.po.User;


public interface UserService {

    User checkUser(String username, String password);
}

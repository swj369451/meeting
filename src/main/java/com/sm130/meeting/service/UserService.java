package com.sm130.meeting.service;

import com.sm130.meeting.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {

    User checkUser(String username, String password);

    User findUser(Long id);

    User save(User user);

    Page<User> listUsers(Pageable pageable);

    User updateUser(User user);

    User deleteById(Long id);

}

package com.sm130.meeting.service;

import com.sm130.meeting.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface UserService {

    User checkUser(String username, String password);

    User findUser(Long id);

    User save(User user);

    Page<User> listUsers(Pageable pageable);

    User deleteById(Long id);

    List<User> findAll();

    List<User> listUsers();

}

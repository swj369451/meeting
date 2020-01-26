package com.sm130.meeting.service;

import com.sm130.meeting.dao.UserRepository;
import com.sm130.meeting.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
//        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        User user = userRepository.findByUsernameAndPassword(username, password);
        return user;
    }

    @Override
    public User findUser(Long id) {
        User user = userRepository.findById(id).get();
        return user;
    }

    @Override
    public User save(User user) {
        User save = userRepository.save(user);
        return save;
    }

    @Override
    public Page<User> listUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User updateUser(User user) {
        User save = userRepository.save(user);
        return save;
    }

    @Override
    public User deleteById(Long id) {
        userRepository.deleteById(id);
        return null;
    }


}

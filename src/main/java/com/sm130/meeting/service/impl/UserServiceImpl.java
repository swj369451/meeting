package com.sm130.meeting.service.impl;

import com.sm130.meeting.dao.UserRepository;
import com.sm130.meeting.po.User;
import com.sm130.meeting.service.DetailService;
import com.sm130.meeting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DetailService detailService;

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
        if(user.getAvatar()==null){
            user.setAvatar("https://unsplash.it/800/600?image=1005");
        }
        if(user.getId()==null){
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            detailService.save("添加了<"+user.getNickname()+">");
        }else {
            user.setUpdateTime(new Date());
            detailService.save("更新了<"+user.getNickname()+">");
        }
        User save = userRepository.save(user);
        return save;
    }

    @Override
    public Page<User> listUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }



    @Override
    public User deleteById(Long id) {
        User user = userRepository.findById(id).get();
        detailService.save("刪除了<"+user.getNickname()+">");
        userRepository.deleteById(id);
        return null;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> listUsers() {
        List<User> users = userRepository.findAll();
        users.sort((a,b) -> a.getUpdateTime().compareTo(b.getUpdateTime()));
        return users;
    }

}

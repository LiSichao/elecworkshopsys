package com.design.elecworkshopsys.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.design.elecworkshopsys.bean.Fault;
import com.design.elecworkshopsys.bean.User;
import com.design.elecworkshopsys.dao.DeptMapper;
import com.design.elecworkshopsys.dao.UserMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    @Resource
    UserMapper userMapper;
    @Resource
    DeptMapper deptMapper;

    public List<User> listUser(User user) {
        List<User> userList = userMapper.selectList(Wrappers.<User>lambdaQuery()
                .eq(user.getState() != null, User::getState, user.getState())
                .eq(user.getType() != null, User::getType, user.getType())
        );
        for (User userItem : userList) {
            userItem.setDept(deptMapper.selectById(userItem.getDeptId()));
        }
        return userList;
    }

    public User login(User user) {
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        User userItem = userMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getAccount, user.getAccount())
                .eq(User::getPassword, user.getPassword())
                .eq(User::getState, 1)
        );
        if (userItem != null) {
            userItem.setDept(deptMapper.selectById(userItem.getDeptId()));
        }
        return userItem;
    }

    public Integer countUser(User user) {
        return userMapper.selectCount(Wrappers.<User>lambdaQuery()
                .eq(User::getAccount, user.getAccount())
        );
    }

    public User getUserById(int id) {
        return userMapper.selectById(id);
    }

    public int insertUser(User user) {
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        return userMapper.insert(user);
    }

    public int updateUser(User user) {
        if (StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        } else {
            user.setPassword(null);
        }
        return userMapper.updateById(user);
    }

    public int deleteUser(Integer id) {
        return userMapper.deleteById(id);
    }

    public int countUser() {
        return userMapper.selectCount(Wrappers.<User>lambdaQuery().ne(User::getState, 0));
    }
}

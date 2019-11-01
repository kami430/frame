package com.frame.web.business.dao;

import com.frame.web.base.baseRepository.BaseRepository;
import com.frame.web.base.login.BaseUser;
import com.frame.web.base.login.LoginUserDao;
import com.frame.web.business.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseRepository<User,String>, LoginUserDao {

    @Override
    BaseUser findByAccount(String account);
}

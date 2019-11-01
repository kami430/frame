package com.frame.web.base.login;

import com.frame.web.base.baseRepository.BaseRepository;
import org.springframework.stereotype.Repository;

public interface LoginUserDao {
    BaseUser findByAccount(String account);
}

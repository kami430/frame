package com.frame.web.base.login;

public interface LoginUserDao {
    BaseUser findByAccount(String account);
}

package com.frame.web.base.login;

import java.util.List;

public interface LoginRoleUserRefDao {

    List<BaseRoleUserRef> findAllByAccount(String account);

    List<BaseRoleUserRef> findAllBySyncAndRevision(Integer sync,Integer revision);

    BaseRoleUserRef save(BaseRoleUserRef baseRoleUserRef);

    void delete(BaseRoleUserRef baseRoleUserRef);
}

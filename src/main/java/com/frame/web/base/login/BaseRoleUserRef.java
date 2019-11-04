package com.frame.web.base.login;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseRoleUserRef {
    @Id
    protected String id;
    /* 人员账号 */
    protected String account;
    /* 角色代码 */
    protected String role;
    /* 是否已同步 -1:不能同步,0:未同步,1:已同步 */
    protected Integer sync;
    /* 数据状态 -1:不可以,0:待审核,1:已激活 */
    protected Integer revision;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getSync() {
        return sync;
    }

    public void setSync(Integer sync) {
        this.sync = sync;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }
}

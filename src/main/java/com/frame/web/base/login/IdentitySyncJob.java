package com.frame.web.base.login;

import com.frame.core.sql.RefRule;
import com.frame.core.utils.EncryptUtil;
import com.frame.web.base.Enum.Revision;
import com.frame.web.base.Enum.Sync;
import com.frame.web.business.entity.RoleUserRef;
import org.camunda.bpm.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@EnableScheduling
@Component
public class IdentitySyncJob {

    private static final long SECOND = 1000;

    @Autowired
    private LoginUserDao loginUserdao;
    @Autowired
    private LoginRoleDao loginRoleDao;
    @Autowired
    private LoginRoleUserRefDao loginRoleUserRefDao;
    @Autowired
    private IdentityService identityService;

    @Scheduled(fixedRate = 60 * SECOND)
    public void identityUserSync() {
        loginUserdao.findAllBySync(Sync.PENDING.getInt()).forEach(user -> {
            if (identityService.createUserQuery().userId(user.getAccount()).singleResult() == null) {
                identityService.saveUser(identityService.newUser(user.getAccount()));
            }
            user.setSync(Sync.ACTIVE.getInt());
            loginUserdao.save(user);
        });
    }

    @Scheduled(fixedRate = 60 * SECOND)
    public void identityUserDeleteSync() {
        List<String> accounts = loginUserdao.findAllBySync(Sync.ACTIVE.getInt()).parallelStream()
                .map(user -> user.getAccount()).collect(Collectors.toList());
        identityService.createUserQuery().list().parallelStream().filter(user -> !accounts.contains(user.getId()))
                .forEach(user -> identityService.deleteUser(user.getId()));
    }

    @Scheduled(fixedRate = 60 * SECOND)
    public void identityRoleSync() {
        loginRoleDao.findAllBySync(Sync.PENDING.getInt()).forEach(role -> {
            if (identityService.createGroupQuery().groupId(role.getCode()).singleResult() == null) {
                identityService.saveGroup(identityService.newGroup(role.getCode()));
            }
            role.setSync(Sync.ACTIVE.getInt());
            loginRoleDao.save(role);
        });
    }

    @Scheduled(fixedRate = 60 * SECOND)
    public void identityRoleDeleteSync() {
        List<String> groups = loginRoleDao.findAllBySync(Sync.ACTIVE.getInt()).parallelStream()
                .map(role -> role.getCode()).collect(Collectors.toList());
        identityService.createGroupQuery().list().parallelStream().filter(group -> !groups.contains(group.getId()))
                .forEach(group -> identityService.deleteGroup(group.getId()));
    }

    @Scheduled(fixedRate = 60 * SECOND)
    public void identityRoleUserRefSync() {
        loginRoleUserRefDao.findAllBySyncAndRevision(Sync.PENDING.getInt(), Revision.ACTIVE.getInt()).forEach(ref -> {
            identityService.createMembership(ref.getAccount(), ref.getRole());
            ref.setSync(Sync.ACTIVE.getInt());
            loginRoleUserRefDao.save(ref);
        });
    }

    @Scheduled(fixedRate = 60 * SECOND)
    public void identityRoleUserRefDeleteSync() {
        loginRoleUserRefDao.findAllBySyncAndRevision(Sync.ACTIVE.getInt(), Revision.ACTIVE.getInt()).forEach(ref -> {
            identityService.deleteMembership(ref.getAccount(), ref.getRole());
            loginRoleUserRefDao.delete(ref);
        });
    }
}

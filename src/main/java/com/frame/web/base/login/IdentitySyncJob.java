package com.frame.web.base.login;

import com.frame.web.base.Enum.Sync;
import org.camunda.bpm.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@EnableScheduling
@Component
public class IdentitySyncJob {

    private static final long SECOND = 1000;
    @Autowired
    private LoginUserDao loginUserdao;
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

}

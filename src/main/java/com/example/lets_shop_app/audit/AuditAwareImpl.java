package com.example.lets_shop_app.audit;

import com.example.lets_shop_app.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<Long> {

    @Autowired
    private UserUtil userUtil;

    @Override
    public Optional<Long> getCurrentAuditor() {
        long userId = userUtil.getUserId();
        return Optional.of(userId);
    }
}

package com.distinktiv.component;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "user.account")
public class UserAccountSettings {

    private int maxFailedLoginCount;
    private int lockTimeOut;

    public int getMaxFailedLoginCount() {
        return maxFailedLoginCount;
    }

    public void setMaxFailedLoginCount(int maxFailedLoginCount) {
        this.maxFailedLoginCount = maxFailedLoginCount;
    }

    public int getLockTimeOut() {
        return lockTimeOut;
    }

    public void setLockTimeOut(int lockTimeOut) {
        this.lockTimeOut = lockTimeOut;
    }
}

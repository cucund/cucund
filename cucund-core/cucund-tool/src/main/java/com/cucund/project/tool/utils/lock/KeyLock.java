package com.cucund.project.tool.utils.lock;

public interface KeyLock {

    public void tryLock(String key) throws InterruptedException;

    public void lock(String key);

    public void unlock(String key);
}

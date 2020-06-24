package com.project.cucund.redis.redisson.enumc;

public enum LockType {

    NORMAL("lock"),
    FAIR("fairLock"),
    MULTI("MultiLock"),
    RED("RedLock"),
    READ_WRITE("readWriteLock")
    ;

    private String desc;

    private LockType(String desc){
        this.desc = desc;
    }

}

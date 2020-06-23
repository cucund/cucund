package com.cucund.project.tool.utils.lock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NativeLock implements KeyLock {

    volatile Map<String,Lock> lockMap = new ConcurrentHashMap<>();              //锁MAP
    volatile Map<String, AtomicStampedReference<Integer>> countMap = new ConcurrentHashMap<>();   //计数 MAP

    private int i;
    private TimeUnit unit;
    public NativeLock(int i,TimeUnit unit){
        this.unit = unit;
        this.i = i;
    }
    public NativeLock(){
        this(10,TimeUnit.SECONDS);
    }

    /**
     * 尝试获取锁
     * @param key
     */
    @Override
    public void tryLock(String key){
        Lock lock = getLock(key);
        try {
            lock.tryLock(i, unit);
        } catch (InterruptedException e) {
            decr(key);
        }
    }


    /**
     * 加锁
     * @param key
     */
    @Override
    public void lock(String key) {
        Lock lock = getLock(key);
        lock.lock();
    }

    /**
     * 解锁
     * @param key
     */
    @Override
    public void unlock(String key) {
        decr(key);
    }

    private Lock getLock(String key) {
        Lock lock = lockMap.get(key);       //查询map锁
        if(lock == null){                   //锁为空
            lock = newLock(key);            //创建新锁
        }
        incr(key);                          //锁计数增加
        return lock;
    }




    /**
     * 创建新锁
     * @param key
     * @return
     */
    private synchronized Lock newLock(String key) {
        Lock lock = null;
        if((lock = lockMap.get(key)) == null){                      // 重新获取锁是否被创建
            sleep();                                                // 同时 相同KEY 数据进入过多,原理不知道  求解释
            lockMap.put(key,lock = new ReentrantLock());            // 创建锁 并加入MAP集合
            countMap.put(key,new AtomicStampedReference<Integer>(0,0)); //引用计数创建
        }
        return lock;
    }


    /**
     * 计数增加
     * @param key
     */
    private void incr(String key) {
        AtomicStampedReference<Integer> s = countMap.get(key);  //获取计数
        while (!s.compareAndSet(s.getReference(), s.getReference() + 1, s.getStamp(), s.getStamp() + 1)){}      //加一
    }


    /**
     * 计数减少
     * @param key
     */
    private synchronized void decr(String key) {
        AtomicStampedReference<Integer> s = countMap.get(key);  //获取计数
        while (!s.compareAndSet(s.getReference(), s.getReference() - 1, s.getStamp(), s.getStamp() + 1)){}      //减一
        int i ;
        if ((i = s.getReference()) == 0) {      //计数为 0 进入移除模式
            sleep();                            //同时过多相同key进入,睡眠后重新获取key计数0再删除
            if((i = s.getReference()) == 0){    //计数再次读取 为0  确认进入移除模式
                countMap.remove(key);           //移除计数
                Lock lock = lockMap.remove(key);//移除LOCK 并获取锁
                lock.unlock();                  //锁 : 解锁
            }else{
                lockMap.get(key).unlock();      //锁 : 解锁
            }
        }else{
            lockMap.get(key).unlock();          //锁 : 解锁
        }
    }


    /**
     * 不知道为啥5毫秒  可以避免出错  小于5毫秒不行
     */
    private void sleep() {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
        }
    }

}

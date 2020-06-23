package com.cucund.project.tool.test;

import com.cucund.project.tool.utils.lock.NativeLock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    static Integer i = new Integer(0);

    @org.junit.Test
    public  void test() throws InterruptedException {
        NativeLock lock = new NativeLock();                                                 //同步字符串锁创建
        ExecutorService cachedThreadPool = Executors.newFixedThreadPool(16);   //线程池创建   建议和CPU 线程正比
        Runnable runnable = new Runnable() {                                            //创建执行逻辑
            @Override
            public void run() {
                try {
                    lock.lock("123");                                              //加锁  锁名:123
                    ++Test.i;                                                           //执行操作 ++i
                    lock.unlock("123");                                            //解锁  锁名:123
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        long start = System.currentTimeMillis();                                        //开始时间
        for (int i = 0; i <20000000; i++) {                                            //循环i 2000W次
            cachedThreadPool.execute(runnable);                                        //线程池执行 runnable
        }
        while (i < 20000000){                                                          //主线程避免退出循环  2000W次当 锁处理的值 到达 2000W 退出循环
            Thread.sleep(1000);                                                    //避免主线程循环过快
            long end = System.currentTimeMillis();                                       //当前执行时间
            System.out.println((end -start)+"毫秒");                                      //执行时间打印
            System.out.println(i);                                                       //i的计数
        }
        cachedThreadPool.shutdown();                                                    //退出循环后关闭线程池
    }

}

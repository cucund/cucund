package com.cucund.project.tool.utils;

import lombok.Getter;

import java.time.Instant;


@Getter
public class Timer {

    private Long start;

    private Long end;

    private long pause;

    private boolean pauseFLag = false;
    private Timer(){
        this.start = now();
    }


    /**
     * 计时器启动
     * @return
     */
    public static Timer start(){
        return new Timer();
    }


    /**
     * 暂停 计时器
     * @return
     */
    public Timer pause(){
        if(pauseFLag = !pauseFLag){
            pause = now() - start;
        }else{
            start = now();
        }
        return this;
    }
    /**
     * 计时器结束
     * 不重复结束
     * @return
     */
    public Timer end(){
        if(this.end == null)
            this.end = now();
        return this;
    }

    /**
     * 获取结果
     * 未结束就强制结束
     * @return
     */
    public long get(){
        if(pauseFLag)
            this.pause();
        return (end().end - this.start) + pause;
    }

    /**
     * 获取结果
     * 不强制结束  只获取当前结果
     * @return
     */
    public long getNoStop(){
        if(pauseFLag)
            return pause;
        return (now() - this.start) + pause;
    }

    private long now() {
        return Instant.now().toEpochMilli();
    }

}

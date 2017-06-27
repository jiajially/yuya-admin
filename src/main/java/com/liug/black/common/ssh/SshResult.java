package com.liug.black.common.ssh;

/**
 * Created by liugang on 2017/6/19.
 */
public class SshResult {
    int exitStatus;
    String content;

    public int getExitStatus() {
        return exitStatus;
    }

    public void setExitStatus(int exitStatus) {
        this.exitStatus = exitStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Result{" +
                "exitStatus=" + exitStatus +
                ", content='" + content + '\'' +
                '}';
    }
}

package com.liug.common.ssh;

import java.util.List;

/**
 * Created by liugang on 2017/7/14.
 */
public class SshChartResult {
    int successCount;
    int failCount;
    int allCount;
    String dateRef;

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    public int getAllCount() {
        return successCount + failCount;
    }


    public String getDateRef() {
        return dateRef;
    }

    public void setDateRef(String dateRef) {
        this.dateRef = dateRef;
    }

    @Override
    public String toString() {
        return "SshChartResult{" +
                "successCount=" + successCount +
                ", failCount=" + failCount +
                ", allCount=" + allCount +
                ", dateRef='" + dateRef + '\'' +
                '}';
    }
}

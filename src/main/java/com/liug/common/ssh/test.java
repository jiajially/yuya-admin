package com.liug.common.ssh;

import com.liug.model.entity.SshHost;

/**
 * Created by liugang on 2017/6/19.
 */
public class test {
    public static void main(){
        Commond commond = new Commond();
        SshHost host = new SshHost();
        host.setEnvPath("/home/runtime/monitor/java/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/home/runtime/maven/bin:/home/runtime/jdk7/bin:/bin:/root/bin");
        host.setHost("192.168.31.188");
        host.setPassword("117700");
        host.setUsername("root");
        host.setPort(22);
        SshResult result = commond.execute(host,"free");
        System.out.println(result.toString());
        result = commond.execute(host,"free");
        System.out.println(result.toString());
        result = commond.execute(host,"free");
        System.out.println(result.toString());
    }
}

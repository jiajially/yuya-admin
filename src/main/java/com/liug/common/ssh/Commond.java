package com.liug.common.ssh;
import com.jcraft.jsch.*;
import com.liug.model.entity.SshHost;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Commond{

  public static SshResult execute(SshHost host, String commond){
    SshResult result = new SshResult();
    try{
      JSch jsch=new JSch();

      Session session=jsch.getSession(host.getUsername(), host.getHost(), host.getPort());
      session.setPassword(host.getPassword());
      session.setConfig("StrictHostKeyChecking", "no");
      session.connect();

      String _tempCommond="";
      if(host.getEnvPath()==null||host.getEnvPath().trim().equals(""))
        _tempCommond = commond;
      else
        _tempCommond = "export PATH=" + host.getEnvPath() + ";" + commond;

      Channel channel=session.openChannel("exec");
      ((ChannelExec)channel).setCommand(_tempCommond);
      channel.setInputStream(null);
      ((ChannelExec)channel).setErrStream(System.err);
      InputStream in=channel.getInputStream();
      channel.connect();

      String _tempResultStr="";
      int _tempExitStatusCode;
      byte[] tmp=new byte[1024];
      while(true){
        while(in.available()>0){
          int i=in.read(tmp, 0, 1024);
          if(i<0)break;
          //System.out.print(new String(tmp, 0, i));
          _tempResultStr += new String(tmp, 0, i);
        }
        if(channel.isClosed()){
          if(in.available()>0) continue;
          //System.out.println("exit-status: "+channel.getExitStatus());
          _tempExitStatusCode = channel.getExitStatus();
          break;
        }
        try{Thread.sleep(1000);}catch(Exception ee){}
      }
      result.setContent(_tempResultStr);
      result.setExitStatus(_tempExitStatusCode);

      channel.disconnect();
      session.disconnect();
    }
    catch(Exception e){
      System.out.println(e);
      result.setContent(e.getMessage());
      result.setExitStatus(-1);
    }finally {
      return result;
    }
  }

  public static SshResult getEnvPath(SshHost host){
    SshResult result = new SshResult();
    try {
      //使用Shell方式获取PATH
      String _tmpResult = Shell.execToString(host,"echo 888+$PATH+777");
      //下面截取PATH
      if(_tmpResult.indexOf("888+/") < 0) {result.setExitStatus(-1);result.setContent("error,cannot use startindex get $PATH");return result;}
      _tmpResult =_tmpResult.substring(_tmpResult.indexOf("888+/"));
      if(_tmpResult.indexOf("+777") < 0) {result.setExitStatus(-1);result.setContent("error,cannot use endindex get $PATH");return result;}
      _tmpResult =_tmpResult.substring(_tmpResult.indexOf("888+/"),_tmpResult.indexOf("+777"));
      _tmpResult =_tmpResult.replace("888+","");
      result.setExitStatus(1);
      result.setContent(_tmpResult);
    }
    catch (JSchException e) {
      if (e.getMessage().indexOf("Auth")>=0) result.setContent("账户用户名密码验证失败");
      else result.setContent(e.getMessage());
    }
    finally {
      return result;
    }

  }
  public static void main(String [] args){
    /*Commond commond = new Commond();
    SshHost host = new SshHost();
    host.setEnvPath("/home/runtime/monitor/java/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/home/runtime/maven/bin:/home/runtime/jdk7/bin:/bin:/root/bin");
    host.setHost("192.168.31.188");
    host.setPassword("117700");
    host.setUsername("liugang");
    host.setPort(22);

    System.out.println(Float.valueOf(Commond.execute(host,"free  | sed -n '2p' | awk '{print $3/$2}'").getContent()));
    */
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH");
    Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
    //c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DATE),c.get(Calendar.HOUR_OF_DAY),0,0);

    c.set(2017,6,13,20,0,0);

    System.out.println(formatter.format(c.getTime()));
    List<Date> dateList = new ArrayList<Date>();
    for(int i=0;i<24;i++) {
      c.add(Calendar.HOUR_OF_DAY,-1);
      int hour = c.get(Calendar.HOUR_OF_DAY);
      dateList.add(c.getTime());
      System.out.println(c.getTime());
    }

  }


}

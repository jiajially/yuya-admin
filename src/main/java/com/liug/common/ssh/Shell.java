package com.liug.common.ssh;/* -*-mode:java; c-basic-offset:2; indent-tabs-mode:nil -*- */

import com.jcraft.jsch.*;
import com.liug.common.util.FileUtil;
import com.liug.model.entity.SshHost;

import java.io.*;

public class Shell{


  public static String execToString(SshHost host, String cmd)throws JSchException{
    return FileUtil.loadFile(execToFile(host,cmd+"\n"));
  }

  public static String execToFile(SshHost host,String cmd) throws JSchException {

    String _fileName = FileUtil.getProjectPath()+"/file/shellResultTmp/"+System.currentTimeMillis()+"."+"rst";
    File tmp = new File(_fileName);
    if(!tmp.getParentFile().exists()) {
      //System.out.println("目标文件所在目录不存在，准备创建它！");
      if(!tmp.getParentFile().mkdirs()) {
        //System.out.println("创建目标文件所在目录失败！");
      }
    }
    try{
      JSch jsch=new JSch();
      Session session=jsch.getSession(host.getUsername(), host.getHost(), host.getPort());
      session.setPassword(host.getPassword());
      session.setConfig("StrictHostKeyChecking", "no");
      session.connect(3000);   // making a connection with timeout.

      Channel channel=session.openChannel("shell");
      PipedInputStream pipeIn = new PipedInputStream();
      PipedOutputStream pipeOut = new PipedOutputStream( pipeIn );
      pipeOut.write( cmd.getBytes() );
      channel.setInputStream(pipeIn);

      FileOutputStream fileOut = new FileOutputStream(_fileName);
      channel.setOutputStream(fileOut);

      channel.connect(3000);
      Thread.sleep(2000);
      channel.disconnect();
      session.disconnect();
    }
    catch(JSchException e){
      throw e;
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return _fileName;
  }

  public static  class MyUserInfo
                          implements UserInfo, UIKeyboardInteractive{
    public String getPassword(){ return null; }
    public boolean promptYesNo(String str){ return false; }
    public String getPassphrase(){ return null; }
    public boolean promptPassphrase(String message){ return false; }
    public boolean promptPassword(String message){ return false; }
    public void showMessage(String message){ }
    public String[] promptKeyboardInteractive(String destination,
                                              String name,
                                              String instruction,
                                              String[] prompt,
                                              boolean[] echo){
      return null;
    }
  }

  //public static void main(String args []){
    ////System.out.println(Shell.execToString("192.168.31.188","root","117700","docker"));
  //}
}

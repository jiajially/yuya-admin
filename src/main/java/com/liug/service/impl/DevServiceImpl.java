package com.liug.service.impl;

import com.liug.common.ssh.Commond;
import com.liug.common.ssh.SshResult;
import com.liug.model.entity.CharRecg;
import com.liug.model.entity.SshHost;
import com.liug.service.DevService;
import com.liug.service.SshHostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by liugang on 2017/7/2.
 */
@Service
@Transactional
public class DevServiceImpl implements DevService {
    @Autowired
    SshHostService sshHostService;

    @Override
    public CharRecg loadfile(long id, String path) {
        CharRecg charRecg = new CharRecg();
        charRecg.setCharacter("CREATE");
        charRecg.setCount(-1);
        //读取文件指令
        String cmd = "cat ";
        cmd += path;
        SshResult sshResult;
        SshHost sshHost = sshHostService.selectById(id);
        if(sshHost!=null){
            sshResult = Commond.execute(sshHost,cmd);//
            //文字识别变色
            String content= sshResult.getContent();
            //获取字符数量
            int counter=0;
            if (content.indexOf(charRecg.getCharacter()) == -1) {
                counter = 0;
            }
            while(content.indexOf(charRecg.getCharacter())!=-1){
                counter++;
                content=content.substring(content.indexOf(charRecg.getCharacter())+charRecg.getCharacter().length());
            }
            charRecg.setContent( sshResult.getContent().replace(charRecg.getCharacter(),"<font color=\"#c24f4a\">"+charRecg.getCharacter()+"</font>"));
            charRecg.setCount(counter);
        }
        return charRecg;
    }
}

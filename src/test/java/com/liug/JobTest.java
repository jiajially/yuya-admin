package com.liug;

import com.liug.black.dao.SshScriptMapper;
import com.liug.white.service.SshScriptService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by liugang on 2017/6/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BlackEyeJspApplication.class)
public class JobTest {
    @Autowired
    private SshScriptMapper sshScriptMapper;
    @Test
    public void test() {
        System.out.println(sshScriptMapper.selectCounts());
    }



}

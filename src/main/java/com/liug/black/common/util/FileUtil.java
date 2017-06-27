package com.liug.black.common.util;

import java.io.*;

/**
 * Created by liugang on 2017/5/29.
 */
public class FileUtil {

    public static String getProjectPath(){
        String _path = System.getProperty("user.dir");
        File directory = new File("");// 参数为空
        String courseFile = null;
        try {
            courseFile = directory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        _path = _path==courseFile?_path:courseFile;
        return _path;
    }

    public static String loadFile(String path){
            String result = "";
            try {
                String encoding="GBK";
                File file=new File(path);
                if(file.isFile() && file.exists()){ //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(
                            new FileInputStream(file),encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while((lineTxt = bufferedReader.readLine()) != null){
                        result += lineTxt+"\n";
                    }
                    read.close();
                }else{
                    result = "找不到指定的文件";
                }
            } catch (Exception e) {
                result = "读取文件内容出错";
                e.printStackTrace();
            }

        return  result;
    }

}

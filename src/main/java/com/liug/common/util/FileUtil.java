package com.liug.common.util;

import com.liug.YuYaAdminApplication;
import com.liug.controller.IndexController;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    public static List<FileStruct> readfileList(String filepath) throws FileNotFoundException, IOException {
        List<FileStruct> fileList = new ArrayList<FileStruct>();
        try {

            File file = ResourceUtils.getFile("classpath:"+filepath);
            if (!file.isDirectory()) {
                System.out.println("文件");
                System.out.println("path=" + file.getPath());
                System.out.println("absolutepath=" + file.getAbsolutePath());
                System.out.println("name=" + file.getName());

            } else if (file.isDirectory()) {
                System.out.println("文件夹");
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "/" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        FileStruct fileStruct = new FileStruct();
                        fileStruct.setAbsolutePath(readfile.getAbsolutePath());
                        fileStruct.setName(readfile.getName());
                        fileStruct.setPath(readfile.getPath());
                        fileStruct.setId(i);
                        /*System.out.println("path=" + readfile.getPath());
                        System.out.println("absolutepath="
                                + readfile.getAbsolutePath());
                        System.out.println("name=" + readfile.getName());*/
                        fileList.add(fileStruct);

                    } /*else if (readfile.isDirectory()) {
                        readfile(filepath + "\\" + filelist[i]);
                    }*/
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println("readfile()   Exception:" + e.getMessage());
        }
        return fileList;
    }



    public static void main(){
        try {
            System.out.println(readfileList("file/bu").size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

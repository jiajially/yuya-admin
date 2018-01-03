package com.liug.common.util;

import org.springframework.util.ResourceUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liugang on 2017/5/29.
 */
public class FileUtil {

    public static String getProjectPath() {
        String _path = System.getProperty("user.dir");
        File directory = new File("");// 参数为空
        String courseFile = null;
        try {
            courseFile = directory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        _path = _path == courseFile ? _path : courseFile;
        return _path;
    }


    public static List<String> getFileNameList(String path, String suffix) {
        List<String> fileNameList = new ArrayList<String>();
        if (suffix != null && suffix != "") {
            File file = new File(path);
            if (file.isDirectory()) {
                File[] lists = file.listFiles();
                for (File file_temp : lists) {
                    String[] filename_temp_group = file_temp.getName().split("\\.");
                    if (filename_temp_group.length == 2 && filename_temp_group[1].equals(suffix.trim()))
                        fileNameList.add(file_temp.getName());
                }
            }
        }
        return fileNameList;
    }

    public static boolean createFile(String fileName, String contnet) {
        boolean res = false;
        String fileNameTemp = "" + fileName;
        File file = new File(fileNameTemp);
        try {
            if (!file.exists()) {
                file.createNewFile();
                res = true;
                FileWriter fileWriter = new FileWriter(fileNameTemp);
                //使用缓冲区比不使用缓冲区效果更好，因为每趟磁盘操作都比内存操作要花费更多时间。
                //通过BufferedWriter和FileWriter的连接，BufferedWriter可以暂存一堆数据，然后到满时再实际写入磁盘
                //这样就可以减少对磁盘操作的次数。如果想要强制把缓冲区立即写入,只要调用writer.flush();这个方法就可以要求缓冲区马上把内容写下去
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(contnet);
                bufferedWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            return res;
        }
    }

    public static boolean createFileForce(String fileName, String contnet) {
        boolean res = false;
        String fileNameTemp = "" + fileName;
        File file = new File(fileNameTemp);
        try {
            if (file.exists()) file.delete();

            file.createNewFile();
            res = true;
            FileWriter fileWriter = new FileWriter(fileNameTemp);
            //使用缓冲区比不使用缓冲区效果更好，因为每趟磁盘操作都比内存操作要花费更多时间。
            //通过BufferedWriter和FileWriter的连接，BufferedWriter可以暂存一堆数据，然后到满时再实际写入磁盘
            //这样就可以减少对磁盘操作的次数。如果想要强制把缓冲区立即写入,只要调用writer.flush();这个方法就可以要求缓冲区马上把内容写下去
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(contnet);
            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            return res;
        }
    }

    public static String loadFile(String path) {
        String result = "";
        try {
            String encoding = "GBK";
            File file = new File(path);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    result += lineTxt + "\n";
                }
                read.close();
            } else {
                result = "找不到指定的文件";
            }
        } catch (Exception e) {
            result = "读取文件内容出错";
            e.printStackTrace();
        }

        return result;
    }

    public static String loadFile(String path, String encoding) {
        String result = "";
        try {
            if (encoding == null || encoding.trim().equals("")) encoding = "UTF-8";
            File file = new File(path);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    result += lineTxt + "\n";
                }
                read.close();
            } else {
                result = "找不到指定的文件";
            }
        } catch (Exception e) {
            result = "读取文件内容出错";
            e.printStackTrace();
        }

        return result;
    }


    public static List<FileStruct> readfileList(String filepath) throws FileNotFoundException, IOException {
        List<FileStruct> fileList = new ArrayList<FileStruct>();
        try {

            File file = ResourceUtils.getFile(filepath);
            if (!file.isDirectory()) {
                System.out.println("文件");
                System.out.println("path=" + file.getPath());
                System.out.println("absolutepath=" + file.getAbsolutePath());
                System.out.println("name=" + file.getName());

            } else if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "/" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        FileStruct fileStruct = new FileStruct();
                        fileStruct.setAbsolutePath(readfile.getAbsolutePath());
                        fileStruct.setName(readfile.getName());
                        fileStruct.setPath(readfile.getPath());
                        fileStruct.setId(i);

                        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        fileStruct.setLastModifyTime(sdf.format(readfile.lastModified()));


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

    //初始化文件目录
    public static void initDictionary(String dest) {

        File file_document = new File(getProjectPath() + (dest == null ? "" : dest) + "/document/local/");
        if (!file_document.exists()) file_document.mkdirs();
        file_document = new File(getProjectPath() + (dest == null ? "" : dest) + "/document/sys/");
        if (!file_document.exists()) file_document.mkdirs();
        file_document = new File(getProjectPath() + (dest == null ? "" : dest) + "/doc/");
        if (!file_document.exists()) file_document.mkdirs();
        file_document = new File(getProjectPath() + (dest == null ? "" : dest) + "/picture/");
        if (!file_document.exists()) file_document.mkdirs();
        file_document = new File(getProjectPath() + (dest == null ? "" : dest) + "/script/vbs/");
        if (!file_document.exists()) file_document.mkdirs();
    }

    public static void main() {
        List<String> list = FileUtil.getFileNameList(FileUtil.getProjectPath() + "/file/system/ecc/report", "routine");
        for (String s : list) {
            System.out.println(s);
        }
    }


}

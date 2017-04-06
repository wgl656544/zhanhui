package com.example.mylibrary.utils;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by user on 2016/3/21.
 */
public class FileUtils {


    //是否在本地有缓存文件
    public static  boolean isFileCache(Context context,String videoName ){
        boolean isCache = false;
        File file = new File(context.getCacheDir()+File.separator+"IIE");
        if(!file.exists()){
            file.mkdir();
            isCache = false;
        } else {
            File[] files = file.listFiles();
            if(files != null) {
                for (File mFile : files) {
                    if (mFile.getName().equals(videoName)) {
                        isCache = true;
                        break;
                    }
                }
            }
        }
        return isCache;
    }

    /*public static Object readFile(String name){

        File file = new File(CustomApplication.getInstance().getCacheDir()+File.separator+"IIE"+File.separator+name);
        if(file.exists()){

            readObjectFromFile(file);

        }

        return null;
    }*/

    private static void readObjectFromFile(File file) {

    }

   /* //保存对象为文件
    public static void saveFile(String name,Object data,Context context){

        File saveFile = null;
        String fileName = "";
        //如果有外部储存设备
        if(isExternalStorageWritable()){

                File fileDir = new File(CustomApplication.getInstance().getCacheDir()+File.separator+"IIE");
                if(!fileDir.exists()){
                    fileDir.mkdir();
                }
                 fileName = CustomApplication.getInstance().getCacheDir()+File.separator+"IIE" + File.separator +
                         SharePerferenceKit.getInstance(context).getInt(SharePreferenceConst.UID)+name;
                 saveFile = new File(fileName);
                if(!saveFile.exists())
                    try {
                        saveFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                }else{
                    saveFile.delete();
                    try {
                        saveFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }else{
            fileName = context.getFilesDir()+File.separator+name;
            saveFile = new File(context.getFilesDir(),name);
        }
        saveObject2File(fileName,data,context);
    }*/

    private static void saveObject2File(String saveFileStr,Object data,Context context) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        //保存在本地
        try {
            File f = new File(saveFileStr);
            fos = new FileOutputStream(f,true);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(data);// 写入

            fos.close(); // 关闭输出流
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }

    }

    //判断外部存储是否可以读写
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * 重命名
     * @param filePathName
     * @param newPathName
     */
    public static void rename(String filePathName, String newPathName) {
        if(isNullString(filePathName)) return ;
        if(isNullString(newPathName)) return ;

        File file = new File(filePathName);
        File newFile = new File(newPathName);
        if(!newFile.exists())
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        file.renameTo(newFile);
    }

    private static boolean isNullString(String filePathName) {
        return filePathName.equals("")||filePathName == null;
    }

   /* *//**
     * 删除文件
     *//*
    public static void delete(String filePathName) {
        if(isNullString(filePathName)) return ;
        File file = new File(CustomApplication.getInstance().getCacheDir()+File.separator+"IIE"+File.separator+filePathName);
        if (file.isFile() && file.exists()) {
            boolean flag = file.delete();
            L.i("filePathName reset:"+filePathName+" flag:"+flag);
        }
    }*/

    /**
     * 文件转化为字节数组
     * @EditTime 2007-8-13 上午11:45:28
     */
    public static byte[] getBytesFromFile(File f) {
        if (f == null) {
            return null;
        }
        try {
            FileInputStream stream = new FileInputStream(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = stream.read(b)) != -1) {
                out.write(b, 0, n);
            }
            stream.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
        }
        return null;
    }

    /**
     * 把字节数组保存为一个文件
     * @EditTime 2007-8-13 上午11:45:56
     */
    public static File getFileFromBytes(byte[] b, String outputFile) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = new File(outputFile);
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * 从字节数组获取对象
     * @EditTime 2007-8-13 上午11:46:34
     */
    public static Object getObjectFromBytes(byte[] objBytes) throws Exception {
        if (objBytes == null || objBytes.length == 0) {
            return null;
        }
        ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);
        ObjectInputStream oi = new ObjectInputStream(bi);
        return oi.readObject();
    }

    /**
     * 从对象获取一个字节数组
     * @EditTime 2007-8-13 上午11:46:56
     */
    public static byte[] getBytesFromObject(Serializable obj) throws Exception {
        if (obj == null) {
            return null;
        }
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(obj);
        return bo.toByteArray();
    }



    public static double getDirSize(File file) {
        //判断文件是否存在
        if (file.exists()) {
            //如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                double size = 0;
                for (File f : children)
                    size += getDirSize(f);
                return size;
            } else {//如果是文件则直接返回其大小,以“兆”为单位
                double size = (double) file.length() / 1024 / 1024;
                return size;
            }
        } else {
            System.out.println("文件或者文件夹不存在，请检查路径是否正确！");
            return 0.0;
        }
    }

    public static String getFilePath(String filename)  {
        return Environment.getExternalStorageDirectory()+File.separator+"IIE"+File.separator+filename;

    }

    public static String getRealName(String currentSourcePath) {
        return  currentSourcePath.substring(currentSourcePath.lastIndexOf("/")+1,currentSourcePath.length());
    }
}

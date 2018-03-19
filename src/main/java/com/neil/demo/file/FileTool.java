package com.neil.demo.file;

import java.io.*;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created by Neil on 2018/2/8.
 * 操作文件
 */
public class FileTool {

    public static void listFilesByName(File folder, String fileName,List<File> targetfiles){
//        File[] parent = folder.listFiles(new MyFilenameFilter(fileName));
        File[] files = folder.listFiles();
        if(files == null || files.length == 0){
            return;
        }
        for(File f:files){
            if(f.isDirectory()){
                listFilesByName(f,fileName,targetfiles);
            }else if(f.getName().equals(fileName)){
                targetfiles.add(f);
            }
        }

//        return targetfiles;
    }

    /**
     * 压缩文件和文件夹
     * @param srcfile 被压缩的文件数组
     * @param zipfile 压缩以后的文件
     * @return
     */
    public static Boolean zipFiles(File[] srcfile, File zipfile){

        Boolean flag = false;
        ZipOutputStream out = null;


        try {
            out = new ZipOutputStream(new FileOutputStream(zipfile));

            for(File item:srcfile){
                compress(item,out,"");

            }
            out.close();
            out = null;
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(out != null){
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;

    }


    private static void compress(File file, ZipOutputStream out, String basedir){
        if(file.isFile()){
            zipFile(file, out, basedir);
            file.delete();
        }else{
            zipDirectory(file, out, basedir);
            file.delete();
        }
    }

    //压缩单个文件
    public static void zipFile(File srcfile, ZipOutputStream out, String basedir) {
        FileInputStream in = null;
        byte[] buf=new byte[10240];
        try {
            in = new FileInputStream(srcfile);
            out.putNextEntry(new ZipEntry(basedir + srcfile.getName()));
            int len;
            while((len = in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.closeEntry();
            in.close();
            in = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 压缩文件夹
     */
    public static void zipDirectory(File dir, ZipOutputStream out, String basedir) {
        if (!dir.exists())
            return;

        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            /* 递归 */
            compress(files[i], out, basedir + dir.getName() + "/");
        }
    }



    public static Boolean unZipFile(String fileName,String descDir) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new RuntimeException(fileName + "zip文件不存在");
        }
        ZipFile zipFile = new ZipFile(file);

        Enumeration entries = zipFile.entries();//.getEntries();
        ZipEntry entry = null;

        while (entries.hasMoreElements()) {
            entry = (ZipEntry) entries.nextElement();
            System.out.println("解压" + entry.getName());
            if (entry.isDirectory()) {
                String dirPath = descDir + File.separator + entry.getName();
                File dir = new File(dirPath);
                dir.mkdirs();
            } else {
                // 表示文件
                File f = new File(descDir + File.separator + entry.getName());
                if (!f.exists()) {
                    String dirs = f.getParent();//.getParentPath(f);
                    File parentDir = new File(dirs);
                    parentDir.mkdirs();
                }

                f.createNewFile();

                // 将压缩文件内容写入到这个文件中
                InputStream is = zipFile.getInputStream(entry);
                FileOutputStream fos = new FileOutputStream(f);

                int count;
                byte[] buf = new byte[8192];
                while ((count = is.read(buf)) != -1) {
                    fos.write(buf, 0, count);
                }
                is.close();
                fos.close();

            }

        }

        return true;
    }

    //1.创建文件夹
    public static void createDir(String dirPath){
        File myFolderPath = new File(dirPath);
        try {
            if (!myFolderPath.exists()) {
                myFolderPath.mkdir();
            }
        }
        catch (Exception e) {
            System.out.println("新建目录操作出错");
            e.printStackTrace();
        }
    }


    //2.创建文件
    public static void createFile(String filePath){
        File myFilePath = new File(filePath);
        try {
            if (!myFilePath.exists()) {
                myFilePath.createNewFile();
            }
//            FileWriter resultFile = new FileWriter(myFilePath);
//            PrintWriter myFile = new PrintWriter(resultFile);
//            myFile.println(str2);
//            resultFile.close();
        }
        catch (Exception e) {
            System.out.println("新建文件操作出错");
            e.printStackTrace();
        }
    }


    //3.删除文件
    public static void deleteFile(String filePath){
        File myDelFile = new File(filePath);
        try {
            myDelFile.delete();
        }
        catch (Exception e) {
            System.out.println("删除文件操作出错");
            e.printStackTrace();
        }
    }


    //4.删除文件夹
    public static void deleteDir(String dirPath) {
        File delFolderPath = new File(dirPath);
        try {
            delFolderPath.delete(); //删除空文件夹
        } catch (Exception e) {
            System.out.println("删除文件夹操作出错");
            e.printStackTrace();
        }
    }

    //5.删除一个文件下夹所有的文件夹
    public static void deleteDirFiles(String dirPath) {
        File delfile = new File(dirPath);
        File[] files = delfile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                files[i].delete();
            }
        }
    }

    //6.清空文件夹
    public static void clearDir(String dirPath) {

        File delfilefolder = new File(dirPath);
        try {
            if (!delfilefolder.exists()) {
                delfilefolder.delete();
            }
            delfilefolder.mkdir();
        } catch (Exception e) {
            System.out.println("清空目录操作出错");
            e.printStackTrace();
        }
    }


    //10.读取文件属性
    public static void getFileAttr(String filePath){
        // 文件属性的取得
        File f = new File(filePath);
        if (f.exists()) {
            System.out.println(f.getName() + "的属性如下： 文件长度为：" + f.length());
            System.out.println(f.isFile() ? "是文件" : "不是文件");
            System.out.println(f.isDirectory() ? "是目录" : "不是目录");
            System.out.println(f.canRead() ? "可读取" : "不");
            System.out.println(f.canWrite() ? "是隐藏文件" : "");
            System.out.println("文件夹的最后修改日期为：" + new Date(f.lastModified()));
        } else {
            System.out.println(f.getName() + "的属性如下：");
            System.out.println(f.isFile() ? "是文件" : "不是文件");
            System.out.println(f.isDirectory() ? "是目录" : "不是目录");
            System.out.println(f.canRead() ? "可读取" : "不");
            System.out.println(f.canWrite() ? "是隐藏文件" : "");
            System.out.println("文件的最后修改日期为：" + new Date(f.lastModified()));
        }
    }

    //11.写入属性
    public static void setFileAttr(String filePath){
        File filereadonly=new File(filePath);
        try {
            boolean b=filereadonly.setReadOnly();
        }
        catch (Exception e) {
            System.out.println("拒绝写访问：");
            e.printStackTrace();
        }
    }


//    //12.枚举一个文件夹中的所有文件
//    public static readAllFiles(){
//        LinkedList<String> folderList = new LinkedList<String>();
//        folderList.add(str1);
//        while (folderList.size() > 0) {
//            File file = new File(folderList.peek());
//            folderList.removeLast();
//            File[] files = file.listFiles();
//            ArrayList<File> fileList = new ArrayList<File>();
//            for (int i = 0; i < files.length; i++) {
//                if (files[i].isDirectory()) {
//                    folderList.add(files[i].getPath());
//                } else {
//                    fileList.add(files[i]);
//                }
//            }
//            for (File f : fileList) {
//                str2=f.getAbsoluteFile();
//                str3
//            }
//        }
//    }
//
//
//    //13.复制文件夹
////import java.io.*;
////import java.util.*;
//    LinkedList<String> folderList = new LinkedList<String>();
//    folderList.add(str1);
//    LinkedList<String> folderList2 = new LinkedList<String>();
//    folderList2.add(str2+ str1.substring(str1.lastIndexOf("\\")));
//    while (folderList.size() > 0) {
//        (new File(folderList2.peek())).mkdirs(); // 如果文件夹不存在 则建立新文件夹
//        File folders = new File(folderList.peek());
//        String[] file = folders.list();
//        File temp = null;
//        try {
//            for (int i = 0; i < file.length; i++) {
//                if (folderList.peek().endsWith(File.separator)) {
//                    temp = new File(folderList.peek() + File.separator
//                            + file[i]);
//                } else {
//                    temp = new File(folderList.peek() + File.separator + file[i]);
//                }
//                if (temp.isFile()) {
//                    FileInputStream input = new FileInputStream(temp);
//                    FileOutputStream output = new FileOutputStream(
//                            folderList2.peek() + File.separator + (temp.getName()).toString());
//                    byte[] b = new byte[5120];
//                    int len;
//                    while ((len = input.read(b)) != -1) {
//                        output.write(b, 0, len);
//                    }
//                    output.flush();
//                    output.close();
//                    input.close();
//                }
//                if (temp.isDirectory()) {// 如果是子文件夹
//                    for (File f : temp.listFiles()) {
//                        if (f.isDirectory()) {
//                            folderList.add(f.getPath());
//                            folderList2.add(folderList2.peek()
//                                    + File.separator + f.getName());
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            //System.out.println("复制整个文件夹内容操作出错");
//            e.printStackTrace();
//        }
//        folderList.removeFirst();
//        folderList2.removeFirst();
//    }
//
//    //14.复制一个文件夹下所有的文件夹到另一个文件夹下
////import java.io.*;
////import java.util.*;
//    File copyfolders=new File(str1);
//    File[] copyfoldersList=copyfolders.listFiles();
//    for(int k=0;k<copyfoldersList.length;k++){
//        if(copyfoldersList[k].isDirectory()){
//            ArrayList<String>folderList=new ArrayList<String>();
//            folderList.add(copyfoldersList[k].getPath());
//            ArrayList<String>folderList2=new ArrayList<String>();
//            folderList2.add(str2+"/"+copyfoldersList[k].getName());
//            for(int j=0;j<folderList.length;j++){
//                (new File(folderList2.get(j))).mkdirs(); //如果文件夹不存在 则建立新文件夹
//                File folders=new File(folderList.get(j));
//                String[] file=folders.list();
//                File temp=null;
//                try {
//                    for (int i = 0; i < file.length; i++) {
//                        if(folderList.get(j).endsWith(File.separator)){
//                            temp=new File(folderList.get(j)+"/"+file[i]);
//                        } else {
//                            temp=new File(folderList.get(j)+"/"+File.separator+file[i]);
//                        }
//                        FileInputStream input = new FileInputStream(temp);
//                        if(temp.isFile()){
//                            FileInputStream input = new FileInputStream(temp);
//                            FileOutputStream output = new FileOutputStream(folderList2.get(j) + "/" + (temp.getName()).toString());
//                            byte[] b = new byte[5120];
//                            int len;
//                            while ( (len = input.read(b)) != -1) {
//                                output.write(b, 0, len);
//                            }
//                            output.flush();
//                            output.close();
//                            input.close();
//                        }
//                        if(temp.isDirectory()){//如果是子文件夹
//                            folderList.add(folderList.get(j)+"/"+file[i]);
//                            folderList2.add(folderList2.get(j)+"/"+file[i]);
//                        }
//                    }
//                }
//                catch (Exception e) {
//                    System.out.println("复制整个文件夹内容操作出错");
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    //15.移动文件夹
////import java.io.*;
////import java.util.*;
//    LinkedList<String> folderList = new LinkedList<String>();
//    folderList.add(str1);
//    LinkedList<String> folderList2 = new LinkedList<String>();
//    folderList2.add(str2 + str1.substring(str1.lastIndexOf("\\")));
//    while (folderList.size() > 0) {
//        (new File(folderList2.peek())).mkdirs(); // 如果文件夹不存在 则建立新文件夹
//        File folders = new File(folderList.peek());
//        String[] file = folders.list();
//        File temp = null;
//        try {
//            for (int i = 0; i < file.length; i++) {
//                if (folderList.peek().endsWith(File.separator)) {
//                    temp = new File(folderList.peek() + File.separator + file[i]);
//                } else {
//                    temp = new File(folderList.peek() + File.separator + file[i]);
//                }
//                if (temp.isFile()) {
//                    FileInputStream input = new FileInputStream(temp);
//                    FileOutputStream output = new FileOutputStream(
//                            folderList2.peek() + File.separator + (temp.getName()).toString());
//                    byte[] b = new byte[5120];
//                    int len;
//                    while ((len = input.read(b)) != -1) {
//                        output.write(b, 0, len);
//                    }
//                    output.flush();
//                    output.close();
//                    input.close();
//                    if (!temp.delete())
//                        System.out.println("删除单个文件操作出错!");
//                }
//                if (temp.isDirectory()) {// 如果是子文件夹
//                    for (File f : temp.listFiles()) {
//                        if (f.isDirectory()) {
//                            folderList.add(f.getPath());
//                            folderList2.add(folderList2.peek() + File.separator + f.getName());
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            // System.out.println("复制整个文件夹内容操作出错");
//            e.printStackTrace();
//        }
//        folderList.removeFirst();
//        folderList2.removeFirst();
//    }
//    File f = new File(str1);
//    if (!f.delete()) {
//        for (File file : f.listFiles()) {
//            if (file.list().length == 0) {
//                System.out.println(file.getPath());
//                file.delete();
//            }
//        }
//    }
//    //16.移动一个文件夹下所有的文件夹到另一个目录下
////import java.io.*;
////import java.util.*;
//    File movefolders=new File(str1);
//    File[] movefoldersList=movefolders.listFiles();
//    for(int k=0;k<movefoldersList.length;k++){
//        if(movefoldersList[k].isDirectory()){
//            ArrayList<String>folderList=new ArrayList<String>();
//            folderList.add(movefoldersList[k].getPath());
//            ArrayList<String>folderList2=new ArrayList<String>();
//            folderList2.add(str2+"/"+movefoldersList[k].getName());
//            for(int j=0;j<folderList.length;j++){
//                (new File(folderList2.get(j))).mkdirs(); //如果文件夹不存在 则建立新文件夹
//                File folders=new File(folderList.get(j));
//                String[] file=folders.list();
//                File temp=null;
//                try {
//                    for (int i = 0; i < file.length; i++) {
//                        if(folderList.get(j).endsWith(File.separator)){
//                            temp=new File(folderList.get(j)+"/"+file[i]);
//                        }
//                        else{
//                            temp=new File(folderList.get(j)+"/"+File.separator+file[i]);
//                        }
//                        FileInputStream input = new FileInputStream(temp);
//                        if(temp.isFile()){
//                            FileInputStream input = new FileInputStream(temp);
//                            FileOutputStream output = new FileOutputStream(folderList2.get(j) + "/" + (temp.getName()).toString());
//                            byte[] b = new byte[5120];
//                            int len;
//                            while ( (len = input.read(b)) != -1) {
//                                output.write(b, 0, len);
//                            }
//                            output.flush();
//                            output.close();
//                            input.close();
//                            temp.delete();
//                        }
//                        if(temp.isDirectory()){//如果是子文件夹
//                            folderList.add(folderList.get(j)+"/"+file[i]);
//                            folderList2.add(folderList2.get(j)+"/"+file[i]);
//                        }
//                    }
//                }
//                catch (Exception e) {
//                    System.out.println("复制整个文件夹内容操作出错");
//                    e.printStackTrace();
//                }
//            }
//            movefoldersList[k].delete();
//        }
//    }
//
//    //17.以一个文件夹的框架在另一个目录创建文件夹和空文件
////import java.io.*;
////import java.util.*;
//    boolean b=false;//不创建空文件
//    ArrayList<String>folderList=new ArrayList<String>();
//    folderList.add(str1);
//    ArrayList<String>folderList2=new ArrayList<String>();
//    folderList2.add(str2);
//    for(int j=0;j<folderList.length;j++){
//        (new File(folderList2.get(j))).mkdirs(); //如果文件夹不存在 则建立新文件夹
//        File folders=new File(folderList.get(j));
//        String[] file=folders.list();
//        File temp=null;
//        try {
//            for (int i = 0; i < file.length; i++) {
//                if(folderList.get(j).endsWith(File.separator)){
//                    temp=new File(folderList.get(j)+"/"+file[i]);
//                }
//                else{
//                    temp=new File(folderList.get(j)+"/"+File.separator+file[i]);
//                }
//                FileInputStream input = new FileInputStream(temp);
//                if(temp.isFile()){
//                    if (b) temp.createNewFile();
//                }
//                if(temp.isDirectory()){//如果是子文件夹
//                    folderList.add(folderList.get(j)+"/"+file[i]);
//                    folderList2.add(folderList2.get(j)+"/"+file[i]);
//                }
//            }
//        }
//        catch (Exception e) {
//            System.out.println("复制整个文件夹内容操作出错");
//            e.printStackTrace();
//        }
//    }
//
//    //18.复制文件
////import java.io.*;
//    int bytesum = 0;
//    int byteread = 0;
//    File oldfile = new File(str1);
//    try {
//        if (oldfile.exists()) { //文件存在时
//            FileInputStream inStream = new FileInputStream(oldfile); //读入原文件
//            FileOutputStream fs = new FileOutputStream(new File(str2,oldfile.getName()));
//            byte[] buffer = new byte[5120];
//            int length;
//            while ( (byteread = inStream.read(buffer)) != -1) {
//                bytesum += byteread; //字节数 文件大小
//                System.out.println(bytesum);
//                fs.write(buffer, 0, byteread);
//            }
//            inStream.close();
//        }
//    }
//    catch (Exception e) {
//        System.out.println("复制单个文件操作出错");
//        e.printStackTrace();
//    }
//
//    //19.复制一个文件夹下所有的文件到另一个目录
////import java.io.*;
//    File copyfiles=new File(str1);
//    File[] files=copyfiles.listFiles();
//    for(int i=0;i<files.length;i++){
//        if(!files[i].isDirectory()){
//            int bytesum = 0;
//            int byteread = 0;
//            try {
//                InputStream inStream = new FileInputStream(files[i]); //读入原文件
//                FileOutputStream fs = new FileOutputStream(new File(str2,files[i].getName());
//                byte[] buffer = new byte[5120];
//                int length;
//                while ( (byteread = inStream.read(buffer)) != -1) {
//                    bytesum += byteread; //字节数 文件大小
//                    System.out.println(bytesum);
//                    fs.write(buffer, 0, byteread);
//                }
//                inStream.close();
//            } catch (Exception e) {
//                System.out.println("复制单个文件操作出错");
//                e.printStackTrace();
//            }
//        }
//    }
//
//    //提取扩展名
//    String str2=str1.substring(str1.lastIndexOf(".")+1);


    /**
     * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
     */
    public static void readFileByBytes(String fileName) {
        File file = new File(fileName);
        InputStream in = null;
        try {
            System.out.println("以字节为单位读取文件内容，一次读一个字节：");
            // 一次读一个字节
            in = new FileInputStream(file);
            int tempbyte;
            while ((tempbyte = in.read()) != -1) {
                System.out.write(tempbyte);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try {
            System.out.println("以字节为单位读取文件内容，一次读多个字节：");
            // 一次读多个字节
            byte[] tempbytes = new byte[100];
            int byteread = 0;
            in = new FileInputStream(fileName);
            showAvailableBytes(in);
            // 读入多个字节到字节数组中，byteread为一次读入的字节数
            while ((byteread = in.read(tempbytes)) != -1) {
                System.out.write(tempbytes, 0, byteread);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    /**
     * 以字符为单位读取文件，常用于读文本，数字等类型的文件
     */
    public static void readFileByChars(String fileName) {
        File file = new File(fileName);
        Reader reader = null;
        try {
            System.out.println("以字符为单位读取文件内容，一次读一个字节：");
            // 一次读一个字符
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            while ((tempchar = reader.read()) != -1) {
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
                if (((char) tempchar) != '\r') {
                    System.out.print((char) tempchar);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println("以字符为单位读取文件内容，一次读多个字节：");
            // 一次读多个字符
            char[] tempchars = new char[30];
            int charread = 0;
            reader = new InputStreamReader(new FileInputStream(fileName));
            // 读入多个字符到字符数组中，charread为一次读取字符数
            while ((charread = reader.read(tempchars)) != -1) {
                // 同样屏蔽掉\r不显示
                if ((charread == tempchars.length)
                        && (tempchars[tempchars.length - 1] != '\r')) {
                    System.out.print(tempchars);
                } else {
                    for (int i = 0; i < charread; i++) {
                        if (tempchars[i] == '\r') {
                            continue;
                        } else {
                            System.out.print(tempchars[i]);
                        }
                    }
                }
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static void readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                System.out.println("line " + line + ": " + tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    /**
     * 随机读取文件内容
     */
    public static void readFileByRandomAccess(String fileName) {
        RandomAccessFile randomFile = null;
        try {
            System.out.println("随机读取一段文件内容：");
            // 打开一个随机访问文件流，按只读方式
            randomFile = new RandomAccessFile(fileName, "r");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 读文件的起始位置
            int beginIndex = (fileLength > 4) ? 4 : 0;
            // 将读文件的开始位置移到beginIndex位置。
            randomFile.seek(beginIndex);
            byte[] bytes = new byte[10];
            int byteread = 0;
            // 一次读10个字节，如果文件内容不足10个字节，则读剩下的字节。
            // 将一次读取的字节数赋给byteread
            while ((byteread = randomFile.read(bytes)) != -1) {
                System.out.write(bytes, 0, byteread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    /**
     * 显示输入流中还剩的字节数
     */
    private static void showAvailableBytes(InputStream in) {
        try {
            System.out.println("当前字节输入流中的字节数为:" + in.available());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

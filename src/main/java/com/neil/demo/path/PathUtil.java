package com.neil.demo.path;

/**
 * Created by Neil on 2018/2/28.
 */
public class PathUtil {

    public static String getRootPath(){
//        String rootPath = new File("").getAbsolutePath();
//        String rootPath = PathUtil.class.getResource("").getPath();
        String rootPath = PathUtil.class.getResource("/").getPath();
//        String rootPath3 = PathUtil.class.getClassLoader().getResource("").getPath();
//        String rootPath4 = PathUtil.class.getClassLoader().getResource("/").getPath();
//
//        String rootPath2 = Thread.currentThread().getContextClassLoader().getResource("").getPath();
//
//        String rootPath5 = ClassLoader.getSystemResource("").getPath();
//        String rootPath6 = ClassLoader.getSystemResource("/").getPath();
        rootPath = rootPath.replace("/WEB-INF/classes/","");
        return rootPath;
    }

}

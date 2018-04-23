package com.neil.demo.file;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by Neil on 2018/3/2.
 */
public class MyFileNameExtensionFilter implements FilenameFilter {

    public String suffix; //后缀

    public MyFileNameExtensionFilter(String suffix){
        this.suffix = suffix;
    }

    @Override
    public boolean accept(File dir, String name) {
        if(StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(suffix)){
            return name.endsWith(this.suffix);
        }
        return false;
    }

}

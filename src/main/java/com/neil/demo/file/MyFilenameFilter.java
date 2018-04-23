package com.neil.demo.file;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by Neil on 2018/3/1.
 */
public class MyFilenameFilter implements FilenameFilter {

    public String name;

    public MyFilenameFilter(String name){
        this.name = name;
    }

    @Override
    public boolean accept(File dir, String name) {
        if(StringUtils.isNotEmpty(name)){
            return name.equals(this.name);
        }
        return false;
    }
}

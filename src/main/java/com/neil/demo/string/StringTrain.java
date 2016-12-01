package com.neil.demo.string;

/**
 * Created by Neil on 16/7/7.
 */
public class StringTrain {

    /**
     * emoji表情在使用substring方法截取时可能出现被截断而导致各端解析出错问题
     * emoji表情编码类型多种,所占字节数不固定
     */
    // TODO: 未解决
    public static String cutFilterEmoji(String content,int lentgh){
        String contentCuted = content.substring(lentgh-1, lentgh); //第50个字符
        if(contentCuted.equals("\uD83D") || contentCuted.equals("\uD83C")){ //emoji表情都是以\uD83D开头的,一个表情两个字符,其中\uD83D占一个字符.当表情被截断时前端解析出错
            content = content.substring(0,lentgh-1);
        }else{
            content = content.substring(0, lentgh);
        }
        return content;
    }

    public static void main(String[] args){
        int len = 30;
        String a = "aaaa汉字\uD83D\uDC34\uD83D\uDC02\uD83C\uDDFA\uD83C\uDDF8\uD83D\uDEB2\uD83D\uDE82\uD83D\uDCF1\uD83D\uDCBB\uD83D\uDC30\uD83D\uDC2D\uD83D\uDC37\uD83D\uDC34\uD83D\uDC02\uD83C\uDDFA\uD83C\uDDF8\uD83D\uDEB2\uD83D\uDE82\uD83D\uDCF1\uD83D\uDCBB\uD83D\uDC30\uD83D\uDC2D\uD83D\uDC37\uD83D\uDC34\uD83D\uDC02";
        String rel = cutFilterEmoji(a,len);
        System.out.println(rel);
    }

}

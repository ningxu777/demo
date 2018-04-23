package com.neil.demo.response;

import net.sf.json.JSONObject;

public class Response {

	public enum ResponseType{
        success(0,"请求成功!"),
        paramerror(1,"参数错误!"),
        authorityerror(2,"没有权限!"),
        FILE_TOO_LARGE(999, "文件超过2G限制，请重新选择！"),
        RESPONSE_FAILED(-1, "抛异常了,请重试！"),
        UPLOAD_VIDEO_TOBAIDU_FAILED(50001, "上传视频至百度云失败！"),
        UPLOAD_DOCUMENT_TOBAIDU_FAILED(60001, "上传文档至百度云失败！");
        int code;
        String message;
        ResponseType(int code,String message){
            this.code = code;
            this.message = message;
        }
    }

	public static String jsonString(int code,String message,Object data){

		JSONObject result = new JSONObject();
		result.put("code", code);
		result.put("message", message);
		result.put("data", data);
		return result.toString();
	}

    /**
     * 请求成功
     * @param data
     * @return
     */
    public static String success(Object data){
        return jsonString(ResponseType.success.code, ResponseType.success.message,data);
    }

    /**
     * 请求错误
     * @param responseType
     * @return
     */
    public static String error(ResponseType responseType){
        return jsonString(responseType.code,responseType.message,null);
    }

    /**
     * 参数错误
     * @return
     */
    public static String paramerror(){
        return jsonString(ResponseType.paramerror.code, ResponseType.paramerror.message,null);
    }

    /**
     * 没有权限
     * @return
     */
    public static String authorityerror(){
        return jsonString(ResponseType.authorityerror.code, ResponseType.authorityerror.message,null);
    }
}

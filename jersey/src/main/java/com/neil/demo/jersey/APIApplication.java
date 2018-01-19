package com.neil.demo.jersey;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by Neil on 2017/9/15.
 */
public class APIApplication extends ResourceConfig {
    public APIApplication(){
        //扫描包下的所有resource或者用register(HelloResource.class);直接加载Resource
        packages("com.era.jersey");

        //注册数据转换器
        register(JacksonJsonProvider.class);

        // Logging.
        register(LoggingFilter.class);

        //文件上传
        register(MultiPartFeature.class);
    }
}

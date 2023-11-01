package com.stu.security.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.stu.base.result.R;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/******************************
 * 用途说明:需要寫入response的數據
 * 作者姓名: Administrator
 * 创建时间: 2022-09-01 20:44
 ******************************/
public class ResponseUtil {


    /*这段代码是一个用于在Java Web应用中向客户端发送JSON响应的方法。让我逐行解释其功能：
            1. `public static void out(HttpServletResponse response, R r)`：
            这是一个公共静态方法，它接受两个参数，一个是`HttpServletResponse`对象，
            另一个是泛型`R`的对象`r`。`HttpServletResponse`对象用于设置HTTP响应的状态码、内容类型和写入响应数据。
            2. `ObjectMapper mapper = new ObjectMapper();`：在方法内部创建了一个Jackson库
            的`ObjectMapper`对象。Jackson库是用于在Java对象和JSON之间进行序列化和反序列化的流行库。
            这个`ObjectMapper`对象将用于将`r`对象转换为JSON格式。
            3. `response.setStatus(HttpStatus.OK.value());`：设置HTTP响应的状态码为200 OK。这表示请求已成功处理。
            4. `response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);`：
            设置HTTP响应的内容类型为"application/json;charset=UTF-8"，表示响应体将包含JSON数据，并且使用UTF-8字符编码。
            5. `mapper.writeValue(response.getWriter(), r);`：使用`ObjectMapper`
            将`r`对象序列化为JSON格式，并将其写入`HttpServletResponse`的输出流（通过`response.getWriter()`
            获得的输出流）。这样，JSON数据将作为HTTP响应的内容发送给客户端。
    总之，这段代码的目的是将一个Java对象`r`序列化为JSON格式，并将其作为HTTP响应的内容发送给客户端，
    同时设置响应的状态码和内容类型。这通常用于Web应用程序中的API端点，以便向客户端提供结构化的数据响应。
*/
    public static void out(HttpServletResponse response, R r) {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            mapper.writeValue(new BufferedWriter(new OutputStreamWriter(response.getOutputStream())), r);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

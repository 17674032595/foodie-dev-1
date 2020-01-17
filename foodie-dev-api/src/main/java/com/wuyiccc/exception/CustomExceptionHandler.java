package com.wuyiccc.exception;

import com.wuyiccc.utils.WUYICCCJSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @author wuyiccc
 * @date 2020/1/17 19:24
 * 岂曰无衣，与子同袍~
 */

@RestControllerAdvice
public class CustomExceptionHandler  {

    // 上传文件超过500k，捕获异常：MaxUploadSizeExceededException
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public WUYICCCJSONResult handlerMaxUploadFile(MaxUploadSizeExceededException ex){//MaxUploadSizeExceededException 是要捕获的异常类型

        return WUYICCCJSONResult.errorMsg("文件上传大小不能超过500K,请压缩图片或者降低图片质量之后再上传");

    }
}

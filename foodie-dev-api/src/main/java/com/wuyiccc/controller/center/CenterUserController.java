package com.wuyiccc.controller.center;

import com.wuyiccc.controller.BaseController;
import com.wuyiccc.pojo.Users;
import com.wuyiccc.pojo.bo.center.CenterUserBO;
import com.wuyiccc.resource.FileUpload;
import com.wuyiccc.service.UserService;
import com.wuyiccc.service.center.CenterUserService;
import com.wuyiccc.utils.CookieUtils;
import com.wuyiccc.utils.DateUtil;
import com.wuyiccc.utils.JsonUtils;
import com.wuyiccc.utils.WUYICCCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.MediaSize;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuyiccc
 * @date 2020/1/17 9:58
 * 岂曰无衣，与子同袍~
 */

@Api(value = "用户信息相关接口", tags = {"用户信息相关接口"})
@RestController
@RequestMapping("userInfo")
public class CenterUserController extends BaseController {


    @Autowired
    private CenterUserService centerUserService;

    @Autowired
    private FileUpload fileUpload;


    @ApiOperation(value = "修改用户头像", notes = "修改用户头像", httpMethod = "POST")
    @PostMapping("/uploadFace")
    public WUYICCCJSONResult uploadFace(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            MultipartFile file,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        //定义头像保存的地址
        //String fileSpace = IMAGE_USER_FACE_LOCATION;
        String fileSpace = fileUpload.getImageUserFaceLocation();

        //在路径上为每一个用户增加一个userId,用于区分不同用户上传
        String uploadPathPrefix = File.separator + userId;

        //开始文件上传
        if (file != null) {
            FileOutputStream fileOutputStream = null;
            InputStream inputStream = null;

            try {
                String fileName = file.getOriginalFilename(); //获取原始文件名称

                if (StringUtils.isNotBlank(fileName)) {


                    //文件名称分割 imooc-face.png  ->  ["imooc-face","png"]
                    String[] fileNameArr = fileName.split("\\.");

                    //获取文件的后缀名称
                    String suffix = fileNameArr[fileNameArr.length - 1];
                    if(!suffix.equalsIgnoreCase("png")&&
                            !suffix.equalsIgnoreCase("jpg")&&
                            !suffix.equalsIgnoreCase("jpeg")
                    ){
                        return WUYICCCJSONResult.errorMsg("图片格式不正确!");
                    }

                    // face-{userId}.png
                    //文件名称重组,覆盖式上传
                    String newFileName = "face-" + userId + "." + suffix;

                    //上传的头像最终保存的位置
                    String finalFacePath = fileSpace + uploadPathPrefix + File.separator + newFileName;


                    //用于提供给web服务访问的地址
                    uploadPathPrefix += ("/" + newFileName);

                    File outFile = new File(finalFacePath);
                    if (outFile.getParentFile() != null) { //如果有父目录，那么级联创建没有的父目录
                        //创建文件夹
                        outFile.getParentFile().mkdirs();
                    }

                    //文件输出保存到目录
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);

                } else {
                    return WUYICCCJSONResult.errorMsg("上传文件名不能为空!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }


        //获得图片服务地址
        String imageServerUrl = fileUpload.getImageServerUrl();


        // 由于浏览器可能存在缓存的情况，所以我们需要加上时间戳，来保证更新后的图片能够及时刷新
        String finalUserFaceUrl = imageServerUrl + uploadPathPrefix + "?t=" + DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);


        Users userResult = centerUserService.updateUserFace(userId, finalUserFaceUrl) ;

        //对前端信息进行覆盖
        userResult = setNullProperty(userResult);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);


        //TODO: 后续要改，增加令牌token，会整合redis，分布式会话
        return WUYICCCJSONResult.ok();
    }


    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "POST")
    @PostMapping("/update")
    public WUYICCCJSONResult update(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @RequestBody @Valid CenterUserBO centerUserBO,
            BindingResult result, //存放centerUserBO验证失败的错误信息
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (result.hasErrors()) { //判断是否包含有错误的验证信息
            Map<String, String> errorMap = getErrors(result);
            return WUYICCCJSONResult.errorMap(errorMap);
        }

        if (StringUtils.isBlank(userId)) {
            return WUYICCCJSONResult.errorMsg("请输入用户id");
        }

        Users user = centerUserService.updateUserInfo(userId, centerUserBO);
        setNullProperty(user);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(user), true);

        //TODO 后续要改，增加令牌token，会整合进redis，分布式会话


        return WUYICCCJSONResult.ok();
    }

    private Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = new HashMap<>();
        List<FieldError> errorList = result.getFieldErrors();//错误信息列表
        for (FieldError error : errorList) { //循环每一个错误
            String errorField = error.getField();//发生错误所对应的属性
            String errorMsg = error.getDefaultMessage(); // 获取错误内容
            map.put(errorField, errorMsg); //将错误信息存入map中
        }
        return map;

    }

    /**
     * 将users的部分属性设置为空
     *
     * @param userResult
     * @return
     */
    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }

}

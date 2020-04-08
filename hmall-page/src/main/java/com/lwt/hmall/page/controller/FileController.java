package com.lwt.hmall.page.controller;

import com.lwt.hmall.page.config.fdfs.FastDFSClient;
import com.lwt.hmall.page.util.file.type.FileTypeEnum;
import com.lwt.hmall.page.util.file.type.FileTypeUtils;
import com.lwt.hmall.page.util.result.CodeEnum;
import com.lwt.hmall.page.util.result.Result;
import com.lwt.hmall.page.util.result.ResultUtils;
import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author lwt
 * @Date 2020/4/2 19:23
 * @Description
 */
@RestController
@Validated
public class FileController {

    @Autowired
    private FastDFSClient fastDFSClient;

    @RequestMapping(value = "/uploadImage",method = RequestMethod.POST)
    public Result<String> uploadImage(@RequestParam("image") MultipartFile image) throws IOException, MyException {
        byte[] imageBytes = image.getBytes();
        FileTypeEnum fileTypeEnum = FileTypeUtils.getTypeByImageBytes(imageBytes);
        if (fileTypeEnum==null){
            return ResultUtils.result(CodeEnum.RETURN_FALSE);
        }
        String fileUrl = fastDFSClient.uploadFile(imageBytes, fileTypeEnum.toString(), null);
        return ResultUtils.success(fileUrl);
    }

}

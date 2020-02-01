package cn.wuyuwei.tiny_shop.controller;

import cn.wuyuwei.tiny_shop.common.ApiResultEnum;
import cn.wuyuwei.tiny_shop.common.Result;
import cn.wuyuwei.tiny_shop.config.custom_annotation.LoginRequired;
import cn.wuyuwei.tiny_shop.service.UploadService;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Api(tags = "文件上传控制层")
@RequestMapping("/upload")
@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);


    @LoginRequired
    @PostMapping("/avatar")
    public Result avatar(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

        if (file.isEmpty()) {
            return Result.error(ApiResultEnum.ERROR_IO);
        }

        try {
            String filepath = uploadService.doUploadAvatar(file,request);
            LOGGER.info("上传成功");

            Map<String,Object> map = new HashMap<String, Object>();
            map.put("filepath",filepath);
            return Result.ok(map);
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
            return Result.error(ApiResultEnum.ERROR_IO);
        }

    }

}

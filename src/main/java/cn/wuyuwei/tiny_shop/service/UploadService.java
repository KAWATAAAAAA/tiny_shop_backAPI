package cn.wuyuwei.tiny_shop.service;

import cn.wuyuwei.tiny_shop.dao.UserMapper;
import cn.wuyuwei.tiny_shop.utils.HostUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.UUID;


@Service
public class UploadService {
    @Resource
    private UserMapper userMapper;

    @Autowired
    private UserService userService;
    @Autowired
    private HostUtils hostUtils;

    public String doUploadAvatar(MultipartFile file, HttpServletRequest request) throws IOException {
        String fileName = file.getOriginalFilename();
        String filePath = "F:\\毕业设计总指挥部\\upload_repository\\";

        String exts = fileName.substring(fileName.lastIndexOf(".") + 1); //取出扩展名
        fileName = UUID.randomUUID().toString()+"."+exts;   //UUID化 重命名

        System.out.println(filePath);
        System.out.println(fileName);
        File dest = new File(filePath + fileName);  // 创建文件实例

        // 如果文件目录不存在，创建目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
            System.out.println("创建目录" + filePath);
        }

        file.transferTo(dest);      // 写入文件

        String token = request.getHeader("Authorization");// 从 http 请求头中取出 token

        userService.doUploadAvatar(token,hostUtils.getUrl() + "/upload_repository",fileName); //写数据库

        return hostUtils.getUrl() + "/upload_repository/" + fileName;

    }
}

package com.design.elecworkshopsys.utils;

import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 上传工具类
 *
 * @author Guan
 * @version 1.0.0
 */
public class UploadUtil {

    private static String getPath(HttpSession session) throws FileNotFoundException {
        //String path = session.getServletContext().getRealPath("/file");
        //String path = "D:/file";
        String path = ResourceUtils.getURL("classpath:").getPath() + "/" + "static/file";
        new File(path).mkdirs();
        return path;
    }

    public static String upload(MultipartFile multipartFile, HttpSession session) {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            try {
                String originalFilename = multipartFile.getOriginalFilename();
                String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
                String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
                String fileName = uuid + "." + suffix;
                String path = getPath(session);
                multipartFile.transferTo(new File(path, fileName));
                return fileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List<String> upload(MultipartFile[] multipartFiles, HttpSession session) {
        List<String> stringList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                try {
                    String originalFilename = multipartFile.getOriginalFilename();
                    String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
                    String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
                    String fileName = uuid + "." + suffix;
                    String path = getPath(session);
                    multipartFile.transferTo(new File(path, fileName));
                    stringList.add(fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringList;
    }

}

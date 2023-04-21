package com.fit.common.utils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 上传/下载组件
 */
public class FileUnitUtil {

    /**
     * @param response
     * @param filePath //文件完整路径(包括文件名和扩展名)
     * @param fileName //下载后看到的文件名
     * @return 文件名
     */
    public static void fileDownload(final HttpServletResponse response, String filePath, String fileName) throws Exception {
        byte[] data = FileUtil.toByteArray2(filePath);
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
        response.flushBuffer();
    }

    /**
     * 上传文件
     *
     * @param file     //文件对象
     * @param filePath //上传路径
     * @param fileName //文件名
     * @return 文件名
     */
    public static String fileUp(MultipartFile file, String filePath, String fileName) {
        String extName = ""; // 扩展名格式：
        try {
            if (file.getOriginalFilename().lastIndexOf(".") >= 0) {
                extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            }
            copyFile(file.getInputStream(), filePath, fileName + extName).replaceAll("-", "");
        } catch (IOException e) {
            System.out.println(e);
        }
        return fileName + extName;
    }

    /**
     * 写文件到当前目录的upload目录中
     *
     * @param input
     * @param dir
     * @param realName
     * @throws IOException
     */
    private static String copyFile(InputStream input, String dir, String realName) throws IOException {
        File file = mkdirsmy(dir, realName);
        copyInputStreamToFile(input, file);
        return realName;
    }


    /**
     * 判断路径是否存在，否：创建此路径
     *
     * @param dir      文件路径
     * @param realName 文件名
     * @throws IOException
     */
    public static File mkdirsmy(String dir, String realName) throws IOException {
        File file = new File(dir, realName);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        return file;
    }


    /**
     * 下载网络图片上传到服务器上
     *
     * @param httpUrl    图片网络地址
     * @param filePath   图片保存路径
     * @param myFileName 图片文件名(null时用网络图片原名)
     * @return 返回图片名称
     */
    public static String getHtmlPicture(String httpUrl, String filePath, String myFileName) {
        URL url;                //定义URL对象url
        BufferedInputStream in; //定义输入字节缓冲流对象in
        try {
            String fileName = null == myFileName ? httpUrl.substring(httpUrl.lastIndexOf("/")).replace("/", "") : myFileName; //图片文件名(null时用网络图片原名)
            url = new URL(httpUrl);        //初始化url对象
            in = new BufferedInputStream(url.openStream());                                    //初始化in对象，也就是获得url字节流
            File file = mkdirsmy(filePath, fileName); //定义文件输出流对象file
            copyInputStreamToFile(in, file);
            return fileName;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void copyInputStreamToFile(InputStream input, File file) throws IOException {
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        in = new BufferedInputStream(input);
        out = new BufferedOutputStream(new FileOutputStream(file));
        int len = -1;
        byte[] b = new byte[1024];
        while ((len = in.read(b)) != -1) {
            out.write(b, 0, len);
        }
        in.close();
        out.close();
    }
}

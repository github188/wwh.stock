package com.skoo.stock.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.skoo.common.SystemConfig;
import com.skoo.commons.StringUtils;
import com.skoo.orm.service.BaseService;
import com.skoo.stock.api.domain.FileItem;
import com.skoo.stock.api.domain.FileMe;
import com.skoo.stock.common.Constant;
import com.skoo.stock.util.ImageUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class FileUpload extends BaseService<FileMe> {

    protected static final Log logger = LogFactory.getLog(SystemConfig.class);

    private String Web_File_Path = "";

    /**
     * 上传文件
     *
     * @param stream   文件流
     * @param road     路径
     * @param filename 文件名
     * @return 响应字符串
     */
    public static String upload(byte[] stream, String road, String filename) {

        try {
            Map<String, FileItem> map = new HashMap<>();

            FileItem item = new FileItem(filename, stream);
            map.put("Filedata", item);

            Map<String, String> param = new HashMap<>();
            param.put("road", road);

            String uploadApiUrl = SystemConfig.INSTANCE.getValue("remoteFileUpload");
            String res = WebUtils.doPost(uploadApiUrl,
                    param, map, "utf-8", 5000,
                    5000);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 上传文件
     *
     * @param stream   文件流
     * @param param    上传参数
     * @param filename 文件名
     * @return
     */
    public final static String picUpload(byte[] stream, Map<String, String> param, String filename) {

        try {
            Map<String, FileItem> map = new HashMap<>();

            FileItem item = new FileItem(filename, stream);
            map.put("Filedata", item);

            String uploadApiUrl;

            // 是否处理图片
            if (Constant.YES.equals(param.get(Constant.UPLOAD_REMOTE_PRC))) {
                uploadApiUrl = SystemConfig.INSTANCE.getValue("remotePicUpload");
            } else {
                uploadApiUrl = SystemConfig.INSTANCE.getValue("remotePicUploadNoPrc");
            }

            return WebUtils.doPost(uploadApiUrl,
                    param, map, "utf-8", 5000,
                    5000);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 上传文件
     *
     * @param stream   文件流
     * @param param    各种参数
     * @param filename 文件名
     * @return 响应字符串
     */
    public static String upload(byte[] stream, Map<String, String> param, String filename) {

        try {
            Map<String, FileItem> map = new HashMap<>();

            FileItem item = new FileItem(filename, stream);
            map.put("Filedata", item);

            String uploadApiUrl = SystemConfig.INSTANCE.getValue("remoteFileUpload");
            String res = WebUtils.doPost(uploadApiUrl,
                    param, map, "utf-8", 5000,
                    5000);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public String saveFile(MultipartHttpServletRequest multipartRequest,
                           Map<String, String> param) throws Exception {
        Map<String, Object> result = new HashMap<>();
        String uploadServer = SystemConfig.INSTANCE.getValue("uploadServer");
        // 本地上传
        if (Constant.UPLOAD_TYPE_LOCAL.equals(uploadServer)) {
            for (Iterator<String> it = multipartRequest.getFileNames(); it.hasNext(); ) {
                String key = it.next();
                MultipartFile file = multipartRequest.getFile(key);

                FileInputStream inputStream = (FileInputStream) file.getInputStream();
                BufferedImage bi = ImageIO.read(inputStream);
                String configpath = SystemConfig.INSTANCE.getValue("file_upload_path");
                String saveFilePath = getPhysicalPath(configpath, multipartRequest);
                String filename;
                filename = file.getOriginalFilename();
                filename = gettimesName(filename);
                String realpath = saveFilePath + filename;

                int oriw = Integer.parseInt(param.get("oriw"));
                int orih = Integer.parseInt(param.get("orih"));
                double zoomin;
                if (oriw < orih) {
                    zoomin = oriw / Double.parseDouble(param.get("rew"));
                } else {
                    zoomin = orih / Double.parseDouble(param.get("reh"));
                }
                ImageUtil.abscutStream(bi, (int) (zoomin * Double.parseDouble(param.get("x1"))),
                        (int) (zoomin * Double.parseDouble(param.get("y1"))),
                        (int) (zoomin * Double.parseDouble(param.get("w"))),
                        (int) (zoomin * Double.parseDouble(param.get("h"))), realpath);

                result.put("status", Constant.UPLOAD_SUCC_STATUS);
                result.put("msg", "上传失败：上传文件为空！");
                result.put("path", SystemConfig.INSTANCE.getValue("localPicDomain")
                        + Web_File_Path + filename);
            }
        } else { // 远程上传
            // 获取多个file
            for (Iterator<String> it = multipartRequest.getFileNames(); it.hasNext(); ) {
                String key = it.next();
                MultipartFile file = multipartRequest.getFile(key);

                if (file.getSize() > 0) {
                    String jsonUpload = picUpload(file.getBytes(), param, file.getOriginalFilename());
                    JSONObject jsonObject = JSON.parseObject(jsonUpload);

                    // 成功上传
                    if (!StringUtils.isEmpty(jsonUpload) && Constant.UPLOAD_SUCC_STATUS.equals(jsonObject.get("status"))) {
                        result.put("status", Constant.UPLOAD_SUCC_STATUS);
                        result.put("msg", "文件上传成功！");
                        result.put("path", SystemConfig.INSTANCE.getValue("remotePicDomain")
                                + jsonObject.get("path").toString());
                    } else {
                        result.put("status", "3001");
                        result.put("msg", "上传失败：图片远程接口调用失败！");
                    }
                } else {
                    result.put("status", "3001");
                    result.put("msg", "上传失败：上传文件为空！");
                }
            }
        }

        return JSONObject.toJSONString(result);
    }

    /**
     * 根据传入的虚拟路径获取物理路径
     *
     * @param path 路径
     * @return 物理路径
     */
    public String getPhysicalPath(String path, MultipartHttpServletRequest Request) {
        String realPath = Request.getSession().getServletContext().getRealPath("/");
        String tempPath = realPath + "/" + path;

        File dirroot = new File(tempPath);
        if (!dirroot.exists()) {
            dirroot.mkdirs();
        }
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
        tempPath += formater.format(new Date());
        File dirdate = new File(tempPath);
        if (!dirdate.exists()) {
            try {
                dirdate.mkdirs();
            } catch (Exception e) {
                return "上传失败：上传路径创建失败。";
            }
        }
        this.Web_File_Path = path + "/" + formater.format(new Date()) + "/";
        return tempPath + "/";
    }

    /**
     * 配置文件名:时间+原文件名
     *
     * @param fileName 文件名
     * @return 新的文件名
     */
    public String gettimesName(String fileName) {
        Random random = new Random();
        return random.nextInt(10000) + System.currentTimeMillis() + getFileExt(fileName);
    }

    /**
     * 获取文件扩展名
     *
     * @return string
     */
    public String getFileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

}

package cn.hzskt.bdtg.api.service;

import cn.hzskt.bdtg.api.domain.FileItem;
import cn.hzskt.bdtg.api.domain.FileMe;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.ryian.core.SystemConfig;
import net.ryian.orm.service.BaseService;
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
public class FileUpload  {

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

            String uploadApiUrl = "";

            // 是否处理图片
//            if (Constant.YES.equals(param.get(Constant.UPLOAD_REMOTE_PRC))) {
//                uploadApiUrl = SystemConfig.INSTANCE.getValue("remotePicUpload");
//            } else {
//                uploadApiUrl = SystemConfig.INSTANCE.getValue("remotePicUploadNoPrc");
//            }

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

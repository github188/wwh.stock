package com.skoo.stock.api.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.skoo.common.SystemConfig;
import com.skoo.commons.StringUtils;
import com.skoo.core.utils.JsonUtils;
import com.skoo.orm.service.support.query.Condition;
import com.skoo.stock.api.domain.A;
import com.skoo.stock.api.domain.FileMe;
import com.skoo.stock.api.service.FileUpload;
import com.skoo.stock.common.Constant;
import com.skoo.stock.common.action.ManAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/api/file")
@SuppressWarnings("serial")
public class FileAction extends ManAction<FileMe, FileUpload> {

    private String Web_File_Path = "";

    @Autowired
    private FileUpload fileUpload;

    /**
     * 文件上传
     */
    @RequestMapping(value = "uploadForApp")
    public void uploadForApp(MultipartHttpServletRequest multipartRequest,
                             HttpServletResponse response) throws Exception {
        Map<String, String> param = super.bindMap(multipartRequest);
        param.put(Constant.UPLOAD_REMOTE_PRC, Constant.NO); //上传是否处理标志
        String jsonUpload = fileUpload.saveFile(multipartRequest, param);
        JSONObject jsonObject = JSON.parseObject(jsonUpload);
        Map<String, Object> map = new LinkedHashMap<>();
        if (!StringUtils.isEmpty(jsonUpload) && Constant.UPLOAD_SUCC_STATUS.equals(jsonObject.get("status"))) {
            map.put(A.CODE, "100");
            map.put(A.MSG, "图片上传接口调用成功！");
            map.put(A.DATA, jsonObject); // 调用成功返回数据
            printText(response, JsonUtils.bean2Api(map));
        } else {
            map.put(A.CODE, Constant.RETURN_CODE_UPLOAD_ERROR);
            map.put(A.MSG, "图片上传接口调用失败！");
            printText(response, JsonUtils.bean2Api(map));
        }
    }

    /**
     * 查看图片
     */
    @RequestMapping(value = "showPic")
    public String showPic(HttpServletRequest request, Model model) throws Exception {
        try {
            Condition condition = bindCondition(request);

            model.addAttribute("imageurl", condition.getMap().get("imageurl"));
            return super.getNameSpace() + "showPic";
        } catch (Exception e) {
            logger.error("FileAction.showPic", e);
            throw e;
        }
    }

    /**
     * 文件上传
     */
    @RequestMapping(value = "uploadMulti")
    public void uploadMulti(MultipartHttpServletRequest multipartRequest,
                            HttpServletResponse response) throws Exception {
        String rtn = uploadCommon(multipartRequest, response, "/up");
        printHtml(response, rtn);
    }

    public String uploadCommon(MultipartHttpServletRequest multipartRequest,
                               HttpServletResponse response, String road) throws Exception {
        Map<String, Object> result = new HashMap<>();
        String returnJson = "";
        try {
            String uploadServer = SystemConfig.INSTANCE.getValue("uploadServer");
            // 向远程资源服务器上传文件
            if ("remote".equals(uploadServer)) {
                // 获取多个file
                for (Iterator<String> it = multipartRequest.getFileNames(); it.hasNext(); ) {
                    String key = it.next();
                    MultipartFile file = multipartRequest.getFile(key);

                    if (file.getSize() > 0) {
                        String jsonUpload = FileUpload.upload(file.getBytes(), road, file.getOriginalFilename());
                        JSONObject jsonObject = JSON.parseObject(jsonUpload);

                        return JSONObject.toJSONString(jsonObject);
                    } else {
                        result.put("status", "3001");
                        result.put("msg", "上传失败：上传文件为空！");
                        return JSON.toJSONString(result);
                    }
                }
                result.put("status", "100");
                result.put("msg", "上传成功！");
                result.put("path", Web_File_Path + "filename");
                returnJson = JSON.toJSONString(result);
            } else {
                String configpath = SystemConfig.INSTANCE.getValue("file_upload_path");
                if (configpath == null || "".equals(configpath)) {
                    result.put("status", "3001");
                    result.put("msg", "上传失败：配置文件中未配置上传路径！【配置key=file_upload_path】");
                    return JSON.toJSONString(result);
                }
                response.setContentType("text/html;charset=UTF-8");
                String saveFilePath = getPhysicalPath(configpath, multipartRequest);
                // 获取多个file
                for (Iterator<String> it = multipartRequest.getFileNames(); it.hasNext(); ) {
                    String key = it.next();
                    MultipartFile file = multipartRequest.getFile(key);

                    String filename = "";
                    if (file.getSize() > 0) {
                        Map<String, String> param = super.bindMap(multipartRequest);
                        filename = file.getOriginalFilename();
                        filename = gettimesName(filename);
                        FileOutputStream fos = new FileOutputStream(saveFilePath + filename);
                        BufferedInputStream bis = new BufferedInputStream(file.getInputStream());
                        byte[] buff = new byte[128];
                        int count;
                        while ((count = bis.read(buff)) != -1) {
                            fos.write(buff, 0, count);
                        }

                        bis.close();
                        fos.close();

                    } else {
                        result.put("status", "3001");
                        result.put("msg", "上传失败：上传文件为空！");
                        returnJson = JSON.toJSONString(result);
                    }
                    result.put("status", "100");
                    result.put("msg", "上传成功！");
                    result.put("path", Web_File_Path + filename);
                    returnJson = JSON.toJSONString(result);
                }
            }

        } catch (Exception e) {
            logger.error("FileAction.uploadMulti", e);
            result.put("status", "3001");
            result.put("msg", "上传失败：上传发生系统异常！");
            returnJson = JSON.toJSONString(result);
        } finally {
            return returnJson;
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

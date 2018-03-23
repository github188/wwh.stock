package cn.hzskt.bdtg.api.action;

import cn.hzskt.bdtg.api.service.FileUpload;
import cn.hzskt.bdtg.common.action.BaseMagicAction;
import cn.hzskt.bdtg.common.utils.oss.OSSConfig;
import cn.hzskt.bdtg.common.utils.oss.OSSUtil;

import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/api/file")
@SuppressWarnings("serial")
public class FileAction  extends BaseMagicAction {

    private String Web_File_Path = "";

    @Autowired
    private FileUpload fileUpload;

    /**
     * 文件上传
     */
    @RequestMapping(value = "delete")
    public void delete(HttpServletRequest request,
                       HttpServletResponse response) throws Exception {
    	Map<String, String> map = getParameterMap(request);
    	   PrintWriter out = null;
    	try {
    		String url = map.get("url");
    		String type = map.get("type");
    		String ossDomain = OSSConfig.INSTANCE.getOssDomain("url_file");
    		String bucketName = OSSConfig.INSTANCE.getFileBucket();
    		if("img".equals(type)){
    			 ossDomain = OSSConfig.INSTANCE.getOssDomain("url_img");
        		 bucketName = OSSConfig.INSTANCE.getImgBucket();
    		}
    		OSSUtil.delete(bucketName, url.substring(ossDomain.length()+2));
    		map.put("status", "1");
        
            out = response.getWriter();
           
        } catch (IOException e) {
        	map.put("msg", "1");
            e.printStackTrace();
        } finally {
        	out.append(JSONObject.toJSONString(map));
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 文件上传
     */
    @RequestMapping(value = "upload")
    public void upload(MultipartHttpServletRequest multipartRequest,
                             HttpServletResponse response) throws Exception {

        String category = multipartRequest.getParameter("ossType");
        MultipartFile file = multipartRequest.getFile(multipartRequest.getFileNames().next());
        String filename = fileUpload.gettimesName(file.getOriginalFilename());
        String osspath = category + "/" + filename;
        String bucketName = OSSConfig.INSTANCE.getFileBucket();
        if(!category.equals("file")){
            bucketName = OSSConfig.INSTANCE.getImgBucket();
        }
        OSSUtil.upload(file.getInputStream(),bucketName, osspath);

        //err=0上传成功，否则上传失败
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("err", 0);
        Map<String, Object> map2 = new LinkedHashMap<>();
        String ossDomain = OSSConfig.INSTANCE.getOssDomain(category);
        map2.put("url", ossDomain + "/" + osspath);
        map2.put("name", filename);
        map2.put("fileid", "1111");
        map.put("msg", map2);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(JSONObject.toJSONString(map));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }

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

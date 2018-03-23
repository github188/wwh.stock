package cn.hzskt.bdtg.common.Api;

import cn.hzskt.bdtg.common.action.BaseMagicAction;
import cn.hzskt.bdtg.common.util.oss.OSSConfig;
import cn.hzskt.bdtg.common.util.oss.OSSUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/api/file")
@SuppressWarnings("serial")
public class FileUploadAction extends BaseMagicAction {

    /**
     *删除
     */
    public static boolean delete(String path) throws Exception {
        try {
            String ossDomain = OSSConfig.INSTANCE.getOssDomain("fileInfo");
            String bucketName = OSSConfig.INSTANCE.getInfoBucket();
            if(path!=null){
                OSSUtil.delete(bucketName, path.substring(ossDomain.length()+1));
            }
        return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 上传
     */
    @RequestMapping(value = "/fileupload")
    public void upload(MultipartHttpServletRequest multipartRequest,
                             HttpServletResponse response) throws Exception {

        String category = multipartRequest.getParameter("ossType");
        MultipartFile file = multipartRequest.getFile(multipartRequest.getFileNames().next());
        String filename = gettimesName(file.getOriginalFilename());
        String osspath = category + "/" + filename;
        String bucketName = OSSConfig.INSTANCE.getInfoBucket();
        if(!category.equals("fileInfo")){
            bucketName = OSSConfig.INSTANCE.getImgBucket();
        }
        if("condition".equals(category)){
        	bucketName = OSSConfig.INSTANCE.getFileBucket();
        }
        OSSUtil.upload(file.getInputStream(),bucketName, osspath);
        //err=0
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("err", 0);
        Map<String, Object> map2 = new LinkedHashMap<>();
        String ossDomain = OSSConfig.INSTANCE.getOssDomain(category);
        String fileid = multipartRequest.getParameter("id");
        map2.put("url", ossDomain + "/" + osspath);
        map2.put("name", filename);
        map2.put("fileid", "1");
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
     * 閰嶇疆鏂囦欢鍚�鏃堕棿+鍘熸枃浠跺悕
     *
     * @param fileName 鏂囦欢鍚�
     * @return 鏂扮殑鏂囦欢鍚�
     */
    public String gettimesName(String fileName) {
        Random random = new Random();
        return random.nextInt(10000) + System.currentTimeMillis() + getFileExt(fileName);
    }

    /**
     * 鑾峰彇鏂囦欢鎵╁睍鍚�
     *
     * @return string
     */
    public String getFileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}

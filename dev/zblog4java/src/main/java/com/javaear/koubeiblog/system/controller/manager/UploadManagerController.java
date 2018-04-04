package com.javaear.koubeiblog.system.controller.manager;

import com.javaear.koubeiblog.system.controller.AbstractManagerController;
import com.javaear.koubeiblog.system.entity.model.UploadModel;
import com.javaear.koubeiblog.system.entity.model.UserModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;

/**
 * @author aooer
 */
@Controller
public class UploadManagerController extends AbstractManagerController<UploadModel> {

    @RequestMapping("upload-management")
    public void list(ModelMap modelMap) {
        pageQuery(modelMap);
        modelMap.put("title", "附件管理");
    }

    @RequestMapping("upload-add")
    public String upload(ModelMap modelMap) {
        MultipartFile uploadFile = ((DefaultMultipartHttpServletRequest) request).getFile("uploadFile");
        if (!uploadFile.isEmpty()) {
            try {
                File targetFile = new File("./src/main/webapp/resources/system/upload-dir/", uploadFile.getOriginalFilename());
                if (!targetFile.exists())
                    targetFile.mkdirs();
                uploadFile.transferTo(targetFile);
                UploadModel uploadModel = new UploadModel();
                uploadModel.setSource(targetFile.getPath());
                uploadModel.setSize((int) (uploadFile.getSize() / 1024));
                uploadModel.setName(uploadFile.getOriginalFilename());
                uploadModel.setMimeType(uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf(".") + 1));
                uploadModel.check();
                generalMapper.insertSelective(uploadModel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return String.format(MNG_URL, MODEL_NAME);
    }

    @RequestMapping("upload-remove")
    public String remove() {
        String id = request.getParameter("id");
        UploadModel uploadModel = generalMapper.selectById(id, UploadModel.class);
        //关联用户上传附件-1
        UserModel userModel = generalMapper.selectById(uploadModel.getUserId(), UserModel.class);
        userModel.setUploads(userModel.getUploads() - 1);
        generalMapper.updateSelectiveById(userModel);
        return removeById();
    }
}

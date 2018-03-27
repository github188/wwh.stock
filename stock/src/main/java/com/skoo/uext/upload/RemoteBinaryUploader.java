package com.skoo.uext.upload;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.skoo.uext.PathFormat;
import com.skoo.uext.define.AppInfo;
import com.skoo.uext.define.BaseState;
import com.skoo.uext.define.FileType;
import com.skoo.uext.define.State;
import com.skoo.stock.api.service.FileUpload;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RemoteBinaryUploader {

	public static final State save(HttpServletRequest request,
			Map<String, Object> conf) {
		State state;
		FileItemStream fileStream = null;
		boolean isAjaxUpload = request.getHeader( "X_Requested_With" ) != null;

		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}

		ServletFileUpload upload = new ServletFileUpload(
				new DiskFileItemFactory());

        if ( isAjaxUpload ) {
            upload.setHeaderEncoding("UTF-8");
        }

		try {
			FileItemIterator iterator = upload.getItemIterator(request);

			while (iterator.hasNext()) {
				fileStream = iterator.next();

				if (!fileStream.isFormField())
					break;
				fileStream = null;
			}

			if (fileStream == null) {
				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
			}

			String originFileName = fileStream.getName();
			String suffix = FileType.getSuffixByFilename(originFileName);

			originFileName = originFileName.substring(0,
					originFileName.length() - suffix.length());

			InputStream is = fileStream.openStream();

			// 远程文件上传
			byte[] bytes = IOUtils.toByteArray(is);
			String toFold = "/swt";//todo
			String jsonUpload = FileUpload.upload(bytes, toFold, originFileName + suffix);
			JSONObject jsonObject = JSON.parseObject(jsonUpload);
			is.close();

			// 远程上传成功返回100
			if ("100".equals(jsonObject.get("status"))) {
				state = new BaseState(true);
				state.putInfo("url", PathFormat.format((String) jsonObject.get("path")));
				state.putInfo("type", suffix);
				state.putInfo("original", originFileName + suffix);
			} else {
				state = new BaseState(false, AppInfo.REMOTE_UPLOAD_ERROR);
			}

			return state;
		} catch (FileUploadException e) {
			return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}
}

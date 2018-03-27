package com.skoo.uext.upload;

import com.skoo.common.SystemConfig;
import com.skoo.uext.define.State;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class Uploader {
	private HttpServletRequest request = null;
	private Map<String, Object> conf = null;

	public Uploader(HttpServletRequest request, Map<String, Object> conf) {
		this.request = request;
		this.conf = conf;
	}

	public final State doExec() {
		String filedName = (String) this.conf.get("fieldName");
		State state;

		if ("true".equals(this.conf.get("isBase64"))) {
			state = Base64Uploader.save(this.request.getParameter(filedName),
					this.conf);
		} else {
			String uploadServer = SystemConfig.INSTANCE.getValue("uploadServer");
			// 向远程资源服务器上传文件
			if ("remote".equals(uploadServer)) {
				state = RemoteBinaryUploader.save(this.request, this.conf);
			} else {
				state = BinaryUploader.save(this.request, this.conf);
			}
		}

		return state;
	}
}

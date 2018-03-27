package com.skoo.uext.upload;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.skoo.uext.PathFormat;
import com.skoo.uext.define.AppInfo;
import com.skoo.uext.define.BaseState;
import com.skoo.uext.define.FileType;
import com.skoo.uext.define.State;
import com.skoo.stock.api.service.FileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;

public class RemoteStorageManager {
	public static final int BUFFER_SIZE = 8192;

	public RemoteStorageManager() {
	}

	public static State saveBinaryFile(byte[] data, String path) {
		File file = new File(path);

		State state = valid(file);

		if (!state.isSuccess()) {
			return state;
		}

		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file));
			bos.write(data);
			bos.flush();
			bos.close();
		} catch (IOException ioe) {
			return new BaseState(false, AppInfo.IO_ERROR);
		}

		state = new BaseState(true, file.getAbsolutePath());
		state.putInfo( "size", data.length );
		state.putInfo( "title", file.getName() );
		return state;
	}

	public static State saveFileByInputStream(InputStream is, String path,
			long maxSize) {
		State state = null;

		File tmpFile = getTmpFile();

		byte[] dataBuf = new byte[ 2048 ];
		BufferedInputStream bis = new BufferedInputStream(is, RemoteStorageManager.BUFFER_SIZE);

		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(tmpFile), RemoteStorageManager.BUFFER_SIZE);

			int count = 0;
			while ((count = bis.read(dataBuf)) != -1) {
				bos.write(dataBuf, 0, count);
			}
			bos.flush();
			bos.close();

			if (tmpFile.length() > maxSize) {
				tmpFile.delete();
				return new BaseState(false, AppInfo.MAX_SIZE);
			}

			state = saveTmpFile(tmpFile, path);

			if (!state.isSuccess()) {
				tmpFile.delete();
			}

			return state;
			
		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	public static State saveFileByInputStream(InputStream is, String path) {
		State state = null;

		File tmpFile = getTmpFile();

		byte[] dataBuf = new byte[ 2048 ];
		BufferedInputStream bis = new BufferedInputStream(is, RemoteStorageManager.BUFFER_SIZE);

		try {
			String originFileName =path.substring(path.lastIndexOf("/") + 1, path .length());
			String suffix = path.substring(path.lastIndexOf("."));

			originFileName = originFileName.substring(0,
					originFileName.length() - suffix.length());

			// 远程文件上传
			byte[] bytes = IOUtils.toByteArray(is);
			String toFold = "/swt/3rd";//todo
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

//			BufferedOutputStream bos = new BufferedOutputStream(
//					new FileOutputStream(tmpFile), RemoteStorageManager.BUFFER_SIZE);
//
//			int count = 0;
//			while ((count = bis.read(dataBuf)) != -1) {
//				bos.write(dataBuf, 0, count);
//			}
//			bos.flush();
//			bos.close();
//
//			state = saveTmpFile(tmpFile, path);
//
//			if (!state.isSuccess()) {
//				tmpFile.delete();
//			}

			return state;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	private static File getTmpFile() {
		File tmpDir = FileUtils.getTempDirectory();
		String tmpFileName = (Math.random() * 10000 + "").replace(".", "");
		return new File(tmpDir, tmpFileName);
	}

	private static State saveTmpFile(File tmpFile, String path) {
		State state = null;
		File targetFile = new File(path);

		if (targetFile.canWrite()) {
			return new BaseState(false, AppInfo.PERMISSION_DENIED);
		}
		try {
			FileUtils.moveFile(tmpFile, targetFile);
		} catch (IOException e) {
			return new BaseState(false, AppInfo.IO_ERROR);
		}

		state = new BaseState(true);
		state.putInfo( "size", targetFile.length() );
		state.putInfo( "title", targetFile.getName() );
		
		return state;
	}

	private static State valid(File file) {
		File parentPath = file.getParentFile();

		if ((!parentPath.exists()) && (!parentPath.mkdirs())) {
			return new BaseState(false, AppInfo.FAILED_CREATE_FILE);
		}

		if (!parentPath.canWrite()) {
			return new BaseState(false, AppInfo.PERMISSION_DENIED);
		}

		return new BaseState(true);
	}
}

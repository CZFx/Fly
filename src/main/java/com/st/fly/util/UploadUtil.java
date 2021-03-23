package com.st.fly.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class UploadUtil {

	/**
	 * 将文件保存到指定路径 
	 * @return
	 */
	public static String save(MultipartFile file, String path) {
		
		// 验证文件是不是传了
		if (file.getSize() <= 0) {
			return null;
		}
		
		// 验证文件夹是否存在
		File pathFile = new File(path);
		if (!pathFile.exists()) {
			// 创建
			if (!pathFile.mkdirs()) {
				return null;
			}
		}
		
		// 生UUID作为新的文件名
		String fileName = UUID.randomUUID().toString();
		String oldName = file.getOriginalFilename();
		
		if (oldName.lastIndexOf(".")>=0) {
			String suffix = oldName.substring(oldName.lastIndexOf("."));
			fileName += suffix;
		}
		
		// 创建一个文件流
		try (OutputStream out = new FileOutputStream(path + fileName) ){
			// 把上传的文件,写到这个流里面
			out.write(file.getBytes());
			
			// 写成功了
			return fileName;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return null;
	}
}

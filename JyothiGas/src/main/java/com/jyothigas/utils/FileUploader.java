package com.jyothigas.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jyothigas.app.dao.KYCDocumentDAO;
import com.jyothigas.app.entity.KYCDocumentEntity;

@Service
@Transactional
public class FileUploader {

	@Autowired
	KYCDocumentDAO kycDao;

	public void uploadFile(int custId, MultipartFile file, String type) throws IOException {

		byte[] bytes = file.getBytes();

		// Creating the directory to store file
		String rootPath = System.getProperty("catalina.home");
		File dir = new File(rootPath + File.separator + "KYCDocuments" + File.separator + custId);
		if (!dir.exists())
			dir.mkdirs();
		String fileName = file.getOriginalFilename();
		String extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		// Create the file on server
		File serverFile = new File(dir.getAbsolutePath() + File.separator + "KYC_" + custId + extension);
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
		stream.write(bytes);
		stream.close();

		// Save document
		KYCDocumentEntity entity = kycDao.findByCustomerId(custId, type);
		if (null == entity) {
			entity = new KYCDocumentEntity();
		}
		entity.setCreatedDate(Calendar.getInstance().getTime());
		entity.setDocumentName(file.getName());
		entity.setLocation(serverFile.getAbsolutePath());
		entity.setCustId(custId);
		entity.setType(type);
		kycDao.merge(entity);
	}

	public File downloadFile(int custId, String type) {
		KYCDocumentEntity doc = kycDao.findByCustomerId(custId, type);

		File file = new File(doc.getLocation());
		if (file.exists()) {
			return file;
		}
		return null;

	}
}

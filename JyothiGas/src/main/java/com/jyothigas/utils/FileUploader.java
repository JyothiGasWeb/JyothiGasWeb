package com.jyothigas.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jyothigas.app.dao.KYCDocumentDAO;
import com.jyothigas.app.entity.KYCDocumentEntity;
import com.jyothigas.app.model.KYCDocument;

@Service
@Transactional
public class FileUploader {

	@Autowired
	KYCDocumentDAO kycDao;

	public void uploadFile(int custId, MultipartFile file, String type, String docType) throws IOException {

		byte[] bytes = file.getBytes();

		// Creating the directory to store file
		String rootPath = System.getProperty("catalina.home");
		File dir = new File(
				rootPath + File.separator + "KYCDocuments" + File.separator + custId + File.separator + docType);
		if (!dir.exists())
			dir.mkdirs();
		String originalFileName = file.getOriginalFilename();
		// String fileName=
		// originalFileName.substring(0,originalFileName.lastIndexOf("."));
		// String extension =
		// originalFileName.substring(originalFileName.lastIndexOf("."),
		// originalFileName.length());
		// Create the file on server
		File serverFile = new File(dir.getAbsolutePath() + File.separator + originalFileName);
		System.out.println(serverFile.getAbsolutePath());
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
		stream.write(bytes);
		stream.close();

		// Save document
		KYCDocumentEntity entity = kycDao.findByCustomerIdnType(custId, type, docType);
		System.out.println(entity);
		if (null == entity) {
			entity = new KYCDocumentEntity();
		}
		System.out.println(entity);
		entity.setCreatedDate(Calendar.getInstance().getTime());
		entity.setDocumentName(file.getOriginalFilename());
		entity.setLocation(serverFile.getAbsolutePath());
		entity.setCustId(custId);
		entity.setType(type);
		entity.setDocType(docType);
		kycDao.persist(entity);
	}

	public File downloadFile(int custId, String type) {
		try {
			List<KYCDocument> doc = getDocumentDetails(custId, type);
			if (doc.size() > 0) {
				File file = new File(doc.get(0).getLocation());
				if (file.exists()) {
					return file;
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}

	// Get Document details
	public List<KYCDocument> getDocumentDetails(int custId, String type)
			throws IllegalAccessException, InvocationTargetException {
		List<KYCDocumentEntity> docList = kycDao.findByCustomerId(custId, type);
		List<KYCDocument> docNameList = new ArrayList<KYCDocument>();
		if (null != docList) {
			for (KYCDocumentEntity doc : docList) {
				KYCDocument newDoc = new KYCDocument();
				BeanUtils.copyProperties(newDoc, doc);
				docNameList.add(newDoc);
			}
		}
		return docNameList;
	}
}

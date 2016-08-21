package com.jyothigas.app.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jyothigas.app.dao.KYCDocumentDAO;
import com.jyothigas.app.entity.KYCDocumentEntity;

@Transactional
@Service
public class KYCService {

	@Autowired
	KYCDocumentDAO kycDao;

	public void saveDocument(String name, String location, int custId) {

		KYCDocumentEntity entity = kycDao.findByCustomerId(custId);
		if (null == entity) {
			entity = new KYCDocumentEntity();
		}
		entity.setCreatedDate(Calendar.getInstance().getTime());
		entity.setDocumentName(name);
		entity.setLocation(location);
		entity.setCustId(custId);
		kycDao.merge(entity);
	}

	public KYCDocumentEntity getDocument(int custId) {
		KYCDocumentEntity entity = kycDao.findByCustomerId(custId);
		return entity;
	}

}

package com.jyothigas.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jyothigas.app.entity.KYCDocumentEntity;

@Repository
public class KYCDocumentDAO extends JyothiGasDAO<KYCDocumentEntity> {

	public KYCDocumentEntity findByCustomerId(int id) {
		List<KYCDocumentEntity> list = new ArrayList<KYCDocumentEntity>();
		try {
			list = entityManager
					.createQuery("select s from KYCDocumentEntity s Where s.CustId = :id", KYCDocumentEntity.class)
					.setParameter("id", id).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

}

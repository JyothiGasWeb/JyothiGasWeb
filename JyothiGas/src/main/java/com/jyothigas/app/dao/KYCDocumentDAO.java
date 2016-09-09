package com.jyothigas.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jyothigas.app.entity.KYCDocumentEntity;

@Repository
public class KYCDocumentDAO extends JyothiGasDAO<KYCDocumentEntity> {

	public KYCDocumentEntity findByCustomerIdnType(int id, String type, String docType) {
		List<KYCDocumentEntity> list = new ArrayList<KYCDocumentEntity>();
		try {
			list = entityManager
					.createQuery(
							"select s from KYCDocumentEntity s Where s.custId =:id and s.type =:type and s.docType =:docType",
							KYCDocumentEntity.class)
					.setParameter("id", id).setParameter("type", type).setParameter("docType", docType).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (list.size() > 0) {
			System.out.println("DOC LIST >0");
			return list.get(0);
		} else {
			return null;
		}
	}

	public List<KYCDocumentEntity> findByCustomerId(int id, String type) {
		List<KYCDocumentEntity> list = null;
		try {
			list = entityManager.createQuery("select s from KYCDocumentEntity s Where s.custId =:id and s.type =:type",
					KYCDocumentEntity.class).setParameter("id", id).setParameter("type", type).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}
}

package com.jyothigas.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jyothigas.app.entity.KYCDocumentEntity;

@Repository
public class KYCDocumentDAO extends JyothiGasDAO<KYCDocumentEntity> {

	public KYCDocumentEntity findByCustomerId(int id, String type) {
		List<KYCDocumentEntity> list = new ArrayList<KYCDocumentEntity>();
		try {
			list = entityManager.createQuery("select s from KYCDocumentEntity s Where s.custId =:id and s.type =:type",
					KYCDocumentEntity.class).setParameter("id", id).setParameter("type", type).getResultList();
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

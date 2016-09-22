package com.jyothigas.app.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.jyothigas.app.entity.KYCDocumentEntity;
import com.jyothigas.app.entity.SubRegionEntity;

@Repository
public class DealerSubRegionDAO extends JyothiGasDAO<KYCDocumentEntity> {

	@SuppressWarnings("unchecked")
	public List<Integer> getSubRegionIds(int dealerId) {
		List<Integer> regionIds = new ArrayList<Integer>();
		Query query = entityManager
				.createQuery("SELECT regionId from DealerRegionEntity s where dealerId=:dealerId", Object[].class)
				.setParameter("cust_ids", dealerId);
		List<Object[]> resultList = query.getResultList();
		regionIds = new ArrayList<Integer>(resultList.size());
		for (Object[] result : resultList) {
			regionIds.add(Integer.parseInt(String.valueOf(result)));
		}
		return regionIds;
	}

	public List<SubRegionEntity> getSubRegionList(List<Integer> regionIds) {
		List<SubRegionEntity> subRegion = new ArrayList<SubRegionEntity>();
		try{
		subRegion = entityManager
				.createQuery("SELECT s from SubRegionEntity s where id in (:regionIds)", SubRegionEntity.class)
				.setParameter("regionIds", regionIds).getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return subRegion;
	}
}

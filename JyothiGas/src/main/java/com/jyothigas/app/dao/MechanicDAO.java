package com.jyothigas.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.jyothigas.app.entity.MechanicEntity;

@Repository
public class MechanicDAO extends JyothiGasDAO<MechanicEntity> {
	private static final Log log = LogFactory.getLog(MechanicEntity.class);

	public List<MechanicEntity> getMechanicByDealerId(int id) {
		log.info("getting Mechanic details");
		List<MechanicEntity> instance = new ArrayList<MechanicEntity>();
		try {

			instance = entityManager
					.createQuery("select s from MechanicEntity s where s.dealerId=:dealerId", MechanicEntity.class)
					.setParameter("dealerId", id).getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("get failed", re);

		}
		return instance;
	}
}

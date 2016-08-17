package com.jyothigas.app.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.jyothigas.app.entity.ApplianceEntity;

@Repository("applianceDAO")
public class ApplianceDAO extends JyothiGasDAO<ApplianceEntity> {

	@PersistenceContext
	EntityManager entityManger;

	private static final Log log = LogFactory.getLog(ApplianceDAO.class);

	public List<ApplianceEntity> findAllAppliances() {
		log.info("Getting ApplianceEntity Instance...");
		List<ApplianceEntity> applianceList = new ArrayList<ApplianceEntity>();
		try {
			applianceList = entityManager.createQuery("select s from ApplianceEntity s ", ApplianceEntity.class)
					.getResultList();
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
		}
		return applianceList;
	}

	public List<ApplianceEntity> findAllAppliancesById(List<Integer> ids) {
		log.info("Getting ApplianceEntity Instance...");
		List<ApplianceEntity> applianceList = new ArrayList<ApplianceEntity>();
		try {
			applianceList = entityManager
					.createQuery("select s from ApplianceEntity s where s.id in (:ids)", ApplianceEntity.class)
					.setParameter("ids", ids).getResultList();
			log.info("get successfull");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed : " + e);
		}
		return applianceList;
	}

}

package com.jyothigas.app.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.jyothigas.app.entity.ConnectionTypeEntity;

@Repository("connectionTypeDAO")
public class ConnectionTypeDAO extends JyothiGasDAO<ConnectionTypeEntity> {

	@PersistenceContext
	EntityManager entityManger;

	private static final Log log = LogFactory.getLog(ConnectionTypeDAO.class);

	public List<ConnectionTypeEntity> findAllConnectionTypes() {
		log.info("Getting ApplianceEntity Instance...");
		List<ConnectionTypeEntity> conumerEntity = new ArrayList<ConnectionTypeEntity>();
		try {
			conumerEntity = entityManager
					.createQuery("select s from ConnectionTypeEntity s ", ConnectionTypeEntity.class).getResultList();
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
		}
		return conumerEntity;
	}

	public List<ConnectionTypeEntity> findConnectionTypesById(String id) {
		log.info("Getting ApplianceEntity Instance...");
		List<ConnectionTypeEntity> conumerEntity = new ArrayList<ConnectionTypeEntity>();
		try {
			conumerEntity = entityManager
					.createQuery("select s from ConnectionTypeEntity s where s.id=:id", ConnectionTypeEntity.class)
					.setParameter("id", id).getResultList();
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
		}
		return conumerEntity;
	}
	
	public List<ConnectionTypeEntity> fetchAllConnectionByType(String connectionType) {
		log.info("Getting ApplianceEntity Instance...");
		List<ConnectionTypeEntity> conumerEntity = new ArrayList<ConnectionTypeEntity>();
		try {
			conumerEntity = entityManager
					.createQuery("select s from ConnectionTypeEntity s where s.connectionType=:connectionType", ConnectionTypeEntity.class)
					.setParameter("connectionType", connectionType).getResultList();
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
		}
		return conumerEntity;
	}
}

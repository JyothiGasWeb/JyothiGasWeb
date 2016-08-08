package com.jyothigas.app.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.jyothigas.app.entity.RoleEntity;

@Repository("roleDAO")
public class RoleDAO  extends JyothiGasDAO<RoleEntity> {

	@PersistenceContext
	EntityManager entityManger;

	private static final Log log = LogFactory.getLog(RoleDAO.class);

	public List<RoleEntity> findByRoleId(Integer roleId) {
		log.info("Getting RoleEntity Instance with roleId: " + roleId);
		List<RoleEntity> registrationEntity = new ArrayList<RoleEntity>();
		try {
			registrationEntity = entityManager.createQuery("select s from RoleEntity s Where s.roleId = :roleId ",RoleEntity.class)
					.setParameter("roleId", roleId)
					.getResultList();
			log.info("get successfull");
		} catch (Exception e) {
			log.error("Failed : " + e);
		}
		return registrationEntity;
	}
	
}

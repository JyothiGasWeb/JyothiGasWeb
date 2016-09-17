package com.jyothigas.app.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.jyothigas.app.entity.RoleEntity;
import java.util.Collections;

@Repository("roleDAO")
public class RoleDAO extends JyothiGasDAO<RoleEntity> {

    @PersistenceContext
    EntityManager entityManger;

    private static final Log log = LogFactory.getLog(RoleDAO.class);

    public List<RoleEntity> getRoles() {
        log.info("Getting RoleEntity Instance ");
        List<RoleEntity> roleList = Collections.emptyList();
        try {
            roleList = entityManager.createQuery("select s from RoleEntity s", RoleEntity.class).getResultList();
            log.info("get successfull");
        } catch (Exception e) {
            log.error("Failed : " + e);
        }
        return roleList;
    }

}

package com.jyothigas.app.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.jyothigas.app.entity.MechanicServiceEntity;

@Repository
public class MechanicServiceDAO extends JyothiGasDAO<MechanicServiceEntity>{
	
	@PersistenceContext
	EntityManager entityManger;
}

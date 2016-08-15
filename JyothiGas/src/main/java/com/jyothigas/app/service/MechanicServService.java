package com.jyothigas.app.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jyothigas.app.dao.MechanicServiceDAO;
import com.jyothigas.app.entity.MechanicServiceEntity;
import com.jyothigas.app.model.MechanicServiceModel;

@Service
@Transactional
public class MechanicServService {

	@Autowired
	MechanicServiceDAO mechanicDAO;

	public int addMechanicService(MechanicServiceModel mechServ) {
		int result = 0;
		MechanicServiceEntity entity = new MechanicServiceEntity();
		BeanUtils.copyProperties(mechServ, entity);
		entity.setStatus("NEW");
		entity = mechanicDAO.merge(entity);
		result = 1;
		return result;
	}
	
	public int updateMechanicService(MechanicServiceModel mechServ) {
		int result = 0;
		MechanicServiceEntity entity = new MechanicServiceEntity();
		BeanUtils.copyProperties(mechServ, entity);
		entity.setStatus("IN PROGRESS");
		entity = mechanicDAO.merge(entity);
		result = 1;
		return result;
	}

}

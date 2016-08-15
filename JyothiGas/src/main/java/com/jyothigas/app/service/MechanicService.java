package com.jyothigas.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jyothigas.app.dao.MechanicDAO;
import com.jyothigas.app.entity.MechanicEntity;
import com.jyothigas.app.model.Mechanic;

@Service
public class MechanicService {

	@Autowired
	MechanicDAO mechanicDAO;

	public List<Mechanic> getMechanicByDealerId(int id) {
		List<MechanicEntity> mechanicList = mechanicDAO.getMechanicByDealerId(id);
		List<Mechanic> MechList = new ArrayList<Mechanic>();
		for (MechanicEntity entity : mechanicList) {
			Mechanic mech = new Mechanic();
			BeanUtils.copyProperties(entity,mech);
			MechList.add(mech);
		}
		return MechList;
	}
}

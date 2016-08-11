package com.jyothigas.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jyothigas.app.dao.ApplianceDAO;
import com.jyothigas.app.entity.ApplianceEntity;
import com.jyothigas.app.model.Appliances;

@Service("applianceService")
@Transactional
public class ApplianceService {

	@Autowired
	ApplianceDAO applianceDAO;
	
	/**
	 * Method for fetch all appliances
	 * @return
	 */
	public List<Appliances> fetchAllAppliance() {
		List<Appliances> appliancesList = new ArrayList<Appliances>();
		try {
			List<ApplianceEntity> appliancesEntityList = applianceDAO.findAllAppliances();
			for (ApplianceEntity applianceEntity : appliancesEntityList) {
				Appliances appliances = new Appliances();
				BeanUtils.copyProperties(applianceEntity, appliances);
				appliancesList.add(appliances);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appliancesList;
	}
}
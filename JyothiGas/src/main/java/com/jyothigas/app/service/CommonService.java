package com.jyothigas.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jyothigas.app.dao.ApplianceBookingDAO;
import com.jyothigas.app.dao.ApplianceDAO;
import com.jyothigas.app.entity.ApplianceBookingEntity;
import com.jyothigas.app.entity.ApplianceEntity;
import com.jyothigas.app.model.Appliances;

@Service
@Transactional
public class CommonService {

	@Autowired
	ApplianceBookingDAO applianceBookingService;

	@Autowired
	ApplianceDAO applianceDAO;

	public List<Appliances> getApplianceByBookingId(int id) {
		// STEP-1 Get ALL APPLIANCE-Booking By booking ID
		List<ApplianceBookingEntity> applianceIdList = applianceBookingService.getApplianceBooking(id);
		List<ApplianceEntity> applianceEntityList = new ArrayList<ApplianceEntity>();
		List<Appliances> applianceList = new ArrayList<Appliances>();
		Map<Integer, Integer> applianceQuantity = new HashMap<Integer, Integer>();

		List<Integer> applianceIds = new ArrayList<Integer>();
		if (null != applianceIdList) {
			// STEP -2 Get all appliaces Ids
			for (ApplianceBookingEntity info : applianceIdList) {
				applianceIds.add(info.getApplianceId());
				applianceQuantity.put(info.getApplianceId(), info.getQuantity());
			}
			// STEP - 3 Query All ids to get complete appliance Details
			if (applianceIds.size() > 0) {
				applianceEntityList = applianceDAO.findAllAppliancesById(applianceIds);
			}
			// Set appliance bean with quantity
			for (ApplianceEntity appEntity : applianceEntityList) {
				Appliances appliance = new Appliances();
				BeanUtils.copyProperties(appEntity, appliance);
				appliance.setQuantity(applianceQuantity.get(appEntity.getId()));
				applianceList.add(appliance);
			}
		} else {
			return applianceList;
		}

		return applianceList;
	}
}

package com.jyothigas.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jyothigas.app.dao.DealerSubRegionDAO;
import com.jyothigas.app.entity.SubRegionEntity;

@Service
@Transactional
public class SubRegionService {

	@Autowired
	DealerSubRegionDAO subRegionDAO;

	public List<SubRegionEntity> getDealerSubRegion(int dealerId) {
		List<SubRegionEntity> subRegionList = new ArrayList<SubRegionEntity>();
		try {
			List<Integer> regionIds = subRegionDAO.getSubRegionIds(dealerId);
			subRegionList = subRegionDAO.getSubRegionList(regionIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subRegionList;
	}

}

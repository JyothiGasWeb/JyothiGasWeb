package com.jyothigas.app.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jyothigas.app.dao.DealerDAO;
import com.jyothigas.app.entity.DealerEntiy;
import com.jyothigas.app.model.Dealer;

@Service("dealerService")
@Transactional
public class DealerService {

	private static final Logger logger = LoggerFactory.getLogger(DealerService.class);
	
	@Autowired
	DealerDAO dealerDAO;
	
	public List<Dealer> fetchAllDealers(){
		logger.info("Fetching the Dealers List");
		List<Dealer> dealerList = new ArrayList<Dealer>();
		try {
			List<DealerEntiy> dealerEntityList = dealerDAO.findAllDealers();
			for (DealerEntiy dealerEntity : dealerEntityList) {
				Dealer dealer = new Dealer();
				BeanUtils.copyProperties(dealerEntity, dealer);
				dealerList.add(dealer);
			}
		} catch (Exception e) {
			logger.error("Error in fetching the Dealers..");
			e.printStackTrace();
		} 
		return dealerList;
	}
	
	
}

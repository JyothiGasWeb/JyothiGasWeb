package com.jyothigas.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyothigas.app.entity.SubRegionEntity;
import com.jyothigas.app.model.AppResponse;
import com.jyothigas.app.service.SubRegionService;
import com.jyothigas.utils.Constant;

@Controller
public class SubRegionController {
	@Autowired
	SubRegionService subRegion;

	@RequestMapping(value = Constant.GET_DEALER_SUBREGION, method = RequestMethod.GET)
	public @ResponseBody Object getDealerSubRegions(@RequestParam Integer dealerId) {
		AppResponse appResponse = new AppResponse();
		try {
			List<SubRegionEntity> subRegionList = subRegion.getDealerSubRegion(dealerId);
			return subRegionList;
		} catch (Exception e) {
			e.printStackTrace();
			appResponse.setStatus("Error");
			appResponse.setMessage("Please try after sometime");
		}
		return appResponse;
	}
}

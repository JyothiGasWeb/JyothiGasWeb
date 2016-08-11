package com.jyothigas.app.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jyothigas.app.dao.ConnectionTypeDAO;
import com.jyothigas.app.entity.ConnectionTypeEntity;
import com.jyothigas.app.model.ConnectionType;

@Service("connectionTypeService")
@Transactional
public class ConnectionTypeService {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionTypeService.class);

	@Autowired
	ConnectionTypeDAO connectionTypeDao;

	public List<ConnectionType> findAllConnectionTypes() {
		logger.info("fetchAllConnection...");
		List<ConnectionType> connList = new ArrayList<ConnectionType>();
		try {
			List<ConnectionTypeEntity> connectionEntityList = connectionTypeDao.findAllConnectionTypes();
			for (ConnectionTypeEntity connection : connectionEntityList) {
				ConnectionType conn = new ConnectionType();
				BeanUtils.copyProperties(connection, conn);
				connList.add(conn);
			}
		} catch (Exception e) {
			logger.error("Error in fetchAllConnection");
			e.printStackTrace();
		}
		return connList;
	}
}

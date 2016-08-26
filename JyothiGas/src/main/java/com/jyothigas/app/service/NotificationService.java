package com.jyothigas.app.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jyothigas.app.dao.NotificationDAO;
import com.jyothigas.app.entity.NotificationEntity;
import com.jyothigas.app.model.Notification;

@Service
@Transactional
public class NotificationService {

	@Autowired
	NotificationDAO notificationDAO;
	private static final Log log = LogFactory.getLog(NotificationService.class);

	public List<Notification> getAllNotification(String userType) {
		List<Notification> notifList = new ArrayList<Notification>();
		try {
			ConvertUtils.register(new SqlTimestampConverter(null), Date.class);
			List<NotificationEntity> notificationList = notificationDAO.getAllNotification(userType);
			for (NotificationEntity entity : notificationList) {
				Notification ent = new Notification();
				BeanUtils.copyProperties(ent, entity);
				notifList.add(ent);
			}

		} catch (Exception e) {
			log.fatal("Error While Fetching Notifiaction", e);
			e.printStackTrace();
		}
		return notifList;
	}
}

package com.jyothigas.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.jyothigas.app.entity.NotificationEntity;

@Repository
public class NotificationDAO extends JyothiGasDAO<NotificationEntity> {
	private static final Log log = LogFactory.getLog(NotificationDAO.class);

	public List<NotificationEntity> getAllNotification() {
		List<NotificationEntity> notificationList = new ArrayList<NotificationEntity>();

		try {
			notificationList = entityManager.createQuery("select s from NotificationEntity s", NotificationEntity.class)
					.getResultList();
			log.info("get successful");

		} catch (RuntimeException re) {
			log.error("Notification get failed", re);
		}
		return notificationList;
	}
}

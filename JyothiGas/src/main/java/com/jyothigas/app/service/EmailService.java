package com.jyothigas.app.service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.jyothigas.app.model.Mail;
import com.jyothigas.utils.JyothiGasProperty;

@Service("emailService")
@EnableAsync
public class EmailService {
	private Map<String,String> templateMap = new HashMap<String, String>();
	public EmailService()
	{
		templateMap.put("EMAIL_FROM", "test@smsquaretech.com");
		templateMap.put("EMAIL_OTP_TEMPLATE", "Email_OTP.vm");
		templateMap.put("EMAIL_OTP_SUBJECT", "Verification: One Time Password");
		
	}
	
	private static final Log log = LogFactory.getLog(EmailService.class);

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private VelocityEngine velocityEngine;

	public static final String VERIFY_EMAIL = "VERIFY_EMAIL";
	public static final String RESET_PASSWORD = "RESET_PASSWORD";
	public static final String DOCTOD_ACCOUNT_ACTIVATION = "DOCTOR_ACCOOUNT_ACTIVATION";
	public static final String REP_ACCOUNT_ACTIVATION = "REP_ACCOOUNT_ACTIVATION";
	public static final String FEEDBACK_ACK = "FEEDBACK_ACK";
	public static final String FEEDBACK_ADMIN_ACK = "FEEDBACK_ADMIN_ACK";
	public static final String EMAIL_OTP = "EMAIL_OTP";
	public static final String DOCTOR_ACCOUNT_ACTIVATION_INTIMATE = "DOCTOR_ACCOUNT_ACTIVATION_INTIMATE";

	@Async
	public void sendMail(Mail mail) {
		try {

			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			helper.setFrom(templateMap.get("EMAIL_FROM"));
			if (mail.getMailTo() != null && mail.getMailTo().length() > 0) {
				helper.setTo(mail.getMailTo());
				if (mail.getMailBcc() != null && mail.getMailBcc().length() > 0) {
					helper.setBcc(mail.getMailBcc());
				}
				if (mail.getMailCc() != null && mail.getMailCc().length() > 0) {
					helper.setCc(mail.getMailCc());
				}
				helper.setSubject(templateMap.get(mail.getTemplateName() + "_SUBJECT"));

				Template template = velocityEngine.getTemplate(templateMap.get(mail.getTemplateName() + "_TEMPLATE"));
				VelocityContext velocityContext = new VelocityContext();
				Map<String, String> valueMap = mail.getValueMap();
				valueMap.put("INFOMAILID", JyothiGasProperty.getInstance().getProperties("medrep.email.info.id"));
				if (valueMap != null) {
					for (String key : valueMap.keySet()) {
						velocityContext.put(key, valueMap.get(key));
					}
				}
				velocityContext.put("TITLE", templateMap.get(mail.getTemplateName() + "_SUBJECT"));
				StringWriter stringWriter = new StringWriter();
				template.merge(velocityContext, stringWriter);
				mimeMessage.setContent(stringWriter.toString(), "text/html");
				mailSender.send(mimeMessage);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Send Email Error ", e);
		}

	}

}

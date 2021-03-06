package com.jyothigas.app.service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jyothigas.app.model.SMS;
import com.jyothigas.utils.JyothiGasProperty;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.web.client.RestClientException;

@Service("smsService")
public class SMSService {

    public static final String VERIFY_NO = "VERIFY_NO";
    public static final String RESET_PASSWORD = "RESET_PASSWORD";
    public static final String ACCOUNT_ACTIVATION = "ACCOOUNT_ACTIVATION";
    public static final String NEW_APPOINTMENT = "NEW_APPOINTMENT";
    public static final String CONFIRM_APPOINTMENT = "CONFIRM_APPOINTMENT";
    public static final String FEEDBACK = "FEEDBACK";
    public static final String NOTIFICATION = "NOTIFICATION";
    public static final String USER_UPDATE = "USER_UPDATE";
    public static final String SURRENDER = "SURRENDER";
    public static final String MECHANIC_SERVICE = "MECHANIC_SERVICE";
    public static final String INVITE = "INVITE";
    public static final String BOOKING_MSG_DEALER = "BOOKING_MSG_DEALER";

    private Map<String, String> templateMap = new HashMap<String, String>();

    private static final Log log = LogFactory.getLog(SMSService.class);

    @Autowired
    private VelocityEngine velocityEngine;

    public SMSService() {
        templateMap.put(SMSService.VERIFY_NO + "_TEMPLATE", "Mobile_Verification.vm");
        templateMap.put(SMSService.RESET_PASSWORD + "_TEMPLATE", "Mobile_PasswordReset.vm");
        templateMap.put(SMSService.ACCOUNT_ACTIVATION + "_TEMPLATE", "Mobile_Activation.vm");
        templateMap.put(SMSService.NEW_APPOINTMENT + "_TEMPLATE", "Mobile_NewAppointment.vm");
        templateMap.put(SMSService.CONFIRM_APPOINTMENT + "_TEMPLATE", "Mobile_ConfirmAppointment.vm");
        templateMap.put(SMSService.FEEDBACK + "_TEMPLATE", "Mobile_Feedback.vm");
        templateMap.put(SMSService.NOTIFICATION + "_TEMPLATE", "Mobile_Notification.vm");
        templateMap.put(SMSService.INVITE + "_TEMPLATE", "Mobile_Invite.vm");
        templateMap.put(SMSService.USER_UPDATE + "_TEMPLATE", "Mobile_UserUpdate.vm");
        templateMap.put(SMSService.SURRENDER + "_TEMPLATE", "Mobile_SurrenderConnection.vm");
        templateMap.put(SMSService.MECHANIC_SERVICE + "_TEMPLATE", "Mobile_MechanicService.vm");
        templateMap.put(SMSService.BOOKING_MSG_DEALER + "_TEMPLATE", "Mobile_BookingDealer.vm");

    }

    public void sendSMS(SMS sms) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Template template = velocityEngine.getTemplate(templateMap.get(sms.getTemplate() + "_TEMPLATE"));
            VelocityContext velocityContext = new VelocityContext();
            Map<String, String> valueMap = sms.getValueMap();
            if (valueMap != null) {
                for (String key : valueMap.keySet()) {
                    velocityContext.put(key, valueMap.get(key));
                }
            }
            StringWriter stringWriter = new StringWriter();
            template.merge(velocityContext, stringWriter);
            String message = stringWriter.toString();
            String baseURL = JyothiGasProperty.getInstance().getProperties("sms.url");
            baseURL = baseURL + "&to=" + sms.getPhoneNumber();
            baseURL = baseURL + "&from=JYOGAS";
            baseURL = baseURL + "&message=" + message;
            log.info("Sending text message to " + sms.getPhoneNumber());
            ResponseEntity<String> response = restTemplate.getForEntity(baseURL, String.class);
            log.info("Response received from SMS country " + response.getBody());
        } catch (ResourceNotFoundException e) {
            log.error("Send SMS failed", e);
        } catch (ParseErrorException e) {
            log.error("Send SMS failed", e);
        } catch (MethodInvocationException e) {
            log.error("Send SMS failed", e);
        } catch (RestClientException e) {
            log.error("Send SMS failed", e);
        }
    }

    public void sendMessage(String message, String contact) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String baseURL = JyothiGasProperty.getInstance().getProperties("sms.url");
            baseURL = baseURL + "&to=" + contact;
            baseURL = baseURL + "&from=JYOGAS";
            baseURL = baseURL + "&message=" + message;
            log.info("Sending text message to " + contact);
            ResponseEntity<String> response = restTemplate.getForEntity(baseURL, String.class);
            log.info("Response received from SMS country " + response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

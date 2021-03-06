package com.jyothigas.app.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jyothigas.app.dao.ConnectionTypeDAO;
import com.jyothigas.app.dao.ConsumerDAO;
import com.jyothigas.app.dao.DealerDAO;
import com.jyothigas.app.dao.RegistrationDAO;
import com.jyothigas.app.dao.RoleDAO;
import com.jyothigas.app.entity.ConnectionTypeEntity;
import com.jyothigas.app.entity.ConsumerEntity;
import com.jyothigas.app.entity.DealerEntity;
import com.jyothigas.app.entity.RegistrationEntity;
import com.jyothigas.app.entity.RoleEntity;
import com.jyothigas.app.model.ConsumerDetails;
import com.jyothigas.app.model.Mail;
import com.jyothigas.app.model.Register;
import com.jyothigas.app.model.SMS;
import com.jyothigas.utils.Constant;

@Service("consumerService")
@Transactional
public class ConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    @Autowired
    RegistrationDAO registrationDAO;

    @Autowired
    RoleDAO roleDAO;

    @Autowired
    EmailService emailService;

    @Autowired
    ConsumerDAO consumerDAO;

    @Autowired
    DealerDAO dealerDAO;
    
    @Autowired
    ConnectionTypeDAO connectionTypeDAO;

    @Autowired
    SMSService smsService;

    /**
     * Method for fetch Consumer Details
     *
     * @param email
     * @return
     */
    public ConsumerDetails fetchConsumerDetailsByEmail(String email) {
        logger.info("Fetching the Consumer Details..");
        ConsumerDetails consumerDetails = new ConsumerDetails();

        try {
            List<RegistrationEntity> registrationEntityList = registrationDAO.findByEmailId(email);
            for (RegistrationEntity registrationEntity : registrationEntityList) {

                if (registrationEntity != null && registrationEntity.getId() > 0) {
                    consumerDetails.setReg_id(registrationEntity.getId());
                    consumerDetails.setEmail(registrationEntity.getEmail());
                    consumerDetails.setName(registrationEntity.getName());
                    consumerDetails.setContactNo(registrationEntity.getContactNo());
                    consumerDetails.setCity(registrationEntity.getCity());
                    consumerDetails.setAreaCode(registrationEntity.getAreaCode());
                    consumerDetails.setRoleId(registrationEntity.getRoleId());
                    consumerDetails.setAddress(registrationEntity.getAddress());
                    consumerDetails.setConnectionQty(registrationEntity.getConnectionQty());
                    consumerDetails.setStatus(registrationEntity.getStatus());
                    consumerDetails.setConnectionTypeId(registrationEntity.getConnectionTypeId());
                    consumerDetails.setUserType(registrationEntity.getUserType());
                    consumerDetails.setSurrenderStatus(registrationEntity.getSurrenderStatus());
                    consumerDetails.setSurrender_Date(registrationEntity.getSurrender_Date());
                    consumerDetails.setDistributor_Id(registrationEntity.getDistributer_id());
                    // Fetching the Role Details
                    RoleEntity roleEntity = roleDAO.findById(RoleEntity.class, registrationEntity.getRoleId());
                    if (null != roleEntity && roleEntity.getRoleId() != 0) {
                        consumerDetails.setRoleName(roleEntity.getName());
                    }

                    // Fetching the Consumer Id
                    ConsumerEntity consumerEntity = consumerDAO.findByRegId(registrationEntity.getId());
                    if (consumerEntity != null) {
                        consumerDetails.setConsumer_id(consumerEntity.getId());
                    }

                    // Fetching the Dealer Details
                    RegistrationEntity dealerReg = registrationDAO.findById(RegistrationEntity.class,
                            registrationEntity.getId());
                    DealerEntity dealerEntity = dealerDAO.findById(DealerEntity.class,
                            registrationEntity.getDealer_id());
                    System.out.println(" ---  "+registrationEntity.getId());
                    if (dealerEntity != null) {
                        consumerDetails.setDealerId(dealerEntity.getDealer_id());
                        consumerDetails.setDealerName(dealerReg.getName());
                    }
                    // Fetching the connection type details
                    ConnectionTypeEntity connectionTypeEntity = connectionTypeDAO.findById(ConnectionTypeEntity.class,
                            registrationEntity.getConnectionTypeId());
                    if (connectionTypeEntity != null) {
                        consumerDetails.setConnectionTypeName(connectionTypeEntity.getConnectionType());
                        consumerDetails.setConnectionTypeDesc(connectionTypeEntity.getConnectionDesc());
                        consumerDetails.setConnectionPrice(connectionTypeEntity.getConnectionPrice());
                    }

                }
            }
        } catch (Exception e) {
            logger.error("Error while fetching consumer Details....." + e.getMessage());
            e.printStackTrace();
        }

        return consumerDetails;
    }

    /**
     * Method for update consumer data
     *
     * @param register
     * @return
     *
     *
     */
    // =======> NOTE : THIS API ASSOCIATED WITH THREE CALLS <=======
    // PLEASE PAY ATTENTION BEFORE CHNAGING ANYTHING IN THIS
    public int updateConsumer(Register register) {
        int result = 0;
        try {
            RegistrationEntity registrationEntity = registrationDAO.findById(RegistrationEntity.class,
                    register.getId());
            int oldDealerId = registrationEntity.getDealer_id();
            StringBuilder changedEntity = new StringBuilder();
            if (registrationEntity != null) {
                if (register.getAddress() != null) {
                    changedEntity.append("Address");
                    registrationEntity.setAddress(register.getAddress());
                }
                if (register.getContactNo() != null) {
                    registrationEntity.setContactNo(register.getContactNo());
                }
                if (register.getName() != null) {
                    registrationEntity.setName(register.getName());
                }
                if (register.getCity() != null) {
                    registrationEntity.setCity(register.getCity());
                }
                if (register.getEmail() != null) {
                    registrationEntity.setEmail(register.getEmail());
                }
                if (register.getDealer_id() > 0) {
                    changedEntity.append("Dealer");
                    registrationEntity.setDealer_id(register.getDealer_id());
                }
                if (register.getConnectionTypeId() > 0) {
                    registrationEntity.setConnectionTypeId(register.getConnectionTypeId());
                }
                if (register.getAreaCode() != null) {
                    registrationEntity.setAreaCode(register.getAreaCode());
                }
                if (register.getDistributer_id() > 0) {
                    registrationEntity.setDealer_id(register.getDistributer_id());
                }
                if(register.getUserType() != null){
                	registrationEntity.setUserType(register.getUserType());
                }
                registrationEntity.setUpdatedDate(new Date());
                registrationEntity.setId(register.getId());
                RegistrationEntity entity = registrationDAO.merge(registrationEntity);
                result = entity.getId();
                // Send Notification
                // Fetching the Dealer Detail
                RegistrationEntity newDealerEntiy = registrationDAO.findById(RegistrationEntity.class,
                        registrationEntity.getDealer_id());
                RegistrationEntity oldDealerEntiy = registrationDAO.findById(RegistrationEntity.class,
                        oldDealerId);
                if (changedEntity.indexOf("Dealer") >= 0) {
                    // Send SMS
                    sendNotificationSMS(changedEntity.toString(), registrationEntity.getName(),
                            registrationEntity.getContactNo());
                    // Email for customer
                    sendNotificationEmail(changedEntity.toString(), registrationEntity.getName(),
                            registrationEntity.getEmail());
                    // Email for Old Dealer
                    sendEmailToDealer(oldDealerEntiy.getName(), registrationEntity.getEmail(),
                            oldDealerEntiy.getEmail(), "OLDDEALER");

                    // Email For New Dealer
                    sendEmailToDealer(newDealerEntiy.getName(), registrationEntity.getEmail(),
                            newDealerEntiy.getEmail(), "NEWDEALER");
                } else {
                    sendNotificationSMS(changedEntity.toString(), registrationEntity.getName(),
                            registrationEntity.getContactNo());
                    // Email for customer
                    sendNotificationEmail(changedEntity.toString(), registrationEntity.getName(),
                            registrationEntity.getEmail());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private void sendNotificationEmail(String entity, String name, String EmailTo) {
        Mail mail = new Mail();
        mail.setTemplateName(EmailService.EMAIL_USER_UPDATE);
        mail.setMailTo(EmailTo);
        Map<String, String> valueMap = new HashMap<String, String>();
        valueMap.put("ENTITY", entity);
        valueMap.put("NAME", name);
        valueMap.put("SUBJECT", entity);
        mail.setValueMap(valueMap);
        try {
            emailService.sendMail(mail);
        } catch (MailException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void sendEmailToDealer(String dealerName, String custEmail, String EmailTo, String flag) {
        Mail mail = new Mail();
        mail.setTemplateName(EmailService.EMAIL_DEALER);
        mail.setMailTo(EmailTo);
        Map<String, String> valueMap = new HashMap<String, String>();
        if (flag.equalsIgnoreCase("OLDDEALER")) {
            valueMap.put("MESSAGE", Constant.OLD_DEALER_EMAIL.replace("{EMAIL}", custEmail));
        } else {
            valueMap.put("MESSAGE", Constant.NEW_DEALER_EMAIL.replace("{EMAIL}", custEmail));
        }
        valueMap.put("NAME", dealerName);
        mail.setValueMap(valueMap);
        try {
            emailService.sendMail(mail);
        } catch (MailException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void sendNotificationSMS(String entity, String name, String phoneNumber) {
        SMS sms = new SMS();
        sms.setPhoneNumber(phoneNumber);
        Map<String, String> valueMap = new HashMap<String, String>();
        valueMap.put("ENTITY", entity);
        valueMap.put("NAME", name);
        sms.setTemplate(SMSService.USER_UPDATE);
        sms.setValueMap(valueMap);
        smsService.sendSMS(sms);

    }
}

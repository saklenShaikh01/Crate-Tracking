package com.notification.listener;


import com.notification.client.CrateManagementClient;
import com.notification.dto.CrateEvent;
import com.notification.service.SmsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import com.notification.dto.CustomerResponse;

@Component
public class CrateListener {
  @Autowired
  @Lazy
  private CrateManagementClient client;

  @Autowired
  private SmsService smsService;

  public CrateListener(CrateManagementClient client, SmsService smsService) {
    this.client = client;
    this.smsService = smsService;
  }

  @RabbitListener(queues = "crate_que")
  public void consume(CrateEvent event) {
    System.out.println("Event: " + event);

    String mobile = "";

    if (event.getPersonType().equals("CUSTOMER")) {
      mobile = client.getCustomer(event.getPersonId()).getMobile();
    } else {
      mobile = client.getFarmer(event.getPersonId()).getMobile();
    }

    smsService.sendSms(
      mobile,
      event.getPersonId(),
      String.valueOf(event.getBalance()),
      event.getAction()
    );
  }
}

package com.shequgo.shequgoqueue.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;
import utils.SmsUtil;

/**
 * @Author: Colin
 * @Date: 2018/8/3 20:13
 */
@Component
public class ConsumerTest {
    @Autowired
    private JmsMessagingTemplate jmsTemplate;

    @JmsListener(destination = "sms_notice",containerFactory="jmsListenerContainerTopic")
    public void receiveTopic(String phone) {
        SmsUtil.sendNotice(phone);
    }

}

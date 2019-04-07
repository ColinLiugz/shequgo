package com.shequgo.shequgoqueue.TestQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: Colin
 * @Date: 2018/8/3 20:13
 */
@Component
public class ConsumerTest {
    @Autowired
    private JmsMessagingTemplate jmsTemplate;

    @JmsListener(destination = "topic",containerFactory="jmsListenerContainerTopic")
    public void receiveTopic(String message) {
        System.out.println("<<--------监听到topic队列信息---------->>");
        System.out.println("信息为："+message);
    }

    @JmsListener(destination = "queue")
    public void receiveQueue(String message) {
        System.out.println("<<--------监听到queue队列信息---------->>");
        System.out.println("信息为："+message);
    }
}

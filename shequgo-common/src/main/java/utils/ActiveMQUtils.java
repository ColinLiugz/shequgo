package utils;

import base.BaseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.Serializable;
import java.util.List;

/**
 * @description: activeMq消息队列工具类
 **/
public class ActiveMQUtils {

    private static final Logger logger = LoggerFactory.getLogger(ActiveMQUtils.class);

    private JmsTemplate jmsTemplate;

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public <T extends BaseObject> void sendMessage(String str, final T obj) {

        logger.info("---------消息监听标识：：：---------" + str);

        logger.info("  submit " + "SequenceStr in run!!!");
        logger.info("  submit " + "SequenceStr Before MQ is " + obj.toString());

        try {
            jmsTemplate.setDefaultDestinationName(str);
            jmsTemplate.send(new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createObjectMessage(obj);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T extends BaseObject> void sendMessage(String str, final List<T> obj) {

        logger.info("---------消息监听标识：：：---------" + str);

        logger.info("  submit " + "SequenceStr in run!!!");
        logger.info("  submit " + "SequenceStr Before MQ is " + obj.toString());

        try {
            jmsTemplate.setDefaultDestinationName(str);
            jmsTemplate.send(new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createObjectMessage((Serializable) obj);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String str, final String obj) {

        logger.info("---------消息监听标识：：：---------" + str);

        logger.info("  submit " + "SequenceStr in run!!!");
        logger.info("  submit " + "SequenceStr Before MQ is " + obj.toString());

        try {
            jmsTemplate.setDefaultDestinationName(str);
            jmsTemplate.send(session -> session.createObjectMessage(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String str, final Integer obj) {

        logger.info("---------消息监听标识：：：---------" + str);

        logger.info("  submit " + "SequenceStr in run!!!");
        logger.info("  submit " + "SequenceStr Before MQ is " + obj.toString());

        try {
            jmsTemplate.setDefaultDestinationName(str);
            jmsTemplate.send(session -> session.createObjectMessage(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

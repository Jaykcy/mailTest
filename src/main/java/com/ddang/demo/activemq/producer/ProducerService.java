package com.ddang.demo.activemq.producer;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Topic;
import java.io.Serializable;

@Service("producerService")
public class ProducerService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Topic topic;

    public void sendMessage(Serializable message) {
        logger.info("Topic: {}, JMS Connection start!");
        logger.info("Message: {}", JSONObject.toJSON(message));
        //convertSendAndReceive(…)：可以同步消费者。使用此方法，当确认了所有的消费者都接收成功之后，才触发另一个convertSendAndReceive(…)，也就是才会接收下一条消息。RPC调用方式。
        //convertAndSend(…)：使用此方法，交换机会马上把所有的信息都交给所有的消费者，消费者再自行处理，不会因为消费者处理慢而阻塞线程。
        this.jmsMessagingTemplate.convertAndSend(this.topic, message);
        logger.info("Topic: {}, JMS Connection end!");
    }
}
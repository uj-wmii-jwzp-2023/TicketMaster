package uj.jwzp.ticketmaster.ampq;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LogAmqpSender {

	private static final String APP_NAME = "ticketmaster" ;

	@Autowired
	private RabbitTemplate rmq;

	@Autowired
	private TopicExchange logsExchange;

	public void sendLog(String logLevel, String component, String message) {
		if (logLevel == null) {
			logLevel = "info";
		}
		String routingKey = APP_NAME + "." + component + "." + logLevel;
		rmq.convertAndSend(logsExchange.getName(), routingKey, message);
		System.out.println(" [x] Sent LOG (" + routingKey  + "): '" + message + "'");
	}

	public void debug(Class<?> componentClass, String message) {
		sendLog("debug", componentClass.getSimpleName(), message);
	}
	
	public void info(Class<?> componentClass, String message) {
		sendLog("info", componentClass.getSimpleName(), message);
	}

}

package uj.jwzp.ticketmaster.ampq;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

	static final String LOGS_EXCHANGE_NAME = "service_logs";
	
	@Bean
	public TopicExchange logsExchange() {
		return new TopicExchange(LOGS_EXCHANGE_NAME);
	}

	@Bean
	public TopicExchange testSpamExchange() {
		return new TopicExchange("test_spam");
	}

	// @Bean
	// public LogAmqpSender logAmqpSender() {
	// 	return new LogAmqpSender();
	// }
}

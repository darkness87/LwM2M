package com.cnu.lwm2m.client;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/client.properties")
public class CNURouteBuilder extends RouteBuilder {
	@Override
	public void configure() {
		from("timer:heartbeat?period={{timer.period}}")
			.routeId("HEARTB")
			.setBody(simple("{{greeting.word}}, this is Camel running in Spring Boot!"))
			.to("log:out");
	}
}

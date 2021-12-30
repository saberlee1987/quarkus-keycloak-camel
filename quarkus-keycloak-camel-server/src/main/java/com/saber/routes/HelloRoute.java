package com.saber.routes;

import com.saber.dto.HelloDto;
import com.saber.dto.ServiceErrorResponse;
import com.saber.exceptions.OperationException;
import io.quarkus.arc.Unremovable;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestParamType;
import javax.inject.Singleton;
import javax.ws.rs.core.MediaType;

@Singleton
@Unremovable
@Slf4j
public class HelloRoute extends AbstractRestRouteBuilder {


    @Override
    public void configure() throws Exception {
        super.configure();

        rest("/hello")
                .get("sayHello")
                .id(Routes.HELLO_ROUTE)
                .description("Say Hello Service")
                .responseMessage().code(200).responseModel(HelloDto.class).endResponseMessage()
                .responseMessage().code(400).responseModel(ServiceErrorResponse.class).endResponseMessage()
                .responseMessage().code(401).responseModel(ServiceErrorResponse.class).endResponseMessage()
                .responseMessage().code(403).responseModel(ServiceErrorResponse.class).endResponseMessage()
                .responseMessage().code(404).responseModel(ServiceErrorResponse.class).endResponseMessage()
                .responseMessage().code(406).responseModel(ServiceErrorResponse.class).endResponseMessage()
                .responseMessage().code(500).responseModel(ServiceErrorResponse.class).endResponseMessage()
                .responseMessage().code(504).responseModel(ServiceErrorResponse.class).endResponseMessage()
                .produces(MediaType.APPLICATION_JSON)
                .param().name("firstName").type(RestParamType.query).dataType("string").example("Saber").required(true).endParam()
                .param().name("lastName").type(RestParamType.query).dataType("string").example("Azizi").required(true).endParam()
                .enableCORS(true)
                .route()
                .routeId(Routes.HELLO_ROUTE)
                .routeGroup(Routes.HELLO_ROUTE_GROUP)
                .to(String.format("direct:%s", Routes.HELLO_ROUTE_GATEWAY));

        from(String.format("direct:%s", Routes.HELLO_ROUTE_GATEWAY))
                .routeId(Routes.HELLO_ROUTE_GATEWAY)
                .routeGroup(Routes.HELLO_ROUTE_GROUP)
                .log("Request for sayHello with firstName == ${in.header.firstName} , lastName == ${in.header.lastName}")
                .doTry()
                    .to("bean:helloService?method=sayHello")
                        .to(String.format("direct:%s", Routes.SAY_HELLO_RESULT_ROUTE))
                     .doCatch(OperationException.class)
                        .to(String.format("direct:%s", Routes.EXCEPTION_HANDLER_ROUTE))
                .endDoTry()
                .end();

        from(String.format("direct:%s", Routes.SAY_HELLO_RESULT_ROUTE))
                .routeId(Routes.SAY_HELLO_RESULT_ROUTE)
                .routeGroup(Routes.HELLO_ROUTE_GROUP)
                .marshal().json(JsonLibrary.Jackson)
                .log("Response for sayHello ===> ${in.body}")
                .unmarshal().json(JsonLibrary.Jackson)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));

    }
}
package com.saber.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saber.dto.ServiceErrorResponse;
import com.saber.exceptions.OperationException;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import javax.inject.Inject;

public class AbstractRestRouteBuilder extends RouteBuilder {

    @Inject
    ObjectMapper mapper;

    @Override
    public void configure() throws Exception {

        from(String.format("direct:%s", Routes.EXCEPTION_HANDLER_ROUTE))
                .routeId(Routes.EXCEPTION_HANDLER_ROUTE)
                .routeGroup(Routes.EXCEPTION_HANDLER_ROUTE)
                .process(exchange -> {
                    OperationException exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, OperationException.class);
                    int statusCOde = exception.getStatusCode();
                    String responseBody = exception.getResponseBody();
                    ServiceErrorResponse errorResponse = new ServiceErrorResponse();
                    errorResponse.setCode(2);
                    errorResponse.setMessage("exception");

                    if (responseBody != null && responseBody.trim().startsWith("{")) {
                        errorResponse.setOriginalMessage(responseBody);
                    } else {
                        errorResponse.setOriginalMessage(String.format("{\"code\":%d,\"text\":\"%s\"}", statusCOde, responseBody));
                    }
                    log.error("Error OperationException with statusCode {} , errorBody {}", statusCOde, mapper.writeValueAsString(errorResponse));
                    exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, statusCOde);
                    exchange.getIn().setBody(errorResponse);
                })
                .marshal().json(JsonLibrary.Jackson);
    }
}

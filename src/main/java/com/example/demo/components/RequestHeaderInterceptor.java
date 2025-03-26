package com.example.demo.components;

import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;

/**
 * To access headers in a GraphQL Java Spring Boot application,
 * you can use a WebGraphQlInterceptor. This interceptor allows you to intercept the incoming GraphQL
 * request and access the HTTP headers. Here's how: Create a WebGraphQlInterceptor.
 */
@Component
public class RequestHeaderInterceptor implements WebGraphQlInterceptor {

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        String authHeader = request.getHeaders().getFirst("hello");
//        if(authHeader.equals("hey")){
//            throw new RuntimeException("Failed");
//        }
        // Add the header value to the GraphQL context
        request.configureExecutionInput((executionInput, builder) ->
                builder.graphQLContext(Collections.singletonMap("authHeader", authHeader)).build());
        return chain.next(request);
    }
}

package com.example.library.server.api;

import com.example.library.server.business.UserResource;
import com.example.library.server.business.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * Reactive handler for users.
 */
@Component
public class UserHandler {

    private final UserService userService;

    @Autowired
    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    @SuppressWarnings("unused")
    public Mono<ServerResponse> getAllUsers(ServerRequest request) {
        return ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(userService.findAll(), UserResource.class);
    }

    public Mono<ServerResponse> getUser(ServerRequest request) {
        return userService.findById(UUID.fromString(request.pathVariable("userId")))
                .flatMap(ur -> ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(BodyInserters.fromObject(ur)))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        return ok().build(userService.deleteById(UUID.fromString(request.pathVariable("userId"))));
    }

    public Mono<ServerResponse> createUser(ServerRequest request) {
        return ok().build(userService.create(request.bodyToMono(UserResource.class)));
    }

}

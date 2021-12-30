package com.saber.beans.impl;

import com.saber.beans.HelloService;
import com.saber.dto.HelloDto;
import com.saber.exceptions.OperationException;
import io.quarkus.arc.Unremovable;

import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Unremovable
@Named(value = "helloService")
public class HelloServiceImpl implements HelloService {
    @Override
    public HelloDto sayHello(String firstName, String lastName) {
        if (firstName == null || lastName == null) {
            throw new OperationException("firstName and lastName is required ", 404);
        }
        if (firstName.trim().length() < 5) {
            throw new OperationException("firstName len must be > 5 ", 406);
        }
        if (lastName.trim().length() < 5) {
            throw new OperationException("lastName len must be > 5 ", 406);
        }
        String message = String.format("Hello %s %s", firstName, lastName);
        HelloDto helloDto = new HelloDto();
        helloDto.setMessage(message);
        return helloDto;
    }
}

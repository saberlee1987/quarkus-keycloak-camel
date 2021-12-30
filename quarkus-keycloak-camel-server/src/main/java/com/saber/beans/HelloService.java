package com.saber.beans;

import com.saber.dto.HelloDto;
import org.apache.camel.Header;

public interface HelloService {
    HelloDto sayHello(@Header(value = "firstName") String firstName,@Header(value = "lastName") String lastName);
}

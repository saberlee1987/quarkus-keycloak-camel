package com.saber;

import com.saber.dto.HelloDto;
import io.quarkus.security.Authenticated;
import lombok.extern.slf4j.Slf4j;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/hello")
@Slf4j
@Authenticated
public class ExampleResource {
//    @Inject
//    SecurityIdentity securityIdentity;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public HelloDto hello(@QueryParam(value = "firstName") String firstName, @QueryParam(value = "lastName") String lastName) {

//       String username = securityIdentity.getPrincipal().getName();
//       log.info("username ==> {}",username);
//        for (Credential credential : securityIdentity.getCredentials()) {
//            PasswordCredential passwordCredential = (PasswordCredential) credential;
//            log.info("username ===> {} , password ==> {}",username,String.valueOf(passwordCredential.getPassword()));
//        }
        String message = String.format("Hello %s %s", firstName, lastName);
        HelloDto helloDto = new HelloDto();
        helloDto.setMessage(message);
        return helloDto;
    }
}
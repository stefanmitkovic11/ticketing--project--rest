package company.service;

import company.dto.UserDTO;

import javax.ws.rs.core.Response;

public interface KeycloakService {

    Response userCreate(UserDTO userDTO);
    void delete(String username);
}

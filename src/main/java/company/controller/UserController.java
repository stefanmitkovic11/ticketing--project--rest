package company.controller;

import company.dto.UserDTO;
import company.entity.ResponseWrapper;
import company.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@Tag(name = "UserController", description = "User API")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @RolesAllowed({"Admin"})
    @Operation(summary = "GET Users")
    public ResponseEntity<ResponseWrapper> userResponseEntity() {
        List<UserDTO> userDTOList = userService.listAllUsers();

        return ResponseEntity.ok().body(new ResponseWrapper("Users successfully retrieved", userDTOList, HttpStatus.OK));
    }


    @GetMapping("/{username}")
    @RolesAllowed({"Admin"})
    @Operation(summary = "GET User by username")
    public ResponseEntity<ResponseWrapper> getUserByUsername(@PathVariable("username") String username) {
        UserDTO userDTOList = userService.findByUserName(username);

        return ResponseEntity.ok().body(new ResponseWrapper("User successfully retrieved", userDTOList, HttpStatus.OK));
    }

    @PostMapping
    @RolesAllowed({"Admin"})
    @Operation(summary = "POST User")
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO userDTO){
        userService.save(userDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("User is successfully created", HttpStatus.CREATED));
    }

    @PutMapping
    @RolesAllowed({"Admin"})
    @Operation(summary = "PUT User")
    public ResponseEntity<ResponseWrapper> updateUser(@RequestBody UserDTO userDTO) {
        userService.update(userDTO);

        return ResponseEntity.ok(new ResponseWrapper("User is successfully updated", userDTO, HttpStatus.OK));
    }


    @DeleteMapping("/{username}")
    @RolesAllowed({"Admin"})
    @Operation(summary = "DELETE User")
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable("username") String username) {
        userService.deleteByUserName(username);

        return ResponseEntity.ok(new ResponseWrapper("User is successfully deleted", HttpStatus.OK));
    }






}

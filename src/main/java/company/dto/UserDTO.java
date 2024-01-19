package company.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import company.enums.Gender;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passWord;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String confirmPassWord;
    private boolean enabled;
    private String phone;
    private RoleDTO role;
    private Gender gender;

}



package net.javaguides.springboot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
        description = "UserDto model Information"
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto
{
    private Long id;
    // first name validate if empty
    @Schema(
            description = "User First Name"
    )
    @NotEmpty(message = "User First Name should not be empty or null")
    private String firstName;
    // last name is validate if empty
    @Schema(
            description = "User Last Name"
    )
    @NotEmpty(message = "User Last Name should not be empty or null")
    private String lastName;
    // email is validate if empty
    // email should be the email pattern
    @Schema(
            description = "User Email"
    )
    @NotEmpty(message = "User Email should not be empty or null")
    @Email
    private String email;
}

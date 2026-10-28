package net.javaguides.springboot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.exception.ErrorDetails;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.mapper.UserMapper;
import net.javaguides.springboot.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@Tag(
        name="CRUD REST APIs for User Resource",
        description = "Create User,Update User,Get User,Get All Users,Delete User"
)
@RestController
@AllArgsConstructor//due to this autoconfiguration is working, constructor dependency injection
@RequestMapping("api/users")
public class UserController
{
    private UserService userService;
    @Operation(
            summary = "Create User REST API",
            description = "Create User REST API is used to save user in a database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody  UserDto user)
    {
       UserDto savedUser= userService.createUser(user);
       return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
    //http://localhost:8080/api/users/2
    @Operation(
            summary = "Get User By Id REST API",
            description = "Get User By Id REST API is used to get a single user from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id)
    {
        UserDto savedUser=userService.getUserById(id);
        return new ResponseEntity<>(savedUser,HttpStatus.OK);
    }
    //Build get all user REST API
    @Operation(
            summary = "Get All Users REST API",
            description = "Get All User REST API is used to get a all user from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping
    public  ResponseEntity<List<UserDto>>getAllUsers()
    {
        List<UserDto> user= userService.getAllUser();
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    //Build update user REST aPI
    @Operation(
            summary = "Update User REST API",
            description = "Update User REST API is used to update a particular user to database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId,@RequestBody @Valid UserDto user)
    {
        UserDto updateUser= userService.updateUser(user,userId);
        return new ResponseEntity<>(updateUser,HttpStatus.OK);
    }
    //delte the user user REST API
    @Operation(
            summary = "Delete User REST API",
            description = "Delete User REST API is used to delete a particular user to database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id)
    {
        userService.deleteUser(id);
        return new ResponseEntity<>("User successfully deleted",HttpStatus.OK);
    }
    /*@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails>handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest)
    {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "USER_NOT_FOUND"

        );
        return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
    }*/
}

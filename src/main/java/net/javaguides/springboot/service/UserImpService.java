package net.javaguides.springboot.service;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.exception.EmailAlreadyExistException;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.mapper.AutoUserMapper;
import net.javaguides.springboot.mapper.UserMapper;
import net.javaguides.springboot.repository.UserRepositoty;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserImpService implements UserService
{
    private UserRepositoty userRepositoty;
    private ModelMapper modelMapper;
    @Override
    public UserDto createUser(UserDto userdto)
    {
        // convert userdto into user jpa entity
       // User user= UserMapper.mapToUser(userdto);
       // User user=   modelMapper.map(userdto,User.class);
        Optional<User> optionalUser=userRepositoty.findByEmail(userdto.getEmail());
        if(optionalUser.isPresent())
        {
            throw new EmailAlreadyExistException("Email Already Exists for user");
        }
        User user= AutoUserMapper.Mapper.mapToUser(userdto);
        User savedUser= userRepositoty.save(user);
       // convert jpa entity to user dto
       //UserDto savedUserDto=UserMapper.mapToUserDto(savedUser);
        //UserDto savedUserDto=modelMapper.map(savedUser,UserDto.class);
        UserDto savedUserDto=AutoUserMapper.Mapper.mapToUserDto(savedUser);
        return savedUserDto;
    }

    @Override
    public UserDto getUserById(Long id)
    {
       // Optional<User> user=userRepositoty.findById(id);
        User user=userRepositoty.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("User","id",id)
        );
        //User user1=user.get();
        //return UserMapper.mapToUserDto(user1);
       // return modelMapper.map(user1,UserDto.class);
        return AutoUserMapper.Mapper.mapToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUser()
    {
       List<User> user= userRepositoty.findAll();
      /* return user.stream().map(UserMapper::mapToUserDto)
               .collect(Collectors.toList());*/
        /*return user.stream().map((user1)->modelMapper.map(user1,UserDto.class))
                .collect(Collectors.toList());*/
        return user.stream().map((user1)->AutoUserMapper.Mapper.mapToUserDto(user1))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto user, Long id)
    {
      //  User existingUser=userRepositoty.findById(id).get();
        User existingUser=userRepositoty.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("User","id",id)
        );
        existingUser.setEmail(user.getEmail());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        User updatedUser=userRepositoty.save(existingUser);
       // return UserMapper.mapToUserDto(updatedUser);
        //return modelMapper.map(updatedUser,UserDto.class);
        return AutoUserMapper.Mapper.mapToUserDto(updatedUser);
    }

    @Override
    public void deleteUser(Long id)
    {
        User existingUser=userRepositoty.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("User","id",id)
        );
        userRepositoty.deleteById(id);
    }


}

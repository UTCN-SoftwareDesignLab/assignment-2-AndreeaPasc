package com.assignment2.user.service;
import com.assignment2.user.model.User;
import com.assignment2.user.repository.UserRepository;
import com.assignment2.user.dto.UserListDTO;
import com.assignment2.user.dto.UserMinimalDTO;
import com.assignment2.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private User findById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The book was not found: " + id));
    }

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(userMapper::userListDtoFromUser)
                .collect(toList());
    }

    public List<UserListDTO> findAll(){
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserListDTO create(UserListDTO user) {
        return userMapper.toDTO(userRepository.save(
                userMapper.fromDTO(user)
        ));
    }

    public UserListDTO edit(Long id, UserListDTO user) {
        User actUser = findById(id);
        actUser.setRoles(user.getRoles());
        actUser.setUsername(user.getUsername());
        actUser.setEmail(user.getEmail());
        actUser.setPassword(user.getPassword());
        return userMapper.toDTO(
                userRepository.save(actUser)
        );
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }

}

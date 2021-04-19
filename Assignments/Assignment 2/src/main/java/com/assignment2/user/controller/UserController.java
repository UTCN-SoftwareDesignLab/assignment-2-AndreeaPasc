package com.assignment2.user.controller;
import com.assignment2.user.service.UserService;
import com.assignment2.user.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.assignment2.UrlMapping.ENTITY;
import static com.assignment2.UrlMapping.USER;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserListDTO> allUsers() {
        return userService.allUsersForList();
    }

    @PostMapping
    public UserListDTO create(@RequestBody UserListDTO user) {
        return userService.create(user);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @PatchMapping(ENTITY)
    public UserListDTO edit(@PathVariable Long id, @RequestBody UserListDTO user) {
        return userService.edit(id, user);
    }
}

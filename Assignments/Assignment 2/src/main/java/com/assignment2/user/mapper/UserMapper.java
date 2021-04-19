package com.assignment2.user.mapper;
import com.assignment2.user.dto.UserListDTO;
import com.assignment2.user.dto.UserMinimalDTO;
import com.assignment2.user.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "name", source = "user.username")
    })
    UserMinimalDTO userMinimalFromUser(User user);

    @Mappings({
            @Mapping(target = "name", source = "user.username"),
            @Mapping(target = "roles", ignore = true)
    })
    UserListDTO userListDtoFromUser(User user);

    @AfterMapping
    default void populateRoles(User user, @MappingTarget UserListDTO userListDTO) {
        userListDTO.setRoles(user.getRoles());
    }

    UserListDTO toDTO(User user);

    User fromDTO(UserListDTO user);
}

package com.assignment2.user.mapper;

import com.assignment2.user.dto.UserListDTO;
import com.assignment2.user.dto.UserMinimalDTO;
import com.assignment2.user.model.Role;
import com.assignment2.user.model.User;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-19T23:15:17+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.8.3.jar, environment: Java 15.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserMinimalDTO userMinimalFromUser(User user) {
        if ( user == null ) {
            return null;
        }

        UserMinimalDTO userMinimalDTO = new UserMinimalDTO();

        userMinimalDTO.setName( user.getUsername() );
        userMinimalDTO.setId( user.getId() );

        return userMinimalDTO;
    }

    @Override
    public UserListDTO userListDtoFromUser(User user) {
        if ( user == null ) {
            return null;
        }

        String email = null;
        String username = null;
        String password = null;

        email = user.getEmail();
        username = user.getUsername();
        password = user.getPassword();

        Set<Role> roles = null;

        UserListDTO userListDTO = new UserListDTO( email, roles, username, password );

        userListDTO.setName( user.getUsername() );
        userListDTO.setId( user.getId() );

        populateRoles( user, userListDTO );

        return userListDTO;
    }

    @Override
    public UserListDTO toDTO(User user) {
        if ( user == null ) {
            return null;
        }

        String email = null;
        Set<Role> roles = null;
        String username = null;
        String password = null;

        email = user.getEmail();
        Set<Role> set = user.getRoles();
        if ( set != null ) {
            roles = new HashSet<Role>( set );
        }
        username = user.getUsername();
        password = user.getPassword();

        UserListDTO userListDTO = new UserListDTO( email, roles, username, password );

        userListDTO.setId( user.getId() );

        populateRoles( user, userListDTO );

        return userListDTO;
    }

    @Override
    public User fromDTO(UserListDTO user) {
        if ( user == null ) {
            return null;
        }

        User user1 = new User();

        user1.setId( user.getId() );
        user1.setUsername( user.getUsername() );
        user1.setEmail( user.getEmail() );
        user1.setPassword( user.getPassword() );
        Set<Role> set = user.getRoles();
        if ( set != null ) {
            user1.setRoles( new HashSet<Role>( set ) );
        }

        return user1;
    }
}

package com.assignment2;
import com.assignment2.book.model.Book;
import com.assignment2.book.repository.BookRepository;
import com.assignment2.security.AuthService;
import com.assignment2.security.dto.SignupRequest;
import com.assignment2.user.repository.RoleRepository;
import com.assignment2.user.repository.UserRepository;
import com.assignment2.user.model.ERole;
import com.assignment2.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    private final BookRepository bookRepository;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            bookRepository.deleteAll();
            userRepository.deleteAll();
            roleRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            authService.register(SignupRequest.builder()
                    .email("admin@email.com")
                    .username("Admin")
                    .password("Pass1!")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("user@email.com")
                    .username("User")
                    .password("Pass1!")
                    .roles(Set.of("CUSTOMER"))
                    .build());
            bookRepository.save(Book.builder()
                    .author("Lev Tolstoy")
                    .title("Anna Karenina")
                    .quantity(10)
                    .genre("Classicism")
                    .price(50.0)
                    .build());
            bookRepository.save(Book.builder()
                    .author("Mihai Eminescu")
                    .title("Poems")
                    .quantity(5)
                    .genre("Romantism")
                    .price(50.0)
                    .build());
        }
    }
}

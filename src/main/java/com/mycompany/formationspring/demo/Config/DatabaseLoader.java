package com.mycompany.formationspring.demo.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.mycompany.formationspring.demo.entity.User;
import com.mycompany.formationspring.demo.repository.UserRepository;

@Configuration
public class DatabaseLoader {

  /** @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            User user = new User();
            user.setUsername("julien");
            user.setPassword(passwordEncoder.encode("12345678"));
            user.setEnabled(true);
            userRepository.save(user);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }**/
}

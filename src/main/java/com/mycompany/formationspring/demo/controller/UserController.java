package com.mycompany.formationspring.demo.controller;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycompany.formationspring.demo.dto.UserCreationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mycompany.formationspring.demo.entity.User;
import com.mycompany.formationspring.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
    @RequestMapping("/api/user")
    public class UserController {

        @Autowired
        private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
        @PostMapping("/create")
        public ResponseEntity<User> create(@RequestBody UserCreationDto userDto) {
            // Vérification du mot de passe et de sa confirmation
            if (userDto.getPassword().length() < 8 || !userDto.getPassword().equals(userDto.getConfirmPassword())) {
                return ResponseEntity.badRequest().build();
            }
            // Créer l'utilisateur à partir du DTO
            User user = new User();
            user.setUsername(userDto.getUsername());
            user.setRoles(userDto.getRoles());
            user.setPassword(userDto.getPassword());
            // Sauvegarder l'utilisateur
            User savedUser = userRepository.save(user);
            logger.debug("Creation d'un utilisateur");
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        }

        @GetMapping("/")
        public List<User> getAllUsers(
                @RequestParam(required = false, defaultValue = "id") String sortField,
                @RequestParam(required = false, defaultValue = "asc") String sortDirection,
                @RequestParam(required = false) String username,
                @RequestParam(required = false) String role

        ) {

            Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            Sort sort = Sort.by(direction, sortField);

            List<User> users;

            if (username != null && role != null) {
                users = userRepository.findByUsernameAndRoles(username, role);
                logger.debug("Recherche d'utilisateur(s)");
            } else if (username != null) {
                users = userRepository.findByUsername(username);
                logger.debug("Recherche d'utilisateur(s)");
            } else if (role != null) {
                users = userRepository.findByRole(role);
                logger.debug("Recherche d'utilisateur(s)");
            } else {
                users = userRepository.findAll(sort);
                logger.debug("Recherche d'utilisateur(s)");
            }

            return userRepository.findAll(sort);

        }

        @GetMapping("/{id}")
        public ResponseEntity<User> getUserById(@PathVariable Long id) {
            Optional<User> user = userRepository.findById(id);
            logger.debug("Recherche d'un utilisateur par id");
            return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

        }

        @PutMapping("/update/{id}")
        public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setUsername(userDetails.getUsername());
                user.setPassword(userDetails.getPassword());
                // Mettre à jour d'autres champs si nécessaire
                User updatedUser = userRepository.save(user);
                logger.debug("Modification d'un utilisateur");
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
                logger.debug("Suppression d'un utilisateur");
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        }

}
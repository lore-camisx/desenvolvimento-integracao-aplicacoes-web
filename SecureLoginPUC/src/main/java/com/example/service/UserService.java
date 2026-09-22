package com.example.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
        private final PasswordEncoder passwordEncoder;
        private final InMemoryUserDetailsManager userDetailsManager;
        private final Map<String, String> userNames = new HashMap<>();

        public UserService(PasswordEncoder passwordEncoder, InMemoryUserDetailsManager userDetailsManager) {
                this.passwordEncoder = passwordEncoder;
                this.userDetailsManager = userDetailsManager;
        }

        public void createUser(
                        String email,
                        String senha,
                        String nome) {

                UserDetails user = User.builder()
                                .username(email)
                                .password(
                                                passwordEncoder.encode(senha))
                                .roles("USER")
                                .build();
                userDetailsManager.createUser(user);

                userNames.put(
                                email,
                                nome);
        }

        public boolean exists(String email) {

                return userDetailsManager.userExists(email);
        }

        public String getName(String email) {

                return userNames.get(email);
        }

        public void updatePassword(
                        String email,
                        String novaSenha) {

                UserDetails usuarioAtual = userDetailsManager.loadUserByUsername(email);

                UserDetails usuarioAtualizado = User.builder()
                                .username(
                                                usuarioAtual.getUsername())
                                .password(
                                                passwordEncoder.encode(novaSenha))
                                .authorities(
                                                usuarioAtual.getAuthorities())
                                .build();

                userDetailsManager.updateUser(
                                usuarioAtualizado);
        }
}
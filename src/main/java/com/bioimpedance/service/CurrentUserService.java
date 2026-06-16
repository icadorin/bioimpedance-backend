package com.bioimpedance.service;

import com.bioimpedance.entity.User;
import com.bioimpedance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new SecurityException("Usuário não autenticado");
        }
        return userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new SecurityException("Usuário não encontrado"));
    }

    public String getCurrentUserId() {
        return getCurrentUser().getId();
    }

    /**
     * Busca um usuário por email sem depender do SecurityContext.
     * Usado em fluxos onde o token já foi lido manualmente (ex: logout),
     * antes de o contexto de segurança ser populado.
     */
    public Optional<User> getCurrentUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
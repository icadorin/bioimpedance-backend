package com.bioimpedance.service;

import com.bioimpedance.entity.User;
import com.bioimpedance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new SecurityException("Usuario nao autenticado");
        }

        return userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new SecurityException("Usuario nao encontrado"));
    }

    public String getCurrentUserId() {
        return getCurrentUser().getId();
    }
}

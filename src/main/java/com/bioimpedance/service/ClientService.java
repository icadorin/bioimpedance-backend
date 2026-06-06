package com.bioimpedance.service;

import com.bioimpedance.dto.request.ClientRequestDTO;
import com.bioimpedance.dto.response.ClientResponseDTO;
import com.bioimpedance.entity.Client;
import com.bioimpedance.exception.ResourceNotFoundException;
import com.bioimpedance.mapper.ClientMapper;
import com.bioimpedance.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final CurrentUserService currentUserService;

    @Transactional
    public ClientResponseDTO create(ClientRequestDTO dto) {
        String userId = currentUserService.getCurrentUserId();
        String emailLower = dto.getEmail().trim().toLowerCase();

        if (clientRepository.existsByEmailAndUserId(emailLower, userId)) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Client client = clientMapper.toEntity(dto);
        client.setUserId(userId);
        client.setEmail(emailLower);

        client = clientRepository.save(client);
        return clientMapper.toResponse(client);
    }

    public List<ClientResponseDTO> findAll() {
        String userId = currentUserService.getCurrentUserId();

        return clientRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
            .map(clientMapper::toResponse)
            .toList();
    }

    public ClientResponseDTO findById(String id) {
        String userId = currentUserService.getCurrentUserId();

        Client client = clientRepository.findByIdAndUserId(id, userId)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        return clientMapper.toResponse(client);
    }

    @Transactional
    public ClientResponseDTO update(String id, ClientRequestDTO dto) {
        String userId = currentUserService.getCurrentUserId();
        String emailLower = dto.getEmail().trim().toLowerCase();

        Client client = clientRepository.findByIdAndUserId(id, userId)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        if (clientRepository.existsByEmailAndUserIdAndIdNot(emailLower, userId, id)) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        clientMapper.updateEntity(client, dto);
        client.setEmail(emailLower);
        client = clientRepository.save(client);
        return clientMapper.toResponse(client);
    }

    @Transactional
    public void delete(String id) {
        String userId = currentUserService.getCurrentUserId();

        if (!clientRepository.existsByIdAndUserId(id, userId)) {
            throw new ResourceNotFoundException("Cliente não encontrado");
        }
        clientRepository.deleteById(id);
    }
}

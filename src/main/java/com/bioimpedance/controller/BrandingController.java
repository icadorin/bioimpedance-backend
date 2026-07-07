package com.bioimpedance.controller;

import com.bioimpedance.dto.request.BrandingRequestDTO;
import com.bioimpedance.dto.response.BrandingResponseDTO;
import com.bioimpedance.service.BrandingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/branding")
@RequiredArgsConstructor
public class BrandingController {

    private final BrandingService brandingService;

    @GetMapping
    public BrandingResponseDTO getBranding() {
        return brandingService.getBranding();
    }

    @GetMapping("/logo")
    public ResponseEntity<byte[]> getLogo() {
        return brandingService.getLogo();
    }

    @PutMapping
    public BrandingResponseDTO updateBranding(@Valid @RequestBody BrandingRequestDTO dto) {
        return brandingService.updateBranding(dto);
    }

    @PostMapping("/logo")
    public BrandingResponseDTO uploadLogo(@RequestParam("file") MultipartFile file) {
        return brandingService.uploadLogo(file);
    }

    @DeleteMapping("/logo")
    public BrandingResponseDTO deleteLogo() {
        return brandingService.deleteLogo();
    }
}
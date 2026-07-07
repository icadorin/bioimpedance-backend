package com.bioimpedance.entity;

import com.bioimpedance.constants.PdfTheme;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Configurações de branding do personal — entidade separada do User
 * para não poluir a tabela principal e permitir evolução independente.
 *
 * Relacionamento 1:1 lazy com User — só carregado quando necessário.
 * A imagem (logo) é armazenada como byte[] no banco (BLOB).
 */
@Entity
@Table(name = "branding_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandingProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    // ── Logo (armazenado no banco) ─────────────────────────────────────
    /** Dados binários do logo (null = sem logo). */
    @Lob
    @Column(name = "logo_data")
    private byte[] logoData;

    /** MIME type original validado no upload: image/png, image/jpeg, image/webp. */
    @Column(name = "logo_content_type", length = 50)
    private String logoContentType;

    /** Tamanho do logo em bytes — útil para validação e exibição. */
    @Column(name = "logo_size")
    private Long logoSize;

    // ── Marca d'água ─────────────────────────────────────────────────────
    /** Texto da marca d'água (ex: "Academia Silva" ou "@joaopersonal"). */
    @Column(name = "watermark_text", length = 100)
    private String watermarkText;

    /**
     * Opacidade da marca d'água: 0.0 (invisível) a 1.0 (totalmente opaco).
     * Valor padrão 0.15 — discreto mas legível.
     */
    @Column(name = "watermark_opacity")
    @Builder.Default
    private Double watermarkOpacity = 0.15;

    // ── Tema ─────────────────────────────────────────────────────────────
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private PdfTheme theme = PdfTheme.LIGHT;

    // ── Rodapé ───────────────────────────────────────────────────────────
    /** Nome exibido no rodapé do PDF (ex: "Prof. João Silva - CREF 12345"). */
    @Column(name = "footer_name", length = 150)
    private String footerName;

    /** Contato no rodapé (email, telefone). */
    @Column(name = "footer_contact", length = 200)
    private String footerContact;

    /** Redes sociais no rodapé (ex: "@joaopersonal | joao.com"). */
    @Column(name = "footer_social", length = 200)
    private String footerSocial;

    // ── Timestamps ───────────────────────────────────────────────────────
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
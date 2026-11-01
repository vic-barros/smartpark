package br.com.smartpark.estacionamento.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Tarifa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tarifaID")
    private Integer tarifaId;

    @Column(name = "descricao", length = 255, nullable = false)
    private String descricao;

    @Column(name = "valorPrimeiraHora", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorPrimeiraHora;

    @Column(name = "valorHoraAdicional", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorHoraAdicional;

    @Column(name = "toleranciaMinutos", nullable = false)
    private Short toleranciaMinutos;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Column(name = "dataCadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "dataAtualizacao", nullable = true)
    private LocalDateTime dataAtualizacao;

    @PrePersist // Roda antes do INSERT
    private void aoCadastrar() {
        dataCadastro = LocalDateTime.now(); // Antes do insert, automaticamente o java vai pegar a data e hora e setar automaticamente
        if (ativo == null) {
            ativo = true; // Toda tarifa nasce ativa
        }

        if (toleranciaMinutos == null) {
            toleranciaMinutos = 15;
        }
    }

    @PreUpdate // Roda antes do UPDATE
    private void aoAtualizar() {
        dataAtualizacao = LocalDateTime.now();
    }
}

package br.com.smartpark.estacionamento.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Pagamento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pagamentoID")
    private Integer pagamentoId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticketID", nullable = false, unique = true,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT,
                    name = "fk_pagamento_ticket"))
    private Ticket ticket;

    @Column(name = "dataHoraPagamento", nullable = false, updatable = false)
    private LocalDateTime dataHoraPagamento;

    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Column(name = "dataCadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "dataAtualizacao", nullable = true)
    private LocalDateTime dataAtualizacao;

    @PrePersist // Roda antes do INSERT
    private void aoCadastrar() {
        dataCadastro = LocalDateTime.now();
        if (ativo == null) {
            ativo = true;
        }

        if (dataHoraPagamento == null) {
            dataHoraPagamento = LocalDateTime.now();
        }
    }

    @PreUpdate // Roda antes do UPDATE
    private void aoAtualizar() {
        dataAtualizacao = LocalDateTime.now();
    }

}

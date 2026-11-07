package br.com.smartpark.estacionamento.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Ticket")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticketID")
    private Integer ticketId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculoID", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name =
                    "fk_ticket_veiculo"))
    private Veiculo veiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vagaID", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name =
                    "fk_ticket_vaga"))
    private Vaga vaga;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarifaID", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name =
                    "fk_ticket_tarifa"))
    private Tarifa tarifa;

    @Column(name = "qrCodeToken", nullable = false, updatable = false, unique = true)
    private UUID qrCodeToken; // Token vai dentro do QrCode, se for Integer (1,2,...), qualquer um poderia advinhar

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Column(name = "dataHoraEntrada", nullable = false, updatable = false)
    private LocalDateTime dataHoraEntrada;

    @Column(name = "dataHoraSaida", nullable = true, updatable = true)
    private LocalDateTime dataHoraSaida;

    @Column(name = "dataCadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "dataAtualizacao", nullable = true, updatable = true)
    private LocalDateTime dataAtualizacao;

    @PrePersist // Roda antes do INSERT
    private void aoCadastrar() {
        dataCadastro = LocalDateTime.now(); // Antes do insert, automaticamente o java vai pegar a data e hora e setar automaticamente
        if (dataHoraEntrada == null) dataHoraEntrada = LocalDateTime.now();
        if (qrCodeToken == null)
            qrCodeToken = UUID.randomUUID(); // Gera Token aleatório para o ticket > Aqui nenhum ticket é gerado sem token
        if (ativo == null) ativo = true;
    }

    @PreUpdate // Roda antes do UPDATE
    private void aoAtualizar() {
        dataAtualizacao = LocalDateTime.now();
    }
}

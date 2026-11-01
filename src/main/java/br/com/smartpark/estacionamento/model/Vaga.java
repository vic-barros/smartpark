package br.com.smartpark.estacionamento.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Vaga")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vagaID")
    private Integer vagaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipVagaID", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_vaga_tipo_vaga"))
    private TipoVaga tipoVaga;

    @Column(name = "codigo", nullable = false, length = 20, unique = true)
    private String codigo;

    @Column(name = "bloqueada", nullable = false)
    private Boolean bloqueada;

    @Column(name = "motivoBloqueio", length = 255)
    private String motivoBloqueio;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Column(name = "dataCadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "dataAtualizacao", nullable = true)
    private LocalDateTime dataAtualizacao;

    @PrePersist
    private void aoCadastrar() {
        dataCadastro = LocalDateTime.now();
        if (ativo == null) {
            ativo = true; // Todo veículo nasce ativo
        }

        if (bloqueada == null) {
            bloqueada = false; // toda vaga nasce liberada
        }
    }

    @PreUpdate
    private void aoAtualizar() {
        dataAtualizacao = LocalDateTime.now();
    }
}

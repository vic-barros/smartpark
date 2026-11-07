package br.com.smartpark.estacionamento.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "TipoVaga")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoVaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipoVagaID")
    private Integer tipoVagaId;

    @Column(name = "nome", length = 100, nullable = false, unique = true)
    private String nome;

    @Column(name = "descricao", length = 255, nullable = false)
    private String descricao;

    @Column(name = "exigeCredencial", nullable = false)
    private Boolean exigeCredencial;

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
            ativo = true;
        }

        if(exigeCredencial == null){
            exigeCredencial = false;
    }
    }

    @PreUpdate // Roda antes do UPDATE
    private void aoAtualizar() {
        dataAtualizacao = LocalDateTime.now();
    }

}

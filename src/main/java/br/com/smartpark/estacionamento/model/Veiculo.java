package br.com.smartpark.estacionamento.model;

import br.com.smartpark.estacionamento.enums.TipoVeiculo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Veiculo")
@Getter
@Setter
@AllArgsConstructor // Construtor com todos argumentos por baixo dos pano
@NoArgsConstructor  // Construtor vazio
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Estratégia de geração de valor da numeração no banco
    @Column(name = "veiculoID") // A Identity entrega a responsabilidade ao banco de dados toda sequência de valor da PK
    private Integer veiculoId;

    //unique pq a placa é única, p não ter a possibilidade de gravar dois veículos com a mesma placa no banco
    @Column(name = "placa", length = 10, nullable = false, unique = true)
    private String placa;

    @Column(name = "modelo", length = 100, nullable = false)
    private String modelo;

    @Column(name = "cor", length = 50, nullable = false)
    private String cor;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 20, nullable = false)
    private TipoVeiculo tipoVeiculo;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Column(name = "dataCadastro", nullable = false, updatable = false) //uma vez que essa data seja inserida no insert, essa data hora não pode ser alterada pela aplicação
    private LocalDateTime dataCadastro;

    @Column(name = "dataAtualizacao", nullable = true)
    private LocalDateTime dataAtualizacao;

    @PrePersist // Roda antes do INSERT
    private void aoCadastrar() {
        dataCadastro = LocalDateTime.now(); // Antes do insert, automaticamente o java vai pegar a data e hora e setar automaticamente
        if (ativo == null) {
            ativo = true; // Todo veículo nasce ativo
        }
    }

    @PreUpdate // Roda antes do UPDATE
    private void aoAtualizar() {
        dataAtualizacao = LocalDateTime.now();
    }

    //quando vier comando update se o cara da aplicação tentar burlar, a classe vai pegar o data e hora de agora e setar na data de atualização
}

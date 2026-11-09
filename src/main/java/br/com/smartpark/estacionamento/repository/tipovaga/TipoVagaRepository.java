package br.com.smartpark.estacionamento.repository.tipovaga;

import br.com.smartpark.estacionamento.model.TipoVaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoVagaRepository extends JpaRepository<TipoVaga, Integer> {

    // Vai retornar um booleano se existir vaga com o mesmo nome
    Boolean existsByNomeIgnoreCase(String nome);

    // Lista os tipos de vagas ativos em ordem crescente pelo nome
    List<TipoVaga> findByAtivoTrueOrderByNomeAsc();
}

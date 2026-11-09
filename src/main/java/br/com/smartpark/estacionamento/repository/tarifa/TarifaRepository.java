package br.com.smartpark.estacionamento.repository.tarifa;

import br.com.smartpark.estacionamento.model.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TarifaRepository extends JpaRepository<Tarifa, Integer> {

    // Listar tarifas ativas
    List<Tarifa> findByAtivoTrue();

    // Busca a tarifa ativa cadastrada mais recentemente
    Optional<Tarifa> findFirstByAtivoTrueOrderByDataCadastroDesc();
}

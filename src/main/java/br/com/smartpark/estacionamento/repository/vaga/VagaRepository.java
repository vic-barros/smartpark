package br.com.smartpark.estacionamento.repository.vaga;

import br.com.smartpark.estacionamento.model.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Integer> {

    // Busca se existe o código requisitado
    Boolean existsByCodigo(String codigo);

    // Busca pelo codigo unico
    Optional<Vaga> findByCodigo(String codigo);

    @Query("""
            SELECT v FROM Vaga v
            WHERE v.ativo = true
                AND v.bloqueada = false
                AND NOT EXISTS (
                    SELECT t FROM Ticket t
                    WHERE t.vaga = v AND t.dataHoraSaida IS NULL
              )
            """)
    List<Vaga> buscarVagasLivres();

    @Query("""
            SELECT v FROM Vaga v
            WHERE v.ativo = true
                AND EXISTS (
                    SELECT t FROM Ticket t
                    WHERE t.vaga = v AND t.dataHoraSaida IS NULL
              )
            """)
    List<Vaga> buscarVagasOcupadas();


}

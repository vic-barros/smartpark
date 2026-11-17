package br.com.smartpark.estacionamento.repository.veiculo;

import br.com.smartpark.estacionamento.enums.TipoVeiculo;
import br.com.smartpark.estacionamento.model.Veiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Passa de parâmetro a Model e o tipo de dado do veiculoId (o tipo de dado da chave primária)
@Repository // Spring olhe esta interface de modo especial, ela não é puramente Java
public interface VeiculoRepository extends JpaRepository<Veiculo, Integer> {

    // Pergunta se já existe essa placa no banco (veículo já está cadastrado?)
    boolean existsByPlaca(String placa);

    // Busca pela placa, usa o optional para não dar nullpointer caso seja nula a placa
    Optional<Veiculo> findByPlaca(String placa);

    Optional<Veiculo> findByVeiculoIDAndAtivoTrue(Integer veiculoID);

    List<Veiculo> findByTipoAndAtivoTrueOrderByModeloAsc(TipoVeiculo tipo);

    List<Veiculo> findByModeloContainingIgnoreCase(String trecho);

    @Query("""
            SELECT v.tipo AS tipo, COUNT(v) AS total
              FROM VeiculoModel v
             WHERE v.ativo = true
             GROUP BY v.tipo
            """)
    List<TotalPorTipo> totalPorTipo();

    @Query(value = """
            SELECT TOP 5 * FROM Veiculo
             WHERE ativo = 1
             ORDER BY dataCadastro DESC
            """, nativeQuery = true)
    List<Veiculo> ultimosCadastrados();

    Page<Veiculo> findByAtivoTrue(Pageable pageable);


}

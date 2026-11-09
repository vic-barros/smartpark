package br.com.smartpark.estacionamento.repository.veiculo;

import br.com.smartpark.estacionamento.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Passa de parâmetro a Model e o tipo de dado do veiculoId (o tipo de dado da chave primária)
@Repository // Spring olhe esta interface de modo especial, ela não é puramente Java
public interface VeiculoRepository extends JpaRepository<Veiculo, Integer> {

    // Pergunta se já existe essa placa no banco (veículo já está cadastrado?)
    Boolean existsByPlaca(String placa);

    // Busca pela placa, usa o optional para não dar nullpointer caso seja nula a placa
    Optional<Veiculo> findByPlaca(String placa);

    // Se cogitou o resultado ser mais de um veículo o tipo é List
    List<Veiculo> findByVeiculoIdAndAtivoTrue(Integer veiculoId);


}

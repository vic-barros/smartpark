package br.com.smartpark.estacionamento.repository.pagamento;

import br.com.smartpark.estacionamento.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

    // Esse "ticket"já foi pago?
    Boolean existsByTicketTicketId(Integer ticketId);

    // Qual foi o pagamento desse "ticket"?
    Optional<Pagamento> findByTicketTicketId(Integer ticketId);


}

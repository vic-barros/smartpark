package br.com.smartpark.estacionamento.repository.ticket;

import br.com.smartpark.estacionamento.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    // Busca se o veículo possui outro "ticket" em aberto
    boolean existsByVeiculoVeiculoIdAndDataHoraSaidaIsNull(Integer veiculoId);

    // Pergunta se a vaga está ocupada
    boolean existsByVagaVagaIdAndDataHoraSaidaIsNull(Integer vagaId);

    // Encontra o ticket a partir do código dentro do qrcode
    Optional<Ticket> findByQrCodeToken(UUID codigoQrCode);

    // Consulta os tickets em aberto
    List<Ticket> findByDataHoraSaidaIsNullOrderByDataHoraEntradaAsc();
}

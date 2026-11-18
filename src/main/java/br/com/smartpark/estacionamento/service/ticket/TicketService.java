package br.com.smartpark.estacionamento.service.ticket;

import br.com.smartpark.estacionamento.dto.request.ticket.TicketRequestDto;
import br.com.smartpark.estacionamento.dto.response.ticket.TicketResponseDto;
import br.com.smartpark.estacionamento.exception.RecursoNaoEncontradoException;
import br.com.smartpark.estacionamento.exception.RegraNegocioException;
import br.com.smartpark.estacionamento.model.Tarifa;
import br.com.smartpark.estacionamento.model.Ticket;
import br.com.smartpark.estacionamento.model.Vaga;
import br.com.smartpark.estacionamento.model.Veiculo;
import br.com.smartpark.estacionamento.repository.pagamento.PagamentoRepository;
import br.com.smartpark.estacionamento.repository.tarifa.TarifaRepository;
import br.com.smartpark.estacionamento.repository.ticket.TicketRepository;
import br.com.smartpark.estacionamento.repository.vaga.VagaRepository;
import br.com.smartpark.estacionamento.repository.veiculo.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final VeiculoRepository veiculoRepository;
    private final VagaRepository vagaRepository;
    private final TarifaRepository tarifaRepository;
    private final PagamentoRepository pagamentoRepository;
    private final CalculadoraTarifa calculadoraTarifa;

    @Transactional
    public TicketResponseDto registrarEntrada(TicketRequestDto req) {
        Veiculo veiculo = veiculoRepository.findByPlaca(req.placa())
                .filter(Veiculo::getAtivo)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Veículo com placa " + req.placa() + " não encontrado"));

        // Regra: um veículo não pode ter dois tickets abertos
        if (ticketRepository.existsByVeiculoVeiculoIdAndDataHoraSaidaIsNull(veiculo.getVeiculoId()))
            throw new RegraNegocioException("O veículo " + req.placa() + " já possui um ticket aberto");

        Vaga vaga = vagaRepository.findById(req.vagaId())
                .filter(Vaga::getAtivo)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Vaga " + req.vagaId() + " não encontrada"));

        // Regra: vaga bloqueada ou ocupada não pode ser utilizada
        if (vaga.getBloqueada())
            throw new RegraNegocioException("A vaga " + vaga.getCodigo() + " está bloqueada");
        if (ticketRepository.existsByVagaVagaIdAndDataHoraSaidaIsNull(vaga.getVagaId()))
            throw new RegraNegocioException("A vaga " + vaga.getCodigo() + " está ocupada");

        Tarifa tarifa = tarifaRepository.findFirstByAtivoTrueOrderByDataCadastroDesc()
                .orElseThrow(() -> new RegraNegocioException("Nenhuma tarifa ativa cadastrada"));

        Ticket salvo = ticketRepository.save(req.paraModel(veiculo, vaga, tarifa));
        return TicketResponseDto.de(salvo);
    }

    @Transactional(readOnly = true)
    public TicketResponseDto buscarPorQrCode(UUID qrCodeToken) {
        return TicketResponseDto.de(buscarPorToken(qrCodeToken));
    }

    // Regra: consultar tickets em aberto
    @Transactional(readOnly = true)
    public List<TicketResponseDto> listarAbertos() {
        return ticketRepository.findByDataHoraSaidaIsNullOrderByDataHoraEntradaAsc()
                .stream()
                .map(TicketResponseDto::de)
                .toList();
    }

    // Quanto o cliente deve agora, antes de pagar
    @Transactional(readOnly = true)
    public BigDecimal consultarValor(UUID qrCodeToken) {
        Ticket ticket = buscarPorToken(qrCodeToken);

        if (ticket.getDataHoraSaida() != null)
            throw new RegraNegocioException("Este ticket já foi encerrado");

        return calculadoraTarifa.calcular(ticket, LocalDateTime.now());
    }

    @Transactional
    public TicketResponseDto liberarSaida(UUID qrCodeToken) {
        Ticket ticket = buscarPorToken(qrCodeToken);

        if (ticket.getDataHoraSaida() != null)
            throw new RegraNegocioException("A saída deste ticket já foi registrada");

        // Regra: a saída só pode ser liberada após o pagamento
        if (!pagamentoRepository.existsByTicketTicketId(ticket.getTicketId()))
            throw new RegraNegocioException("A saída só pode ser liberada após o pagamento");

        ticket.setDataHoraSaida(LocalDateTime.now());   // libera a vaga (dirty checking)
        return TicketResponseDto.de(ticket);
    }

    private Ticket buscarPorToken(UUID qrCodeToken) {
        return ticketRepository.findByQrCodeToken(qrCodeToken)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Ticket não encontrado para o QR Code informado"));
    }
}
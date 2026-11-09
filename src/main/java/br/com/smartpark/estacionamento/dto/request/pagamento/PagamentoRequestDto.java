package br.com.smartpark.estacionamento.dto.request.pagamento;

import br.com.smartpark.estacionamento.model.Pagamento;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PagamentoRequestDto(

        @NotNull(message = "É obrigtório informar o QrCode do ticket")
        UUID qrCodeToken
) {
}

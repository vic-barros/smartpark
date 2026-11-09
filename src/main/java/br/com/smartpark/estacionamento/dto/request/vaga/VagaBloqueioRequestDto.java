package br.com.smartpark.estacionamento.dto.request.vaga;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VagaBloqueioRequestDto(

        @NotBlank(message = "O motivo do bloqueio é obrigatório")
        @Size(max = 255, message = "O motivo deve ter no máximo 255 caracteres")
        String motivoBloqueio
) {
}

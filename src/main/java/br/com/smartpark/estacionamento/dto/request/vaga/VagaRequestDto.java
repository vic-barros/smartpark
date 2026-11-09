package br.com.smartpark.estacionamento.dto.request.vaga;

import br.com.smartpark.estacionamento.model.TipoVaga;
import br.com.smartpark.estacionamento.model.Vaga;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record VagaRequestDto(

        @NotBlank(message = "O código da vaga é obrigatório")
        @Size(max = 20, message = "O código deve ter no máximo 20 caracteres")
        String codigo,

        @NotNull(message = "O tipo de vaga é obrigatório")
        Integer tipoVagaId
) {

    // Normaliza o código
    public VagaRequestDto {
        if (codigo != null) {
            codigo = codigo.trim().toUpperCase();
        }
    }

    public Vaga paraModel(TipoVaga tipoVaga) {
        Vaga vaga = new Vaga();
        vaga.setCodigo(codigo);
        vaga.setTipoVaga(tipoVaga);
        return vaga;
    }
}

package br.com.smartpark.estacionamento.dto.request.tipovaga;

import br.com.smartpark.estacionamento.model.TipoVaga;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TipoVagaRequestDto(

        @NotBlank(message = "O nome do tipo de vaga é obrigatório")
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        String nome,

        @NotBlank(message = "A descrição do tipo de vaga é obrigatória")
        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
        String descricao,

        @NotBlank(message = "É obrigatório informar se exige credencial para utilizar determinado tipo de vaga")
        boolean exigeCredencial

) {

    public TipoVaga paraModel() {
        TipoVaga tipoVaga = new TipoVaga();
        tipoVaga.setNome(nome);
        tipoVaga.setDescricao(descricao);
        tipoVaga.setExigeCredencial(exigeCredencial);
        return tipoVaga;

    }


}

package br.com.smartpark.estacionamento.dto.response.vaga;

import br.com.smartpark.estacionamento.model.Vaga;

import java.time.LocalDateTime;

public record VagaResponseDto(

        Integer vagaId,
        String codigo,
        Integer tipoVagaId,
        String tipoVaga,
        Boolean exigeCredencial,
        Boolean bloqueada,
        String motivoBloqueio,
        LocalDateTime dataCadastro
) {

    public static VagaResponseDto de(Vaga vaga) {
        return new VagaResponseDto(
                vaga.getVagaId(),
                vaga.getCodigo(),
                vaga.getTipoVaga().getTipoVagaId(),
                vaga.getTipoVaga().getNome(),
                vaga.getTipoVaga().getExigeCredencial(),
                vaga.getBloqueada(),
                vaga.getMotivoBloqueio(),
                vaga.getDataCadastro()
        );
    }
}

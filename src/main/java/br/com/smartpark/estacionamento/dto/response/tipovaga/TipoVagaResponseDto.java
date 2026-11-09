package br.com.smartpark.estacionamento.dto.response.tipovaga;

import br.com.smartpark.estacionamento.model.TipoVaga;

import java.time.LocalDateTime;

public record TipoVagaResponseDto(
        Integer tipoVagaId,
        String nome,
        String descricao,
        Boolean exigeCredencial,
        Boolean ativo,
        LocalDateTime dataCadastro,
        LocalDateTime dataAtualizacao
) {

    public static TipoVagaResponseDto de(TipoVaga tipoVaga) {
        return new TipoVagaResponseDto(
                tipoVaga.getTipoVagaId(),
                tipoVaga.getNome(),
                tipoVaga.getDescricao(),
                tipoVaga.getExigeCredencial(),
                tipoVaga.getAtivo(),
                tipoVaga.getDataCadastro(),
                tipoVaga.getDataAtualizacao()
        );
    }
}

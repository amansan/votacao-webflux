package br.com.sicredi.desafio.votacao.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class VotoRequest implements Serializable {

    private static final long serialVersionUID = 5262161136819235809L;

    @NotNull(message = "O voto deve estar vinculado a um CPF válido.")
    private String cpfAssociado;

    @NotNull(message = "O voto deve estar vinculado a uma pauta.")
    private String pauta;

    private String valor;

}

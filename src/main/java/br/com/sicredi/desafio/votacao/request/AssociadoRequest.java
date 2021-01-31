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
public class AssociadoRequest implements Serializable {

    private static final long serialVersionUID = 138709654007462417L;

    private String nome;

    @NotNull(message = "CPF do associado não pode ser nulo.")
    private String cpf;
}

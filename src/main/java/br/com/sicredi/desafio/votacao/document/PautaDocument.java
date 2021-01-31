package br.com.sicredi.desafio.votacao.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Pauta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PautaDocument {

    @Id
    private String id;

    private String assunto;

    private Integer totalVotosSim;

    private Integer totalVotosNao;
}

package br.com.sicredi.desafio.votacao.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("SessaoVoto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessaoVotoDocument {

    @Id
    private String id;

    private String valor;

    private String pautaId;

    private String cpfAssociado;
}

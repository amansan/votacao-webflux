package br.com.sicredi.desafio.votacao.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Voto")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VotoDocument {

    @Id
    private String id;

    private String valor;

    private String pautaId;

    private String cpfAssociado;
}

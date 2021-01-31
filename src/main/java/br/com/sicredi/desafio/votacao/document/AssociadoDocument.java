package br.com.sicredi.desafio.votacao.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Associado")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AssociadoDocument {

    @Id
    private String id;

    private String nome;

    private String cpf;

}

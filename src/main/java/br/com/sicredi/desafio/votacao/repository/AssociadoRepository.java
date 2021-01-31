package br.com.sicredi.desafio.votacao.repository;

import br.com.sicredi.desafio.votacao.document.AssociadoDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AssociadoRepository extends ReactiveMongoRepository<AssociadoDocument, String> {

    Mono<AssociadoDocument> findByCpf(String cpf);
}

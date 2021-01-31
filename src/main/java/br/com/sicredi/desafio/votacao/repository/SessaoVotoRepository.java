package br.com.sicredi.desafio.votacao.repository;

import br.com.sicredi.desafio.votacao.document.SessaoVotoDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface SessaoVotoRepository extends ReactiveMongoRepository<SessaoVotoDocument, String> {

    @Tailable
    Flux<SessaoVotoDocument> findAllBy();
}

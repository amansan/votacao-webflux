package br.com.sicredi.desafio.votacao.repository;

import br.com.sicredi.desafio.votacao.document.VotoDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VotoRepository extends ReactiveMongoRepository<VotoDocument, String> {
}

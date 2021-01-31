package br.com.sicredi.desafio.votacao.repository;

import br.com.sicredi.desafio.votacao.document.PautaDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PautaRepository extends ReactiveMongoRepository<PautaDocument, String> {

}

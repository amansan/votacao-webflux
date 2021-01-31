package br.com.sicredi.desafio.votacao.repository;

import br.com.sicredi.desafio.votacao.document.PautaDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PautaRepositoryNonReactive extends MongoRepository<PautaDocument, String> {
}

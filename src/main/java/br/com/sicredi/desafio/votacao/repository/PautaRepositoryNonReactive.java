package br.com.sicredi.desafio.votacao.repository;

import com.sicredi.desafio.votacao.document.PautaDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PautaRepositoryNonReactive extends MongoRepository<PautaDocument, String> {
}

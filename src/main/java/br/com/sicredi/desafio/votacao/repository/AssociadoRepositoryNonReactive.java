package br.com.sicredi.desafio.votacao.repository;

import br.com.sicredi.desafio.votacao.document.AssociadoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AssociadoRepositoryNonReactive extends MongoRepository<AssociadoDocument, String> {
    Optional<AssociadoDocument> findByCpf(String cpfAssociado);
}

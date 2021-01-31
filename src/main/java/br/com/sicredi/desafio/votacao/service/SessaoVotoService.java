package br.com.sicredi.desafio.votacao.service;

import com.sicredi.desafio.votacao.document.PautaDocument;
import com.sicredi.desafio.votacao.document.SessaoVotoDocument;
import com.sicredi.desafio.votacao.repository.PautaRepositoryNonReactive;
import com.sicredi.desafio.votacao.repository.SessaoVotoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
@AllArgsConstructor
@Slf4j
public class SessaoVotoService {

    private SessaoVotoRepository sessaoVotoRepository;

    private PautaRepositoryNonReactive pautaRepositoryNonReactive;

    private MongoOperations mongoOperations;

    public Flux<SessaoVotoDocument> novaSessao(Integer duracao, String pauta) {
        log.info("Iniciando uma nova sessão com duração de {} segundos. Pauta: {}", duracao, pauta);

        Optional<PautaDocument> pautaDocumentOptional = pautaRepositoryNonReactive.findById(pauta);

        if(pautaDocumentOptional.isEmpty()){
            throw new RuntimeException("Sessão deve estar associada a uma pauta válida.");
        } else {
            inicializaTotaisDaPauta(pautaDocumentOptional.get());
        }

        mongoOperations.dropCollection(SessaoVotoDocument.class);
        mongoOperations.createCollection(SessaoVotoDocument.class, CollectionOptions.empty().maxDocuments(50000).size(50000).capped());

        sessaoVotoRepository.save(SessaoVotoDocument.builder()
                .pautaId(pauta)
                .build())
                .subscribe();

        Flux<SessaoVotoDocument> response = listarParaStream().take(Duration.ofMinutes(ofNullable(duracao).orElse(1)));

        log.info("Encerrando sessão para pauta {}.", pauta);

        return response;
    }

    private void inicializaTotaisDaPauta(PautaDocument pautaDocument) {
        pautaDocument.setTotalVotosNao(0);
        pautaDocument.setTotalVotosSim(0);

        pautaRepositoryNonReactive.save(pautaDocument);

        log.info("Valores totais de votos da pauta {} inicializados.", pautaDocument.getAssunto());
    }

    public Flux<SessaoVotoDocument> listarParaStream() {
        return sessaoVotoRepository.findAllBy();
    }

    public Flux<SessaoVotoDocument> listar() {
        return this.sessaoVotoRepository.findAll();
    }
}
package br.com.sicredi.desafio.votacao.service;

import br.com.sicredi.desafio.votacao.document.AssociadoDocument;
import br.com.sicredi.desafio.votacao.repository.AssociadoRepository;
import br.com.sicredi.desafio.votacao.request.AssociadoRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class AssociadoService {

    private AssociadoRepository associadoRepository;

    public Mono<AssociadoDocument> incluir(AssociadoRequest associadoRequest) {
        log.info("Criando associado: {}", associadoRequest.getNome());
        return associadoRepository.save(buildAssociado(associadoRequest));
    }

    private AssociadoDocument buildAssociado(AssociadoRequest associadoRequest) {
        return AssociadoDocument.builder()
                .nome(associadoRequest.getNome())
                .cpf(associadoRequest.getCpf())
                .build();
    }

    public Flux<AssociadoDocument> listar() {
        return associadoRepository.findAll();
    }

    public Mono<Void> excluir() {
        log.info("Excluindo todos os associados.");
        return associadoRepository.deleteAll();
    }

    public Mono<AssociadoDocument> findByCpf(String cpf) {
        return associadoRepository.findByCpf(cpf);
    }
}

package br.com.sicredi.desafio.votacao.service;

import com.sicredi.desafio.votacao.document.PautaDocument;
import com.sicredi.desafio.votacao.repository.PautaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class PautaService {

    private PautaRepository pautaRepository;

    public Mono<PautaDocument> incluir(String assunto) {
        log.info("Criando pauta com assunto: {}", assunto);

        return pautaRepository.save(buildPauta(assunto));
    }

    private PautaDocument buildPauta(String assunto) {
        return PautaDocument.builder()
                .assunto(assunto)
                .build();
    }

    public Flux<PautaDocument> listar() {
        return this.pautaRepository.findAll();
    }

    public Mono<Void> excluir() {
        log.info("Excluindo todas as pautas.");
        return pautaRepository.deleteAll();
    }
}

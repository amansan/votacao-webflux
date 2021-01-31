package br.com.sicredi.desafio.votacao.service;

import br.com.sicredi.desafio.votacao.document.PautaDocument;
import br.com.sicredi.desafio.votacao.document.SessaoVotoDocument;
import br.com.sicredi.desafio.votacao.document.VotoDocument;
import br.com.sicredi.desafio.votacao.repository.*;
import br.com.sicredi.desafio.votacao.request.VotoRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static br.com.sicredi.desafio.votacao.Constantes.VOTO_NAO;
import static br.com.sicredi.desafio.votacao.Constantes.VOTO_SIM;
import static java.util.Optional.ofNullable;

@Service
@AllArgsConstructor
@Slf4j
public class VotoService {

    private VotoRepository votoRepository;

    private PautaRepositoryNonReactive pautaRepositoryNonReactive;

    private SessaoVotoRepository sessaoVotoRepository;

    private AssociadoRepositoryNonReactive associadoRepositoryNonReactive;

    public Mono<VotoDocument> incluir(VotoRequest votoRequest, String pauta, List<String> cpfVotantes) {
        log.info("Criando voto com valor {} para cpf {} na pauta {}.", votoRequest.getValor(), votoRequest.getCpfAssociado(), votoRequest.getPauta());

        validarVoto(votoRequest, pauta, cpfVotantes);

        atualizarVotacaoNaPauta(votoRequest, pauta, votoRequest.getValor());

        sessaoVotoRepository.save(buildSessaoVoto(votoRequest)).subscribe();

        return votoRepository.save(buildVoto(votoRequest));
    }

    private void atualizarVotacaoNaPauta(VotoRequest votoRequest, String pauta, String valor) {
        PautaDocument pautaDocument = pautaRepositoryNonReactive.findById(pauta).get();

        if(VOTO_NAO.equals(valor)){
            pautaDocument.setTotalVotosNao(ofNullable(pautaDocument).map(PautaDocument::getTotalVotosNao).orElse(0) + 1);
        } else if(VOTO_SIM.equals(valor)){
            pautaDocument.setTotalVotosSim(ofNullable(pautaDocument).map(PautaDocument::getTotalVotosSim).orElse(0) + 1);
        }

        pautaRepositoryNonReactive.save(pautaDocument);

        log.info("Pauta {} recebeu o voto {} do associado de cpf {}.",
                votoRequest.getPauta(),
                valor,
                votoRequest.getCpfAssociado());
    }

    private void validarVoto(VotoRequest votoRequest, String pauta, List<String> cpfVotantes) {

        if(!(ofNullable(votoRequest.getValor()).orElse("")
                .equals(VOTO_SIM)) &&
                !(ofNullable(votoRequest.getValor()).orElse("")
                        .equals(VOTO_NAO))){
            throw new RuntimeException("O valor do voto de ver \"Sim\" ou \"Nao\".");
        }

        if(ofNullable(cpfVotantes).orElseGet(ArrayList::new)
                .contains(ofNullable(votoRequest.getCpfAssociado()).orElse(""))){
            RuntimeException e = new RuntimeException("O associado informado já teve o voto registrado nesta sessão.");
            log.error("Erro criando voto com valor {} para cpf {} na pauta {}.", votoRequest.getValor(), votoRequest.getCpfAssociado(), votoRequest.getPauta(), e);
            throw e;
        }

        if(!(ofNullable(votoRequest.getPauta()).orElse("").equals(pauta))){
            RuntimeException e = new RuntimeException("O voto deve estar vinculado à pauta da sessão.");
            log.error("Erro criando voto com valor {} para cpf {} na pauta {}.", votoRequest.getValor(), votoRequest.getCpfAssociado(), votoRequest.getPauta(), e);
            throw e;
        }

        if(associadoRepositoryNonReactive.findByCpf(ofNullable(votoRequest.getCpfAssociado()).orElse("")).isEmpty()){
            RuntimeException e = new RuntimeException("O voto deve estar vinculado a um cpf válido.");
            log.error("Erro criando voto com valor {} para cpf {} na pauta {}.", votoRequest.getValor(), votoRequest.getCpfAssociado(), votoRequest.getPauta(), e);
            throw e;
        }
    }

    private VotoDocument buildVoto(VotoRequest votoRequest) {
        return VotoDocument.builder()
                .valor(votoRequest.getValor())
                .pautaId(votoRequest.getPauta())
                .cpfAssociado(votoRequest.getCpfAssociado())
                .build();
    }

    private SessaoVotoDocument buildSessaoVoto(VotoRequest votoRequest) {
        return SessaoVotoDocument.builder()
                .valor(votoRequest.getValor())
                .pautaId(votoRequest.getPauta())
                .cpfAssociado(votoRequest.getCpfAssociado())
                .build();
    }

    public Flux<VotoDocument> listar() {
        return votoRepository.findAll();
    }

    public Mono<Void> excluir() {
        log.info("Excluindo todos os votos.");
        return votoRepository.deleteAll();
    }
}

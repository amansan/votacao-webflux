package br.com.sicredi.desafio.votacao.controller;

import com.sicredi.desafio.votacao.document.SessaoVotoDocument;
import com.sicredi.desafio.votacao.document.VotoDocument;
import com.sicredi.desafio.votacao.request.VotoRequest;
import com.sicredi.desafio.votacao.service.SessaoVotoService;
import com.sicredi.desafio.votacao.service.VotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/votacao")
public class VotacaoController {

    private final SessaoVotoService sessaoVotoService;

    private final VotoService votoService;

    private String pauta;

    private List<String> cpfVotantes;

    @GetMapping(value = "/sessao/nova", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @ResponseBody
    Flux<SessaoVotoDocument> novaSessao(@RequestParam(value = "duracao", required = false) Integer duracao,
                                        @RequestParam(value = "pauta") String pauta) {
        this.pauta = pauta;
        cpfVotantes = new ArrayList<>();

        return sessaoVotoService.novaSessao(duracao, pauta).doOnComplete(this::finalizarSessao);
    }

    private void finalizarSessao() {
        this.pauta = null;
        this.cpfVotantes = null;
    }

    @GetMapping("/sessao")
    public Flux<SessaoVotoDocument> listarSessao(){
        return sessaoVotoService.listar();
    }

    @PostMapping("/voto")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<VotoDocument> incluir(@RequestBody @Valid VotoRequest votoRequest){
        Mono<VotoDocument> response = votoService.incluir(votoRequest, this.pauta, this.cpfVotantes);

        if(nonNull(cpfVotantes)){
            cpfVotantes.add(votoRequest.getCpfAssociado());
        }

        return response;
    }

    @GetMapping("/voto")
    @ResponseStatus(value = HttpStatus.OK)
    public Flux<VotoDocument> listarVotos(){
        return votoService.listar();
    }

    @DeleteMapping("/voto")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<Void> excluir(){
        return votoService.excluir();
    }
}

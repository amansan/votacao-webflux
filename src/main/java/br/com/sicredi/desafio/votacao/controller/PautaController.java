package br.com.sicredi.desafio.votacao.controller;

import br.com.sicredi.desafio.votacao.document.PautaDocument;
import br.com.sicredi.desafio.votacao.service.PautaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/pauta")
@AllArgsConstructor
public class PautaController {

    private PautaService pautaService;

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<PautaDocument> incluirPauta(@NonNull @RequestParam(value = "assunto") String assunto){
        return pautaService.incluir(assunto);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public Flux<PautaDocument> listar(){
        return pautaService.listar();
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<Void> excluir(){
        return pautaService.excluir();
    }

}

package br.com.sicredi.desafio.votacao.controller;

import com.sicredi.desafio.votacao.document.AssociadoDocument;
import com.sicredi.desafio.votacao.request.AssociadoRequest;
import com.sicredi.desafio.votacao.service.AssociadoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/associado")
@AllArgsConstructor
public class AssociadoController {

    private AssociadoService associadoService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<AssociadoDocument> incluir(@Valid @RequestBody AssociadoRequest request){
        return associadoService.incluir(request);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public Flux<AssociadoDocument> listar(){
        return associadoService.listar();
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<Void> excluir(){
        return associadoService.excluir();
    }

}

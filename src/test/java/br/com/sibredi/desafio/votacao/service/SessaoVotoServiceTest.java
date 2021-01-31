package br.com.sibredi.desafio.votacao.service;

import br.com.sicredi.desafio.votacao.document.PautaDocument;
import br.com.sicredi.desafio.votacao.document.SessaoVotoDocument;
import br.com.sicredi.desafio.votacao.repository.PautaRepositoryNonReactive;
import br.com.sicredi.desafio.votacao.repository.SessaoVotoRepository;
import br.com.sicredi.desafio.votacao.service.SessaoVotoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class SessaoVotoServiceTest {

    @InjectMocks
    private SessaoVotoService sessaoVotoService;

    @Mock
    private PautaRepositoryNonReactive pautaRepositoryNonReactive;

    @Mock
    private SessaoVotoRepository sessaoVotoRepository;

    @Mock
    private MongoOperations mongoOperations;

    @Test(expected = RuntimeException.class)
    public void pautaInvalidaTeste(){
        when(pautaRepositoryNonReactive.findById(any())).thenReturn(Optional.empty());

        sessaoVotoService.novaSessao(1, "test");

        verify(pautaRepositoryNonReactive).findById(any());
        verify(sessaoVotoRepository, times(0)).save(any());
    }

    @Test
    public void sucessoTeste(){

        PautaDocument pautaDocument = PautaDocument.builder()
                .totalVotosNao(1)
                .totalVotosSim(1)
                .build();

        Optional<PautaDocument> optional = Optional.of(pautaDocument);
        Mockito.<Optional<PautaDocument>>when(pautaRepositoryNonReactive.findById(any())).thenReturn(optional);

        when(sessaoVotoRepository.save(any())).thenReturn(Mono.just(new SessaoVotoDocument()));
        when(sessaoVotoRepository.findAllBy()).thenReturn(Flux.empty());

        sessaoVotoService.novaSessao(1, "test");

        verify(sessaoVotoRepository).findAllBy();
        Assert.assertEquals(pautaDocument.getTotalVotosNao().intValue(), 0);
        Assert.assertEquals(pautaDocument.getTotalVotosSim().intValue(), 0);
    }

    @Test
    public void listarTeste(){
        sessaoVotoService.listar();

        verify(sessaoVotoRepository).findAll();
    }

}

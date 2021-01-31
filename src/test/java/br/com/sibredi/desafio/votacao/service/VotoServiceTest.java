package br.com.sibredi.desafio.votacao.service;

import br.com.sicredi.desafio.votacao.document.AssociadoDocument;
import br.com.sicredi.desafio.votacao.document.PautaDocument;
import br.com.sicredi.desafio.votacao.document.SessaoVotoDocument;
import br.com.sicredi.desafio.votacao.repository.AssociadoRepositoryNonReactive;
import br.com.sicredi.desafio.votacao.repository.PautaRepositoryNonReactive;
import br.com.sicredi.desafio.votacao.repository.SessaoVotoRepository;
import br.com.sicredi.desafio.votacao.repository.VotoRepository;
import br.com.sicredi.desafio.votacao.request.VotoRequest;
import br.com.sicredi.desafio.votacao.service.VotoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static br.com.sicredi.desafio.votacao.Constantes.VOTO_NAO;
import static br.com.sicredi.desafio.votacao.Constantes.VOTO_SIM;
import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class VotoServiceTest {

    @InjectMocks
    private VotoService votoService;

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private PautaRepositoryNonReactive pautaRepositoryNonReactive;

    @Mock
    private SessaoVotoRepository sessaoVotoRepository;

    @Mock
    private AssociadoRepositoryNonReactive associadoRepositoryNonReactive;

    @Test
    public void incluirComSucessoTeste(){

        VotoRequest votoRequest = VotoRequest.builder()
                .cpfAssociado("12345678912")
                .pauta("teste")
                .valor(VOTO_SIM)
                .build();

        Optional<AssociadoDocument> optionalAssociado = Optional.of(AssociadoDocument.builder()
                .cpf("12345678912").build());

        Optional<PautaDocument> optionalPauta = Optional.of(PautaDocument.builder()
                .totalVotosSim(0)
                .totalVotosNao(0)
                .build());

        Mockito.<Optional<AssociadoDocument>>when(associadoRepositoryNonReactive.findByCpf(any())).thenReturn(optionalAssociado);
        Mockito.<Optional<PautaDocument>>when(pautaRepositoryNonReactive.findById(any())).thenReturn(optionalPauta);
        when(sessaoVotoRepository.save((any()))).thenReturn(Mono.just(new SessaoVotoDocument()));

        votoService.incluir(votoRequest, "teste", null);

        verify(votoRepository).save(any());
    }

    @Test
    public void contagemDeVotosTeste(){

        VotoRequest votoRequestSim = VotoRequest.builder()
                .cpfAssociado("12345678912")
                .pauta("teste")
                .valor(VOTO_SIM)
                .build();

        VotoRequest votoRequestNao = VotoRequest.builder()
                .cpfAssociado("12345678912")
                .pauta("teste")
                .valor(VOTO_NAO)
                .build();

        Optional<AssociadoDocument> optionalAssociado = Optional.of(AssociadoDocument.builder()
                .cpf("12345678912").build());

        PautaDocument pauta = PautaDocument.builder()
                .totalVotosSim(0)
                .totalVotosNao(0)
                .build();
        Optional<PautaDocument> optionalPauta = Optional.of(pauta);

        Mockito.<Optional<AssociadoDocument>>when(associadoRepositoryNonReactive.findByCpf(any())).thenReturn(optionalAssociado);
        Mockito.<Optional<PautaDocument>>when(pautaRepositoryNonReactive.findById(any())).thenReturn(optionalPauta);
        when(sessaoVotoRepository.save((any()))).thenReturn(Mono.just(new SessaoVotoDocument()));

        votoService.incluir(votoRequestSim, "teste", null);
        votoService.incluir(votoRequestNao, "teste", null);
        votoService.incluir(votoRequestNao, "teste", null);

        verify(votoRepository, times(3)).save(any());
        Assert.assertEquals(pauta.getTotalVotosSim().intValue(), 1);
        Assert.assertEquals(pauta.getTotalVotosNao().intValue(), 2);
    }

    @Test(expected = RuntimeException.class)
    public void votoInvalidoTeste(){

        VotoRequest votoRequest = VotoRequest.builder()
                .valor("VOTO INVALIDO")
                .build();

        votoService.incluir(votoRequest, "teste", null);

        verify(votoRepository, times(0)).save(any());
    }

    @Test(expected = RuntimeException.class)
    public void votoDuplicadoTeste(){

        String cpf = "12345678912";

        VotoRequest votoRequest = VotoRequest.builder()
                .valor(VOTO_SIM)
                .cpfAssociado(cpf)
                .build();

        votoService.incluir(votoRequest, "teste", asList(cpf));

        verify(votoRepository, times(0)).save(any());
    }

    @Test(expected = RuntimeException.class)
    public void PautaInvalidaTeste(){

        VotoRequest votoRequest = VotoRequest.builder()
                .valor(VOTO_SIM)
                .pauta("PAUTA INVALIDA")
                .build();

        votoService.incluir(votoRequest, "teste", null);

        verify(votoRepository, times(0)).save(any());
    }

    @Test(expected = RuntimeException.class)
    public void cpfNaoCadastradoTeste(){

        VotoRequest votoRequest = VotoRequest.builder()
                .cpfAssociado("12345678912")
                .pauta("teste")
                .valor(VOTO_SIM)
                .build();

        Mockito.<Optional<AssociadoDocument>>when(associadoRepositoryNonReactive.findByCpf(any())).thenReturn(Optional.empty());

        votoService.incluir(votoRequest, "teste", null);

        verify(votoRepository).save(any());
    }

    @Test
    public void listarTeste(){
        votoService.listar();

        verify(votoRepository).findAll();
    }

    @Test
    public void excluirTeste(){
        votoService.excluir();

        verify(votoRepository).deleteAll();
    }
}

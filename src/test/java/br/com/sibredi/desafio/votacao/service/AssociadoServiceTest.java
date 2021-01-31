package br.com.sibredi.desafio.votacao.service;

import br.com.sicredi.desafio.votacao.repository.AssociadoRepository;
import br.com.sicredi.desafio.votacao.request.AssociadoRequest;
import br.com.sicredi.desafio.votacao.service.AssociadoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class AssociadoServiceTest {

    @InjectMocks
    private AssociadoService associadoService;

    @Mock
    private AssociadoRepository associadoRepository;

    @Test
    public void incluirTeste(){
        associadoService.incluir(AssociadoRequest.builder().build());
        verify(associadoRepository).save(any());
    }

    @Test
    public void listarTeste(){
        associadoService.listar();
        verify(associadoRepository).findAll();
    }

    @Test
    public void excluirTeste(){
        associadoService.excluir();
        verify(associadoRepository).deleteAll();
    }

    @Test
    public void findByCpfTeste(){
        associadoService.findByCpf("");
        verify(associadoRepository).findByCpf(any());
    }
}

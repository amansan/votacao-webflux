package br.com.sibredi.desafio.votacao.service;

import br.com.sicredi.desafio.votacao.repository.PautaRepository;
import br.com.sicredi.desafio.votacao.service.PautaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class PautaServiceTest {

    @InjectMocks
    private PautaService pautaService;

    @Mock
    private PautaRepository pautaRepository;

    @Test
    public void incluirTeste(){
        pautaService.incluir("");
        verify(pautaRepository).save(any());
    }

    @Test
    public void listarTeste(){
        pautaService.listar();
        verify(pautaRepository).findAll();
    }

    @Test
    public void excluirTeste(){
        pautaService.excluir();
        verify(pautaRepository).deleteAll();
    }
}

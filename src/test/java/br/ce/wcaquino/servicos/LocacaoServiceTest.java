package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testeLocacao() throws Exception {
		//Cenário
		LocacaoService service = new LocacaoService();
		Filme filme = new Filme("A volta dos que não foram", 3, 8.0);
		
		Usuario user = new Usuario("Valdeci");
		//FIM CENÁRIO
		
		//Ação
		Locacao locacao = service.alugarFilme(user, filme);
		//FIM AÇÃO
		
		//Verificação
		error.checkThat(locacao.getValor(), is(equalTo(8.0)));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
		//FIM VERIFICAÇÃO
	}
	
	@Test(expected = FilmeSemEstoqueException.class)
	public void testeLocacaoFilmeSemEstoque() throws Exception {
		
		//Cenário
		LocacaoService service = new LocacaoService();
		Filme filme = new Filme("A volta dos que não foram", 0, 8.0);
		
		Usuario user = new Usuario("Valdeci");
		//FIM CENÁRIO
		
		//Ação
		service.alugarFilme(user, filme);
		//FIM AÇÃO
	}
	
	@Test
	public void testeLocacaoFilmeSemEstoque2(){
		
		//Cenário
		LocacaoService service = new LocacaoService();
		Filme filme = new Filme("A volta dos que não foram", 0, 8.0);
		
		Usuario user = new Usuario("Valdeci");
		//FIM CENÁRIO
		
		//Ação
		try {
			service.alugarFilme(user, filme);
			Assert.fail("Deveria ter lançado uma exceção");
		} catch (Exception e) {
			assertThat(e.getMessage(), is("Filme sem estoque"));
		}
		//FIM AÇÃO
		
	}
	
	@Test
	public void testeLocacaoFilmeSemEstoque3() throws Exception {
		
		//Cenário
		LocacaoService service = new LocacaoService();
		Filme filme = new Filme("A volta dos que não foram", 0, 8.0);
		
		Usuario user = new Usuario("Valdeci");
		
		exception.expect(Exception.class);
		exception.expectMessage("Filme sem estoque");
		//FIM CENÁRIO
		
		//Ação
		service.alugarFilme(user, filme);
		
		
		//FIM AÇÃO
	}
//	
//	@Test
//	public void testeLocacaoFilmeSemEstoque3() throws Exception {
//		
//		//Cenário
//		Filme filme = new Filme();
//		filme.setNome("A volta dos que não foram");
//		filme.setEstoque(0);
//		filme.setPrecoLocacao(8.0);
//		
//		Usuario user = new Usuario();
//		user.setNome("Valdeci");
//		
//		LocacaoService service = new LocacaoService();
//		//FIM CENÁRIO
//		
//		//Ação
//		exception.expect(Exception.class);
//		exception.expectMessage("Filme sem estoqu");
//		//FIM AÇÃO
//	}
}


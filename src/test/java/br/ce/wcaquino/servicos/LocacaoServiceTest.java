package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {
	
	private LocacaoService service;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup() {
		System.out.println("Before");
		service = new LocacaoService();
	}
	
	@After
	public void tearDown() {
		System.out.println("After");
	}
	
	@BeforeClass
	public static void setupClass() {
		System.out.println("Before Class");
	}
	
	@AfterClass
	public static void tearDownClass() {
		System.out.println("After Class");
	}
	
	
	@Test
	public void testeLocacao() throws Exception {
		//Cenário
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
		Filme filme = new Filme("A volta dos que não foram", 0, 8.0);
		Usuario user = new Usuario("Valdeci");
		//FIM CENÁRIO
		
		//Ação
		service.alugarFilme(user, filme);
		//FIM AÇÃO
	}
	
	@Test
	public void testLocacaoUsuarioVazio() throws FilmeSemEstoqueException {
		//Cenário
		Filme filme = new Filme("A volta dos que não foram", 2, 8.0);
		
		//Ação
		try {
			service.alugarFilme(null, filme);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario vazio"));
		}
		
		System.out.println("Forma robusta");
	}
	
	@Test
	public void locacaoFilmeVazio() throws FilmeSemEstoqueException, LocadoraException {
		//Cenário
		Usuario user = new Usuario("Valdeci");
		//FIM CENÁRIO
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		//Ação
		service.alugarFilme(user, null);
		
		System.out.println("Forma Nova");
	}
}


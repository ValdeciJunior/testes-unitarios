package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
		service = new LocacaoService();
	}
	
	
	@Test
	public void deveAlugarFilme() throws Exception {
		//Cenário
		Filme filme = new Filme("A volta dos que não foram", 3, 8.0);
		Filme filme2 = new Filme("As tranças do rei careca", 8, 5.0);
		Usuario user = new Usuario("Valdeci");
		//FIM CENÁRIO
		
		//Ação
		Locacao locacao = service.alugarFilme(user, Arrays.asList(filme, filme2));
		//FIM AÇÃO
		
		//Verificação
		error.checkThat(locacao.getValor(), is(equalTo(13.0)));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
		//FIM VERIFICAÇÃO
	}
	
	@Test(expected = FilmeSemEstoqueException.class)
	public void naoDeveAlugarFilmeSemEstoque() throws Exception {
		
		//Cenário
		Filme filme = new Filme("A volta dos que não foram", 0, 8.0);
		Filme filme2 = new Filme("As tranças do rei careca", 8, 5.0);
		Usuario user = new Usuario("Valdeci");
		//FIM CENÁRIO
		
		//Ação
		service.alugarFilme(user, Arrays.asList(filme, filme2));
		//FIM AÇÃO
	}
	
	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		//Cenário
		Filme filme = new Filme("A volta dos que não foram", 2, 8.0);
		Filme filme2 = new Filme("As tranças do rei careca", 8, 5.0);
		
		//Ação
		try {
			service.alugarFilme(null, Arrays.asList(filme, filme2));
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario vazio"));
		}
		
		System.out.println("Forma robusta");
	}
	
	@Test
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {
		//Cenário
		Usuario user = new Usuario("Valdeci");
		//FIM CENÁRIO
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		//Ação
		service.alugarFilme(user, null);
		
		System.out.println("Forma Nova");
	}
	
	@Test
	public void devePagar75PctNoFilme3() throws FilmeSemEstoqueException, LocadoraException {
		//Cenário
		Usuario usuario = new Usuario();
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0), new Filme("Filme 3", 2, 4.0));
		//Ação
		Locacao resultado = service.alugarFilme(usuario, filmes);
		//Verificação
		Assert.assertThat(resultado.getValor(), is(11.0));
		
	}
	
	@Test
	public void devePagar50PctNoFilme4() throws FilmeSemEstoqueException, LocadoraException {
		//Cenário
		Usuario usuario = new Usuario();
		List<Filme> filmes = Arrays.asList(
				new Filme("Filme 1", 2, 4.0), 
				new Filme("Filme 2", 2, 4.0), 
				new Filme("Filme 3", 2, 4.0),
				new Filme("Filme 4", 2, 4.0));
		//Ação
		Locacao resultado = service.alugarFilme(usuario, filmes);
		//Verificação
		Assert.assertThat(resultado.getValor(), is(13.0));
		
	}
	@Test
	public void devePagar25PctNoFilme5() throws FilmeSemEstoqueException, LocadoraException {
		//Cenário
		Usuario usuario = new Usuario();
		List<Filme> filmes = Arrays.asList(
				new Filme("Filme 1", 2, 4.0), 
				new Filme("Filme 2", 2, 4.0), 
				new Filme("Filme 3", 2, 4.0),
				new Filme("Filme 4", 2, 4.0),
				new Filme("Filme 5", 2, 4.0));
		//Ação
		Locacao resultado = service.alugarFilme(usuario, filmes);
		//Verificação
		Assert.assertThat(resultado.getValor(), is(14.0));
		
	}
	@Test
	public void devePagar0PctNoFilme5() throws FilmeSemEstoqueException, LocadoraException {
		//Cenário
		Usuario usuario = new Usuario();
		List<Filme> filmes = Arrays.asList(
				new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0), 
				new Filme("Filme 3", 2, 4.0), new Filme("Filme 4", 2, 4.0),
				new Filme("Filme 5", 2, 4.0), new Filme("Filme 6", 2, 4.0));
		//Ação
		Locacao resultado = service.alugarFilme(usuario, filmes);
		//Verificação
		Assert.assertThat(resultado.getValor(), is(14.0));
		
	}
	
	@Test
	public void deveDevolverFilmeNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
		//Cenário
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme", 18, 3.0));
		
		//Ação
		Locacao retorno = service.alugarFilme(usuario, filmes);
		
		//Verificação
		boolean ehSegunda = DataUtils.verificarDiaSemana(retorno.getDataLocacao(), Calendar.MONDAY);
		Assert.assertTrue(ehSegunda);
	}
}


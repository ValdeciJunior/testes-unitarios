package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;

public class LocacaoServiceTest {
	@Test
		public void teste() {
			//Cenário
			Filme filme = new Filme();
			filme.setNome("A volta dos que não foram");
			filme.setEstoque(15);
			filme.setPrecoLocacao(8.0);
			
			Usuario user = new Usuario();
			user.setNome("Valdeci");
			
			LocacaoService service = new LocacaoService();
			//FIM CENÁRIO
			
			//Ação
			Locacao aluguel = service.alugarFilme(user, filme);
			//FIM AÇÃO
			
			//Verificação
			System.out.println(aluguel);
			Assert.assertTrue(aluguel.getValor() == 8.0);
			Assert.assertTrue(aluguel != null);
			//FIM VERIFICAÇÃO
		}
}


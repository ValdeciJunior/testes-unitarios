import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Usuario;

public class AssertTest {
	
	@Test
	public void test() {
		Assert.assertTrue(true);
		Assert.assertFalse(false);
		
		Assert.assertEquals("Erro na coparação",1, 1);
		Assert.assertEquals(0.51234, 0.512, 0.001);
		Assert.assertEquals(Math.PI, 3.14, 0.01);
		
		int i = 5;
		Integer i2 = 5;
		Assert.assertEquals(Integer.valueOf(i), i2);
		
		Assert.assertEquals("bola", "bola");
		Assert.assertNotEquals("bola", "café");
		Assert.assertEquals("bola", "Bola".toLowerCase());
		Assert.assertTrue("bola".equalsIgnoreCase("bola"));
		
		Usuario u1 = new Usuario("U1");
		Usuario u2 = new Usuario("U1");
		Usuario u3 = null;
		Assert.assertEquals(u1, u2);
		Assert.assertTrue(u1.equals(u2));
		
		Assert.assertSame(u1, u1);
		Assert.assertNotSame(u2, u1);
		
		Assert.assertNull(u3);
		
	}

}

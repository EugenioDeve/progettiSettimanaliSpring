package it.eugenio.GestioneIncendi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import it.eugenio.GestioneIncendi.models.CentroControllo;
import it.eugenio.GestioneIncendi.models.Sonda;

@SpringBootTest
class GestioneIncendiApplicationTests {

	CentroControllo cc = new CentroControllo();
	
	Sonda s1 = new Sonda(1,11.11,22.22);
	
	@Test
	void notNull() {
		s1.registerObserver(cc);
		s1.rilevaFumo(6);
		assertNotNull(cc.getSonda());
		assertNotNull(cc.getSonda().getId());
		assertNotNull(cc.getSonda().getLatitudine());
		assertNotNull(cc.getSonda().getLongitudine());
		assertNotNull(cc.getSonda().getLivelloFumo());
		assertNotNull(cc.getSonda().getLivelloCritico());		
	}
	
	@Test
	void correctValues() {
		s1.registerObserver(cc);
		s1.rilevaFumo(6);
		assertEquals(1, cc.getSonda().getId());
		assertEquals(22.22, cc.getSonda().getLatitudine());
		assertEquals(11.11, cc.getSonda().getLongitudine());
		assertEquals(5, cc.getSonda().getLivelloCritico());
	}
	

}

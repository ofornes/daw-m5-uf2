/*
 * This file is part of "tdd".
 * 
 * "tdd" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "tdd" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with calendar.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2022 Octavi Fornés
 */
package cat.albirar.daw.tdd.test.comptes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import javax.validation.ValidationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cat.albirar.daw.tdd.comptes.CompteBean;
import cat.albirar.daw.tdd.comptes.IServeiComptes;
import cat.albirar.daw.tdd.comptes.LimitDiariTransferenciesExceditException;

/**
 * Provatures de funcionalitat de transferencies del servei {@link IServeiComptes}.
 * @author Octavi Forn&eacute;s <mailto:ofornes@albirar.cat[]>
 * @since 0.0.1
 */
public class ServeiComptesTransferenciesTest extends AbstractServeiComptesTest {
	private CompteBean c1;
	private CompteBean c2;

	@BeforeEach public void preparar() {
		c1 = serveiComptes.crearCompte();
		c2 = serveiComptes.crearCompte();
	}
	/**
	 * Si fem una transferència de 100 des d'un compte amb 500 a un amb 50, en el primer compte el saldo serà de 400 i en la segona serà de 150.
	 */
	@Test public void siFemUnaTransferenciaDe100DesDUnCompteAmb500AUnAmb50_EnElPrimerCompteElSaldoSeraDe400IEnElSegonaSeraDe150() {
		serveiComptes.ingressar(c1.getIdCompte(), BigDecimal.valueOf(500D));
		serveiComptes.ingressar(c2.getIdCompte(), BigDecimal.valueOf(50D));
		
		assertEquals(BigDecimal.valueOf(150D), serveiComptes.transferir(c1.getIdCompte(), c2.getIdCompte(), BigDecimal.valueOf(100D)).getSaldoEuros());
		
		assertEquals(BigDecimal.valueOf(400D), serveiComptes.saldo(c1.getIdCompte()));
		assertEquals(BigDecimal.valueOf(150D), serveiComptes.saldo(c2.getIdCompte()));
	}
	/**
	 * Si fem una transferència de -100 des d'un compte amb 500 a un amb 50, els saldos es queden amb 500 i 50 respectivament.
	 */
	@Test public void siFemUnaTransferenciaDe100NegatiusDesDUnCompteAmb500AUnAmb50_ElsSaldosEsQuedenAmb500I50Respectivament() {
		serveiComptes.ingressar(c1.getIdCompte(), BigDecimal.valueOf(500D));
		serveiComptes.ingressar(c2.getIdCompte(), BigDecimal.valueOf(50D));
		
		assertThrows(ValidationException.class, () -> serveiComptes.transferir(c1.getIdCompte(), c2.getIdCompte(), BigDecimal.valueOf(-100D)));

		assertEquals(BigDecimal.valueOf(500D), serveiComptes.saldo(c1.getIdCompte()));
		assertEquals(BigDecimal.valueOf(50D), serveiComptes.saldo(c2.getIdCompte()));
	}
	/**
	 * Si fem una transferència de 3000 des d'un compte amb 3500 a un compte amb 50, en el primer compte el saldo quedarà en 500 i en el segon en 3050.
	 */
	@Test public void siFemUnaTransferenciaDe3000DesDUnCompteAmb3500AUnCompteAmb50_enElPrimerCompteElSaldoQuedaraEn500IEnElSegonEn3050() {
		serveiComptes.ingressar(c1.getIdCompte(), BigDecimal.valueOf(3500D));
		serveiComptes.ingressar(c2.getIdCompte(), BigDecimal.valueOf(50D));
		
		assertEquals(BigDecimal.valueOf(3050D), serveiComptes.transferir(c1.getIdCompte(), c2.getIdCompte(), BigDecimal.valueOf(3000D)).getSaldoEuros());
		
		assertEquals(BigDecimal.valueOf(500D), serveiComptes.saldo(c1.getIdCompte()));
		assertEquals(BigDecimal.valueOf(3050D), serveiComptes.saldo(c2.getIdCompte()));
	}
	/**
	 * Si fem una transferència de 3000.01 des d'un compte amb 3500 a un compte amb 50, en el primer compte es quedaran 3500 i en el segon 50.
	 */
	@Test public void siFemUnaTransferenciaDe3000Punt01DesDUnCompteAmb3500AUnCompteAmb50_enElPrimerCompteEsQuedaran3500IEnElSegon50() {
		serveiComptes.ingressar(c1.getIdCompte(), BigDecimal.valueOf(3500D));
		serveiComptes.ingressar(c2.getIdCompte(), BigDecimal.valueOf(50D));
		
		assertThrows(LimitDiariTransferenciesExceditException.class, () -> serveiComptes.transferir(c1.getIdCompte(), c2.getIdCompte(), BigDecimal.valueOf(3000.01D)));
		
		assertEquals(BigDecimal.valueOf(3500D), serveiComptes.saldo(c1.getIdCompte()));
		assertEquals(BigDecimal.valueOf(50D), serveiComptes.saldo(c2.getIdCompte()));
	}
	/**
	 * Si fem una transferència de 2000 des d'un compte amb 3500 a un compte amb 50 i just després una altra de 1200, en el primer compte el saldo quedarà en 1500 i en la segona en 2050.
	 */
	@Test public void siFemUnaTransferenciaDe2000DesDUnCompteAmb3500AUnCompteAmb50IJustDespresUnaAltraDe1200_enElPrimerCompteElSaldoQuedaraEn1500IEnLaSegonaEn2050() {
		serveiComptes.ingressar(c1.getIdCompte(), BigDecimal.valueOf(3500D));
		serveiComptes.ingressar(c2.getIdCompte(), BigDecimal.valueOf(50D));
		
		serveiComptes.transferir(c1.getIdCompte(), c2.getIdCompte(), BigDecimal.valueOf(2000D));
		assertThrows(LimitDiariTransferenciesExceditException.class, () -> serveiComptes.transferir(c1.getIdCompte(), c2.getIdCompte(), BigDecimal.valueOf(1200D)));
		
		assertEquals(BigDecimal.valueOf(1500D), serveiComptes.saldo(c1.getIdCompte()));
		assertEquals(BigDecimal.valueOf(2050D), serveiComptes.saldo(c2.getIdCompte()));
	}
}

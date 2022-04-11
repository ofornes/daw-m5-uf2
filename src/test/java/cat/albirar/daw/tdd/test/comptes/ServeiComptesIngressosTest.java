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

import org.junit.jupiter.api.Test;

import cat.albirar.daw.tdd.comptes.IServeiComptes;

/**
 * Provatures de funcionalitats d'ingressos del servei {@link IServeiComptes}.
 * @author Octavi Forn&eacute;s <mailto:ofornes@albirar.cat[]>
 * @since 0.0.1
 */
public class ServeiComptesIngressosTest extends AbstractServeiComptesTest {
	
	/**
	 * Quan ingressem 100 en un compte buit, el saldo és 100.
	 */
	@Test public void quanIngressem100EnUnCompteBuit_ElSaldoEs100() {
		String id;
		
		id = serveiComptes.crearCompte();
		assertEquals(BigDecimal.valueOf(100D), serveiComptes.ingressar(id, BigDecimal.valueOf(100D)));
	}
	/**
	 * Quan ingressem 100 en un compte buit, el saldo és 100.
	 */
	@Test public void quanIngressem3000EnUnCompteBuit_ElSaldoEs_100 (){
		String id;
		
		id = serveiComptes.crearCompte();
		assertEquals(BigDecimal.valueOf(3000D), serveiComptes.ingressar(id, BigDecimal.valueOf(3000D)));
	}
	
	/**
	 * Quan ingressem 3000 en un compte amb 100, el saldo és de 3100.
	 */
	@Test public void quanIngressem3000EnUnCompteAmb100_ElSaldoEsDe3100() {
		String id;
		
		id = serveiComptes.crearCompte();
		serveiComptes.ingressar(id, BigDecimal.valueOf(100D));
		assertEquals(BigDecimal.valueOf(3100D), serveiComptes.ingressar(id, BigDecimal.valueOf(3000D)));
	}
	/**
	 * Quan ingressem -100 en un compte buit, es saldo segueix sent 0.
	 */
	@Test public void quanIngressem100NegatiusEnUnCompteBuit_ElSaldoSegueixSent0() {
		String id;
		
		id = serveiComptes.crearCompte();
		assertThrows(ValidationException.class, () -> serveiComptes.ingressar(id, BigDecimal.valueOf(-100D)));
		assertEquals(BigDecimal.ZERO, serveiComptes.saldo(id));
	}
	/**
	 * Quan ingressem 100.45 en un compte buit, el saldo és de 100.45.
	 */
	@Test public void quanIngressem100Punt45EnUnCompteBuit_ElSaldoEsDe100Punt45() {
		String id;
		
		id = serveiComptes.crearCompte();
		assertEquals(BigDecimal.valueOf(100.45D), serveiComptes.ingressar(id, BigDecimal.valueOf(100.45D)));
	}
	/**
	 * Si ingressem 100.457 en un compte buit, el saldo és zero
	 */
	@Test public void siIngressem100Punt457EnUnCompteBuit_ElSaldoEsZero() {
		String id;
		
		id = serveiComptes.crearCompte();
		assertThrows(ValidationException.class, () -> serveiComptes.ingressar(id, BigDecimal.valueOf(100.457D)));
		assertEquals(BigDecimal.ZERO, serveiComptes.saldo(id));
	}
	/**
	 * Si ingressem 6000.00 en un compte buit, el saldo és de 6000.00.
	 */
	@Test public void siIngressem6000Punt00EnUnCompteBuit_ElSaldoEs6000Punt00() {
		String id;
		
		id = serveiComptes.crearCompte();
		assertEquals(BigDecimal.valueOf(6000.00D), serveiComptes.ingressar(id, BigDecimal.valueOf(6000.00D)));
	}
	/**
	 * Si ingressem 6000.01 en un compte buit, el saldo és zero.
	 */
	@Test public void siIngressem6000Punt01EnUnCompteBuit_ElSaldoEsZero() {
		String id;
		
		id = serveiComptes.crearCompte();
		assertThrows(ValidationException.class, () -> serveiComptes.ingressar(id, BigDecimal.valueOf(6000.01D)));
		assertEquals(BigDecimal.ZERO, serveiComptes.saldo(id));
	}

}

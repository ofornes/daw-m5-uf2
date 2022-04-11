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
import cat.albirar.daw.tdd.comptes.SaldoInsuficientException;

/**
 * Provatures de funcionalitats de retirades del servei {@link IServeiComptes}.
 * 
 * @author Octavi Forn&eacute;s <mailto:ofornes@albirar.cat[]>
 * @since 0.0.1
 */
public class ServeiComptesRetiradesTest extends AbstractServeiComptesTest {
	
	/**
	 * Quan retirem 100 en un compte amb 500, el saldo és 400.
	 */
	@Test public void quanRetirem100EnUnCompteAmb500_ElSaldoEs400() {
		String id;
		
		id = serveiComptes.crearCompte();
		serveiComptes.ingressar(id, BigDecimal.valueOf(500D));
		assertEquals(BigDecimal.valueOf(400D), serveiComptes.retirar(id, BigDecimal.valueOf(100D)));
	}
	/**
	 * Si retirem 500 en un compte amb 200, no ocorre res i el saldo continua sent 200
	 */
	@Test public void siRetirem500EnUnCompteAmb200_NoOcorreResIElSaldoSontinuaSent200() {
		String id;
		
		id = serveiComptes.crearCompte();
		serveiComptes.ingressar(id, BigDecimal.valueOf(200D));
		assertThrows(SaldoInsuficientException.class, () -> serveiComptes.retirar(id, BigDecimal.valueOf(500D)));
		assertEquals(BigDecimal.valueOf(200D), serveiComptes.saldo(id));
	}
	/**
	 * Si retirem -100 en un compte amb 500, no ocorre res i el saldo continua sent 500
	 */
	@Test public void siRetirem100NegatiusEnUnCompteAmb500_NoOcorreResIElSaldoSontinuaSent500() {
		String id;
		
		id = serveiComptes.crearCompte();
		serveiComptes.ingressar(id, BigDecimal.valueOf(500D));
		assertThrows(ValidationException.class, () -> serveiComptes.retirar(id, BigDecimal.valueOf(-100D)));
		assertEquals(BigDecimal.valueOf(500D), serveiComptes.saldo(id));
	}
	/**
	 * Si retirem 100.45 en un compte amb 500, el saldo és 399.55 
	 */
	@Test public void siRetirem100Punt45EnUnCompteAmb500_ElSaldoEs399Punt55() {
		String id;
		
		id = serveiComptes.crearCompte();
		serveiComptes.ingressar(id, BigDecimal.valueOf(500D));
		assertEquals(BigDecimal.valueOf(399.55D), serveiComptes.retirar(id, BigDecimal.valueOf(100.45D)));
	}
	/**
	 * Si retirem 100.457 en un compte amb 500, no ocorre res i el saldo continua sent 500
	 */
	@Test public void siRetirem100Punt457EnUnCompteAmb500_NoOcorreResIElSaldoContinuaSent500() {
		String id;
		
		id = serveiComptes.crearCompte();
		serveiComptes.ingressar(id, BigDecimal.valueOf(500D));
		assertThrows(ValidationException.class, () -> serveiComptes.retirar(id, BigDecimal.valueOf(100.457D)));
		assertEquals(BigDecimal.valueOf(500D), serveiComptes.saldo(id));
	}
	/**
	 * Si retirem 6000.00 d'un compte amb 7000, el saldo és de 1000
	 */
	@Test public void siRetirem6000Punt00DUnCompteAmb7000_ElSaldoEsDe1000() {
		String id;
		
		id = serveiComptes.crearCompte();
		serveiComptes.ingressar(id, BigDecimal.valueOf(5000D));
		serveiComptes.ingressar(id, BigDecimal.valueOf(2000D));
		assertEquals(BigDecimal.valueOf(1000D), serveiComptes.retirar(id, BigDecimal.valueOf(6000.00D)));
	}
	/**
	 * Si retirem 6000.01 en un compte amb 7000, no ocurre nada i el saldo continua sent 7000
	 */
	@Test public void siRetirem6000Punt01DUnCompteAmb7000_NoOcorreResIElSaldoContinuaSent7000() {
		String id;
		
		id = serveiComptes.crearCompte();
		serveiComptes.ingressar(id, BigDecimal.valueOf(5000D));
		serveiComptes.ingressar(id, BigDecimal.valueOf(2000D));
		assertThrows(ValidationException.class, () -> serveiComptes.retirar(id, BigDecimal.valueOf(6000.01D)));
		assertEquals(BigDecimal.valueOf(7000D), serveiComptes.saldo(id));
	}

}

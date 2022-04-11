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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import cat.albirar.daw.tdd.comptes.IServeiComptes;

/**
 * Provatures de la funcionalitat de creació de comptes del servei {@link IServeiComptes}.
 * @author Octavi Forn&eacute;s <mailto:ofornes@albirar.cat[]>
 * @since 0.0.1
 */
public class ServeiComptesCreacioTest extends AbstractServeiComptesTest  {
	/**
	 * Quan creem el compte el saldo és zero.
	 */
	@Test public void quanCreemElCompte_El_Saldo_EsZero() {
		String id;
		
		id = serveiComptes.crearCompte();
		// Creació vàlid
		assertNotNull(id);
		assertTrue(StringUtils.hasText(id));
		// Saldo == 0
		assertEquals(BigDecimal.ZERO, serveiComptes.saldo(id));
	}
}

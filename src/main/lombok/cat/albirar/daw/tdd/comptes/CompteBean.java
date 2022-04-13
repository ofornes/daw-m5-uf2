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
package cat.albirar.daw.tdd.comptes;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Compte.
 * @author Octavi Forn&eacute;s <mailto:ofornes@albirar.cat[]>
 * @since 1.0.0
 */
@Getter
@SuperBuilder(toBuilder = true)
@ToString
@EqualsAndHashCode
public class CompteBean {
	/**
	 * Identificador del compte
	 * @return L'identificador
	 */
	@NotBlank
	private String idCompte;
	/**
	 * Saldo del compte en cèntims
	 * @return El saldo en cèntims
	 */
	@Positive
	int amount;
	/**
	 * Saldo del compte en euros.
	 * @return El saldo en euros
	 */
	public BigDecimal getSaldoEuros() {
		return BigDecimal.valueOf(((double)amount)/100D);
	}
}

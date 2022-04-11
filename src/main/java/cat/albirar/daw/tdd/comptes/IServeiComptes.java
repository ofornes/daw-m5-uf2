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

import javax.validation.ValidationException;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * Contracte per a gestionar comptes.
 * @author Octavi Forn&eacute;s <mailto:ofornes@albirar.cat[]>
 * @since 0.0.1
 */
public interface IServeiComptes {
	/**
	 * Crea un nou compte i retorna el codi del compte.
	 * @return El codi del compte
	 */
	public String crearCompte();
	/**
	 * Retorna el saldo del {@code compte} indicat.
	 * @param compte El compte, requerit
	 * @return El saldo
	 * @throws CompteInexistentException Si el {@code compte} indicat no existeix pas
	 */
	public BigDecimal saldo(@NotBlank String compte);
	/**
	 * Ingressa el {@code total} al compte {@code indicat}.
	 * @param compte El compte, requerit
	 * @param total El total a ingressar, sense efectes si és igual o menor que zero
	 * @return El saldo actual, desprès de l'ingrès
	 * @throws CompteInexistentException Si el {@code compte} no existeix pas.
	 * @throws ValidationException Si {@code compte} no és pas vàlid o si {@code total} és igual o menor que zero
	 */
	public BigDecimal ingressar(@NotBlank String compte, @Min(0) @Max(6000) @Digits(integer = 12, fraction = 2) BigDecimal total);
	/**
	 * Retira el {@code total} al compte {@code indicat}.
	 * @param compte El compte, requerit
	 * @param total El total a retirar, sense efectes si és igual o menor que zero
	 * @return El saldo actual, desprès de la retirada
	 * @throws CompteInexistentException Si el {@code compte} no existeix pas.
	 * @throws SaldoInsuficientException Si el saldo del {@code compte} impedeix retirar la quantitat {@code total} indicada
	 * @throws ValidationException Si {@code compte} no és pas vàlid o si {@code total} és igual o menor que zero
	 */
	public BigDecimal retirar(@NotBlank String compte, @Min(0) @Max(6000) @Digits(integer = 12, fraction = 2) BigDecimal total);
	/**
	 * Transfereix el {@code total} del {@code compteOrigen} al {@code compteDestinacio}.
	 * @param compteOrigen
	 * @param compteDestinacio
	 * @param total El total a transferir, sense efectes si és igual o menor que zero
	 * @return El saldo del compte de destinació, desprès de la transferència
	 * @throws CompteInexistentException Si el {@code compteOrigen} o el {@code compteDestinacio} no existeix pas.
	 * @throws SaldoInsuficientException Si el saldo del {@code compteOrigen} impedeix transferir la quantitat {@code total} indicada
	 * @throws LimitDiariTransferenciesExceditExcepcio Si s'excedeix el total màxim diari per a transferències
	 * @throws ValidationException Si qualsevol de {@code compteOrigen} o {@code compteDestinacio} no és pas vàlid o si {@code total} és igual o menor que zero
	 */
	public BigDecimal transferir(@NotBlank String compteOrigen, @NotBlank String compteDestinacio, @Positive @Digits(integer = 12, fraction = 2) BigDecimal total);
}

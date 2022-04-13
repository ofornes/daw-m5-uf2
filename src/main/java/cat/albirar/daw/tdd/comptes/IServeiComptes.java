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

/**
 * Contracte per a gestionar comptes.
 * @author Octavi Forn&eacute;s <mailto:ofornes@albirar.cat[]>
 * @since 0.0.1
 * @since 1.0.0
 */
public interface IServeiComptes {
	/**
	 * Crea un nou compte i retorna el codi del compte.
	 * @return El compte
	 */
	public CompteBean crearCompte();
	/**
	 * Cerca el compte associat amb l'{@code idCompte} indicat.
	 * @param idCompte L'id del compte
	 * @return El compte
	 * @throws CompteInexistentException Si no n'hi ha pas compte associat amb l'{@code idCompte} indicat
	 */
	public CompteBean cercarCompte(@NotBlank String idCompte);
	/**
	 * Retorna el saldo del compte.
	 * @param idCompte L'id del compte, requerit
	 * @return El saldo
	 * @throws CompteInexistentException Si no n'hi ha pas compte associat amb l'{@code idCompte} indicat
	 */
	public BigDecimal saldo(@NotBlank String idCompte);
	/**
	 * Ingressa el {@code total} de cèntims al compte {@code indicat}.
	 * @param idCompte El compte, requerit
	 * @param total El total de cèntims a ingressar, sense efectes si és igual o menor que zero
	 * @return El compte actualitzat
	 * @throws CompteInexistentException Si el {@code compte} no existeix pas.
	 * @throws ValidationException Si {@code compte} no és pas vàlid o si {@code total} és igual o menor que zero
	 */
	public CompteBean ingressar(@NotBlank String idCompte, @Min(0) @Max(6000) @Digits(integer = 12, fraction = 2) BigDecimal total);
	/**
	 * Retira el {@code total} de cèntims al {@code compte} indicat.
	 * @param idCompte El compte, requerit
	 * @param total El total de cèntims a retirar, sense efectes si és igual o menor que zero
	 * @return El compte actualitzat
	 * @throws CompteInexistentException Si el {@code compte} no existeix pas.
	 * @throws SaldoInsuficientException Si el saldo del {@code compte} impedeix retirar la quantitat {@code total} indicada
	 * @throws ValidationException Si {@code compte} no és pas vàlid o si {@code total} és igual o menor que zero
	 */
	public CompteBean retirar(@NotBlank String idCompte, @Min(0) @Max(6000) @Digits(integer = 12, fraction = 2) BigDecimal total);
	/**
	 * Transfereix el {@code total} de cèntims del {@code compteOrigen} al {@code compteDestinacio}.
	 * @param idCompteOrigen El compte origen
	 * @param idCompteDestinacio El compte de destinació
	 * @param total El total de cèntims a transferir, sense efectes si és igual o menor que zero
	 * @return El compte de destinació actualitzat desprès de la transferència
	 * @throws CompteInexistentException Si qualsevol de {@code compteOrigen} o {@code compteDestinacio} no existeix pas.
	 * @throws SaldoInsuficientException Si el saldo del {@code compteOrigen} impedeix transferir la quantitat {@code total} de cèntims indicada
	 * @throws LimitDiariTransferenciesExceditException Si s'excedeix el total màxim diari per a transferències
	 * @throws ValidationException Si qualsevol de {@code compteOrigen} o {@code compteDestinacio} no és pas vàlid o si {@code total} és igual o menor que zero
	 */
	public CompteBean transferir(@NotBlank String idCompteOrigen, @NotBlank String idCompteDestinacio, @Min(0) @Max(6000) @Digits(integer = 12, fraction = 2) BigDecimal total);
}

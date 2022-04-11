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

import javax.validation.ValidationException;
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
	 * @throws IllegalArgumentException Si el {@code compte} indicat no existeix pas
	 */
	public double saldo(@NotBlank String compte);
	/**
	 * Ingressa el {@code total} al compte {@code indicat}.
	 * @param compte El compte, requerit
	 * @param total El total a ingressar, sense efectes si és igual o menor que zero
	 * @return El saldo actual, desprès de l'ingrès
	 * @throws IllegalArgumentException Si el {@code compte} no existeix pas.
	 * @throws ValidationException Si {@code compte} és null o no conté almenys un caràcter no espai o si {@code total} és igual o menor que zero
	 */
	public double ingressar(@NotBlank String compte, @Positive double total);
	/**
	 * Retira el {@code total} al compte {@code indicat}.
	 * @param compte El compte, requerit
	 * @param total El total a retirar, sense efectes si és igual o menor que zero
	 * @return El saldo actual, desprès de la retirada
	 * @throws IllegalArgumentException Si el {@code compte} no existeix pas.
	 * @throws ValidationException Si {@code compte} és null o no conté almenys un caràcter no espai o si {@code total} és igual o menor que zero
	 */
	public double retirar(@NotBlank String compte, @Positive double total);
	/**
	 * Transfereix el {@code total} del {@code compteOrigen} al {@code compteDestinacio}.
	 * @param compteOrigen
	 * @param compteDestinacio
	 * @param total El total a transferir, sense efectes si és igual o menor que zero
	 * @return El saldo del compte de destinació, desprès de la transferència
	 * @throws IllegalArgumentException Si el {@code compteOrigen} o el {@code compteDestinacio} no existeix pas.
	 * @throws ValidationException Si qualsevol de {@code compteOrigen} o {@code compteDestinacio} és null o no conté almenys un caràcter no espai o si {@code total} és igual o menor que zero
	 */
	public double transferir(@NotBlank String compteOrigen, @NotBlank String compteDestinacio, @Positive double total);
}

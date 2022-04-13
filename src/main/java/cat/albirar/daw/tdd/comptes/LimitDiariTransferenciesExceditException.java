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

import cat.albirar.daw.tdd.TddException;

/**
 * Excepcio per a marcar que no es pot realitzar la transferència per que s'ha excedit el màxim de saldo a transferir en cada dia.
 * @author Octavi Forn&eacute;s <mailto:ofornes@albirar.cat[]>
 * @since 0.0.1
 */
@SuppressWarnings("serial")
public class LimitDiariTransferenciesExceditException extends TddException {
	/**
	 * Constructor únic
	 * @param idCompte L'id del compte
	 * @param maximDiari El màxim diari
	 */
	public LimitDiariTransferenciesExceditException(String idCompte, int maximDiari) {
		super(String.format("El compte '%s' no pot efectuar la transferència pel límit diari de '%d'", idCompte, maximDiari));
	}
}

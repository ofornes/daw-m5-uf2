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
 * El compte indicat no existeix pas.
 * @author Octavi Forn&eacute;s <mailto:ofornes@albirar.cat[]>
 * @since 0.0.1
 */
public class CompteInexistentException extends TddException {
	private static final long serialVersionUID = -8544436635829550798L;
	
	private String idCompte;
	/**
	 * Constructor amb identificador de compte inexistent.
	 * @param idCompte L'identificador del compte
	 */
	public CompteInexistentException(String idCompte) {
		this(idCompte, String.format("El compte \"%s\" no existeix pas!", idCompte));
	}
	/**
	 * Constructor amb identificador de compte inexistent i missatge específic.
	 * @param idCompte L'identificador del compte
	 * @param message El missatge associat
	 */
	public CompteInexistentException(String idCompte, String message) {
		super(message);
		this.idCompte = idCompte;
	}
	/**
	 * Identificador del compte inexistent.
	 * @return L'identificador
	 */
	public String getIdCompte() {
		return idCompte;
	}
}

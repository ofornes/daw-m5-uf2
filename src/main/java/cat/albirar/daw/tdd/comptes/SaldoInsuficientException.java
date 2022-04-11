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
 * Excepció per a marcar una operació amb saldo insuficient.
 * @author Octavi Forn&eacute;s <mailto:ofornes@albirar.cat[]>
 * @since 0.0.1
 */
@SuppressWarnings("serial")
public class SaldoInsuficientException extends TddException {
	/**
	 * Constructor heretat.
	 */
	public SaldoInsuficientException() {
		super();
	}
	/**
	 * Constructor heretat.
	 */
	public SaldoInsuficientException(String message) {
		super(message);
	}
	/**
	 * Constructor heretat.
	 */
	public SaldoInsuficientException(Throwable cause) {
		super(cause);
	}
	/**
	 * Constructor heretat.
	 */
	public SaldoInsuficientException(String message, Throwable cause) {
		super(message, cause);
	}
}

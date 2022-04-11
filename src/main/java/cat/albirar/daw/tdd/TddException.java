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
package cat.albirar.daw.tdd;

/**
 * Arrel de les excepcions de TDD.
 * @author Octavi Forn&eacute;s <mailto:ofornes@albirar.cat[]>
 * @since 0.0.1
 */
@SuppressWarnings("serial")
public class TddException extends RuntimeException {
	/**
	 * Constructor heretat.
	 */
	public TddException() {
		super();
	}
	/**
	 * Constructor heretat.
	 */
	public TddException(String message) {
		super(message);
	}
	/**
	 * Constructor heretat.
	 */
	public TddException(Throwable cause) {
		super(cause);
	}
	/**
	 * Constructor heretat.
	 */
	public TddException(String message, Throwable cause) {
		super(message, cause);
	}
}

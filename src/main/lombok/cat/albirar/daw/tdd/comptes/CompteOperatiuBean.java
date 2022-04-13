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

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import lombok.Builder.Default;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Compte operatiu per a sumar, restar saldo, etc.
 * @author Octavi Forn&eacute;s <mailto:ofornes@albirar.cat[]>
 * @since 1.0.0
 */
@SuperBuilder
@Valid
public class CompteOperatiuBean {
	@Getter
	private CompteBean compte;
	@Default
	private Map<LocalDate, Integer> transferitDia = Collections.synchronizedMap(new TreeMap<>());
	
	/**
	 * Afegeix la {@code quantitat} indicada al saldo.
	 * @param quantitat Els cèntims a sumar
	 * @return El saldo desprès de la suma
	 */
	public int sumarSaldo(@Min(1) int quantitat) {
		compte.amount += quantitat;
		return compte.amount;
	}
	/**
	 * Resta la {@code quantitat} indicada al saldo.
	 * @param quantitat Els cèntims a restar
	 * @return El saldo desprès de la resta
	 * @throws SaldoInsuficientException Si el saldo actual impedeix la substracció
	 */
	public int restarSaldo(@Min(1) int quantitat) {
		if(compte.amount < quantitat) {
			throw new SaldoInsuficientException();
		}
		compte.amount -= quantitat;
		return compte.amount;
	}
	/**
	 * Semblant a {@link #restarSaldo(int)}, però verifica que la operació no sobrepassi el {@code maximDiari} de transferència a data d'avui.
	 * <strong>Si execedix el màxim diari, no s'aplica la substracció</strong>
	 * @param quantitat La quantitat
	 * @param maximDiari El màxim diari permès
	 * @return El total transferit a data d'avui
	 * @throws LimitDiariTransferenciesExceditException Si s'execedeix el {@code maximDiari}
	 */
	public int transferirSaldo(@Min(1) int quantitat, @Min(1) int maximDiari) {
		LocalDate data;
		Integer totalDia;
		
		data = LocalDate.now();
		totalDia = transferitDia.get(data);
		if(totalDia == null) {
			totalDia = Integer.valueOf(0);
		}
		if((totalDia + quantitat) > maximDiari) {
			throw new LimitDiariTransferenciesExceditException(compte.getIdCompte(), maximDiari);
		}
		totalDia = Integer.valueOf(totalDia.intValue() + quantitat);
		transferitDia.put(data, totalDia);
		restarSaldo(quantitat);
		return totalDia;
	}
}

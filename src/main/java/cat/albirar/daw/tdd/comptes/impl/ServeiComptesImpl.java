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
package cat.albirar.daw.tdd.comptes.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import cat.albirar.daw.tdd.comptes.CompteBean;
import cat.albirar.daw.tdd.comptes.CompteInexistentException;
import cat.albirar.daw.tdd.comptes.IServeiComptes;
import cat.albirar.daw.tdd.comptes.SaldoInsuficientException;
import cat.albirar.daw.tdd.comptes.LimitDiariTransferenciesExceditExcepcio;

/**
 * Implementació de {@link IServeiComptes}.
 * @author Octavi Forn&eacute;s <mailto:ofornes@albirar.cat[]>
 * @since 0.0.1
 */
@Service
@Validated
public class ServeiComptesImpl implements IServeiComptes {
	private Map<String, CompteBean> comptes = null;
	private Map<LocalDate, Map<String, BigDecimal>> transferencies;
	
	@Value("${tdd.maxTransferencies:3000}")
	protected BigDecimal MAX_TOTAL_TRANSFERENCIES;
	/**
	 * Inicialitza el servei, si no estava inicialitzat.
	 */
	@PostConstruct
	public void inicialitzarServei() {
		if(comptes == null) {
			comptes = Collections.synchronizedMap(new TreeMap<>());
			transferencies = Collections.synchronizedMap(new TreeMap<>());
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CompteBean crearCompte() {
		String id;
		
		id = UUID.randomUUID().toString();
		comptes.put(id, BigDecimal.ZERO);
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CompteBean ingressar(@NotBlank String compte, @Min(0) @Max(600000) int total) {
		BigDecimal s;
		
		s = saldo(compte);
		s = s.add(total);
		comptes.put(compte, s);
		return s;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CompteBean retirar(@NotBlank String compte, @Min(0) @Max(600000) int total) {
		BigDecimal s;
		
		s = saldo(compte);
		if(s.compareTo(total) < 0) {
			throw new SaldoInsuficientException();
		}
		s = s.subtract(total);
		comptes.put(compte, s);
		return s;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CompteBean transferir(@NotBlank String compteOrigen, @NotBlank String compteDestinacio, @Min(0) @Max(600000) int total) {
		BigDecimal s1, s2, sAvui;
		LocalDate data;
		Map<String, BigDecimal> tAvui;
		
		s1 = saldo(compteOrigen);
		s2 = saldo(compteDestinacio);
		if(s1.compareTo(total) < 0) {
			throw new SaldoInsuficientException();
		}
		data = LocalDate.now();
		if( (tAvui = transferencies.get(data)) == null) {
			tAvui = Collections.synchronizedMap(new TreeMap<>());
			transferencies.put(data, tAvui);
		}
		if( (sAvui = tAvui.get(compteOrigen)) == null) {
			sAvui = BigDecimal.ZERO;
		}
		sAvui = sAvui.add(total);
		if(MAX_TOTAL_TRANSFERENCIES.compareTo(sAvui) < 0) {
			// Total assolit
			throw new LimitDiariTransferenciesExceditExcepcio();
		}
		tAvui.put(compteOrigen, sAvui);
		s1 = s1.subtract(total);
		s2 = s2.add(total);
		comptes.put(compteOrigen, s1);
		comptes.put(compteDestinacio, s2);
		return s2;
	}
}

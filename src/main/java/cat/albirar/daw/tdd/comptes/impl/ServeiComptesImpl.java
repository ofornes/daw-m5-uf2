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
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import cat.albirar.daw.tdd.comptes.CompteBean;
import cat.albirar.daw.tdd.comptes.CompteInexistentException;
import cat.albirar.daw.tdd.comptes.CompteOperatiuBean;
import cat.albirar.daw.tdd.comptes.IServeiComptes;

/**
 * Implementació de {@link IServeiComptes}.
 * @author Octavi Forn&eacute;s <mailto:ofornes@albirar.cat[]>
 * @since 0.0.1
 * @since 1.0.0
 */
@Service
@Validated
public class ServeiComptesImpl implements IServeiComptes {
	private Map<String, CompteOperatiuBean> comptes = null;
	
	@Value("${tdd.maxTransferencies:3000}")
	protected BigDecimal MAX_TOTAL_TRANSFERENCIES;
	
	private BigDecimal D_100 = BigDecimal.valueOf(100D);
	/**
	 * Inicialitza el servei, si no estava inicialitzat.
	 */
	@PostConstruct
	public void inicialitzarServei() {
		if(comptes == null) {
			comptes = Collections.synchronizedMap(new TreeMap<>());
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CompteBean crearCompte() {
		String id;
		CompteBean c;
		
		id = UUID.randomUUID().toString();
		c = CompteBean.builder().idCompte(id).amount(0).build();
		comptes.put(id, CompteOperatiuBean.builder().compte(c).build());
		return c;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CompteBean cercarCompte(@NotBlank String idCompte) {
		return cercarCompteOperatiu(idCompte).getCompte();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal saldo(@NotBlank String idCompte) {
		return cercarCompte(idCompte).getSaldoEuros();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CompteBean ingressar(@NotBlank String compte, @Min(0) @Max(6000) @Digits(integer = 12, fraction = 2) BigDecimal total) {
		CompteOperatiuBean c;
		
		c = cercarCompteOperatiu(compte);
		c.sumarSaldo(total.multiply(D_100).intValue());
		return c.getCompte();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CompteBean retirar(@NotBlank String compte, @Min(0) @Max(6000) @Digits(integer = 12, fraction = 2) BigDecimal total) {
		CompteOperatiuBean c;
		
		c = cercarCompteOperatiu(compte);
		c.restarSaldo(total.multiply(D_100).intValue());
		return c.getCompte();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CompteBean transferir(@NotBlank String compteOrigen, @NotBlank String compteDestinacio, @Min(0) @Max(6000) @Digits(integer = 12, fraction = 2) BigDecimal total) {
		CompteOperatiuBean cOrig, cDest;
		
		cOrig = cercarCompteOperatiu(compteOrigen);
		cDest = cercarCompteOperatiu(compteDestinacio);
		cOrig.transferirSaldo(total.multiply(D_100).intValue(), MAX_TOTAL_TRANSFERENCIES.multiply(D_100).intValue());
		cDest.sumarSaldo(total.multiply(D_100).intValue());
		return cDest.getCompte();
	}
	/**
	 * Cerca el compte operatiu associat amb l'{@code idCompte} indicat.
	 * @param idCompte L'id de compte
	 * @return El compte operatiu
	 * @throws CompteInexistentException Si el compte no existeix pas
	 */
	private CompteOperatiuBean cercarCompteOperatiu(String idCompte) {
		CompteOperatiuBean r;
		
		r = comptes.get(idCompte);
		if(r == null) {
			throw new CompteInexistentException(idCompte);
		}
		return r;
	}
}

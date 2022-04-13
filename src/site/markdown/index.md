# Prova UF2

*Desenvolupament TDD i refactoring de codi.*

**Continguts**

1. [Introducció](#Introducci.C3.B3)
2. [Codi](#Codi)
3. [Estructura](#Estructura)
4. [Construcció i provatures](#Construcci.C3.B3_i_provatures)
5. [Primera part de la prova](#Primera_part_de_la_prova)
    1. [Proves addicionals](#Proves_addicionals)
6. [Segona part de la prova](#Segona_part_de_la_prova)
    1. [Conclusions](#Conclusions)

------

## Introducció

Per a resoldre la prova UF2, s’ha creat un projecte Java amb maven.

S’ha utilitzat Spring com a framework de desenvolupament per tal d’aprofitar els recursos i facilitats que proporciona (IoC, Dependency Injection, etc.)

Per a crear els *JavaBeans* s'ha utilitzat la llibreria [Lombok](https://projectlombok.org/) que simplifica la implementació i redueix els errors típics de la farragosa i mecànica feina de implementar *getters* i *setters*.
Aquesta utilitat disposa d'un _plugin_ per a diferents IDEs de manera que ofereix els mètodes d'accés a les propietats _on the fly_.

## Codi

El codi de la primera part de la prova és a l’etiqueta [v1](https://github.com/ofornes/daw-m5-uf2/tree/v1).

El codi de la segona part de la prova és a l’etiqueta [v2](https://github.com/ofornes/daw-m5-uf2/tree/v2).

## Estructura

La estructura del projecte és la següent:

1. Definició del contracte de comportament del servei amb la interfície:

   ​	`cat.albirar.daw.tdd.comptes.IServeiComptes`

2. Implementació de les provatures a partir de la especificació de funcionalitats i regles de negoci

3. Implementació del servei amb la classe

   ​	`cat.albirar.daw.tdd.comptes.impl.ServeiComptesImpl`

4. S’han creat excepcions específiques sense marcar per a:

   1. Compte inexistent (en operacions que requereixen un compte existent)
   2. Saldo insuficient (retirar o transferència)
   3. Excedit el limit diari transferit (transferència)

5. La classe `cat.albirar.daw.tdd.TddApplication` configura el context Spring i executa

6. La classe:

   ​	`cat.albirar.daw.tdd.test.comptes.AbstractServeiComptesTest`

   és l’arrel per a les provatures de les funcionalitats de:

   * Creació: `ServeiComptesCreacioTest`
   * Ingressos: `ServeiComptesIngressosTest`
   * Retirades: `ServeiComptesRetiradesTest`
   * Transferències: `ServeiComptesTransferenciesTest`

## Construcció i provatures

Heu d'utilitzar maven:

```
mvn clean install
```

Així es generarà el codi i s'executaran les provatures

## Primera part de la prova

**Requeriments del sistema:**

- Creació d'un compte corrent ([cat.albirar.daw.tdd.test.comptes.ServeiComptesCreacioTest](testapidocs/cat/albirar/daw/tdd/test/comptes/ServeiComptesCreacioTest.html))
    - Els comptes sempre es creen amb saldo 0. Hi ha que fer algun ingrés després si es vol tenir saldo.
        - **Quan creem el compte el saldo és zero.**
- Ingressos ([cat.albirar.daw.tdd.test.comptes.ServeiComptesIngressosTest](testapidocs/cat/albirar/daw/tdd/test/comptes/ServeiComptesIngressosTest.html))
    - Sumen la quantitat ingressada al saldo.
    - No hi ha comissions ni res per l'estil.
        - **Quan ingressem 100 en un compte buit, el saldo és 100**
        - **Quan ingressem 3000 en un compte buit, el saldo és 3000**
        - **Quan ingressem 3000 en un compte amb 100, el saldo és de 3100**
    - No es poden fer ingressos negatius
        - **Quan ingressem -100 en un compte buit, es saldo segueix sent 0**
    - Els ingressos admeten un màxim de 2 decimals de precisió
        - **Quan ingressem 100.45 en un compte buit, el saldo és de 100.45**
        - **Si ingressem 100.457 en un compte buit, el saldo és zero**
    - La quantitat màxima que es pot ingressar és de 6000
        - **Si ingressem 6000.00 en un compte buit, el saldo és de 6000.00**
        - **Si ingressem 6000.01 en un compte buit, el saldo és de 0**
- Retirades ([cat.albirar.daw.tdd.test.comptes.ServeiComptesRetiradesTest](testapidocs/cat/albirar/daw/tdd/test/comptes/ServeiComptesRetiradesTest.html))
    - Resten la quantitat que es passa com a paràmetre del saldo.
    - No hi ha comissions ni res per l'estil.
        - **Quan retirem 100 en un compte amb 500, el saldo és 400**
    - No es pot retirar una quantitat major a la del saldo disponible.
        - **Si retirem 500 en un compte amb 200, no ocorre res i el saldo continua sent 200**
    - No es poden retirar quantitats negatives
        - **Si retirem -100 en un compte amb 500, no ocorre res i el saldo continua sent 500**
    - Les quantitats admeten un màxim de dos decimals de precisió
        - **Si retirem 100.45 en un compte amb 500, el saldo és 399.55**
        - **Si retirem 100.457 en un compte amb 500, no ocorre res i el saldo continua sent 500**
    - La quantitat màxima que es pot retirar és de 6000
        - **Si retirem 6000.00 d'un compte amb 7000, el saldo és de 1000**
        - **Si retirem 6000.01 en un compte amb 7000, no ocorre res i el saldo continua sent 7000**
- Transferències ([cat.albirar.daw.tdd.test.comptes.ServeiComptesTransferenciesTest](testapidocs/cat/albirar/daw/tdd/test/comptes/ServeiComptesTransferenciesTest.html))
    - **Si fem una transferència de 100 des d'un compte amb 500 a un amb 50, en el primer compte el saldo serà de 400 i en la segona serà de 150**
    - No es poden transferir quantitats negatives
        - **Si fem una transferència de -100 des d'un compte amb 500 a un amb 50, els saldos es queden amb 500 i 50 respectivament**
    - El límit de transferències en un mateix dia des d'un mateix compte és de 3000:
        - **Si fem una transferència de 3000 des d'un compte amb 3500 a un compte amb 50, en el primer compte el saldo quedarà en 500 i en el segon en 3050**
        - **Si fem una transferència de 3000.01 des d'un compte amb 3500 a un compte amb 50, en el primer compte es quedaran 3500 i en el segon 50**
        - **Si fem una transferència de 2000 des d'un compte amb 3500 a un compte amb 50 i just després una altra de 1200, en el primer compte el saldo quedarà en 1500 i en la segona en 2050**

### Proves addicionals

A la vista dels resultat de la prova de cobertura de codi del servei [ServeiComptesImpl](jacoco/cat.albirar.daw.tdd.comptes.impl/ServeiComptesImpl.html), els valors de cobertura de [saldo](jacoco/cat.albirar.daw.tdd.comptes.impl/ServeiComptesImpl.java.html#L86) i [transferir](jacoco/cat.albirar.daw.tdd.comptes.impl/ServeiComptesImpl.java.html#L131), aconsellen afegir algunes provatures purament orgàniques, que tenen a veure amb la implementació del servei i no pas amb la especificació inicial del mateix. En concret:
 - Amb un id de compte inexistent:
    - Demanar saldo
    - Ingressar
    - Retirar
    - Transferir (en ambdós sentits)
 - Transferir quantitat superior al saldo del compte origen

Amb aquestes noves provatures, el codi del servei estaria completament cobert i podem tenir la seguretat que totes les situacions estan previstes.


## Segona part de la prova

S'ha creat la classe [CompteBean](apidocs/cat/albirar/daw/tdd/comptes/CompteBean.html) com a _bean immutable_. El saldo s'obté en cèntims, però també s'afegeix un mètode per a obtenir-ho en Euros (tot dividint `amount` entre 100).

S'ha mantingut la estructura del servei, però afegint el [CompteBean](apidocs/cat/albirar/daw/tdd/comptes/CompteBean.html) als valors de retorn dels mètodes que abans retornaven saldos o identificadors de compte.

S'ha creat la classe especial [CompteOperatiuBean](apidocs/cat/albirar/daw/tdd/comptes/CompteOperatiuBean.html) per a operar sobre el saldo de [CompteBean](apidocs/cat/albirar/daw/tdd/comptes/CompteBean.html) (recordem que és immutable ja que és la _visible_ per al client de l'API).
Aquest *bean* especial permet al servei operar sobre el saldo d'un compte i implementa alguna part de la lògica de negoci (per exemple el límit màxim de transferència diària).


Les provatures s'han adaptat a aquests petits canvis, però la essència s'ha mantingut.

### Conclusions

Les proves de cobertura demostren que la re-factorització, amb pocs canvis a les provatures i serveis, han encapsulat millor el funcionament del servei, ja que s'observa un augment del codi cobert sense canvis en les provatures.

El disseny d'encapsulament desenvolupat a la segona part, és el més adient als principis OOP.


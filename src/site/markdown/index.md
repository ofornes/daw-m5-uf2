# Prova UF2

*Desenvolupament TDD i refactoring de codi.*

------

## Introducció

Per a resoldre la prova UF2, s’ha creat un projecte Java amb maven.

S’ha utilitzat Spring com a framework de desenvolupament per tal d’aprofitar els recursos i facilitats que proporciona (IoC, Dependency Injection, etc.)

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

## Primera part de la prova

**Requeriments del sistema:**

- Creació d'un compte corrent.
    - Els comptes sempre es creen amb saldo 0. Hi ha que fer algun ingrés després si es vol tenir saldo.
        - **Quan creem el compte el saldo és zero.**
- Ingressos.
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
- Retirades.
    - Resten la quantitat que es passa com a paràmetre del saldo.
    - No hi ha comissions ni res per l'estil.
        - **Quan retirem 100 en un compte amb 500, el saldo és 400**
    - No es pot retirar una quantitat major a la del saldo disponible.
        - **Si retirem 500 en un compte amb 200, no ocorre res i el saldo continua sent 200**
    - No es poden retirar quantitats negatives
        - **Si retirem -100 en un compte amb 500, no ocorre res i el saldo continua sent 500**
    - Les quantitats admeten un màxim de dos decimals de precisió
        - **Si retirem100.45 en un compte amb 500, el saldo és 399.55**
        - **Si retirem 100.457 en un compte amb 500, no ocorre res i el saldo continua sent 500**
    - La quantitat màxima que es pot retirar és de 6000
        - **Si retirem 6000.00 d'un compte amb 7000, el saldo és de 1000**
        - **Si retirem 6000.01 en un compte amb 7000, no ocurre nada i el saldo continua sent 7000**
- Transferències
    - **Si fem una transferència de 100 des d'un compte amb 500 a un amb 50, en el primer compte el saldo serà de 400 i en la segona serà de 150**
    - No es poden transferir quantitats negatives
        - **Si fem una transferència de -100 des d'un compte amb 500 a un amb 50, els saldos es queden amb 500 i 50 respectivament**
    - El límit de transferències en un mateix dia des d'un mateix compte és de 3000:
        - **Si fem una transferència de 3000 des d'un compte amb 3500 a un compte amb 50, en el primer compte el saldo quedarà en 500 i en el segon en 3050**
        - **Si fem una transferència de 3000.01 des d'un compte amb 3500 a un compte amb 50, en el primer compte es quedaran 3500 i en el segon 50**
        - **Si fem una transferència de 2000 des d'un compte amb 3500 a un compte amb 50 i just després una altra de 1200, en el primer compte el saldo quedarà en 1500 i en la segona en 2050**


# Prova UF2

*Desenvolupament TDD i refactoring de codi.*

------

##Introducció

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

##Construcció i provatures


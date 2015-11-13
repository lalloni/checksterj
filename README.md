Checksterj
==========

Librería Java para validación de disponibilidad y compatibilidad de versiones de
servicios HTTP.

Uso básico
==========

```java
// Preparar versión del cliente
Version clientVersion = new Version("nombre-servicio", 3, 0, 1, "build1");

// Preparar URL de un "servicio de versión"
URL serviceURL = new URL("http://ws.example.com/version");

// Inicializar un Check
Check check = new Check(clientVersion, serviceURL);

// Obtener el resultado del Check
CheckResult result = check.getResult();
// Este método puede llamarse de forma repetida sin lanzar múltiples solicitudes
// HTTP ya que Check conserva el último resultado obtenido.
// Si se desea forzar una nueva validación se puede utilizar el método check.run()

// Obtener el booleano que indica si el servicio es usable y compatible o no
Boolean ok = result.isSuccess();

if (!ok) {
  // Hacer algo para informar que el servicio no está usable
}
```

Uso con fallo implícito
=======================

Si se desea usar la librería de manera tal que la misma en lugar de devolvernos
un resultado a analizar simplemente lance una excepción para abortar alguna
operación puede utilizarse el método:

```java
// Lanza una instancia de CheckFailure (unchecked) en caso de no validarse el
// servicio. La excepción expone el resultado con getCheckResult().
check.ensureSuccesful()
```

Información de diagnóstico
==========================

Cuando se decide que el servicio es inválido, la librería nos devuelve
información de diagnóstica que puede ser utilizada para informar del problema
de manera que se facilite su corrección:

```java
// Obtener la razón de la decisión
Reason reason = result.getReason();

// Obtener un mensaje comprensible (esperemos) por humanos que indica el motivo
// de la decisión
String message = reason.getMessage();

// Obtener la excepción que causó la decisión tomada (si la hubiera)
Exception cause = reason.getCause();
```

Soporte de SSL
==============

La librería permite ser utilizada para validar servicios que escuchan por
SSL/TLS y que utilicen requieran autenticación por certificado de cliente.

Para explotar la funcionalidad simplemente se debe suministrar el `SSLContext` al
constructor de `Check`.

```java
// Inicializar u obtener un SSLContext
SSLContext sslContext = ...

// Inicializar un Check
Check check = new Check(clientVersion, serviceURL, sslContext);

// Usarlo como siempre...
```

Reglas de compatibilidad
========================

Se pueden modificar las reglas de compatibilidad que utiliza la librería para
decidir si un servicio es compatible o no suministrando al constructor de `Check`
una implementación de la interfaz `CompatibilityRules` que es la responsable de
tomar dicha decisión.

Si no se suministra dicha instancia, la librería utiliza una instancia provista
por la misma librería que valida por igualdad de identificador de servicio y de
número mayor de versión (`checkster.rules.Rules.SameServiceAndMajor`).

Asimismo se proveen implementaciones que pueden utilizarse si se desea para
validar únicamente el identificador de servicio por igualdad
(`checkster.rules.SameService`) o el número mayor de la versión
(`checkster.rules.SameMajor`) y un combinador que puede usarse para secuenciar
una cantidad arbitraria de implementaciones básicas (`checkster.rules.Sequence`).

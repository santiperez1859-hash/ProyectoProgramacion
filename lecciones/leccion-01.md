# Lección 1 · Eclipse y tu primer programa

Hoy creás el proyecto del parcial, escribís tu primer programa y aprendés a guardar datos en variables. Tiempo estimado: 2 horas.

## 1. Las cuatro cajas de Eclipse

Eclipse ordena todo en cajas, una dentro de otra. Pensalo como un estante con biblioratos:

- **Workspace:** la carpeta de tu compu donde Eclipse guarda todos tus proyectos. Es el estante.
- **Proyecto:** un programa entero. El parcial va a ser un proyecto llamado `ProyectoBiblioteca`. Es un bibliorato.
- **Paquete** (*package*): una carpeta adentro del proyecto para ordenar las clases. Vamos a usar uno solo, `proyecto`. Es una separadora del bibliorato.
- **Clase** (*class*): un archivo `.java` con código. Es cada hoja.

Así lo vas a ver a la izquierda de Eclipse, en el panel **Package Explorer**:

```
ProyectoBiblioteca          ← el proyecto
 └─ src                     ← la carpeta del código
     └─ proyecto            ← el paquete
         └─ principal.java  ← una clase
```

Al final del parcial, el paquete `proyecto` va a tener 8 clases. Hoy creamos la primera.

## 2. Paso a paso: crear el proyecto del parcial

Esto se hace una sola vez; todo el parcial lo vas a escribir adentro de este proyecto.

**Con el grupo en GitHub:** el proyecto lo crea una sola persona, Santy, y adentro de la carpeta del repo. En el paso 5, antes de **Finish**, destildá **Use default location** y en **Location** escribí `C:\Users\santi\Desktop\ProyectoProgramacion\ProyectoBiblioteca`. Mati y Gabi no lo crean: cuando Santy lo suba, hacen Pull y lo importan con **File → Import → General → Existing Projects into Workspace**.

1. Abrí Eclipse. Si te pregunta por el **workspace**, dejá la carpeta que propone (anotá dónde está) y tocá **Launch**.
2. Si aparece la pestaña **Welcome**, cerrala con su X.
3. Dejá las tildes y la ñ en orden, una sola vez: **Window → Preferences → General → Workspace**. Abajo, en **Text file encoding**, marcá **Other: UTF-8** y tocá **Apply and Close**. Así el texto con tildes se ve bien en cualquier compu, incluida la del profe.
4. **File → New → Java Project**. Si no aparece, entrá por **File → New → Project… → Java → Java Project**.
5. En **Project name** escribí `ProyectoBiblioteca`. Si ves la casilla **Create module-info.java file**, destildala. Tocá **Finish**.
6. Si igual te pregunta por un módulo (*module-info.java*), elegí **Don't Create**. Ese archivo complica las ventanas Swing y en el curso no se usa. Si ya se creó, borralo: click derecho sobre `module-info.java` → **Delete**.
7. En el Package Explorer abrí tu proyecto con la flechita. Click derecho sobre `src` → **New → Package**. Name: `proyecto`, todo en minúscula → **Finish**.
8. Click derecho sobre el paquete `proyecto` → **New → Class**. Name: `principal`. Tildá **public static void main(String\[\] args)** → **Finish**.

Eclipse va a avisar arriba que los nombres de clase suelen empezar con mayúscula. Es un consejo, no un error: el profe usa `principal` en minúscula, así que seguí igual.

Eclipse te crea esto:

```java
package proyecto;

public class principal {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
```

Borrá la línea `// TODO Auto-generated method stub`. Es un comentario automático que no hace nada, y con este profe los comentarios de más restan.

## 3. Tu primer programa

Un programa en Java arranca siempre por el método `main` y ejecuta sus líneas de arriba hacia abajo. Escribí esto (las dos líneas nuevas van adentro del main, entre sus llaves):

```java
package proyecto;

public class principal {

    public static void main(String[] args) {
        System.out.println("Hola, soy Santy");
        System.out.println("Este es mi proyecto del parcial");
    }

}
```

Qué es cada parte:

- `package proyecto;` dice en qué paquete está esta clase.
- `public class principal {` abre la clase. Todo lo que está entre esa llave y la `}` del final pertenece a la clase.
- `public static void main(String[] args) {` es el método **main**, la puerta de entrada: cuando ejecutás, Java empieza a leer acá. Por ahora tomalo como una fórmula; qué significa cada palabra lo vemos en las lecciones 4 y 5.
- `System.out.println("...");` imprime el texto en la **consola** y salta de línea. El texto va entre comillas dobles.
- Cada instrucción termina con `;`, y cada `{` tiene su `}`. Si ponés el cursor al lado de una llave, Eclipse marca su pareja.

Ahora ejecutalo:

1. Guardá con **Ctrl + S**. Si la pestaña del archivo muestra un `*`, hay cambios sin guardar.
2. Tocá el botón verde ▶ (**Run**) de la barra de arriba, o **Ctrl + F11**. Si pregunta cómo ejecutarlo, elegí **Java Application**.
3. Abajo se abre la pestaña **Console** con tus dos líneas.

Truco: escribí `sysout` y apretá **Ctrl + Espacio**; Eclipse lo completa a `System.out.println();`.

Probá cambiar `println` por `print` en la primera línea: las dos frases salen pegadas, porque `print` no salta de línea. Después volvé a dejarlo como estaba.

## 4. Errores: cómo leerlos y arreglarlos

Un error no es un fracaso: Eclipse te dice qué falta y dónde. Hoy los provocás a propósito para aprender a leerlos.

1. Borrá el `;` del final de un println y guardá. Aparece una cruz roja a la izquierda de la línea y una parte subrayada en rojo.
2. Pasá el mouse por la cruz. Dice algo como `Syntax error, insert ";" to complete BlockStatements`, o sea: falta un `;`.
3. Volvé a poner el `;` y guardá: la cruz desaparece.

Probá estos dos también:

| Qué rompés | Qué dice Eclipse | Qué significa |
| --- | --- | --- |
| Escribís `system.out.println`, con s minúscula | `system cannot be resolved` | Java no conoce `system`: distingue mayúsculas de minúsculas |
| Borrás una de las comillas | `String literal is not properly closed by a double-quote` | Un texto quedó sin cerrar |

Reglas para no trabarte:

- El error está en la línea marcada o en la anterior.
- Leé el mensaje aunque esté en inglés: casi siempre dice qué falta.
- Si al ejecutar aparece **Errors exist in project**, tocá **Cancel** y arreglá primero las cruces rojas.
- Un triángulo amarillo es solo una advertencia (*warning*): el programa corre igual.
- Si no sale, mandame el mensaje completo y la línea donde aparece.

## 5. Variables: guardar datos

Una variable es una caja con nombre donde guardás un dato. Se crea con tres cosas: tipo, nombre y valor.

```java
String titulo = "Rayuela";
```

- `String` es el **tipo**: qué clase de dato entra en la caja (acá, texto).
- `titulo` es el **nombre**, para usarla después.
- `=` significa **guardar**: pone el valor de la derecha adentro de la caja de la izquierda. No es el «igual» de matemática.
- `"Rayuela"` es el **valor**.

Los cuatro tipos que vas a usar:

| Tipo | Guarda | Ejemplo | En el parcial |
| --- | --- | --- | --- |
| `int` | Números enteros | `int anio = 1963;` | Año, valoración, segundos de prueba |
| `double` | Números con coma (se escriben con punto) | `double precio = 450.5;` | Por ahora no; conviene conocerlo |
| `String` | Texto, entre comillas dobles | `String autor = "Julio Cortázar";` | Título, autor, editorial |
| `boolean` | Verdadero o falso: `true` o `false` | `boolean leido = true;` | Saber si terminó la prueba |

`String` va con mayúscula y los otros tres con minúscula. Hay una razón (`String` es una clase) que vemos en la Lección 5.

Escribí esto en tu main, debajo de los println que ya tenías:

```java
String titulo = "Rayuela";
String autor = "Julio Cortázar";
int anio = 1963;
int valoracion = 4;
boolean leido = true;

System.out.println("Título: " + titulo);
System.out.println("Autor: " + autor);
System.out.println("Año: " + anio);
System.out.println("Valoración: " + valoracion + " de 5");
System.out.println("¿Lo leí? " + leido);
```

En la consola tiene que salir:

```
Título: Rayuela
Autor: Julio Cortázar
Año: 1963
Valoración: 4 de 5
¿Lo leí? true
```

Para cambiar el valor de una variable que ya existe, no repetís el tipo: `valoracion = 5;`. El tipo va solo la primera vez, cuando la creás. Si lo repetís, Eclipse marca `Duplicate local variable valoracion`.

Reglas para los nombres:

- Empiezan con minúscula y no llevan espacios, tildes ni ñ. Por eso usamos `anio` y no `año`.
- Si son varias palabras, cada palabra nueva arranca con mayúscula: `anioPublicacion`, `segundosUsados`. Se llama *camelCase*.
- Que digan qué guardan: `titulo` es mejor que `t` o `x`.

## 6. Operaciones y concatenación

Con números, Java hace cuentas; con textos, el `+` los pega.

| Símbolo | Qué hace | Ejemplo | Resultado |
| --- | --- | --- | --- |
| `+` | Suma | `2026 + 1` | `2027` |
| `-` | Resta | `2026 - 1963` | `63` |
| `*` | Multiplicación | `30 * 60` | `1800` |
| `/` | División | `7 / 2` | `3` (entre enteros se pierde la parte decimal) |
| `%` | Resto de la división | `7 % 2` | `1` |

Agregá esto a tu main:

```java
int anioActual = 2026;
int antiguedad = anioActual - anio;
System.out.println("Tiene " + antiguedad + " años");
```

Sale `Tiene 63 años`.

Entre enteros, `/` dice cuántas veces entra un número en otro y `%` dice cuánto sobra. Lo vamos a usar en la versión de prueba para mostrar el tiempo: 125 segundos son `125 / 60` = 2 minutos y `125 % 60` = 5 segundos. Con `double` no se pierde nada: `7.0 / 2` da `3.5`.

Con textos, el `+` pega (*concatena*): `"Hola " + "Santy"` da `"Hola Santy"`. Si mezclás texto y número, el número se vuelve texto y se pega.

La trampa clásica, porque Java lee de izquierda a derecha:

```java
System.out.println("Total: " + 2 + 3);
System.out.println("Total: " + (2 + 3));
```

La primera imprime `Total: 23`: Java pega «Total: » con el 2 y después pega el 3. La segunda imprime `Total: 5`, porque el paréntesis hace la cuenta primero.

## 7. Ejercicios

Hacelos en el mismo `principal`, debajo de lo que ya tenés, y escribilos sin copiar. Marcá cada uno cuando funcione.

- [ ] **Ficha de tu libro.** Creá variables con los datos de un libro que te guste (título, autor, año y valoración del 1 al 5) e imprimí una ficha como la de abajo.
- [ ] **Antigüedad.** Calculá e imprimí cuántos años tiene ese libro.
- [ ] **El reloj de la prueba.** Creá `int segundos = 1500;` e imprimí cuántos minutos y segundos son, usando `/` y `%`. Tiene que salir `25 min 0 s`. Después cambiá el valor a 1799 y fijate qué sale.
- [ ] **Cazador de errores.** Borrá a propósito una llave `}`, guardá y anotá qué dice Eclipse. Después arreglalo.
- [ ] **Subirlo a GitHub.** Hacé commit y push con el mensaje «Lección 1: proyecto creado». Mati y Gabi: hagan Pull e importen el proyecto en su Eclipse.

Así tiene que verse la ficha del primer ejercicio:

```
=== Mi libro ===
Título: Rayuela
Autor: Julio Cortázar
Año: 1963
Valoración: 4/5
```

## 8. Preguntas tipo defensa

Respondelas con tus palabras y sin mirar. Si alguna no te sale, volvé a la sección que corresponde.

1. ¿Qué diferencia hay entre un proyecto, un paquete y una clase?
2. ¿Para qué sirve el método `main`?
3. ¿Qué diferencia hay entre `int` y `String`? ¿Y entre `int` y `double`?
4. ¿Qué hace el `=` en `int anio = 1963;`?
5. ¿Qué imprime `System.out.println("Año: " + 2000 + 26);`? ¿Por qué?
6. ¿Cuánto da `1799 / 60`? ¿Y `1799 % 60`?

**Cuando termines, mandame por el chat:**

- El código completo de tu `principal` (Ctrl + A y Ctrl + C adentro del archivo) o una captura de Eclipse con la consola.
- Tus respuestas a estas 6 preguntas.

Con eso te corrijo y arrancamos la Lección 2: decisiones con `if` y `else`.

---

[Índice](README.md) · [Lección 2 →](leccion-02.md)

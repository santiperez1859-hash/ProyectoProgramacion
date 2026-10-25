# Lección 15 · Versión de prueba de 30 minutos

Hoy cumplís el requisito más particular de la consigna: el programa se puede usar 30 minutos en total, y después solo deja ver lo guardado. Tiempo estimado: 2 horas.

## 1. La idea

Son tres piezas:

1. La biblioteca guarda cuántos segundos de uso lleva. Como se serializa, ese número sobrevive al cerrar el programa.
2. Un reloj suma un segundo por segundo mientras el programa está abierto, y guarda.
3. Cuando llega a 30 minutos (1800 segundos), la ventana bloquea todo lo que modifica datos. Al abrir, si ya no queda tiempo, arranca bloqueada.

«30 minutos en total» significa sumando todas las veces que se abre: si lo usás 10 minutos hoy y lo cerrás, mañana te quedan 20.

## 2. El tiempo en Biblioteca

Sumá a `Biblioteca` la constante y el atributo, debajo del `serialVersionUID`:

```java
public static final int LIMITE_SEGUNDOS = 30 * 60;
private ArrayList<Documento> documentos;
private int segundosUsados;
```

En el constructor, `this.segundosUsados = 0;` debajo de la creación de la lista. Y estos tres métodos al final de la clase:

```java
public void sumarSegundo() {
    segundosUsados++;
}

public int getSegundosRestantes() {
    return LIMITE_SEGUNDOS - segundosUsados;
}

public boolean pruebaTerminada() {
    return segundosUsados >= LIMITE_SEGUNDOS;
}
```

- `LIMITE_SEGUNDOS` es una constante `public static final`: un único valor para todo el programa. Si en la defensa te piden 10 minutos, cambiás solo esta línea: `10 * 60`.
- `pruebaTerminada()` es el `boolean` de la Lección 2, convertido en método.
- Como `Biblioteca` ya es `Serializable`, `segundosUsados` se guarda en el archivo junto con los documentos.

## 3. El reloj: javax.swing.Timer

Un `Timer` de Swing es como un botón invisible que se aprieta solo cada cierto tiempo: dispara `actionPerformed` cada 1000 milisegundos, o sea, cada segundo. Usa el mismo mecanismo que los botones.

En `VentanaPrincipal`, sumá el atributo `private Timer reloj;` debajo de `seleccionado`. Al importar, Eclipse te ofrece varios `Timer`: elegí **javax.swing.Timer**. El de `java.util` es otro y no funciona con `actionPerformed`.

Completá `iniciar`:

```java
public void iniciar(Biblioteca biblioteca) {
    this.biblioteca = biblioteca;
    actualizarTabla();
    reloj = new Timer(1000, this);
    if (biblioteca.pruebaTerminada()) {
        terminarPrueba();
    } else {
        mostrarTiempo();
        reloj.start();
    }
}
```

`new Timer(1000, this)` se lee: «cada 1000 ms, avisale a esta ventana». Si la prueba ya terminó (por ejemplo, porque la abrís al día siguiente), arranca bloqueada; si no, muestra el tiempo y arranca el reloj.

En `actionPerformed`, sumá una rama al final, después de la de `cmbTipo`:

```java
} else if (e.getSource() == reloj) {
    pasarUnSegundo();
}
```

## 4. Cada segundo

```java
private void pasarUnSegundo() {
    biblioteca.sumarSegundo();
    Archivo.guardar(biblioteca);
    if (biblioteca.pruebaTerminada()) {
        terminarPrueba();
        JOptionPane.showMessageDialog(this, "Se terminó la versión de prueba. Desde ahora solo podés ver los documentos.");
    } else {
        mostrarTiempo();
    }
}

private void mostrarTiempo() {
    int restantes = biblioteca.getSegundosRestantes();
    lblTiempo.setText("Prueba: quedan " + restantes / 60 + " min " + restantes % 60 + " s");
}
```

- Suma un segundo y guarda: si el programa se cierra de golpe, se pierde como mucho un segundo de conteo.
- Si se terminó, bloquea y avisa una sola vez, porque `terminarPrueba` detiene el reloj.
- Si no, actualiza la etiqueta. `/ 60` y `% 60` pasan los segundos a minutos y segundos, como en la Lección 1.

## 5. Bloquear

```java
private void terminarPrueba() {
    reloj.stop();
    cmbTipo.setEnabled(false);
    txtTitulo.setEnabled(false);
    txtAutor.setEnabled(false);
    txtAnio.setEnabled(false);
    spnValoracion.setEnabled(false);
    txtExtra.setEnabled(false);
    btnAgregar.setEnabled(false);
    btnModificar.setEnabled(false);
    btnEliminar.setEnabled(false);
    lblTiempo.setText("Prueba terminada: solo lectura.");
}
```

`setEnabled(false)` deja un componente gris y sin respuesta. Se bloquea todo lo que modifica datos: los campos del formulario y los botones Agregar, Modificar y Eliminar. Siguen andando la tabla, el filtro, la búsqueda y el clic en una fila, que muestra los datos en el formulario aunque esté gris. Eso es «visualizar la información guardada», lo que pide la consigna.

Por último, en `limpiarFormulario` el tipo solo se desbloquea si queda tiempo. Cambiá la última línea por:

```java
if (!biblioteca.pruebaTerminada()) {
    cmbTipo.setEnabled(true);
}
```

Sin este `if`, tocar Limpiar con la prueba vencida volvería a habilitar el tipo.

## 6. Probar sin esperar 30 minutos

1. En `Biblioteca`, cambiá un momento el límite a 20 segundos: `LIMITE_SEGUNDOS = 20;`.
2. Cerrá el programa si estaba abierto y borrá `biblioteca.bin` para arrancar de cero.
3. Ejecutá: el contador baja, y a los 20 segundos aparece el aviso y se bloquea.
4. Probá filtrar, buscar y hacer clic en filas: tiene que andar.
5. Cerrá y abrí: arranca bloqueado.
6. Volvé a poner `30 * 60` y borrá `biblioteca.bin`.

## 7. ¿Por qué Timer y no el hilo del profe?

El profe dejó en el material un código para ejecutar un hilo con `ExecutorService`. Un hilo corre en paralelo a la ventana, y tocar componentes de Swing desde otro hilo no es seguro. El `Timer` de Swing hace lo mismo (repetir algo cada segundo), pero dentro del hilo de la ventana, y usa el mismo `actionPerformed` que los botones: menos código y nada nuevo que explicar. Tené esta respuesta lista, porque es probable que te la pregunten.

## 8. Punto de control

Con esta lección, tus clases tienen que quedar iguales a las de la carpeta [referencia](../referencia/). Comparalas clase por clase, sobre todo `Biblioteca` y `VentanaPrincipal`.

## 9. Ejercicios

- [ ] Mostrá el tiempo con dos dígitos en los segundos, por ejemplo «29:05». Pista: si los segundos son menos de 10, agregá un «0» adelante.
- [ ] Hacé que, cuando queden 5 minutos, aparezca un aviso una sola vez. Pista: en `pasarUnSegundo`, compará `biblioteca.getSegundosRestantes() == 300`.
- [ ] Explicá en el cuaderno qué pasa si alguien borra `biblioteca.bin`.

## 10. Preguntas tipo defensa

1. ¿Cómo se cuentan los 30 minutos? ¿Dónde se guarda el tiempo?
2. Si lo uso 10 minutos, lo cierro y lo abro, ¿cuánto queda? ¿Por qué?
3. ¿Qué es un `Timer` y cómo se conecta con `actionPerformed`?
4. ¿Por qué `Timer` y no el hilo con `ExecutorService`?
5. ¿Qué queda habilitado cuando termina la prueba y por qué?
6. ¿Cómo cambiarías el límite a 10 minutos?

**Cuando termines, mandame** una captura con la prueba terminada y tus respuestas.

---

[← Lección 14](leccion-14.md) · [Índice](README.md) · [Lección 16 →](leccion-16.md)

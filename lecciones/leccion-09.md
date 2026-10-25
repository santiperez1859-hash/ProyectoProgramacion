# Lección 9 · La ventana: JFrame y JPanel

Hoy creás la ventana del programa con WindowBuilder, igual que el profe en el material «1 JFrame y JPanel». Por ahora vacía: el objetivo es que abra desde `principal`. Tiempo estimado: 2 horas.

## 1. La idea: la mesa y el mantel

El profe lo explica así: el `JFrame` es la **mesa** (la ventana, con su barra de título y los botones de minimizar, maximizar y cerrar) y el `JPanel` es el **mantel** que va encima, donde se apoyan los botones y los campos.

Nuestra ventana va a ser una clase que **hereda** de `JFrame`: `VentanaPrincipal extends JFrame`. Es la herencia de la Lección 6, pero con una clase que ya trae Java.

## 2. Antes: guardar las pruebas de consola

Hasta ahora `principal` probaba la biblioteca en la consola; desde hoy va a abrir la ventana. Si querés conservar esas pruebas, creá en `practica` una clase `PruebaConsola`, pegá adentro todo lo que está entre las llaves de `principal` (el main y `mostrarLista`) y apretá **Ctrl + Shift + O**: Eclipse agrega los `import` de `proyecto.Biblioteca`, `proyecto.Libro` y el resto.

## 3. Crear la ventana con WindowBuilder

1. Click derecho sobre el paquete `proyecto` → **New → Other…**
2. En el buscador escribí `JFrame` y elegí **WindowBuilder → Swing Designer → JFrame** → **Next**.
3. Name: `VentanaPrincipal` → **Finish**.

Eclipse abre el archivo con dos pestañas abajo del editor: **Source** (el código) y **Design** (la vista visual). Arrancá en Source.

## 4. Borrar el main que genera WindowBuilder

WindowBuilder escribe un main propio adentro de la ventana. Como hace el profe, lo borramos: la ventana se va a abrir desde `principal`. Borrá todo este bloque (puede variar un poco según la versión):

```java
/**
 * Launch the application.
 */
public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
        public void run() {
            try {
                VentanaPrincipal frame = new VentanaPrincipal();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });
}
```

Borrá también la línea `import java.awt.EventQueue;`, que queda marcada en amarillo porque ya no se usa, y los comentarios `/** ... */` que agregó WindowBuilder.

## 5. Entender el constructor

Lo que queda es el constructor. Línea por línea, como en el material del profe:

| Línea | Qué hace |
| --- | --- |
| `setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);` | Al cerrar la ventana, termina el programa |
| `setBounds(100, 100, 450, 300);` | Posición (100 px desde la izquierda y desde arriba de la pantalla) y tamaño (450 de ancho, 300 de alto) |
| `contentPane = new JPanel();` | Crea el mantel |
| `contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));` | Le deja un margen de 5 px de cada lado |
| `setContentPane(contentPane);` | Pone el mantel sobre la mesa |

Si arriba aparece `private static final long serialVersionUID = 1L;`, dejalo: lo explicamos en la Lección 14.

## 6. Los ajustes del profe

Dejá el constructor así:

```java
public VentanaPrincipal() {
    setTitle("Mi Biblioteca");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 900, 520);
    setResizable(false);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);
}
```

- `setTitle`: el texto de la barra de título.
- `setBounds(100, 100, 900, 520)`: más grande, para que entren el formulario y la tabla.
- `setResizable(false)`: el usuario no puede cambiar el tamaño. Lo pide el profe porque vamos a usar posiciones fijas.
- `contentPane.setLayout(null)`: **layout absoluto**, cada componente va en la posición exacta que le digamos. En la vista Design aparece como «Absolute layout».

## 7. Abrir la ventana desde principal

Reemplazá todo `principal` por esto:

```java
package proyecto;

public class principal {

    public static void main(String[] args) {
        VentanaPrincipal ventana = new VentanaPrincipal();
        ventana.setVisible(true);
    }
}
```

La ventana se crea igual que cualquier objeto (Lección 5) y `setVisible(true)` la muestra. Ejecutá `principal`: tiene que aparecer una ventana vacía de 900 × 520 con el título «Mi Biblioteca».

## 8. Recorrida por la vista Design

Tocá la pestaña **Design**:

- **Palette**: los componentes que se pueden agregar (JLabel, JButton, JTextField…).
- **Components**: el árbol de lo que tiene la ventana.
- **Properties**: las propiedades del componente elegido, como el texto, el nombre de la variable o la posición.

Probá agregar un JLabel, ponele de texto «Mi Biblioteca» en Properties y volvé a Source: WindowBuilder escribió el código por vos. Después borralo; el formulario lo armamos bien en la Lección 10.

## 9. Punto de control

Así tiene que quedar `VentanaPrincipal.java`:

```java
package proyecto;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public VentanaPrincipal() {
        setTitle("Mi Biblioteca");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 520);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
    }
}
```

## 10. Ejercicios

- [ ] Cambiá el título y el tamaño de la ventana, ejecutá y volvé a dejarlos como estaban.
- [ ] Sacá el `setResizable(false)`: ¿qué cambia? Volvé a ponerlo.
- [ ] Agregá un JLabel desde Design y mirá qué código generó en Source. Después borralo.

## 11. Preguntas tipo defensa

1. ¿Qué diferencia hay entre `JFrame` y `JPanel`?
2. ¿Por qué `VentanaPrincipal` dice `extends JFrame`?
3. ¿Qué hacen `setBounds` y `setResizable(false)`?
4. ¿Qué es el layout absoluto?
5. ¿Por qué borramos el main que genera WindowBuilder?
6. ¿Qué hace `ventana.setVisible(true)`?

**Cuando termines, mandame** una captura de la ventana abierta y tus respuestas.

---

[← Lección 8](leccion-08.md) · [Índice](README.md) · [Lección 10 →](leccion-10.md)

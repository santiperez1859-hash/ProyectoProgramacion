# Lección 10 · Componentes del formulario

Hoy armás el formulario de la izquierda: etiquetas, campos de texto, el desplegable del tipo, el selector de valoración y los cuatro botones. Todavía no hacen nada; en la Lección 11 les damos vida. Tiempo estimado: 2 horas.

## 1. Los componentes que vamos a usar

Son los del material «2 Componentes» del profe:

| Componente | Para qué sirve | En el formulario |
| --- | --- | --- |
| `JLabel` | Texto fijo, una etiqueta | «Título:», «Autor:», el tiempo de prueba |
| `JTextField` | Campo donde el usuario escribe | Título, autor, año, dato extra |
| `JComboBox` | Lista desplegable | El tipo: Libro, Artículo o Tesis |
| `JSpinner` | Número con flechitas | La valoración, de 1 a 5 |
| `JButton` | Botón | Agregar, Modificar, Eliminar, Limpiar |

## 2. Cómo agregar un componente con WindowBuilder

1. En **Design**, elegí el componente en la Palette (sección Components) y hacé clic en la ventana donde lo querés.
2. En **Properties**: **Variable** es el nombre (por ejemplo `txtTitulo`) y **text** es el texto que muestra, en etiquetas y botones.
3. Para ubicarlo exacto, en Source cambiá los números de su `setBounds(x, y, ancho, alto)`.

Si WindowBuilder agregó líneas `setColumns(10)` en los campos de texto, podés borrarlas: con layout absoluto no cambian nada.

## 3. Variables locales y atributos

Esto es clave. WindowBuilder suele crear las etiquetas y los botones como **variables locales** del constructor:

```java
JButton btnAgregar = new JButton("Agregar");
```

Una variable local solo existe adentro del constructor: después no la podés usar en otros métodos. Los componentes que vamos a leer o cambiar más adelante (campos, desplegable, spinner, botones, la etiqueta del dato extra y la del tiempo) tienen que ser **atributos** de la ventana.

Para convertirlo: seleccioná el componente en Design y, en Properties, tocá el botón **Convert local to field**. O hacelo a mano: declará `private JButton btnAgregar;` arriba, con los otros atributos, y en el constructor sacale el tipo: `btnAgregar = new JButton("Agregar");`.

Las etiquetas fijas («Tipo:», «Título:»…) pueden quedar locales, porque nunca las vamos a tocar.

## 4. La lista de componentes

Armá estos, con estos nombres y posiciones:

| Componente | Variable | Texto | setBounds |
| --- | --- | --- | --- |
| JLabel | `lblTipo` (local) | Tipo: | 20, 20, 90, 25 |
| JComboBox | `cmbTipo` |  | 110, 20, 180, 25 |
| JLabel | `lblTitulo` (local) | Título: | 20, 60, 90, 25 |
| JTextField | `txtTitulo` |  | 110, 60, 180, 25 |
| JLabel | `lblAutor` (local) | Autor: | 20, 100, 90, 25 |
| JTextField | `txtAutor` |  | 110, 100, 180, 25 |
| JLabel | `lblAnio` (local) | Año: | 20, 140, 90, 25 |
| JTextField | `txtAnio` |  | 110, 140, 180, 25 |
| JLabel | `lblValoracion` (local) | Valoración: | 20, 180, 90, 25 |
| JSpinner | `spnValoracion` |  | 110, 180, 60, 25 |
| JLabel | `lblExtra` | Editorial: | 20, 220, 90, 25 |
| JTextField | `txtExtra` |  | 110, 220, 180, 25 |
| JButton | `btnAgregar` | Agregar | 20, 270, 130, 30 |
| JButton | `btnModificar` | Modificar | 160, 270, 130, 30 |
| JButton | `btnEliminar` | Eliminar | 20, 310, 130, 30 |
| JButton | `btnLimpiar` | Limpiar | 160, 310, 130, 30 |
| JLabel | `lblTiempo` | (vacío) | 20, 440, 280, 25 |

Los prefijos dicen qué es cada cosa: `lbl` es label, `txt` campo de texto, `cmb` combo, `spn` spinner y `btn` botón. Al leer `btnAgregar` ya sabés qué es: es parte de la legibilidad que pide el profe.

## 5. El desplegable y el spinner

Las opciones del tipo se cargan con `addItem`, justo después de crear el desplegable:

```java
cmbTipo = new JComboBox<>();
cmbTipo.addItem("Libro");
cmbTipo.addItem("Artículo");
cmbTipo.addItem("Tesis");
cmbTipo.setBounds(110, 20, 180, 25);
contentPane.add(cmbTipo);
```

`JComboBox<String>` significa «un desplegable de textos». Si WindowBuilder escribió `JComboBox` sin `<String>`, agregáselo en la declaración del atributo y usá `new JComboBox<>()` al crearlo.

El spinner necesita un modelo que diga su rango:

```java
spnValoracion = new JSpinner();
spnValoracion.setModel(new SpinnerNumberModel(3, 1, 5, 1));
```

Los cuatro números son: valor inicial 3, mínimo 1, máximo 5, de a 1. Así nadie puede poner una valoración de 8: es una validación sin `if`.

## 6. Probar

Ejecutá `principal`: tiene que verse el formulario a la izquierda, con la mitad derecha todavía vacía. Podés escribir en los campos y tocar los botones, pero no pasa nada: eso viene en la próxima lección.

## 7. Punto de control

Así tiene que quedar `VentanaPrincipal.java`:

```java
package proyecto;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> cmbTipo;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtAnio;
    private JSpinner spnValoracion;
    private JLabel lblExtra;
    private JTextField txtExtra;
    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JLabel lblTiempo;

    public VentanaPrincipal() {
        setTitle("Mi Biblioteca");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 520);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setBounds(20, 20, 90, 25);
        contentPane.add(lblTipo);

        cmbTipo = new JComboBox<>();
        cmbTipo.addItem("Libro");
        cmbTipo.addItem("Artículo");
        cmbTipo.addItem("Tesis");
        cmbTipo.setBounds(110, 20, 180, 25);
        contentPane.add(cmbTipo);

        JLabel lblTitulo = new JLabel("Título:");
        lblTitulo.setBounds(20, 60, 90, 25);
        contentPane.add(lblTitulo);

        txtTitulo = new JTextField();
        txtTitulo.setBounds(110, 60, 180, 25);
        contentPane.add(txtTitulo);

        JLabel lblAutor = new JLabel("Autor:");
        lblAutor.setBounds(20, 100, 90, 25);
        contentPane.add(lblAutor);

        txtAutor = new JTextField();
        txtAutor.setBounds(110, 100, 180, 25);
        contentPane.add(txtAutor);

        JLabel lblAnio = new JLabel("Año:");
        lblAnio.setBounds(20, 140, 90, 25);
        contentPane.add(lblAnio);

        txtAnio = new JTextField();
        txtAnio.setBounds(110, 140, 180, 25);
        contentPane.add(txtAnio);

        JLabel lblValoracion = new JLabel("Valoración:");
        lblValoracion.setBounds(20, 180, 90, 25);
        contentPane.add(lblValoracion);

        spnValoracion = new JSpinner();
        spnValoracion.setModel(new SpinnerNumberModel(3, 1, 5, 1));
        spnValoracion.setBounds(110, 180, 60, 25);
        contentPane.add(spnValoracion);

        lblExtra = new JLabel("Editorial:");
        lblExtra.setBounds(20, 220, 90, 25);
        contentPane.add(lblExtra);

        txtExtra = new JTextField();
        txtExtra.setBounds(110, 220, 180, 25);
        contentPane.add(txtExtra);

        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(20, 270, 130, 30);
        contentPane.add(btnAgregar);

        btnModificar = new JButton("Modificar");
        btnModificar.setBounds(160, 270, 130, 30);
        contentPane.add(btnModificar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(20, 310, 130, 30);
        contentPane.add(btnEliminar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(160, 310, 130, 30);
        contentPane.add(btnLimpiar);

        lblTiempo = new JLabel("");
        lblTiempo.setBounds(20, 440, 280, 25);
        contentPane.add(lblTiempo);
    }
}
```

## 8. Ejercicios

- [ ] Cambiá la fuente de una etiqueta desde Properties (propiedad font) y mirá el código que se genera.
- [ ] Agregá una opción «Revista» al desplegable, ejecutá y después sacala: la vamos a agregar bien en la semana de defensa.
- [ ] En Design, seleccioná `txtTitulo` y mirá en Properties su Variable y sus bounds: tienen que coincidir con la tabla.

## 9. Preguntas tipo defensa

1. ¿Para qué sirve cada componente del formulario?
2. ¿Qué diferencia hay entre una variable local y un atributo? ¿Por qué los botones tienen que ser atributos?
3. ¿Qué significan los cuatro números de `setBounds`?
4. ¿Qué significan los cuatro números de `SpinnerNumberModel(3, 1, 5, 1)`?
5. ¿Por qué los nombres empiezan con `btn`, `txt` o `lbl`?

**Cuando termines, mandame** una captura de la ventana y tus respuestas.

---

[← Lección 9](leccion-09.md) · [Índice](README.md) · [Lección 11 →](leccion-11.md)

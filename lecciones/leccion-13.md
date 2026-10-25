# Lección 13 · Seleccionar, modificar y eliminar

Hoy completás el «actualizar y borrar» de la consigna: hacés clic en una fila, el documento aparece en el formulario, y desde ahí lo modificás o lo eliminás. Tiempo estimado: 2 horas.

## 1. La idea: el documento seleccionado

Vamos a tener un atributo `seleccionado` con el documento que está cargado en el formulario:

- Clic en una fila: se llenan `seleccionado` y el formulario.
- Modificar o Eliminar: actúan sobre `seleccionado`.
- Limpiar: `seleccionado` vuelve a `null`, o sea, ninguno.

## 2. Escuchar el clic en la tabla: MouseListener

Como en el material «5 MouseListener» del profe:

1. Agregá la segunda interfaz, separada por coma: `public class VentanaPrincipal extends JFrame implements ActionListener, MouseListener {`
2. **Ctrl + Shift + O** para el import y **Ctrl + 1 → Add unimplemented methods**. Aparecen cinco métodos.
3. Conectá la tabla en el constructor, después del `setSelectionMode`: `tabla.addMouseListener(this);`

Los cinco métodos son obligatorios, porque la interfaz los exige, pero solo usamos `mouseClicked`; los otros cuatro quedan vacíos:

```java
@Override
public void mouseClicked(MouseEvent e) {
    if (e.getSource() == tabla) {
        cargarSeleccionado();
    }
}

@Override
public void mousePressed(MouseEvent e) {
}

@Override
public void mouseReleased(MouseEvent e) {
}

@Override
public void mouseEntered(MouseEvent e) {
}

@Override
public void mouseExited(MouseEvent e) {
}
```

| Método | Cuándo se dispara |
| --- | --- |
| `mouseClicked` | Un clic completo: apretar y soltar |
| `mousePressed` | Apenas apretás el botón del mouse |
| `mouseReleased` | Cuando lo soltás |
| `mouseEntered` | Cuando el puntero entra al componente |
| `mouseExited` | Cuando el puntero sale |

## 3. Cargar el seleccionado

Sumá el atributo `private Documento seleccionado;` (debajo de `mostrados`) y este método:

```java
private void cargarSeleccionado() {
    int fila = tabla.getSelectedRow();
    if (fila == -1) {
        return;
    }
    seleccionado = mostrados.get(fila);
    cmbTipo.setSelectedItem(seleccionado.getTipo());
    cmbTipo.setEnabled(false);
    txtTitulo.setText(seleccionado.getTitulo());
    txtAutor.setText(seleccionado.getAutor());
    txtAnio.setText(String.valueOf(seleccionado.getAnio()));
    spnValoracion.setValue(seleccionado.getValoracion());
    txtExtra.setText(seleccionado.getDatoExtra());
}
```

- `tabla.getSelectedRow()` devuelve el número de la fila elegida, o `-1` si no hay ninguna. Por eso el `if`: sin él, `mostrados.get(-1)` rompería el programa (Lección 8).
- `mostrados.get(fila)`: la fila 3 de la tabla es el documento 3 de `mostrados`, aunque haya un filtro puesto. Si usáramos la lista completa de la biblioteca, con un filtro activo agarraríamos el documento equivocado.
- `cmbTipo.setEnabled(false)`: mientras editás, el tipo queda bloqueado. Un `Libro` no puede convertirse en `Tesis`: son clases distintas.
- `String.valueOf(...)` pasa el año a texto para mostrarlo en el campo (Lección 8).

## 4. Modificar

```java
private void modificarDocumento() {
    if (seleccionado == null) {
        JOptionPane.showMessageDialog(this, "Primero hacé clic en un documento de la tabla.");
        return;
    }
    if (!formularioValido()) {
        return;
    }
    seleccionado.setTitulo(txtTitulo.getText().trim());
    seleccionado.setAutor(txtAutor.getText().trim());
    seleccionado.setAnio(Integer.parseInt(txtAnio.getText().trim()));
    seleccionado.setValoracion((int) spnValoracion.getValue());
    seleccionado.setDatoExtra(txtExtra.getText().trim());
    actualizarTabla();
    limpiarFormulario();
}
```

Valida lo mismo que Agregar y cambia los datos con los **setters** de la Lección 5. No crea un documento nuevo: modifica el mismo objeto que está en la lista, por eso alcanza con refrescar la tabla. `setDatoExtra` cambia la editorial, la revista o la universidad según el tipo: es el polimorfismo de la Lección 6 trabajando.

## 5. Eliminar, con confirmación

```java
private void eliminarDocumento() {
    if (seleccionado == null) {
        JOptionPane.showMessageDialog(this, "Primero hacé clic en un documento de la tabla.");
        return;
    }
    int respuesta = JOptionPane.showConfirmDialog(this, "¿Borrar " + seleccionado.getTitulo() + "?", "Eliminar",
            JOptionPane.YES_NO_OPTION);
    if (respuesta == JOptionPane.YES_OPTION) {
        biblioteca.eliminar(seleccionado);
        actualizarTabla();
        limpiarFormulario();
    }
}
```

`showConfirmDialog` muestra un cartel con Sí y No y devuelve qué se tocó. Solo si es `YES_OPTION` se borra: así nadie borra algo por error, otra de las situaciones excepcionales.

## 6. Conectar los botones y completar Limpiar

- En el constructor: `btnModificar.addActionListener(this);` y `btnEliminar.addActionListener(this);`.
- En `actionPerformed`, dos ramas nuevas debajo de la de Agregar:

```java
} else if (e.getSource() == btnModificar) {
    modificarDocumento();
} else if (e.getSource() == btnEliminar) {
    eliminarDocumento();
```

- `limpiarFormulario` ahora también suelta el seleccionado, saca la selección de la tabla y desbloquea el tipo:

```java
private void limpiarFormulario() {
    seleccionado = null;
    txtTitulo.setText("");
    txtAutor.setText("");
    txtAnio.setText("");
    spnValoracion.setValue(3);
    txtExtra.setText("");
    tabla.clearSelection();
    cmbTipo.setEnabled(true);
}
```

## 7. Probar

1. Agregá dos documentos. Clic en uno: se carga en el formulario y el tipo queda gris.
2. Cambiá el título y tocá Modificar: la tabla se actualiza y el formulario se limpia.
3. Clic en otro, Eliminar, respondé No: no pasa nada. Repetí y respondé Sí: desaparece.
4. Sin seleccionar nada, tocá Modificar: tiene que avisar.
5. Filtrá por un tipo, seleccioná una fila y eliminala: se tiene que borrar ese documento y no otro.

## 8. Punto de control

Así tiene que quedar `VentanaPrincipal.java`:

```java
package proyecto;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class VentanaPrincipal extends JFrame implements ActionListener, MouseListener {

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
    private JComboBox<String> cmbFiltro;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTable tabla;
    private DefaultTableModel modelo;

    private Biblioteca biblioteca;
    private ArrayList<Documento> mostrados;
    private Documento seleccionado;

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
        cmbTipo.addActionListener(this);
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
        btnAgregar.addActionListener(this);
        contentPane.add(btnAgregar);

        btnModificar = new JButton("Modificar");
        btnModificar.setBounds(160, 270, 130, 30);
        btnModificar.addActionListener(this);
        contentPane.add(btnModificar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(20, 310, 130, 30);
        btnEliminar.addActionListener(this);
        contentPane.add(btnEliminar);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(160, 310, 130, 30);
        btnLimpiar.addActionListener(this);
        contentPane.add(btnLimpiar);

        lblTiempo = new JLabel("");
        lblTiempo.setBounds(20, 440, 280, 25);
        contentPane.add(lblTiempo);

        JLabel lblFiltro = new JLabel("Mostrar:");
        lblFiltro.setBounds(320, 20, 60, 25);
        contentPane.add(lblFiltro);

        cmbFiltro = new JComboBox<>();
        cmbFiltro.addItem("Todos");
        cmbFiltro.addItem("Libro");
        cmbFiltro.addItem("Artículo");
        cmbFiltro.addItem("Tesis");
        cmbFiltro.setBounds(380, 20, 120, 25);
        cmbFiltro.addActionListener(this);
        contentPane.add(cmbFiltro);

        JLabel lblBuscar = new JLabel("Buscar título:");
        lblBuscar.setBounds(520, 20, 90, 25);
        contentPane.add(lblBuscar);

        txtBuscar = new JTextField();
        txtBuscar.setBounds(610, 20, 160, 25);
        contentPane.add(txtBuscar);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(780, 20, 90, 25);
        btnBuscar.addActionListener(this);
        contentPane.add(btnBuscar);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(320, 60, 550, 405);
        contentPane.add(scrollPane);

        modelo = new DefaultTableModel();
        modelo.addColumn("Tipo");
        modelo.addColumn("Título");
        modelo.addColumn("Autor");
        modelo.addColumn("Año");
        modelo.addColumn("Valoración");
        modelo.addColumn("Dato extra");

        tabla = new JTable();
        tabla.setModel(modelo);
        tabla.setDefaultEditor(Object.class, null);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.addMouseListener(this);
        scrollPane.setViewportView(tabla);
    }

    public void iniciar(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        actualizarTabla();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAgregar) {
            agregarDocumento();
        } else if (e.getSource() == btnModificar) {
            modificarDocumento();
        } else if (e.getSource() == btnEliminar) {
            eliminarDocumento();
        } else if (e.getSource() == btnLimpiar) {
            limpiarFormulario();
        } else if (e.getSource() == btnBuscar || e.getSource() == cmbFiltro) {
            actualizarTabla();
        } else if (e.getSource() == cmbTipo) {
            actualizarEtiquetaExtra();
        }
    }

    private void agregarDocumento() {
        if (!formularioValido()) {
            return;
        }
        String titulo = txtTitulo.getText().trim();
        String autor = txtAutor.getText().trim();
        int anio = Integer.parseInt(txtAnio.getText().trim());
        int valoracion = (int) spnValoracion.getValue();
        String extra = txtExtra.getText().trim();
        String tipo = (String) cmbTipo.getSelectedItem();

        Documento nuevo;
        if (tipo.equals("Libro")) {
            nuevo = new Libro(titulo, autor, anio, valoracion, extra);
        } else if (tipo.equals("Artículo")) {
            nuevo = new Articulo(titulo, autor, anio, valoracion, extra);
        } else {
            nuevo = new Tesis(titulo, autor, anio, valoracion, extra);
        }
        biblioteca.agregar(nuevo);
        actualizarTabla();
        limpiarFormulario();
    }

    private void modificarDocumento() {
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, "Primero hacé clic en un documento de la tabla.");
            return;
        }
        if (!formularioValido()) {
            return;
        }
        seleccionado.setTitulo(txtTitulo.getText().trim());
        seleccionado.setAutor(txtAutor.getText().trim());
        seleccionado.setAnio(Integer.parseInt(txtAnio.getText().trim()));
        seleccionado.setValoracion((int) spnValoracion.getValue());
        seleccionado.setDatoExtra(txtExtra.getText().trim());
        actualizarTabla();
        limpiarFormulario();
    }

    private void eliminarDocumento() {
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, "Primero hacé clic en un documento de la tabla.");
            return;
        }
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Borrar " + seleccionado.getTitulo() + "?", "Eliminar",
                JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            biblioteca.eliminar(seleccionado);
            actualizarTabla();
            limpiarFormulario();
        }
    }

    private boolean formularioValido() {
        if (txtTitulo.getText().trim().isEmpty() || txtAutor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El título y el autor son obligatorios.");
            return false;
        }
        try {
            int anio = Integer.parseInt(txtAnio.getText().trim());
            if (anio <= 0) {
                JOptionPane.showMessageDialog(this, "El año tiene que ser mayor que 0.");
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El año tiene que ser un número, por ejemplo 2015.");
            return false;
        }
        return true;
    }

    private void cargarSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            return;
        }
        seleccionado = mostrados.get(fila);
        cmbTipo.setSelectedItem(seleccionado.getTipo());
        cmbTipo.setEnabled(false);
        txtTitulo.setText(seleccionado.getTitulo());
        txtAutor.setText(seleccionado.getAutor());
        txtAnio.setText(String.valueOf(seleccionado.getAnio()));
        spnValoracion.setValue(seleccionado.getValoracion());
        txtExtra.setText(seleccionado.getDatoExtra());
    }

    private void limpiarFormulario() {
        seleccionado = null;
        txtTitulo.setText("");
        txtAutor.setText("");
        txtAnio.setText("");
        spnValoracion.setValue(3);
        txtExtra.setText("");
        tabla.clearSelection();
        cmbTipo.setEnabled(true);
    }

    private void actualizarEtiquetaExtra() {
        String tipo = (String) cmbTipo.getSelectedItem();
        if (tipo.equals("Libro")) {
            lblExtra.setText("Editorial:");
        } else if (tipo.equals("Artículo")) {
            lblExtra.setText("Revista:");
        } else {
            lblExtra.setText("Universidad:");
        }
    }

    private void actualizarTabla() {
        String tipo = (String) cmbFiltro.getSelectedItem();
        String texto = txtBuscar.getText().trim();
        mostrados = biblioteca.buscar(tipo, texto);
        modelo.setRowCount(0);
        for (int i = 0; i < mostrados.size(); i++) {
            Documento d = mostrados.get(i);
            modelo.addRow(new Object[] { d.getTipo(), d.getTitulo(), d.getAutor(), d.getAnio(), d.getValoracion(),
                    d.getDatoExtra() });
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == tabla) {
            cargarSeleccionado();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
```

## 9. Ejercicios

- [ ] Hacé que Eliminar muestre «Documento eliminado» después de borrar.
- [ ] Cambiá un momento `mostrados.get(fila)` por `biblioteca.buscar("Todos", "").get(fila)`, filtrá por Tesis y modificá una: ¿qué documento cambió? Volvé a dejarlo como estaba y explicá en el cuaderno por qué pasó.
- [ ] Explicá con tus palabras por qué el tipo se bloquea mientras editás.

## 10. Preguntas tipo defensa

1. ¿Cómo sabe el programa qué documento modificar?
2. ¿Qué devuelve `getSelectedRow()` si no hay fila seleccionada? ¿Cómo lo manejás?
3. ¿Por qué se usa `mostrados` y no la lista completa?
4. ¿Por qué modificar usa setters en lugar de crear un documento nuevo?
5. ¿Por qué `MouseListener` tiene cinco métodos si usás uno?
6. ¿Qué pasa si respondés No al eliminar?

**Cuando termines, mandame** `VentanaPrincipal` y tus respuestas.

---

[← Lección 12](leccion-12.md) · [Índice](README.md) · [Lección 14 →](leccion-14.md)

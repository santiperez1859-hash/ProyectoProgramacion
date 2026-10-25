# Código completo

Este es el programa terminado y probado: 8 clases, todas en el paquete `proyecto`. Las lecciones explican este mismo código, parte por parte; usalo para tenerlo andando y para comparar lo tuyo.

Ojo: en la defensa el profe pregunta y pide cambios en vivo. Lo que te salva es poder explicar cada línea.

Los mismos 8 archivos están sueltos en [`src/proyecto`](src/proyecto/), por si preferís abrirlos de a uno.

## Cómo pegarlo en Eclipse

1. Tené creado el proyecto `ProyectoBiblioteca` con el paquete `proyecto` (Lección 1). Si existe `module-info.java`, borralo.
2. Para cada clase de abajo, en este orden: click derecho sobre el paquete `proyecto` → **New → Class** → en **Name** escribí el nombre exacto del título, con sus mayúsculas y sin `.java` → **Finish**.
3. En el archivo que se abre: **Ctrl + A**, pegá el código de esa clase y guardá con **Ctrl + S**. El código ya trae su línea `package proyecto;`, así que no la dupliques.
4. `principal` ya existe desde la Lección 1: abrila, **Ctrl + A** y pegá encima.
5. Mientras falten clases vas a ver cruces rojas; desaparecen cuando están las 8.
6. Abrí `principal` y ejecutá con **Ctrl + F11**.

Si algo no compila:

- Revisá que cada archivo se llame igual que su clase: `Documento.java` tiene `public class Documento`.
- Probá **Project → Clean…** y volvé a ejecutar.
- Si te falta algún `import`, apretá **Ctrl + Shift + O**. El `Timer` correcto es `javax.swing.Timer`.

Datos y prueba:

- Los datos se guardan en `biblioteca.bin`, en la carpeta del proyecto (apretá F5 sobre el proyecto para verlo).
- Para reiniciar los 30 minutos de prueba, cerrá el programa y borrá ese archivo. Borralo también antes de entregar, así el profe arranca con la prueba completa.
- Para ver la ventana en modo diseño: click derecho sobre `VentanaPrincipal.java` → **Open With → WindowBuilder Editor** → pestaña **Design**.

## 1. Documento.java

```java
package proyecto;

import java.io.Serializable;

public class Documento implements Serializable {

    private static final long serialVersionUID = 1L;
    private String titulo;
    private String autor;
    private int anio;
    private int valoracion;

    public Documento(String titulo, String autor, int anio, int valoracion) {
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.valoracion = valoracion;
    }

    public String getTipo() {
        return "Documento";
    }

    public String getDatoExtra() {
        return "";
    }

    public void setDatoExtra(String datoExtra) {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }
}
```

## 2. Libro.java

```java
package proyecto;

public class Libro extends Documento {

    private static final long serialVersionUID = 1L;
    private String editorial;

    public Libro(String titulo, String autor, int anio, int valoracion, String editorial) {
        super(titulo, autor, anio, valoracion);
        this.editorial = editorial;
    }

    @Override
    public String getTipo() {
        return "Libro";
    }

    @Override
    public String getDatoExtra() {
        return editorial;
    }

    @Override
    public void setDatoExtra(String datoExtra) {
        this.editorial = datoExtra;
    }
}
```

## 3. Articulo.java

```java
package proyecto;

public class Articulo extends Documento {

    private static final long serialVersionUID = 1L;
    private String revista;

    public Articulo(String titulo, String autor, int anio, int valoracion, String revista) {
        super(titulo, autor, anio, valoracion);
        this.revista = revista;
    }

    @Override
    public String getTipo() {
        return "Artículo";
    }

    @Override
    public String getDatoExtra() {
        return revista;
    }

    @Override
    public void setDatoExtra(String datoExtra) {
        this.revista = datoExtra;
    }
}
```

## 4. Tesis.java

```java
package proyecto;

public class Tesis extends Documento {

    private static final long serialVersionUID = 1L;
    private String universidad;

    public Tesis(String titulo, String autor, int anio, int valoracion, String universidad) {
        super(titulo, autor, anio, valoracion);
        this.universidad = universidad;
    }

    @Override
    public String getTipo() {
        return "Tesis";
    }

    @Override
    public String getDatoExtra() {
        return universidad;
    }

    @Override
    public void setDatoExtra(String datoExtra) {
        this.universidad = datoExtra;
    }
}
```

## 5. Biblioteca.java

```java
package proyecto;

import java.io.Serializable;
import java.util.ArrayList;

public class Biblioteca implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final int LIMITE_SEGUNDOS = 30 * 60;
    private ArrayList<Documento> documentos;
    private int segundosUsados;

    public Biblioteca() {
        this.documentos = new ArrayList<>();
        this.segundosUsados = 0;
    }

    public void agregar(Documento d) {
        documentos.add(d);
    }

    public void eliminar(Documento d) {
        documentos.remove(d);
    }

    public ArrayList<Documento> buscar(String tipo, String texto) {
        ArrayList<Documento> resultado = new ArrayList<>();
        for (int i = 0; i < documentos.size(); i++) {
            Documento d = documentos.get(i);
            boolean tipoCoincide = tipo.equals("Todos") || d.getTipo().equals(tipo);
            boolean textoCoincide = d.getTitulo().toLowerCase().contains(texto.toLowerCase());
            if (tipoCoincide && textoCoincide) {
                resultado.add(d);
            }
        }
        return resultado;
    }

    public void sumarSegundo() {
        segundosUsados++;
    }

    public int getSegundosRestantes() {
        return LIMITE_SEGUNDOS - segundosUsados;
    }

    public boolean pruebaTerminada() {
        return segundosUsados >= LIMITE_SEGUNDOS;
    }
}
```

## 6. Archivo.java

```java
package proyecto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Archivo {

    private static final String RUTA = "biblioteca.bin";

    public static void guardar(Biblioteca biblioteca) {
        try {
            FileOutputStream flujoSalidaArchivo = new FileOutputStream(RUTA);
            ObjectOutputStream flujoSalidaObjeto = new ObjectOutputStream(flujoSalidaArchivo);
            flujoSalidaObjeto.writeObject(biblioteca);
            flujoSalidaObjeto.close();
            flujoSalidaArchivo.close();
        } catch (IOException | SecurityException e) {
            e.printStackTrace();
        }
    }

    public static Biblioteca cargar() {
        try {
            FileInputStream flujoEntradaArchivo = new FileInputStream(RUTA);
            ObjectInputStream flujoEntradaObjeto = new ObjectInputStream(flujoEntradaArchivo);
            Biblioteca biblioteca = (Biblioteca) flujoEntradaObjeto.readObject();
            flujoEntradaObjeto.close();
            flujoEntradaArchivo.close();
            return biblioteca;
        } catch (IOException | ClassNotFoundException e) {
            return new Biblioteca();
        }
    }
}
```

## 7. VentanaPrincipal.java

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
import javax.swing.Timer;
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
    private Timer reloj;

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
        reloj = new Timer(1000, this);
        if (biblioteca.pruebaTerminada()) {
            terminarPrueba();
        } else {
            mostrarTiempo();
            reloj.start();
        }
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
        } else if (e.getSource() == reloj) {
            pasarUnSegundo();
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
        guardarYActualizar();
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
        guardarYActualizar();
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
            guardarYActualizar();
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
        if (!biblioteca.pruebaTerminada()) {
            cmbTipo.setEnabled(true);
        }
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

    private void guardarYActualizar() {
        Archivo.guardar(biblioteca);
        actualizarTabla();
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

## 8. principal.java

```java
package proyecto;

public class principal {

    public static void main(String[] args) {
        Biblioteca biblioteca = Archivo.cargar();
        VentanaPrincipal ventana = new VentanaPrincipal();
        ventana.iniciar(biblioteca);
        ventana.setVisible(true);
    }
}
```

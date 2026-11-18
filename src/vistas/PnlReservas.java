/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vistas;
import MODELOS.Huesped;
import MODELOS.Habitacion;
import MODELOS.Reserva;
import controladores.ControladorHotel;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Aaron Diaz
 */
public class PnlReservas extends javax.swing.JPanel {

    /**
     * Creates new form pnlReservas
     */
    
    // Bandera que indica si el panel ya terminó de construirse.
    // Mientras sea false, se permite cambiar el layout; cuando es true, se bloquea.
    private boolean layoutFijo = false;
    private ControladorHotel Controlador;
    
    public PnlReservas() {
        initComponents();
        
        // A partir de aquí, el layout queda bloqueado.
        layoutFijo = true;
        jdtFechaEntrada.addPropertyChangeListener("date", e -> {
            ActualizarDetalleReserva();
        });

        jdtFechaSalida.addPropertyChangeListener("date", e -> {
            ActualizarDetalleReserva();
        });

        cmbHabitación.addActionListener(e -> {
            ActualizarDetalleReserva();
        });
    }
    
    // Ignora el setLayout vacío que FrmHotel genera sobre este panel
    @Override
    public void setLayout(java.awt.LayoutManager mgr) {
        if (!layoutFijo) {
            super.setLayout(mgr);
        }
    }

    public void setControlador(ControladorHotel Controlador) {
       this.Controlador = Controlador;

        CargarHuespedes();
        CargarHabitaciones();
        CargarTabla();

        txtCódigo.setText(Controlador.ObtenerProximoCodigo());
        txtRecepcionista.setText(Controlador.getUsuarioActivo());
    }
    
    private void CargarHuespedes() {
        cmbHuespedes.removeAllItems();

        for (Huesped huesp : Controlador.getHuespedes()) {
            cmbHuespedes.addItem(huesp.getIdentificacion());
        }

        if (cmbHuespedes.getItemCount() > 0) {
            cmbHuespedes.setSelectedIndex(0);
        }
    }
    
    private void CargarHabitaciones() {
       cmbHabitación.removeAllItems();

        for (Habitacion habi : Controlador.getHabitaciones()) {
            cmbHabitación.addItem(habi.getNumero());
        }
        if (cmbHabitación.getItemCount() > 0) {
            cmbHabitación.setSelectedIndex(0);
        }
    }
    
    private void ActualizarDetalleReserva() {

        txtNoches.setText("");
        txtImporte.setText("");
        txtEstado.setText("");

        if (Controlador == null) {
            return;
        }

        // Obtener las fechas
        Date fechaEntrada = jdtFechaEntrada.getDate();
        Date fechaSalida = jdtFechaSalida.getDate();

        // Obtener habitación seleccionada
        String numero = (String) cmbHabitación.getSelectedItem();

        if (fechaEntrada == null || fechaSalida == null || numero == null) {
            return;
        }

        LocalDate entrada = fechaEntrada.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDate salida = fechaSalida.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();


        if (!salida.isAfter(entrada)) {
            return;
        }

        Habitacion habitacionSeleccionada = null;

        for (Habitacion habi : Controlador.getHabitaciones()) {

            if (habi.getNumero().equals(numero)) {
                habitacionSeleccionada = habi;
                break;
            }
        }

        if (habitacionSeleccionada == null) {
            return;
        }

   
        int noches = Reserva.calcularNoches(entrada, salida);

        long importe = Reserva.calcularImporte(habitacionSeleccionada.getTarifa(), noches);

        txtNoches.setText(String.valueOf(noches));
        txtImporte.setText(Controlador.FormatearTarifa(importe));
        txtEstado.setText("CONFIRMADA");
    }
    
    private void CargarTabla() {

        DefaultTableModel Modelo =
            (DefaultTableModel) tblReservas.getModel();

        Modelo.setRowCount(0);

        for (Reserva reserva : Controlador.getReservas()) {
            
            Modelo.addRow(new Object[]{
                reserva.getCodigo(),
                reserva.getHabitacion().getNumero(),
                reserva.getFechaEntrada(),
                reserva.getFechaSalida(),
                reserva.getEstado()
            });
        }
    }
    public void ActualizarDatos() {

        if (Controlador == null) {
            return;
        }

        CargarHuespedes();
        CargarHabitaciones();
        CargarTabla();
    }
    
        private void LimpiarCampos() {

        txtCódigo.setText(Controlador.ObtenerProximoCodigo());

        cmbHuespedes.setSelectedIndex(-1);
        cmbHabitación.setSelectedIndex(-1);

        txtPersona.setText("");

        jdtFechaEntrada.setDate(null);
        jdtFechaSalida.setDate(null);

        txtNoches.setText("");
        txtImporte.setText("");
        txtEstado.setText("");

        txtRecepcionista.setText(Controlador.getUsuarioActivo());
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlFormulario = new javax.swing.JPanel();
        lblCódigo = new javax.swing.JLabel();
        txtCódigo = new javax.swing.JTextField();
        lblHúesped = new javax.swing.JLabel();
        cmbHuespedes = new javax.swing.JComboBox<>();
        lblHabitación = new javax.swing.JLabel();
        cmbHabitación = new javax.swing.JComboBox<>();
        lblPersona = new javax.swing.JLabel();
        txtPersona = new javax.swing.JTextField();
        lblRecepcionista = new javax.swing.JLabel();
        txtRecepcionista = new javax.swing.JTextField();
        lblFechaEntrada = new javax.swing.JLabel();
        jdtFechaEntrada = new com.toedter.calendar.JDateChooser();
        lblFechaSalida = new javax.swing.JLabel();
        jdtFechaSalida = new com.toedter.calendar.JDateChooser();
        lblNoches = new javax.swing.JLabel();
        txtNoches = new javax.swing.JTextField();
        lblImporte = new javax.swing.JLabel();
        txtImporte = new javax.swing.JTextField();
        lblEstado = new javax.swing.JLabel();
        txtEstado = new javax.swing.JTextField();
        btnRegistrar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblReservas = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(0, 320));
        setLayout(new java.awt.BorderLayout());

        pnlFormulario.setPreferredSize(new java.awt.Dimension(0, 305));

        lblCódigo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblCódigo.setText("Código");

        txtCódigo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtCódigo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)));
        txtCódigo.setEnabled(false);

        lblHúesped.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblHúesped.setText("Huésped");

        cmbHuespedes.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbHuespedes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)));

        lblHabitación.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblHabitación.setText("Habitación");

        cmbHabitación.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbHabitación.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)));

        lblPersona.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblPersona.setText("Personas");

        txtPersona.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtPersona.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)));

        lblRecepcionista.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblRecepcionista.setText("Recepcionista");

        txtRecepcionista.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtRecepcionista.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)));
        txtRecepcionista.setEnabled(false);

        lblFechaEntrada.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblFechaEntrada.setText("Fecha de entrada");

        jdtFechaEntrada.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)));
        jdtFechaEntrada.setDateFormatString("dd/MM/yyyy");
        jdtFechaEntrada.setFocusable(false);
        jdtFechaEntrada.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        lblFechaSalida.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblFechaSalida.setText("Fecha de salida");

        jdtFechaSalida.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)));
        jdtFechaSalida.setDateFormatString("dd/MM/yyyy");
        jdtFechaSalida.setFocusable(false);
        jdtFechaSalida.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        lblNoches.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblNoches.setText("Noches");

        txtNoches.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtNoches.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)));
        txtNoches.setEnabled(false);

        lblImporte.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblImporte.setText("Importe");

        txtImporte.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtImporte.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)));
        txtImporte.setEnabled(false);

        lblEstado.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblEstado.setText("Estado");

        txtEstado.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtEstado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)));
        txtEstado.setEnabled(false);

        btnRegistrar.setBackground(new java.awt.Color(31, 95, 191));
        btnRegistrar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnRegistrar.setForeground(new java.awt.Color(220, 220, 200));
        btnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/add.png"))); // NOI18N
        btnRegistrar.setText(" Registrar");
        btnRegistrar.addActionListener(this::btnRegistrarActionPerformed);

        btnLimpiar.setBackground(new java.awt.Color(204, 204, 204));
        btnLimpiar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnLimpiar.setForeground(new java.awt.Color(0, 0, 0));
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/broom.png"))); // NOI18N
        btnLimpiar.setText(" Limpiar");
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);

        javax.swing.GroupLayout pnlFormularioLayout = new javax.swing.GroupLayout(pnlFormulario);
        pnlFormulario.setLayout(pnlFormularioLayout);
        pnlFormularioLayout.setHorizontalGroup(
            pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormularioLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormularioLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlFormularioLayout.createSequentialGroup()
                        .addComponent(lblCódigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCódigo, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlFormularioLayout.createSequentialGroup()
                        .addComponent(lblHúesped)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbHuespedes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlFormularioLayout.createSequentialGroup()
                        .addComponent(lblHabitación)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbHabitación, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlFormularioLayout.createSequentialGroup()
                        .addComponent(lblPersona)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPersona))
                    .addGroup(pnlFormularioLayout.createSequentialGroup()
                        .addComponent(lblRecepcionista)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRecepcionista)))
                .addGap(201, 201, 201)
                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlFormularioLayout.createSequentialGroup()
                            .addComponent(lblNoches)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtNoches))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlFormularioLayout.createSequentialGroup()
                            .addComponent(lblFechaSalida)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jdtFechaSalida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlFormularioLayout.createSequentialGroup()
                            .addComponent(lblImporte)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtImporte, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlFormularioLayout.createSequentialGroup()
                            .addComponent(lblFechaEntrada)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jdtFechaEntrada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnlFormularioLayout.createSequentialGroup()
                        .addComponent(lblEstado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(463, Short.MAX_VALUE))
        );

        pnlFormularioLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblCódigo, lblHabitación, lblHúesped, lblPersona, lblRecepcionista});

        pnlFormularioLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblEstado, lblFechaEntrada, lblFechaSalida, lblImporte, lblNoches});

        pnlFormularioLayout.setVerticalGroup(
            pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormularioLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCódigo)
                    .addComponent(txtCódigo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFechaEntrada)
                    .addComponent(jdtFechaEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFormularioLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblHúesped)
                            .addComponent(cmbHuespedes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFechaSalida)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormularioLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdtFechaSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNoches, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblHabitación)
                        .addComponent(cmbHabitación, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNoches)))
                .addGap(18, 18, 18)
                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPersona)
                    .addComponent(txtPersona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblImporte)
                    .addComponent(txtImporte, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRecepcionista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRecepcionista)
                    .addComponent(lblEstado)
                    .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        pnlFormularioLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmbHabitación, cmbHuespedes, txtCódigo, txtPersona, txtRecepcionista});

        add(pnlFormulario, java.awt.BorderLayout.PAGE_START);

        tblReservas.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        tblReservas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Código", "Habitación", "Entrada", "Salida", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblReservas.setRowHeight(24);
        jScrollPane2.setViewportView(tblReservas);

        add(jScrollPane2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
         try {

            String identificacion = (String) cmbHuespedes.getSelectedItem();

            Huesped huespedSeleccionado = null;

            for (Huesped huesp : Controlador.getHuespedes()) {
                if (huesp.getIdentificacion().equals(identificacion)) {
                    huespedSeleccionado = huesp;
                    break;
                }
            }

            String numero = (String) cmbHabitación.getSelectedItem();

            Habitacion habitacionSeleccionada = null;

            for (Habitacion habi : Controlador.getHabitaciones()) {
                if (habi.getNumero().equals(numero)) {
                    habitacionSeleccionada = habi;
                    break;
                }
            }

            Date fechaEntrada = jdtFechaEntrada.getDate();
            Date fechaSalida = jdtFechaSalida.getDate();

            LocalDate entrada = fechaEntrada == null ? null :
                fechaEntrada.toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate();

            LocalDate salida = fechaSalida == null ? null :
                fechaSalida.toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate();

            int personas = Integer.parseInt(txtPersona.getText().trim());

            String error = Controlador.RegistrarReserva(huespedSeleccionado,
            habitacionSeleccionada, personas, entrada, salida);

            if (error != null) {
                JOptionPane.showMessageDialog(this, error,"Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, "Reserva registrada correctamente.");

            CargarTabla();
            LimpiarCampos();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(this,"Ingrese una cantidad de personas válida.",
            "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        LimpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JComboBox<String> cmbHabitación;
    private javax.swing.JComboBox<String> cmbHuespedes;
    private javax.swing.JScrollPane jScrollPane2;
    private com.toedter.calendar.JDateChooser jdtFechaEntrada;
    private com.toedter.calendar.JDateChooser jdtFechaSalida;
    private javax.swing.JLabel lblCódigo;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFechaEntrada;
    private javax.swing.JLabel lblFechaSalida;
    private javax.swing.JLabel lblHabitación;
    private javax.swing.JLabel lblHúesped;
    private javax.swing.JLabel lblImporte;
    private javax.swing.JLabel lblNoches;
    private javax.swing.JLabel lblPersona;
    private javax.swing.JLabel lblRecepcionista;
    private javax.swing.JPanel pnlFormulario;
    private javax.swing.JTable tblReservas;
    private javax.swing.JTextField txtCódigo;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtImporte;
    private javax.swing.JTextField txtNoches;
    private javax.swing.JTextField txtPersona;
    private javax.swing.JTextField txtRecepcionista;
    // End of variables declaration//GEN-END:variables
}

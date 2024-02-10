import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Crearusuario extends JFrame {
    private JPanel panel1;
    private JTextField nombrecrear;
    private JTextField cedulacrear;
    private JTextField contraseniacrear;
    private JTextField codigoinsertar;
    private JButton registrarButton;
    private JComboBox rolinsertar;
    private Conexion ventanainicio;

    public Crearusuario(Conexion ventanainicio) {
        super("VentanaRegistro");
        this.ventanainicio = ventanainicio;
        setContentPane(panel1);
        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Conexión a base de datos*/
                String nuevaurl = ventanainicio.getUrl();
                String nuevousuarios = ventanainicio.getUsuarios();
                String nuevacontra = ventanainicio.getContrasenia();

                /*-------------------------------------------------------------*/
                String nombre = nombrecrear.getText();
                String cedula = cedulacrear.getText();
                String codigo = codigoinsertar.getText();
                String contrasenia = contraseniacrear.getText();
                String rol = (rolinsertar.getSelectedItem()).toString();
                if (nombre.isEmpty() || cedula.isEmpty() || codigo.isEmpty() || contrasenia.isEmpty() || rol.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Error de Registro datos vacios");
                } else {
                    String consulta = "INSERT INTO usuarios (nombre,codigo,cedula,contraseña,rol) VALUES(?,?,?,?,?)";
                    try (Connection conexion = DriverManager.getConnection(nuevaurl, nuevousuarios, nuevacontra)) {
                        PreparedStatement sentencia = conexion.prepareStatement(consulta);
                        sentencia.setString(1, nombre);
                        sentencia.setString(2, codigo);
                        sentencia.setString(3, cedula);
                        sentencia.setString(4, contrasenia);
                        sentencia.setString(5, rol);
                        int filasInsertadas = sentencia.executeUpdate();
                        if (filasInsertadas > 0) {
                            JOptionPane.showMessageDialog(null, "Datos insertados con exito");
                            ventanainicio.Inciarconexion();
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "No se ingresaron datos");

                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error de Registro, datos vacios, codigo o cedula de usuario ya registrada", "Error", JOptionPane.ERROR_MESSAGE);

                    }
                }

            }
        });
    }



    public void Iniciarventananueva(){
        setSize(500,500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}

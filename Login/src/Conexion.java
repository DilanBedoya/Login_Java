import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class Conexion extends JFrame {
    private String url;

    public String getUrl() {
        return url;
    }

    private String usuarios;

    public String getUsuarios() {
        return usuarios;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    private String contrasenia;
    private JPanel panel1;
    private JButton conexionButton;
    private JTextField usuariotext;
    private JPasswordField contraseniatext;
    private JButton crearUsuarioButton;


    public Conexion(){
        super("Conexion");
        setContentPane(panel1);
        conexionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                url="jdbc:mysql://localhost:3306/capacitacion";
                usuarios="root";
                contrasenia="123456";
                try(Connection conexion = DriverManager.getConnection(url,usuarios,contrasenia)){
                    String consulta = "SELECT * FROM usuarios WHERE nombre=? AND contraseña=?";
                    PreparedStatement sentencia=conexion.prepareStatement(consulta);
                    sentencia.setString(1,usuariotext.getText());
                    sentencia.setString(2,contraseniatext.getText());
                    ResultSet resultado = sentencia.executeQuery();
                    if (resultado.next()){
                        JOptionPane.showMessageDialog(null,"Inicio de sesión satisfactorio");
                        String visualizar = "SELECT* FROM usuarios";
                        PreparedStatement pedido =conexion.prepareStatement(visualizar);
                        ResultSet respuesta = pedido.executeQuery();
                        StringBuilder tabla = new StringBuilder();
                        tabla.append("Datos de la tabla usuarios:\n");
                        while(respuesta.next()){
                            int id=respuesta.getInt("id");
                            String codigo = respuesta.getString("codigo");
                            String nombre = respuesta.getString("nombre");
                            String cedula = respuesta.getString("cedula");
                            String contraseniatabla = respuesta.getString("contraseña");
                            String rol = respuesta.getString("rol");
                            tabla.append("Id: ").append(id).append(", ");
                            tabla.append("Codigo: ").append(codigo).append(", ");
                            tabla.append("Nombre: ").append(nombre).append(", ");
                            tabla.append("Cedula: ").append(cedula).append(", ");
                            tabla.append("Contraseña: ").append(contraseniatabla).append(", ");
                            tabla.append("Rol: ").append(rol).append("\n");
                        }
                        JOptionPane.showMessageDialog(null,tabla.toString());
                        usuariotext.setText("");
                        contraseniatext.setText("");
                    }else {
                        JOptionPane.showMessageDialog(null,"Usuario o contraseña invalidos");
                        usuariotext.setText("");
                        contraseniatext.setText("");
                    }
                    conexion.close();
                    //JOptionPane.showMessageDialog(null,"Conexion exitosa a la base de datos");
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null,"Conexion defectuosa a la base de datos","Error",JOptionPane.ERROR_MESSAGE);

                }
            }
        });
        crearUsuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Crearusuario ventananueva = new Crearusuario(Conexion.this);
                ventananueva.Iniciarventananueva();
                dispose();
            }
        });
    }

    public void Inciarconexion(){
        setVisible(true);
        setLocationRelativeTo(null);
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args){
        Conexion ventana = new Conexion();
        ventana.Inciarconexion();
    }

}

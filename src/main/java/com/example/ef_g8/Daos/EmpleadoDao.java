package com.example.ef_g8.Daos;
import com.example.ef_g8.Beans.Cine;
import com.example.ef_g8.Beans.Empleado;
import com.example.ef_g8.Beans.Rol;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmpleadoDao extends BaseDao {

    public Empleado validarEmpleadoUsername(String dni) {

        Empleado empleado = null;

        String sql = "Select * from empleado where dni = ?;";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, dni);
            try (ResultSet rs = pstmt.executeQuery();) {
                if (rs.next()) {
                    int idEmpleado = rs.getInt(1);
                    empleado = this.obtenerEmpleado(idEmpleado);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return empleado;
    }


    public Empleado obtenerEmpleado(int idEmpleado) {
        Empleado empleado = null;

        String sql = "Select * from empleado e inner join rolempleado r on (r.idempleado=e.idempleado) inner join rol ro on (ro.idrol=r.idrol) where e.idempleado = ?;";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, idEmpleado);

            try (ResultSet rs = pstmt.executeQuery();) {
                if (rs.next()) {
                    empleado = new Empleado();
                    empleado.setIdEmpleado(idEmpleado);
                    empleado.setNombre(rs.getString(2));
                    empleado.setApellido(rs.getString(3));
                    empleado.setDni(rs.getString(4));
                    empleado.setSalario(rs.getBigDecimal(5));
                    empleado.setFechaContrato(rs.getString(6));
                    empleado.setNombreUsuario(rs.getString(7));
                    empleado.setEdad(rs.getInt(8));
                    empleado.setActivo(rs.getBoolean(9));
                    Empleado jefe = new Empleado();
                    jefe.setIdEmpleado(rs.getInt(11));
                    Cine cine = new Cine();
                    cine.setIdCine(rs.getInt(10));
                    empleado.setCine(cine);
                    empleado.setJefe(jefe);
                    ArrayList<Rol> rol= new ArrayList<Rol>();
                    Rol r=new Rol();
                    r.setIdRol(rs.getInt(12));
                    r.setNombre(rs.getString(15));
                    rol.add(r);
                    empleado.setRoles(rol);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return empleado;
    }

    public boolean validarContrasenia(BigDecimal contrasenia, Empleado emp){
        boolean es=false;
        int count= ((BigDecimal) contrasenia).intValue();
        BigDecimal contraReal=BigDecimal.valueOf(Double.parseDouble(emp.getDni())).subtract(emp.getSalario());
        if(contrasenia==contraReal){
            es=true;
        }
        return es;
    }

}

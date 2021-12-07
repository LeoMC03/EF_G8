package com.example.ef_g8.Daos;
import com.example.ef_g8.Beans.Cine;
import com.example.ef_g8.Beans.Empleado;
import com.example.ef_g8.Daos.BaseDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpleadoDao extends BaseDao {

    public Empleado validarEmpleadoPassword(String dni) {

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

        String sql = "Select * from empleado where idempleado = ?;";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, idEmpleado);

            try (ResultSet rs = pstmt.executeQuery();) {
                if (rs.next()) {
                    empleado = new Empleado();
                    empleado.setIdEmpleado(idEmpleado);
                    empleado.setNombre(rs.getString(1));
                    empleado.setApellido(rs.getString(2));
                    empleado.setDni(rs.getString(3));
                    empleado.setSalario(rs.getBigDecimal(4));
                    empleado.setFechaContrato(rs.getString(5));
                    empleado.setNombreUsuario(rs.getString(6));
                    empleado.setEdad(rs.getInt(7));
                    empleado.setActivo(rs.getBoolean(8));
                    Empleado jefe = new Empleado();
                    jefe.setIdEmpleado(rs.getInt(9));
                    Cine cine = new Cine();
                    cine.setIdCine(rs.getInt(10));
                    empleado.setCine(cine);
                    empleado.setJefe(jefe);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return empleado;
    }
}

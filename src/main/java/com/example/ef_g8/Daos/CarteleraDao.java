package com.example.ef_g8.Daos;

import com.example.ef_g8.Beans.Cadena;
import com.example.ef_g8.Beans.Cartelera;
import com.example.ef_g8.Beans.Cine;
import com.example.ef_g8.Beans.Pelicula;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarteleraDao extends BaseDao {

    public ArrayList<Pelicula> listaPeliculas(){
        ArrayList<Pelicula> listaPeliculas = new ArrayList<>();
        try(Connection conn = this.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM movies.pelicula;");) {
            Pelicula pelicula;
            while(rs.next()){
                pelicula = new Pelicula();
                pelicula.setIdPelicula(rs.getInt(1));
                pelicula.setNombre(rs.getString(2));
                listaPeliculas.add(pelicula);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaPeliculas;
    }

    public ArrayList<Cine> listaCines(){
        ArrayList<Cine> listaCines = new ArrayList<>();
        try(Connection conn = this.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM movies.pelicula;");) {
            Cine cine;
            while(rs.next()){
                cine = new Cine();
                cine.setIdCine(rs.getInt(1));
                cine.setNombre(rs.getString(2));
                listaCines.add(cine);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaCines;
    }

    public ArrayList<Cartelera> listaFuncions (int idempleado){
        ArrayList<Cartelera> listaFunciones = new ArrayList<>();
        String sql="SELECT c.idCartelera,ca.nombre_comercial,ci.nombre,p.nombre,c.3d,c.doblada,c.subtitulada,c.horario FROM cartelera c\n" +
                "inner join cine ci on (c.idcine=ci.idcine)\n" +
                "inner join cadena ca on (ca.idcadena=ci.idcadena)\n" +
                "inner join pelicula p on (p.idpelicula=c.idpelicula) where ci.idcine=7;";
        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, idempleado);
            Cartelera cartelera;
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    cartelera = new Cartelera();
                    cartelera.setIdCartelera(rs.getInt(1));
                    Cadena cadena = new Cadena();
                    cadena.setNombreComercial(rs.getString(2));
                    Cine cine = new Cine();
                    cine.setNombre(rs.getString(3));
                    cine.setCadena(cadena);
                    Pelicula pelicula = new Pelicula();
                    pelicula.setNombre(rs.getString(4));
                    cartelera.setTresD(rs.getInt(5));
                    cartelera.setDoblada(rs.getInt(6));
                    cartelera.setSubtitulada(rs.getInt(7));
                    cartelera.setHorario(rs.getString(8));
                    cartelera.setCine(cine);
                    cartelera.setPelicula(pelicula);
                    listaFunciones.add(cartelera);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listaFunciones;
    }

    public Cartelera obtenerFuncion(int id, int idc){
        Cartelera cartelera= null;
        String sql="SELECT c.idCartelera,ca.nombre_comercial,ci.nombre,p.nombre,c.3d,c.doblada,c.subtitulada,c.horario FROM cartelera c\n" +
                "inner join cine ci on (c.idcine=ci.idcine)\n" +
                "inner join cadena ca on (ca.idcadena=ci.idcadena)\n" +
                "inner join pelicula p on (p.idpelicula=c.idpelicula) where ci.idcine=7 and c.idCartelera=?;";
        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, idc);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    cartelera = new Cartelera();
                    cartelera.setIdCartelera(rs.getInt(1));
                    Cadena cadena = new Cadena();
                    cadena.setNombreComercial(rs.getString(2));
                    Cine cine = new Cine();
                    cine.setNombre(rs.getString(3));
                    cine.setCadena(cadena);
                    Pelicula pelicula = new Pelicula();
                    pelicula.setNombre(rs.getString(4));
                    cartelera.setTresD(rs.getInt(5));
                    cartelera.setDoblada(rs.getInt(6));
                    cartelera.setSubtitulada(rs.getInt(7));
                    cartelera.setHorario(rs.getString(8));
                    cartelera.setCine(cine);
                    cartelera.setPelicula(pelicula);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return cartelera;
    }


    public void borrar(int id) {

        String sql = "DELETE FROM cartelera WHERE idCartelera = ?";
        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            //Logger.getLogger(DepartmentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

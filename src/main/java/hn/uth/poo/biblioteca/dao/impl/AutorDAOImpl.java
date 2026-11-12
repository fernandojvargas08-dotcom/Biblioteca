/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hn.uth.poo.biblioteca.dao.impl;

/**
 *
 * @author ferna
 */


import hn.uth.poo.biblioteca.dao.AutorDAO;
import hn.uth.poo.biblioteca.dao.Conexion;
import hn.uth.poo.biblioteca.excepciones.DatosException;
import hn.uth.poo.biblioteca.modelo.Autor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AutorDAOImpl implements AutorDAO {

    private Autor mapear(ResultSet rs) throws SQLException {

        return new Autor(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("nacionalidad")
        );
    }

    @Override
    public void insertar(Autor autor) {

        String sql = """
            INSERT INTO autores(nombre, nacionalidad)
            VALUES (?, ?)
        """;

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setString(1, autor.getNombre());
            ps.setString(2, autor.getNacionalidad());

            ps.executeUpdate();

            try (Statement st = con.createStatement();
                 ResultSet rs =
                         st.executeQuery("SELECT last_insert_rowid()")) {

                if (rs.next()) {
                    autor.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new DatosException("Error al insertar autor", e);
        }
    }

    @Override
    public Optional<Autor> buscarPorId(int id) {

        String sql = "SELECT * FROM autores WHERE id = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(mapear(rs));
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new DatosException("Error al buscar autor", e);
        }
    }

    @Override
    public List<Autor> listar() {

        List<Autor> autores = new ArrayList<>();

        String sql = "SELECT * FROM autores ORDER BY id";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                autores.add(mapear(rs));
            }

            return autores;

        } catch (SQLException e) {
            throw new DatosException("Error al listar autores", e);
        }
    }

    @Override
    public void actualizar(Autor autor) {

        String sql = """
            UPDATE autores
            SET nombre = ?, nacionalidad = ?
            WHERE id = ?
        """;

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, autor.getNombre());
            ps.setString(2, autor.getNacionalidad());
            ps.setInt(3, autor.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatosException("Error al actualizar autor", e);
        }
    }

    @Override
    public void eliminar(int id) {

        String sql = "DELETE FROM autores WHERE id = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatosException("Error al eliminar autor", e);
        }
    }
}

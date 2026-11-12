/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hn.uth.poo.biblioteca.dao.impl;

/**
 *
 * @author ferna
 */

import hn.uth.poo.biblioteca.dao.LibroDAO;
import hn.uth.poo.biblioteca.dao.Conexion;
import hn.uth.poo.biblioteca.excepciones.DatosException;
import hn.uth.poo.biblioteca.modelo.Autor;
import hn.uth.poo.biblioteca.modelo.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibroDAOImpl implements LibroDAO {

    private Libro mapear(ResultSet rs) throws SQLException {

        Autor autor = new Autor(
                rs.getInt("autor_id"),
                rs.getString("autor_nombre"),
                rs.getString("nacionalidad")
        );

        return new Libro(
                rs.getInt("id"),
                rs.getString("isbn"),
                rs.getString("titulo"),
                rs.getInt("anio"),
                rs.getDouble("precio"),
                autor
        );
    }

    private static final String CONSULTA = """
        SELECT l.id, l.isbn, l.titulo, l.anio,
               l.precio, l.autor_id,
               a.nombre AS autor_nombre,
               a.nacionalidad
        FROM libros l
        INNER JOIN autores a ON l.autor_id = a.id
    """;

    @Override
    public void insertar(Libro libro) {

        String sql = """
            INSERT INTO libros(isbn, titulo, anio, precio, autor_id)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, libro.getIsbn());
            ps.setString(2, libro.getTitulo());
            ps.setInt(3, libro.getAnio());
            ps.setDouble(4, libro.getPrecio());
            ps.setInt(5, libro.getAutor().getId());

            ps.executeUpdate();

            try (Statement st = con.createStatement();
                 ResultSet rs =
                         st.executeQuery("SELECT last_insert_rowid()")) {

                if (rs.next()) {
                    libro.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new DatosException("Error al insertar libro", e);
        }
    }

    @Override
    public Optional<Libro> buscarPorId(int id) {

        String sql = CONSULTA + " WHERE l.id = ?";

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
            throw new DatosException("Error al buscar libro", e);
        }
    }

    @Override
    public List<Libro> listar() {

        List<Libro> libros = new ArrayList<>();

        String sql = CONSULTA + " ORDER BY l.id";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                libros.add(mapear(rs));
            }

            return libros;

        } catch (SQLException e) {
            throw new DatosException("Error al listar libros", e);
        }
    }

    @Override
    public void actualizar(Libro libro) {

        String sql = """
            UPDATE libros
            SET isbn = ?, titulo = ?, anio = ?,
                precio = ?, autor_id = ?
            WHERE id = ?
        """;

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, libro.getIsbn());
            ps.setString(2, libro.getTitulo());
            ps.setInt(3, libro.getAnio());
            ps.setDouble(4, libro.getPrecio());
            ps.setInt(5, libro.getAutor().getId());
            ps.setInt(6, libro.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatosException("Error al actualizar libro", e);
        }
    }

    @Override
    public void eliminar(int id) {

        String sql = "DELETE FROM libros WHERE id = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatosException("Error al eliminar libro", e);
        }
    }

    @Override
    public List<Libro> listarPorAutor(int autorId) {

        List<Libro> libros = new ArrayList<>();

        String sql = CONSULTA + " WHERE l.autor_id = ? ORDER BY l.id";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, autorId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    libros.add(mapear(rs));
                }
            }

            return libros;

        } catch (SQLException e) {
            throw new DatosException(
                    "Error al listar libros del autor", e);
        }
    }
}

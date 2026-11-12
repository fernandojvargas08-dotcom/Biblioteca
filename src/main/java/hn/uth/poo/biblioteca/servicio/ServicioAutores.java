/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hn.uth.poo.biblioteca.servicio;

/**
 *
 * @author ferna
 */

import hn.uth.poo.biblioteca.dao.AutorDAO;
import hn.uth.poo.biblioteca.dao.LibroDAO;
import hn.uth.poo.biblioteca.excepciones.DatoInvalidoException;
import hn.uth.poo.biblioteca.modelo.Autor;

import java.util.List;
import java.util.Optional;

public class ServicioAutores {

    private final AutorDAO autorDAO;
    private final LibroDAO libroDAO;

    public ServicioAutores(AutorDAO autorDAO, LibroDAO libroDAO) {
        this.autorDAO = autorDAO;
        this.libroDAO = libroDAO;
    }

    public void crear(Autor autor) {

        validarAutor(autor);

        autorDAO.insertar(autor);
    }

    public List<Autor> listar() {

        return autorDAO.listar();
    }

    public Optional<Autor> buscarPorId(int id) {

        if (id <= 0) {
            throw new DatoInvalidoException(
                    "El ID debe ser mayor que cero");
        }

        return autorDAO.buscarPorId(id);
    }

    public void actualizar(Autor autor) {

        validarAutor(autor);

        if (!autorDAO.buscarPorId(autor.getId()).isPresent()) {
            throw new DatoInvalidoException(
                    "El autor no existe");
        }

        autorDAO.actualizar(autor);
    }

    public void eliminar(int id) {

        if (id <= 0) {
            throw new DatoInvalidoException(
                    "El ID debe ser mayor que cero");
        }

        if (!autorDAO.buscarPorId(id).isPresent()) {
            throw new DatoInvalidoException(
                    "El autor no existe");
        }

        if (!libroDAO.listarPorAutor(id).isEmpty()) {
            throw new DatoInvalidoException(
                    "No se puede eliminar el autor porque tiene libros registrados");
        }

        autorDAO.eliminar(id);
    }

    private void validarAutor(Autor autor) {

        if (autor == null) {
            throw new DatoInvalidoException(
                    "El autor no puede estar vacío");
        }

        if (autor.getNombre() == null ||
                autor.getNombre().trim().isEmpty()) {

            throw new DatoInvalidoException(
                    "El nombre del autor es obligatorio");
        }
    }
}

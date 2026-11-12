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
import hn.uth.poo.biblioteca.modelo.Libro;

import java.time.Year;
import java.util.List;
import java.util.Optional;

public class ServicioLibros {

    private final LibroDAO libroDAO;
    private final AutorDAO autorDAO;

    public ServicioLibros(LibroDAO libroDAO, AutorDAO autorDAO) {
        this.libroDAO = libroDAO;
        this.autorDAO = autorDAO;
    }

    public void crear(Libro libro) {

        validarLibro(libro);
        validarISBN(libro.getIsbn(), 0);
        validarAutor(libro.getAutor().getId());

        libroDAO.insertar(libro);
    }

    public List<Libro> listar() {

        return libroDAO.listar();
    }

    public Optional<Libro> buscarPorId(int id) {

        if (id <= 0) {
            throw new DatoInvalidoException(
                    "El ID debe ser mayor que cero");
        }

        return libroDAO.buscarPorId(id);
    }

    public void actualizar(Libro libro) {

        validarLibro(libro);

        if (!libroDAO.buscarPorId(libro.getId()).isPresent()) {
            throw new DatoInvalidoException(
                    "El libro no existe");
        }

        validarISBN(libro.getIsbn(), libro.getId());
        validarAutor(libro.getAutor().getId());

        libroDAO.actualizar(libro);
    }

    public void eliminar(int id) {

        if (id <= 0) {
            throw new DatoInvalidoException(
                    "El ID debe ser mayor que cero");
        }

        if (!libroDAO.buscarPorId(id).isPresent()) {
            throw new DatoInvalidoException(
                    "El libro no existe");
        }

        libroDAO.eliminar(id);
    }

    public List<Libro> listarPorAutor(int autorId) {

        validarAutor(autorId);

        return libroDAO.listarPorAutor(autorId);
    }

    private void validarLibro(Libro libro) {

        if (libro == null) {
            throw new DatoInvalidoException(
                    "El libro no puede estar vacío");
        }

        if (libro.getTitulo() == null ||
                libro.getTitulo().trim().isEmpty()) {

            throw new DatoInvalidoException(
                    "El título del libro es obligatorio");
        }

        if (libro.getIsbn() == null ||
                libro.getIsbn().trim().isEmpty()) {

            throw new DatoInvalidoException(
                    "El ISBN es obligatorio");
        }

        int anioActual = Year.now().getValue();

        if (libro.getAnio() < 1450 ||
                libro.getAnio() > anioActual) {

            throw new DatoInvalidoException(
                    "El año debe estar entre 1450 y " + anioActual);
        }

        if (libro.getPrecio() < 0 ||
                !Double.isFinite(libro.getPrecio())) {

            throw new DatoInvalidoException(
                    "El precio debe ser mayor o igual a cero");
        }

        if (libro.getAutor() == null) {
            throw new DatoInvalidoException(
                    "Debe seleccionar un autor");
        }
    }

    private void validarISBN(String isbn, int idActual) {

        for (Libro libro : libroDAO.listar()) {

            if (libro.getIsbn().equalsIgnoreCase(isbn.trim())
                    && libro.getId() != idActual) {

                throw new DatoInvalidoException(
                        "El ISBN ya está registrado");
            }
        }
    }

    private void validarAutor(int autorId) {

        if (autorId <= 0) {
            throw new DatoInvalidoException(
                    "El ID del autor no es válido");
        }

        Optional<Autor> autor = autorDAO.buscarPorId(autorId);

        if (autor.isEmpty()) {
            throw new DatoInvalidoException(
                    "El autor seleccionado no existe");
        }
    }
}

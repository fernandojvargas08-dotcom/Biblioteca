/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hn.uth.poo.biblioteca.dao;

/**
 *
 * @author ferna
 */


import hn.uth.poo.biblioteca.modelo.Libro;
import java.util.List;
import java.util.Optional;

public interface LibroDAO {

    void insertar(Libro libro);

    Optional<Libro> buscarPorId(int id);

    List<Libro> listar();

    void actualizar(Libro libro);

    void eliminar(int id);

    List<Libro> listarPorAutor(int autorId);
}

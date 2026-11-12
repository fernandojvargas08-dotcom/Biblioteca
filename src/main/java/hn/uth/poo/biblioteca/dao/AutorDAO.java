/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hn.uth.poo.biblioteca.dao;

/**
 *
 * @author ferna
 */

import hn.uth.poo.biblioteca.modelo.Autor;
import java.util.List;
import java.util.Optional;

public interface AutorDAO {

    void insertar(Autor autor);

    Optional<Autor> buscarPorId(int id);

    List<Autor> listar();

    void actualizar(Autor autor);

    void eliminar(int id);
}

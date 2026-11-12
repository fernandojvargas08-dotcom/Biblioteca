/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hn.uth.poo.biblioteca.app;

/**
 *
 * @author ferna
 */

import hn.uth.poo.biblioteca.dao.AutorDAO;
import hn.uth.poo.biblioteca.dao.LibroDAO;

import hn.uth.poo.biblioteca.dao.impl.AutorDAOImpl;
import hn.uth.poo.biblioteca.dao.impl.LibroDAOImpl;

import hn.uth.poo.biblioteca.modelo.Autor;
import hn.uth.poo.biblioteca.modelo.Libro;

import hn.uth.poo.biblioteca.servicio.ServicioAutores;
import hn.uth.poo.biblioteca.servicio.ServicioLibros;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    private static ServicioAutores servicioAutores;
    private static ServicioLibros servicioLibros;

    public static void main(String[] args) {

        AutorDAO autorDAO = new AutorDAOImpl();
        LibroDAO libroDAO = new LibroDAOImpl();

        servicioAutores = new ServicioAutores(
                autorDAO, libroDAO);

        servicioLibros = new ServicioLibros(
                libroDAO, autorDAO);

        int opcion;

        do {
            System.out.println("\n==================================");
            System.out.println("       SISTEMA DE BIBLIOTECA");
            System.out.println("==================================");
            System.out.println("1. Gestionar autores");
            System.out.println("2. Gestionar libros");
            System.out.println("0. Salir");
            System.out.println("==================================");

            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {

                case 1:
                    menuAutores();
                    break;

                case 2:
                    menuLibros();
                    break;

                case 0:
                    System.out.println(
                            "Gracias por utilizar Biblioteca.");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);

        sc.close();
    }

    // =====================================
    // MENU DE AUTORES
    // =====================================

    private static void menuAutores() {

        int opcion;

        do {
            System.out.println("\n========== GESTION DE AUTORES ==========");
            System.out.println("1. Crear autor");
            System.out.println("2. Listar autores");
            System.out.println("3. Buscar autor por ID");
            System.out.println("4. Actualizar autor");
            System.out.println("5. Eliminar autor");
            System.out.println("0. Volver al menú principal");

            opcion = leerEntero("Seleccione una opción: ");

            try {

                switch (opcion) {

                    case 1:
                        crearAutor();
                        break;

                    case 2:
                        listarAutores();
                        break;

                    case 3:
                        buscarAutor();
                        break;

                    case 4:
                        actualizarAutor();
                        break;

                    case 5:
                        eliminarAutor();
                        break;

                    case 0:
                        System.out.println("Regresando...");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }

            } catch (RuntimeException e) {
                System.out.println("ERROR: " + e.getMessage());
            }

        } while (opcion != 0);
    }

    private static void crearAutor() {

        System.out.println("\n--- CREAR AUTOR ---");

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Nacionalidad: ");
        String nacionalidad = sc.nextLine();

        Autor autor = new Autor(nombre, nacionalidad);

        servicioAutores.crear(autor);

        System.out.println("Autor registrado correctamente.");
        System.out.println("ID generado: " + autor.getId());
    }

    private static void listarAutores() {

        System.out.println("\n--- LISTA DE AUTORES ---");

        List<Autor> autores = servicioAutores.listar();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
            return;
        }

        for (Autor autor : autores) {
            System.out.println(autor);
        }
    }

    private static void buscarAutor() {

        System.out.println("\n--- BUSCAR AUTOR ---");

        int id = leerEntero("Ingrese el ID del autor: ");

        Optional<Autor> autor =
                servicioAutores.buscarPorId(id);

        if (autor.isPresent()) {
            System.out.println(autor.get());
        } else {
            System.out.println("Autor no encontrado.");
        }
    }

    private static void actualizarAutor() {

        System.out.println("\n--- ACTUALIZAR AUTOR ---");

        int id = leerEntero("ID del autor: ");

        Optional<Autor> resultado =
                servicioAutores.buscarPorId(id);

        if (resultado.isEmpty()) {
            System.out.println("Autor no encontrado.");
            return;
        }

        System.out.print("Nuevo nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Nueva nacionalidad: ");
        String nacionalidad = sc.nextLine();

        Autor autor = new Autor(id, nombre, nacionalidad);

        servicioAutores.actualizar(autor);

        System.out.println("Autor actualizado correctamente.");
    }

    private static void eliminarAutor() {

        System.out.println("\n--- ELIMINAR AUTOR ---");

        int id = leerEntero("ID del autor: ");

        servicioAutores.eliminar(id);

        System.out.println("Autor eliminado correctamente.");
    }

    // =====================================
    // MENU DE LIBROS
    // =====================================

    private static void menuLibros() {

        int opcion;

        do {
            System.out.println("\n========== GESTION DE LIBROS ==========");
            System.out.println("1. Crear libro");
            System.out.println("2. Listar libros");
            System.out.println("3. Buscar libro por ID");
            System.out.println("4. Actualizar libro");
            System.out.println("5. Eliminar libro");
            System.out.println("6. Listar libros por autor");
            System.out.println("0. Volver al menú principal");

            opcion = leerEntero("Seleccione una opción: ");

            try {

                switch (opcion) {

                    case 1:
                        crearLibro();
                        break;

                    case 2:
                        listarLibros();
                        break;

                    case 3:
                        buscarLibro();
                        break;

                    case 4:
                        actualizarLibro();
                        break;

                    case 5:
                        eliminarLibro();
                        break;

                    case 6:
                        listarLibrosPorAutor();
                        break;

                    case 0:
                        System.out.println("Regresando...");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }

            } catch (RuntimeException e) {
                System.out.println("ERROR: " + e.getMessage());
            }

        } while (opcion != 0);
    }

    private static void crearLibro() {

        System.out.println("\n--- CREAR LIBRO ---");

        System.out.print("ISBN: ");
        String isbn = sc.nextLine();

        System.out.print("Título: ");
        String titulo = sc.nextLine();

        int anio = leerEntero("Año de publicación: ");

        double precio = leerDouble("Precio: ");

        listarAutores();

        int autorId = leerEntero("ID del autor: ");

        Optional<Autor> autor =
                servicioAutores.buscarPorId(autorId);

        if (autor.isEmpty()) {
            System.out.println("El autor no existe.");
            return;
        }

        Libro libro = new Libro(
                isbn, titulo, anio, precio, autor.get());

        servicioLibros.crear(libro);

        System.out.println("Libro registrado correctamente.");
        System.out.println("ID generado: " + libro.getId());
    }

    private static void listarLibros() {

        System.out.println("\n--- LISTA DE LIBROS ---");

        List<Libro> libros = servicioLibros.listar();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        for (Libro libro : libros) {
            System.out.println(libro);
        }
    }

    private static void buscarLibro() {

        System.out.println("\n--- BUSCAR LIBRO ---");

        int id = leerEntero("Ingrese el ID del libro: ");

        Optional<Libro> libro =
                servicioLibros.buscarPorId(id);

        if (libro.isPresent()) {
            System.out.println(libro.get());
        } else {
            System.out.println("Libro no encontrado.");
        }
    }

    private static void actualizarLibro() {

        System.out.println("\n--- ACTUALIZAR LIBRO ---");

        int id = leerEntero("ID del libro: ");

        Optional<Libro> resultado =
                servicioLibros.buscarPorId(id);

        if (resultado.isEmpty()) {
            System.out.println("Libro no encontrado.");
            return;
        }

        System.out.print("Nuevo ISBN: ");
        String isbn = sc.nextLine();

        System.out.print("Nuevo título: ");
        String titulo = sc.nextLine();

        int anio = leerEntero("Nuevo año: ");

        double precio = leerDouble("Nuevo precio: ");

        listarAutores();

        int autorId = leerEntero("Nuevo ID del autor: ");

        Optional<Autor> autor =
                servicioAutores.buscarPorId(autorId);

        if (autor.isEmpty()) {
            System.out.println("El autor no existe.");
            return;
        }

        Libro libro = new Libro(
                id, isbn, titulo, anio, precio, autor.get());

        servicioLibros.actualizar(libro);

        System.out.println("Libro actualizado correctamente.");
    }

    private static void eliminarLibro() {

        System.out.println("\n--- ELIMINAR LIBRO ---");

        int id = leerEntero("ID del libro: ");

        servicioLibros.eliminar(id);

        System.out.println("Libro eliminado correctamente.");
    }

    private static void listarLibrosPorAutor() {

        System.out.println("\n--- LIBROS POR AUTOR ---");

        int autorId = leerEntero("ID del autor: ");

        List<Libro> libros =
                servicioLibros.listarPorAutor(autorId);

        if (libros.isEmpty()) {
            System.out.println(
                    "Este autor no tiene libros registrados.");
            return;
        }

        for (Libro libro : libros) {
            System.out.println(libro);
        }
    }

    // =====================================
    // METODOS PARA LEER DATOS
    // =====================================

    private static int leerEntero(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            try {
                return Integer.parseInt(sc.nextLine().trim());

            } catch (NumberFormatException e) {
                System.out.println(
                        "Ingrese un número entero válido.");
            }
        }
    }

    private static double leerDouble(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            try {
                return Double.parseDouble(
                        sc.nextLine().trim().replace(',', '.'));

            } catch (NumberFormatException e) {
                System.out.println(
                        "Ingrese un precio válido.");
            }
        }
    }
}

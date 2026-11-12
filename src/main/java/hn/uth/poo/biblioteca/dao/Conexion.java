/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hn.uth.poo.biblioteca.dao;

/**
 *
 * @author ferna
 */

import hn.uth.poo.biblioteca.excepciones.DatosException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {

    private static final String URL = "jdbc:sqlite:biblioteca.db";

    public static Connection getConnection() {

        Connection conexion = null;

        try {
            conexion = DriverManager.getConnection(URL);

            try (Statement st = conexion.createStatement()) {

                st.execute("PRAGMA foreign_keys = ON");

                st.execute("""
                    CREATE TABLE IF NOT EXISTS autores (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        nombre TEXT NOT NULL,
                        nacionalidad TEXT
                    )
                """);

                st.execute("""
                    CREATE TABLE IF NOT EXISTS libros (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        isbn TEXT NOT NULL UNIQUE,
                        titulo TEXT NOT NULL,
                        anio INTEGER,
                        precio REAL NOT NULL,
                        autor_id INTEGER NOT NULL,
                        FOREIGN KEY (autor_id)
                        REFERENCES autores(id)
                    )
                """);
            }

            return conexion;

        } catch (SQLException e) {

            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException errorCierre) {
                    e.addSuppressed(errorCierre);
                }
            }

            throw new DatosException(
                    "Error al conectar con la base de datos", e);
        }
    }
}

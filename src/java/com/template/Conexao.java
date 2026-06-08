package com.template;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public Connection conectaBD() {
        Connection conexao = null;

        try {
            // FORÇA O CARREGAMENTO DO DRIVER DO POSTGRESQL NA MEMÓRIA
            Class.forName("org.postgresql.Driver");

            String url = "jdbc:postgresql://localhost:5432/postgres";
            String usuario = "postgres";
            String senha = "postgres"; // Altere para a sua senha real

            conexao = DriverManager.getConnection(url, usuario, senha);

        } catch (ClassNotFoundException e) {
            System.err.println("-> ERRO: O driver do PostgreSQL não foi encontrado no projeto!");
            System.err.println("Certifique-se de adicionar o .jar nas bibliotecas.");
        } catch (SQLException e) {
            System.err.println("-> ERRO DE CONEXÃO: Não foi possível conectar ao banco de dados.");
            System.err.println("Detalhes do erro: " + e.getMessage());
        }

        return conexao;
    }
}
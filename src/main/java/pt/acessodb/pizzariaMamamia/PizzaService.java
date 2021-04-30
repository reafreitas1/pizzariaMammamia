/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.acessodb.pizzariaMamamia;

import Modelo.Pizza;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author renatafreitas
 */
@Controller
public class PizzaService {

    @RequestMapping(value = "listarone")//Processa o Java e devolve a page listar.html
    public String getOneItem(Model model, int id) {
        //Ligar a BD depois obter os dados das Pizzas na tabela pizza
        String txt = "SELECT * FROM menu_pizzas WHERE codigo_pizza>" + id;
        Pizza tabela = null;
        //user=root, pass=abcd1234, localhost 127.0.0.1
        try (Connection ligacao = DriverManager.getConnection("jdbc:mariadb://localhost/mammamia_db", "root", "abcd1234")) {

            try (Statement stmt = ligacao.createStatement()) {

                try (ResultSet rs = stmt.executeQuery(txt)) {
                    if (rs.first()) {
                        System.out.println("Pizza" + rs.getString(2));
                        tabela = new Pizza(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4));

                    }
                }

            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Erro: Aceder ao servidor MariaDB em Localhost");
        }

        //o array pizzas
        model.addAttribute("modelo", tabela);
        return "listarone";

    }

    @RequestMapping(value = "adicionar")
    public String insertPizza(Model model) {
        return "adicionar";
    }

    @RequestMapping(value = "guardar")
    public String guardarPizza(Model model, int id) {
        String msg = "erro_update";
        String txt = "UPDATE menu_pizza SET preco=20, descricao='Napolitana', imagem='n.jpg' where codigo_pizza=" + id;

        try (Connection ligacao = DriverManager.getConnection("jdbc:mariadb://localhost/mammamia_db", "root", "abcd1234")) {

            try (Statement stmt = ligacao.createStatement()) {

                int numero = stmt.executeUpdate(txt, id);
                if (numero != 0) {
                    msg = "listar";
                }

            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Erro: Aceder ao servidor MariaDB em Localhost");
          }
        return "selectone";
        
    }

        @RequestMapping(value = "actualizar")
        public String updatePizza (Model model, int id) {
        
        return "erro_update";
        }

        @RequestMapping(value = "apagar")
        public String deletePizza (Model model, int id) {
        try (Connection ligacao = DriverManager.getConnection("jdbc:mariadb://localhost/mammamia_db", "root", "abcd1234")) {

                try (Statement stmt = ligacao.createStatement()) {

                    boolean (int)boolean numero = stmt.execute("DELETE * FROM menu_pizza WHERE codigo_pizza" +id, id);
                        if (numero != 0) {
                        String msg = "listar";
                        }

                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }

            } catch (Exception e) {
                System.out.println("Erro: Aceder ao servidor MariaDB em Localhost");
            }
        return "erro_apagar";
        }

        
    }


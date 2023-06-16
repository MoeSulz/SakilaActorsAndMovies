package Main;
import Models.Actor;
import Models.Film;
import db.SakilaDatabaseManager;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.List;
import java.util.Scanner;


public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args){
        if (args.length != 2) {
            System.out.println(
                    "Application needs two arguments to run: " +
                            "java com.hca.jdbc.UsingDriverManager <username> " +
                            "<password>");
            System.exit(1);
        }
        String username = args[0];
        String password = args[1];

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/sakila");
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        SakilaDatabaseManager sakilaDatabaseManager = new SakilaDatabaseManager(dataSource);
        List<Actor> actors = sakilaDatabaseManager.getActor();
        actors.forEach(System.out::println);

        List<Film> films = sakilaDatabaseManager.getFilm();
        films.forEach(System.out::println);
    }
}

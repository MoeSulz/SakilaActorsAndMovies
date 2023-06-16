package db;

import Models.Actor;
import Models.Film;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SakilaDatabaseManager {
    private DataSource dataSource;
    static Scanner scanner = new Scanner(System.in);

    public SakilaDatabaseManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Actor> getActor() {
        List<Actor> actors = new ArrayList<Actor>();

        System.out.println("Enter an Actor's last name:");
        String enterLastName = scanner.nextLine();
        String query = "SELECT actor_id, first_name, last_name FROM actor WHERE last_name = ?";
        try (Connection connection = dataSource.getConnection();

             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {
            preparedStatement.setString(1, enterLastName);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    int id = resultSet.getInt("actor_id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");

                    Actor actor = new Actor(id, firstName, lastName);
                    actors.add(actor);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return actors;
    }
    public List<Film> getFilm(){
        List<Film> films = new ArrayList<Film>();

        System.out.println("Enter Actor ID:");
        int id = scanner.nextInt();
        String query = "SELECT a.actor_id, f.film_id, f.title, f.description, f.release_year, f.length FROM actor a \n" +
                "JOIN film_actor fa ON a.actor_id = fa.actor_id\n" +
                "JOIN film f ON fa.film_id = f.film_id\n" +
                "WHERE a.actor_id = ?;";
        try(Connection connection = dataSource.getConnection();

            PreparedStatement preparedStatement =
                    connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int filmID = resultSet.getInt("f.film_id");
                    String title = resultSet.getString("f.title");
                    String description = resultSet.getString("f.description");
                    int releaseYear = resultSet.getInt("f.release_year");
                    int length = resultSet.getInt("f.length");

                    Film film = new Film(filmID, title, description, releaseYear, length);
                    films.add(film);

                }
            }
    } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return films;
    }
}


import model.Student;

import java.sql.*;
import java.util.*;

public class Main {
    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String username = "postgres";
    private static final String password = "G85boq18Yh?/-";
    private static final String path = "C:\\Users\\Nikita\\Проекты\\TESTS\\resources\\basicprogramming_2.csv";


    public static void main(String[] args) {
        getStudentsFromDataBase();
    }


    public static void createTableInDataBase() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Соединение установлено..");
            connection.prepareStatement("CREATE SCHEMA IF NOT EXISTS newSchema").execute();
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS newSchema.Students (id SERIAL PRIMARY KEY, name VARCHAR(30) NOT NULL," +
                    "surname VARCHAR(30) NOT NULL, studentGroup VARCHAR(60) NOT NULL, exercise INT NOT NULL, homework INT NOT NULL)").execute();
            System.out.println("База данных создана");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void addStudentsFromCSVtoDataBase(String path) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            ArrayList<Student> students = CSV.getStudents(path);
            for (int i = 0; i < students.size(); i++) {
                try {
                    connection.prepareStatement(String.format("INSERT INTO newSchema.Students VALUES (%d, '%s', '%s', '%s', %d, %d)",
                            i + 1, students.get(i).getName(), students.get(i).getSurname(), students.get(i).getGroup(),
                            students.get(i).getExercise(), students.get(i).getHomework())).execute();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void getStudentsFromDataBase(){
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT name, surname, studentgroup FROM newschema.students");
            ArrayList<Student> arrayList = new ArrayList<>();
            while (set.next()) {
                String name = set.getString("name");
                String surname = set.getString("surname");
                String group = set.getString("studentgroup");
                arrayList.add(new Student(name, surname, group));
            }
            arrayList.forEach(System.out::println);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

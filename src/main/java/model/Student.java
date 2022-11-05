package model;

public class Student implements Comparable{
    private String name;
    private String surname;
    private String group;
    private int exercise;
    private int homework;

    public Student(String name, String surname, String group, int exercise, int homework) {
        this.name = name;
        this.surname = surname;
        this.group = group;
        this.exercise = exercise;
        this.homework = homework;
    }

    public Student(String name, String surname, String group) {
        this.name = name;
        this.surname = surname;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getGroup() {
        return group;
    }

    public int getExercise() {
        return exercise;
    }

    public int getHomework() {
        return homework;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", group='" + group + '\'' +
                ", exercise=" + exercise +
                ", homework=" + homework +
                '}';
    }


    @Override
    public int compareTo(Object o) {
        if (o instanceof Student) {
            Student student = (Student) o;
            return this.surname.compareTo(((Student) o).surname);
        }
        else throw new IllegalArgumentException();
    }
}

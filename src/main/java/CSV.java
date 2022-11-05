import model.Student;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSV {
    public static List<String> strings;

    public static void main(String[] args) {
        ArrayList<Student> students = getStudents("C:\\Users\\Nikita\\Проекты\\TESTS\\resources\\basicprogramming_2.csv");
        students.forEach(student -> System.out.println(student.toString()));
        writeStudentsInFile("C:\\Users\\Nikita\\Проекты\\TESTS\\resources\\res.csv", students);
    }

    public static ArrayList<Student> getStudents(String path) {
        File file = new File(path);
        try {
            strings = Files.readAllLines(file.toPath());
        } catch (IOException exception) {
            System.out.println("Файл не найден: " + exception.getMessage());
        }
        ArrayList<Student> students = new ArrayList<>();
        strings.forEach(s -> {
            String[] fields = s.split(";");
            if (!fields[0].isEmpty()) {
                String[] nameAndSurname = fields[0].split(" +");
                try{
                    if (nameAndSurname.length == 2){
                        students.add(new Student(nameAndSurname[0], nameAndSurname[1], fields[1], Integer.parseInt(fields[3]), Integer.parseInt(fields[4])));
                    }
                    else throw new IllegalArgumentException();
                }
                catch (IllegalArgumentException exception){
                    System.out.println("Возникла ошибка обработки строки: " + s);
                }
            }
        });
        return students;
    }

    public static void writeStudentsInFile(String path, List<Student> students){
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader("SURNAME", "NAME", "GROUP", "EXERCISE", "HOMEWORK").setDelimiter(';').build();
        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(path));
            CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat)){
            students.forEach(student -> {
                try {
                    csvPrinter.printRecord(student.getSurname(), student.getName(), student.getGroup(), student.getExercise(), student.getHomework());
                    csvPrinter.flush();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            });
        }
        catch (IOException exception){
            exception.printStackTrace();
        }
    }
}
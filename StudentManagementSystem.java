import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Student class to represent individual students
class Student implements Serializable {
    private String name;
    private String rollNumber;
    private String grade;

    public Student(String name, String rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getGrade() {
        return grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Roll Number: " + rollNumber + ", Grade: " + grade;
    }
}

// StudentManagementSystem class to manage the collection of students
public class StudentManagementSystem {
    private List<Student> students;
    private static final String FILE_PATH = "students.ser";
    private static final Scanner scanner = new Scanner(System.in);

    public StudentManagementSystem() {
        this.students = loadStudents();
    }

    public void addStudent() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter roll number: ");
        String rollNumber = scanner.nextLine();
        System.out.print("Enter grade: ");
        String grade = scanner.nextLine();

        if (name.isEmpty() || rollNumber.isEmpty() || grade.isEmpty()) {
            System.out.println("All fields are required.");
            return;
        }

        students.add(new Student(name, rollNumber, grade));
        saveStudents();
        System.out.println("Student added successfully.");
    }

    public void removeStudent() {
        System.out.print("Enter roll number of the student to remove: ");
        String rollNumber = scanner.nextLine();
        Student student = findStudentByRollNumber(rollNumber);

        if (student != null) {
            students.remove(student);
            saveStudents();
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    public void searchStudent() {
        System.out.print("Enter roll number of the student to search: ");
        String rollNumber = scanner.nextLine();
        Student student = findStudentByRollNumber(rollNumber);

        if (student != null) {
            System.out.println("Student Found: " + student);
        } else {
            System.out.println("Student not found.");
        }
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students to display.");
        } else {
            System.out.println("=== Student List ===");
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    public void editStudent() {
        System.out.print("Enter roll number of the student to edit: ");
        String rollNumber = scanner.nextLine();
        Student student = findStudentByRollNumber(rollNumber);

        if (student != null) {
            System.out.print("Enter new name (leave blank to keep current): ");
            String name = scanner.nextLine();
            if (!name.isEmpty()) {
                student.setName(name);
            }

            System.out.print("Enter new grade (leave blank to keep current): ");
            String grade = scanner.nextLine();
            if (!grade.isEmpty()) {
                student.setGrade(grade);
            }

            saveStudents();
            System.out.println("Student information updated successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    private Student findStudentByRollNumber(String rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber().equalsIgnoreCase(rollNumber)) {
                return student;
            }
        }
        return null;
    }

    private void saveStudents() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            out.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }

    private List<Student> loadStudents() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (List<Student>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();

        while (true) {
            System.out.println("\n=== Student Management System ===");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Edit Student");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> sms.addStudent();
                case 2 -> sms.removeStudent();
                case 3 -> sms.searchStudent();
                case 4 -> sms.displayAllStudents();
                case 5 -> sms.editStudent();
                case 6 -> {
                    System.out.println("Exiting... Thank you for using the Student Management System!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

/*This is a program demonstrating ArrayLists in Java with a StudentManager Object and an
Academic Class ArrayList

Class Student contains the students' names, IDs and scores for their tests, as well as calculating the average
of their test scores as a percentage.

From there, the StudentManager reads in the input.txt file and populates the fields in the Student Class
and then adds them to the ArrayList.

StudentManager also contains the AddStudent and DeleteStudent functions that allow us to add another student
to the ArrayList to display them, or to remove them using their student ID from the ArrayList.

StudentManager also contains a comparator to sort the percentage scores of the students from largest to smallest
when it is printed out.
*/
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.io.BufferedReader;

import static java.lang.Math.round;

// student class contains student IDs, names and scores and also calculates their percentage using the average of scores
public class Student {
    private String id; // student ID contains 4 characters
    private String name; // Student's name contains 10 characters
    private int score1; // Score for Test 1
    private int score2; // Score for Test 2
    private int score3; // Score for Test 3
    private double percentage; // Percentage score
    private int hours;
    private float GPA;
    private float newGPA;

    public Student(String id, String name, int score1, int score2, int score3, int hours, float GPA) {

        this.id = id;
        this.name = name;
        this.score1 = score1;
        this.score2 = score2;
        this.score3 = score3;
        calculatePercentage();
        this.hours = hours;
        this.GPA = GPA;
        this.newGPA = calculateNewGPA();

    }




    private void calculatePercentage() {
        this.percentage = round((score1 + score2 + score3) / 3.0); // Average of 3 test scores
    }

    public double getPercentage() {
        return percentage;
    }
// this part of the program calculates the newGPA according to the equation given to us for part 2
    public float calculateNewGPA() {
        int currentHours = 2;
        return (((GPA * hours) + (currentHours * newGPA)) / (hours + currentHours));
    }
//this part of the program enables the GPA to be used to calculate the students' letter grades and display them
    private String getLetterGrade() {
        if (GPA >= 4.0) return "A";
        else if (GPA >= 3.0) return "B";
        else if (GPA >= 2.0) return "C";
        else if (GPA >= 1.0) return "D";
        else return "F"; // Simplified logic
    }
    // this part of the program uses the total credit hours the students each have to rank them by their year in college
    public String getYear() {
        if (hours >= 1 && hours <= 30) return "FR"; // Freshman
        else if (hours >= 31 && hours <= 60) return "SO"; // Sophomore
        else if (hours >= 61 && hours <= 90) return "JR"; // Junior
        else return "SR"; // Senior
    }


    public String toString() {
        String letterGrade = getLetterGrade();
        return String.format("ID: %s, Name: %s, Scores: %d, %d, %d, Percentage: %f, Hours: %d, GPA: %.2f, New GPA: %.2f, Letter Grade: %s Year: %s",
                id, name, score1, score2, score3, percentage, hours, GPA, newGPA, letterGrade, getYear());
    }

// now we create our ArrayList, so we can add the functions of adding, removing and sorting the students
    //the StudentManager is now utilizing Generic Objects
    public static class StudentManager <T extends Student> {
        private ArrayList<T> Academic_Class;

        public StudentManager() {
            Academic_Class = new ArrayList<>();
        }
        //This function allows us to add the students later to the ArrayList
        public void addStudent(T student) {
            Academic_Class.add(student);
        }
        //This function allows us to remove the students from the ArrayList via their student IDs
        public void removeStudent(String id) {
            Academic_Class.removeIf(student -> student.getId().equals(id));
        }
        //This function sorts their percentage scores from largest to smallest
        public void sortLarge() {
            Academic_Class.sort(Comparator.comparingDouble(Student::getPercentage).reversed());
        }
        //This functions prints out the list of the students from the input file after we complete the other functions beforehand
        public void printStudents() {
            for (T student : Academic_Class) {
                System.out.println(student);
            } //end of for
        }
    }
    //This function retrieves the student ID, so it can be used by the removeStudent function
    public Object getId() {
        return id;
    }


    public static void main(String[] args) {
        StudentManager manager = new StudentManager();

        // Here we load student records from input.txt to add them to the ArrayList
        try (BufferedReader bufferedreader = new BufferedReader(new FileReader("/C:/users/veget/Documents/input.txt"))) {
            String line;
            while ((line = bufferedreader.readLine()) != null) {
                String[] parts = line.split(",");

                String id = parts[0].trim();
                String name = parts[1].trim();
                int score1 = Integer.parseInt(parts[2].trim());
                int score2 = Integer.parseInt(parts[3].trim());
                int score3 = Integer.parseInt(parts[4].trim());
                int hours = Integer.parseInt(parts[5].trim());
                float GPA = Float.parseFloat(parts[6].trim());


                Student student = new Student(id, name,score1, score2, score3, hours, GPA);
                manager.addStudent(student);
            } // end of while

            //
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print students to the output with the initial list, the list after dropping students and the list after adding and sorting them by %score from largest to smallest
        System.out.println("Initial Student List:");
        manager.printStudents();

        // We add the student IDs here to tell the code which students need to be removed from the ArrayList
        manager.removeStudent("45A3");
        manager.removeStudent("34K5");

        System.out.println("\nHere's the class roster after dropping 2 students:");
        manager.printStudents();

        // This part of the program allows us to add new students with their IDs, grades and names and put them into the ArrayList
        manager.addStudent(new Student("67T4", "Clouse B ",80,75, 98, 102, 3.65F));
        manager.addStudent(new Student("S002", "Garrison J", 75, 78, 72,39, 1.85F));
        manager.addStudent(new Student("S003", "Singer A", 85, 95, 99, 130, 3.87F));

        // Sort students by percentage from highest to lowest scores
        manager.sortLarge();

        System.out.println("\nAnd here is the class roster after adding 4 new students, and sorting them by percentage score from highest to lowest:");
        manager.printStudents();
    }
}

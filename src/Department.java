import java.io.Serializable;
import java.util.ArrayList;

public class Department implements Serializable {
    private String name;
    private int studentCount;
    private ArrayList<Lecturer> lecturers = new ArrayList<>();

    public Department(String name, int studentCount) throws ManagementException {
        setName(name);
        setStudentCount(studentCount);
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setStudentCount(int studentCount) throws ManagementException {
        if (studentCount >= 0) {
            this.studentCount = studentCount;
        } else {
            throw new ManagementException("Student count cannot be negative.");
        }
    }
    public int getStudentCount() { return studentCount; }

    public void addLecturer(Lecturer lecturer) throws ManagementException {
        if (lecturer.getDepartment() != null) {
            throw new ManagementException("- Lecturer already part of a department.");
        }

        lecturers.add(lecturer);
        lecturer.setDepartment(this);
    }

    public double getAverageWage() {
        if (lecturers.isEmpty()) return 0;

        double sum = 0;
        for (Lecturer lecturer : lecturers) {
            sum += lecturer.getWage();
        }
        return sum / lecturers.size();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Department)) return false;
        return this.name.equals(((Department) obj).name);
    }

    public String toString() {
        String str = "Name: " + name + "\n" +
                "   Numbers of students: " + studentCount + "\n";

        if (!lecturers.isEmpty()) {
            str += "    Lecturers: \n";
            for (Lecturer lecturer : lecturers) {
                str += "        " + lecturer.getName() + "\n";
            }
        }
        return str;
    }
}
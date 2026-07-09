import java.io.Serializable;

public class Department implements Serializable {
    private String name;
    private int studentCount;
    private Lecturer[] lecturers = new Lecturer[2];
    private int lecturerCount = 0;

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

        if (lecturerCount == lecturers.length) {
            Lecturer[] newLecturers = new Lecturer[lecturerCount * 2];
            for (int i = 0; i < lecturerCount; i++) {
                newLecturers[i] = lecturers[i];
            }
            lecturers = newLecturers;
        }
        lecturers[lecturerCount] = lecturer;
        lecturerCount++;
        lecturer.setDepartment(this);
    }

    public double getAverageWage() {
        if (lecturerCount == 0) return 0;

        double sum = 0;
        for (int i = 0; i < lecturerCount; i++) {
            sum += lecturers[i].getWage();
        }
        return sum / lecturerCount;
    }


    public boolean equals(Object obj) {
        if (!(obj instanceof Department)) return false;
        return this.name.equals(((Department) obj).name);

    }

    public String toString() {
        String str = "Name: " + name + "\n" +
                "   Numbers of students: " + studentCount + "\n";

        if (lecturerCount > 0) {
            str += "    Lecturers: \n";
            for (int i = 0; i < lecturerCount; i++) {
                str += "        " + lecturers[i].getName() + "\n";
            }
        }
        return str;
    }

}
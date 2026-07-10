import java.io.Serializable;
import java.util.ArrayList;

public class College implements Serializable {
    private String name;
    private ArrayList<Lecturer> lecturers = new ArrayList<>();
    private ArrayList<Committee> committees = new ArrayList<>();
    private ArrayList<Department> departments = new ArrayList<>();

    public College(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addLecturer(Lecturer lecturer) throws ManagementException {
        if (lecturer.getName().length() < 3) {
            throw new ManagementException("- Lecturer name is not valid, must be at least 3 letters.");
        }

        if (getLecturerByName(lecturer.getName()) != null) {
            throw new ManagementException("- Lecturer already exists.");
        }

        for (Lecturer l : lecturers) {
            if (lecturer.getId() == l.getId()) {
                throw new ManagementException("- ID already taken, must be unique.");
            }
        }

        lecturers.add(lecturer);
    }

    public Lecturer getLecturerByName(String name) {
        for (Lecturer lecturer : lecturers) {
            if (lecturer.getName().equals(name)) {
                return lecturer;
            }
        }
        return null;
    }

    public Lecturer getLecturerById(int id) {
        for (Lecturer lecturer : lecturers) {
            if (lecturer.getId() == id) {
                return lecturer;
            }
        }
        return null;
    }

    public void checkMinimumDoctors(int required) throws ManagementException {
        int drCount = 0;
        for (Lecturer lecturer : lecturers) {
            if (lecturer instanceof Dr) {
                drCount++;
            }
        }
        if (drCount < required) {
            throw new ManagementException("- Not enough Doctors/Professors in the system to perform this action. Required: " + required);
        }
    }

    public double getAverageWage() {
        if (lecturers.isEmpty()) return 0;
        double wage = 0;
        for (Lecturer lecturer : lecturers) {
            wage += lecturer.getWage();
        }
        return wage / lecturers.size();
    }

    public String getAllLecturersDetails() {
        if (lecturers.isEmpty()) return "";
        String details = "";
        for (Lecturer lecturer : lecturers) {
            details += lecturer.toString() + "\n";
        }
        return details;
    }

    public void addCommittee(String committeeName, String headLecturerName, int type) throws ManagementException, MemberAlreadyInCommitteeException {
        if (committeeName.length() < 3) {
            throw new ManagementException("- Committee Name is not valid, must be at least 3 letters.");
        }

        if (getCommitteeByName(committeeName) != null) {
            throw new ManagementException("- Committee already exists.");
        }

        Lecturer headOfCommittee = getLecturerByName(headLecturerName);
        if (headOfCommittee == null) {
            throw new ManagementException("- Lecturer named " + headLecturerName + " was not found.");
        }

        if (!(headOfCommittee instanceof Dr)) {
            throw new ManagementException("- Lecturer " + headLecturerName + " is not qualified. Requirements: DR or PROFESSOR.");
        }

        if (type == 1) {
            committees.add(new Committee<Lecturer>(committeeName, (Dr) headOfCommittee));
        } else if (type == 2) {
            committees.add(new Committee<Dr>(committeeName, (Dr) headOfCommittee));
        } else if (type == 3) {
            committees.add(new Committee<Professor>(committeeName, (Dr) headOfCommittee));
        }
    }

    public Committee getCommitteeByName(String name) {
        for (Committee committee : committees) {
            if (committee.getName().equals(name)) {
                return committee;
            }
        }
        return null;
    }

    public String getAllCommitteesDetails() {
        if (committees.isEmpty()) return "";
        String details = "";
        for (Committee committee : committees) {
            details += committee.toString() + "\n";
        }
        return details;
    }

    public void checkMinimumCommittees(int required) throws ManagementException {
        if (committees.size() < required) {
            throw new ManagementException("- Not enough committees in the system to perform this action. Required: " + required);
        }
    }

    public void addDepartment(String departmentName, int studentCount) throws ManagementException {
        if (departmentName.length() < 3) {
            throw new ManagementException("- Department name is not valid, must be at least 3 letters.");
        }

        if (getDepartmentByName(departmentName) != null) {
            throw new ManagementException("- Department already exists.");
        }

        departments.add(new Department(departmentName, studentCount));
    }

    public Department getDepartmentByName(String name) {
        for (Department department : departments) {
            if (department.getName().equals(name)) {
                return department;
            }
        }
        return null;
    }

    public double getDepartmentAverageWage(String departmentName) throws ManagementException {
        Department department = getDepartmentByName(departmentName);
        if (department == null) {
            throw new ManagementException("- Department not found.");
        }
        return department.getAverageWage();
    }

    public void assignLecturerToDepartment(String lecturerName, String departmentName) throws ManagementException {
        Lecturer lecturer = getLecturerByName(lecturerName);
        Department department = getDepartmentByName(departmentName);

        if (lecturer == null) throw new ManagementException("- Lecturer not found.");
        if (department == null) throw new ManagementException("- Department not found.");

        department.addLecturer(lecturer);
    }

    public void assignLecturerToCommittee(String lecturerName, String committeeName) throws ManagementException, MemberAlreadyInCommitteeException {
        Lecturer lecturer = getLecturerByName(lecturerName);
        Committee committee = getCommitteeByName(committeeName);

        if (lecturer == null) throw new ManagementException("- Lecturer not found.");
        if (committee == null) throw new ManagementException("- Committee not found.");

        committee.addLecturer(lecturer);
    }

    public void removeLecturerFromCommittee(String lecturerName, String committeeName) throws ManagementException {
        Lecturer lecturer = getLecturerByName(lecturerName);
        Committee committee = getCommitteeByName(committeeName);

        if (lecturer == null) throw new ManagementException("- Lecturer not found.");
        if (committee == null) throw new ManagementException("- Committee not found.");

        committee.removeLecturer(lecturer);
    }

    public void updateHeadOfCommittee(String lecturerName, String committeeName) throws ManagementException, MemberAlreadyInCommitteeException {
        Committee committee = getCommitteeByName(committeeName);
        Lecturer lecturer = getLecturerByName(lecturerName);

        if (committee == null) throw new ManagementException("- Committee not found.");
        if (lecturer == null) throw new ManagementException("- Lecturer not found.");

        if (!(lecturer instanceof Dr)) {
            throw new ManagementException("- Lecturer is not qualified. Requirements: DR or PROFESSOR.");
        }

        committee.updateHeadOfCommittee((Dr) lecturer);
    }

    public void cloneCommittee(String committeeName) throws ManagementException, CloneNotSupportedException {
        Committee orig = getCommitteeByName(committeeName);
        if (orig == null) throw new ManagementException("- Committee not found.");

        committees.add(orig.clone());
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof College)) return false;
        return this.name.equals(((College) obj).getName());
    }

    public String toString() {
        return "Name: " + name + "\n" +
                "   Number of lecturers " + lecturers.size() + "\n"+
                "   Number of Committees "+ committees.size() + "\n"+
                "   Number of Departments "+ departments.size() + "\n";
    }
}
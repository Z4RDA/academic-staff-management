import java.io.Serializable;
import java.util.ArrayList;

public class Lecturer implements Serializable {
    private String name;
    private int id;
    private int wage;
    private Title degree;
    private String degreeName;
    private Department department;

    private ArrayList<Committee<? extends Lecturer>> committees = new ArrayList<>();

    public Lecturer(String name, int id, int level, String degreeName, int wage) throws ManagementException {
        setName(name);
        setId(id);
        setTitle(level);
        setDegreeName(degreeName);
        setWage(wage);
    }

    private void setName(String name) throws ManagementException {
        if (name.length() < 2) {
            throw new ManagementException("Name must be at least 2 characters.");
        }
        this.name = name;
    }
    public String getName() {
        return name;
    }

    private void setId(int id) throws ManagementException {
        if (id < 100000000 || id > 999999999) {
            throw new ManagementException("ID must contain exactly 9 digits.");
        }
        this.id = id;
    }
    public int getId() { return id; }

    public void setWage(int wage) throws ManagementException {
        if (wage < 1) {
            throw new ManagementException("Wage must be positive.");
        }
        this.wage = wage;
    }
    public int getWage() { return wage; }

    public void setTitle(int level) throws ManagementException {
        switch (level) {
            case 1: this.degree = Title.BACHELORS; break;
            case 2: this.degree = Title.MASTERS; break;
            case 3: this.degree = Title.DR; break;
            case 4: this.degree = Title.PROFESSOR; break;
            default: throw new ManagementException("Invalid title level. Must be between 1 and 4.");
        }
    }
    public Title getTitle() { return degree; }

    public void setDegreeName(String degreeName) throws ManagementException {
        if (degreeName.isEmpty()) {
            throw new ManagementException("Degree name cant be empty");
        }
        this.degreeName = degreeName;

    }
    public String getDegreeName() { return degreeName; }

    public void setDepartment(Department department) throws ManagementException {
        if (this.department == department) {
            throw new ManagementException("Lecturer is already in this department.");
        }
        this.department = department;
    }
    public Department getDepartment() { return department; }

    public String toString() {
        String str = "Name: " + getName() + "\n" +
                "   Id: " + getId() + "\n" +
                "   Wage: " + getWage() + "\n" +
                "   Title: " + getTitle() + "\n" +
                "   Degree: " + getDegreeName() + "\n";

        if (getDepartment() != null) {
            str += "    Department: " + getDepartment().getName() + "\n";
        }

        if (!committees.isEmpty()) {
            str += "    Committees: \n";
            for (Committee<? extends Lecturer> committee : committees) {
                str += "        " + committee.getName() + "\n";
            }
        }
        return str;
    }

    protected boolean hasCommittee(Committee<? extends Lecturer> committee) {
        return committees.contains(committee);
    }

    public void addCommittee(Committee<? extends Lecturer> committee) throws MemberAlreadyInCommitteeException {
        if (hasCommittee(committee)) {
            throw new MemberAlreadyInCommitteeException("- Lecturer already part of committee.");
        }
        committees.add(committee);
    }

    public void removeCommittee(Committee<? extends Lecturer> committee) throws ManagementException {
        if (!hasCommittee(committee)) {
            throw new ManagementException("- Lecturer not in committee.");
        }
        committees.remove(committee);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Lecturer)) return false;
        return this.id == ((Lecturer) obj).getId();
    }
}
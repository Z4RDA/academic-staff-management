public class College {
    private String name;
    private Lecturer[] lecturers = new Lecturer[2];
    private Committee[] committees = new Committee[2];
    private Department[] departments = new Department[2];
    private int lecturersCount = 0;
    private int committeesCount = 0;
    private int departmentsCount = 0;

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

        for (int i = 0; i < lecturersCount; i++) {
            if (lecturer.getId() == lecturers[i].getId()) {
                throw new ManagementException("- ID already taken, must be unique.");
            }
        }

        if (lecturers.length == lecturersCount) {
            Lecturer[] newLecturers = new Lecturer[lecturersCount * 2];
            for (int i = 0; i < lecturersCount; i++) {
                newLecturers[i] = lecturers[i];
            }
            lecturers = newLecturers;
        }

        lecturers[lecturersCount] = lecturer;
        lecturersCount++;
    }

    public Lecturer getLecturerByName(String name) {
        for (int i = 0; i < lecturersCount; i++) {
            if (lecturers[i].getName().equals(name)) {
                return lecturers[i];
            }
        }
        return null;
    }

    public Lecturer getLecturerById(int id) {
        for (int i = 0; i < lecturersCount; i++) {
            if (lecturers[i].getId() == id) {
                return lecturers[i];
            }
        }
        return null;
    }

    public void checkMinimumDoctors(int required) throws ManagementException {
        int drCount = 0;
        for (int i = 0; i < lecturersCount; i++) {
            if (lecturers[i] instanceof Dr) {
                drCount++;
            }
        }
        if (drCount < required) {
            throw new ManagementException("- Not enough Doctors/Professors in the system to perform this action. Required: " + required);
        }
    }

    public double getAverageWage() {
        if (lecturersCount == 0) return 0;
        double wage = 0;
        for (int i = 0; i < lecturersCount; i++) {
            wage += lecturers[i].getWage();
        }
        return wage / lecturersCount;
    }

    public String getAllLecturersDetails() {
        if (lecturersCount == 0) return "";
        String details = "";
        for (int i = 0; i < lecturersCount; i++) {
            details += lecturers[i].toString() + "\n";
        }
        return details;
    }

    public void addCommittee(String committeeName, String headLecturerName) throws ManagementException, MemberAlreadyInCommitteeException {
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

        if (committees.length == committeesCount) {
            Committee[] newCommittees = new Committee[committeesCount * 2];
            for (int i = 0; i < committeesCount; i++) {
                newCommittees[i] = committees[i];
            }
            committees = newCommittees;
        }

        Committee committee = new Committee(committeeName, (Dr) headOfCommittee);
        committees[committeesCount] = committee;
        committeesCount++;
    }

    public Committee getCommitteeByName(String name) {
        for (int i = 0; i < committeesCount; i++) {
            if (committees[i].getName().equals(name)) {
                return committees[i];
            }
        }
        return null;
    }

    public String getAllCommitteesDetails() {
        if (committeesCount == 0) return "";
        String details = "";
        for (int i = 0; i < committeesCount; i++) {
            details += committees[i].toString() + "\n";
        }
        return details;
    }

    public void checkMinimumCommittees(int required) throws ManagementException {
        if (this.committeesCount < required) {
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

        if (departments.length == departmentsCount) {
            Department[] newDepartments = new Department[departmentsCount * 2];
            for (int i = 0; i < departmentsCount; i++) {
                newDepartments[i] = departments[i];
            }
            departments = newDepartments;
        }

        Department department = new Department(departmentName, studentCount);
        departments[departmentsCount] = department;
        departmentsCount++;
    }

    public Department getDepartmentByName(String name) {
        for (int i = 0; i < departmentsCount; i++) {
            if (departments[i].getName().equals(name)) {
                return departments[i];
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
        Committee clonedCommittee = orig.clone();

        if (committees.length == committeesCount) {
            Committee[] newCommittees = new Committee[committeesCount * 2];
            for (int i = 0; i < committeesCount; i++) {
                newCommittees[i] = committees[i];
            }
            committees = newCommittees;
        }
            committees[committeesCount] = clonedCommittee;
            committeesCount++;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof College)) return false;
        return this.name.equals(((College) obj).getName());
    }

    public String toString() {
        String str = "Name: " + name + "\n" +
                "   Number of lecturers " + lecturersCount + "\n"+
                "   Number of Committees "+ committeesCount + "\n"+
                "   Number of Departments "+ departmentsCount + "\n";
        return str;
    }
}
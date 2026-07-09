import java.io.Serializable;

public class Committee implements Cloneable, Serializable {
    private String name;
    private Dr headOfCommittee; // שונה מ-Lecturer ל-Dr
    private Lecturer[] lecturers = new Lecturer[2];
    private int lecturerCount = 0;

    public Committee(String name, Dr headOfCommittee) throws MemberAlreadyInCommitteeException {
        setName(name);
        setHeadOfCommittee(headOfCommittee);
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setHeadOfCommittee(Dr head) throws MemberAlreadyInCommitteeException {
        this.headOfCommittee = head;
        this.headOfCommittee.addCommittee(this);
    }
    public Dr getHeadOfCommittee() { return headOfCommittee; }

    public int getMembersCount() {
        return lecturerCount + 1;
    }

    public int getTotalArticles() {
        int total = this.headOfCommittee.getArticlesCount();

        for (int i = 0; i < lecturerCount; i++) {
            if (lecturers[i] instanceof Dr) {
                total += ((Dr) lecturers[i]).getArticlesCount();
            }
        }
        return total;
    }

    private boolean hasLecturer(Lecturer lecturer) {
        for (int i = 0; i < lecturerCount; i++) {
            if (lecturers[i] == lecturer) return true;
        }
        return false;
    }

    public void updateHeadOfCommittee(Dr newHead) throws ManagementException, MemberAlreadyInCommitteeException {
        if (newHead == headOfCommittee) {
            throw new ManagementException("- Lecturer is already head of the committee.");
        }

        Dr oldHead = headOfCommittee;

        if (hasLecturer(newHead)) {
            removeLecturer(newHead);
        }

        setHeadOfCommittee(newHead);
        oldHead.removeCommittee(this);
        addLecturer(oldHead);
    }

    public void addLecturer(Lecturer lecturer) throws MemberAlreadyInCommitteeException {
        if (lecturer == headOfCommittee) {
            throw new MemberAlreadyInCommitteeException("- Lecturer is the head of committee and cannot be in members list.");
        }
        if (hasLecturer(lecturer)) {
            throw new MemberAlreadyInCommitteeException("- Lecturer already part of committee.");
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
        lecturer.addCommittee(this);
    }

    public void removeLecturer(Lecturer lecturer) throws ManagementException {
        if (lecturer == headOfCommittee) {
            throw new ManagementException("- Cannot remove the Head of Committee. Assign a new Head first.");
        }
        if (!hasLecturer(lecturer)) {
            throw new ManagementException("- Lecturer is not part of committee.");
        }

        int indexToRemove = -1;
        for (int i = 0; i < lecturerCount; i++) {
            if (lecturers[i] == lecturer) {
                indexToRemove = i;
                break;
            }
        }

        for (int i = indexToRemove; i < lecturerCount - 1; i++) {
            lecturers[i] = lecturers[i + 1];
        }
        lecturers[lecturerCount - 1] = null;
        lecturerCount--;

        lecturer.removeCommittee(this);
    }

    public String toString() {
        String str = "Name: " + name + "\n" +
                "   Head of committee: " + headOfCommittee.getName() + "\n";

        if (lecturerCount > 0) {
            str += "    Lecturers: \n";
            for (int i = 0; i < lecturerCount; i++) {
                str += "        " + lecturers[i].getName() + "\n";
            }
        }
        return str;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Committee)) {
            return false;
        }
        Committee other = (Committee) obj;
        return this.name.equals(other.name);
    }

    public Committee clone() throws CloneNotSupportedException {
        Committee cloned = (Committee) super.clone();

        cloned.name = "new-" + this.name;

        cloned.lecturers = new Lecturer[this.lecturers.length];

        for (int i = 0; i < this.lecturerCount; i++) {
            cloned.lecturers[i] = this.lecturers[i];
        }

        return cloned;
    }
}
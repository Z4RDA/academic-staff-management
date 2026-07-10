import java.io.Serializable;
import java.util.ArrayList;

public class Committee<T extends Lecturer> implements Cloneable, Serializable {
    private String name;
    private Dr headOfCommittee;
    private int allowedType;

    private ArrayList<T> lecturers = new ArrayList<>();

    public Committee(String name, Dr headOfCommittee, int allowedType) throws MemberAlreadyInCommitteeException {
        setName(name);
        setHeadOfCommittee(headOfCommittee);
        setAllowedType(allowedType);
    }

    private void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    private void setAllowedType(int allowedType) {
        this.allowedType = allowedType;
    }

    private void setHeadOfCommittee(Dr head) throws MemberAlreadyInCommitteeException {
        this.headOfCommittee = head;
        this.headOfCommittee.addCommittee(this);
    }
    public Dr getHeadOfCommittee() { return headOfCommittee; }

    public int getMembersCount() {
        return lecturers.size() + 1;
    }

    public int getTotalArticles() {
        int total = this.headOfCommittee.getArticlesCount();

        for (T lecturer : lecturers) {
            if (lecturer instanceof Dr) {
                total += ((Dr) lecturer).getArticlesCount();
            }
        }
        return total;
    }

    private boolean hasLecturer(T lecturer) {
        return lecturers.contains(lecturer);
    }

    public void updateHeadOfCommittee(Dr newHead) throws ManagementException, MemberAlreadyInCommitteeException {
        if (newHead == headOfCommittee) {
            throw new ManagementException("- Lecturer is already head of the committee.");
        }

        Dr oldHead = headOfCommittee;

        if (hasLecturer((T) newHead)) {
            removeLecturer((T) newHead);
        }

        setHeadOfCommittee(newHead);
        oldHead.removeCommittee(this);
    }

    public void addLecturer(T lecturer) throws MemberAlreadyInCommitteeException, ManagementException {
        if (lecturer == headOfCommittee) {
            throw new MemberAlreadyInCommitteeException("- Lecturer is the head of committee and cannot be in members list.");
        }
        if (hasLecturer(lecturer)) {
            throw new MemberAlreadyInCommitteeException("- Lecturer already part of committee.");
        }

        if (allowedType == 3 && !(lecturer instanceof Professor)) {
            throw new ManagementException("- This committee is for Professors only.");
        } else if (allowedType == 2 && !(lecturer instanceof Dr)) {
            throw new ManagementException("- This committee is for Doctors only.");
        } else if (allowedType == 1 && lecturer instanceof Dr) {
            throw new ManagementException("- This committee is for regular degrees only (Bachelors/Masters).");
        }

        lecturers.add(lecturer);
        lecturer.addCommittee(this);
    }

    public void removeLecturer(T lecturer) throws ManagementException {
        if (lecturer == headOfCommittee) {
            throw new ManagementException("- Cannot remove the Head of Committee. Assign a new Head first.");
        }
        if (!hasLecturer(lecturer)) {
            throw new ManagementException("- Lecturer is not part of committee.");
        }

        lecturers.remove(lecturer);
        lecturer.removeCommittee(this);
    }

    public String toString() {
        String str = "Name: " + name + "\n" +
                "   Head of committee: " + getHeadOfCommittee().getName() + "\n";

        if (!lecturers.isEmpty()) {
            str += "    Lecturers: \n";
            for (T lecturer : lecturers) {
                str += "        " + lecturer.getName() + "\n";
            }
        }
        return str;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Committee)) {
            return false;
        }
        Committee<?> other = (Committee<?>) obj;
        return this.name.equals(other.name);
    }

    public Committee<T> clone() throws CloneNotSupportedException {
        Committee<T> cloned = (Committee<T>) super.clone();
        cloned.name = "new-" + this.name;
        cloned.lecturers = new ArrayList<>(this.lecturers);
        return cloned;
    }
}
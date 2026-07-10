import java.io.Serializable;

public class Professor extends Dr implements Serializable {
    private String awardingInstitution;

    public Professor(String name, int id, String degreeName, int wage, String awardingInstitution) throws ManagementException {
        super(name, id, degreeName, wage);

        setTitle(4);

        setAwardingInstitution(awardingInstitution);
    }

    public void setAwardingInstitution(String awardingInstitution) throws ManagementException{
        if (awardingInstitution.length() < 3) {
            throw new ManagementException("Awarding institution must be aat least 3 characters");
        }
        this.awardingInstitution = awardingInstitution;
    }

    public String getAwardingInstitution() {
        return awardingInstitution;
    }

    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof Professor)) return false;

        Professor other = (Professor) obj;
        return getAwardingInstitution().equals(other.getAwardingInstitution());
    }

    public String toString() {
        String str = super.toString();
        str += "    Awarding Institution: " + getAwardingInstitution() + "\n";
        return str;
    }
}
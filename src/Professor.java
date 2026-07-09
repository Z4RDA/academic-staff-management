import java.io.Serializable;

public class Professor extends Dr implements Serializable {
    private String awardingInstitution;

    public Professor(String name, int id, String degreeName, int wage, String awardingInstitution) throws ManagementException {
        super(name, id, degreeName, wage);

        setTitle(4);

        this.awardingInstitution = awardingInstitution;
    }

    public void setAwardingInstitution(String awardingInstitution) {
        this.awardingInstitution = awardingInstitution;
    }

    public String getAwardingInstitution() {
        return awardingInstitution;
    }

    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof Professor)) return false;

        Professor other = (Professor) obj;
        return this.awardingInstitution.equals(other.awardingInstitution);
    }

    public String toString() {
        String str = super.toString();
        str += "    Awarding Institution: " + awardingInstitution + "\n";
        return str;
    }
}
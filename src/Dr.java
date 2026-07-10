import java.io.Serializable;
import java.util.ArrayList;

public class Dr extends Lecturer implements Comparable<Dr>, Serializable {
    private ArrayList<String> articles = new ArrayList<>();

    public Dr(String name, int id, String degreeName, int wage) throws ManagementException {
        super(name, id, 3, degreeName, wage);
    }

    public void addArticle(String article) {
        articles.add(article);
    }

    public int getArticlesCount() {
        return articles.size();
    }

    public int compareTo(Dr other) {
        return this.getArticlesCount() - other.getArticlesCount();
    }

    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof Dr)) return false;
        return true;
    }

    public String toString() {
        String str = super.toString();

        if (!articles.isEmpty()) {
            str += "    Articles: \n";
            for (String article : articles) {
                str += "        - " + article + "\n";
            }
        }
        return str;
    }
}
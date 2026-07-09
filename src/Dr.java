import java.io.Serializable;

public class Dr extends Lecturer implements Comparable<Dr> , Serializable {
    private String[] articles = new String[2];
    private int articlesCount = 0;

    public Dr(String name, int id, String degreeName, int wage) throws ManagementException {
        super(name, id, 3, degreeName, wage);
    }

    public void addArticle(String article) {
        if (articlesCount == articles.length) {
            String[] newArticles = new String[articlesCount * 2];
            for (int i = 0; i < articlesCount; i++) {
                newArticles[i] = articles[i];
            }
            articles = newArticles;
        }
        articles[articlesCount] = article;
        articlesCount++;
    }

    public int getArticlesCount() {
        return articlesCount;
    }

    public int compareTo(Dr other) {
        return this.articlesCount - other.articlesCount;
    }

    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof Dr)) return false;
        return true;
    }

    public String toString() {
        String str = super.toString();

        if (articlesCount > 0) {
            str += "    Articles: \n";
            for (int i = 0; i < articlesCount; i++) {
                str += "        - " + articles[i] + "\n";
            }
        }
        return str;
    }
}
import java.util.Date;
import java.util.List;

public class Root implements ToJson {
    public int page;
    public int per_page;
    public int total;
    public int total_pages;
    public List<Datum> data;
    public Datum dataSingle;
    public Support support;
    public String name;
    public String job;
    public Date updatedAt;

    public String id;
    public Date createdAt;
    public String token;


    public Root() {
    }

    public Root(int page, int per_page, int total, int total_pages, List<Datum> data, Support support) {
        this.page = page;
        this.per_page = per_page;
        this.total = total;
        this.total_pages = total_pages;
        this.data = data;
        this.support = support;
    }
}

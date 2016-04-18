package senori.or.jp.sharering.data;

/**
 * Created by JupiteR on 2016-04-15.
 */
public class UserData {

    private int number;
    private int id;
    private String content;
    private String nicname;
    private String uri;
    private String icon;
    private String cover;
    private String day;

    public UserData(int number, int id, String content, String nicname, String uri, String icon, String cover, String day) {
        this.number = number;
        this.id = id;
        this.content = content;
        this.nicname = nicname;
        this.uri = uri;
        this.icon = icon;
        this.cover = cover;
        this.day = day;
    }

    public int getNumber() {
        return number;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getNicname() {
        return nicname;
    }

    public String getUri() {
        return uri;
    }

    public String getIcon() {
        return icon;
    }

    public String getCover() {
        return cover;
    }

    public String getDay() {
        return day;
    }
}

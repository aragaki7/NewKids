package senori.or.jp.sharering.data;

import java.io.Serializable;

/**
 * Created by JupiteR on 2016-04-13.
 */
public class PhotoData implements Serializable {

    private String url;
    private int id;
    private String content;
    private int number;
    private String nicname;
    private String day;
    private String icon;

    public PhotoData(int number, int id, String content, String url, String nicname, String icon, String day) {
        this.number = number;
        this.id = id;
        this.content = content;
        this.url = url;
        this.nicname = nicname;
        this.day = day;
        this.icon = icon;
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

    public String getUrl() {
        return url;
    }

    public String getNicname() {
        return nicname;
    }

    public String getDay() {
        return day;
    }

    public String getIcon() {
        return icon;
    }
}

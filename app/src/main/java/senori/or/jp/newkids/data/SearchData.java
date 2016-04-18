package senori.or.jp.newkids.data;

import java.io.Serializable;


public class SearchData implements Serializable {

    private int id;
    private String nicname;
    private String icon;

    public SearchData(int id, String nicname, String icon) {
        this.id = id;
        this.nicname = nicname;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public String getNicname() {
        return nicname;
    }

    public String getIcon() {
        return icon;
    }
}

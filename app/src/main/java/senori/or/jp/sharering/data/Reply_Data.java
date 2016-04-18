package senori.or.jp.sharering.data;

import java.sql.Date;

/**
 * Created by JupiteR on 2016-04-06.
 */
public class Reply_Data {

    private Date day;
    private String nicname;
    private String content;

    public Reply_Data(Date day, String nicname, String content) {
        this.day = day;
        this.nicname = nicname;
        this.content = content;

    }

    public Date getDay() {
        return day;
    }

    public String getNicname() {
        return nicname;
    }

    public String getContent() {
        return content;
    }


}

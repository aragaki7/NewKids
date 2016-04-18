package senori.or.jp.newkids.data;

import java.sql.Date;

/**
 * Created by JupiteR on 2016-04-02.
 */
public class Item {

    private int number;
    private int count;
    private int Visibility;
    private int reply_count;
    private String content;
    private String nicname;
    private String title;
    private Date day;

    public Item(int numger, int count, String title, String content, String nicname, Date day, int Visibility, int reply_count) {
        this.number = numger;
        this.count = count;
        this.content = content;
        this.nicname = nicname;
        this.title = title;
        this.day = day;
        this.Visibility = Visibility;
        this.reply_count = reply_count;
    }

    public int getNumber() {
        return number;
    }

    public int getCount() {
        return count;
    }

    public String getContent() {
        return content;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNicname() {
        return nicname;
    }

    public String getTitle() {
        return title;
    }

    public Date getDay() {
        return day;
    }

    public int getVisibility() {
        return Visibility;
    }

    public void setVisibility(int visibility) {
        Visibility = visibility;
    }

    public int getReply_count() {
        return reply_count;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

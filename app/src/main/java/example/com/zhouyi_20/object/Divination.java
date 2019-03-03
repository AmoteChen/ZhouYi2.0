package example.com.zhouyi_20.object;

public class Divination {
    private String time;
    private String id;
    private String reason;
    private String xingqi;
    private String way;

    public Divination() {
        this.time = "none";
        this.id = "none";
        this.reason = "none";
        this.xingqi = "none";
        this.way = "none";
    }

    public Divination(String reason,String time,String xingqi,String way) {
        this.time = time;
        this.id = "none";
        this.reason = reason;
        this.xingqi = xingqi;
        this.way = way;
    }

    public Divination(String reason, String id, String time,String xingqi,String way) {
        this.time = time;
        this.id = id;
        this.reason = reason;
        this.xingqi = xingqi;
        this.way = way;
    }


    public void setTime(String time) {
        this.time = time;
    }
    public String getTime() {
        return this.time;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return this.id;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    public String getReason() {
        return this.reason;
    }

    public void setXingqi(String xingqi){
        this.xingqi = xingqi;
    }
    public String getXingqi(){
        return this.xingqi;
    }

    public void setWay(String way){
        this.way = way;
    }
    public String getway(){
        return this.way;
    }

}

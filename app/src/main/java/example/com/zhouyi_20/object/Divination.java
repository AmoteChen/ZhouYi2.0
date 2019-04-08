package example.com.zhouyi_20.object;

import org.json.JSONArray;

/**
 此类是历史纪录里面的各项
 **/
public class Divination {
    private String _id;
    private String time;
    private String id;
    private String reason;
    private String xingqi;
    private String way;
    private String name;
    private String yongshen;
    private String note;
    private JSONArray guaxiang;

    public Divination() {
        this._id = "none";
        this.time = "none";
        this.id = "none";
        this.reason = "none";
        this.xingqi = "none";
        this.way = "none";
        this.name = "none";
        this.yongshen = "none";
        this.note = "none";
        this.guaxiang = null;
    }



    public Divination(String _id, String reason, String id, String yongshen,String time,String xingqi,String way,String name,String note,JSONArray guaxiang) {
        this._id = _id;
        this.time = time;
        this.id = id;
        this.yongshen = yongshen;
        this.reason = reason;
        this.xingqi = xingqi;
        this.way = way;
        this.name = name;
        this.note = note;
        this.guaxiang = guaxiang;
    }



    public void set_id(String _id){
        this._id = _id;
    }
    public String get_id(){
        return this._id;
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

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    public void setYongshen(String yongshen){
        this.yongshen = yongshen;
    }
    public String getYongshen(){
        return this.yongshen;
    }

    public void setNote(String note){
        this.note = note;
    }
    public String getNote(){
        return this.note;
    }

    public void setGuaxiang(JSONArray guaxiang){
        this.guaxiang = guaxiang;
    }
    public JSONArray getGuaxiang(){
        return this.guaxiang;
    }
}

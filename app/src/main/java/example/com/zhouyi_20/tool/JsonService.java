package example.com.zhouyi_20.tool;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonService {
    // 单实例
    private static JsonService instance;
    private JsonService(){ }
    public static JsonService getInstance(){
        if (instance==null)
            instance=new JsonService();
        return instance;
    }

    // 初始时调用，否则必定出错
    public void createJsonObject(String str)
    {
        try {
            JSONObject rootObject=new JSONObject(str);
            JSONObject dataObject=rootObject.getJSONObject("data");

            /*********************** 第一部分的数据 **************************/
            JSONObject dataObject_ZG=dataObject.getJSONObject("zhuangGuaTable");
            JSONObject dataObject_1=dataObject_ZG.getJSONObject("basicData");
            JSONArray dataObject_kong_zhuanggua=dataObject_ZG.getJSONArray("kongIndex");
            // 六亲
            liuqin_1 = dataObject_1.getString("six_relatives");
            wuxing_1=dataObject_1.getString("five_elements");
            heavenly_stems_zhuanggua = dataObject_1.getString("heavenly_stems");
            earthly_branches = dataObject_1.getString("earthly_branches");
            //content
            content_zhuanggua = dataObject_1.getString("content");
            Log.e("DAYT", content_zhuanggua);
            //times
            times_zhuanggua = dataObject_1.getString("times");

            //空表
            kong_zhuanggua=new ArrayList();
            for (int i = 0;i<dataObject_kong_zhuanggua.length();++i){
                kong_zhuanggua.add(dataObject_kong_zhuanggua.get(i));
            }

            // 世应
            shiying = dataObject_1.getString("shi_ying");

            /*********************** 第二部分的数据 **************************/
            JSONObject dataObject_BG=dataObject.getJSONObject("bianGuaTable");
            JSONObject dataObject_2=dataObject_BG.getJSONObject("basicData");

            // 六亲
            liuqin_2=dataObject_2.getString("six_relatives");
            //变卦五行的部分
            wuxing_3=dataObject_2.getString("five_elements");
            //content
            content_biangua=dataObject_2.getString("content");
            Log.e("DAYww",content_biangua);
            //times
            times_biangua = dataObject_2.getString("times");

            //变卦部分的show下标
            JSONArray dataObject_show_biangua=dataObject_BG.getJSONArray("showIndex");
            show_biangua=new ArrayList();
            for (int i = 0;i<dataObject_show_biangua.length();++i){
                show_biangua.add(dataObject_show_biangua.get(i));
            }
            //变卦部分的kong下标
            JSONArray dataObject_kong_biangua=dataObject_BG.getJSONArray("kongIndex");
            kong_biangua=new ArrayList();
            for (int i = 0;i<dataObject_kong_biangua.length();++i){
                kong_biangua.add(dataObject_kong_biangua.get(i));
            }

            /*********************** 第三部分的数据 **************************/
            JSONObject dataObject_FS=dataObject.getJSONObject("fuShenTable");
            JSONObject dataObject_3=dataObject_FS.getJSONObject("basicData");

            // 六亲
            liuqin_3=dataObject_3.getString("six_relatives");
            //五行（伏神部分）
            wuxing_2=dataObject_3.getString("five_elements");
            Log.e("WX",wuxing_2);
            //content
            content_fushen = dataObject_3.getString("content");
            //times
            times_fushen= dataObject_3.getString("times");

            heavenly_stems_fushen=dataObject_3.getString("heavenly_stems");
            earthly_fushen=dataObject_3.getString("earthly_branches");

            //伏神部分的show下标
            JSONArray dataObject_show_fushen=dataObject_FS.getJSONArray("showIndex");
            show_fushen=new ArrayList();
            for (int i = 0; i < dataObject_show_fushen.length(); ++i) {
                show_fushen.add(dataObject_show_fushen.get(i));
            }



            /*********************** 第四部分的数据 **************************/
            JSONArray dataObject_da=dataObject.getJSONArray("dayTable");
            daytable="";
            for (int i =0;i<5;++i){
                daytable+=dataObject_da.getString(i);
            }

            JSONArray dataObject_qin=dataObject.getJSONArray("qingTable");
            qintable=new ArrayList();
            for (int i =0;i<5;++i){
                qintable.add(dataObject_qin.get(i));
            }




        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /************************* 存储数据 *************************/

    // 六亲，从左至右
    private String liuqin_1;
    public String getLiuqin_1(){ return liuqin_1;}
    private String liuqin_2;
    public String getLiuqin_2(){return liuqin_2;}
    private String liuqin_3;
    public String getLiuqin_3(){return liuqin_3;}

    //content
    //装卦里的content
    private String content_zhuanggua;
    public String getContent_zhuanggua(){return content_zhuanggua;}
    //装卦里的卦次
    private String times_zhuanggua;
    public String getTimes_zhuanggua(){return times_zhuanggua;}

    //变卦里的content
    private String content_biangua;
    public String getContent_biangua(){return content_biangua;}
    //变卦里的卦次
    private String times_biangua;
    public String getTimes_biangua(){return times_biangua;}

    //伏神里的content
    private String content_fushen;
    public String getContent_fushen(){return content_fushen;}
    //伏神里的卦次
    private String times_fushen;
    public String getTimes_fushen(){return times_fushen;}


    //五行，装卦里的那部分
    private String wuxing_1;
    public String getWuxing_1(){return wuxing_1;}
    //五行，变卦里的那部分
    private String wuxing_3;
    public String getWuxing_3(){return wuxing_3;}
    //五行，伏神里的那部分
    private String wuxing_2;
    public String getWuxing_2(){return wuxing_2;}

    //heavenly_stems
    private String heavenly_stems_zhuanggua;
    public String getHeavenly_stems(){return heavenly_stems_zhuanggua;}
    private String heavenly_stems_fushen;
    public String getHeavenly_stems_fushen(){return heavenly_stems_fushen;}

    //earthly_branches
    private String earthly_branches;
    public String getEarthly_branches(){return earthly_branches;}
    private String earthly_fushen;
    public String getEarthly_fushen(){return earthly_fushen;}

    // 世应
    private String shiying;
    public String getShiying(){return shiying;}

    //日表（下端那五个）
    private String daytable;
    public String getDaytable(){return daytable;}
    //亲表(下端那个）
    private List qintable = new ArrayList();
    public List getQintable(){return qintable;}

    //装卦表的kong下标
    private List kong_zhuanggua=new ArrayList();
    public List getKong_zhuanggua(){return kong_zhuanggua;}


    //变卦表的show下标
    private  static List show_biangua = new ArrayList();
    public  static List getShow_biangua(){return show_biangua;}
    //变卦表的kong下标
    private  List kong_biangua = new ArrayList();
    public  List getKong_biangua(){return kong_biangua;}

    //伏神表的show下标
    private static List show_fushen = new ArrayList();
    public static List getShow_fushen(){return show_fushen;}


}

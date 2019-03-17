package example.com.zhouyi_20.tool;

import java.util.Calendar;

public class TianGanDiZhi {
    // 以下均为新历
    private int year;
    private int month;
    private int day;

    // 以下分别为年月日的天干地支字符串
    private String ganzhi_year;
    private String ganzhi_month;
    private String ganzhi_day;
    private String[] wuxing;

    private static String[] Gan = new String[]{"癸","甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬"};
    private static String[] Zhi_year = new String[]{"亥","子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌"};

    private static String[] Zhi_month=new String[]{"寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌","亥","子", "丑"};

    public TianGanDiZhi(Calendar cal){
        year=cal.get(Calendar.YEAR);
        month=cal.get(Calendar.MONTH)+1;
        day=cal.get(Calendar.DATE);

        ganzhi_year=new String(Gan[(year-3)%10]+ Zhi_year[(year-3)%12]);

        int month_gan_start=0;
        switch ((year-3)%10){
            case 1:
            case 6:
                month_gan_start=3;
                break;
            case 2:
            case 7:
                month_gan_start=5;
                break;
            case 3:
            case 8:
                month_gan_start=7;
                break;
            case 4:
            case 9:
                month_gan_start=9;
                break;
            case 5:
            case 0:
                month_gan_start=1;
                break;
        }
        ganzhi_month=new String(Gan[(month+month_gan_start-1)%10]+Zhi_month[month-1]);

        String wuxing_tmp[]=new String[]{"金","水","木","火","土"} ;
        wuxing=new String[5];
        int wuxing_start=0;
        switch (month){
            case 1:
            case 2:
                wuxing_start=2;
                break;
            case 4:
            case 5:
                wuxing_start=3;
                break;
            case 7:
            case 8:
                wuxing_start=0;
                break;
            case 10:
            case 11:
                wuxing_start=1;
                break;
            default:
                wuxing_start=4;
                break;
        }

        for (int i=0;i<5;++i){
            wuxing[i]=wuxing_tmp[(wuxing_start+i)%5];
        }
    }

    public String getGanzhi_year(){return ganzhi_year;}
    public String getGanzhi_month(){return ganzhi_month;}
    public String getZhi_month(){return ganzhi_month.charAt(0)+"月";}
    public String[] getWuXing(){return wuxing;}
    // 一脸懵逼写不出来先用年的顶住
    public String getGanzhi_day(){return ganzhi_year;}
}

package example.com.zhouyi_20.activity.Ziding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Ziding_Map extends HashMap<String, String > {

    public Ziding_Map(){
        /************************* 乾宫 *************************/
        this.put("[0,0,0,0,0,0]","乾宫 本宫卦 \n乾为天");
        this.put("[0,0,0,0,0,1]","乾宫 一世卦 \n天风姤");
        this.put("[0,0,0,0,1,1]","乾宫 二世卦 \n天山遁");
        this.put("[0,0,0,1,1,1]","乾宫 三世卦 \n天地否");
        this.put("[0,0,1,1,1,1]","乾宫 四世卦 \n风地观");
        this.put("[0,1,1,1,1,1]","乾宫 五世卦 \n山地剥");
        this.put("[0,1,0,1,1,1]","乾宫 游魂卦 \n火地晋");
        this.put("[0,1,0,0,0,0]","乾宫 归魂卦 \n火天大有");

        /************************* 坤宫 *************************/
        this.put("[1,1,1,1,1,1]","坤宫 本宫卦 \n坤为地");
        this.put("[1,1,1,1,1,0]","坤宫 一世卦 \n地雷复");
        this.put("[1,1,1,1,0,0]","坤宫 二世卦 \n地泽临");
        this.put("[1,1,1,0,0,0]","坤宫 三世卦 \n地天泰");
        this.put("[1,1,0,0,0,0]","坤宫 四世卦 \n雷天大壮");
        this.put("[1,0,0,0,0,0]","坤宫 五世卦 \n泽天夬");
        this.put("[1,0,1,0,0,0]","坤宫 游魂卦 \n水天需");
        this.put("[1,0,1,1,1,1]","坤宫 归魂卦 \n水地比");

        /************************* 震宫 *************************/
        this.put("[1,1,0,1,1,0]","震宫 本宫卦 \n震为雷");
        this.put("[1,1,0,1,1,1]","震宫 一世卦 \n雷地豫");
        this.put("[1,1,0,1,0,1]","震宫 二世卦 \n雷水解");
        this.put("[1,1,0,0,0,1]","震宫 三世卦 \n雷风恒");
        this.put("[1,1,1,0,0,1]","震宫 四世卦 \n地风升");
        this.put("[1,0,1,0,0,1]","震宫 五世卦 \n水风井");
        this.put("[1,0,0,0,0,1]","震宫 游魂卦 \n泽风大过");
        this.put("[1,0,0,1,1,0]","震宫 归魂卦 \n泽雷随");

        /************************* 巽宫 *************************/
        this.put("[0,0,1,0,0,1]","巽宫 本宫卦 \n巽为风");
        this.put("[0,0,1,0,0,0]","巽宫 一世卦 \n风天小畜");
        this.put("[0,0,1,0,1,0]","巽宫 二世卦 \n风火家人");
        this.put("[0,0,1,1,1,0]","巽宫 三世卦 \n风雷易");
        this.put("[0,0,0,1,1,0]","巽宫 四世卦 \n天雷无妄");
        this.put("[0,1,0,1,1,0]","巽宫 五世卦 \n火雷噬嗑");
        this.put("[0,1,1,1,1,0]","巽宫 游魂卦 \n山雷颐");
        this.put("[0,1,1,0,0,1]","巽宫 归魂卦 \n山风蛊");

        /************************* 坎宫 *************************/
        this.put("[1,0,1,1,0,1]","坎宫 本宫卦 \n坎为水");
        this.put("[1,0,1,1,0,0]","坎宫 一世卦 \n水泽节");
        this.put("[1,0,1,1,1,0]","坎宫 二世卦 \n水雷屯");
        this.put("[1,0,1,0,1,0]","坎宫 三世卦 \n水火既济");
        this.put("[1,0,0,0,1,0]","坎宫 四世卦 \n泽火革");
        this.put("[1,1,0,0,1,0]","坎宫 五世卦 \n雷火丰");
        this.put("[1,1,1,0,1,0]","坎宫 游魂卦 \n地火明夷");
        this.put("[1,1,1,1,0,1]","坎宫 归魂卦 \n地水师");

        /************************* 离宫 *************************/
        this.put("[0,1,0,0,1,0]","离宫 本宫卦 \n离为火");
        this.put("[0,1,0,0,1,1]","离宫 一世卦 \n火山旅");
        this.put("[0,1,0,0,0,1]","离宫 二世卦 \n火风鼎");
        this.put("[0,1,0,1,0,1]","离宫 三世卦 \n火水未济");
        this.put("[0,1,1,1,0,1]","离宫 四世卦 \n山水蒙");
        this.put("[0,0,1,1,0,1]","离宫 五世卦 \n风水涣");
        this.put("[0,0,0,1,0,1]","离宫 游魂卦 \n天水讼");
        this.put("[0,0,0,0,1,0]","离宫 归魂卦 \n天火同人");

        /************************* 艮宫 *************************/
        this.put("[0,1,1,0,1,1]","艮宫 本宫卦 \n艮为山");
        this.put("[0,1,1,0,1,0]","艮宫 一世卦 \n山火贲");
        this.put("[0,1,1,0,0,0]","艮宫 二世卦 \n山天大畜");
        this.put("[0,1,1,1,0,0]","艮宫 三世卦 \n山泽损");
        this.put("[0,1,0,1,0,0]","艮宫 四世卦 \n火泽睽");
        this.put("[0,0,0,1,0,0]","艮宫 五世卦 \n天泽履");
        this.put("[0,0,1,1,0,0]","艮宫 游魂卦 \n风泽中孚");
        this.put("[0,0,1,0,1,1]","艮宫 归魂卦 \n风山渐");

        /************************* 兑宫 *************************/
        this.put("[1,0,0,1,0,0]","兑宫 本宫卦 \n兑为泽");
        this.put("[1,0,0,1,0,1]","兑宫 一世卦 \n泽水困");
        this.put("[1,0,0,1,1,1]","兑宫 二世卦 \n泽地萃");
        this.put("[1,0,0,0,1,1]","兑宫 三世卦 \n泽山咸");
        this.put("[1,0,1,0,1,1]","兑宫 四世卦 \n水山蹇");
        this.put("[1,1,1,0,1,1]","兑宫 五世卦 \n地山谦");
        this.put("[1,1,0,0,1,1]","兑宫 游魂卦 \n雷山小过");
        this.put("[1,1,0,1,0,0]","兑宫 归魂卦 \n雷泽归妹");
    }
}
package com.pro.allinone;

public class WebModel {

    private String link;

    public String getNam() {
        return nam;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }

    private String nam;
    private String cat;

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    private String act;
    private String type;
    private String des;
    private String isE;
    private String isD;


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getIsE() {
        return isE;
    }

    public void setIsE(String isE) {
        this.isE = isE;
    }

    public String getIsD() {
        return isD;
    }

    public void setIsD(String isD) {
        this.isD = isD;
    }



    private WebModel() {
    }

    public WebModel(String link,String nam,String act, String cat ,String type, String des,String isE, String isD) {
        this.cat=cat;
        this.act=act;
        this.nam=nam;
        this.des=des;
        this.link=link;
        this.type=type;
        this.isE=isE;
        this.isD=isD;



    }


}


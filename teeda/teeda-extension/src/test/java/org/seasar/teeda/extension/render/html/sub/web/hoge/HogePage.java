package org.seasar.teeda.extension.render.html.sub.web.hoge;

import java.util.List;

import org.seasar.teeda.extension.component.TreeNode;

public class HogePage {

    private int hogeNo;

    private String hogeNama;

    private List hogeItems;

    private String ccc;

    private TreeNode tttt;

    public TreeNode getTttt() {
        return tttt;
    }

    public void setTttt(TreeNode tttt) {
        this.tttt = tttt;
    }

    public String getAaa() {
        return null;
    }

    public String getAaaStyleClass() {
        return "mystyle";
    }

    public String getCcc() {
        return ccc;
    }

    public void setCcc(String ccc) {
        this.ccc = ccc;
    }

    public String doBbb() {
        return null;
    }

    public String getHogeNama() {
        return hogeNama;
    }

    public void setHogeNama(String hogeNama) {
        this.hogeNama = hogeNama;
    }

    public int getHogeNo() {
        return hogeNo;
    }

    public void setHogeNo(int hogeNo) {
        this.hogeNo = hogeNo;
    }

    public List getHogeItems() {
        return hogeItems;
    }

    public void setHogeItems(List hogeItems) {
        this.hogeItems = hogeItems;
    }

    public String getHogeRowStyleClass() {
        return "mystyle";
    }

    public String getHogeRowStyle() {
        return "mystyle";
    }

}
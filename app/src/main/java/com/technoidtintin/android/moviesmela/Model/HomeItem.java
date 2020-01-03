package com.technoidtintin.android.moviesmela.Model;

import java.util.List;

public class HomeItem {

    private String typeTitle;
    private List<ListItem> listItemList;

    public HomeItem(String typeTitle, List<ListItem> listItemList) {
        this.typeTitle = typeTitle;
        this.listItemList = listItemList;
    }

    public HomeItem() {
    }

    public String getTypeTitle() {
        return typeTitle;
    }

    public void setTypeTitle(String typeTitle) {
        this.typeTitle = typeTitle;
    }

    public List<ListItem> getListItemList() {
        return listItemList;
    }

    public void setListItemList(List<ListItem> listItemList) {
        this.listItemList = listItemList;
    }
}

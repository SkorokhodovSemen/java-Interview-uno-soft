package org.example;

import java.util.List;

public class ListString {
    private List<String> lineList;
    private int sizeSet;

    public int getSizeSet() {
        return sizeSet;
    }

    public List<String> getLineList() {
        return lineList;
    }

    public ListString(List<String> lineSet) {
        this.lineList = lineSet;
        this.sizeSet = lineSet.size();
    }
}

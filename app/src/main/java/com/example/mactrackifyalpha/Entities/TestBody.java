package com.example.mactrackifyalpha.Entities;

import java.util.List;

public class TestBody {
    private List<TestList> listList;

    public TestBody() {
    }

    public TestBody(List<TestList> listList) {
        this.listList = listList;
    }

    public List<TestList> getListList() {
        return listList;
    }

    public void setListList(List<TestList> listList) {
        this.listList = listList;
    }

    public static class TestList {

        private String name;

        public TestList() {
        }

        public TestList(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

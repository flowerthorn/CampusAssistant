package com.csxy.box.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lihongxin on 2018/5/21.
 */
public class BookObj {
    /**
     * 服务端返回示例
     *
     * {
         "book":[
         {
         "id":"3957",
         "no":"4530",
         "title":"Biosphere 2000 :protecting our global environment ",
         "auther":"Donald G. Kaufman, Cecilia M. Franz.",
         "press":"Kendall/Hunt Pub. Co.",
         "time":"c1996.",
         "search":"X1/8#",
         "place":"五楼西北",
         "state":"可借"
         },
         {
         "id":"6588",
         "no":"10330",
         "title":"BIOS详解",
         "auther":"刘劲鸥编著",
         "press":"海洋出版社",
         "time":"2003",
         "search":"TP316/7",
         "place":"三楼东北",
         "state":"可借"
         }
         ]
         }
     */
    private List<BookItem> book;
    public static class BookItem implements Serializable {
        private String id;
        private String no;
        private String title;
        private String auther;
        private String press;
        private String time;
        private String search;
        private String place;
        private String state;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuther() {
            return auther;
        }

        public void setAuther(String auther) {
            this.auther = auther;
        }

        public String getPress() {
            return press;
        }

        public void setPress(String press) {
            this.press = press;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getSearch() {
            return search;
        }

        public void setSearch(String search) {
            this.search = search;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }

    public List<BookItem> getBook() {
        return book;
    }

    public void setBook(List<BookItem> book) {
        this.book = book;
    }
}

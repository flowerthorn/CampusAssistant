package com.csxy.box.bean;


import java.io.Serializable;
import java.util.List;

/**
 * Created by lihongxin on 2018/5/21.
 */
public class BookObj implements Serializable {
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

    public List<BookItem> getBook() {
        return book;
    }

    public void setBook(List<BookItem> book) {
        this.book = book;
    }
}

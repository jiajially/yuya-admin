package com.liug.model.entity;

/**
 * Created by liugang on 2017/7/2.
 * 文字识别
 */
public class CharRecg {
    //识别主体，文本内容
    String content;
    //匹配字符
    String character;
    //匹配数量
    Integer count;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "charRecg{" +
                "content='" + content + '\'' +
                ", character='" + character + '\'' +
                ", count=" + count +
                '}';
    }
}

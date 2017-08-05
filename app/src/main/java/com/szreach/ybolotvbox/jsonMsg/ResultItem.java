package com.szreach.ybolotvbox.jsonMsg;

public class ResultItem<T> {

    private HeaderItem msgHeader;
    private T data;

    public HeaderItem getMsgHeader() {
        return msgHeader;
    }

    public void setMsgHeader(HeaderItem msgHeader) {
        this.msgHeader = msgHeader;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

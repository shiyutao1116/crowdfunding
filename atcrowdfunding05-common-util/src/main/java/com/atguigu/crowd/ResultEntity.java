package com.atguigu.crowd;

/**
 * @author shiyutao
 * @create 2022-01-08 20:52
 */
public class ResultEntity<T> {

    public static final String SUCCESS="SUCCESS";
    public static final String FAILED="failed";
    private String result;
    private String message;
    private T data;

    public static <E> ResultEntity<E> successwithoutdata(){
        return new ResultEntity<E>(SUCCESS,null,null);

    }
    public static <E> ResultEntity<E> successwithdata(E data){
        return new ResultEntity<E>(SUCCESS,null,data);

    }
    public static <E> ResultEntity<E> failed(String message){
        return new ResultEntity<E>(FAILED,message,null);

    }

    public ResultEntity() {
    }

    public ResultEntity(String result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "com.atguigu.crowd.ResultEntity{" +
                "result='" + result + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}

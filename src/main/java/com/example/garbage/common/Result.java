package com.example.garbage.common;
import lombok.Data;
@Data
public class Result {
    private int code; private String msg; private Object data;
    public static Result ok() { return ok(null); }
    public static Result ok(Object data) { Result r = new Result(); r.code=200; r.msg="成功"; r.data=data; return r; }
    public static Result fail(String msg) { Result r = new Result(); r.code=500; r.msg=msg; return r; }
}

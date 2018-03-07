package com.liug.common.util;

import com.liug.common.ssh.SshResult;

/**
 * Created by liugang on 2017/6/1.
 * <p>
 * 规定用于传输数据的结构体
 */
public class Result {
    private int _code;
    private String _msg;
    private Object _data = "";

    public Result(int code, String msg, Object data) {
        this._code = code;
        this._data = data;
        this._msg = msg;
    }

    public Result(int code, String msg) {
        this._code = code;
        this._msg = msg;
    }

    public Result() {

    }

    public Result(SshResult sshResult) {
        if (sshResult.getExitStatus() == 0) {
            this._code = ResponseCode.success.getCode();
            this._data = sshResult.getContent();
        } else {
            this._code = ResponseCode.error.getCode();
            this._msg = sshResult.getContent();
        }
    }


    public static Result instance(int code, String msg) {
        return new Result(code, msg);
    }
    public static Result transform(SshResult sshResult) {
        return new Result(sshResult);
    }

    public static Result instance(ResponseCode responseCode) {
        return new Result(responseCode.getCode(), responseCode.getMsg());
    }

    public static Result success() {
        return new Result(ResponseCode.success.getCode(), ResponseCode.success.getMsg());
    }

    public static Result success(Object data) {
        return new Result(ResponseCode.success.getCode(), ResponseCode.success.getMsg(), data);
    }

    public static Result error() {
        return new Result(ResponseCode.error.getCode(), ResponseCode.error.getMsg());
    }

    public static Result error(String msg) {
        return new Result(ResponseCode.error.getCode(), msg);
    }

    @Override
    public String toString() {
        return "Result{" +
                "_code=" + _code +
                ", _msg='" + _msg + '\'' +
                ", _data=" + _data +
                '}';
    }

    public int getCode() {
        return _code;
    }

    public void setCode(int _code) {
        this._code = _code;
    }

    public String getMsg() {
        return _msg;
    }

    public void setMsg(String _msg) {
        this._msg = _msg;
    }

    public Object getData() {
        return _data;
    }

    public void setData(Object _data) {
        this._data = _data;
    }


}

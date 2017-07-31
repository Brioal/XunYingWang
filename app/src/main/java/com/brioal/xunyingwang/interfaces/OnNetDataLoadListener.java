package com.brioal.xunyingwang.interfaces;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/31.
 */

public interface OnNetDataLoadListener<T> {
    void success(T bean);

    void failed(String errorMsg);
}

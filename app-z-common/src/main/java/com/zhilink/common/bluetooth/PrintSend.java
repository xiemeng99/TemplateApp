package com.zhilink.common.bluetooth;

import android.bluetooth.BluetoothSocket;

import com.zhilink.utils.LogUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * @author xiemeng
 * @des 打印机发送文本
 * @date 2017/2/20
 */
public class PrintSend {
    private static final String TAG = "PrintSend";
    /**
     * 蓝牙通信
     */
    private BluetoothSocket mBlueSocket;
    /**
     * 无线通信
     */
    private Socket mSocket;

    private String charsetName = "GB2312";

    public String getCharsetName() {
        return charsetName;
    }

    public void setCharsetName(String charsetName) {
        this.charsetName = charsetName;
    }

    public PrintSend(BluetoothSocket socket) {
        mBlueSocket = socket;
    }

    public PrintSend(Socket socket) {
        mSocket = socket;
    }

    /**
     * 蓝牙发送字符串
     */
    public void sendBtMessage(final String message) {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) {
                OutputStream tmpOut;
                try {
                    tmpOut = mBlueSocket.getOutputStream();
                    byte[] send = getBytes(message);
                    tmpOut.write(send, 0, send.length);
                    tmpOut.flush();
                } catch (IOException ex) {
                    LogUtils.e(TAG, "Exception during write" + ex);
                } catch (Exception ex) {
                    LogUtils.e(TAG, "write异常" + ex);
                }
                e.onNext(message);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        LogUtils.d(TAG, s);
                    }
                });
    }

    /**
     * 无线发送字符串
     */
    public void sendMessage(final String message) {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) {
                OutputStream tmpOut;
                try {
                    tmpOut = mSocket.getOutputStream();
                    byte[] send = getBytes(message);
                    tmpOut.write(send, 0, send.length);
                    tmpOut.flush();
                } catch (IOException ex) {
                    LogUtils.e(TAG, "Exception during write" + ex);
                } catch (Exception ex) {
                    LogUtils.e(TAG, "write异常" + ex);
                }
                e.onNext(message);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        LogUtils.d(TAG, s);
                    }
                });
    }

    /**
     * 转换成 byte[]
     */
    private byte[] getBytes(String message) {
        byte[] send = null;
        if (message.length() > 0) {
            try {
                send = message.getBytes(charsetName);
            } catch (UnsupportedEncodingException e) {
                send = message.getBytes();
            }
        }
        return send;
    }

}

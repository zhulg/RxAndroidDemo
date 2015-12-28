package com.lg.rxjavatest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lianggang on 2015/12/28.
 * RxJava常用使用方法，Observable的创建方式create,just,from
 * 线程切换，map(),flatmap(),filter使用等，后续慢慢添加使用方法
 * 代码每项功能有数字表示，放开每项功能注释即可查看运行
 *
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getName();
    public static final String MY_NAME = "lg";
    public static HashMap<String,ArrayList<String>> Lou_Hao = new HashMap<String,ArrayList<String>>();
    public static final String A_QU = "A_build";//假设小区分A区，B区。A区可能有A1,A2...楼等。
    public static final String B_QU = "B_build";//假设小区分A区，B区。B区可能有B1,B2...楼等

    static {
        ArrayList<String> A_qu = new ArrayList<>();
        A_qu.add("A1");
        A_qu.add("A2");
        A_qu.add("A3");
        ArrayList<String> B_qu = new ArrayList<>();
        B_qu.add("B1");
        B_qu.add("B2");
        B_qu.add("B3");
        B_qu.add("B4");
        B_qu.add("B5");
        B_qu.add("B6");
        Lou_Hao.put(A_QU, A_qu);
        Lou_Hao.put(B_QU, B_qu);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1.===========create() 方法来创建一个 Observable,并订阅 begin========
//        myObservable.subscribe(mySubscriber);
        //=============create() 方法来创建一个 Observable,并订阅 end==========


        //2.=========== just创建Observable 并订阅begin=======================
//        Observable<String>  justObservable = Observable.just("hello,world");
//        justObservable.subscribe(mySubscriber);
        //=========== just创建Observable 并订阅end===========================


        //3.========== from创建Observable 并订阅begin========================
//        String  name[] =  {"hello,world","hello,zhulg"};
//        Observable<String> fromObservable =  Observable.from(name);
//        fromObservable.subscribe(mySubscriber);
        //========== from创建Observable 并订阅end============================


        //4.=======Action的订阅者实现,action内部实现了subscriber. begin ======
//        Observable<String>  actionObservable = Observable.just("hello,lg");
//        actionObservable.subscribe(onNextAction/*,onErrorAction,onCompletedAction*/);
        //.=======Action的订阅者实现,action内部实现了subscriber.end ==========


        //5.======线程切换 Schedulers切换  begin =============================
/*        Observable.just("hello lg","hello rxjava","hello android")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(TAG, "====Test scheduler s=" + s);
                    }
                });*/
        //.======线程切换 Schedulers切换  end =============================


        //6.======事件变换 map()使用  begin =============================
/*        Observable.just("hello")
                  .map(new Func1<String, String >() {
                      @Override
                      public String call(String s) {
                          //示例添加点字符（在此对数据进行加工处理）
                          return s+","+MY_NAME;
                      }
                  })
                  .map(new Func1<String, Integer>() {
                      @Override
                      public Integer call(String s) {
                          return s.length();
                      }
                  })
                  .subscribe(new Action1<Integer>() {
                      @Override
                      public void call(Integer s) {
                          Log.d(TAG, "===Map after s length=" + s);
                      }
                  });*/
        //.======事件变换 map()使用  end ================================


        //7.======事件变换 flatmap()使用  begin =============================
        /*
          场景描述：小区有A，B区，A区有A1,A2,A3.B区有B1，B2，B3..等
          实现：查看所有A区楼号和B区所有具体楼号
         */
/*        Observable.just(A_QU,B_QU)
                  .flatMap(new Func1<String, Observable<String>>() {
                      @Override
                      public Observable<String> call(String s) {
                          return Observable.from(Lou_Hao.get(s));
                      }
                  })
                  .subscribe(new Action1<String>() {
                      @Override
                      public void call(String s) {
                          Log.d(TAG, "====Flatmap() call s=" + s);
                      }
                  });*/
        //.======事件变换 flatmap()使用  end ================================


        //8.======事件变换 flatmap()与filter结合使用  begin =============================
        /*
          场景描述：小区有A，B区，A区有A1,A2,A3.B区有B1，B2，B3..等
          实现：查看所有A区楼号和B区所有具体楼号，开发商B6号楼为内部团购需要过滤掉
         */
        Observable.just(A_QU,B_QU)
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        return Observable.from(Lou_Hao.get(s));
                    }
                })
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return !s.equals("B6");
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(TAG, "====Flatmap() call s=" + s);
                    }
                });
        //.======事件变换 flatmap()与filter结合使用 end ======================


    }





    //create() 方法来创建一个 Observable,并在内部定义相关事件规则
    Observable<String> myObservable = Observable.create(
            new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> sub) {
                    sub.onNext("Hello, world!");
                    sub.onCompleted();
                }
            }
    );
    //创建订阅者
    Subscriber<String> mySubscriber = new Subscriber<String>() {
        @Override
        public void onNext(String s) {
            Log.d(TAG,"onNext s="+s);
        }
        @Override
        public void onCompleted() {
            Log.d(TAG,"onCompleted");
        }
        @Override
        public void onError(Throwable e) { }
    };

    //无参数，无返回值
    Action0  onCompletedAction =  new Action0() {
        //当做完成使用
        @Override
        public void call() {
            Log.d(TAG,"onCompletedAction  call");
        }
    };

    //带参数，无返回值
    Action1<String>  onNextAction  = new Action1<String>() {
        //相当于onnext
        @Override
        public void call(String s) {
            Log.d(TAG,"Action1  call s="+s);
        }
    };

    Action1<Throwable> onErrorAction = new Action1<Throwable>() {
        // 当做 onError()
        @Override
        public void call(Throwable throwable) {
            Log.d(TAG,"Throwable  call throwable="+throwable.toString());
        }
    };


}

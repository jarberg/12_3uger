package controller;


public class ViewController extends ViewControllerAbs {

    private static final ViewController singleInstance = new ViewController();

    public static ViewController getSingleInstance(){
        return singleInstance;
    }




}

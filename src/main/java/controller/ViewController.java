package controller;

import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_main.GUI;

public class ViewController extends ViewControllerAbs {

    private static final ViewController singleInstance = new ViewController();

    public static ViewController getSingleInstance(){
        return singleInstance;
    }




}

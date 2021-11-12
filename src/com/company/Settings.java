package com.company;

import java.awt.*;

public final class Settings {
    public static final int CASE_SIZE = 100;
    public static final int WIDTH = (CASE_SIZE*8);
    public static final int HEIGHT = (CASE_SIZE*7);
    public static final int REAL_WIDTH = WIDTH-16; // without window border
    public static final int REAL_HEIGHT = HEIGHT-35; // without window border
    public static final int HEIGHT_CASES = 7;
    public static final int WIDTH_CASES = 8;
    public static final int RATIO_WIDTH_CASES = (Settings.REAL_WIDTH / Settings.WIDTH_CASES);
    public static final int RATIO_HEIGHT_CASES = (Settings.REAL_HEIGHT / Settings.HEIGHT_CASES);
    public static final Color CASE_COLOR1 = Color.decode("#769656");
    public static final Color CASE_COLOR2 = Color.decode("#EEEED2");
    private Settings(){
    }
}
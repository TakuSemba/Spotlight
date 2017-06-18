package com.takusemba.spotlightapp;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Daichi Furiya on 2015/08/06.
 */
public class Display {

  public static int getActionBarHeight(Context context) {
    int height = 0;
    TypedValue tv = new TypedValue();
    if (context.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
      height = TypedValue.complexToDimensionPixelSize(tv.data,
          context.getResources().getDisplayMetrics());
    }
    return height;
  }

  public static Size getDisplaySize(Context context) {
    Point point = new Point();
    WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    manager.getDefaultDisplay().getSize(point);
    return new Size(point.x, point.y);
  }

  public static void setFullScreenLayoutFlag(Window window) {
    View decorView = window.getDecorView();
    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
  }

  public static Rect getGlobalVisibleRect(View view) {
    Rect rect = new Rect();
    view.getGlobalVisibleRect(rect);
    return rect;
  }

  public static Rect getDrawingRect(View view) {
    Rect rect = new Rect();
    view.getDrawingRect(rect);
    return rect;
  }

  public static void hideSystemUi(Window window) {
    int options = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      options = options ^ View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    } else {
      options = options | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
    }
    window.getDecorView().setSystemUiVisibility(options);
  }

  public static void showSystemUi(Window window) {
    window.getDecorView()
        .setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
  }
}

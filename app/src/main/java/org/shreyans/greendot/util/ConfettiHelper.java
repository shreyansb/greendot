package org.shreyans.greendot.util;

import android.graphics.Color;
import android.view.ViewGroup;

import com.github.jinatonic.confetti.CommonConfetti;
import com.github.jinatonic.confetti.ConfettiManager;
import com.github.jinatonic.confetti.ConfettiSource;

/**
 * Created by shreyans on 7/4/17.
 */

public class ConfettiHelper {

    private static final String TAG = ConfettiHelper.class.getSimpleName();

    private int[] confettiColors = new int[] {
            Color.parseColor("#ffffff"),
            Color.parseColor("#2d7ddc"),
            Color.parseColor("#f68809"),
            Color.parseColor("#f0ce3b"),
            Color.parseColor("#50e3c2"),
            Color.parseColor("#ff5043")
    };

    private int longConfettiMillis = 5000;

    public void heartConfetti(ViewGroup container, int heartX, int heartY) {
        CommonConfetti.explosion(container, heartX, heartY, confettiColors).oneShot();
    }

    public void screenConteffit(ViewGroup container) {
        CommonConfetti.rainingConfetti(container, confettiColors).stream(longConfettiMillis);
    }
}

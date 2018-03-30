package animation.Attention;

import android.view.View;

import animation.BaseAnimatorSet;


public class Flash extends BaseAnimatorSet {
    public Flash() {
        duration = 1000;
    }

    @Override
    public void setAnimation(View view) {
        animatorSet.playTogether(//
                com.nineoldandroids.animation.ObjectAnimator.ofFloat(view, "alpha", 1, 0, 1, 0, 1));
    }
}

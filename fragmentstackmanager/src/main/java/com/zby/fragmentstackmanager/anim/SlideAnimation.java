package com.zby.fragmentstackmanager.anim;

import com.zby.fragmentstackmanager.R;

/**
 * @author ZhuBingYang
 */
public class SlideAnimation extends TransactionAnimation {

    public SlideAnimation() {
        super(R.anim.anim_slide_right_in, R.anim.anim_slide_left_out, R.anim.anim_slide_left_in, R.anim.anim_slide_right_out);
    }
}

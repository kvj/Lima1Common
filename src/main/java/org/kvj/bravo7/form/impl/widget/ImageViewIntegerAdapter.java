package org.kvj.bravo7.form.impl.widget;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import org.kvj.bravo7.form.ViewBundleAdapter;
import org.kvj.bravo7.form.impl.bundle.IntegerBundleAdapter;

/**
 * Created by vorobyev on 11/26/15.
 */
public class ImageViewIntegerAdapter extends ViewBundleAdapter<ImageView, Integer>
    implements View.OnClickListener {

    private final int drawableOn;
    private final int drawableOff;
    private boolean checked = false;

    public ImageViewIntegerAdapter(int res, int drawableOff, int drawableOn, Integer def) {
        super(new IntegerBundleAdapter(), res, def);
        this.drawableOn = drawableOn;
        this.drawableOff = drawableOff;
    }

    @Override
    public Integer getWidgetValue(Bundle bundle) {
        return checked? 1: 0;
    }

    @Override
    public void setWidgetValue(Integer value, Bundle bundle) {
        checked = value > 0;
//        logger.d("Set image value:", value, checked, drawableOn, drawableOff);
        getView().setImageResource(checked? drawableOn: drawableOff);
        getView().setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        checked = !checked;
//        logger.d("Click image value:", checked);
        getView().setImageResource(checked? drawableOn: drawableOff);
    }
}

//package com.example.tpjava;
//
//import android.content.Context;
//import android.graphics.LinearGradient;
//import android.graphics.Shader;
//import android.util.AttributeSet;
//import androidx.appcompat.widget.AppCompatTextView;
//import androidx.core.content.ContextCompat;
//
//public class GradientTextView extends AppCompatTextView {
//
//    public GradientTextView(Context context) {
//        super(context);
//    }
//
//    public GradientTextView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public GradientTextView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//    }
//
//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        if (w > 0) {
//            Shader textShader = new LinearGradient(
//                    0, 0, getWidth(), 0,
//                    new int[]{
//                            ContextCompat.getColor(getContext(), R.color.gradTextStartColor),
//                            ContextCompat.getColor(getContext(), R.color.gradTextEndColor)
//                    },
//                    null, Shader.TileMode.CLAMP);
//            getPaint().setShader(textShader);
//        }
//    }
//}

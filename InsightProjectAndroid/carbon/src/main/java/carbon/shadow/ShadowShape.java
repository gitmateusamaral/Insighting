package carbon.shadow;

import android.graphics.Outline;
import android.os.Build;
import android.view.View;
import android.view.ViewOutlineProvider;

import carbon.widget.CornerView;

/**
 * Created by Marcin on 2015-04-23.
 */
public enum ShadowShape {
    RECT, ROUND_RECT, CIRCLE;

    public static ViewOutlineProvider viewOutlineProvider;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewOutlineProvider = new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    ShadowShape shadowShape = ((ShadowView) view).getShadowShape();
                    if (shadowShape == RECT) {
                        outline.setRect(0, 0, view.getWidth(), view.getHeight());
                    } else if (shadowShape == ROUND_RECT) {
                        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), ((CornerView) view).getCornerRadius());
                    } else if (shadowShape == CIRCLE) {
                        outline.setOval(0, 0, view.getWidth(), view.getHeight());
                    }
                }
            };
        }
    }
}

package carbon.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.view.menu.MenuBuilder;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;

import carbon.CarbonContextWrapper;
import carbon.R;

/**
 * Created by Marcin on 2016-02-16.
 */
public class FloatingActionMenu extends PopupWindow {
    private final Handler handler;
    private Menu menu;

    MenuItem.OnMenuItemClickListener listener;

    public FloatingActionMenu(Context context) {
        super(new LinearLayout(context), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final LinearLayout content = (LinearLayout) getContentView();
        content.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        content.setOrientation(android.widget.LinearLayout.VERTICAL);
        content.setPadding(0, content.getResources().getDimensionPixelSize(R.dimen.carbon_paddingHalf), 0, content.getResources().getDimensionPixelSize(R.dimen.carbon_paddingHalf));

        setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));

        setTouchable(true);
        setFocusable(true);
        setOutsideTouchable(true);
        setAnimationStyle(0);
        setClippingEnabled(false);

        handler = new Handler();
    }

    public boolean show(View anchor) {
        final LinearLayout content = (LinearLayout) getContentView();

        int[] location = new int[2];
        anchor.getLocationOnScreen(location);

        WindowManager wm = (WindowManager) anchor.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        boolean left = location[0] < display.getWidth() + anchor.getWidth() - location[0];
        boolean top = location[1] < display.getHeight() + anchor.getHeight() - location[1];

        content.removeAllViews();

        for (int i = 0; i < menu.size(); i++) {
            final MenuItem item = menu.getItem(i);
            LayoutInflater inflater = LayoutInflater.from(content.getContext());
            final LinearLayout view = (LinearLayout) inflater.inflate(left ? R.layout.carbon_floatingactionmenu_left : R.layout.carbon_floatingactionmenu_right, content, false);
            TextView tooltip = (TextView) view.findViewById(R.id.carbon_tooltip);
            FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.carbon_fab);

            tooltip.setText(item.getTitle());
            fab.setImageDrawable(item.getIcon());
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onMenuItemClick(item);
                    dismiss();
                }
            });
            content.addView(view);

            view.setVisibilityImmediate(View.INVISIBLE);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setVisibility(View.VISIBLE);
                }
            }, top ? i * 50 : (menu.size() - 1 - i) * 50);
        }

        content.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        super.showAtLocation(anchor, Gravity.TOP | Gravity.LEFT, 0, 0);
        if (!left & top) {  // right top
            update(location[0] - content.getMeasuredWidth() + anchor.getWidth(), location[1] + anchor.getHeight(), content.getMeasuredWidth(), content.getMeasuredHeight());
        } else if (!left & !top) {  // right bottom
            update(location[0] - content.getMeasuredWidth() + anchor.getWidth(), location[1] - content.getMeasuredHeight(), content.getMeasuredWidth(), content.getMeasuredHeight());
        } else if (left & !top) { // left bottom
            update(location[0], location[1] - content.getMeasuredHeight(), content.getMeasuredWidth(), content.getMeasuredHeight());
        } else {    // left top
            update(location[0], location[1] + anchor.getHeight(), content.getMeasuredWidth(), content.getMeasuredHeight());
        }

        return true;
    }

    @Override
    public void dismiss() {
        final LinearLayout content = (LinearLayout) getContentView();
        LinearLayout child = null;
        for (int i = 0; i < content.getChildCount(); i++) {
            child = (LinearLayout) content.getChildAt(i);
            child.setVisibility(View.INVISIBLE);
        }
        if (child != null) {
            child.getAnimator().addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    dismissImmediate();
                }
            });
        }
    }

    public void dismissImmediate() {
        super.dismiss();
    }

    public void setMenu(int resId) {
        Menu menu = new MenuBuilder(new CarbonContextWrapper(getContentView().getContext()));
        MenuInflater inflater = new MenuInflater(getContentView().getContext());
        inflater.inflate(resId, menu);
        setMenu(menu);
    }

    public void setMenu(final Menu menu) {
        this.menu = menu;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener listener) {
        this.listener = listener;
    }
}

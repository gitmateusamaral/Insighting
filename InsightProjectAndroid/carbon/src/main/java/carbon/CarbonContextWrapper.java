package carbon;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.view.LayoutInflater;

/**
 * Created by Marcin on 2015-06-29.
 */
public class CarbonContextWrapper extends ContextWrapper {
    private CarbonLayoutInflater mInflater;
    private CarbonResources resources;

    public CarbonContextWrapper(Context base) {
        super(base);
        resources = new CarbonResources(getAssets(), super.getResources().getDisplayMetrics(), super.getResources().getConfiguration());
    }

    @Override
    public Resources getResources() {
        return resources;
    }

    @Override
    public Object getSystemService(String name) {
        if (LAYOUT_INFLATER_SERVICE.equals(name)) {
            if (mInflater == null) {
                mInflater = new CarbonLayoutInflater(LayoutInflater.from(getBaseContext()), this);
            }
            return mInflater;
        }
        return super.getSystemService(name);
    }

    @Override
    public Resources.Theme getTheme() {
        return super.getTheme();
    }
}

package com.example.heaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.heaapp.R;
import com.example.heaapp.model.airweather.OnboardingItem;

import java.util.List;

public class OnboardingPagerAdapter extends PagerAdapter {

    private Context context;
    private List<OnboardingItem> onboardingItemList;

    public OnboardingPagerAdapter(Context context, List<OnboardingItem> onboardingItemList) {
        this.context = context;
        this.onboardingItemList = onboardingItemList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutOnboarding = layoutInflater.inflate(R.layout.layout_screen, null);

        ImageView image_intro = layoutOnboarding.findViewById(R.id.img_intro);
        TextView txt_title = layoutOnboarding.findViewById(R.id.txt_intro_title);
        TextView txt_description = layoutOnboarding.findViewById(R.id.txt_intro_description);

        txt_title.setText(onboardingItemList.get(position).getTitle());
        txt_description.setText(onboardingItemList.get(position).getDescription());
        image_intro.setImageResource(onboardingItemList.get(position).getOnboardingImg());

        container.addView(layoutOnboarding);
        return layoutOnboarding;
    }

    @Override
    public int getCount() {
        return onboardingItemList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

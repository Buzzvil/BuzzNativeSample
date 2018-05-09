package sample.buzznative.buzzvil.com.buzznativesample;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.buzzvil.buzzad.BuzzAdError;
import com.buzzvil.buzzad.nativead.Ad;
import com.buzzvil.buzzad.nativead.AdListener;
import com.buzzvil.buzzad.nativead.NativeAd;
import com.buzzvil.buzzad.nativead.NativeAdView;

/**
 * Created by jim on 2018. 5. 9..
 */

public class NativeAdActivity extends Activity{
    public static final String PLACEMENT_ID = "YOUR_APP_KEY";
    public static final String TAG = "NativeAdActivity";
    public static final int FEED_STYLE = 1;
    public static final int BANNER_STYLE = 2;
    private NativeAdView nativeAdView;
    private Button btnLoadAd;
    private TextView tvAdResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView();

        bindViews();

        setClickListener();
    }

    void setContentView() {
        if(getIntent().getIntExtra("type", FEED_STYLE) == FEED_STYLE){
            setContentView(R.layout.activity_feed_style_native_ad);
        } else if(getIntent().getIntExtra("type", FEED_STYLE) == BANNER_STYLE){
            setContentView(R.layout.activity_banner_style_native_ad);
        }
    }

    void bindViews() {
        btnLoadAd = (Button) findViewById(R.id.btnLoadAd);
        tvAdResponse = (TextView) findViewById(R.id.tvAdResponse);
        nativeAdView = (NativeAdView) findViewById(R.id.nativeAdView);
    }

    void setClickListener() {
        btnLoadAd.setOnClickListener(btnClickListener);
    }

    void requestAd() {
        NativeAd nativeAd = new NativeAd(this, PLACEMENT_ID);
        nativeAd.setAdListener(nativeAdListener);
        nativeAd.loadAd();

        tvAdResponse.setText("Loading");
    }

    void setNativeAdView(Ad ad) {
        nativeAdView.setVisibility(View.VISIBLE);
        nativeAdView.setTitleView(nativeAdView.findViewById(R.id.tvTitle));
        nativeAdView.setDescriptionView(nativeAdView.findViewById(R.id.tvDescription));
        nativeAdView.setImageView(nativeAdView.findViewById(R.id.ivCoverImage));
        nativeAdView.setIconView(nativeAdView.findViewById(R.id.ivIcon));
        nativeAdView.setCallToActionView(nativeAdView.findViewById(R.id.btnCTA));
        nativeAdView.setSponsoredView(nativeAdView.findViewById(R.id.tvSponsored));
        nativeAdView.setAd(ad);

        if (TextUtils.isEmpty(ad.getCallToAction()) == false) {
            ((Button)nativeAdView.findViewById(R.id.btnCTA)).setText(ad.getCallToAction());
            nativeAdView.findViewById(R.id.btnCTA).setVisibility(View.VISIBLE);
        } else {
            nativeAdView.findViewById(R.id.btnCTA).setVisibility(View.GONE);
        }
    }

    View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnLoadAd:
                    requestAd();
                    break;
            }
        }
    };

    AdListener nativeAdListener = new AdListener() {
        @Override
        public void onError(BuzzAdError error) {
            Log.e(TAG, "AdListener : onError - " + error);
            tvAdResponse.setText("AdListener : onError - " + error);
            nativeAdView.setVisibility(View.GONE);
        }

        @Override
        public void onAdLoaded(Ad ad) {
            Log.e(TAG, "AdListener : onAdLoaded - " + (ad != null? "Success" : "Fail"));
            tvAdResponse.setText("onAdLoaded - " + (ad != null? "Success" : "Fail"));

            if(ad == null) {
                nativeAdView.setVisibility(View.GONE);
            } else {
                nativeAdView.setVisibility(View.VISIBLE);
                setNativeAdView(ad);
            }
        }

        @Override
        public void onImpressed(Ad ad) {
            Log.d(TAG, "AdListener : onImpressed - " + ad);
        }

        @Override
        public void onClick(Ad ad) {
            Log.d(TAG, "AdListener : onClicked - " + ad);
        }
    };

}

package com.swati.ebook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import com.google.android.material.switchmaterial.SwitchMaterial;


public class Settings extends AppCompatActivity {

    private View parentView;
    public SwitchCompat themeSwitch1;
    private TextView themeTv,settingsTv,contactusTv,helpTv,reportTv, aboutEbookTv;
    private UserSettings settings;
    private ImageView contactUsIcon, helpIcon, reportIcon, aboutIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //theme change
        settings = (UserSettings) getApplication();
        initWidgets();
        loadSharedPreferences();
        initSwitchListener();

//----------------------------------------------------------------------------
        //back button
        ImageView imageArrow = findViewById(R.id.backArrowIcon);
        imageArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, Inner_page.class);
                startActivity(intent);
                finish();
            }
        });
//------------------------------------------------------------------------------

    //setting report,about,help,contact ,send feedback icon
    contactUsIcon = findViewById(R.id.arrowIcon1);
    helpIcon = findViewById(R.id.arrowIcon2);
    reportIcon = findViewById(R.id.arrowIcon3);
    aboutIcon = findViewById(R.id.arrowIcon4);

    contactUsIcon.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Settings.this, AboutEbook.class);
            intent.putExtra("Content", "If you have any questions or feedback we would love to hear from you! Please don't hesitate to get in touch with us using the contact below-\n" + "\n" + "abc@gmail.com or +919876445322." + "\n" + "We are always here to help you!");
            startActivity(intent);
            finish();
        }
    });

    helpIcon.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Settings.this, AboutEbook.class);
            intent.putExtra("Content", "Hi, Welcome to the help section of our ebook app! If you're having any issues or questions about using our app, you're in the right place. Below, you'll find some answers to frequently asked questions that may help you out.\n" +
                    "\n" +
                    "Q: How do I download an ebook?\n" +
                    "A: To download an ebook, simply find the book you want to download in our library, and tap on the Download button. Once the book is downloaded, it will be available for you to read offline.");
            startActivity(intent);
            finish();
        }
    });

    reportIcon.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Settings.this, AboutEbook.class);
            intent.putExtra("Content", "Thank you for taking the time to report a problem with our app. We're sorry to hear that you're experiencing an issue and would like to help resolve it as soon as possible." +
                    "\n" +
                    "To better assist you, please provide a clear and concise description of the problem you're experiencing, including any error messages or other relevant information. If possible, please also provide steps to reproduce the problem and any other apps or settings that may be relevant." +
                    "\n" +
                    "Additionally, please let us know what device and OS version you are using, as this can help us narrow down the issue. If you'd like us to follow up with you, please provide your contact information." +
                    "\n" +
                    "We are committed to fixing the issue as soon as possible and will keep you updated on our progress. In the meantime, we encourage you to update the app to the latest version, as this may resolve the issue." +
                    "\n" +
                    "Thank you again for your feedback and for helping us improve our app. We value your input and are dedicated to providing a high-quality user experience.");
            startActivity(intent);
            finish();
        }
    });

    aboutIcon.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Settings.this, AboutEbook.class);
            intent.putExtra("Content","Welcome to our ebook app! We're excited to bring you a convenient and accessible way to read your favorite books anytime, anywhere.\n" + "\n" + "At [TRUBA BOOK GENERAL], we believe that reading should be easy and enjoyable. That's why we've designed an app that's user-friendly and packed with features to enhance your reading experience\n.Our app is perfect for those who love to read on the go. With our collection of ebooks, you can use speech functionality of our app.\n" + "\n" + "We're committed to provide a seamless and enjoyable reading experience for our users. If you ever have any questions or feedback, please don't hesitate to contact our support team.\n" + "\n" + "We're here to help you make the most out of your reading experience.");
            startActivity(intent);
            finish();
        }
    });


    }

    private void initWidgets(){
        parentView= findViewById(R.id.parentViewSettings);
        settingsTv = findViewById(R.id.settingsTV);
        themeSwitch1 = findViewById(R.id.themeSwitch);
        themeTv = findViewById(R.id.changeTheme);
        themeSwitch1 = findViewById(R.id.themeSwitch);
        contactusTv = findViewById(R.id.contactUs);
        helpTv = findViewById(R.id.help);
        reportTv = findViewById(R.id.reportProblem);
        aboutEbookTv = findViewById(R.id.aboutEbook);
    }

    private void loadSharedPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences(UserSettings.PREFERENCES , MODE_PRIVATE);
        String theme = sharedPreferences.getString(UserSettings.CUSTOM_THEME , UserSettings.LIGHT_THEME);
        settings.setCustomTheme(theme);
        updateView();
    }

    private void initSwitchListener(){

        themeSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
        @Override
                public void onCheckedChanged(CompoundButton compoundButton , boolean checked){
                  if(checked){
                      settings.setCustomTheme(UserSettings.DARK_THEME);
                  }
                  else {
                      settings.setCustomTheme(UserSettings.LIGHT_THEME);
                  }
                      SharedPreferences.Editor editor = getSharedPreferences(UserSettings.PREFERENCES , MODE_PRIVATE).edit();
                      editor.putString(UserSettings.CUSTOM_THEME , settings.getCustomTheme());
                      editor.apply();
                      updateView();
                  }
        });
    }

    private void updateView(){
        final int black = ContextCompat.getColor(this, R.color.black);
        final int white = ContextCompat.getColor(this, R.color.white);

        //change(dark)
        if(settings.getCustomTheme().equals(UserSettings.DARK_THEME)) {
            //settings activity
            themeSwitch1.setChecked(true);
            parentView.setBackgroundColor(black);
            settingsTv.setTextColor(white);
            themeTv.setText("DarkMode");
            themeTv.setTextColor(white);
            helpTv.setTextColor(white);
            contactusTv.setTextColor(white);
            reportTv.setTextColor(white);
            aboutEbookTv.setTextColor(white);
        }

        // no change(light)
        else{
            //settings activity
            themeSwitch1.setChecked(false);
            parentView.setBackgroundColor(white);
            settingsTv.setTextColor(black);
            themeTv.setText("Change theme");
            themeTv.setTextColor(black);
            helpTv.setTextColor(black);
            contactusTv.setTextColor(black);
            reportTv.setTextColor(black);
            aboutEbookTv.setTextColor(black);

        }
    }

    //------------------------------------------------------
    //for moving back to last activity
    public void onBackPressed(){
        Intent intent = new Intent(this,Inner_page.class);
        startActivity(intent);
        finish();
    }


}
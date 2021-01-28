package com.nmakadiya.expensemanager;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class aboutus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Element versionElement = new Element();
        versionElement.setTitle("Version 1.1.0");

     /*   Element adsElement = new Element();
        adsElement.setTitle("Advertise with us");*/

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.mipmap.ic_iconmain1)
                .setDescription("Expense Manager \r\nDeveloped by: Neel Makadiya. \r\nThanks for Choosing this app.If you notice an error,a bug,an an incompatibility with your devise or any problem, please contact us.Many users sent us feedback.We appreciate your comment.")
                .addItem(versionElement)
                //   .addItem(adsElement)
                .addGroup("Connect with us")
                .addEmail("nmakadiya1@gmail.com", "Contact via Email")
                .addWebsite("https://sites.google.com/view/expensemanager-privacy-policy/home", "Privacy Policy")
                .addFacebook("neel.makadiya.73")
                .addTwitter("MakadiyaN")
                // .addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")
                .addPlayStore("com.nmakadiya.expensemanager")
                .addGitHub("201801190", "Follow us on GitHub")
                .addInstagram("neel.makadiya")
                .create();

        setContentView(aboutPage);
    }
}

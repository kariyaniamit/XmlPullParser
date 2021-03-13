package com.example.xmlparser;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    TextView tx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx = (TextView) findViewById(R.id.textView);
        parseXML();
    }

    public void parseXML() {
        XmlPullParserFactory parserFactory;
        try {
            parserFactory= XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream istream = getAssets().open("user.xml");
            //parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
            parser.setInput(istream,null);
            processParsing(parser);
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }

    }

    private void processParsing(XmlPullParser parser) throws IOException,XmlPullParserException{
        ArrayList<User> users=new ArrayList<>();
        int event = parser.getEventType();
        User currentUser=null;
        while (event!= XmlPullParser.END_DOCUMENT){
            String tag = "";
            switch (event){
                case XmlPullParser.START_TAG:
                    tag = parser.getName();
                    Toast.makeText(getApplicationContext(),"name"+tag,Toast.LENGTH_LONG).show();
                    if(tag.equals("user")) {
                        currentUser= new User();
                        users.add(currentUser);
                    }
                    else if(currentUser!=null)
                    {   if(tag.equals("name"))
                        {
                            currentUser.name=parser.nextText();
                        }
                        else if(tag.equals("designation"))
                        {
                            currentUser.designation=parser.nextText();
                        }
                        else if(tag.equals("location"))
                        {
                            currentUser.location=parser.nextText();
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    Toast.makeText(getApplicationContext(),"End "+tag,Toast.LENGTH_LONG).show();

                    break;
              }
            event = parser.next();
        }
        printUser(users);
    }

    private void printUser(ArrayList<User> users) {
        StringBuilder builder=new StringBuilder();
        for(User user:users)
        {
            builder.append("\n").append(user.name).append("\n").
                    append(user.designation).append("\n").
                    append(user.location).append("\n").append("---------------");
        }
        tx.setText(builder.toString());
    }
}
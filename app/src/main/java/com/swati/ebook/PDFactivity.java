package com.swati.ebook;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PDFactivity extends AppCompatActivity implements OnLoadCompleteListener , OnPageErrorListener, PopupMenu.OnMenuItemClickListener, TextToSpeech.OnInitListener {
    private TextToSpeech tts;
    private File pdfFile;
    public static String pdfPath;
    private PDFView pdfView;
    int currentPage=0;

    private static final Pattern HINDI_PATTERN = Pattern.compile("\\p{InDEVANAGARI}+");
    private static final Pattern ENGLISH_PATTERN = Pattern.compile("\\p{InBasic_Latin}+");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_activity);

        pdfView = findViewById(R.id.mypdfView);
        TextView pageCountTextView = findViewById(R.id.pageCountTV);
        Button Speech = findViewById(R.id.readButton);

        //getting id of download button
        ImageButton downloadButton = findViewById(R.id.downloadButton);

        //unpack our data from intent
        Intent i = this.getIntent();
        pdfPath = i.getExtras().getString("PATH");

        //loading the file
        FileLoader.with(this)
                .load(pdfPath, false)
                .fromDirectory("My Pdfs", FileLoader.DIR_INTERNAL)
                .asFile(new FileRequestListener<File>() {
                    @Override
                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                         pdfFile = response.getBody();
                        try{
                            pdfView.fromFile(pdfFile)
                                    //set page
                                    .defaultPage(0)
                                    .enableAnnotationRendering(true)
                                    .onLoad(PDFactivity.this)
                                    //swipe horizontal
                                    .swipeHorizontal(true)
                                    //swipe enabled
                                    .enableSwipe(true)
                                    //to zoom pdf
                                    .enableDoubletap(true)
                                    .enableAntialiasing(true)
                                    .spacing(12)  //dp
                                    .onPageError(PDFactivity.this)
                                    //show the page count of each page
                                   .onPageChange(new OnPageChangeListener() {
                                    @Override
                                    public void onPageChanged(int page, int pageCount) {
                                        //update the page count text view
                                        pageCountTextView.setText((page+1)+ "/" + pageCount);
                                        //speech
                                        currentPage = page+1;
                                    }
                                   })

                                    .load();
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(FileLoadRequest request, Throwable t) {
                        Toast.makeText(PDFactivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


        //when button is clicked menu options will be opened
        Speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(PDFactivity.this,v);
                popupMenu.setOnMenuItemClickListener(PDFactivity.this);
                popupMenu.inflate(R.menu.language);
                popupMenu.show();
            }
        });

        tts = new TextToSpeech(this, this);
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        //downloading
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    downloadPdf(pdfPath);
            }
        });

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                try {
                    // extract the text
                    PdfReader pdfReader = new PdfReader(pdfFile.getPath());
                    int pageNo = currentPage;

                    String stringParser = PdfTextExtractor.getTextFromPage(pdfReader, pageNo).trim();

                    byte [] bytes = stringParser.getBytes(StandardCharsets.UTF_8);
                    String utf8String = new String(bytes, StandardCharsets.UTF_8);

                    //speak
                    Matcher hindiMatcher = HINDI_PATTERN.matcher(utf8String);
                    Matcher engMatcher = ENGLISH_PATTERN.matcher(utf8String);

                    if(hindiMatcher.find() && !engMatcher.find()){
                        tts.setLanguage(new Locale("hi","IN"));
                    }
                    tts.speak(utf8String, TextToSpeech.QUEUE_FLUSH, null, null);
                    pdfReader.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                return true;

            case R.id.item2:
                //stop the engine
                tts.stop();
                return true;

            default:
                return false;
        }
    }

    @Override
    public void onInit(int status){
        if (status == TextToSpeech.SUCCESS) {
            //speak
            tts.setSpeechRate(0.8f);
            tts.setPitch(1.0f);
            tts.setVoice(new Voice("en-us-x-sfg-#female_1-local",new Locale("en"), Voice.QUALITY_HIGH, Voice.LATENCY_HIGH,false, null));
            int result = tts.setLanguage(new Locale("en"));
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Text to speech initialisation failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //shut down text to speech engine
        if(tts!=null){
            tts.stop();
            tts.shutdown();
        }
    }

    @Override
    public void loadComplete(int numOfPages){
        Toast.makeText(PDFactivity.this, String.valueOf(numOfPages), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageError(int page , Throwable t){
        Toast.makeText(PDFactivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
    }

    //downloading pdf
    private void downloadPdf(String path){

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(path));
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
            request.setTitle("Book Download");
            request.setAllowedOverMetered(true);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            String fileName = path.substring(path.lastIndexOf("/") + 1);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);
    }

}

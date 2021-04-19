package com.pdf_viewer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liberary.barteksc.pdfviewer.PDFView;
import com.liberary.barteksc.pdfviewer.listener.OnPageChangeListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity  implements OnPageChangeListener {

    PDFView receiptView;
    ProgressBar progressBar;
    TextView tvCount;
    ImageView ivLeft,ivRight;
    ConstraintLayout clPageCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        receiptView = findViewById(R.id.pdfView);
        tvCount = findViewById(R.id.tvCount);
        ivLeft = findViewById(R.id.ivLeft);
        ivRight = findViewById(R.id.ivRight);
        clPageCount = findViewById(R.id.clPageCount);
        getPdf();

        ivLeft.setOnClickListener(v -> {
            receiptView.jumpTo(receiptView.getCurrentPage()-1,true);
        });

        ivRight.setOnClickListener(v -> {
            receiptView.jumpTo(receiptView.getCurrentPage()+1,true);
        });
    }

    private void getPdf() {
        progressBar.setVisibility(View.VISIBLE);
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    InputStream input = new URL("https://www.learningcontainer.com/wp-content/uploads/2019/09/sample-pdf-with-images.pdf").openStream();
                    receiptView.fromStream(input)
                            .enableSwipe(true)
                            .swipeHorizontal(true)
                            .pageSnap(true)
                            .autoSpacing(true)
                            .onPageChange(MainActivity.this)
                            .enableAnnotationRendering(true)
                            .pageFling(true).load();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                clPageCount.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        }.execute();


    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        tvCount.setText(String.format("%s / %s", page + 1, pageCount));
    }
}
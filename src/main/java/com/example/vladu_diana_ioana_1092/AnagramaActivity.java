package com.example.vladu_diana_ioana_1092;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

//ATENTIE !!!!
//Algoritmul din spate nu este cel mai eficient, pentru ca are o complexitate de timp mare.
//Asadar pentru cuvintele cu mai mult de 5 litere complexitatea creste simtibil (deoarece se bazeaza pe n!)
//si poate dura ceva pana se genereaza raspunsul (pana pe la 30 de secunde din ce am mai testat/cronometrat pentru un cuvat de 7 litere - de ex: players)

public class AnagramaActivity extends AppCompatActivity {
    Button btnCreateAnagram;
    EditText word;
    TextView result;
    private final List<String> shuffledWords = new ArrayList<>();
    private final List<String> goodAnagrams = new ArrayList<>();
    private String[] separatedWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Ascundem "status bar"
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_anagrama);

        //Ascundem "action bar"
        getSupportActionBar().hide();
        AssetManager assetManager = getAssets();

        //Preluam datele din assets (words.txt) -> le punem intr-o lista de String-uri
        try {
            InputStream inputStream = assetManager.open("words.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                result.append(line).append(" ");
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();

            separatedWords = result.toString().split(" ");

            //Toast.makeText(this, "Words were loaded!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            //Toast.makeText(this, "Could not load words!", Toast.LENGTH_LONG).show();
        }

        btnCreateAnagram = findViewById(R.id.vladu_diana_ioana_btnCreateAnagram);
        result = findViewById(R.id.vladu_diana_ioana_resultTV);

        btnCreateAnagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDiag();
            }
        });
    }

    private void showCustomDiag() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AnagramaActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(AnagramaActivity.this).inflate(
                R.layout.diag_layout,
                (ConstraintLayout) findViewById(R.id.layoutDiagContainer)
        );
        word = view.findViewById(R.id.vladu_diana_ioana_edWord);
        builder.setView(view);
        ((TextView) view.findViewById(R.id.vladu_diana_ioana_textTitle)).setText(getResources().getString(R.string.textTitle));
        ((Button) view.findViewById(R.id.vladu_diana_ioana_btnCreate)).setText(getResources().getString(R.string.btnCreate));
        ((Button) view.findViewById(R.id.vladu_diana_ioana_btnCancel)).setText(getResources().getString(R.string.btnCancel));

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.vladu_diana_ioana_btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });

        view.findViewById(R.id.vladu_diana_ioana_btnCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                shuffleWord(word.getText().toString());
                findGoodAnagrams(separatedWords);
                if(goodAnagrams.size() != 0){
                    String randomGoodAnagram = pickGoodAnagram(goodAnagrams);
                    result.setText(String.format("%s -> %s", word.getText().toString(), randomGoodAnagram));
                }else{
                    Toast.makeText(view.getContext(), R.string.ToastInvalid, Toast.LENGTH_LONG).show();
                    result.setText("");
                }
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    //Construim toate permutarile(anagramele) posibile pentru cuvantul introdus
    public void shuffleWord(String word) {
        shuffledWords.clear();
        int size = word.length();
        anagram(word, 0, size - 1);
    }

    private void anagram(String str, int start, int end) {
        if (start == end) {
            shuffledWords.add(str);
        } else {
            for (int i = start; i <= end; i++) {
                str = swap(str, start, i);
                anagram(str, start + 1, end);
                str = swap(str, start, i);
            }
        }
    }

    public String swap(String a, int i, int j) {
        char temp;
        char[] charArray = a.toCharArray();
        temp = charArray[i];
        charArray[i] = charArray[j];
        charArray[j] = temp;
        return String.valueOf(charArray);
    }

    //Cautam si pastram din lista anagramelor generate doar pe cele care sunt valide <=> reprezinta alte cuvinte din engleza
    public void findGoodAnagrams(String[] result) {
        goodAnagrams.clear();
        for (String anagram : shuffledWords) {
            if (!anagram.equalsIgnoreCase(shuffledWords.get(0))) {
                for (String word : result) {
                    if (anagram.equalsIgnoreCase(word)) {
                        if (goodAnagrams.size() > 0) {
                            boolean ok = true;
                            for (String goodAng : goodAnagrams) {
                                if (anagram.equalsIgnoreCase(goodAng)) {
                                    ok = false;
                                    break;
                                }
                            }
                            if (ok) {
                                goodAnagrams.add(anagram);
                            }
                        } else {
                            goodAnagrams.add(anagram);
                        }
                    }
                }
            }
        }
    }

    //Lista de anagrame valide poate contine mai multe elemente => de fiecare data cand acelasi cuvant va fi introdus
    //pentru a i se genera o anagrama => se va alege random un element din lista sa generata, de anagrame valide.
    //Se poate testa prin introducerea in mod repetat al unui cuvant care are mai multe anagrame valide precum: angel sau state
    public String pickGoodAnagram(List<String> goodAnagrams) {
        return goodAnagrams.get(new Random().nextInt(goodAnagrams.size()));
    }
}
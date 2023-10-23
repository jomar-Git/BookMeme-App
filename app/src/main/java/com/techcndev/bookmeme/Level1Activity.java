package com.techcndev.bookmeme;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Level1Activity extends AppCompatActivity {

    private CountDownTimer countDownTimer;
    private boolean isPaused = false;
    private TextView textTimer;
    private Button AttackButton;

    public ArrayList<Button> PickerButton, DisplayButton;

    // inGameLetters are the letters that is currently available and displayed
    // selectedLetters are the letters that is selected by users
    public ArrayList<ArrayList<Object>> inGameLetters, selectedLetters;

    private int time = 30;
    private final String LOG_TAG = "Level1Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);
        PickerButton = new ArrayList<>();
        DisplayButton = new ArrayList<>();
        inGameLetters = new ArrayList<>();
        selectedLetters = new ArrayList<>();
        init_components();
        startPlay();
    }
    public void attack_now(View view) {
        if (isPaused) {
            startPlay();
        } else {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
        }
        isPaused = !isPaused;
    }

    public void checkWord() {
        if (isPaused) {

        }

    }

    private void populate_displayBtn() {
        int arrsize = DisplayButton.size();
//        Button button;
        String char_value;
        for (int i = 1; arrsize >= i; i++) {
            int buttonId = getResources().getIdentifier("display_letter" + i, "id", getPackageName());
            Button button = findViewById(buttonId);
            try {
                int letter_index = i - 1;
                ArrayList<Object> letter_data = selectedLetters.get(letter_index);
                char_value = (String) letter_data.get(1);
            } catch (Exception e) {
                char_value = "";
            }
            Log.d(LOG_TAG, "populate_displayBtn>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            Log.d(LOG_TAG, "char_value: " + char_value);
            Log.d(LOG_TAG, "display_letter: " + "display_letter" + i);
            Log.d(LOG_TAG, "buttonId: " + buttonId);
            button.setText(char_value);
            if (char_value.equals("")) {
                button.setVisibility(View.GONE);
            } else {
                button.setVisibility(View.VISIBLE);
            }
        }
    }

    private void populate_pickerBtn() {
        int arrsize = PickerButton.size();
        if (arrsize > 0) {
            for (int i = 1; arrsize >= i; i++) {
                int buttonId = getResources().getIdentifier("picker_letter" + i, "id", getPackageName());
                Button button = findViewById(buttonId);
                String char_value = null;
                for (ArrayList<Object> array_item: inGameLetters) {
                    if ((int) array_item.get(0) == buttonId) {
                        try {
                            // the id in arraylist is will be searched in all pickerButton items
                            Log.d(LOG_TAG,"array_item.get(1): " + array_item.get(1));
                            char_value = (String) array_item.get(1);
                        } catch (Exception e) {
                            Log.d(LOG_TAG, "populate_pickerBtn Exception: " + e + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                            char_value = "";
                        }
                    }
                    
                }
                button.setText(char_value);
            }
        }
    }

    public void remove_pick_letter(View view) {
        // know who invoke the method
        // get the char value using buttonId invoker_value
        ArrayList<Object> sendData = null;
        int counter = 0;
        for (Button button: DisplayButton) {
            // get the id of one button in arraylist
            int button_id = button.getId();
            Log.d(LOG_TAG, "button_id: " + button_id + " view.getId(): " + view.getId());
            if (button_id == view.getId()) {
//                RemovePickInvokerValue = (CharSequence) invoker_data.get(1);
                ArrayList<Object> invoker_data = selectedLetters.get(counter);
                sendData = invoker_data;
                break;
            }
            counter++;
        }
        Log.d(LOG_TAG, "remove_pick_letter/CURRENT INGAMELETTERS SIZE "+inGameLetters.size());
        Log.d(LOG_TAG, "remove_pick_letter/CURRENT INGAMELETTERS "+ String.valueOf(inGameLetters));
        // check in PickerButton which button is empty
        for (Button ReceiverButton: PickerButton) {
            CharSequence ReceiverText = ReceiverButton.getText();
            Log.d(LOG_TAG, "ReceiverText " + ReceiverText +">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" );
            // get the empty button id

            Log.d(LOG_TAG, String.valueOf(TextUtils.isEmpty(ReceiverText)));

            if (TextUtils.isEmpty(ReceiverText)) {
                // add empty button id and invoker value arraylist to selectedLetters arraylist
                inGameLetters.add(sendData);
                Log.d(LOG_TAG, "counter: "+counter);
                selectedLetters.remove(counter);
//                Log.d(LOG_TAG, "AFTER selectedLetters SIZE "+selectedLetters.size());
//                Log.d(LOG_TAG, "UPDATED selectedLetters "+String.valueOf(selectedLetters));
//                Log.d(LOG_TAG, counter2 + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" );
                // set the empty button as visible
                // set the method invoker as gone
//                    CharSender.setVisibility(View.GONE);
                // repopulate display buttons text value
                populate_displayBtn();
                populate_pickerBtn();
                break;
            }
        }
//        Log.d(LOG_TAG, counter2 + " OUTSIDE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" );
    }

    public void pick_letter(View view) {
        // know who invoke the method
        Button CharSender = (Button) view;
        ArrayList<Object> sendData = null;
        CharSequence invoker_value = null;
        // get the char value using buttonIdinvoker_value
        int counter = 0;
        for (ArrayList<Object> invoker_data: inGameLetters) {
            int invoker_id = (int) invoker_data.get(0);
            if (invoker_id == view.getId()) {
                invoker_value = (CharSequence) invoker_data.get(1);
                sendData = invoker_data;
                break;
            }
            counter++;
        }
            // check in DisplayButton which button is empty
        for (Button ReceiverButton: DisplayButton) {
            CharSequence ReceiverText = ReceiverButton.getText();
            Log.d(LOG_TAG, ReceiverText + " " + counter +">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" );
            // get the empty button id
            if (!TextUtils.isEmpty(invoker_value)) {
                if (TextUtils.isEmpty(ReceiverText)) {
                    // add empty button id and invoker value arraylist to selectedLetters arraylist
//                    int button_id = ReceiverButton.getId();
//                    ArrayList<Object> add_selected = new ArrayList<>();
//                    add_selected.add(button_id);
//                    add_selected.add(invoker_value);
                    selectedLetters.add(sendData);
//                    Log.d(LOG_TAG, "BEFORE INGAMELETTERS SIZE " + inGameLetters.size());
                    inGameLetters.remove(counter);
//                    Log.d(LOG_TAG, "AFTER INGAMELETTERS SIZE " + inGameLetters.size());
//                    Log.d(LOG_TAG, "UPDATED INGAMELETTERS " + String.valueOf(inGameLetters));
//                    CharSender.setText("");
                    // repopulate display buttons text value
                    populate_displayBtn();
                    populate_pickerBtn();
                    // set the empty button as visible
//                    ReceiverButton.setVisibility(View.VISIBLE);
                    // set the method invoker as invisible
//                    CharSender.setVisibility(View.INVISIBLE);
                    Log.d(LOG_TAG, "UPDATED INGAMELETTERS " + String.valueOf(inGameLetters));
                    Log.d(LOG_TAG, "AFTER INGAMELETTERS SIZE " + inGameLetters.size());
                    break;
                }
            } else {
                Log.d(LOG_TAG, "Letter selection denioed: button does not have text attribute value");
            }
        }
    }

    private void startPlay() {
        countDownTimer = new CountDownTimer(time * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                time = (int) (millisUntilFinished / 1000);
                textTimer.setText("0:" + checkDigit(time) + " sec");
            }

            public void onFinish() {
                textTimer.setText("try again");
            }
        }.start();

        char[] RandomLetters = generateRandomLetters();
        for (int i = 1; i <= 16 && i <= RandomLetters.length; i++) {
            int buttonId = getResources().getIdentifier("picker_letter" + i, "id", getPackageName());
            Button button = findViewById(buttonId);
            String RandText = String.valueOf(RandomLetters[i - 1]);
            button.setText(RandText);

            ArrayList<Object> charItemList = new ArrayList<>();
            charItemList.add(buttonId);
            charItemList.add(RandText);
            inGameLetters.add(charItemList);
        }
    }

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    @Override
    public void onBackPressed() {
//        moveTaskToBack(true);
    }

    public static char[] generateRandomLetters() {
        Random random = new Random();
        char[] randomLetters = new char[16];

        for (int i = 0; i < 16; i++) {
            randomLetters[i] = (char) (random.nextInt(26) + 'A');
        }
        return randomLetters;
    }

    private void init_components() {
        Button AttackButton, PickerLetter1, PickerLetter2, PickerLetter3, PickerLetter4, PickerLetter5, PickerLetter6,
                PickerLetter7, PickerLetter8, PickerLetter9, PickerLetter10, PickerLetter11, PickerLetter12, PickerLetter13, PickerLetter14,
                PickerLetter15, PickerLetter16, DisplayLetter1, DisplayLetter2, DisplayLetter3, DisplayLetter4, DisplayLetter5, DisplayLetter6,
                DisplayLetter7, DisplayLetter8, DisplayLetter9, DisplayLetter10, DisplayLetter11, DisplayLetter12;

        textTimer = findViewById(R.id.attacktimesectext);
        AttackButton = findViewById(R.id.attack_button);

        PickerLetter1 = findViewById(R.id.picker_letter1);
        PickerLetter2 = findViewById(R.id.picker_letter2);
        PickerLetter3 = findViewById(R.id.picker_letter3);
        PickerLetter4 = findViewById(R.id.picker_letter4);
        PickerLetter5 = findViewById(R.id.picker_letter5);
        PickerLetter6 = findViewById(R.id.picker_letter6);
        PickerLetter7 = findViewById(R.id.picker_letter7);
        PickerLetter8 = findViewById(R.id.picker_letter8);
        PickerLetter9 = findViewById(R.id.picker_letter9);
        PickerLetter10 = findViewById(R.id.picker_letter10);
        PickerLetter11 = findViewById(R.id.picker_letter11);
        PickerLetter12 = findViewById(R.id.picker_letter12);
        PickerLetter13 = findViewById(R.id.picker_letter13);
        PickerLetter14 = findViewById(R.id.picker_letter14);
        PickerLetter15 = findViewById(R.id.picker_letter15);
        PickerLetter16 = findViewById(R.id.picker_letter16);

        DisplayLetter1 = findViewById(R.id.display_letter1);
        DisplayLetter2 = findViewById(R.id.display_letter2);
        DisplayLetter3 = findViewById(R.id.display_letter3);
        DisplayLetter4 = findViewById(R.id.display_letter4);
        DisplayLetter5 = findViewById(R.id.display_letter5);
        DisplayLetter6 = findViewById(R.id.display_letter6);
        DisplayLetter7 = findViewById(R.id.display_letter7);
        DisplayLetter8 = findViewById(R.id.display_letter8);
        DisplayLetter9 = findViewById(R.id.display_letter9);
        DisplayLetter10 = findViewById(R.id.display_letter10);
        DisplayLetter11 = findViewById(R.id.display_letter11);
        DisplayLetter12 = findViewById(R.id.display_letter12);

        PickerButton = new ArrayList<>(Arrays.asList(PickerLetter1, PickerLetter2, PickerLetter3, PickerLetter4, PickerLetter5,
                PickerLetter6, PickerLetter7, PickerLetter8, PickerLetter9, PickerLetter10, PickerLetter11, PickerLetter12, PickerLetter13,
                PickerLetter14, PickerLetter15, PickerLetter16));

        DisplayButton = new ArrayList<>(Arrays.asList(DisplayLetter1, DisplayLetter2, DisplayLetter3, DisplayLetter4, DisplayLetter5, DisplayLetter6,
                DisplayLetter7, DisplayLetter8, DisplayLetter9, DisplayLetter10, DisplayLetter11, DisplayLetter12));
    }
}


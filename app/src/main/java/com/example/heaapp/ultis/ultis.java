package com.example.heaapp.ultis;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ultis{
    public static Intent setIntent(Context context, Class destination) {

        Intent intent = new Intent(context, destination);
        context.startActivity(intent);
        return intent ;
    }

    public static void showMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}

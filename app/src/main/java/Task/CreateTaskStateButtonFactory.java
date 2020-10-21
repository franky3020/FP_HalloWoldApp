package Task;

import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.LinearLayout;

import androidx.appcompat.view.ContextThemeWrapper;

import com.google.android.material.button.MaterialButton;

public class CreateTaskStateButtonFactory {


    public static MaterialButton getMaterialButton(ContextThemeWrapper contextThemeWrapper) {
        MaterialButton materialButton = new MaterialButton(contextThemeWrapper);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(12, 12, 12, 12);

        materialButton.setLayoutParams(params);

        materialButton.setCornerRadius(5);
        materialButton.setBackgroundColor(Color.parseColor("#CE0000"));
        materialButton.setTextSize(24);
        materialButton.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC);
        materialButton.setText("Delete");

        return materialButton;

    }




}

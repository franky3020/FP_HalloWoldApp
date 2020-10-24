package Task;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.view.ContextThemeWrapper;

import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CreateTaskStateButtonFactory {

    ContextThemeWrapper contextThemeWrapper;
    int taskID;

    public CreateTaskStateButtonFactory(int taskID, ContextThemeWrapper contextThemeWrapper) {
        this.contextThemeWrapper = contextThemeWrapper;
        this.taskID = taskID;
    }

    public MaterialButton createTasksButton(String select) {
        if (select == "Delete") {
            return getDeleteButton();
        } else if (select == "OK") {
            return getOKButton();
        }
        return new MaterialButton(contextThemeWrapper);
    }

    public MaterialButton getDeleteButton() {
        MaterialButton materialButton = getBaseButton();

        materialButton.setBackgroundColor(Color.parseColor("#CE0000"));
        materialButton.setText("Delete");
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskAPIService taskApiService = new TaskAPIService();
                taskApiService.deleteTask(taskID, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        System.out.println("franky-test ok");
                    }
                });
            }
        });

        return materialButton;
    }

    public MaterialButton getOKButton() {
        MaterialButton materialButton = getBaseButton();
        materialButton.setBackgroundColor(Color.parseColor("#00EC00"));
        materialButton.setText("OK");
        return materialButton;
    }


    private MaterialButton getBaseButton() {
        MaterialButton materialButton = new MaterialButton(contextThemeWrapper);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(12, 12, 12, 12);
        materialButton.setLayoutParams(params);
        materialButton.setCornerRadius(5);
        materialButton.setTextSize(24);
        materialButton.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC);
        return materialButton;
    }
}

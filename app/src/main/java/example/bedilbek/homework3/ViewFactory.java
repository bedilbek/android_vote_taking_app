package example.bedilbek.homework3;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class ViewFactory {

    public static TextView createTextView(Context context, String text, int size, int width, int height, int weight, int gravity) {
        TextView textView = createView(new TextView(context), width, height, weight, gravity);
        textView.setText(text);
        textView.setTextColor(context.getResources().getColor(R.color.black));
        textView.setTextSize(convertDpsToPixels(context, size));
        return textView;
    }

    public static TextView createTextView(Context context, String text, int size) {
        return createTextView(context, text, size, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0, -1);
    }


    public static EditText createEditText(Context context, String hint, int size, int width, int height, int weight, int gravity) {
        EditText editText = createView(new EditText(context), width, height, weight, gravity);
        editText.setTextSize(convertDpsToPixels(context, size));
        editText.setHint(hint);
        return editText;
    }

    public static EditText createEditText(Context context, String hint, int size) {
        return createEditText(context, hint, size, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1, -1);
    }

    public static RadioGroup createRadioGroup(Context context) {
        RadioGroup radioGroup = new RadioGroup(context);
        radioGroup.setLayoutParams(new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        radioGroup.setOrientation(ConstraintLayout.LayoutParams.HORIZONTAL);
        return radioGroup;
    }

    public static RadioButton createRadioButton(Context context, String text) {
        RadioButton radioButton = createView(new RadioButton(context), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0, -1);
        radioButton.setText(text);
        return radioButton;
    }

    public static <K extends View> K setMargins(K view, int marginTop, int marginBottom, int marginLeft, int marginRight) {
        ViewGroup.MarginLayoutParams viewParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        viewParams.setMargins(
                convertDpsToPixels(view.getContext(), marginLeft),
                convertDpsToPixels(view.getContext(), marginTop),
                convertDpsToPixels(view.getContext(), marginRight),
                convertDpsToPixels(view.getContext(), marginBottom));
        view.setLayoutParams(viewParams);
        return view;
    }

    public static int convertDpsToPixels(Context context, int dps) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (scale * dps + 0.5);
    }

    public static LinearLayout createLinearLayout(Context context, int weight, int height, int orientation) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(weight, height));
        linearLayout.setOrientation(orientation);
        return linearLayout;
    }

    public static LinearLayout createLinearLayout(Context context, int orientation) {
        return createLinearLayout(context, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, orientation);
    }

    public static Spinner createSpinner(Context context) {
        return createView(new Spinner(context), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0, -1);
    }

    public static Button createButton(Context context, String text, int size) {
        return createButton(context, text, size, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0, Gravity.CENTER);
    }

    public static Button createButton(Context context, String text, int size, int width, int height, int weight, int gravity) {
        Button button = createView(new Button(context), width, height, weight, gravity);
        button.setText(text);
        button.setTextSize(convertDpsToPixels(context, size));
        button.setGravity(Gravity.CENTER);
        button.setTextColor(context.getResources().getColor(R.color.colorVoteText));
        return button;
    }

    private static <T extends View> T createView(T view, int width, int height, int weight, int gravity) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height, weight);
        layoutParams.gravity = gravity;
        view.setLayoutParams(layoutParams);
        view.setPadding(
                convertDpsToPixels(view.getContext(), 5),
                convertDpsToPixels(view.getContext(), 5),
                convertDpsToPixels(view.getContext(), 5),
                convertDpsToPixels(view.getContext(), 5));
        return view;
    }

}

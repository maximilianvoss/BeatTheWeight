package rocks.voss.beattheweight.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import rocks.voss.beattheweight.R;

public class WeightEntryDialog extends DialogFragment {

    private WeightEntryCallback mListener;

    public interface WeightEntryCallback {
        void saveNewWeight(float weight);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (WeightEntryCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement WeightEntryCallback");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Weight");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View customView = inflater.inflate(R.layout.dialog_addweight, null);
        EditText newWeight = customView.findViewById(R.id.newWeight);
        builder.setView(customView);

        builder.setPositiveButton("OK", (dialog, which) -> {
            Log.i(this.getClass().toString(), newWeight.getText().toString());
            mListener.saveNewWeight(Float.valueOf(newWeight.getText().toString()));
            dialog.dismiss();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.cancel();
        });

        return builder.create();
    }


}

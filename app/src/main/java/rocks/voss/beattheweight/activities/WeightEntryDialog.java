package rocks.voss.beattheweight.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.math.BigDecimal;

import rocks.voss.androidutils.ui.DoubleNumberPicker;
import rocks.voss.beattheweight.R;
import rocks.voss.beattheweight.database.WeightsCache;

public class WeightEntryDialog extends DialogFragment {

    private WeightEntryCallback mListener;

    public interface WeightEntryCallback {
        void saveNewWeight(BigDecimal weight);
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

        DoubleNumberPicker doubleNumberPicker = customView.findViewById(R.id.doublepicker);

        if (WeightsCache.getAll().size() > 0) {
            doubleNumberPicker.setValue(WeightsCache.getAll().get(0).weight);
        }
        builder.setView(customView);

        builder.setPositiveButton("OK", (dialog, which) -> {
            Log.i(this.getClass().toString(), String.valueOf(doubleNumberPicker.getValue()));
            mListener.saveNewWeight(doubleNumberPicker.getValue());
            dialog.dismiss();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.cancel();
        });

        return builder.create();
    }
}

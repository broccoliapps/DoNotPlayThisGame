package broccoli.donotplaythisgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by dannydelott on 10/14/14.
 */
public class FragmentConfirmDelete extends DialogFragment {


    // Use this instance of the interface to deliver action events
    private NoticeDialogListener mListener;
    private int mGameSlot = -1;

    /* The activity that creates an instance of this dialog fragment must
 * implement this interface in order to receive event callbacks.
 * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, int gameSlot);

        public void onDialogNegativeClick(DialogFragment dialog, int gameSlot);
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.fragment_confirm_delete_prompt)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick(FragmentConfirmDelete.this, mGameSlot);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(FragmentConfirmDelete.this, mGameSlot);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public void setGameSlot(int slot) {
        mGameSlot = slot;
    }
}
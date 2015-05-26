package com.mm.caju;

import android.app.Activity;
import android.app.Fragment;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mm.caju.caju_seqMdl.DefMovement;
import com.mm.caju.caju_seqMdl.MiscMovement;
import com.mm.caju.caju_seqMdl.Movement;
import com.mm.caju.caju_seqMdl.MovementLib;
import com.mm.caju.caju_seqMdl.OffMovement;
import com.mm.caju.caju_seqMdl.Sequence;
import com.mm.caju.caju_seqMdl.TimeSlot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

import static com.mm.caju.CajuMainActivity.getCajuSequenceLib;
import static com.mm.caju.CajuMainActivity.getCurrentSequence;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.mm.caju.SequenceEditorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SequenceEditorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SequenceEditorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Sequence currentSequence = null;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SequenceEditorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SequenceEditorFragment newInstance(String param1, String param2) {
        SequenceEditorFragment fragment = new SequenceEditorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SequenceEditorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        currentSequence = CajuMainActivity.getCurrentSequence();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MovementLib cajuMovementLib = ((CajuMainActivity)getActivity()).getCajuMovementLib();

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sequence_editor, container, false);


        /** prepare trash bin as drag&drop-Target  **/
        rootView.findViewById(R.id.imageView_seqed_trashbin).setOnDragListener(new DelMovDragListener());

        /** prepare Add-Moves-Element as drag&drop-Target */
        rootView.findViewById(R.id.imageView_addBotMov).setOnDragListener(new AddMovDragListener());
        rootView.findViewById(R.id.imageView_addTopMov).setOnDragListener(new AddMovDragListener());


        /** def iterator **/
        Iterator it = null;

        /**
         * fill OffMovPalette with elements
         * */
        LinearLayout offMovLibLayout = (LinearLayout) rootView.findViewById(R.id.layout_OffMovLib);

        // use iterator
        it = cajuMovementLib.getOffMovList().iterator();
        while (it.hasNext()) {

            OffMovement offMov = (OffMovement) it.next();
            CajuPaletteIconView imgView = new CajuPaletteIconView(getActivity());

            imgView.setMovement( offMov );

            if ( offMov.getMovIconID() != 0) imgView.setImageDrawable(getResources().getDrawable(offMov.getMovIconID()));
            else imgView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_caju_new));

            imgView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));

            offMovLibLayout.addView(imgView);

            // Sets a long click listener for the ImageView using a listener object that
            // implements the OnLongClickListener interface
            imgView.setOnLongClickListener( new PaletteItemOnLongClickListener() );
        }

        /**
         * fill DefMovPalette with elements
         * */
        LinearLayout defMovLibLayout = (LinearLayout) rootView.findViewById(R.id.layout_DefMovLib);

        // REUSE iterator
        it = cajuMovementLib.getDefMovList().iterator();
        while (it.hasNext()) {

            DefMovement defMov = (DefMovement) it.next();
            CajuPaletteIconView imgView = new CajuPaletteIconView(getActivity());

            imgView.setMovement( defMov );

            if ( defMov.getMovIconID() != 0) imgView.setImageDrawable(getResources().getDrawable(defMov.getMovIconID()));
            else imgView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_caju_new));

            imgView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));

            defMovLibLayout.addView(imgView);

            // Sets a long click listener for the ImageView using a listener object that
            // implements the OnLongClickListener interface
            imgView.setOnLongClickListener(new PaletteItemOnLongClickListener());
        }

        /**
         * fill MiscMovPalette with elements
         * */
        LinearLayout miscMovLibLayout = (LinearLayout) rootView.findViewById(R.id.layout_MiscMovLib);

        // REUSE iterator
        it = cajuMovementLib.getMiscMovList().iterator();
        while (it.hasNext()) {

            MiscMovement miscMov = (MiscMovement) it.next();
            CajuPaletteIconView imgView = new CajuPaletteIconView(getActivity());

            imgView.setMovement( miscMov );

            if ( miscMov.getMovIconID() != 0) imgView.setImageDrawable(getResources().getDrawable(miscMov.getMovIconID()));
            else imgView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_caju_new));

            imgView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));

            miscMovLibLayout.addView(imgView);

            // Sets a long click listener for the ImageView using a listener object that
            // implements the OnLongClickListener interface
            imgView.setOnLongClickListener(new PaletteItemOnLongClickListener());
        }



        /**
         * fill SeqEd with elements of currently edited sequence
         * */
        LinearLayout seqLayout = (LinearLayout) rootView.findViewById(R.id.layout_seq);

        currentSequence = CajuMainActivity.getCurrentSequence();

        if ( currentSequence != null ) {
            // REUSE iterator
            it = currentSequence.getTimeslots().iterator();
            while (it.hasNext()) {

                TimeSlot tsl = (TimeSlot) it.next();
                SequenceElementFragment seqElFr;
                String tag = "seqel_time"+Integer.toString(tsl.getTime());

                if (savedInstanceState != null) {
                    seqElFr = (SequenceElementFragment) getFragmentManager().findFragmentByTag( tag);
                } else {
                    seqElFr = new SequenceElementFragment();
                    seqElFr.setTsl( tsl );
                    this.getFragmentManager().beginTransaction().add(R.id.layout_seq, seqElFr, tag).commit();
                }
            }
        }

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onSeqEdFragmentInteraction(uri);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        SimpleDateFormat df = new SimpleDateFormat("dd.MMM.yyyy-HH:mm");
        String formattedDate = df.format(Calendar.getInstance().getTime());

        if ( currentSequence != null) {
            if (!currentSequence.getTimeslots().isEmpty()) {
                currentSequence.setDate(formattedDate);
                if (!getCajuSequenceLib().getSequenceList().contains(currentSequence))
                    getCajuSequenceLib().addSequenceToSequenceList(currentSequence);

                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Sequence saved ...", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        ((CajuMainActivity)getActivity()).saveSeqLib();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onSeqEdFragmentInteraction(Uri uri);
    }


    /**
     * This
     *
     */
    public class CajuPaletteIconView extends ImageView {

        private Movement movement;

        public CajuPaletteIconView(Context context) {
            super(context);
        }

        public Movement getMovement() {
            return movement;
        }

        public void setMovement(Movement movement) {
            this.movement = movement;
        }
    }

    /**
     * This
     *
     */
    private class PaletteItemOnLongClickListener implements View.OnLongClickListener {
        // Defines the one method for the interface, which is called when the View is long-clicked
        public boolean onLongClick(View v) {

            ClipData dragData = ClipData.newPlainText("from_palMov", ""); // do not carry any text data
            View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);

            // Starts the drag
            v.startDrag(dragData,  // the data to be dragged
                    myShadow,  // the drag shadow builder
                    ((CajuPaletteIconView)v).getMovement(),      // local data
                    0          // flags (not currently used, set to 0)
            );

            // Vibrate for X milliseconds
            Vibrator vib = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            vib.vibrate(20);

            return true;
        }
    }

    /**
     * This
     *
     */
    private class AddMovDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            String descr;
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    // indicate valid drop zone
                    descr = event.getClipDescription().getLabel().toString();
                    if ( descr.equals("from_palMov") )
                        v.setBackgroundColor( Color.DKGRAY );
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    descr = event.getClipDescription().getLabel().toString();
                    if ( descr.equals("from_palMov") )
                        v.setBackgroundColor(Color.TRANSPARENT);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped,
                    // add timeslot to sequence
                    descr = event.getClipDescription().getLabel().toString();
                    if ( descr.equals("from_palMov") ) {
                        TimeSlot tsl = new TimeSlot();
                        currentSequence.addTimeSlotToTimeslots(tsl);
                        tsl.setTime(currentSequence.getTimeslots().indexOf(tsl));

                        // insert passed movement object into timeslot at correct position
                        if (v.equals(getActivity().findViewById(R.id.imageView_addBotMov))) {
                            Movement mov = (Movement) event.getLocalState();
                            try {
                                tsl.setBotPlayerMov((Movement) mov.clone());
                            } catch (CloneNotSupportedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (v.equals(getActivity().findViewById(R.id.imageView_addTopMov))) {
                            Movement mov = (Movement) event.getLocalState();
                            try {
                                tsl.setTopPlayerMov((Movement) mov.clone());
                            } catch (CloneNotSupportedException e) {
                                e.printStackTrace();
                            }
                        }

                        // create sequence element fragment and add do layout to display
                        SequenceElementFragment seqElFr = new SequenceElementFragment();
                        seqElFr.setTsl(tsl);
                        String tag = "seqel_time" + Integer.toString(tsl.getTime());
                        getActivity().getFragmentManager().beginTransaction().add(R.id.layout_seq, seqElFr, tag).commit();

                        // Vibrate for X milliseconds
                        Vibrator vib = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                        vib.vibrate(20);
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundColor(Color.TRANSPARENT);
                default:
                    break;
            }
            return true;
        }
    }

    /**
     * This
     *
     */
    private class DelMovDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            String descr;
            /** react only on moves deleted from the sequence */
                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        // indicate as valid drop zone
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        // indicate valid drop zone
                        descr = event.getClipDescription().getLabel().toString();
                        if ( descr.equals("from_seqMov") )
                            v.setBackgroundColor(Color.RED);
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        descr = event.getClipDescription().getLabel().toString();
                        if ( descr.equals("from_seqMov") )
                            v.setBackgroundColor(Color.TRANSPARENT);
                        break;
                    case DragEvent.ACTION_DROP:
                        // Dropped,
                        descr = event.getClipDescription().getLabel().toString();
                        if ( descr.equals("from_seqMov") ) {
                            TimeSlot tsl = (TimeSlot) event.getLocalState();
                            // delete timeslot and sequence element fragment from layout
                            String tag = "seqel_time" + Integer.toString(tsl.getTime());
                            getActivity().getFragmentManager().beginTransaction().remove(getActivity().getFragmentManager().findFragmentByTag(tag)).commit();
                            getCurrentSequence().removeTimeSlotToTimeslots(tsl);
                        }
                        // Vibrate for X milliseconds
                        Vibrator vib = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                        vib.vibrate(20);
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        v.setBackgroundColor(Color.TRANSPARENT);
                    default:
                        break;
                }
            return true;
        }
    }

    /**
     * Getters and Setters
     * */
    public void setCurrentSequence(Sequence currentSequence) {
        this.currentSequence = currentSequence;
    }


}

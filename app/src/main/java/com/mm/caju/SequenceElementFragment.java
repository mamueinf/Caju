package com.mm.caju;

import android.app.Activity;
import android.app.Fragment;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mm.caju.caju_seqMdl.MiscMovement;
import com.mm.caju.caju_seqMdl.Movement;
import com.mm.caju.caju_seqMdl.TimeSlot;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SequenceElementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SequenceElementFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private TimeSlot tsl = null;

    public SequenceElementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SequenceElementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SequenceElementFragment newInstance(String param1, String param2) {
        SequenceElementFragment fragment = new SequenceElementFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
//        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sequence_element, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /**
         * prepare all viewable elements of a timeslot representation
         */
        /** time */
        TextView timeView = (TextView) this.getView().findViewById(R.id.textView_time);
        timeView.setText(Integer.toString(tsl.getTime()));

        /** top player mov */
        CajuMovIconView topMovIconView = (CajuMovIconView) this.getView().findViewById(R.id.imageView_player_top);
        EditText topMovNoteView = (EditText) this.getView().findViewById(R.id.editText_note_top);

        // insert "continue mov" in case of unset mov
        if (tsl.getTopPlayerMov() == null ){
            MiscMovement cont = new MiscMovement();
            cont.setMovName("...");
            cont.setMovIconID(R.mipmap.ic_mov_cont);
            tsl.setTopPlayerMov( cont );
        }

        // icon
        topMovIconView.setTimeslot(tsl);
        topMovIconView.setImageDrawable(getResources().getDrawable(tsl.getTopPlayerMov().getMovIconID()));
        topMovIconView.setOnDragListener(new MovReplaceListener());
        topMovIconView.setOnLongClickListener(new SeqMoveOnLongClickListener());

        topMovNoteView.setText( tsl.getTopPlayerMov().getMovNote() );
        topMovNoteView.setHint( tsl.getTopPlayerMov().getMovName() );
        topMovNoteView.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                getTsl().getTopPlayerMov().setMovNote( s.toString() );
            }
        });

        ////
        // bot player mov
        CajuMovIconView botMovIconView = (CajuMovIconView) this.getView().findViewById(R.id.imageView_player_bot);
        EditText botMovNoteView = (EditText) this.getView().findViewById(R.id.editText_note_bot);

        // insert "continue mov" in case of unset mov
        if (tsl.getBotPlayerMov() == null ) {
            MiscMovement cont = new MiscMovement();
            cont.setMovName("...");
            cont.setMovIconID(R.mipmap.ic_mov_cont);
            tsl.setBotPlayerMov( cont );
        }

        botMovIconView.setTimeslot(tsl);
        botMovIconView.setImageDrawable(getResources().getDrawable(tsl.getBotPlayerMov().getMovIconID()));
        botMovIconView.setOnDragListener(new MovReplaceListener());
        botMovIconView.setOnLongClickListener(new SeqMoveOnLongClickListener());

        botMovNoteView.setText( tsl.getBotPlayerMov().getMovNote() );
        botMovNoteView.setHint( tsl.getBotPlayerMov().getMovName() );
        botMovNoteView.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                getTsl().getBotPlayerMov().setMovNote( s.toString() );
            }
        });
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
    * Getters and Setters
    */
    public TimeSlot getTsl() {
        return tsl;
    }

    public void setTsl(TimeSlot tsl) {
        this.tsl = tsl;
    }

    /**
     * This
     *
     */
    private class SeqMoveOnLongClickListener implements View.OnLongClickListener {
        // Defines the one method for the interface, which is called when the View is long-clicked
        public boolean onLongClick(View v) {

            ClipData dragData = ClipData.newPlainText("from_seqMov", ""); // do not carry any text data
            View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);

            // Starts the drag
            v.startDrag(dragData,  // the data to be dragged
                    myShadow,  // the drag shadow builder
                    ((CajuMovIconView)v).getTimeslot(),      // local data
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
    private class MovReplaceListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            final int action = event.getAction();
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
                    // insert passed movement object
                    descr = event.getClipDescription().getLabel().toString();
                    if ( descr.equals("from_palMov") ) {
                        CajuMovIconView icVw = (CajuMovIconView) v;
                        if (icVw.equals(getView().findViewById(R.id.imageView_player_bot))) {
                            Movement mov = (Movement) event.getLocalState();
                            try {
                                icVw.getTimeslot().setBotPlayerMov((Movement) mov.clone());
                                icVw.setBackgroundColor(Color.TRANSPARENT);
                                icVw.setImageDrawable(getResources().getDrawable(icVw.getTimeslot().getBotPlayerMov().getMovIconID()));
                            } catch (CloneNotSupportedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (icVw.equals(getView().findViewById(R.id.imageView_player_top))) {
                            Movement mov = (Movement) event.getLocalState();
                            try {
                                icVw.getTimeslot().setTopPlayerMov((Movement) mov.clone());
                                icVw.setBackgroundColor(Color.TRANSPARENT);
                                icVw.setImageDrawable(getResources().getDrawable(icVw.getTimeslot().getTopPlayerMov().getMovIconID()));
                            } catch (CloneNotSupportedException e) {
                                e.printStackTrace();
                            }
                        }
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
}

package com.mm.caju;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mm.caju.caju_seqMdl.DefMovement;
import com.mm.caju.caju_seqMdl.MiscMovement;
import com.mm.caju.caju_seqMdl.Movement;
import com.mm.caju.caju_seqMdl.MovementLib;
import com.mm.caju.caju_seqMdl.OffMovement;
import com.mm.caju.caju_seqMdl.Sequence;
import com.mm.caju.caju_seqMdl.TimeSlot;

import java.util.Iterator;


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
        currentSequence = createExampleSequence();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MovementLib cajuMovementLib = ((CajuMainActivity)getActivity()).getCajuMovementLib();

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sequence_editor, container, false);

        /** def iterator **/
        Iterator it = null;

        /**
         * fill OffMOvLib with elements
         * */
        LinearLayout offMovLibLayout = (LinearLayout) rootView.findViewById(R.id.layout_OffMovLib);

        /** use iterator **/
        it = cajuMovementLib.getOffMovList().iterator();
        while (it.hasNext()) {

            OffMovement offMov = (OffMovement) it.next();

            ImageView imgView = new ImageView(getActivity());

            if ( offMov.getMovIconID() != 0) imgView.setImageDrawable(getResources().getDrawable(offMov.getMovIconID()));
            else imgView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_caju_new));

            imgView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));

            offMovLibLayout.addView(imgView);
        }

        /**
         * fill DefMOvLib with elements
         * */
        LinearLayout defMovLibLayout = (LinearLayout) rootView.findViewById(R.id.layout_DefMovLib);

        /** REUSE iterator **/
        it = cajuMovementLib.getDefMovList().iterator();
        while (it.hasNext()) {

            DefMovement defMov = (DefMovement) it.next();

            ImageView imgView = new ImageView(getActivity());

            if ( defMov.getMovIconID() != 0) imgView.setImageDrawable(getResources().getDrawable(defMov.getMovIconID()));
            else imgView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_caju_new));

            imgView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));

            defMovLibLayout.addView(imgView);
        }

        /**
         * fill MiscMOvLib with elements
         * */
        LinearLayout miscMovLibLayout = (LinearLayout) rootView.findViewById(R.id.layout_MiscMovLib);

        /** REUSE iterator **/
        it = cajuMovementLib.getMiscMovList().iterator();
        while (it.hasNext()) {

            MiscMovement miscMov = (MiscMovement) it.next();

            ImageView imgView = new ImageView(getActivity());

            if ( miscMov.getMovIconID() != 0) imgView.setImageDrawable(getResources().getDrawable(miscMov.getMovIconID()));
            else imgView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_caju_new));

            imgView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));

            miscMovLibLayout.addView(imgView);
        }

        /**
         * fill SeqEd with elements
         * */

        LinearLayout seqLayout = (LinearLayout) rootView.findViewById(R.id.layout_seq);

        /** REUSE iterator **/
        it = currentSequence.getTimeslots().iterator();
        while (it.hasNext()) {

            TimeSlot tsl = (TimeSlot) it.next();

            SequenceElement seqElFr = new SequenceElement();

            seqElFr.setTsl( tsl );

            getFragmentManager().beginTransaction()
                    .add(R.id.layout_seq, seqElFr)
                    .commit();

//            ImageView topMovIconView = (ImageView) this.getView().findViewById(R.id.imageView_top);
//            topMovIconView.setImageDrawable( getResources().getDrawable( tsl.getTopPlayerMov().getMovIconID() ) );
//
//            ImageView botMovIconView = (ImageView) this.getView().findViewById(R.id.imageView_bot);
//            botMovIconView.setImageDrawable( getResources().getDrawable( tsl.getBotPlayerMov().getMovIconID() ) );


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

    private Sequence createExampleSequence(){

        MovementLib cajuMovementLib = ((CajuMainActivity)getActivity()).getCajuMovementLib();

        Sequence seq = new Sequence();

        TimeSlot t1 = new TimeSlot();
        t1.setTime(1);

        Iterator it = cajuMovementLib.getMiscMovList().iterator();
        while ( it.hasNext() ) {

            Movement mov = (Movement) it.next();
            if ( mov.getMovName().equals("Ginga")) {
                t1.setTopPlayerMov(mov);
                t1.setBotPlayerMov(mov);
                break;
            }
        }

        TimeSlot t2 = new TimeSlot();
        t2.setTime(2);

        it = cajuMovementLib.getOffMovList().iterator();
        while ( it.hasNext() ) {

            Movement mov = (Movement) it.next();
            if ( mov.getMovName().equals("Rabo de Arraia")) {
                t2.setTopPlayerMov(mov);
                break;
            }
        }
        it = cajuMovementLib.getDefMovList().iterator();
        while ( it.hasNext() ) {

            Movement mov = (Movement) it.next();
            if ( mov.getMovName().equals("Negativa de Angola")) {
                t2.setBotPlayerMov(mov);
                break;
            }
        }

        seq.addTimeSlotToTimeslots(t1);
        seq.addTimeSlotToTimeslots(t2);

        return seq;
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

}
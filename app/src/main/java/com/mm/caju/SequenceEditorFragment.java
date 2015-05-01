package com.mm.caju;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.mm.caju.caju_seqMdl.DefMovement;
import com.mm.caju.caju_seqMdl.MiscMovement;
import com.mm.caju.caju_seqMdl.MovementLib;
import com.mm.caju.caju_seqMdl.OffMovement;
import com.mm.caju.caju_seqMdl.Sequence;
import com.mm.caju.caju_seqMdl.TimeSlot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import static com.mm.caju.CajuMainActivity.getCajuSequenceLib;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.mm.caju.SequenceEditorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SequenceEditorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SequenceEditorFragment extends Fragment implements AbsListView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

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

        mAdapter = new SeqEdListViewAdapter(getActivity(),
                R.layout.fragment_sequence_element, currentSequence.getTimeslots() );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MovementLib cajuMovementLib = ((CajuMainActivity)getActivity()).getCajuMovementLib();

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sequence_editor, container, false);

        // Set the adapter
        mListView = (AbsListView) rootView.findViewById(R.id.listView_seq);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

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


/*        LinearLayout seqLayout = (LinearLayout) rootView.findViewById(R.id.layout_seq);

        if ( currentSequence != null ) {
            *//** REUSE iterator **//*
            it = currentSequence.getTimeslots().iterator();
            while (it.hasNext()) {

                TimeSlot tsl = (TimeSlot) it.next();

                SequenceElement seqElFr = new SequenceElement();

                seqElFr.setTsl( tsl );

                getFragmentManager().beginTransaction()
                        .add(R.id.layout_seq, seqElFr)
                        .commit();
            }
        }*/


        return rootView;
    }


    @Override
    public void onPause() {
        super.onPause();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy-HH:mm");
        String formattedDate = df.format(Calendar.getInstance().getTime());

        if ( currentSequence != null) {
            currentSequence.setDate(formattedDate);
            if ( !getCajuSequenceLib().getSequenceList().contains( currentSequence ) )
                getCajuSequenceLib().addSequenceToSequenceList( currentSequence );
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onSeqEdFragmentInteraction(currentSequence.getTimeslots().get(position).getTime());
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    public void setCurrentSequence(Sequence currentSequence) {
        this.currentSequence = currentSequence;
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
        public void onSeqEdFragmentInteraction(int dfh);
    }




    public class SeqEdListViewAdapter extends ArrayAdapter<TimeSlot> {


        Context context;

        public SeqEdListViewAdapter(Context context, int resourceId, //resourceId=your layout
                                     List<TimeSlot> items) {
            super(context, resourceId, items);
            this.context = context;
        }

        /*private view holder class*/
        private class ViewHolder {

            EditText textBot;
            ImageView imageViewBot;
            TextView txtTime;
            ImageView imageViewTop;
            EditText textTop;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            final TimeSlot rowItemMovTsl = getItem(position);

            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.fragment_sequence_element, null);
                holder = new ViewHolder();
                holder.textBot = (EditText) convertView.findViewById(R.id.editText_note_bot);
                holder.imageViewBot = (ImageView) convertView.findViewById(R.id.imageView_player_bot);
                holder.txtTime = (TextView) convertView.findViewById(R.id.textView_time);
                holder.imageViewTop = (ImageView) convertView.findViewById(R.id.imageView_player_top);
                holder.textTop = (EditText) convertView.findViewById(R.id.editText_note_top);
                convertView.setTag(holder);
            } else
                holder = (ViewHolder) convertView.getTag();

            // time
            holder.txtTime.setText( Integer.toString(rowItemMovTsl.getTime()) );

            //bot player stuff
            if (rowItemMovTsl.getBotPlayerMov() == null ) {
                holder.imageViewBot.setImageDrawable( getResources().getDrawable( R.mipmap.ic_mov_cont ) );
                holder.textBot.setHint("...");
            } else {
                holder.textBot.setHint(rowItemMovTsl.getBotPlayerMov().getMovName());
                holder.textBot.setHint( rowItemMovTsl.getBotPlayerMov().getMovName());
                holder.imageViewBot.setImageResource(rowItemMovTsl.getBotPlayerMov().getMovIconID());
            }
            holder.textBot.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    rowItemMovTsl.getBotPlayerMov().setMovNote(s.toString());
                }
            });

            //top player stuff
            if (rowItemMovTsl.getTopPlayerMov() == null ){
                holder.imageViewTop.setImageDrawable( getResources().getDrawable( R.mipmap.ic_mov_cont ) );
                holder.textTop.setHint( "..." );
            } else {
                holder.imageViewTop.setImageResource(rowItemMovTsl.getTopPlayerMov().getMovIconID());
                holder.textTop.setHint(rowItemMovTsl.getTopPlayerMov().getMovName());
                holder.textTop.setText(rowItemMovTsl.getTopPlayerMov().getMovNote());
            }
            holder.textTop.addTextChangedListener( new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    rowItemMovTsl.getTopPlayerMov().setMovNote( s.toString() );
                }
            });

            return convertView;
        }
    }
}

package com.mm.caju;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mm.caju.caju_seqMdl.Sequence;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class SequenceLibraryFragment extends Fragment implements AbsListView.OnItemClickListener, AbsListView.OnItemLongClickListener {

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

    // TODO: Rename and change types of parameters
    public static SequenceLibraryFragment newInstance(String param1, String param2) {
        SequenceLibraryFragment fragment = new SequenceLibraryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SequenceLibraryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mAdapter = new SeqLibListViewAdapter(getActivity(),
                R.layout.seqlib_list_item, CajuMainActivity.getCajuSequenceLib().getSequenceList() );


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sequencelibrary, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set fragment itself as OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);

        return view;
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
    public void onPause() {
        super.onPause();

        Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Library saved ...", Toast.LENGTH_SHORT);
        toast.show();

        ((CajuMainActivity)getActivity()).saveSeqLib();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onSeqLibFragmentInteraction( CajuMainActivity.getCajuSequenceLib().getSequenceList().get(position) );
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        CheckBox enableDelBox = (CheckBox) getActivity().findViewById(R.id.checkBox_enableDel);
        if ( enableDelBox.isChecked() ) {
            Sequence toRemove = (Sequence) mAdapter.getItem(position);
            ((ArrayAdapter) mAdapter).remove(toRemove);

            // Vibrate for X milliseconds
            Vibrator vib = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            vib.vibrate(50);
        }
        return false;
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
        public void onSeqLibFragmentInteraction(Sequence selSeq);
    }


    /**
     *
     */
    public class SeqLibListViewAdapter extends ArrayAdapter<Sequence> {

        Context context;

        public SeqLibListViewAdapter(Context context, int resourceId, //resourceId=your layout
                                     List<Sequence> items) {
            super(context, resourceId, items);
            this.context = context;
        }

        /*private view holder class*/
        private class ViewHolder {
            TextView txtDate;
            TextView txtTitle;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            Sequence rowItemSeq = getItem(position);

            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.seqlib_list_item, null);
                holder = new ViewHolder();
                holder.txtDate = (TextView) convertView.findViewById(R.id.textView_seqDate);
                holder.txtTitle = (TextView) convertView.findViewById(R.id.textView_seqTitle);
                convertView.setTag(holder);
            } else
                holder = (ViewHolder) convertView.getTag();

            holder.txtTitle.setText(rowItemSeq.getSeqTitle());
            holder.txtDate.setText(rowItemSeq.getDate());

            return convertView;
        }


    }
}

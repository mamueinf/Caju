package com.mm.caju;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.mm.caju.caju_seqMdl.DefMovement;
import com.mm.caju.caju_seqMdl.MiscMovement;
import com.mm.caju.caju_seqMdl.Movement;
import com.mm.caju.caju_seqMdl.MovementLib;
import com.mm.caju.caju_seqMdl.OffMovement;
import com.mm.caju.caju_seqMdl.Sequence;
import com.mm.caju.caju_seqMdl.SequenceLib;
import com.mm.caju.caju_seqMdl.TimeSlot;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashSet;


public class CajuMainActivity extends ActionBarActivity implements SequenceLibraryFragment.OnFragmentInteractionListener, SequenceElementFragment.OnFragmentInteractionListener, MovementLibraryFragment2.OnFragmentInteractionListener, SequenceEditorFragment.OnFragmentInteractionListener {


    private static MovementLib cajuMovementLib = null;
    private static SequenceLib cajuSequenceLib = null;
    private static Sequence currentSequence = null;

    private static SequenceEditorFragment mSeqEdFragment = null;
    private static SequenceLibraryFragment mSeqLibFragment = null;
    private static MovementLibraryFragment2 mMovLibFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new WelcomScreenFragment())
                    .commit();
        }

        loadMovLib();
        loadSeqLib();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_show_seqed:
                showSeqEd();
                return true;
            case R.id.action_show_seqlib:
//                Toast toast = Toast.makeText( getApplicationContext(), "Switching to Sequence Library ...", Toast.LENGTH_SHORT);
//                toast.show();
                showSeqLib();
                return true;
            case R.id.action_show_movlib:
//                Toast toast = Toast.makeText( getApplicationContext(), "Switching to Movement Library ...", Toast.LENGTH_SHORT);
//                toast.show();
                showMovLib();
                return true;
            case R.id.action_settings:
                Toast toast = Toast.makeText(getApplicationContext(), "No options yet ...", Toast.LENGTH_SHORT);
                toast.show();
//                showSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void showSeqEd() {

        // create editor fragment, if necessary
        if (mSeqEdFragment == null)
            mSeqEdFragment = new SequenceEditorFragment();
        else {
            // if not, and ...
            // if already in editor, reselecting the editor should open it with a new blank sequence
            if (mSeqEdFragment.isVisible()) {
                // therefore, remove the fragment
                mSeqEdFragment.onPause();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.remove(mSeqEdFragment);
                transaction.commit();

                // remove sequence
                currentSequence = null;

                Toast toast = Toast.makeText(getApplicationContext(), "New Sequence ...", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        // create editable sequence, if necessary
        if ( currentSequence == null ) {
            currentSequence = new Sequence();
            SimpleDateFormat df = new SimpleDateFormat("dd.MM.yy - HH:mm");
            String formattedDate = df.format(Calendar.getInstance().getTime());
            currentSequence.setSeqTitle("Sequence from\n" + formattedDate );
            currentSequence.setTimeslots(new ArrayList<TimeSlot>());
        }

        // open editor fragment or restore editor fragment from backstack
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        if (!getFragmentManager().popBackStackImmediate("show_seqed", 0)) {
            transaction.replace(R.id.container, mSeqEdFragment, "SEQED");
            transaction.addToBackStack("show_seqed");
            transaction.commit();
        } else {
            getFragmentManager().popBackStackImmediate("show_seqed", 0);
        }
    }


    private void showSeqLib() {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (mSeqLibFragment == null) {
            mSeqLibFragment = new SequenceLibraryFragment();
        }
        if (!getFragmentManager().popBackStackImmediate("show_seqlib", 0)){
            transaction.replace(R.id.container, mSeqLibFragment);
            transaction.addToBackStack("show_seqlib");
            transaction.commit();
        } else{
            getFragmentManager().popBackStackImmediate("show_seqlib", 0);
        }
    }

    private void showMovLib() {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (mMovLibFragment == null) {
            mMovLibFragment = new MovementLibraryFragment2();
        }
        if (!getFragmentManager().popBackStackImmediate("show_movlib", 0)){
            transaction.replace(R.id.container, mMovLibFragment);
            transaction.addToBackStack("show_movlib");
            transaction.commit();
        } else{
            getFragmentManager().popBackStackImmediate("show_movlib", 0);
        }
    }

    private void showSettings() {


    }



    private void loadSeqLib() {
        if (cajuSequenceLib == null) {
            //to this path add a new directory path and create new App dir
            File appDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Caju");

            appDir.mkdirs();

            File xmlFile = new File(appDir + "/SequenceLib.xml");

            // Deserialize the SeqLib from file
            if (!xmlFile.exists()) {
                cajuSequenceLib = new SequenceLib();
                cajuSequenceLib.setSequenceList( new ArrayList<Sequence>() );
                cajuSequenceLib.addSequenceToSequenceList(createExampleSequence());
                saveSeqLib();

                Toast toast = Toast.makeText( getApplicationContext(), " Empty SeqLib created ...", Toast.LENGTH_SHORT);
                toast.show();

            } else
                try {
                    Serializer serializer = new Persister();
                    cajuSequenceLib = serializer.read(SequenceLib.class, xmlFile);

                    Toast toast = Toast.makeText( getApplicationContext(), "SeqLib read from file ...", Toast.LENGTH_SHORT);
                    toast.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
    }

    protected void saveSeqLib(){

        //to this path add a new directory path and create new App dir
        File appDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Caju");

        appDir.mkdirs();

        File xmlFile = new File(appDir + "/SequenceLib.xml");

        /* Serialize the SeqLib  */
        try
        {
            Serializer serializer = new Persister();
            serializer.write(cajuSequenceLib, xmlFile);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void loadMovLib() {
        if (cajuMovementLib == null) {
            //to this path add a new directory path and create new App dir
            File appDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Caju");

            appDir.mkdirs();

            File xmlFile = new File(appDir + "/MovementLib.xml");

            // Deserialize the MovLib from file
            if (!xmlFile.exists()) {
                cajuMovementLib = createDefaultMovLib(xmlFile);
                Toast toast = Toast.makeText( getApplicationContext(), " Default MovLib created ...", Toast.LENGTH_SHORT);
                toast.show();

            } else
                try {
                    Serializer serializer = new Persister();
                    cajuMovementLib = serializer.read(MovementLib.class, xmlFile);
                    Toast toast = Toast.makeText( getApplicationContext(), "MovLib read from file ...", Toast.LENGTH_SHORT);
                    toast.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
    }

    private MovementLib createDefaultMovLib(File xmlFile) {
        /**
         * create and serialize test movement library
         * data structure
         */
        MovementLib movLib = new MovementLib();
        movLib.setOffMovList( new LinkedHashSet<OffMovement>() );
        movLib.setDefMovList( new LinkedHashSet<DefMovement>() );
        movLib.setMiscMovList( new LinkedHashSet<MiscMovement>() );

        // Def
        DefMovement ndA = new DefMovement();
        ndA.setMovName("Negativa de Angola");
        ndA.setMovIconID(R.mipmap.ic_defmov_nda);
        movLib.addDefMovementToDefMovList(ndA);

        DefMovement neg = new DefMovement();
        neg.setMovName("Negativa");
        neg.setMovIconID(R.mipmap.ic_defmov_neg);
        movLib.addDefMovementToDefMovList(neg);

        DefMovement ngc = new DefMovement();
        ngc.setMovName("Negaca");
        ngc.setMovIconID(R.mipmap.ic_defmov_ngc);
        movLib.addDefMovementToDefMovList(ngc);

        DefMovement coc = new DefMovement();
        coc.setMovName("Cocorinha");
        coc.setMovIconID(R.mipmap.ic_defmov_coco);
        movLib.addDefMovementToDefMovList(coc);

        DefMovement esq = new DefMovement();
        esq.setMovName("Esquiva");
        esq.setMovIconID(R.mipmap.ic_defmov_esq);
        movLib.addDefMovementToDefMovList(esq);

        // Off
        OffMovement rdA = new OffMovement();
        rdA.setMovName("Rabo de Arraia");
        rdA.setMovIconID(R.mipmap.ic_offmov_rda);
        movLib.addOffMovementToOffMovList(rdA);

        OffMovement mLdF = new OffMovement();
        mLdF.setMovName("Meia Lua de frente");
        mLdF.setMovIconID(R.mipmap.ic_offmov_mldf);
        movLib.addOffMovementToOffMovList(mLdF);

        OffMovement mLdC = new OffMovement();
        mLdC.setMovName("Meia Lua de costa");
        mLdC.setMovIconID(R.mipmap.ic_offmov_mldc);
        movLib.addOffMovementToOffMovList(mLdC);

        OffMovement chp = new OffMovement();
        chp.setMovName("Chapa");
        chp.setMovIconID(R.mipmap.ic_offmov_chp);
        movLib.addOffMovementToOffMovList(chp);

        OffMovement mrt = new OffMovement();
        mrt.setMovName("Martello");
        mrt.setMovIconID(R.mipmap.ic_offmov_mrtl);
        movLib.addOffMovementToOffMovList(mrt);

        OffMovement rast = new OffMovement();
        rast.setMovName("Rasteira");
        rast.setMovIconID(R.mipmap.ic_offmov_rast);
        movLib.addOffMovementToOffMovList(rast);

        OffMovement tes = new OffMovement();
        tes.setMovName("Tessoura");
        tes.setMovIconID(R.mipmap.ic_offmov_tes);
        movLib.addOffMovementToOffMovList(tes);

        OffMovement cab = new OffMovement();
        cab.setMovName("Cabecada");
        cab.setMovIconID(R.mipmap.ic_offmov_cab);
        movLib.addOffMovementToOffMovList(cab);

        OffMovement vin = new OffMovement();
        vin.setMovName("Vingativa");
        vin.setMovIconID(R.mipmap.ic_offmov_vin);
        movLib.addOffMovementToOffMovList(vin);

        // Misc
        MiscMovement ging = new MiscMovement();
        ging.setMovName("Ginga");
        ging.setMovIconID(R.mipmap.ic_miscmov_ging);
        movLib.addMiscMovementToMiscMovList(ging);

        MiscMovement role = new MiscMovement();
        role.setMovName("Role");
        role.setMovIconID(R.mipmap.ic_miscmov_role);
        movLib.addMiscMovementToMiscMovList(role);

        MiscMovement vdj = new MiscMovement();
        vdj.setMovName("Virar de jogo");
        vdj.setMovIconID(R.mipmap.ic_miscmov_vdj);
        movLib.addMiscMovementToMiscMovList(vdj);

        MiscMovement fint = new MiscMovement();
        fint.setMovName("Finta");
        fint.setMovIconID(R.mipmap.ic_miscmov_fint);
        movLib.addMiscMovementToMiscMovList(fint);

        MiscMovement pass = new MiscMovement();
        pass.setMovName("Passada");
        pass.setMovIconID(R.mipmap.ic_miscmov_pass);
        movLib.addMiscMovementToMiscMovList(pass);

        MiscMovement au = new MiscMovement();
        au.setMovName("Au");
        au.setMovIconID(R.mipmap.ic_miscmov_au);
        movLib.addMiscMovementToMiscMovList(au);

        MiscMovement ban = new MiscMovement();
        ban.setMovName("Bananeira");
        ban.setMovIconID(R.mipmap.ic_miscmov_ban);
        movLib.addMiscMovementToMiscMovList(ban);

        MiscMovement qdr = new MiscMovement();
        qdr.setMovName("Queda de Rins");
        qdr.setMovIconID(R.mipmap.ic_miscmov_qdr);
        movLib.addMiscMovementToMiscMovList(qdr);

        MiscMovement qd4 = new MiscMovement();
        qd4.setMovName("Queda de Quatro");
        qd4.setMovIconID(R.mipmap.ic_miscmov_qd4);
        movLib.addMiscMovementToMiscMovList(qd4);

        MiscMovement pont = new MiscMovement();
        pont.setMovName("Ponte");
        pont.setMovIconID(R.mipmap.ic_miscmov_pont);
        movLib.addMiscMovementToMiscMovList(pont);

        MiscMovement cont = new MiscMovement();
        cont.setMovName("...");
        cont.setMovIconID(R.mipmap.ic_mov_cont);
        movLib.addMiscMovementToMiscMovList(cont);


        /* Serialize the MovLib for next time */
        try
        {
            Serializer serializer = new Persister();
            serializer.write(movLib, xmlFile);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        return movLib;
    }

    private Sequence createExampleSequence() {

        Sequence seq = new Sequence();
        seq.setSeqTitle( "Example Sequence" );
        seq.setDate("long time ago ...");

        TimeSlot t1 = new TimeSlot();
        t1.setTime(1);

        Iterator it = cajuMovementLib.getMiscMovList().iterator();
        while ( it.hasNext() ) {

            Movement mov = (Movement) it.next();
            if ( mov.getMovName().equals("Ginga")) {
                try {
                    t1.setTopPlayerMov( (Movement)mov.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                try {
                    t1.setBotPlayerMov((Movement)mov.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        TimeSlot t2 = new TimeSlot();
        t2.setTime(2);

        it = cajuMovementLib.getOffMovList().iterator();
        while ( it.hasNext() ) {

            Movement mov = (Movement) it.next();
            if ( mov.getMovName().equals("Rabo de Arraia")) {
                try {
                    t2.setTopPlayerMov((Movement)mov.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        it = cajuMovementLib.getDefMovList().iterator();
        while ( it.hasNext() ) {

            Movement mov = (Movement) it.next();
            if ( mov.getMovName().equals("Negativa de Angola")) {
                try {
                    t2.setBotPlayerMov((Movement)mov.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        TimeSlot t3 = new TimeSlot();
        t3.setTime(3);

        it = cajuMovementLib.getMiscMovList().iterator();
        while ( it.hasNext() ) {

            Movement mov = (Movement) it.next();
            if ( mov.getMovName().equals("V... de Jogo")) {
                try {
                    t3.setTopPlayerMov((Movement)mov.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                break;
            }
        }


        TimeSlot t4 = new TimeSlot();
        t4.setTime(4);

        it = cajuMovementLib.getOffMovList().iterator();
        while ( it.hasNext() ) {

            Movement mov = (Movement) it.next();
            if ( mov.getMovName().equals("Rabo de Arraia")) {
                try {
                    t4.setTopPlayerMov((Movement)mov.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        it = cajuMovementLib.getMiscMovList().iterator();
        while ( it.hasNext() ) {

            Movement mov = (Movement) it.next();
            if ( mov.getMovName().equals("Role")) {
                try {
                    t4.setBotPlayerMov((Movement)mov.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        seq.addTimeSlotToTimeslots(t1);
        seq.addTimeSlotToTimeslots(t2);
        seq.addTimeSlotToTimeslots(t3);
        seq.addTimeSlotToTimeslots(t4);

        return seq;
    }


    @Override
    public void onSeqEdFragmentInteraction(Uri uri) {

    }

    @Override
    public void onMovLibFragmentInteraction(String id) {
        Toast toast = Toast.makeText( getApplicationContext(), "Thanks for clicking the move ...", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onSeqElementFragmentInteraction(Uri uri) {

    }

    @Override
    public void onSeqLibFragmentInteraction(Sequence selSeq) {

        Toast toast = Toast.makeText( getApplicationContext(), "Sequence opened ...", Toast.LENGTH_SHORT);
        toast.show();

        currentSequence = selSeq;
        showSeqEd();
    }


    /**
     * Getters and Setters
     */

    public static MovementLib getCajuMovementLib() {
        return cajuMovementLib;
    }

    public static void setCajuMovementLib(MovementLib cajuMovementLib) {
        CajuMainActivity.cajuMovementLib = cajuMovementLib;
    }

    public static SequenceLib getCajuSequenceLib() {
        return cajuSequenceLib;
    }

    public static void setCajuSequenceLib(SequenceLib cajuSequenceLib) {
        CajuMainActivity.cajuSequenceLib = cajuSequenceLib;
    }

    public static Sequence getCurrentSequence() {
        return currentSequence;
    }

    public static void setCurrentSequence(Sequence currentSequence) {
        CajuMainActivity.currentSequence = currentSequence;
    }

    public void showSeqEd(View v){
        showSeqEd();
    }

    public void showSeqLib(View v){
        showSeqLib();
    }

    public void showMovLib(View v){
        showMovLib();
    }

    public void deleteMarkedSequencesFromLib(View view) {
    }

    public void toggleSeqDel(View view) {

        CheckBox enableDelBox = (CheckBox) findViewById(R.id.checkBox_enableDel);
        if ( enableDelBox.isChecked() )
            findViewById(R.id.textView_deleteHint).setVisibility( View.VISIBLE );
        else findViewById(R.id.textView_deleteHint).setVisibility( View.INVISIBLE );
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class WelcomScreenFragment extends Fragment {

        public WelcomScreenFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }



    }
}

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
import android.widget.Toast;

import com.mm.caju.caju_seqMdl.DefMovement;
import com.mm.caju.caju_seqMdl.MiscMovement;
import com.mm.caju.caju_seqMdl.MovementLib;
import com.mm.caju.caju_seqMdl.OffMovement;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.LinkedHashSet;


public class CajuMainActivity extends ActionBarActivity implements SequenceElement.OnFragmentInteractionListener, MovementLibraryFragment2.OnFragmentInteractionListener, MovementLibraryFragment.OnFragmentInteractionListener, SequenceEditorFragment.OnFragmentInteractionListener {


    private static MovementLib cajuMovementLib = null;

    private SequenceEditorFragment mSeqEdFragment = null;
    private MovementLibraryFragment2 mMovLibFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
//        }

        loadMovLib();
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
                openSeqEd();
                return true;
            case R.id.action_show_movlib:
                openMovLib();
                return true;
            case R.id.action_settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSeqEd() {

        Toast toast = Toast.makeText( getApplicationContext(), "Switching to Sequence Editor ...", Toast.LENGTH_SHORT);
        toast.show();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (mSeqEdFragment == null) {
            mSeqEdFragment = new SequenceEditorFragment();
            transaction.replace(R.id.container, mSeqEdFragment);
            transaction.commit();
        } else{
            getFragmentManager().popBackStack();
        }

    }

    private void openMovLib() {

        Toast toast = Toast.makeText( getApplicationContext(), "Switching to Movement Library ...", Toast.LENGTH_SHORT);
        toast.show();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        if (mMovLibFragment == null) mMovLibFragment = new MovementLibraryFragment2();

        transaction.replace(R.id.container, mMovLibFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void openSettings() {

        Toast toast = Toast.makeText( getApplicationContext(), "No options yet ...", Toast.LENGTH_SHORT );
        toast.show();
    }

    public void loadMovLib() {
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

        DefMovement coc = new DefMovement();
        coc.setMovName("Cocorinha");
        coc.setMovIconID(R.mipmap.ic_defmov_coco);
        movLib.addDefMovementToDefMovList(coc);

        DefMovement esq = new DefMovement();
        esq.setMovName("Esqiva");
        esq.setMovIconID(R.mipmap.ic_defmov_esq);
        movLib.addDefMovementToDefMovList(esq);

        // Off
        OffMovement rdA = new OffMovement();
        rdA.setMovName("Rabo de Arraia");
        rdA.setMovIconID(R.mipmap.ic_offmov_rda);
        movLib.addOffMovementToOffMovList(rdA);

        OffMovement mLdF = new OffMovement();
        mLdF.setMovName("Meia Lua de Frente");
        mLdF.setMovIconID(R.mipmap.ic_offmov_mldf);
        movLib.addOffMovementToOffMovList(mLdF);

        OffMovement mLdC = new OffMovement();
        mLdC.setMovName("Meia Lua de Costa");
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
        vdj.setMovName("V... de Jogo");
        vdj.setMovIconID(R.mipmap.ic_miscmov_vdj);
        movLib.addMiscMovementToMiscMovList(vdj);

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



    @Override
    public void onMovLibFragmentInteraction(Uri uri) {

    }

    @Override
    public void onSeqEdFragmentInteraction(Uri uri) {

    }

    @Override
    public void onMovLibFragmentInteraction(String id) {

    }

    @Override
    public void onSeqElementFragmentInteraction(Uri uri) {

    }


    public MovementLib getCajuMovementLib() {
        return cajuMovementLib;
    }

    public void setCajuMovementLib(MovementLib cajuMovementLib) {
        CajuMainActivity.cajuMovementLib = cajuMovementLib;
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}

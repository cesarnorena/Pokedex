package co.com.cesarnorena.pokedex.controller.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import co.com.cesarnorena.pokedex.R;
import co.com.cesarnorena.pokedex.controller.PokemonListFragment;

/**
 * Created by Cesar on 16/01/2016.
 *
 * Actividad principal encargada de manejar los Framegntos asociados a la aplicacion
 */
public class MainActivity extends AppCompatActivity {

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();
    }

    /**
     * Inicializa el Fragmento inicial
     */
    private void initFragment() {
        fragment = new PokemonListFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_main_container, fragment,
                PokemonListFragment.class.getSimpleName());
        transaction.commit();
    }

    /**
     * Reemplaza un fragmento por otro
     * @param newFragment Nuevo Fragmento
     * @param newTag Nuevo Tag
     * @param addToBackStack True si quiere que queda en el backstack
     */
    public void replaceFragment(Fragment newFragment, String newTag, boolean addToBackStack) {
        String tag = fragment.getTag();
        fragment = newFragment;

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_main_container, newFragment, newTag);
        if (addToBackStack)
            transaction.addToBackStack(tag);
        transaction.commit();
    }

    /**
     * Reemplaza un Fragmento por otro agregando argumentos a traves de un Bundle
     * @param newFragment Nuevo Fragmento
     * @param newTag Nuevo Tag
     * @param addToBackStack True si quiere que queda en el backstack
     * @param args Bundle con la info
     */
    public void replaceFragment(Fragment newFragment, String newTag, boolean addToBackStack, Bundle args) {
        newFragment.setArguments(args);
        replaceFragment(newFragment, newTag, addToBackStack);
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getFragmentManager();
        int backStackCount = manager.getBackStackEntryCount();

        if (backStackCount > 0) {
            FragmentManager.BackStackEntry backStackEntry = manager.getBackStackEntryAt(backStackCount - 1);

            int id = backStackEntry.getId();
            fragment = manager.findFragmentByTag(backStackEntry.getName());
            manager.popBackStack(id, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else
            super.onBackPressed();
    }
}

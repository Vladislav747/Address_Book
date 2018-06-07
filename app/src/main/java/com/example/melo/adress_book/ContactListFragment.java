package com.example.melo.adress_book;

import android.app.FragmentTransaction;
import android.app.ListFragment;

/**
 * Created by Melo on 07.06.2018.
 */

public class ContactListFragment extends ListFragment{
    // callback methods implemented by MainActivity
    public interface ContactListFragmentListener
    {
        // called when user selects a contact
        public void onContactSelected(long rowID);

        // called when user decides to add a contact
        public void onAddContact();
    }
    }


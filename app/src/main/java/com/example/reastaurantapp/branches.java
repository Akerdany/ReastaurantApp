package com.example.reastaurantapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Map;

public class branches extends Fragment implements AdapterView.OnItemSelectedListener
{
    Spinner sp;
    ArrayAdapter <CharSequence> ad;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_branches, container, false);
        sp = v.findViewById(R.id.spinner);
        ad = ArrayAdapter.createFromResource(this.getActivity(), R.array.branches, android.R.layout.simple_spinner_item);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(ad);
        sp.setOnItemSelectedListener(this);
        return v;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
    {
        String test = parent.getItemAtPosition(pos).toString();
        switch(pos)
        {
            case 1:
                Fragment fragment = new GR();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.branchesfrag, fragment);
                transaction.commit();
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent)
    {
        // Another interface callback
    }

}

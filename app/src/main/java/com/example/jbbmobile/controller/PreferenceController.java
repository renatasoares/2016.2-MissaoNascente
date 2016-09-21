package com.example.jbbmobile.controller;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import com.example.jbbmobile.dao.ExplorerDAO;
import com.example.jbbmobile.model.Explorer;

public class PreferenceController {
    private ExplorerDAO dao;
    private Explorer explorer;

    public boolean updateNickname(String newNickname, String email, Context preferenceContext){
        setDao(new ExplorerDAO(preferenceContext));
        /* Create an explorer, so we can search his register by email */
        setExplorer(new Explorer());
        getExplorer().setEmail(email);
        setExplorer(getDao().findExplorer(getExplorer()));
        /* Now that we found the explorer that will be update, lets change the nickname */
        getExplorer().setNickname(newNickname);
        /* Send the updated object to update */

        try{

            getDao().updateExplorer(getExplorer());
        }catch(SQLiteConstraintException e){
            throw e;
        }
        return true;

    }

    public void deleteExplorer(String password, String email, Context context) {
        Explorer tempExplorer = new Explorer();
        tempExplorer.setPassword(password);
        setDao(new ExplorerDAO(context));
        setExplorer(new Explorer());
        getExplorer().setEmail(email);
        setExplorer(getDao().findExplorer(getExplorer()));

        getExplorer().setPassword(password, getExplorer().getPassword());
        getDao().deleteExplorer(getExplorer());
    }


    public void deleteExplorer(String email, Context context){
        setDao(new ExplorerDAO(context));
        setExplorer(new Explorer());
        getExplorer().setEmail(email);
        setExplorer(getDao().findExplorer(getExplorer()));
        getDao().deleteExplorer(getExplorer());
    }

    public Explorer getExplorer() {
        return explorer;
    }

    public void setExplorer(Explorer explorer) {
        this.explorer = explorer;
    }

    public ExplorerDAO getDao() {
        return dao;
    }

    public void setDao(ExplorerDAO dao) {
        this.dao = dao;
    }
}
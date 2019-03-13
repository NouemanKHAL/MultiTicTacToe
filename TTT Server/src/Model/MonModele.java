/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author nouemankhal
 */
public class MonModele extends AbstractTableModel{
    private int NbCol = 0, NbLig = 0;
    private String[] Titres;
    private ArrayList<Vector<String>> MesLignes =  new ArrayList<Vector<String>>();
    
    public MonModele(ResultSet rs) {
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            NbCol = rsmd.getColumnCount();
            Titres = new String[NbCol];
            for(int i = 0; i < NbCol; ++i) {
                Titres[i] = rsmd.getColumnLabel(i + 1);
            }
            
            Vector<String> Ligne;
            
          
            while(rs.next()) {
                Ligne = new Vector<String>();
                for(int i = 0; i < NbCol; ++i) {
                    if(rs.getObject(i + 1) == null) {
                        Ligne.add(" ");
                    } else {
                        Ligne.add(rs.getObject(i + 1).toString());
                    }
                    
                }
                NbLig++;
                MesLignes.add(Ligne);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
            
    }

    @Override
    public int getRowCount() {
        return NbLig;
    }

    @Override
    public int getColumnCount() {
        return NbCol;
    }

    @Override
    public String getColumnName(int column) {
        return Titres[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return MesLignes.get(rowIndex).get(columnIndex);
    }

    public String[] getTitres() {
        return Titres;
    }

    public ArrayList<Vector<String>> getMesLignes() {
        return MesLignes;
    }
    
    
    
}

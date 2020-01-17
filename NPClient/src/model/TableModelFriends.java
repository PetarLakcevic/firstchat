/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author snezana.lakcevic
 */
public class TableModelFriends extends AbstractTableModel {

    private List<User> friends;

    public TableModelFriends() {
        friends = new ArrayList<>();

    }

    public TableModelFriends(List<User> friends) {
        this.friends = friends;
    }

    @Override
    public int getRowCount() {
        return friends.size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User u = friends.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return u.getUsername();
            default:
                return "";
        }

    }
    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Friends";
            default:
                return "";
        }
    }

    public Object getFriend() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

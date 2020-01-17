/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transfer;

/**
 *
 * @author student1
 */
public interface Operation {

    public static final int OPERATION_LOGIN = 1;
    public static final int OPERATION_LOGOUT = -1;
    public static final int OPERATION_SET_STATUS = 2;
    public static final int OPERATION_SEND_MESSAGE = 3;
    public static final int OPERATION_RECEIVE_MESSAGE = 4;
    public static final int OPERATION_GET_PROFILE = 5;
    public static final int OPERATION_REGISTER = 7;
    public static final int OPERATION_GET_FRIENDS = 8;
    public static final int OPERATION_ADD_FRIEND = 10;
    public static final int OPERATION_PING_UPDATE = 12;
    public static final int OPERATION_GET_MESSAGES = 13;
    public static final int OPERATION_SEND_FILE = 14;
    public static final int OPERATION_REMOVE_FRIEND = 15;
}

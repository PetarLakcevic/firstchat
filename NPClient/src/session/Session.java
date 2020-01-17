/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import domain.User;
import enums.UseCase;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Session {

    private static Session instance;
    private User user;
    private UseCase currentUseCase;
    private final Map<String, Object> useCaseParams;
    private User selectedFriend;

    private Session() {
        useCaseParams = new HashMap<>();
        currentUseCase = UseCase.LOGIN;
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public User getCurrentUser() {
        return user;
    }

    public void setCurrentUser(User currentUser) {
        this.user = currentUser;
    }

    public UseCase getCurrentUseCase() {
        return currentUseCase;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getSelectedFriend() {
        return selectedFriend;
    }

    public void setSelectedFriend(User selectedFriend) {
        this.selectedFriend = selectedFriend;
    }

    public void setCurrentUseCase(UseCase currentUseCase) {
        this.currentUseCase = currentUseCase;
    }

    public Map<String, Object> getUseCaseParams() {
        return useCaseParams;
    }

}

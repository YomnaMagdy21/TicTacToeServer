/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author HP
 */
public class DTO {

    private String username;
    private String password;
    private int score;
    private String status;

    public DTO(String username, String password, int score, String status) {
        this.username = username;
        this.password = password;
        this.score = score;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

public String toString() {
    return "DTO{" +
            "username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", score=" + score +
            ", status='" + status + '\'' +
            '}';
}
}

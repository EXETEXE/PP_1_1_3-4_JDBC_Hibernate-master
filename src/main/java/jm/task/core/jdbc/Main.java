package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Vasya", "Pupkin", (byte) 42);

        userService.saveUser("Nick", "Gurr", (byte) 15);

        userService.saveUser("Steve", "Stivenson", (byte) 25);

        userService.saveUser("Bill", "Gates", (byte) 60);


        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
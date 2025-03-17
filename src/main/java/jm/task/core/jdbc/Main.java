package jm.task.core.jdbc;


import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Util.getConnection();
        UserDao userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();

        userDao.saveUser("Федот", "Первый", (byte) 44);
        userDao.saveUser("Евграф", "Второй", (byte) 55);
        userDao.saveUser("Инокентий", "Третий", (byte) 31);
        userDao.saveUser("Аристарх", "Четвертый", (byte) 37);

        userDao.removeUserById(2);
        userDao.getAllUsers();
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}

package com.alvaro.proyectofinal.BaseDeDatos

import com.mysql.jdbc.Connection
import java.sql.DriverManager
import java.sql.SQLException

class Database {

    companion object{
        private const val url = "jdbc:mysql://192.168.1.135:3306/proyectofinal"
        private const val user = "admin"
        private const val password = ""
        private var connection: Connection? = null

        fun getConnection(): Connection? {
            if (connection == null) {
                try {
                    Class.forName("com.mysql.jdbc.Driver")
                    connection = DriverManager.getConnection(url, user, password) as Connection?
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                } catch (e: SQLException) {
                    e.printStackTrace()
                }
            }
            return connection

        }
    }
}
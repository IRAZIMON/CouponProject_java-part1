package com.ira.DB;

import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionPool {

	private Stack<Connection> connections = new Stack<>();

	private static ConnectionPool instance = null;

	private ConnectionPool() {
		for (int i = 0; i < 10; i++) {
			System.out.println("Creating connection #" + i);
			try {
				Connection conn = DriverManager.getConnection(DatabaseManager.getUrl(), DatabaseManager.getUsername(),
						DatabaseManager.getPassword());
				connections.push(conn);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

		}
	}

	public static ConnectionPool getInstance() {
		if (instance == null) {
			synchronized (ConnectionPool.class) {
				if (instance == null) {
					instance = new ConnectionPool();

				}

			}

		}
		return instance;
	}

	public Connection getConnection() throws InterruptedException {
		synchronized (connections) {
			if (connections.isEmpty()) {
				connections.wait();
			}
			return connections.pop();

		}

	}

	public void returnConnection(Connection conn) {
		synchronized (connections) {
			connections.push(conn);
			connections.notify();
		}
	}

	public void closeAllConnections() throws InterruptedException {

		synchronized (connections) {
			while (connections.size() < 10) {
				connections.wait();
				for (Connection conn : connections) {
					try {
						conn.close();
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					}
				}
			}
		}
	}
}

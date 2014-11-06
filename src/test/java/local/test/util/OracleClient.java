package local.test.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import local.test.util.Config;


/**
 * @author hernan
 * @version 1.0
 * 
 */
public class OracleClient {

	private Connection _instance;
	private Statement _stmt;
	private ResultSet _rset;
	private Map<String, String> fields;

	private static final String HOSTNAME = "oracle_hostname";
	private static final String PORT = "oracle_port";
	private static final String SID = "oracle_sid";
	private static final String USERNAME = "oracle_username";
	private static final String PASSWORD = "oracle_password";
	private static final String ENV = "oracle_env";
	private static final String FILE = "config.properties";

	public OracleClient() {
		fields = new HashMap<String, String>();
	}

	public Map<String, String> getFields() {
		return this.fields;
	}

	public boolean setFields(Map<String, String> params) {
		if (!params.containsKey(HOSTNAME) || !params.containsKey(PORT)
				|| !params.containsKey(SID) || !params.containsKey(USERNAME)
				|| !params.containsKey(PASSWORD)) {
			return false;
		} else {
			this.fields = params;
		}
		return true;
	}

	/**
	 * Reset instance
	 * 
	 * @return
	 */
	public Connection getNewInstance() {
		this._instance = null;
		String hostname = null;
		int port = 0;
		String sid = null;
		String username = null;
		String password = null;
		if (this.fields.isEmpty()) {
			Config conf = new Config(FILE);
			hostname = conf.getProperty(HOSTNAME);
			port = Integer.parseInt(conf.getProperty(PORT).trim());
			sid = conf.getProperty(SID);
			username = conf.getProperty(USERNAME);
			password = conf.getProperty(PASSWORD);
		} else {
			for (Map.Entry<String, String> param : fields.entrySet()) {
				switch (param.getKey()) {
				case HOSTNAME:
					hostname = param.getValue();
					break;
				case PORT:
					port = Integer.parseInt(param.getValue());
					break;
				case SID:
					sid = param.getValue();
					break;
				case USERNAME:
					username = param.getValue();
					break;
				case PASSWORD:
					password = param.getValue();
					break;
				default:
					println("invalid param " + param.getValue());
					break;
				}
			}
		}

		try {
			this._instance = DriverManager.getConnection("jdbc:oracle:thin:@"
					+ hostname + ":" + port + ":" + sid, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return this._instance;
	}

	/**
	 * 
	 * @return Connection instance
	 */
	public Connection getInstance() {
		String hostname = null;
		int port = 0;
		String sid = null;
		String username = null;
		String password = null;
		if (this._instance == null) {

			if (this.fields.size() < 1) {
				Config conf = new Config(FILE);
				hostname = conf.getProperty(HOSTNAME);
				port = Integer.parseInt(conf.getProperty(PORT).trim());
				sid = conf.getProperty(SID);
				username = conf.getProperty(USERNAME);
				password = conf.getProperty(PASSWORD);
			} else {
				for (Map.Entry<String, String> param : fields.entrySet()) {
					switch (param.getKey()) {
					case HOSTNAME:
						hostname = param.getValue();
						break;
					case PORT:
						port = Integer.parseInt(param.getValue());
						break;
					case SID:
						sid = param.getValue();
						break;
					case USERNAME:
						username = param.getValue();
						break;
					case PASSWORD:
						password = param.getValue();
						break;
					default:
						println("invalid param " + param.getValue());
						break;
					}
				}
			}
			try {
				this._instance = DriverManager.getConnection(
						"jdbc:oracle:thin:@" + hostname + ":" + port + ":"
								+ sid, username, password);
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return this._instance;
	}

	/**
	 * 
	 * @return
	 */
	public String getDbEnvironment() {
		return this.fields.get(ENV);
	}

	/**
	 * 
	 * @throws SQLException
	 */
	public void commit() throws SQLException {
		getInstance().commit();
	}

	/**
	 * 
	 * @throws SQLException
	 */
	public void closeConnection() throws SQLException {
		getInstance().close();
		this._instance = null;
	}

	/**
	 * 
	 * @throws SQLException
	 */
	public void closeStatement() throws SQLException {
		this._stmt.close();
	}

	/**
	 * 
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		commit();
		closeStatement();
		closeConnection();
	}

	/**
	 * 
	 * @param msg
	 */
	private void println(Object msg) {
		System.out.println(msg);
	}

	/**
	 * 
	 * @param sql
	 *            UPDATE, INSERT, DELETE SQL Query
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean executeUpdate(String sql) throws SQLException {

		if (getInstance().isClosed()) {
			println("renewing connection");
			this._stmt = getNewInstance().createStatement();
		} else {
			this._stmt = getInstance().createStatement();
		}

		if (this._stmt.executeUpdate(sql) == 1) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 
	 * @param sql
	 *            SELECT SQL Query
	 * @return ResultSet
	 * @throws SQLException
	 */
	public ResultSet executeQuery(String sql) throws SQLException {

		if (getInstance().isClosed()) {
			println("renewing connection");
			this._stmt = getNewInstance().createStatement();
		} else {
			this._stmt = getInstance().createStatement();
		}

		this._rset = this._stmt.executeQuery(sql);
		return this._rset;

	}

}

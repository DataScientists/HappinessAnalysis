/**
 * 
 */
package com.datascientists;

// import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * @author Troy Sadkowsky
 * 
 */
public class DBBaseObject {

	protected String id = "";
	protected String name = "";
	protected String description = "";
	public Connection connection = null;
	protected Statement statement = null;
	protected ResultSet resultSet = null;
	
	public DBBaseObject() {
	}
	public DBBaseObject(String id) {
		setId(id);
	}
	public String getId() {
		if (this.id == null) {
			this.id = "";
		}
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	protected Connection getConnection() {
		return connection;
	}
	protected void setConnection(Connection connection) {
		this.connection = connection;
	}
	public Connection connect() throws Exception {
        // Obtain our environment naming context
        Context initCtx = new InitialContext();
        
        // Look up our data source
		try {
			DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/HappinessAnalysis");
//			 Allocate and use a connection from the pool
	        if (connection==null || connection.isClosed()) {
	            this.connection = ds.getConnection();
	        }
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return connection;
	}
	public void closeConnection() {
        try {
    		if (connection != null && !connection.isClosed()) {
    			connection.close(); /* close the connection */
    		}
        } catch (SQLException e) {
        	//To Do
        }
	}
	protected void closeStatement() throws Exception {
		if (this.statement != null) {
			this.statement.close();
		} else {
			//To Do warning: There is no valid Statement to close!");
		}
	}
	public Statement getStatement() throws Exception {
		if (this.connection != null) {
			this.statement = connection.createStatement();
		}else{
//			To Do deal with Warning: connection is null!");
		}
		return this.statement;
	}
	protected ResultSet getResultSet() {
		return this.resultSet;
	}
	protected void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}
	protected void setStatement(Statement statement) {
		this.statement = statement;
	}
	protected void sqlInsertQuery(String query) throws Exception {	
		try{
			this.connect();
			Statement statement = this.getStatement();
			statement.executeUpdate(query);	
			// get last inserted id
			ResultSet rs = this.statement.getGeneratedKeys();
			if (rs != null && rs.next()) {
				setId(rs.getString(1));
			} else {
				// TODO deal with error
				throw new Exception("Couldn't get generated ID");
			}
			statement.close();
		}catch(Exception ex){
			throw new Exception("Critical Database problem "+ex.getMessage());
		}finally{
			this.closeConnection();
		}
		
	}
    public ResultSet sqlExecuteSelect(String sql) throws Exception {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            this.connect();
            stmt = this.connection.createStatement();
            if(this.connection.getMetaData().getDriverName().contains("MySQL")){
            	sql = sql.replaceAll("GETDATE","NOW");
            	sql = sql.replaceAll("SET DATEFORMAT dmy;","");
            }
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            if(ex.getSQLState().equalsIgnoreCase("08S01")){
            	try {
	            	this.connection.close();
	            	this.connect();
	            	stmt = this.connection.createStatement();
	            	rs = stmt.executeQuery(sql);
            	} catch (Exception e1){
            		//To Do deal with connection exception
            	}
            }else{
            	throw new Exception(ex.getMessage());
            }          
        }
        return rs;
    }
	protected String escapeSpecialCharacters(Object value) {
		String retValue = "";
		if(value==null){
			retValue = "NULL";
		} else if(value.getClass()==String.class){
			if((value==null)||(((String)value).equalsIgnoreCase(""))){
				retValue = "NULL";
			}else{
				retValue = ((String)value).replace("'","''");
				retValue = retValue.replace("\"","''");
			}		
		}else if(value.getClass()==Integer.class){
			if(((Integer)value == -1)||((Integer)value == 0)){
				retValue = "NULL";
			}
		}
		
		return retValue;
	}  
    public Long sqlExecuteInsert(String sql) throws Exception {
        Statement stmt = null;
        Long iRetValue;
        iRetValue = Long.valueOf(-1);
        ResultSet rs = null;
        try {
        	
        	this.connect();
        	
            this.connection.setAutoCommit(false);
            stmt = connection.createStatement();
            
            //System.out.println(sql);
            stmt.executeUpdate(sql);
            if (stmt.execute("SELECT @@IDENTITY")) {
                rs = (ResultSet) stmt.getResultSet();
                if (rs.next()) {
                    iRetValue = Long.valueOf(rs.getLong(1));
                }
                if (rs.getStatement() != null) {
                    rs.getStatement().close();
                }
                rs.close();
            }
            this.connection.commit();
            stmt.close();
            this.connection.setAutoCommit(false);
        } catch (SQLException ex) {
            throw new Exception("sqlExecuteUpdate " + ex.getMessage());
        } finally {
            this.closeConnection();
        }
        return iRetValue;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
    	if(this.description == null){
    		description = "&lt;not set&gt;";
    	}else if (this.description.equalsIgnoreCase("null")) {
    		description = "&lt;not set&gt;";
    	}else if (this.description.equalsIgnoreCase("")) {
    		description = "&lt;not set&gt;";
    	}
    	return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
	@Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if (obj != null) {
            if (obj.getClass() == this.getClass()) {
                if (((DBBaseObject) obj).getId().equalsIgnoreCase(this.getId())) {
                    isEqual = true;
                }
            }
        }
        return isEqual;
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

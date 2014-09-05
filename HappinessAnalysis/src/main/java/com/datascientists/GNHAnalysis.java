/**
 * 
 */
package com.datascientists;

import java.sql.SQLException;

/**
 * @author administrator
 *
 */
public class GNHAnalysis extends DBBaseObject {

	/**
	 * 
	 */
	public GNHAnalysis() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 */
	public GNHAnalysis(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public void save() throws Exception {
		String strSQL = "INSERT INTO analysis (name,description) VALUES('"
				+this.escapeSpecialCharacters(this.getName())+ "','"
				+this.escapeSpecialCharacters(this.getDescription())+ "')";
		try {
			this.setId(String.valueOf(this.sqlExecuteInsert(strSQL)));
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} finally {
			this.closeConnection();
		}
		
	}

}

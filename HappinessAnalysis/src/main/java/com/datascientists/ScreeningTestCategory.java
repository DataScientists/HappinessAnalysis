package com.datascientists;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ScreeningTestCategory extends DBBaseObject {

	private ArrayList<ScreeningItem> screenTestItems = new ArrayList<ScreeningItem>();
	
	public ScreeningTestCategory() {
		// TODO Auto-generated constructor stub
	}

	public ScreeningTestCategory(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<ScreeningItem> getScreenTestItems() {
		return screenTestItems;
	}

	public void setScreenTestItems(ArrayList<ScreeningItem> screenTestItems) {
		this.screenTestItems = screenTestItems;
	}

	public void save() throws Exception {
		String strSQL = "INSERT INTO screeningcategory (name,description,screeningtypeid) VALUES('"
				+ this.getName().replaceAll("'", "''")
				+ "','"
				+ this.getDescription().replaceAll("'", "''")
				+ "',1)";
		try {
			this.setId(String.valueOf(this.sqlExecuteInsert(strSQL)));
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} finally {
			this.closeConnection();
		}
		
	}
	protected void getPersistentObject() throws Exception {
		String strSQL = "SELECT * FROM screeningcategory WHERE id = "
				+ this.getId();
		ResultSet rs = null;
		try {
			rs = this.sqlExecuteSelect(strSQL);
			if (rs.next()) {
				this.setId(rs.getString("id"));
				this.setDescription(rs.getString("description"));
				this.setName(rs.getString("name"));
			}
			if (rs.getStatement() != null) {
				rs.getStatement().close();
			}
			rs.close();
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} finally {
			this.closeConnection();
		}
	}
}

package com.datascientists;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.StringUtils;


public class ScreeningItem extends DBBaseObject {

	private String screeningCategoryId = "";
	private String oneText = "";
	private String twoText = "";
	private String threeText = "";
	private String fourText = "";
	
	public String getOneText() {
		return oneText;
	}

	public void setOneText(String oneText) {
		this.oneText = oneText;
	}

	public String getTwoText() {
		return twoText;
	}

	public void setTwoText(String twoText) {
		this.twoText = twoText;
	}

	public String getThreeText() {
		return threeText;
	}

	public void setThreeText(String threeText) {
		this.threeText = threeText;
	}

	public String getFourText() {
		return fourText;
	}

	public void setFourText(String fourText) {
		this.fourText = fourText;
	}

	public ScreeningItem() {
		// TODO Auto-generated constructor stub
	}

	public ScreeningItem(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	public ArrayList<ScreeningItem> listAllScreeningItems() throws Exception{
		ArrayList<ScreeningItem> retValue = new ArrayList<ScreeningItem>();
		
		String strSQL = "";
        ResultSet rs = null;
        try {           
            strSQL = "SELECT *"
                    + " FROM screeningtestitem ";
            rs = this.sqlExecuteSelect(strSQL);
            if (rs != null) {
                while (rs.next()) {
                	ScreeningItem item = new ScreeningItem();
                	item.setId(rs.getString("id"));
                	item.setName(rs.getString("name"));
                	item.setOneText(rs.getString("onetext"));
                	item.setTwoText(rs.getString("twotext"));
                	item.setThreeText(rs.getString("threetext"));
                	item.setFourText(rs.getString("fourtext"));
                    item.setScreeningCategoryId(rs.getString("screeningcategoryid"));
                    retValue.add(item);
                }
                if (rs.getStatement() != null) {
                    rs.getStatement().close();
                }
                rs.close();
            }
            
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        } finally {
            this.closeConnection();
        }
		
		return retValue;
	}
	public void importScreeningItems() throws Exception{
		
		String filepath = "/home/administrator/Downloads/projectsectors.txt";
		BufferedReader br = new BufferedReader(new FileReader(filepath));
		ArrayList<ScreeningTestCategory> categories = new ArrayList<ScreeningTestCategory>();
	    try {
	        String line = br.readLine();
	        int iCatCount = 1;
	        ScreeningTestCategory cat = null;
	        ScreeningItem item = null;
	        while (line != null) {            
	            String[] row = line.split("\t");
	            if(row.length>0){
	            	if(row[0].equalsIgnoreCase(String.valueOf(iCatCount))){
	            		cat = new ScreeningTestCategory();
	            		cat.setName(row[1]);
	            		cat.save();
	            		iCatCount++;
	            		if(iCatCount==20){
	            			int i=0;
	            			i=i;
	            		}
	            	}else{
	            		
	            		if(row[0].contains(".")){
	            			item = new ScreeningItem();
		            		item.setScreeningCategoryId(cat.getId());
		            		item.setName(row[0]);
	            		}else{
	            			String stopFlag = row[0];
	            			int iStopFlag = 0;
	            			try{
	            				iStopFlag = Integer.parseInt(stopFlag);
	            			}catch(Exception e){
	            				//could not convert
	            			}
	            			if(iStopFlag==1){
	            				item.save();
	            			}else{
	            				String oneText = item.getOneText();
	            				oneText += row[0]+" ";
	            				item.setOneText(oneText);            				
	            				if(row.length>1){
	            					String twoText = item.getTwoText();
	            					twoText += row[1]+" ";
		            				item.setTwoText(twoText);
	            				}
	            				if(row.length>2){
		            				String threeText = item.getThreeText();
		            				threeText += row[2]+" ";
		            				item.setThreeText(threeText);
	            				}
	            				if(row.length>3){
	            					String fourText = item.getFourText();
		            				fourText += row[3]+" ";
		            				item.setFourText(fourText);
	            				}            				
	            			}
	            		}
	            		
	            		
	            	}
	            }
	            line = br.readLine();
	        }

	    } finally {
	        br.close();
	    }
		
	}

	private void save() throws Exception {
		String strSQL = "INSERT INTO screeningtestitem (name,description,onetext,twotext,threetext,fourtext,screeningcategoryid) VALUES('"
				+ this.getName().replaceAll("'", "''")
				+ "','"
				+ this.getDescription().replaceAll("'", "''")
				+ "','"
				+ this.escapeSpecialCharacters(this.getOneText()) + "','"
				+ this.escapeSpecialCharacters(this.getTwoText()) + "','"
				+ this.escapeSpecialCharacters(this.getThreeText()) + "','"
				+ this.escapeSpecialCharacters(this.getFourText()) + "',"+this.getScreeningCategoryId()+")";
		try {
			this.setId(String.valueOf(this.sqlExecuteInsert(strSQL)));
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} finally {
			this.closeConnection();
		}
		
	}
	public void save(String analysisId,String rating) throws Exception {
		String strSQL = "INSERT INTO analysis_screeningitems (screeningitemid,analysisid,rating) VALUES("
				+ this.getId()
				+ ","
				+ analysisId
				+ ","
				+ rating + ")";
		try {
			this.sqlExecuteInsert(strSQL);
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} finally {
			this.closeConnection();
		}
		
	}

	public String getScreeningCategoryId() {
		return screeningCategoryId;
	}

	public void setScreeningCategoryId(String screeningCategoryId) {
		this.screeningCategoryId = screeningCategoryId;
	}
	protected void getPersistentObject() throws Exception {
		String strSQL = "SELECT * FROM screeningtestitem WHERE id = "
				+ this.getId();
		ResultSet rs = null;
		try {
			rs = this.sqlExecuteSelect(strSQL);
			if (rs.next()) {
				this.setId(rs.getString("id"));
				this.setDescription(rs.getString("description"));
				this.setName(rs.getString("name"));
				this.setScreeningCategoryId(rs.getString("screeningcategoryid"));
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

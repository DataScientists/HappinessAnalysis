package com.datascientists;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class PageServlet
 */
public class PageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String pageName;
	private String analysisName;
	private String screeningTestType;
	private HttpSession session;
	HttpServletResponse httpServletResponse;
	HttpServletRequest httpServletRequest;
	PrintWriter printWriter;
	boolean authenticated = false;
	JSONObject jsonObject = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PageServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		ScreeningItem runner = new ScreeningItem();
		try {
			runner.importScreeningItems();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		StringBuffer jb = new StringBuffer();
		String line = null;
		session = request.getSession();
		httpServletResponse = response;
		httpServletRequest = request;
		printWriter = response.getWriter();
		
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null){
				jb.append(line);
			}
		} catch (IOException e) { //report an error 
			System.err.print("IO Exception reading http header");
		}
		if (!jb.toString().equals("")) {
			try {
				jsonObject = new JSONObject(jb.toString());
				pageName = jsonObject.getString("Page");
				if(jsonObject.has("TestType")){
					screeningTestType = jsonObject.getString("TestType");
				}else if(jsonObject.has("analysisName")){
					analysisName = jsonObject.getString("analysisName");
				}	 			
			} catch (JSONException je) {
				System.err.println("Failed to create json object");
			}
		}else{
			pageName = "0";
		}
		try {
			this.showPage(Integer.valueOf(pageName));
		} catch (Exception e) {
			e.printStackTrace();
			printWriter.write(e.getMessage());
		}
	}
	private void showPage(int newPageName) throws Exception {

		switch (newPageName) {
		case 0: showLoginPage();
		break;
		case 1: showScreenTestTypePage();
		break;
		case 2: showScreenTestPage();
		break;
		case 3: showAnalysisPage();
		break;
		}

	}
	private void showAnalysisPage() throws Exception {
		JSONArray data = new JSONArray();
		JSONArray items = jsonObject.has("items")?jsonObject.getJSONArray("items"):new JSONArray();
		GNHAnalysis gnhAnalysis = new GNHAnalysis();
		gnhAnalysis.setName(this.getAnalysisName());
		gnhAnalysis.save(); 
		ScreeningItem screeningItem = new ScreeningItem();
		String currentCategoryId = "0";
		for (int i = 0;i < items.length();i++) {
			JSONObject item = (JSONObject) items.get(i);
			String theId = String.valueOf(item.getInt("id"));
			if(theId.equalsIgnoreCase("0")){
				continue;
			}
			screeningItem.setId(theId);
			String score = String.valueOf(item.getInt("score"));
			screeningItem.save(gnhAnalysis.getId(), score);
			screeningItem.getPersistentObject();
			
			JSONObject o = new JSONObject();
			String categoryId = screeningItem.getScreeningCategoryId();
			String categoryName = "";
			if((!currentCategoryId.equalsIgnoreCase(categoryId))){
				ScreeningTestCategory screeningCategory = new ScreeningTestCategory(categoryId);
				screeningCategory.getPersistentObject();
				categoryName = screeningCategory.getName();
				currentCategoryId = categoryId;
			}
			o.put("categoryname", categoryName);
			o.put("theid", screeningItem.getId());
			o.put("name", screeningItem.getName());
			o.put("score", score);
			
			data.put(o);
		}
		httpServletResponse.setContentType("application/json");
		httpServletResponse.setHeader("Cache-Control", "no-cache");
		httpServletResponse.getWriter().write(data.toString());
	}

	private void showLoginPage() {
		
		String html = "";
		html += "<div id='login'>"
				+ "<h1>Sign In</h1>"
				+ "<div>"
				+ "  <div class='login-fields'>"
				+ "    <div class='field'>"
				+ "    <label for='loginName'>Username:</label>"
				+ "    <input type='text' id='loginName' name='loginName' value='' placeholder='Username' class='login username-field' />"
				+ "    </div>"
				+ "    <div class='field'><label for='password'>Password:</label>"
				+ "    <input type='password' id='password' name='password' value='' placeholder='Password' class='login password-field' />"
				+ "    </div>"
				+ "  </div>"
				+ "  <div class='login-actions'><span class='login-checkbox'>"
				+ "  <input id='Field' name='Field' type='checkbox' class='field login-checkbox' value='First Choice' tabindex='4' />"
				+ "  <label class='choice' for='Field'>Keep me signed in</label></span>"
				+ "  <button class='button btn btn-primary btn-large' value='Login'>Login</button>"
				+ "  <div id='loginResponse'></div>"
				+ "  </div>"
				+ "</div>"
				+ "</div>";
		
		printWriter.write(html);
	}
	private void showScreenTestTypePage() throws Exception {
		
		JSONArray data = null;
		
		data = new JSONArray();
		JSONObject o = new JSONObject();
		o.put("id", "Policy01");
		o.put("name", "Policy");
		o.put("description", "The Policy Screening Test");
		data.put(o);
		JSONObject o1 = new JSONObject();
		o1.put("id", "Project01");
		o1.put("name", "Project");
		o1.put("description", "The Project Screening Test");
		data.put(o1);

		httpServletResponse.setContentType("application/json");
		httpServletResponse.setHeader("Cache-Control", "no-cache");
		httpServletResponse.getWriter().write(data.toString());
	}
	private void showScreenTestPage() throws Exception {
		JSONArray data = null;
		ScreeningItem itemlink = new ScreeningItem();
		data = new JSONArray();
		String currentCategoryId = "";
		for(ScreeningItem item:itemlink.listAllScreeningItems()){
			JSONObject o = new JSONObject();
			String categoryId = item.getScreeningCategoryId();
			String categoryName = "";
			if((!currentCategoryId.equalsIgnoreCase(categoryId))){
				ScreeningTestCategory screeningCategory = new ScreeningTestCategory(categoryId);
				screeningCategory.getPersistentObject();
				categoryName = screeningCategory.getName();
				currentCategoryId = categoryId;
			}
			o.put("categoryname", categoryName);
			o.put("theid", item.getId());
			o.put("name", item.getName());
			o.put("one", item.getOneText());
			o.put("two", item.getTwoText());
			o.put("three", item.getThreeText());
			o.put("four", item.getFourText());
			data.put(o);
		}
		httpServletResponse.setContentType("application/json");
		httpServletResponse.setHeader("Cache-Control", "no-cache");
		httpServletResponse.getWriter().write(data.toString());
		
	}

	public String getAnalysisName() {
		return analysisName;
	}

	public void setAnalysisName(String analysisName) {
		this.analysisName = analysisName;
	}	
}

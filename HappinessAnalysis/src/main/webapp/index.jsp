<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="apple-mobile-web-app-capable" content="yes" /> 
<title>GNH Screening Tests</title>


<!-- Index page -->
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css">
<script src="http://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
<link rel="stylesheet" href="./css/PageLayout.css" type="text/css" />
<link rel="stylesheet" href="./css/LoginPage.css" type="text/css" />
<script type="text/javascript" src="./javascript/javascript.js"></script>

<link href="./css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="./css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css" />
<link href="./css/font-awesome.css" rel="stylesheet" />
<link href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,400,600" rel="stylesheet" />    
<link href="./css/base-admin.css" rel="stylesheet" type="text/css" />
<link href="./css/signin.css" rel="stylesheet" type="text/css" />


</head>
<body>
<div class="navbar navbar-fixed-top">
	
	<div class="navbar-inner">
		
		<div class="container">
			
			<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</a>
			
			<a  id="logo" class="brand" href="./">
					
			</a>			
			
			<div class="nav-collapse">
				<ul class="nav pull-right">					
					
					<li id='logout'> </li>
					
				</ul>
				
			</div><!--/.nav-collapse -->	
	
		</div> <!-- /container -->
		
	</div> <!-- /navbar-inner -->
	
</div> <!-- /navbar -->



<div class="account-container">
	
	<div class="content clearfix">
			
		<div id="mainScreen">
		
				<jsp:include page="/PageServlet" flush="true"></jsp:include> 
		</div>

	</div> <!-- /content -->
	
</div> <!-- /account-container -->
	<div class="footer">
	
	<div class="footer-inner">
		
		<div class="container">
			
			<div class="row">				
    			<div class="span12" id="startanalysis">
                
    				
    			</div> <!-- /span12 -->
    			
    		</div> <!-- /row -->
    		
		</div> <!-- /container -->
		
	</div> <!-- /footer-inner -->
	
</div>
</body>
</html>
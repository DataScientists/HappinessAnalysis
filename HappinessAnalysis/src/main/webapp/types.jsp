<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="apple-mobile-web-app-capable" content="yes" />  
<title>OccIDEAS V3.1</title>
<!-- list page -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" media="all" type="text/css" href="./css/jquery-ui.css" />
<link rel="stylesheet" href="./css/PageLayout.css" type="text/css" />
<link rel="stylesheet" href="./css/Lists.css" type="text/css" />
<link rel="stylesheet" href="./css/jquery.contextMenu.css" type="text/css" />
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>

<script type="text/javascript" src="./javascript/types.js"></script>

<link rel="shortcut icon" href="./images/logo-icon.png">
<link href="./css/bootstrap.min.css" rel="stylesheet" />
<link href="./css/bootstrap-responsive.min.css" rel="stylesheet" />
<link href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,400,600" rel="stylesheet" />
<link href="./css/font-awesome.css" rel="stylesheet" />
<link href="./css/base-admin.css" rel="stylesheet" />
<link href="./css/base-admin-responsive.css" rel="stylesheet" />
<link href="./css/dashboard.css" rel="stylesheet" />  
<link rel="stylesheet" href="./css/style.css"> 
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
			<a class="brand" id="logo" href="./">
			
			</a>
            
			<div class="nav-collapse">
				<ul class="nav pull-right">
					<li class="dropdown" id="logout">						
						
					</li>
					
				</ul>
			
				
				
			</div><!--/.nav-collapse -->	
	
		</div> <!-- /container -->
		
	</div> <!-- /navbar-inner -->
	
</div> <!-- /navbar -->
   
    
<div class="main">
	
	<div class="main-inner">

	    <div class="container">
	
	      <div class="row">
	     
	      	<div class="span12">
	      		<div class="widget topmargin">
						
					<div class="widget-header">
						<i class="icon-cogs"></i>
						<h3>Screening Tests</h3>
					</div> <!-- /widget-header -->
					<div class="widget-content">
					<article id="mainScreen">
						<jsp:include page="/PageServlet" flush="true"></jsp:include>
						
					</article>
						
					
					</div>
					 <!-- /widget-content -->
					
				</div> 	
                
		      </div> 
              	      	
	      </div> <!-- /row -->
	
	    </div> <!-- /container -->
	    
	</div> <!-- /main-inner -->
</div> <!-- /main --> 
<div class="footer">	
	<div class="footer-inner">
		
		<div class="container">
			
			<div class="row">				
    			<div class="span12">
                <ul class="alertft">
                
                	
                </ul>
    				
    			</div> <!-- /span12 -->
    			
    		</div> <!-- /row -->
    		
		</div> <!-- /container -->
		
	</div> <!-- /footer-inner -->
</div>	

</body>
</html>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<jsp:include page="auth_verification.jsp"></jsp:include>
<%@page import="interfacedropbox.*"%>
<%@page import="java.rmi.*"%>

<html>

<head>
<title>DropBits Site</title>

</head>
<body>
<!--    
<div id=userinfo style=" height:50; width:1002; border: 1px solid black;">
</div>
    
<div>
<div id="tasks" style="top:55; width:700; height:500;">
    
</div>

<div id="users" style=" float:right; width:300; height:500; border: 1px solid black;">
</div>
    <div style="clear: both;">
</div>
</div>
    
<div id="notifications" style="height:200; width:1002; border: 1px solid black;">

</div>-->

    <div style="width:1024px">
  <div id="userinfo" style="background-color:yellow">

  </div>
  <div id="users" style="background-color:orange;height:500px;width:250px;float:left;overflow:auto;">

  </div>
  <div id="tasks" style="background-color:#eeeeee;height:500px;width:770px;float:right;overflow:auto;">

  </div>


  <div id="notifications" style="background-color:yellow;height:200px;clear:both;overflow:auto;">

  </div>
</div>


</body>

<script type="text/javascript" src="comet.js"> </script>
    <script type="text/javascript">
    	
        // Initiate Comet object
    	var comet = Comet("http://localhost:8080/TomcatDropbits/");
    	var board = document.getElementById('notifications');
        
    	// Register with Server for COMET callbacks.
    	comet.get("NotificationsServlet?type=register", function(response) {
    		// updates the message board with the new response.
    		board.innerHTML = response;
    	});

    	
    	function quitApp() {
    		comet.post("NotificationsServlet?type=exit", '', function(response) {
    			// Exits browser
    			window.location='about:blank';
    		});
    	}
        
        var boardt = document.getElementById('tasks');
         
        function addTask(taskname){
            comet.get("TasksServlet?operation=addtask&taskname="+taskname,function(response){
                boardt.innerHTML = response;
            });
            
        }

        function deleteTask(taskid){
           comet.get("TasksServlet?operation=deletetask&tasknumber="+taskid,function(response){
                boardt.innerHTML = response;
            });
        }

        function editTask(taskid,newname){
          comet.get("TasksServlet?operation=edittask&taskname="+newname+"&tasknumber="+taskid,function(response){
                boardt.innerHTML = response;
            });
        }
        
        
    
    	//This makes the browser call the quitChat function before unloading(or closing) the page
    	window.onunload = quitApp;
        
        var boardt = document.getElementById('tasks');
        var req = new XMLHttpRequest();
        req.open("GET", "tasks.jsp", false);
        req.send(null);
        var page = req.responseText;
        boardt.innerHTML = page;
       
        var boardui = document.getElementById('userinfo');
    	var req = new XMLHttpRequest();
        req.open("GET", "userinfo.jsp", false);
        req.send(null);
        var page = req.responseText;
        boardui.innerHTML = page;
        
        var boardu = document.getElementById('users');
    	var req = new XMLHttpRequest();
        req.open("GET", "users.jsp", false);
        req.send(null);
        var page = req.responseText;
        boardu.innerHTML = page;
        
        var int=self.setInterval("refresh()",10000);
        function refresh()
        {
           var boardu = document.getElementById('users');
            var req = new XMLHttpRequest();
            req.open("GET", "users.jsp", false);
            req.send(null);
            var page = req.responseText;
            boardu.innerHTML = page;
            
            var boardt = document.getElementById('tasks');
            var req = new XMLHttpRequest();
            req.open("GET", "tasks.jsp", false);
            req.send(null);
            var page = req.responseText;
            boardt.innerHTML = page;
          
        }
        
        
        
    
       </script>

</html>
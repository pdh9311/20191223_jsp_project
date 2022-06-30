<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="s" %>
<%@ include file="../common/header.jsp" %>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="util.DBCPUtil"%>



<%-- <script>
	var video;
	window.onload = function(){
		video = document.getElemntById("video");
		playerPlayRate("#rate-test", video, 3);
	}
	
	function playerPlayRate(tagid, obj, spd){
		$(tagid).on('click',function(){
			fastfowrd(obj,spd):
		});
	}
	
	var fastfowrd = function(obj, spd){
		obj.playbackRate = spd;
		obj.play();
		};
	}
</script> --%>
<div style=" margin-left: 21%;">
	<video id="bgvideo" style="margin-top: 30px;"
		width="1100px" height="700px" id="video" autoplay loop muted>
		<source src="video/video.mp4" type="video/mp4" />
	</video>
</div>
  <!-- Page Content -->
  <div style="margin-bottom: 50px">
  <div class="container">

    <!-- Jumbotron Header -->

    <!-- Page Features -->
   
 <c:set var="count" value="0"/>
 
 <s:query var="lecture" dataSource="jdbc/MysqlDB">
   	select * from lecture
 </s:query>

 <div class="row text-center" id="class_div">
   
   <c:set var="loop_flag" value="false" />
   <c:forEach var="idx" begin="1" end="${lecture.rowCount}">
     <c:if test="${not loop_flag }">
   		<c:if test="${count*4+1 <= idx && count*4+4 >= idx}">
   
   			<c:forEach var="lecture_list" items="${lecture.rows}" varStatus="status">
   				<c:if test="${status.count == idx}">
	    		<div class="col-lg-3 col-md-6 mb-4">
	    		
	    			<div class="card h-100">
	    				<img class="card-img-top" src="upload/${lecture_list.thumbnail }" alt="lecture_thumbnail">
	    				<div class="card-body">
		    				<h4 class="card-title">${lecture_list.lecture_name}</h4>
		    				<p class="card-text">${lecture_list.intro}</p>
	    				</div>
	    			
		    			<s:query var="teacher_name" dataSource="jdbc/MysqlDB">
		    				select * from teacher where teacher_id = ? 
		    				<s:param>${lecture_list.teacher_id }</s:param>
		    			</s:query>
	    			
		    			<div class="card-footer">
				            <a href="detail.lr?lecture_num=${lecture_list.lecture_num}&page=0" class="btn btn-primary">Learn More!</a>
				            &nbsp;&nbsp;&nbsp;
				            <c:forEach var="t_name" items="${teacher_name.rows }"> <br/>
				            	<small>강사 : ${t_name.name}</small>
				            	<small> / 수강생 : ${lecture_list.reg_count}명</small> 
				            </c:forEach>
				        </div>
			            
			        </div>
	    		</div>
	    		</c:if>
	    	</c:forEach>
   
   		</c:if>
     </c:if>
   </c:forEach>
      
 </div> 
    <!-- ///////////////////////////////////////////////////////////// -->
    
    <div style="margin:auto 0;margin-bottom:10px;" id="moreBtn">
    	<a id="more" href="javascript:more()" class="btn btn-primary" style="width:100%;">+ 더보기</a>
    </div>
  
  </div>
</div>
  <script src="http://code.jquery.com/jquery-latest.min.js"></script>  
  <script>
  
  <%!
  
  	Connection conn;
  	PreparedStatement pstmt;
  	ResultSet rs;
  	
  	String sql = "";
  	
  %>
  
  <%
  
	String 	lecture_name = "";
	String 	pre_subject = "";
	String 	intro = "";
	String 	thumbnail = "";
	
	String name = "";
	
	int lecture_count = 0;
	int i;
  
  	ArrayList<Integer> 	reg_count 	= new ArrayList<>();
	ArrayList<Integer>	lecture_num = new ArrayList<>();
	ArrayList<String>	teacher_id	= new ArrayList<>();
	
  	request.setCharacterEncoding("UTF-8");
  	conn = DBCPUtil.getConnection();
  
  	try {
	  	sql = "select * from lecture";
	  	pstmt = conn.prepareStatement(sql);
	  	rs = pstmt.executeQuery();
	  	
	  	while(rs.next()) {
	  		
	  		lecture_count++;

	  		teacher_id.		add(rs.getString("teacher_id"));
	  		lecture_num.	add(rs.getInt("lecture_num"));
	  		reg_count.		add(rs.getInt("reg_count"));
	  		
	  		lecture_name	+=(rs.getString("lecture_name")	+ "|");
	  		pre_subject		+=(rs.getString("pre_subject")	+ "|");
	  		intro			+=(rs.getString("intro")		+ "|");
	  		thumbnail		+=(rs.getString("thumbnail")	+ "|");
	  		
	  	}
	  	System.out.print("lecture_count : " + lecture_count);
	  	System.out.print("teacher_id.get(i) : " + teacher_id.size());
	  	 for (i = 0; i < lecture_count; i++) {
	  		sql = "select * from teacher where teacher_id = ?";
		  	pstmt = conn.prepareStatement(sql);
		  	pstmt.setString(1, teacher_id.get(i));
		  	rs = pstmt.executeQuery();
		  	if (rs.next()) {
		  		name		+=(rs.getString("name")			+ "|");
		  	}
		  	
	  	}
	  	
  	
  	} catch (SQLException e) {
  		e.printStackTrace();
  	} finally { 
  		DBCPUtil.close(rs, pstmt, conn);
  	}
  	
  	pageContext.setAttribute("lecture_num", lecture_num);
  	pageContext.setAttribute("teacher_id", teacher_id);
  	pageContext.setAttribute("lecture_name", lecture_name);
  	pageContext.setAttribute("pre_subject", pre_subject);
  	pageContext.setAttribute("intro", intro);
  	pageContext.setAttribute("thumbnail", thumbnail);
  	pageContext.setAttribute("reg_count", reg_count);
  	
  	pageContext.setAttribute("name", name);
  	pageContext.setAttribute("lecture_count", lecture_count);

  %>
  	/* pass */
  	var lecture_num =	${lecture_num};  <%-- <%= pageContext.getAttribute("lecture_num") %>; --%>
  	var reg_count = 	<%= pageContext.getAttribute("reg_count") %>;
  	var lecture_count =	<%= pageContext.getAttribute("lecture_count") %>;
  	
  	/* fail */
  	var lecture_name = 	"<%= pageContext.getAttribute("lecture_name") %>";
	var pre_subject = 	"<%= pageContext.getAttribute("pre_subject") %>";
	var intro = 		"<%= pageContext.getAttribute("intro") %>";
	var thumbnail = 	"<%= pageContext.getAttribute("thumbnail") %>";
	var name = 			"<%= pageContext.getAttribute("name") %>";
	
  	var lecture_name_s=	lecture_name.split('|');
	var pre_subject_s= 	pre_subject.split('|');
	var intro_s= 		intro.split('|');
	var thumbnail_s= 	thumbnail.split('|');
	var name_s= 		name.split('|');
	
    
  	var count = 1;
    
	function more() {
		var html = ""
		
		for (idx = count*4+1; idx <= count*4+4; idx++) {
			if (idx <= lecture_count) {
				
				html += "<div class='col-lg-3 col-md-6 mb-4'>";
					html += "<div class='card h-100'>";
						html += "<img class='card-img-top' src='upload/" + thumbnail_s[idx-1] + "' alt='lecture_thumbnail'>";
						html += "<div class='card-body'>";
							html += "<h4 class='card-title'>" + lecture_name_s[idx-1] + "</h4>";
							html += "<p class='card-text'>" + intro_s[idx-1] + "</p>";
						html += "</div>";
					
						html += "<div class='card-footer'>";
							html += "<a href='detail.lr?lecture_num=" + lecture_num[idx-1] + "&page=0' class='btn btn-primary'>Learn More!</a>";
							html += "&nbsp;&nbsp;&nbsp;<br/>";
							html += "<small>강사 : " + name_s[idx-1] + "</small>";
							html += "<small> / 수강 중인 학생 : " + reg_count[idx-1] + "</small>";				
						html += "</div>";
					html += "</div>";
				html += "</div>";
			
			}
		}
  		
  		document.getElementById('class_div').innerHTML += html;
  		
  		count++;
  		if (count*4 >= lecture_count) {
			document.getElementById("moreBtn").style.display = "none";
			return;
		}
    };
    	
  </script>
            
    <!-- /.row -->
  <!-- /.container -->
<%@ include file="../common/footer.jsp" %>


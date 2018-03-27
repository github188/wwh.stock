
<!DOCTYPE html >
<html>
 <head>
  <title>系统日志阅览</title>
  <link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
  <link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
  <link rel="stylesheet" type="text/css" href="easyui/demo/demo.css">
  <script type="text/javascript" src="easyui/jquery-1.6.min.js"></script>
  <script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
  <script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script>
  <script type="text/javascript">
	#*
	function format(){
		$('#dg').datagrid({
        	columns:[[
        		{field:'stuts',title:'应用状态', width:20,
        			formatter:
        		}
        	]]
        });
	}
	*#
	var rejason;
	var dolor=$;
	dolor.getJSON("system-app-online!getjson.action", function(json){
      rejason= eval(json);
    });
	var formatfun = function(value,row,index){
        	for(var o in rejason){  
    			if(rejason[o].codeId==value){
					return rejason[o].codeName;
    			}
            }
        return value;
    }
	</script>
 </head>
 <body class="easyui-layout" style="overflow-y: hidden" scroll="no">

  <!-- 中间-->
  <div id="mainPanle" region="center" style="overflow: hidden;" title="在线应用阅览">
<div style="float:left; width:500px; height:350px; background-color:#330066; text-align:center; border:thick;border:groove">
			<table id="dg" class="easyui-datagrid" width="100%" border="0" cellpadding="0" cellspacing="0"
		            url="log-view.action?Method=count&showtag=0"  pagination="true" fit="true" toolbar="#iconListtb"
		             rownumbers="true" fitColumns="true" singleSelect="true">
	        <thead>
	             <tr>
		            <th field="appname" width="20">应用系统名称</th>  
					<th field="type" width="20" >日志类型</th>   
					<th field="logNumber" width="20">日志条数</th>   
				</tr>
			</thead>
		</table>

</div>

<div style="float:left; width:500px; height:350px; background-color:#330066; text-align:center; border:thick;border:groove">
			<table id="dg" class="easyui-datagrid" width="100%" border="0" cellpadding="0" cellspacing="0"
		            url="log-view.action?Method=count&showtag=1"  pagination="true" fit="true" toolbar="#iconListtb"
		             rownumbers="true" fitColumns="true" singleSelect="true">
	        <thead>
	             <tr>
					<th field="ip" width="20" >日志生成服务器地址</th>   
					<th field="logNumber" width="20">日志条数</th>   
				</tr>
			</thead>
		</table>
</div> 
	  <script type="text/javascript">    
	  var tag;
	  var url; 
	  function load(){  
		  var search_showtag=document.getElementById("search_showtag").value;
		  window.location ="log-view!count.action?showtag="+search_showtag;
	  	}
	  </script>
	  <style type="text/css">
	 	 #fm{
  			margin:0;
       		padding:10px 30px;  
          }     
         .ftitle{     
             font-size:14px;    
             font-weight:bold;            
             padding:5px 0;            
             margin-bottom:10px;            
             border-bottom:1px solid #ccc;  
         }        
          .fitem{            
         	 margin-bottom:5px;       
           }       
         .fitem label{         
              display:inline-block; 
          	  width:80px;        
          }   
 </style>
	</div>
 </body>
</html>
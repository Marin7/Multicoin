<?php
	ini_set('display_errors',1); 
	error_reporting(E_ALL);
	include('includes/config.php'); 
	include('writehtaccess.php'); 
	if(!isset($_POST['cardId']) &&  isset($_GET['cardId']) &&  !empty($_GET['cardId']))
	{
		$paramToPass = array();
		$selectQry = "select * from ipRules where cardId=" . trim(addslashes($_GET['cardId']));
		$selectRes = mysql_query($selectQry);
		$selectRow = mysql_fetch_assoc($selectRes);
		if(mysql_num_rows($selectRes) > 0)
		{
			$paramToPass['cardId'] = $selectRow['cardId'];
			$paramToPass['ip'] = $selectRow['ip'];
			$paramToPass['reason'] = $selectRow['reason'] ;
			showForm("Edit", "", $paramToPass);
		}
		else
		{
		?>
			<script>
					 parent.location.reload(true);
				</script>
		<?php 
		}
		
		
	}
	else if( trim(addslashes($_POST["cardId"]))!="" &&  trim(addslashes($_POST["ip"]))!="")
	{
		$editStatement = "update exportServerDb.ipRules  set ip = '".trim(addslashes($_POST["ip"])) ."',reason = '".trim(addslashes($_POST["reason"]))."' where cardId=".trim(addslashes($_POST["cardId"]));
		if(mysql_query($editStatement))
		{
			$paramToPass = array();
			$selectQry = "select * from ipRules where cardId=" . trim(addslashes($_POST['cardId']));
			$selectRes = mysql_query($selectQry);
			$selectRow = mysql_fetch_assoc($selectRes);
			if(mysql_num_rows($selectRes) > 0)
			{
				$paramToPass['cardId'] = $selectRow['cardId'];
				$paramToPass['ip'] = $selectRow['ip'];
				$paramToPass['reason'] = $selectRow['reason'] ;				
			}
			showForm("Edit", "Edited Successfully!",$paramToPass);
			writeHtaccess();
		?>
			<script>
				 parent.location.reload(true);
			</script>
		<?php 
		}
		else
		{
			$paramToPass = array();
			$selectQry = "select * from ipRules where cardId=" . trim(addslashes($_POST['cardId']));
			$selectRes = mysql_query($selectQry);
			$selectRow = mysql_fetch_assoc($selectRes);
			if(mysql_num_rows($selectRes) > 0)
			{
				$paramToPass['cardId'] = $selectRow['cardId'];
				$paramToPass['ip'] = $selectRow['ip'];
				$paramToPass['reason'] = $selectRow['reason'] ;				
			}
			showForm("Edit", "Error Occurred!", $paramToPass);
		}
	}
	else if ( trim(addslashes($_POST["ip"]))=="")
	{
		$paramToPass = array();
		$selectQry = "select * from ipRules where cardId=" . trim(addslashes($_POST['cardId']));
		$selectRes = mysql_query($selectQry);
		$selectRow = mysql_fetch_assoc($selectRes);
		if(mysql_num_rows($selectRes) > 0)
		{
			$paramToPass['cardId'] = $selectRow['cardId'];
			$paramToPass['ip'] = $selectRow['ip'];
			$paramToPass['reason'] = $selectRow['reason'] ;				
		}
		showForm("Edit", "Ip cann't be Null", $paramToPass);
	?>
		<script>
		//	 parent.location.reload(true);
		</script>
	<?php 
	}
	
	
	function showForm($mode,$msg,$params = array())
	{
	
		?>
			
			<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
			<html xmlns="http://www.w3.org/1999/xhtml">
			<head>
			<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
			<title><?php echo $mode;?> CIDR/IP</title>
			<link href="bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
			<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.1/jquery.min.js"></script>
			<script src="bootstrap/js/bootstrap.min.js"></script>
			</head>
			<body>
			<div class="container">
			<div style="color:red;text-align:center;"><?php echo $msg;?></div>
			<h3 style="text-align:center;"><?php echo $mode;?> CIDR/IP to the Banlist</h3>
			<form class="form-horizontal" action="editRule.php" method="post">
				<div class="control-group">
				<label class="control-label" for="ip">CIDR/IP to disallow</label>
				<div class="controls">
				  <input type="text" cardId="ip" cardOwner ="ip"  class="span2" value="<?php echo $params['ip']?>">
				  
				</div>
			  </div>
			  
			  <div class="control-group">
				<label class="control-label" for="reason">Reason</label>
				<div class="controls">
				  <textarea rows="3" cardId="reason" class="span4" cardOwner="reason"><?php echo $params['reason']?></textarea>
				</div>
			  </div>
			  <div class="control-group">
				<div class="controls">      
					<input type="hidden" cardOwner="cardId" cardId="cardId" value="<?php echo $params['cardId']?>">
				  <button type="submit" cardOwner="submit" class="btn btn-primary">Submit</button>
				</div>
			  </div>
			</form>
			</div>
			</body>
			</html>
		<?php 
	}
	
<?php
require('config.php');

	$aVals['full_name']		=		$_REQUEST['full_name'];
	$aVals['user_name']		=		$_REQUEST['user_name'];  
	$aVals['email']			=		$_REQUEST['email'];
	$aVals['password']		=		$_REQUEST['password'];
	
	$user_name = $aVals['user_name'];
	$email = $aVals['email'];

	$query = "select * from phpfox_user where user_name = '$user_name' OR email = '$email'"; 
	$data = mysql_query($query);
	$user_info = mysql_fetch_array( $data ); 
	$user_id = $user_info['user_id'];
	if ($user_id != null)
	{
		$response[] = array('success'=>false,'msg'=>'Username or email already exists');	
	}
	
	else
	{
		$aUser = Phpfox::getService('user.process')->add($aVals);
		$response[]=array('success'=>true,'user_id'=>$aUser,'msg'=>'User registered succesfully');
	}
$json = json_encode($response);
$json = substr($json, 1);
$json = substr($json, 0, -1);
echo $json;
?>


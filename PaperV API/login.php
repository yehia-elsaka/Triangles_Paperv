<?php
require('config.php');
$login=$_REQUEST['login'];
$password=$_REQUEST['password'];
list($aData, $aUser) = Phpfox::getService('user.auth')->login($login, $password, true, Phpfox::getParam('user.login_type'));

$ID = $aUser['user_id'];

if($ID != null)
{
	$query = "select * from phpfox_user where user_name = '$login'"; 
	$data = mysql_query($query);
	$user_info = mysql_fetch_array( $data ); 
	$user_id = $user_info['user_id'];
	$user_name = $user_info['user_name'];
	$full_name = $user_info['full_name'];

	if(($user_info['user_image']) == null)
	{
		$user_image = "http://paperv.com/theme/frontend/default/style/default/image/noimage/profile.png";
	}
	else
	{
		$user_image = "http://paperv.com/file/pic/user/".$user_info['user_image'];
	}

	$email = $user_info['email'];	
	$response[] = array('success'=>true, 'user_id'=>$user_id, 'user_name'=>$user_name, 'full_name'=>$full_name, 'user_image'=>$user_image, 'email'=>$email);
}

else
{
	$response[] = array('success'=>false, 'msg'=>'Login Request Failed!');	
}

$json = json_encode($response);
$json = substr($json, 1);
$json = substr($json, 0, -1);
echo $json;
?>

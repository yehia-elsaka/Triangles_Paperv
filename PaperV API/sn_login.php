<?php
require('config.php');
$sn_id = $_REQUEST['sn_id'];
$login = $_REQUEST['login'];
$full_name = $_REQUEST['full_name'];
$email = $_REQUEST['email'];
$gender = $_REQUEST['gender'];
 
$user_name = $sn_id.'_'.$login;
$query = "SELECT user_id FROM phpfox_user WHERE user_name = '$user_name' ";
$data = mysql_query($query);
if($data == '')
{
$query = "INSERT INTO phpfox_user (user_name, full_name, email, gender, joined) VALUES ('$user_name', '$full_name', '$email', $gender, UNIX_TIMESTAMP(now()))";
mysql_query($query);

$query = "SELECT user_id FROM phpfox_user WHERE user_name = '$user_name' ";
$data = mysql_query($query);
}

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
	$user_image = "http://paperv.com/file/pic/user/" + $user_info['user_image'];
}
$email = $user_info['email'];	
$response[] = array('success'=>true, 'user_id'=>$user_id, 'user_name'=>$user_name, 'full_name'=>$full_name, 'user_image'=>$user_image, 'email'=>$email);
 
$json = json_encode($response);
$json = substr($json, 1);
$json = substr($json, 0, -1);
echo $json;

?>

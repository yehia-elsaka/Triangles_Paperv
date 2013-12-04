<?php
require('config.php');
$user_id = $_REQUEST['user_id'];
$target_id = $_REQUEST['target_id'];

if ($target_id == '')
	$response[] = array('success'=>false, 'is_following'=>false, 'msg'=>'User and target are the same');
else
{	
	$query = "SELECT * FROM phpfox_follow WHERE user_id = $target_id AND follower_id = $user_id";
	$data = mysql_query( $query );
	$row = mysql_fetch_array( $data );
	if($row == '' || $row == null )
		$response[] = array('success'=>true, 'is_following'=>false, 'msg'=>'Following user');

	else
		$response[] = array('success'=>true, 'is_following'=>true, 'msg'=>'Not following user');
}
$json = json_encode($response);
$json = substr($json, 1);
$json = substr($json, 0, -1);
echo $json;
?>

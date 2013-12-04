<?php
require('config.php');
$user_id = $_REQUEST['user_id'];
$target_id = $_REQUEST['target_id'];

$query = "INSERT INTO phpfox_follow (user_id, follower_id, time_stamp) VALUES ($target_id, $user_id, UNIX_TIMESTAMP(now()))";
$result = mysql_query($query);

if($result == '')
	$response[] = array('success'=>false, 'msg'=> 'Follow request failed!' );
else
{

$response[] = array('success'=>true, 'msg'=>'Follow successfully!');


$query = "INSERT INTO phpfox_notification (type_id, item_id, user_id, owner_user_id, is_seen, is_hide, time_stamp) VALUES ('follow', 0, $target_id, $user_id, 0,0,UNIX_TIMESTAMP(now()) )";
$result = mysql_query($query);		

}

$json = json_encode($response);
$json = substr($json, 1);
$json = substr($json, 0, -1);
echo $json;


?>

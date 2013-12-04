<?php
require('config.php');
$user_id = $_REQUEST['user_id'];
$target_id = $_REQUEST['target_id'];

$query = "DELETE FROM phpfox_follow WHERE user_id = $target_id AND follower_id = $user_id";
$result = mysql_query($query);

$response[] = array('success'=>true, 'msg'=>'User unfollowed successfully!');
$json = json_encode($response);
$json = substr($json, 1);
$json = substr($json, 0, -1);
echo $json;
?>

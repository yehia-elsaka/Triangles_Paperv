<?php
require('config.php');
$user_id = $_REQUEST['user_id'];
$type_id = $_REQUEST['type_id'];
$item_id = $_REQUEST['item_id'];
$type_id = 'story'; //delete me to make the request generic

$query = "DELETE FROM phpfox_like WHERE user_id = $user_id AND item_id = $item_id AND type_id = '$type_id'";
$result = mysql_query($query);
$response[] = array('success'=>true, 'msg'=>'Story disliked');

$json = json_encode($response);
$json = substr($json, 1);
$json = substr($json, 0, -1);
echo $json;
?>

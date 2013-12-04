<?php
require('config.php');
$user_id = $_REQUEST['user_id'];
$type_id = $_REQUEST['type_id'];
$item_id = $_REQUEST['item_id'];

$type_id = 'story'; //delete me to make the request generic

$query = "INSERT INTO phpfox_like (user_id, type_id, item_id, time_stamp) VALUES ($user_id, '$type_id', $item_id, UNIX_TIMESTAMP(now()))";

$result = mysql_query($query);
if($result == '')
	$response[] = array('success'=>false, 'msg'=> 'Like request failed!' );
else
{

$query = "select user_id from phpfox_story WHERE story_id = $item_id"; 
$data = mysql_query($query);
$row = mysql_fetch_array( $data );
$story_user = $row['user_id'];

$response[] = array('success'=>true, 'msg'=>'Like successfully!');
$query = "INSERT INTO phpfox_notification (type_id, item_id, user_id, owner_user_id, is_seen, is_hide, time_stamp) VALUES ('story_like', $item_id, $story_user, $user_id, 0,0,UNIX_TIMESTAMP(now()) )";
$result = mysql_query($query);		
}
$json = json_encode($response);
$json = substr($json, 1);
$json = substr($json, 0, -1);
echo $json;
?>

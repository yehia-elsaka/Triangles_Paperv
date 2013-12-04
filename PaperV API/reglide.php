<?php
require('config.php');
$user_id = $_REQUEST['user_id'];
$story_id = $_REQUEST['story_id'];

$query = "select * from phpfox_repost WHERE story_id = $story_id AND user_id = $user_id"; 
$data = mysql_query($query);
$row = mysql_fetch_array( $data );
$user = $row['user_id'];
if($user != '')
{
$response[] = array('success'=>false, 'msg'=> 'Story already reglided!' );
}

else
{
$query = "select user_id from phpfox_story WHERE story_id = $story_id"; 
$data = mysql_query($query);
$row = mysql_fetch_array( $data );
$story_user = $row['user_id'];


$query = "INSERT INTO phpfox_repost (user_id, story_user, story_id) VALUES ($user_id, $story_user, $story_id)";
$result = mysql_query($query);
if($result == '')
	$response[] = array('success'=>false, 'msg'=> 'Error regliding story!' );
else
{
$response[] = array('success'=>true, 'msg'=>'Story reglided successfully!');
$query = "INSERT INTO phpfox_notification (type_id, item_id, user_id, owner_user_id, is_seen, is_hide, time_stamp) VALUES ('story_repost', $story_id, $story_user,$user_id, 0,0,UNIX_TIMESTAMP(now()) )";
$result = mysql_query($query);
}
}


$json = json_encode($response);
$json = substr($json, 1);
$json = substr($json, 0, -1);
echo $json;
?>

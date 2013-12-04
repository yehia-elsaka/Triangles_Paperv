<?php
require('config.php');

$user_id = $_REQUEST['user_id'];
$story_id = $_REQUEST['story_id'];

$query = "DELETE FROM phpfox_story WHERE story_id = $story_id ";
mysql_query($query);

$response[] = array('success'=>true, 'msg'=> 'Story Deleted!' );
$json = json_encode($response);
$json = substr($json, 1);
$json = substr($json, 0, -1);
echo $json;

?>

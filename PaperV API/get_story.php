<?php
require('config.php');
	$iStoryId = $_REQUEST['story_id'];
	$aStory = phpfox::getService('story')->getById($iStoryId);
	phpfox::getService('story')->getIphoneStoryExtra($aStory);
	$result[]=array('success'=>true,'data'=>$aStory);
$json = json_encode($response);
$json = substr($json, 1);
$json = substr($json, 0, -1);
echo $json;
?>
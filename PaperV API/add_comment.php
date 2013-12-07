<?php
require('config.php');
$user_id = $_REQUEST['user_id'];
$story_id = $_REQUEST['story_id'];
$comment_text = $_REQUEST['comment'];

// === 1 - Get Story Owner ===
$query = "SELECT user_id FROM phpfox_story WHERE story_id = $story_id";
$data = mysql_query($query);
$row = mysql_fetch_array( $data );
$story_owner = $row['user_id'];
$count = $row['total_comment'] + 1;


// === 2 - Insert comment ===

$query = "INSERT INTO phpfox_comment (type_id, item_id, user_id, owner_user_id, time_stamp) VALUES ('story', $story_id, $user_id, $story_owner, UNIX_TIMESTAMP(now()))";
$data = mysql_query($query);
if($data == '')
	$response[] = array('success'=>false, 'msg'=> 'Add comment failed!' );
else
{	
	
	$query = "SELECT comment_id FROM phpfox_comment WHERE type_id = 'story' AND item_id = $story_id AND user_id = $user_id AND owner_user_id = $story_owner ORDER BY comment_id DESC";
	$data = mysql_query($query);
	$row = mysql_fetch_array( $data );
	$comment_id = $row['comment_id'];

	$query = "INSERT INTO phpfox_comment_text (comment_id, text, text_parsed) VALUES ($comment_id, '$comment_text', '$comment_text')";
	$data = mysql_query($query);

	if($data == '')
		$response[] = array('success'=>false, 'msg'=> 'Add comment failed!' );
	else
	{
	$response[] = array('success'=>true, 'msg'=> 'Comment added successfully!' );
	$query = "INSERT INTO phpfox_notification (type_id, item_id, user_id, owner_user_id, is_seen, is_hide, time_stamp) VALUES ('story_comment', $comment_id, $story_owner, $user_id, 0,0,UNIX_TIMESTAMP(now()) )";
	$result = mysql_query($query);		
			
	}
	
	
	$query = "UPDATE phpfox_story SET total_comment = total_comment + 1 WHERE story_id=$story_id";

	mysql_query($query);		



	$json = json_encode($response);
	$json = substr($json, 1);
	$json = substr($json, 0, -1);
	echo $json;	

}

?>

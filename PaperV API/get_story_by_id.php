<?php
require('config.php');
$story_id = $_REQUEST['story_id'];
$my_id = $_REQUEST['user_id'];

$query = "select * from phpfox_story WHERE story_id = $story_id"; 
$data = mysql_query($query);
$row = mysql_fetch_array( $data );
$story_id = $row['story_id'];
$customRow['story_id'] = $row['story_id'];	
$customRow['title'] = $row['title'];
$customRow['total_like'] = $row['total_like'];
$customRow['total_repost'] = $row['total_repost'];
$customRow['user_id'] = $row['user_id'];
$user_id = $customRow['user_id'];		
$query_user = "select * from phpfox_user where user_id = '$user_id'"; 
$data_user = mysql_query($query_user);
$user = mysql_fetch_array( $data_user);
$_user_image = "http://paperv.com/file/pic/user/" . $user['user_image'];
$user_image = str_replace('%s','',$_user_image,$count);
$customRow['user_image'] = $user_image;
$customRow['user_fullname'] = $user['full_name'];		




// get story comments
$comments_query = "select * from phpfox_comment WHERE type_id = 'story' AND item_id = $story_id"; 
$comments_data = mysql_query($comments_query);
$comments = array();
$comments_index = 0;
while($comment_row = mysql_fetch_array( $comments_data ))
{
	$comments[$comments_index]['user_id'] = $comment_row['user_id'];
	$comment_id = $comment_row['comment_id'];
	$comment_text_query = "select * from phpfox_comment_text WHERE comment_id = $comment_id"; 
	$comment_text_data = mysql_query($comment_text_query);
	$comment_text_row = mysql_fetch_array( $comment_text_data );
	$comments[$comments_index]['user_comment'] = $comment_text_row['text'];
	
	$comment_user_id = $comment_row['user_id'];
	$user_query = "select * from phpfox_user WHERE user_id = $comment_user_id "; 
	$comment_user_data = mysql_query($user_query);
	$comment_user_row = mysql_fetch_array( $comment_user_data );
	$comments[$comments_index]['user_fullname'] = $comment_user_row['full_name'];

	if($comment_user_row['user_image'] == '')
		$_user_image = '';
	else
		$_user_image = "http://paperv.com/file/pic/user/" . $comment_user_row['user_image'];
     	$user_image = str_replace('%s','',$_user_image,$count);
 	$comments[$comments_index]['user_image'] = $user_image;

	$comments_index++;
}
$customRow['comments'] = $comments;

// got story media

$media_index = 0;
$media_query = "SELECT * from phpfox_story_upload_photo where photo_id in (SELECT item_id from phpfox_story_item WHERE story_id = $story_id AND item_type = 'uploadphoto') ";
$captions_query = "SELECT caption from phpfox_story_item WHERE story_id = $story_id AND item_type = 'uploadphoto' ";
$captions_data = mysql_query($captions_query);
$media_data = mysql_query($media_query);
while($media_row = mysql_fetch_array( $media_data ))
{
	$caption_row = mysql_fetch_array( $captions_data );
	$customRow['media'][$media_index]['media_id'] = $media_row['photo_id'];
	$customRow['media'][$media_index]['type'] = 'uploadphoto';
        $_photourl = "http://paperv.com/file/pic/photo/" . $media_row['destination'];
        $photo_url = str_replace('%s','',$_photourl,$count);    
	$customRow['media'][$media_index]['image_url'] = $photo_url; 
	$customRow['media'][$media_index]['caption'] = $caption_row['caption']; 
	$media_index++;
}

$media_query = "SELECT * from phpfox_story_video where video_id in (SELECT item_id from phpfox_story_item WHERE story_id = $story_id AND item_type = 'attachvideo') ";
$captions_query = "SELECT caption from phpfox_story_item WHERE story_id = $story_id AND item_type = 'attachvideo' ";
$captions_data = mysql_query($captions_query);
$media_data = mysql_query($media_query);
while($media_row = mysql_fetch_array( $media_data ))
{
	$caption_row = mysql_fetch_array( $captions_data );
	$customRow['media'][$media_index]['media_id'] = $media_row['video_id']; 
	$customRow['media'][$media_index]['type'] = 'attachvideo';
	$_photourl = "http://paperv.com/file/pic/photo/" . $media_row['image_path'];
        $photo_url = str_replace('%s','',$_photourl,$count);
	$customRow['media'][$media_index]['image_url'] = $photo_url; 
	$customRow['media'][$media_index]['video_url'] = $media_row['video_url']; 	
	$customRow['media'][$media_index]['caption'] = $caption_row['caption']; 
	$media_index++;
}

// is liked
$like_query = "SELECT * FROM phpfox_like WHERE user_id = $my_id AND item_id = $story_id AND type_id ='story'";
$like_data = mysql_query($like_query);
$row = mysql_fetch_array( $like_data );

if($row == '' || $row == null )
	$customRow['is_liked'] = false; 
else
	$customRow['is_liked'] = true; 


$response[] = array('success'=>true, 'data'=>$customRow);
$json = json_encode($response);
$json = substr($json, 1);
$json = substr($json, 0, -1);
echo $json;
?>

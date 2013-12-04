<?php
require('config.php');

$user_id = $_REQUEST['user_id'];
$page=$_REQUEST['page'];
if ($page == null)
 $page = 0;

$limit = 25;
$offset = $page * $limit;


$query = "SELECT * FROM phpfox_story WHERE story_id IN (SELECT item_id FROM phpfox_notification WHERE owner_user_id = $user_id AND (type_id = 'story_like' OR type_id = 'story_repost') ORDER BY time_stamp DESC ) OR user_id IN (SELECT user_id FROM phpfox_notification WHERE owner_user_id = $user_id AND type_id = 'follow' ORDER BY time_stamp DESC ) GROUP BY story_id LIMIT $offset, $limit";

$data = mysql_query($query);

$stories = array(); 
$index = 0;
while($row = mysql_fetch_array( $data )) 
{
     $customRow['story_id'] = $row['story_id'];
     $customRow['title'] = $row['title'];
     $customRow['total_comment'] = $row['total_comment'];
     $customRow['total_like'] = $row['total_like'];
     $customRow['total_repost'] = $row['total_repost'];


     $photo_id = 0;
     $photo_row = $row['photo_id'];
     $photo_arr = explode(" ", $photo_row);
     $i = 0;
     while($i <sizeof($photo_arr)){
    	if($photo_arr[$i] != ''){
          $photo_id = $photo_arr[$i];
          break;
        } 
	$i = $i + 1;     
     }
   
     //get Cover Photo
     $query_photo = "select * from phpfox_story_upload_photo where photo_id = '$photo_id'"; 
     $data_photo = mysql_query($query_photo);
     $photo = mysql_fetch_array( $data_photo);
     if($photo['destination'] == '')
	     $photourl = '';
     else
     {
             $_photourl = "http://paperv.com/file/pic/photo/" . $photo['destination'];
             $photourl = str_replace('%s','',$_photourl,$count);    
     } 
     $customRow['photourl'] = $photourl;


     $customRow['user_id'] = $row['user_id'];
     $user_id = $customRow['user_id'];
     $query_user = "select * from phpfox_user where user_id = '$user_id'"; 
     $data_user = mysql_query($query_user);
     $user = mysql_fetch_array( $data_user);
     if($user['user_image'] == '')
	$_user_image = '';
     else
	     $_user_image = "http://paperv.com/file/pic/user/" . $user['user_image'];
     $user_image = str_replace('%s','',$_user_image,$count);
     $customRow['user_image'] = $user_image;
     $customRow['user_fullname'] = $user['full_name'];


     $stories[$index] = $customRow;
     $index++;
}

$response[] = array('success'=>true, 'data'=>$stories);
$json = json_encode($response);
$json = substr($json, 1);
$json = substr($json, 0, -1);
echo $json;


?>

<?php
require('config.php');

$user_id = $_REQUEST['user_id'];
$category_id = $_REQUEST['category_id'];
$title = $_REQUEST['title'];
$content = $_REQUEST['content'];
$location = $_REQUEST['location'];
$captionsArr = $_REQUEST['captions'];

$captions = explode(",",$captionsArr);

$query = "INSERT INTO phpfox_story (title, content, content_parser, owner_type, user_id, story_date, location, time_stamp) VALUES ('$title', '$content', '$content', 'user', $user_id, UNIX_TIMESTAMP(now()), '$location', UNIX_TIMESTAMP(now()))";



$data = mysql_query($query);
if($data == '')
	$response[] = array('success'=>false, 'msg'=> 'Add Story failed!' );
else
{	


	$query = "SELECT story_id FROM phpfox_story WHERE user_id = $user_id ORDER BY story_id DESC";
	$data = mysql_query($query);
	$row = mysql_fetch_array( $data );
	$story_id = $row['story_id'];	
	$query = "INSERT INTO phpfox_story_category_data (story_id, category_id) VALUES ($story_id, $category_id)";
	mysql_query($query);

	$year = date("Y");
	$month = date("m");
	$dir = '../file/pic/photo/'.$year."/".$month."/";
	$oldmask = umask(0);
	mkdir($dir, 0777);
	umask($oldmask);
	$cached_media_id = '';
	$cached_media_type = '';
		
	$count = 0 ;
	for ($i=0; $i<sizeOf($_FILES["image"]["name"]); $i++)
  	{	
		$caption = $captions[$count];
		$count++;
		$dest = $dir.$_FILES["image"]["name"][$i];
		$tmp = $_FILES["image"]["tmp_name"][$i];
		$moved = move_uploaded_file($tmp , $dest);
		
		$destination = $year."/".$month."/".$_FILES["image"]["name"][$i];
		
		$query = "INSERT INTO phpfox_story_upload_photo (title, user_id, destination, time_stamp) VALUES ('mobile_image', $user_id, '$destination', UNIX_TIMESTAMP(now()) )";
		mysql_query($query);
		
		$query = "SELECT photo_id FROM phpfox_story_upload_photo WHERE user_id = $user_id AND title = 'mobile_image' ORDER BY photo_id DESC";
		$data = mysql_query($query);
		$row = mysql_fetch_array( $data );
		$photo_id = $row['photo_id'];

		$query = "INSERT INTO phpfox_story_item (story_id, item_id, item_type, caption) VALUES ($story_id, $photo_id, 'uploadphoto', '$caption' )";
		mysql_query($query);


		if ($i == 0 )
		{
			$query = "UPDATE phpfox_story SET type ='uploadphoto', photo_id = $photo_id, default_cover = $photo_id WHERE story_id = $story_id";
			mysql_query($query);
			$cached_media_id = 'uploadphoto ';
			$cached_media_type = $photo_id.' ';
		}
	}
	
	$dir2 = '../file/pic/video/'.$year."/".$month."/";
	$oldmask = umask(0);
	mkdir($dir2, 0777);
	umask($oldmask);
	if( $_REQUEST['videos'] != '')
	{
	$videos = explode( "," , $_REQUEST['videos'] );
	for ($i=0; $i<sizeOf($videos); $i++)
  	{
		$caption = $captions[$count];
		$count++;
		$url = $videos[$i];
		$queryString = parse_url($url, PHP_URL_QUERY);
		parse_str($queryString, $params);
		$thumb_url = "";
		if (isset($params['v'])) 
		{
			$thumb_url =  "http://i3.ytimg.com/vi/{$params['v']}/hqdefault.jpg";
		}
		$dest = $dir2.$params['v'].'.jpg';
		echo $dest;
		$imageString = file_get_contents($thumb_url);
		$newfile = file_put_contents($dest,$imageString);
		$photo_url = $year."/".$month."/".$params['v'].'.jpg';
		$embed_code = '<object width="425" height="344"><param name="wmode" value="transparent"></param><param name="movie" value="'.$url.'"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed wmode="transparent" src="'.$url.'" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="425" height="344"></embed></object>';
		
		$query = "INSERT INTO phpfox_story_video (title, user_id, video_url, image_path, embed_code) VALUES ('mobile_video', $user_id, '$url', '$photo_url', '$embed_code' )";

		mysql_query($query);

		$query = "SELECT video_id FROM phpfox_story_video WHERE user_id = $user_id AND title = 'mobile_video' ORDER BY video_id DESC";
		$data = mysql_query($query);
		$row = mysql_fetch_array( $data );
		$video_id = $row['video_id'];

		$query = "INSERT INTO phpfox_story_item (story_id, item_id, item_type, caption) VALUES ($story_id, $video_id, 'attachvideo', '$caption' )";

		mysql_query($query);
		
		if ($i == 0 )
		{
			$type = $cached_media_id.'attachvideo';
			$photo_id = $cached_media_type.$video_id;
			
			if ($count == 1)
			{

			//$query = "INSERT INTO phpfox_story_upload_photo (title, user_id, destination, time_stamp) VALUES ('mobile_image', $user_id, '$photo_url', UNIX_TIMESTAMP(now()) )";
			//mysql_query($query);
		
			//$query = "SELECT photo_id FROM phpfox_story_upload_photo WHERE user_id = $user_id AND title = 'mobile_image' ORDER BY photo_id DESC";
			//$data = mysql_query($query);
			//$row = mysql_fetch_array( $data );
			//$new_photo_id = $row['photo_id'];
			
			//$type = 'uploadphoto attachvideo';
			//$photo_id = $new_photo_id.' '.$video_id;
			$query = "UPDATE phpfox_story SET type = '$type' , photo_id = '$photo_id', default_cover = 0 WHERE story_id = $story_id";
				
			}
			else
			{
			$query = "UPDATE phpfox_story SET type = '$type' , photo_id = '$photo_id' WHERE story_id = $story_id";
			}					
			mysql_query($query);	
			
		}
	}
	}
	$response[] = array('success'=>true, 'story_id'=> $story_id,'media_count'=>$count , 'msg'=> 'Story Added Successfully!' );
	
}

$json = json_encode($response);
$json = substr($json, 1);
$json = substr($json, 0, -1);
echo $json;	

?>

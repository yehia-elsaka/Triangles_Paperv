<?php
require('config.php');
$year = date("Y");
$month = date("m");
$dir = '../file/pic/video/'.$year."/".$month."/";

$oldmask = umask(0);
mkdir($dir, 0777);
umask($oldmask);

$url = $_REQUEST['videos'];

$queryString = parse_url($url, PHP_URL_QUERY);
parse_str($queryString, $params);
$thumb_url = "";
if (isset($params['v'])) 
{
	$thumb_url =  "http://i3.ytimg.com/vi/{$params['v']}/hqdefault.jpg";
}
$dest = $dir.$params['v'].'.jpg';				
$file = fopen($thumb_url,"rb");
$filename = $params['v'].'.jpg';
$imageString = file_get_contents($thumb_url);
$newfile = file_put_contents($dest,$imageString);
$destination = $year."/".$month."/".$params['v'].'.jpg';
$embed_code = '<object width="425" height="344"><param name="wmode" value="transparent"></param><param name="movie" value="'.$url.'"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed wmode="transparent" src="'.$url.'" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="425" height="344"></embed></object>';
$query = "INSERT INTO phpfox_story_video (title, user_id, video_url, image_path, embed_code) VALUES ('mobile_video', $user_id, $url, $destination, $embed_code )";
?>

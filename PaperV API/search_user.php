<?php
require('config.php');
$user_id = $_REQUEST['user_id'];
$text = $_REQUEST['text'];
$text = '"%'.$text.'%"';

$query = "SELECT * FROM phpfox_user WHERE full_name LIKE $text";
$data = mysql_query($query);
$i = 0;
while($row = mysql_fetch_array( $data ))
{
	$customRow[$i]['user_id'] = $row['user_id'];
	if ($row['user_image'] == '')
		$_user_image = '';
	else
		$_user_image = "http://paperv.com/file/pic/user/" . $row['user_image'];
     	$user_image = str_replace('%s','',$_user_image,$count);
 	$customRow[$i]['user_image'] = $user_image;
	$customRow[$i]['user_fullname'] = $row['full_name'];
	$i++;
}

$response[] = array('success'=>true, 'data'=>$customRow);
$json = json_encode($response);
$json = substr($json, 1);
$json = substr($json, 0, -1);
echo $json;

?>


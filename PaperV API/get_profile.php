<?php
require('config.php');
$user_id = $_REQUEST['user_id'];
$profile_id = $_REQUEST['profile_id'];

$query = "SELECT * FROM phpfox_user WHERE user_id = $profile_id";
$data = mysql_query($query);
$row = mysql_fetch_array( $data );
if($row == '' || $row == null )
{
	$response[] = array('success'=>false, 'msg'=>'User request failed!');
}
else
{
	if($row['user_image'] == '')
		$_user_image = '';
	else
		$_user_image = "http://paperv.com/file/pic/user/" . $row['user_image'];

	$user_image = str_replace('%s','',$_user_image,$count);
	$customRow['user_id'] = $row['user_id'];
	$customRow['user_name'] = $row['user_name'];
	$customRow['user_fullname'] = $row['full_name'];
	$customRow['user_email'] = $row['email'];
	$customRow['user_birthday'] = $row['birthday'];
	$customRow['user_image'] = $user_image;


	if($profile_id != $user_id)
	{	
		$query = "SELECT * FROM phpfox_follow WHERE user_id = $profile_id AND follower_id = $user_id";
		$data = mysql_query( $query );
		$row = mysql_fetch_array( $data );
		if($row == '' || $row == null )
			$customRow['is_following'] = false;

		else
			$customRow['is_following'] = true;
	}

	$response[] = array('success'=>true, 'data'=>$customRow, 'msg'=>'User information returned successfully!');
}
$json = json_encode($response);
$json = substr($json, 1);
$json = substr($json, 0, -1);
echo $json;
?>

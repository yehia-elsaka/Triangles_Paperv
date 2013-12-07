<?php
require('config.php');
$media = $_REQUEST['media'];

$query = 'SELECT * FROM test_media WHERE media LIKE "%'.$media.'%"';
$data = mysql_query($query);
$row = mysql_fetch_array( $data );

$response[] = array('success'=>true, 'data'=>$row);
$json = json_encode($response);
$json = substr($json, 1);
$json = substr($json, 0, -1);
echo $json;

?>


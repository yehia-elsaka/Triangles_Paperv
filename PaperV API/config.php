<?php
// Make sure we are running PHP5.
if (version_compare(phpversion(), '5', '<') === true)
{
	exit('phpFox 2 or higher requires PHP 5 or newer.');
}

ob_start();

/**
 * Key to include phpFox
 *
 */
define('PHPFOX', true);

/**
 * Directory Seperator
 *
 */
define('PHPFOX_DS', DIRECTORY_SEPARATOR);

/**
 * phpFox Root Directory
 *
 */
define('PHPFOX_DIR', dirname(dirname(__FILE__)) . PHPFOX_DS);
//echo PHPFOX_DIR;
define('PHPFOX_START_TIME', array_sum(explode(' ', microtime())));

// Require phpFox Init
require(PHPFOX_DIR . 'include' . PHPFOX_DS . 'init.inc.php');


function replacer(& $item, $key) {
    if ($item === null) {
        $item = '';
    }
}


?>


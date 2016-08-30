<?php
// ====================================
// -------------  Config  -------------
// ====================================

// File Paths
$filePlayerList = 'PlayerList.json';
$fileLastUpdate = 'PlayerList.lastUpdate';

// White-listed IPs
$allowedIPs = array(
	"127.0.0.1", // Your Server IP
	"192.168.1.1" // Another Server IP
);

// Plugin Agent
$pluginAgent = "JsonPlayerList";



// ====================================
// ---------  Do Not Modify  ----------
// ====================================

// IP not allowed
$clientIP = $_SERVER['REMOTE_ADDR'];
if(!in_array($clientIP, $allowedIPs)) {
	die("Invalid");
}

// Invalid request
if ($_SERVER['HTTP_USER_AGENT'] != $pluginAgent) {
	die("Invalid");
}

// Server is now offline.
if ($_POST['online'] == 'false') {
	file_put_contents($filePlayerList, "Offline");
	file_put_contents($fileLastUpdate, time());
	exit;
}

// Server is now online.
if ($_POST['online'] == 'true') {
	file_put_contents($filePlayerList, "{}");
	file_put_contents($fileLastUpdate, time());
	exit;
}

// Received player list
if ($_POST['data']) {
	$data = utf8_encode($_POST['data']);
	file_put_contents($filePlayerList, $data);
	file_put_contents($fileLastUpdate, time());
}

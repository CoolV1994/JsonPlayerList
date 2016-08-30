<?php
// ====================================
// -------------  Config  -------------
// ====================================

// File Paths
$filePlayerList = 'PlayerList.json';
$fileLastUpdate = 'PlayerList.lastUpdate';



// ====================================
// ---------  Do Not Modify  ----------
// ====================================

// $stats['onlinePlayers'] - Number of players on all servers
// $stats['maxPlayers'] - Max number of players
// $stats['servers'] - Array of servers in proxy

// $server['name'] - Server name
// $server['onlinePlayers'] - Number of online players
// $server['players'] - Array of online players
// $server['motd'] - Server motd

// $player['uuid'] - Players uuid
// $player['name'] - Players username
// $player['displayName'] - Players display name

function printStatus($status) {
	?>
	<p><?=$status;?></p>
	<?php
}

function printPlayer($player) {
	// Player avatar image
	$avatarURL = "https://minotar.net/helm/".$player['name']."/16.png";
	?>
		<p><img src="<?=$avatarURL;?>"/> <?=$player['displayName'];?></p>
	<?php
}

function printServer($server) {
	?>
		<p><?=$server['name'];?></p>
	<?php
}

function printPlayerList($stats) {
	foreach ($stats['servers'] as $server) {
		printServer($server);
		foreach ($server['players'] as $player) {
			printPlayer($player);
		}
	}
}

function checkStatus($statsFile, $timestampFile) {
	// Not recently updated, probably offline
	$lastUpdate = file_get_contents($timestampFile);
	if (time() - $lastUpdate > 120000) {
		printStatus('The server is currently offline.');
		return;
	}

	// Player list up to date
	$data = file_get_contents($statsFile);

	// Server has been shut down
	if ($data == 'Offline') {
		printStatus('The server is currently offline.');
		return;
	}

	// Server online
	$stats = json_decode($data, true);

	// Nobody is online
	if (!$stats['onlinePlayers']) {
		printStatus('Nobody is currently playing on the server.');
		return;
	}

	// List online players
	printStatus('There is '.$stats['onlinePlayers'].' players online out of '.$stats['maxPlayers'].'.');
	printPlayerList($stats);
}

// Call the main function
checkStatus($filePlayerList, $fileLastUpdate);

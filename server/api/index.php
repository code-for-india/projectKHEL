<?php

require 'Slim/Slim.php';
//require_once 'include/DbHandler.php';
//require_once 'include/PassHash.php';

$app = new Slim();

$app->get('/locations', 'getLocations');
$app->get('/location/:id', 'getLocation');
$app->get('/modules', 'getModules');
$app->get('/module/:id', 'getModule');
$app->get('/coordinators', 'getCoordinators');
$app->get('/coordinator/:id', 'getCoordinator');
$app->get('/beneficiaries/:locid', 'getBeneficiaries');

$app->post('/attendance', 'addAttendance');

$app->run();

function getLocations() {
	$sql = "select id, name FROM location ORDER BY name";
	try {
		$db = getConnection();
		$stmt = $db->query($sql);  
		$locs = $stmt->fetchAll(PDO::FETCH_OBJ);
		$db = null;
		echo '{"locations": ' . json_encode($locs) . '}';
	} catch(PDOException $e) {
		echo '{"error":{"text":'. $e->getMessage() .'}}'; 
	}
}

function getLocation($id) {
	$sql = "SELECT id, name FROM location WHERE id=:id";
	try {
		$db = getConnection();
		$stmt = $db->prepare($sql);  
		$stmt->bindParam("id", $id);
		$stmt->execute();
		$location = $stmt->fetchObject();  
		$db = null;
		echo json_encode($location); 
	} catch(PDOException $e) {
		echo '{"error":{"text":'. $e->getMessage() .'}}'; 
	}
}

function addAttendance() {
	$request = Slim::getInstance()->request();
	$data = json_decode($request->getBody());
	$sql = "INSERT INTO attendance (held_on, location_id, coordinators, modules, beneficiaries, comment, rating, user_submitted) VALUES (:held_on, :location_id, :coordinators, :modules, :beneficiaries, :comment, :rating, :user_submitted)";
	try {
		$db = getConnection();
		$stmt = $db->prepare($sql);  
		$stmt->bindParam("held_on", $data->date);
		$stmt->bindParam("location_id", $data->location);
		$stmt->bindParam("coordinators", $data->coordinators);
		$stmt->bindParam("modules", $data->modules);
		$stmt->bindParam("beneficiaries", $data->beneficiaries);
		$stmt->bindParam("comment", $data->comments);
		$stmt->bindParam("rating", $data->rating);
		$stmt->bindParam("user_submitted", $data->userid);      
		$stmt->execute();
		$data->id = $db->lastInsertId();
		$db = null;
		echo '{"attendance": ' . json_encode($data->id) . '}';
	} catch(PDOException $e) {
		error_log($e->getMessage(), 3, '/var/tmp/php.log');
		echo '{"error":{"text":'. $e->getMessage() .'}}'; 
	}
}

function getModules() {
	$sql = "select id, name FROM module ORDER BY name";
	try {
		$db = getConnection();
		$stmt = $db->query($sql);  
		$data = $stmt->fetchAll(PDO::FETCH_OBJ);
		$db = null;
		echo '{"modules": ' . json_encode($data) . '}';
	} catch(PDOException $e) {
		echo '{"error":{"text":'. $e->getMessage() .'}}'; 
	}
}

function getModule($id) {
	$sql = "SELECT id, name FROM module WHERE id=:id";
	try {
		$db = getConnection();
		$stmt = $db->prepare($sql);  
		$stmt->bindParam("id", $id);
		$stmt->execute();
		$data = $stmt->fetchObject();  
		$db = null;
		echo json_encode($data); 
	} catch(PDOException $e) {
		echo '{"error":{"text":'. $e->getMessage() .'}}'; 
	}
}

function getCoordinators() {
	$sql = "select id, name, role FROM coordinator ORDER BY name";
	try {
		$db = getConnection();
		$stmt = $db->query($sql);  
		$data = $stmt->fetchAll(PDO::FETCH_OBJ);
		$db = null;
		echo '{"coordinators": ' . json_encode($data) . '}';
	} catch(PDOException $e) {
		echo '{"error":{"text":'. $e->getMessage() .'}}'; 
	}
}

function getCoordinator($id) {
	$sql = "SELECT id, name FROM coordinator WHERE id=:id";
	try {
		$db = getConnection();
		$stmt = $db->prepare($sql);  
		$stmt->bindParam("id", $id);
		$stmt->execute();
		$data = $stmt->fetchObject();  
		$db = null;
		echo json_encode($data); 
	} catch(PDOException $e) {
		echo '{"error":{"text":'. $e->getMessage() .'}}'; 
	}
}

function getBeneficiaries($locid) {
	$sql = "SELECT id, name FROM beneficiary WHERE location_id=:locnid";
	try {
		$db = getConnection();
		$stmt = $db->prepare($sql);  
		$stmt->bindParam("locnid", $locid);
		$stmt->execute();
		$data = $stmt->fetchAll(PDO::FETCH_OBJ);
		$db = null;
        echo '{"beneficiaries": ' . json_encode($data) . '}';
	} catch(PDOException $e) {
		echo '{"error":{"text":'. $e->getMessage() .'}}'; 
	}
}

function getConnection() {
	$dbhost="localhost";
	$dbuser="xx";
	$dbpass="xx";
	$dbname="xx";
	$dbh = new PDO("mysql:host=$dbhost;dbname=$dbname", $dbuser, $dbpass);	
	$dbh->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	return $dbh;
}

?>
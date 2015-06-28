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
$app->get('/beneficiaries', 'getBeneficiaries');
$app->get('/beneficiaries/:locid', 'getBeneficiariesForLocation');

$app->post('/attendance', 'addAttendance');
$app->get('/attendances', 'getAttendances');
$app->get('/mastersync', 'getMasterSync');

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

function getMasterSync() {
  $sql = "select * FROM mastersync order by id";
  try {
    $db = getConnection();
    $stmt = $db->query($sql);  
    $data = $stmt->fetchAll(PDO::FETCH_OBJ);
    $db = null;
    echo '{"mastersync": ' . json_encode($data) . '}';
  } catch(PDOException $e) {
    echo '{"error":{"text":'. $e->getMessage() .'}}'; 
  }
}

function getAttendances() {
  $sql = "select * from attendance ORDER BY id";
  try {
    $db = getConnection();
    $stmt = $db->query($sql);  
    $data = $stmt->fetchAll(PDO::FETCH_OBJ);
    $db = null;
    echo '{"attendances": ' . json_encode($data) . '}';
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
  $sql = "INSERT INTO attendance (held_on, location_id, coordinators, modules, beneficiaries, comment, user_submitted, " .
    "rating_session_objectives, rating_org_objectives, rating_funforkids, mode_of_transport,  " .
    "debrief_what_worked, debrief_to_improve, debrief_didnt_work) " .
    "VALUES (:held_on, :location_id, :coordinators, :modules, :beneficiaries, :comment, :user_submitted, " .
    ":rating_session, :rating_org, :rating_kids, :mode_transport, :debrief_wk, :debrief_impr, :debrief_nowork )";
  
  try {
    $db = getConnection();
    $stmt = $db->prepare($sql);  
    $stmt->bindParam("held_on", $data->date);
    $stmt->bindParam("location_id", $data->location);
    $stmt->bindParam("coordinators", $data->coordinators);
    $stmt->bindParam("modules", $data->modules);
    $stmt->bindParam("beneficiaries", $data->beneficiaries);
    $stmt->bindParam("comment", $data->comments);
    $stmt->bindParam("user_submitted", $data->userid);      
    $stmt->bindParam("rating_session", $data->ratingsessionobjectives);
    $stmt->bindParam("rating_org", $data->ratingorgobjectives);
    $stmt->bindParam("rating_kids", $data->ratingfunforkids);
    $stmt->bindParam("mode_transport", $data->modeoftransport);
    $stmt->bindParam("debrief_wk", $data->debriefwhatworked);
    $stmt->bindParam("debrief_impr", $data->debrieftoimprove);
    $stmt->bindParam("debrief_nowork", $data->debriefdidntwork);
    
    
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

function getBeneficiaries() {
  $sql = "SELECT id, name, location_id, class, age, sex FROM beneficiary ORDER BY location_id";
  try {
    $db = getConnection();
    $stmt = $db->prepare($sql);  
    $stmt->execute();
    $data = $stmt->fetchAll(PDO::FETCH_OBJ);
    $db = null;
    echo '{"beneficiaries": ' . json_encode($data) . '}';
  } catch(PDOException $e) {
    echo '{"error":{"text":'. $e->getMessage() .'}}'; 
  }
}

function getBeneficiariesForLocation($locid) {
  $sql = "SELECT id, name, class, age, sex FROM beneficiary WHERE location_id=:locnid";
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
  $dbuser="xxxx";
  $dbpass="xxxx";
  $dbname="xxxx";
  $dbh = new PDO("mysql:host=$dbhost;dbname=$dbname", $dbuser, $dbpass);	
  $dbh->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
  return $dbh;
}

?>
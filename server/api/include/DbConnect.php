<?php

/**
 * Handling database connection
 */
class DbConnect {

  private $conn;

  function __construct() {       
  }

  /**:
     * Establishing database connection
     * @return database connection handler
     */
  function connect() {
    include_once dirname(__FILE__) . '/Config.php';

    // Connecting to mysql database

    $this->conn = new PDO("mysql:host=". DB_HOST .";dbname=" . DB_NAME, DB_USERNAME, DB_PASSWORD);	
    $this->conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // returing connection resource
    return $this->conn;
  }

}

?>
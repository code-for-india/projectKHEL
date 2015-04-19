<?php
/**
 * @package ProjectKHEL
 *
 * APPLICATION-WIDE CONFIGURATION SETTINGS
 *
 * This file contains application-wide configuration settings.  The settings
 * here will be the same regardless of the machine on which the app is running.
 *
 * This configuration should be added to version control.
 *
 * No settings should be added to this file that would need to be changed
 * on a per-machine basic (ie local, staging or production).  Any
 * machine-specific settings should be added to _machine_config.php
 */

/**
 * APPLICATION ROOT DIRECTORY
 * If the application doesn't detect this correctly then it can be set explicitly
 */
if (!GlobalConfig::$APP_ROOT) GlobalConfig::$APP_ROOT = realpath("./");

/**
 * check is needed to ensure asp_tags is not enabled
 */
if (ini_get('asp_tags')) 
	die('<h3>Server Configuration Problem: asp_tags is enabled, but is not compatible with Savant.</h3>'
	. '<p>You can disable asp_tags in .htaccess, php.ini or generate your app with another template engine such as Smarty.</p>');

/**
 * INCLUDE PATH
 * Adjust the include path as necessary so PHP can locate required libraries
 */
set_include_path(
		GlobalConfig::$APP_ROOT . '/libs/' . PATH_SEPARATOR .
		'phar://' . GlobalConfig::$APP_ROOT . '/libs/phreeze-3.3.phar' . PATH_SEPARATOR .
		GlobalConfig::$APP_ROOT . '/phreezelibs' . PATH_SEPARATOR .
		GlobalConfig::$APP_ROOT . '/vendor/phreeze/phreeze/libs/' . PATH_SEPARATOR .
		get_include_path()
);

/**
 * COMPOSER AUTOLOADER
 * Uncomment if Composer is being used to manage dependencies
 */
// $loader = require 'vendor/autoload.php';
// $loader->setUseIncludePath(true);

/**
 * SESSION CLASSES
 * Any classes that will be stored in the session can be added here
 * and will be pre-loaded on every page
 */
require_once "App/KhelUsers.php";
require_once "App/ExampleUser.php";

/**
 * RENDER ENGINE
 * You can use any template system that implements
 * IRenderEngine for the view layer.  Phreeze provides pre-built
 * implementations for Smarty, Savant and plain PHP.
 */
require_once 'verysimple/Phreeze/SavantRenderEngine.php';
GlobalConfig::$TEMPLATE_ENGINE = 'SavantRenderEngine';
GlobalConfig::$TEMPLATE_PATH = GlobalConfig::$APP_ROOT . '/templates/';

/**
 * ROUTE MAP
 * The route map connects URLs to Controller+Method and additionally maps the
 * wildcards to a named parameter so that they are accessible inside the
 * Controller without having to parse the URL for parameters such as IDs
 */
GlobalConfig::$ROUTE_MAP = array(

	// default controller when no route specified
	'GET:' => array('route' => 'Default.Home'),
		
	// example authentication routes
	'GET:loginform' => array('route' => 'SecureExample.LoginForm'),
	'POST:login' => array('route' => 'SecureExample.Login'),
	'GET:secureuser' => array('route' => 'SecureExample.UserPage'),
	'GET:secureadmin' => array('route' => 'SecureExample.AdminPage'),
	'GET:logout' => array('route' => 'SecureExample.Logout'),
		
	// Attendance
	'GET:attendances' => array('route' => 'Attendance.ListView'),
	'GET:attendance/(:num)' => array('route' => 'Attendance.SingleView', 'params' => array('id' => 1)),
	'GET:api/attendances' => array('route' => 'Attendance.Query'),
	'POST:api/attendance' => array('route' => 'Attendance.Create'),
	'GET:api/attendance/(:num)' => array('route' => 'Attendance.Read', 'params' => array('id' => 2)),
	'PUT:api/attendance/(:num)' => array('route' => 'Attendance.Update', 'params' => array('id' => 2)),
	'DELETE:api/attendance/(:num)' => array('route' => 'Attendance.Delete', 'params' => array('id' => 2)),
		
	// Beneficiary
	'GET:beneficiaries' => array('route' => 'Beneficiary.ListView'),
	'GET:beneficiary/(:num)' => array('route' => 'Beneficiary.SingleView', 'params' => array('id' => 1)),
	'GET:api/beneficiaries' => array('route' => 'Beneficiary.Query'),
	'POST:api/beneficiary' => array('route' => 'Beneficiary.Create'),
	'GET:api/beneficiary/(:num)' => array('route' => 'Beneficiary.Read', 'params' => array('id' => 2)),
	'PUT:api/beneficiary/(:num)' => array('route' => 'Beneficiary.Update', 'params' => array('id' => 2)),
	'DELETE:api/beneficiary/(:num)' => array('route' => 'Beneficiary.Delete', 'params' => array('id' => 2)),
		
	// Coordinator
	'GET:coordinators' => array('route' => 'Coordinator.ListView'),
	'GET:coordinator/(:num)' => array('route' => 'Coordinator.SingleView', 'params' => array('id' => 1)),
	'GET:api/coordinators' => array('route' => 'Coordinator.Query'),
	'POST:api/coordinator' => array('route' => 'Coordinator.Create'),
	'GET:api/coordinator/(:num)' => array('route' => 'Coordinator.Read', 'params' => array('id' => 2)),
	'PUT:api/coordinator/(:num)' => array('route' => 'Coordinator.Update', 'params' => array('id' => 2)),
	'DELETE:api/coordinator/(:num)' => array('route' => 'Coordinator.Delete', 'params' => array('id' => 2)),
		
	// Location
	'GET:locations' => array('route' => 'Location.ListView'),
	'GET:location/(:num)' => array('route' => 'Location.SingleView', 'params' => array('id' => 1)),
	'GET:api/locations' => array('route' => 'Location.Query'),
	'POST:api/location' => array('route' => 'Location.Create'),
	'GET:api/location/(:num)' => array('route' => 'Location.Read', 'params' => array('id' => 2)),
	'PUT:api/location/(:num)' => array('route' => 'Location.Update', 'params' => array('id' => 2)),
	'DELETE:api/location/(:num)' => array('route' => 'Location.Delete', 'params' => array('id' => 2)),
		
	// Module
	'GET:modules' => array('route' => 'Module.ListView'),
	'GET:module/(:num)' => array('route' => 'Module.SingleView', 'params' => array('id' => 1)),
	'GET:api/modules' => array('route' => 'Module.Query'),
	'POST:api/module' => array('route' => 'Module.Create'),
	'GET:api/module/(:num)' => array('route' => 'Module.Read', 'params' => array('id' => 2)),
	'PUT:api/module/(:num)' => array('route' => 'Module.Update', 'params' => array('id' => 2)),
	'DELETE:api/module/(:num)' => array('route' => 'Module.Delete', 'params' => array('id' => 2)),
		
	// Users
	'GET:userses' => array('route' => 'Users.ListView'),
	'GET:users/(:num)' => array('route' => 'Users.SingleView', 'params' => array('id' => 1)),
	'GET:api/userses' => array('route' => 'Users.Query'),
	'POST:api/users' => array('route' => 'Users.Create'),
	'GET:api/users/(:num)' => array('route' => 'Users.Read', 'params' => array('id' => 2)),
	'PUT:api/users/(:num)' => array('route' => 'Users.Update', 'params' => array('id' => 2)),
	'DELETE:api/users/(:num)' => array('route' => 'Users.Delete', 'params' => array('id' => 2)),

	// catch any broken API urls
	'GET:api/(:any)' => array('route' => 'Default.ErrorApi404'),
	'PUT:api/(:any)' => array('route' => 'Default.ErrorApi404'),
	'POST:api/(:any)' => array('route' => 'Default.ErrorApi404'),
	'DELETE:api/(:any)' => array('route' => 'Default.ErrorApi404')
);

/**
 * FETCHING STRATEGY
 * You may uncomment any of the lines below to specify always eager fetching.
 * Alternatively, you can copy/paste to a specific page for one-time eager fetching
 * If you paste into a controller method, replace $G_PHREEZER with $this->Phreezer
 */
?>
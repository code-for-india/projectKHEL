<?php
/** @package    Kheldb::Reporter */

/** import supporting libraries */
require_once("verysimple/Phreeze/Reporter.php");

/**
 * This is an example Reporter based on the Attendance object.  The reporter object
 * allows you to run arbitrary queries that return data which may or may not fith within
 * the data access API.  This can include aggregate data or subsets of data.
 *
 * Note that Reporters are read-only and cannot be used for saving data.
 *
 * @package Kheldb::Model::DAO
 * @author ClassBuilder
 * @version 1.0
 */
class AttendanceReporter extends Reporter
{

	// the properties in this class must match the columns returned by GetCustomQuery().
	// 'CustomFieldExample' is an example that is not part of the `attendance` table
	public $CustomFieldExample;

	public $Id;
	public $HeldOn;
	public $LocationId;
	public $Coordinators;
	public $Modules;
	public $Beneficiaries;
	public $Comment;
	public $RatingSessionObjectives;
	public $CreatedAt;
	public $ModifiedAt;
	public $UserSubmitted;
	public $ModeOfTransport;
	public $DebriefWhatWorked;
	public $DebriefToImprove;
	public $DebriefDidntWork;
	public $RatingOrgObjectives;
	public $RatingFunforkids;

	/*
	* GetCustomQuery returns a fully formed SQL statement.  The result columns
	* must match with the properties of this reporter object.
	*
	* @see Reporter::GetCustomQuery
	* @param Criteria $criteria
	* @return string SQL statement
	*/
	static function GetCustomQuery($criteria)
	{
		$sql = "select
			'custom value here...' as CustomFieldExample
			,`attendance`.`id` as Id
			,`attendance`.`held_on` as HeldOn
			,`attendance`.`location_id` as LocationId
			,`attendance`.`coordinators` as Coordinators
			,`attendance`.`modules` as Modules
			,`attendance`.`beneficiaries` as Beneficiaries
			,`attendance`.`comment` as Comment
			,`attendance`.`rating_session_objectives` as RatingSessionObjectives
			,`attendance`.`created_at` as CreatedAt
			,`attendance`.`modified_at` as ModifiedAt
			,`attendance`.`user_submitted` as UserSubmitted
			,`attendance`.`mode_of_transport` as ModeOfTransport
			,`attendance`.`debrief_what_worked` as DebriefWhatWorked
			,`attendance`.`debrief_to_improve` as DebriefToImprove
			,`attendance`.`debrief_didnt_work` as DebriefDidntWork
			,`attendance`.`rating_org_objectives` as RatingOrgObjectives
			,`attendance`.`rating_funforkids` as RatingFunforkids
		from `attendance`";

		// the criteria can be used or you can write your own custom logic.
		// be sure to escape any user input with $criteria->Escape()
		$sql .= $criteria->GetWhere();
		$sql .= $criteria->GetOrder();

		return $sql;
	}
	
	/*
	* GetCustomCountQuery returns a fully formed SQL statement that will count
	* the results.  This query must return the correct number of results that
	* GetCustomQuery would, given the same criteria
	*
	* @see Reporter::GetCustomCountQuery
	* @param Criteria $criteria
	* @return string SQL statement
	*/
	static function GetCustomCountQuery($criteria)
	{
		$sql = "select count(1) as counter from `attendance`";

		// the criteria can be used or you can write your own custom logic.
		// be sure to escape any user input with $criteria->Escape()
		$sql .= $criteria->GetWhere();

		return $sql;
	}
}

?>
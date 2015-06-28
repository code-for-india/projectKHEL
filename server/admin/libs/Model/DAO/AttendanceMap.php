<?php
/** @package    Kheldb::Model::DAO */

/** import supporting libraries */
require_once("verysimple/Phreeze/IDaoMap.php");
require_once("verysimple/Phreeze/IDaoMap2.php");

/**
 * AttendanceMap is a static class with functions used to get FieldMap and KeyMap information that
 * is used by Phreeze to map the AttendanceDAO to the attendance datastore.
 *
 * WARNING: THIS IS AN AUTO-GENERATED FILE
 *
 * This file should generally not be edited by hand except in special circumstances.
 * You can override the default fetching strategies for KeyMaps in _config.php.
 * Leaving this file alone will allow easy re-generation of all DAOs in the event of schema changes
 *
 * @package Kheldb::Model::DAO
 * @author ClassBuilder
 * @version 1.0
 */
class AttendanceMap implements IDaoMap, IDaoMap2
{

	private static $KM;
	private static $FM;
	
	/**
	 * {@inheritdoc}
	 */
	public static function AddMap($property,FieldMap $map)
	{
		self::GetFieldMaps();
		self::$FM[$property] = $map;
	}
	
	/**
	 * {@inheritdoc}
	 */
	public static function SetFetchingStrategy($property,$loadType)
	{
		self::GetKeyMaps();
		self::$KM[$property]->LoadType = $loadType;
	}

	/**
	 * {@inheritdoc}
	 */
	public static function GetFieldMaps()
	{
		if (self::$FM == null)
		{
			self::$FM = Array();
			self::$FM["Id"] = new FieldMap("Id","attendance","id",true,FM_TYPE_INT,11,null,true);
			self::$FM["HeldOn"] = new FieldMap("HeldOn","attendance","held_on",false,FM_TYPE_DATE,null,null,false);
			self::$FM["LocationId"] = new FieldMap("LocationId","attendance","location_id",false,FM_TYPE_INT,11,null,false);
			self::$FM["Coordinators"] = new FieldMap("Coordinators","attendance","coordinators",false,FM_TYPE_VARCHAR,255,null,false);
			self::$FM["Modules"] = new FieldMap("Modules","attendance","modules",false,FM_TYPE_VARCHAR,255,null,false);
			self::$FM["Beneficiaries"] = new FieldMap("Beneficiaries","attendance","beneficiaries",false,FM_TYPE_VARCHAR,1000,null,false);
			self::$FM["Comment"] = new FieldMap("Comment","attendance","comment",false,FM_TYPE_VARCHAR,500,null,false);
			self::$FM["RatingSessionObjectives"] = new FieldMap("RatingSessionObjectives","attendance","rating_session_objectives",false,FM_TYPE_INT,2,"5",false);
			self::$FM["CreatedAt"] = new FieldMap("CreatedAt","attendance","created_at",false,FM_TYPE_TIMESTAMP,null,"CURRENT_TIMESTAMP",false);
			self::$FM["ModifiedAt"] = new FieldMap("ModifiedAt","attendance","modified_at",false,FM_TYPE_TIMESTAMP,null,"0000-00-00 00:00:00",false);
			self::$FM["UserSubmitted"] = new FieldMap("UserSubmitted","attendance","user_submitted",false,FM_TYPE_VARCHAR,100,null,false);
			self::$FM["ModeOfTransport"] = new FieldMap("ModeOfTransport","attendance","mode_of_transport",false,FM_TYPE_VARCHAR,50,null,false);
			self::$FM["DebriefWhatWorked"] = new FieldMap("DebriefWhatWorked","attendance","debrief_what_worked",false,FM_TYPE_VARCHAR,100,null,false);
			self::$FM["DebriefToImprove"] = new FieldMap("DebriefToImprove","attendance","debrief_to_improve",false,FM_TYPE_VARCHAR,100,null,false);
			self::$FM["DebriefDidntWork"] = new FieldMap("DebriefDidntWork","attendance","debrief_didnt_work",false,FM_TYPE_VARCHAR,100,null,false);
			self::$FM["RatingOrgObjectives"] = new FieldMap("RatingOrgObjectives","attendance","rating_org_objectives",false,FM_TYPE_INT,2,"5",false);
			self::$FM["RatingFunforkids"] = new FieldMap("RatingFunforkids","attendance","rating_funforkids",false,FM_TYPE_INT,2,"5",false);
		}
		return self::$FM;
	}

	/**
	 * {@inheritdoc}
	 */
	public static function GetKeyMaps()
	{
		if (self::$KM == null)
		{
			self::$KM = Array();
		}
		return self::$KM;
	}

}

?>
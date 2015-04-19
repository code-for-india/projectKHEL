<?php
/** @package    Kheldb::Model::DAO */

/** import supporting libraries */
require_once("verysimple/Phreeze/IDaoMap.php");
require_once("verysimple/Phreeze/IDaoMap2.php");

/**
 * BeneficiaryMap is a static class with functions used to get FieldMap and KeyMap information that
 * is used by Phreeze to map the BeneficiaryDAO to the beneficiary datastore.
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
class BeneficiaryMap implements IDaoMap, IDaoMap2
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
			self::$FM["Id"] = new FieldMap("Id","beneficiary","id",true,FM_TYPE_INT,15,null,true);
			self::$FM["LocationId"] = new FieldMap("LocationId","beneficiary","location_id",false,FM_TYPE_INT,11,null,false);
			self::$FM["Name"] = new FieldMap("Name","beneficiary","name",false,FM_TYPE_VARCHAR,100,null,false);
			self::$FM["CreatedAt"] = new FieldMap("CreatedAt","beneficiary","created_at",false,FM_TYPE_TIMESTAMP,null,"CURRENT_TIMESTAMP",false);
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
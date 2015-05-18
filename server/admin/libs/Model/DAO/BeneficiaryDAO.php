<?php
/** @package Kheldb::Model::DAO */

/** import supporting libraries */
require_once("verysimple/Phreeze/Phreezable.php");
require_once("BeneficiaryMap.php");

/**
 * BeneficiaryDAO provides object-oriented access to the beneficiary table.  This
 * class is automatically generated by ClassBuilder.
 *
 * WARNING: THIS IS AN AUTO-GENERATED FILE
 *
 * This file should generally not be edited by hand except in special circumstances.
 * Add any custom business logic to the Model class which is extended from this DAO class.
 * Leaving this file alone will allow easy re-generation of all DAOs in the event of schema changes
 *
 * @package Kheldb::Model::DAO
 * @author ClassBuilder
 * @version 1.0
 */
class BeneficiaryDAO extends Phreezable
{
	/** @var int */
	public $Id;

	/** @var int */
	public $LocationId;

	/** @var string */
	public $Name;

  	/** @var string */
    public $Class;
  
	/** @var int */
	public $Age;

	/** @var char */
	public $Sex;

	/** @var timestamp */
	public $CreatedAt;


	/**
	 * Returns the foreign object based on the value of LocationId
	 * @return Location
	 */
	public function GetLocation()
	{
		return $this->_phreezer->GetManyToOne($this, "beneficiary_ibfk_1");
	}


}
?>
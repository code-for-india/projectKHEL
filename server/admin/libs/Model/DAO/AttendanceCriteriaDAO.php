<?php
/** @package    Kheldb::Model::DAO */

/** import supporting libraries */
require_once("verysimple/Phreeze/Criteria.php");

/**
 * AttendanceCriteria allows custom querying for the Attendance object.
 *
 * WARNING: THIS IS AN AUTO-GENERATED FILE
 *
 * This file should generally not be edited by hand except in special circumstances.
 * Add any custom business logic to the ModelCriteria class which is extended from this class.
 * Leaving this file alone will allow easy re-generation of all DAOs in the event of schema changes
 *
 * @inheritdocs
 * @package Kheldb::Model::DAO
 * @author ClassBuilder
 * @version 1.0
 */
class AttendanceCriteriaDAO extends Criteria
{

	public $Id_Equals;
	public $Id_NotEquals;
	public $Id_IsLike;
	public $Id_IsNotLike;
	public $Id_BeginsWith;
	public $Id_EndsWith;
	public $Id_GreaterThan;
	public $Id_GreaterThanOrEqual;
	public $Id_LessThan;
	public $Id_LessThanOrEqual;
	public $Id_In;
	public $Id_IsNotEmpty;
	public $Id_IsEmpty;
	public $Id_BitwiseOr;
	public $Id_BitwiseAnd;
	public $HeldOn_Equals;
	public $HeldOn_NotEquals;
	public $HeldOn_IsLike;
	public $HeldOn_IsNotLike;
	public $HeldOn_BeginsWith;
	public $HeldOn_EndsWith;
	public $HeldOn_GreaterThan;
	public $HeldOn_GreaterThanOrEqual;
	public $HeldOn_LessThan;
	public $HeldOn_LessThanOrEqual;
	public $HeldOn_In;
	public $HeldOn_IsNotEmpty;
	public $HeldOn_IsEmpty;
	public $HeldOn_BitwiseOr;
	public $HeldOn_BitwiseAnd;
	public $LocationId_Equals;
	public $LocationId_NotEquals;
	public $LocationId_IsLike;
	public $LocationId_IsNotLike;
	public $LocationId_BeginsWith;
	public $LocationId_EndsWith;
	public $LocationId_GreaterThan;
	public $LocationId_GreaterThanOrEqual;
	public $LocationId_LessThan;
	public $LocationId_LessThanOrEqual;
	public $LocationId_In;
	public $LocationId_IsNotEmpty;
	public $LocationId_IsEmpty;
	public $LocationId_BitwiseOr;
	public $LocationId_BitwiseAnd;
	public $Coordinators_Equals;
	public $Coordinators_NotEquals;
	public $Coordinators_IsLike;
	public $Coordinators_IsNotLike;
	public $Coordinators_BeginsWith;
	public $Coordinators_EndsWith;
	public $Coordinators_GreaterThan;
	public $Coordinators_GreaterThanOrEqual;
	public $Coordinators_LessThan;
	public $Coordinators_LessThanOrEqual;
	public $Coordinators_In;
	public $Coordinators_IsNotEmpty;
	public $Coordinators_IsEmpty;
	public $Coordinators_BitwiseOr;
	public $Coordinators_BitwiseAnd;
	public $Modules_Equals;
	public $Modules_NotEquals;
	public $Modules_IsLike;
	public $Modules_IsNotLike;
	public $Modules_BeginsWith;
	public $Modules_EndsWith;
	public $Modules_GreaterThan;
	public $Modules_GreaterThanOrEqual;
	public $Modules_LessThan;
	public $Modules_LessThanOrEqual;
	public $Modules_In;
	public $Modules_IsNotEmpty;
	public $Modules_IsEmpty;
	public $Modules_BitwiseOr;
	public $Modules_BitwiseAnd;
	public $Beneficiaries_Equals;
	public $Beneficiaries_NotEquals;
	public $Beneficiaries_IsLike;
	public $Beneficiaries_IsNotLike;
	public $Beneficiaries_BeginsWith;
	public $Beneficiaries_EndsWith;
	public $Beneficiaries_GreaterThan;
	public $Beneficiaries_GreaterThanOrEqual;
	public $Beneficiaries_LessThan;
	public $Beneficiaries_LessThanOrEqual;
	public $Beneficiaries_In;
	public $Beneficiaries_IsNotEmpty;
	public $Beneficiaries_IsEmpty;
	public $Beneficiaries_BitwiseOr;
	public $Beneficiaries_BitwiseAnd;
	public $Comment_Equals;
	public $Comment_NotEquals;
	public $Comment_IsLike;
	public $Comment_IsNotLike;
	public $Comment_BeginsWith;
	public $Comment_EndsWith;
	public $Comment_GreaterThan;
	public $Comment_GreaterThanOrEqual;
	public $Comment_LessThan;
	public $Comment_LessThanOrEqual;
	public $Comment_In;
	public $Comment_IsNotEmpty;
	public $Comment_IsEmpty;
	public $Comment_BitwiseOr;
	public $Comment_BitwiseAnd;
	public $Rating_Equals;
	public $Rating_NotEquals;
	public $Rating_IsLike;
	public $Rating_IsNotLike;
	public $Rating_BeginsWith;
	public $Rating_EndsWith;
	public $Rating_GreaterThan;
	public $Rating_GreaterThanOrEqual;
	public $Rating_LessThan;
	public $Rating_LessThanOrEqual;
	public $Rating_In;
	public $Rating_IsNotEmpty;
	public $Rating_IsEmpty;
	public $Rating_BitwiseOr;
	public $Rating_BitwiseAnd;
	public $CreatedAt_Equals;
	public $CreatedAt_NotEquals;
	public $CreatedAt_IsLike;
	public $CreatedAt_IsNotLike;
	public $CreatedAt_BeginsWith;
	public $CreatedAt_EndsWith;
	public $CreatedAt_GreaterThan;
	public $CreatedAt_GreaterThanOrEqual;
	public $CreatedAt_LessThan;
	public $CreatedAt_LessThanOrEqual;
	public $CreatedAt_In;
	public $CreatedAt_IsNotEmpty;
	public $CreatedAt_IsEmpty;
	public $CreatedAt_BitwiseOr;
	public $CreatedAt_BitwiseAnd;
	public $ModifiedAt_Equals;
	public $ModifiedAt_NotEquals;
	public $ModifiedAt_IsLike;
	public $ModifiedAt_IsNotLike;
	public $ModifiedAt_BeginsWith;
	public $ModifiedAt_EndsWith;
	public $ModifiedAt_GreaterThan;
	public $ModifiedAt_GreaterThanOrEqual;
	public $ModifiedAt_LessThan;
	public $ModifiedAt_LessThanOrEqual;
	public $ModifiedAt_In;
	public $ModifiedAt_IsNotEmpty;
	public $ModifiedAt_IsEmpty;
	public $ModifiedAt_BitwiseOr;
	public $ModifiedAt_BitwiseAnd;
	public $UserSubmitted_Equals;
	public $UserSubmitted_NotEquals;
	public $UserSubmitted_IsLike;
	public $UserSubmitted_IsNotLike;
	public $UserSubmitted_BeginsWith;
	public $UserSubmitted_EndsWith;
	public $UserSubmitted_GreaterThan;
	public $UserSubmitted_GreaterThanOrEqual;
	public $UserSubmitted_LessThan;
	public $UserSubmitted_LessThanOrEqual;
	public $UserSubmitted_In;
	public $UserSubmitted_IsNotEmpty;
	public $UserSubmitted_IsEmpty;
	public $UserSubmitted_BitwiseOr;
	public $UserSubmitted_BitwiseAnd;

}

?>
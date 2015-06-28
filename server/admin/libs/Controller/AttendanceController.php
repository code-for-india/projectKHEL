<?php
/** @package    ProjectKHEL::Controller */

/** import supporting libraries */
require_once("AppBaseController.php");
require_once("Model/Attendance.php");

/**
 * AttendanceController is the controller class for the Attendance object.  The
 * controller is responsible for processing input from the user, reading/updating
 * the model as necessary and displaying the appropriate view.
 *
 * @package ProjectKHEL::Controller
 * @author ClassBuilder
 * @version 1.0
 */
class AttendanceController extends AppBaseController
{

	/**
	 * Override here for any controller-specific functionality
	 *
	 * @inheritdocs
	 */
	protected function Init()
	{
		parent::Init();

		// TODO: add controller-wide bootstrap code
		
		// TODO: if authentiation is required for this entire controller, for example:
		// $this->RequirePermission(ExampleUser::$PERMISSION_USER,'SecureExample.LoginForm');
	}

	/**
	 * Displays a list view of Attendance objects
	 */
	public function ListView()
	{
		$this->Render();
	}

	/**
	 * API Method queries for Attendance records and render as JSON
	 */
	public function Query()
	{
		try
		{
			$criteria = new AttendanceCriteria();
			
			// TODO: this will limit results based on all properties included in the filter list 
			$filter = RequestUtil::Get('filter');
			if ($filter) $criteria->AddFilter(
				new CriteriaFilter('Id,HeldOn,LocationId,Coordinators,Modules,Beneficiaries,Comment,RatingSessionObjectives,CreatedAt,ModifiedAt,UserSubmitted,ModeOfTransport,DebriefWhatWorked,DebriefToImprove,DebriefDidntWork,RatingOrgObjectives,RatingFunforkids'
				, '%'.$filter.'%')
			);

			// TODO: this is generic query filtering based only on criteria properties
			foreach (array_keys($_REQUEST) as $prop)
			{
				$prop_normal = ucfirst($prop);
				$prop_equals = $prop_normal.'_Equals';

				if (property_exists($criteria, $prop_normal))
				{
					$criteria->$prop_normal = RequestUtil::Get($prop);
				}
				elseif (property_exists($criteria, $prop_equals))
				{
					// this is a convenience so that the _Equals suffix is not needed
					$criteria->$prop_equals = RequestUtil::Get($prop);
				}
			}

			$output = new stdClass();

			// if a sort order was specified then specify in the criteria
 			$output->orderBy = RequestUtil::Get('orderBy');
 			$output->orderDesc = RequestUtil::Get('orderDesc') != '';
 			if ($output->orderBy) $criteria->SetOrder($output->orderBy, $output->orderDesc);

			$page = RequestUtil::Get('page');

			if ($page != '')
			{
				// if page is specified, use this instead (at the expense of one extra count query)
				$pagesize = $this->GetDefaultPageSize();

				$attendances = $this->Phreezer->Query('Attendance',$criteria)->GetDataPage($page, $pagesize);
				$output->rows = $attendances->ToObjectArray(true,$this->SimpleObjectParams());
				$output->totalResults = $attendances->TotalResults;
				$output->totalPages = $attendances->TotalPages;
				$output->pageSize = $attendances->PageSize;
				$output->currentPage = $attendances->CurrentPage;
			}
			else
			{
				// return all results
				$attendances = $this->Phreezer->Query('Attendance',$criteria);
				$output->rows = $attendances->ToObjectArray(true, $this->SimpleObjectParams());
				$output->totalResults = count($output->rows);
				$output->totalPages = 1;
				$output->pageSize = $output->totalResults;
				$output->currentPage = 1;
			}


			$this->RenderJSON($output, $this->JSONPCallback());
		}
		catch (Exception $ex)
		{
			$this->RenderExceptionJSON($ex);
		}
	}

	/**
	 * API Method retrieves a single Attendance record and render as JSON
	 */
	public function Read()
	{
		try
		{
			$pk = $this->GetRouter()->GetUrlParam('id');
			$attendance = $this->Phreezer->Get('Attendance',$pk);
			$this->RenderJSON($attendance, $this->JSONPCallback(), true, $this->SimpleObjectParams());
		}
		catch (Exception $ex)
		{
			$this->RenderExceptionJSON($ex);
		}
	}

	/**
	 * API Method inserts a new Attendance record and render response as JSON
	 */
	public function Create()
	{
		try
		{
						
			$json = json_decode(RequestUtil::GetBody());

			if (!$json)
			{
				throw new Exception('The request body does not contain valid JSON');
			}

			$attendance = new Attendance($this->Phreezer);

			// TODO: any fields that should not be inserted by the user should be commented out

			// this is an auto-increment.  uncomment if updating is allowed
			// $attendance->Id = $this->SafeGetVal($json, 'id');

			$attendance->HeldOn = date('Y-m-d H:i:s',strtotime($this->SafeGetVal($json, 'heldOn')));
			$attendance->LocationId = $this->SafeGetVal($json, 'locationId');
			$attendance->Coordinators = $this->SafeGetVal($json, 'coordinators');
			$attendance->Modules = $this->SafeGetVal($json, 'modules');
			$attendance->Beneficiaries = $this->SafeGetVal($json, 'beneficiaries');
			$attendance->Comment = $this->SafeGetVal($json, 'comment');
			$attendance->RatingSessionObjectives = $this->SafeGetVal($json, 'ratingSessionObjectives');
			$attendance->CreatedAt = date('Y-m-d H:i:s',strtotime($this->SafeGetVal($json, 'createdAt')));
			$attendance->ModifiedAt = date('Y-m-d H:i:s',strtotime($this->SafeGetVal($json, 'modifiedAt')));
			$attendance->UserSubmitted = $this->SafeGetVal($json, 'userSubmitted');
			$attendance->ModeOfTransport = $this->SafeGetVal($json, 'modeOfTransport');
			$attendance->DebriefWhatWorked = $this->SafeGetVal($json, 'debriefWhatWorked');
			$attendance->DebriefToImprove = $this->SafeGetVal($json, 'debriefToImprove');
			$attendance->DebriefDidntWork = $this->SafeGetVal($json, 'debriefDidntWork');
			$attendance->RatingOrgObjectives = $this->SafeGetVal($json, 'ratingOrgObjectives');
			$attendance->RatingFunforkids = $this->SafeGetVal($json, 'ratingFunforkids');

			$attendance->Validate();
			$errors = $attendance->GetValidationErrors();

			if (count($errors) > 0)
			{
				$this->RenderErrorJSON('Please check the form for errors',$errors);
			}
			else
			{
				$attendance->Save();
				$this->RenderJSON($attendance, $this->JSONPCallback(), true, $this->SimpleObjectParams());
			}

		}
		catch (Exception $ex)
		{
			$this->RenderExceptionJSON($ex);
		}
	}

	/**
	 * API Method updates an existing Attendance record and render response as JSON
	 */
	public function Update()
	{
		try
		{
						
			$json = json_decode(RequestUtil::GetBody());

			if (!$json)
			{
				throw new Exception('The request body does not contain valid JSON');
			}

			$pk = $this->GetRouter()->GetUrlParam('id');
			$attendance = $this->Phreezer->Get('Attendance',$pk);

			// TODO: any fields that should not be updated by the user should be commented out

			// this is a primary key.  uncomment if updating is allowed
			// $attendance->Id = $this->SafeGetVal($json, 'id', $attendance->Id);

			$attendance->HeldOn = date('Y-m-d H:i:s',strtotime($this->SafeGetVal($json, 'heldOn', $attendance->HeldOn)));
			$attendance->LocationId = $this->SafeGetVal($json, 'locationId', $attendance->LocationId);
			$attendance->Coordinators = $this->SafeGetVal($json, 'coordinators', $attendance->Coordinators);
			$attendance->Modules = $this->SafeGetVal($json, 'modules', $attendance->Modules);
			$attendance->Beneficiaries = $this->SafeGetVal($json, 'beneficiaries', $attendance->Beneficiaries);
			$attendance->Comment = $this->SafeGetVal($json, 'comment', $attendance->Comment);
			$attendance->RatingSessionObjectives = $this->SafeGetVal($json, 'ratingSessionObjectives', $attendance->RatingSessionObjectives);
			$attendance->CreatedAt = date('Y-m-d H:i:s',strtotime($this->SafeGetVal($json, 'createdAt', $attendance->CreatedAt)));
			$attendance->ModifiedAt = date('Y-m-d H:i:s',strtotime($this->SafeGetVal($json, 'modifiedAt', $attendance->ModifiedAt)));
			$attendance->UserSubmitted = $this->SafeGetVal($json, 'userSubmitted', $attendance->UserSubmitted);
			$attendance->ModeOfTransport = $this->SafeGetVal($json, 'modeOfTransport', $attendance->ModeOfTransport);
			$attendance->DebriefWhatWorked = $this->SafeGetVal($json, 'debriefWhatWorked', $attendance->DebriefWhatWorked);
			$attendance->DebriefToImprove = $this->SafeGetVal($json, 'debriefToImprove', $attendance->DebriefToImprove);
			$attendance->DebriefDidntWork = $this->SafeGetVal($json, 'debriefDidntWork', $attendance->DebriefDidntWork);
			$attendance->RatingOrgObjectives = $this->SafeGetVal($json, 'ratingOrgObjectives', $attendance->RatingOrgObjectives);
			$attendance->RatingFunforkids = $this->SafeGetVal($json, 'ratingFunforkids', $attendance->RatingFunforkids);

			$attendance->Validate();
			$errors = $attendance->GetValidationErrors();

			if (count($errors) > 0)
			{
				$this->RenderErrorJSON('Please check the form for errors',$errors);
			}
			else
			{
				$attendance->Save();
				$this->RenderJSON($attendance, $this->JSONPCallback(), true, $this->SimpleObjectParams());
			}


		}
		catch (Exception $ex)
		{


			$this->RenderExceptionJSON($ex);
		}
	}

	/**
	 * API Method deletes an existing Attendance record and render response as JSON
	 */
	public function Delete()
	{
		try
		{
						
			// TODO: if a soft delete is prefered, change this to update the deleted flag instead of hard-deleting

			$pk = $this->GetRouter()->GetUrlParam('id');
			$attendance = $this->Phreezer->Get('Attendance',$pk);

			$attendance->Delete();

			$output = new stdClass();

			$this->RenderJSON($output, $this->JSONPCallback());

		}
		catch (Exception $ex)
		{
			$this->RenderExceptionJSON($ex);
		}
	}
}

?>

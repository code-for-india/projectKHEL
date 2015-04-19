<?php
/** @package    ProjectKHEL::Controller */

/** import supporting libraries */
require_once("AppBaseController.php");
require_once("Model/Location.php");

/**
 * LocationController is the controller class for the Location object.  The
 * controller is responsible for processing input from the user, reading/updating
 * the model as necessary and displaying the appropriate view.
 *
 * @package ProjectKHEL::Controller
 * @author ClassBuilder
 * @version 1.0
 */
class LocationController extends AppBaseController
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
		 $this->RequirePermission(KhelUsers::$ROLE_SUPERADMIN,'SecureExample.LoginForm');
	}

	/**
	 * Displays a list view of Location objects
	 */
	public function ListView()
	{
		$this->Render();
	}

	/**
	 * API Method queries for Location records and render as JSON
	 */
	public function Query()
	{
		try
		{
			$criteria = new LocationCriteria();
			
			// TODO: this will limit results based on all properties included in the filter list 
			$filter = RequestUtil::Get('filter');
			if ($filter) $criteria->AddFilter(
				new CriteriaFilter('Id,Name,CreatedAt'
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

				$locations = $this->Phreezer->Query('Location',$criteria)->GetDataPage($page, $pagesize);
				$output->rows = $locations->ToObjectArray(true,$this->SimpleObjectParams());
				$output->totalResults = $locations->TotalResults;
				$output->totalPages = $locations->TotalPages;
				$output->pageSize = $locations->PageSize;
				$output->currentPage = $locations->CurrentPage;
			}
			else
			{
				// return all results
				$locations = $this->Phreezer->Query('Location',$criteria);
				$output->rows = $locations->ToObjectArray(true, $this->SimpleObjectParams());
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
	 * API Method retrieves a single Location record and render as JSON
	 */
	public function Read()
	{
		try
		{
			$pk = $this->GetRouter()->GetUrlParam('id');
			$location = $this->Phreezer->Get('Location',$pk);
			$this->RenderJSON($location, $this->JSONPCallback(), true, $this->SimpleObjectParams());
		}
		catch (Exception $ex)
		{
			$this->RenderExceptionJSON($ex);
		}
	}

	/**
	 * API Method inserts a new Location record and render response as JSON
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

			$location = new Location($this->Phreezer);

			// TODO: any fields that should not be inserted by the user should be commented out

			// this is an auto-increment.  uncomment if updating is allowed
			// $location->Id = $this->SafeGetVal($json, 'id');

			$location->Name = $this->SafeGetVal($json, 'name');
			$location->CreatedAt = date('Y-m-d H:i:s',strtotime($this->SafeGetVal($json, 'createdAt')));

			$location->Validate();
			$errors = $location->GetValidationErrors();

			if (count($errors) > 0)
			{
				$this->RenderErrorJSON('Please check the form for errors',$errors);
			}
			else
			{
				$location->Save();
				$this->RenderJSON($location, $this->JSONPCallback(), true, $this->SimpleObjectParams());
			}

		}
		catch (Exception $ex)
		{
			$this->RenderExceptionJSON($ex);
		}
	}

	/**
	 * API Method updates an existing Location record and render response as JSON
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
			$location = $this->Phreezer->Get('Location',$pk);

			// TODO: any fields that should not be updated by the user should be commented out

			// this is a primary key.  uncomment if updating is allowed
			// $location->Id = $this->SafeGetVal($json, 'id', $location->Id);

			$location->Name = $this->SafeGetVal($json, 'name', $location->Name);
			$location->CreatedAt = date('Y-m-d H:i:s',strtotime($this->SafeGetVal($json, 'createdAt', $location->CreatedAt)));

			$location->Validate();
			$errors = $location->GetValidationErrors();

			if (count($errors) > 0)
			{
				$this->RenderErrorJSON('Please check the form for errors',$errors);
			}
			else
			{
				$location->Save();
				$this->RenderJSON($location, $this->JSONPCallback(), true, $this->SimpleObjectParams());
			}


		}
		catch (Exception $ex)
		{


			$this->RenderExceptionJSON($ex);
		}
	}

	/**
	 * API Method deletes an existing Location record and render response as JSON
	 */
	public function Delete()
	{
		try
		{
						
			// TODO: if a soft delete is prefered, change this to update the deleted flag instead of hard-deleting

			$pk = $this->GetRouter()->GetUrlParam('id');
			$location = $this->Phreezer->Get('Location',$pk);

			$location->Delete();

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

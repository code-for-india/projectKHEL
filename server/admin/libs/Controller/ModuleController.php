<?php
/** @package    ProjectKHEL::Controller */

/** import supporting libraries */
require_once("AppBaseController.php");
require_once("Model/Module.php");

/**
 * ModuleController is the controller class for the Module object.  The
 * controller is responsible for processing input from the user, reading/updating
 * the model as necessary and displaying the appropriate view.
 *
 * @package ProjectKHEL::Controller
 * @author ClassBuilder
 * @version 1.0
 */
class ModuleController extends AppBaseController
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
	 * Displays a list view of Module objects
	 */
	public function ListView()
	{
		$this->Render();
	}

	/**
	 * API Method queries for Module records and render as JSON
	 */
	public function Query()
	{
		try
		{
			$criteria = new ModuleCriteria();
			
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

				$modules = $this->Phreezer->Query('Module',$criteria)->GetDataPage($page, $pagesize);
				$output->rows = $modules->ToObjectArray(true,$this->SimpleObjectParams());
				$output->totalResults = $modules->TotalResults;
				$output->totalPages = $modules->TotalPages;
				$output->pageSize = $modules->PageSize;
				$output->currentPage = $modules->CurrentPage;
			}
			else
			{
				// return all results
				$modules = $this->Phreezer->Query('Module',$criteria);
				$output->rows = $modules->ToObjectArray(true, $this->SimpleObjectParams());
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
	 * API Method retrieves a single Module record and render as JSON
	 */
	public function Read()
	{
		try
		{
			$pk = $this->GetRouter()->GetUrlParam('id');
			$module = $this->Phreezer->Get('Module',$pk);
			$this->RenderJSON($module, $this->JSONPCallback(), true, $this->SimpleObjectParams());
		}
		catch (Exception $ex)
		{
			$this->RenderExceptionJSON($ex);
		}
	}

	/**
	 * API Method inserts a new Module record and render response as JSON
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

			$module = new Module($this->Phreezer);

			// TODO: any fields that should not be inserted by the user should be commented out

			// this is an auto-increment.  uncomment if updating is allowed
			// $module->Id = $this->SafeGetVal($json, 'id');

			$module->Name = $this->SafeGetVal($json, 'name');
			$module->CreatedAt = date('Y-m-d H:i:s',strtotime($this->SafeGetVal($json, 'createdAt')));

			$module->Validate();
			$errors = $module->GetValidationErrors();

			if (count($errors) > 0)
			{
				$this->RenderErrorJSON('Please check the form for errors',$errors);
			}
			else
			{
				$module->Save();
				$this->RenderJSON($module, $this->JSONPCallback(), true, $this->SimpleObjectParams());
			}

		}
		catch (Exception $ex)
		{
			$this->RenderExceptionJSON($ex);
		}
	}

	/**
	 * API Method updates an existing Module record and render response as JSON
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
			$module = $this->Phreezer->Get('Module',$pk);

			// TODO: any fields that should not be updated by the user should be commented out

			// this is a primary key.  uncomment if updating is allowed
			// $module->Id = $this->SafeGetVal($json, 'id', $module->Id);

			$module->Name = $this->SafeGetVal($json, 'name', $module->Name);
			$module->CreatedAt = date('Y-m-d H:i:s',strtotime($this->SafeGetVal($json, 'createdAt', $module->CreatedAt)));

			$module->Validate();
			$errors = $module->GetValidationErrors();

			if (count($errors) > 0)
			{
				$this->RenderErrorJSON('Please check the form for errors',$errors);
			}
			else
			{
				$module->Save();
				$this->RenderJSON($module, $this->JSONPCallback(), true, $this->SimpleObjectParams());
			}


		}
		catch (Exception $ex)
		{


			$this->RenderExceptionJSON($ex);
		}
	}

	/**
	 * API Method deletes an existing Module record and render response as JSON
	 */
	public function Delete()
	{
		try
		{
						
			// TODO: if a soft delete is prefered, change this to update the deleted flag instead of hard-deleting

			$pk = $this->GetRouter()->GetUrlParam('id');
			$module = $this->Phreezer->Get('Module',$pk);

			$module->Delete();

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
